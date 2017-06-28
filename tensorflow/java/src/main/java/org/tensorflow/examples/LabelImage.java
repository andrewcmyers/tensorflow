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

package org.tensorflow.examples;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.tensorflow.Graph;
import org.tensorflow.Output;
import org.tensorflow.Session;
import org.tensorflow.Tensor;
import org.tensorflow.TensorFlow;
import static org.tensorflow.Type.*;

/**
 * Sample use of the TensorFlow Java API to label images using a pre-trained
 * model.
 */
public class LabelImage {
    private static void printUsage(PrintStream s) {
        final String url = "https://storage.googleapis.com/download.tensorflow.org/models/inception5h.zip";
        s.println(
                "Java program that uses a pre-trained Inception model (http://arxiv.org/abs/1512.00567)");
        s.println("to label JPEG images.");
        s.println("TensorFlow version: " + TensorFlow.version());
        s.println();
        s.println("Usage: label_image <model dir> <image file>");
        s.println();
        s.println("Where:");
        s.println(
                "<model dir> is a directory containing the unzipped contents of the inception model");
        s.println("            (from " + url + ")");
        s.println("<image file> is the path to a JPEG image file");
    }

    public static void main(String[] args) {
        if (args.length != 2) {
            printUsage(System.err);
            System.exit(1);
        }
        String modelDir = args[0];
        String imageFile = args[1];

        byte[] graphDef = readAllBytesOrExit(
                Paths.get(modelDir, "tensorflow_inception_graph.pb"));
        List<String> labels = readAllLinesOrExit(
                Paths.get(modelDir, "imagenet_comp_graph_label_strings.txt"));
        byte[] imageBytes = readAllBytesOrExit(Paths.get(imageFile));

        try (Tensor<Float> image = constructAndExecuteGraphToNormalizeImage(
                imageBytes)) {
            float[] labelProbabilities = executeInceptionGraph(graphDef, image);
            int bestLabelIdx = maxIndex(labelProbabilities);
            System.out.println(String.format("BEST MATCH: %s (%.2f%% likely)",
                    labels.get(bestLabelIdx),
                    labelProbabilities[bestLabelIdx] * 100f));
        }
    }

    private static Tensor<Float> constructAndExecuteGraphToNormalizeImage(
            byte[] imageBytes) {
        try (Graph g = new Graph()) {

            // Some constants specific to the pre-trained model at:
            // https://storage.googleapis.com/download.tensorflow.org/models/inception5h.zip
            //
            // - The model was trained with images scaled to 224x224 pixels.
            // - The colors, represented as R, G, B in 1-byte each were
            // converted to
            // float using (value - Mean)/Scale.
            final int H = 224;
            final int W = 224;
            final float mean = 117f;
            final float scale = 1f;

            // Since the graph is being constructed once per execution here, we
            // can use a constant for the
            // input image. If the graph were to be re-used for multiple input
            // images, a placeholder would
            // have been more appropriate.

            Output<Float> output = new GraphBuilder(g) {
                Output<Float> imageIdentifier(String inp) {
                    final Output<String> input = stringConstant(imageBytes);

                    return div(
                            sub(resizeBilinear(
                                    withScope("decode",
                                            () -> expandDims(
                                                    cast(decodeJpeg(input, 3), FLOAT),
                                                    constant(0))),
                                    constant(new int[] { H, W })),
                                    constant(mean)),
                            constant(scale));
                }
            }.imageIdentifier("input");

            try (Session s = new Session(g)) {
                return s.runner().fetch(output.op().name()).run().get(0)
                        .expect(FLOAT);
            }
        }
    }

    private static float[] executeInceptionGraph(byte[] graphDef,
            Tensor<Float> image) {
        try (Graph g = new Graph()) {
            g.importGraphDef(graphDef);
            try (Session s = new Session(g);
                    Tensor<Float> result = s.runner().feed("input", image)
                            .fetch("output").run().get(0).expect(FLOAT)) {
                final long[] rshape = result.shape();
                if (result.numDimensions() != 2 || rshape[0] != 1) {
                    throw new RuntimeException(String.format(
                            "Expected model to produce a [1 N] shaped tensor where N is the number of labels, instead it produced one with shape %s",
                            Arrays.toString(rshape)));
                }
                int nlabels = (int) rshape[1];
                return result.copyTo(new float[1][nlabels])[0];
            }
        }
    }

    private static int maxIndex(float[] probabilities) {
        int best = 0;
        for (int i = 1; i < probabilities.length; ++i) {
            if (probabilities[i] > probabilities[best]) {
                best = i;
            }
        }
        return best;
    }

    private static byte[] readAllBytesOrExit(Path path) {
        try {
            return Files.readAllBytes(path);
        } catch (IOException e) {
            System.err.println(
                    "Failed to read [" + path + "]: " + e.getMessage());
            System.exit(1);
        }
        return null;
    }

    private static List<String> readAllLinesOrExit(Path path) {
        try {
            return Files.readAllLines(path, Charset.forName("UTF-8"));
        } catch (IOException e) {
            System.err.println(
                    "Failed to read [" + path + "]: " + e.getMessage());
            System.exit(0);
        }
        return null;
    }
}
