/* Copyright 2016 The TensorFlow Authors. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
==============================================================================*/

package org.tensorflow;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;

/**
 * A symbolic handle to a tensor produced by an {@link Operation}.
 *
 * <p>An Output<T> is a symbolic handle to a Tensor<T>. The value of the tensor is computed by executing
 * the {@link Operation} in a {@link Session}.
 */
public final class Output<T> extends Tensor<T> {

  /** Handle to the idx-th output of the Operation {@code op}. */
  public Output(Operation op, int idx) {
    operation = op;
    index = idx;
  }

  /** Returns the Operation that will produce the tensor referred to by this Output. */
  public Operation op() {
    return operation;
  }

  /** Returns the index into the outputs of the Operation. */
  public int index() {
    return index;
  }

  /** Returns the (possibly partially known) shape of the tensor referred to by this Output. */
  @Override public Shape shape() {
    return new Shape(operation.shape(index));
  }

  /** Returns the DataType of the tensor referred to by this Output. */
  @Override public DataType dataType() {
    return operation.dtype(index);
  }
  
  public TensorValue<T> value() {
	  if (value == null) value = force();
	  return value;
  }
  
  /** Force computation of this output. */
  private TensorValue<T> force() {
	  throw new UnsupportedOperationException(); // XXX implement me
  }
  
  @Override public <U> Tensor<U> expect(BaseType<U> type) {
		return value().expect(type);
	}

	@Override public int numDimensions() {
		return value().numDimensions();
	}

	@Override public int numBytes() {
		return value().numBytes();
	}

	@Override public int numElements() {
		return value().numElements();
	}

	@Override public void writeTo(DoubleBuffer dst) {
		value().writeTo(dst);
	}

	@Override public void writeTo(ByteBuffer dst) {
		value().writeTo(dst);
	}
	
	@Override
	public void close() {
		if (value != null) value.close();
	}

	@Override
	public float floatValue() {
		return value().floatValue();
	}

  private final Operation operation;
  private final int index;
  private TensorValue<T> value; // may be null if not computed yet
}
