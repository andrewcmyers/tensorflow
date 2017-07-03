# Copyright 2016 The TensorFlow Authors. All Rights Reserved.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
# ==============================================================================
"""Tests for the Bernoulli distribution."""

from __future__ import absolute_import
from __future__ import division
from __future__ import print_function

import importlib

import numpy as np

from tensorflow.python.framework import constant_op
from tensorflow.python.framework import dtypes
from tensorflow.python.ops import array_ops
from tensorflow.python.ops import math_ops
from tensorflow.python.ops.distributions import bernoulli
from tensorflow.python.ops.distributions import kullback_leibler
from tensorflow.python.platform import test
from tensorflow.python.platform import tf_logging


def try_import(name):  # pylint: disable=invalid-name
  module = None
  try:
    module = importlib.import_module(name)
  except ImportError as e:
    tf_logging.warning("Could not import %s: %s" % (name, str(e)))
  return module


special = try_import("scipy.special")


def make_bernoulli(batch_shape, dtype=dtypes.int32):
  p = np.random.uniform(size=list(batch_shape))
  p = constant_op.constant(p, dtype=dtypes.float32)
  return bernoulli.Bernoulli(probs=p, dtype=dtype)


def entropy(p):
  q = 1. - p
  return -q * np.log(q) - p * np.log(p)


class BernoulliTest(test.TestCase):

  def testP(self):
    p = [0.2, 0.4]
    dist = bernoulli.Bernoulli(probs=p)
    with self.test_session():
      self.assertAllClose(p, dist.probs.eval())

  def testLogits(self):
    logits = [-42., 42.]
    dist = bernoulli.Bernoulli(logits=logits)
    with self.test_session():
      self.assertAllClose(logits, dist.logits.eval())

    if not special:
      return

    with self.test_session():
      self.assertAllClose(special.expit(logits), dist.probs.eval())

    p = [0.01, 0.99, 0.42]
    dist = bernoulli.Bernoulli(probs=p)
    with self.test_session():
      self.assertAllClose(special.logit(p), dist.logits.eval())

  def testInvalidP(self):
    invalid_ps = [1.01, 2.]
    for p in invalid_ps:
      with self.test_session():
        with self.assertRaisesOpError("probs has components greater than 1"):
          dist = bernoulli.Bernoulli(probs=p, validate_args=True)
          dist.probs.eval()

    invalid_ps = [-0.01, -3.]
    for p in invalid_ps:
      with self.test_session():
        with self.assertRaisesOpError("Condition x >= 0"):
          dist = bernoulli.Bernoulli(probs=p, validate_args=True)
          dist.probs.eval()

    valid_ps = [0.0, 0.5, 1.0]
    for p in valid_ps:
      with self.test_session():
        dist = bernoulli.Bernoulli(probs=p)
        self.assertEqual(p, dist.probs.eval())  # Should not fail

  def testShapes(self):
    with self.test_session():
      for batch_shape in ([], [1], [2, 3, 4]):
        dist = make_bernoulli(batch_shape)
        self.assertAllEqual(batch_shape, dist.batch_shape.as_list())
        self.assertAllEqual(batch_shape, dist.batch_shape_tensor().eval())
        self.assertAllEqual([], dist.event_shape.as_list())
        self.assertAllEqual([], dist.event_shape_tensor().eval())

  def testDtype(self):
    dist = make_bernoulli([])
    self.assertEqual(dist.dtype, dtypes.int32)
    self.assertEqual(dist.dtype, dist.sample(5).dtype)
    self.assertEqual(dist.dtype, dist.mode().dtype)
    self.assertEqual(dist.probs.dtype, dist.mean().dtype)
    self.assertEqual(dist.probs.dtype, dist.variance().dtype)
    self.assertEqual(dist.probs.dtype, dist.stddev().dtype)
    self.assertEqual(dist.probs.dtype, dist.entropy().dtype)
    self.assertEqual(dist.probs.dtype, dist.prob(0).dtype)
    self.assertEqual(dist.probs.dtype, dist.log_prob(0).dtype)

    dist64 = make_bernoulli([], dtypes.int64)
    self.assertEqual(dist64.dtype, dtypes.int64)
    self.assertEqual(dist64.dtype, dist64.sample(5).dtype)
    self.assertEqual(dist64.dtype, dist64.mode().dtype)

  def _testPmf(self, **kwargs):
    dist = bernoulli.Bernoulli(**kwargs)
    with self.test_session():
      # pylint: disable=bad-continuation
      xs = [
          0,
          [1],
          [1, 0],
          [[1, 0]],
          [[1, 0], [1, 1]],
      ]
      expected_pmfs = [
          [[0.8, 0.6], [0.7, 0.4]],
          [[0.2, 0.4], [0.3, 0.6]],
          [[0.2, 0.6], [0.3, 0.4]],
          [[0.2, 0.6], [0.3, 0.4]],
          [[0.2, 0.6], [0.3, 0.6]],
      ]
      # pylint: enable=bad-continuation

      for x, expected_pmf in zip(xs, expected_pmfs):
        self.assertAllClose(dist.prob(x).eval(), expected_pmf)
        self.assertAllClose(dist.log_prob(x).eval(), np.log(expected_pmf))

  def testPmfCorrectBroadcastDynamicShape(self):
    with self.test_session():
      p = array_ops.placeholder(dtype=dtypes.float32)
      dist = bernoulli.Bernoulli(probs=p)
      event1 = [1, 0, 1]
      event2 = [[1, 0, 1]]
      self.assertAllClose(
          dist.prob(event1).eval({
              p: [0.2, 0.3, 0.4]
          }), [0.2, 0.7, 0.4])
      self.assertAllClose(
          dist.prob(event2).eval({
              p: [0.2, 0.3, 0.4]
          }), [[0.2, 0.7, 0.4]])

  def testPmfInvalid(self):
    p = [0.1, 0.2, 0.7]
    with self.test_session():
      dist = bernoulli.Bernoulli(probs=p, validate_args=True)
      with self.assertRaisesOpError("must be non-negative."):
        dist.prob([1, 1, -1]).eval()
      with self.assertRaisesOpError("Elements cannot exceed 1."):
        dist.prob([2, 0, 1]).eval()

  def testPmfWithP(self):
    p = [[0.2, 0.4], [0.3, 0.6]]
    self._testPmf(probs=p)
    if not special:
      return
    self._testPmf(logits=special.logit(p))

  def testBroadcasting(self):
    with self.test_session():
      p = array_ops.placeholder(dtypes.float32)
      dist = bernoulli.Bernoulli(probs=p)
      self.assertAllClose(np.log(0.5), dist.log_prob(1).eval({p: 0.5}))
      self.assertAllClose(
          np.log([0.5, 0.5, 0.5]), dist.log_prob([1, 1, 1]).eval({
              p: 0.5
          }))
      self.assertAllClose(
          np.log([0.5, 0.5, 0.5]), dist.log_prob(1).eval({
              p: [0.5, 0.5, 0.5]
          }))

  def testPmfShapes(self):
    with self.test_session():
      p = array_ops.placeholder(dtypes.float32, shape=[None, 1])
      dist = bernoulli.Bernoulli(probs=p)
      self.assertEqual(2, len(dist.log_prob(1).eval({p: [[0.5], [0.5]]}).shape))

    with self.test_session():
      dist = bernoulli.Bernoulli(probs=0.5)
      self.assertEqual(2, len(dist.log_prob([[1], [1]]).eval().shape))

    with self.test_session():
      dist = bernoulli.Bernoulli(probs=0.5)
      self.assertEqual((), dist.log_prob(1).get_shape())
      self.assertEqual((1), dist.log_prob([1]).get_shape())
      self.assertEqual((2, 1), dist.log_prob([[1], [1]]).get_shape())

    with self.test_session():
      dist = bernoulli.Bernoulli(probs=[[0.5], [0.5]])
      self.assertEqual((2, 1), dist.log_prob(1).get_shape())

  def testBoundaryConditions(self):
    with self.test_session():
      dist = bernoulli.Bernoulli(probs=1.0)
      self.assertAllClose(np.nan, dist.log_prob(0).eval())
      self.assertAllClose([np.nan], [dist.log_prob(1).eval()])

  def testEntropyNoBatch(self):
    p = 0.2
    dist = bernoulli.Bernoulli(probs=p)
    with self.test_session():
      self.assertAllClose(dist.entropy().eval(), entropy(p))

  def testEntropyWithBatch(self):
    p = [[0.1, 0.7], [0.2, 0.6]]
    dist = bernoulli.Bernoulli(probs=p, validate_args=False)
    with self.test_session():
      self.assertAllClose(dist.entropy().eval(), [[entropy(0.1), entropy(0.7)],
                                                  [entropy(0.2), entropy(0.6)]])

  def testSampleN(self):
    with self.test_session():
      p = [0.2, 0.6]
      dist = bernoulli.Bernoulli(probs=p)
      n = 100000
      samples = dist.sample(n)
      samples.set_shape([n, 2])
      self.assertEqual(samples.dtype, dtypes.int32)
      sample_values = samples.eval()
      self.assertTrue(np.all(sample_values >= 0))
      self.assertTrue(np.all(sample_values <= 1))
      # Note that the standard error for the sample mean is ~ sqrt(p * (1 - p) /
      # n). This means that the tolerance is very sensitive to the value of p
      # as well as n.
      self.assertAllClose(p, np.mean(sample_values, axis=0), atol=1e-2)
      self.assertEqual(set([0, 1]), set(sample_values.flatten()))
      # In this test we're just interested in verifying there isn't a crash
      # owing to mismatched types. b/30940152
      dist = bernoulli.Bernoulli(np.log([.2, .4]))
      self.assertAllEqual((1, 2), dist.sample(1, seed=42).get_shape().as_list())

  def testSampleActsLikeSampleN(self):
    with self.test_session() as sess:
      p = [0.2, 0.6]
      dist = bernoulli.Bernoulli(probs=p)
      n = 1000
      seed = 42
      self.assertAllEqual(
          dist.sample(n, seed).eval(), dist.sample(n, seed).eval())
      n = array_ops.placeholder(dtypes.int32)
      sample, sample = sess.run([dist.sample(n, seed), dist.sample(n, seed)],
                                feed_dict={n: 1000})
      self.assertAllEqual(sample, sample)

  def testMean(self):
    with self.test_session():
      p = np.array([[0.2, 0.7], [0.5, 0.4]], dtype=np.float32)
      dist = bernoulli.Bernoulli(probs=p)
      self.assertAllEqual(dist.mean().eval(), p)

  def testVarianceAndStd(self):
    var = lambda p: p * (1. - p)
    with self.test_session():
      p = [[0.2, 0.7], [0.5, 0.4]]
      dist = bernoulli.Bernoulli(probs=p)
      self.assertAllClose(
          dist.variance().eval(),
          np.array(
              [[var(0.2), var(0.7)], [var(0.5), var(0.4)]], dtype=np.float32))
      self.assertAllClose(
          dist.stddev().eval(),
          np.array(
              [[np.sqrt(var(0.2)), np.sqrt(var(0.7))],
               [np.sqrt(var(0.5)), np.sqrt(var(0.4))]],
              dtype=np.float32))

  def testBernoulliWithSigmoidProbs(self):
    p = np.array([8.3, 4.2])
    dist = bernoulli.BernoulliWithSigmoidProbs(logits=p)
    with self.test_session():
      self.assertAllClose(math_ops.sigmoid(p).eval(), dist.probs.eval())

  def testBernoulliBernoulliKL(self):
    with self.test_session() as sess:
      batch_size = 6
      a_p = np.array([0.5] * batch_size, dtype=np.float32)
      b_p = np.array([0.4] * batch_size, dtype=np.float32)

      a = bernoulli.Bernoulli(probs=a_p)
      b = bernoulli.Bernoulli(probs=b_p)

      kl = kullback_leibler.kl_divergence(a, b)
      kl_val = sess.run(kl)

      kl_expected = (a_p * np.log(a_p / b_p) + (1. - a_p) * np.log(
          (1. - a_p) / (1. - b_p)))

      self.assertEqual(kl.get_shape(), (batch_size,))
      self.assertAllClose(kl_val, kl_expected)


if __name__ == "__main__":
  test.main()
