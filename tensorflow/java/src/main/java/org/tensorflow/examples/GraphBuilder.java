package org.tensorflow.examples;

import static org.tensorflow.Type.FLOAT;
import static org.tensorflow.Type.INT32;
import static org.tensorflow.Type.STRING;
import static org.tensorflow.Type.UINT8;

import java.util.function.Supplier;

import org.tensorflow.DataType;
import org.tensorflow.Graph;
import org.tensorflow.Output;
import org.tensorflow.Tensor;
import org.tensorflow.Type;
import org.tensorflow.op.Scope;

//In the fullness of time, equivalents of the methods of this class should be auto-generated from
// the OpDefs linked into libtensorflow_jni.so. That would match what is done in other languages
// like Python, C++ and Go.
public class GraphBuilder {

    GraphBuilder(Graph g) {
        graph = g;
        scope = new Scope(g);
    }

    <T> Output<T> withScope(String scopeName, Supplier<Output<T>> body) {
        Scope old = scope;
        scope = scope.withName(scopeName);
        Output<T> r = body.get();
        scope = old;
        return r;
    }

    Output<Float> div(Output<Float> x, Output<Float> y) {
        return binaryOp("Div", x, y);
    }

    <T> Output<T> sub(Output<T> x, Output<T> y) {
        return binaryOp("Sub", x, y);
    }

    <T> Output<Float> resizeBilinear(Output<T> images, Output<Integer> size) {
        return binaryOp3("ResizeBilinear", images, size);
    }

    <T> Output<T> expandDims(Output<T> input, Output<Integer> dim) {
        return binaryOp3("ExpandDims", input, dim);
    }

    <T, U> Output<U> cast(Output<T> value, Type<U> type) {
        DataType dtype = type.dataType();
        return graph.opBuilder("Cast", "Cast").addInput(value)
                .setAttr("DstT", dtype).build().output(0);
    }

    Output<Byte> decodeJpeg(Output<String> contents, long channels) {
        return graph.opBuilder("DecodeJpeg", "DecodeJpeg").addInput(contents)
                .setAttr("channels", channels).build().output(0);
    }

    <T> Output<T> constant(Object value, Type<T> type) {
        try (Tensor<T> t = Tensor.create(value, type)) {
            return graph.opBuilder("Const", scope.makeOpName("constant")).setAttr("dtype", t.dataType())
                    .setAttr("value", t).build().output(0);
        }
    }

    Output<Byte> byteConstant(byte[] value) {
        try (Tensor<Byte> t = Tensor.create(value, UINT8)) {
            return graph.opBuilder("Const", scope.makeOpName("constant")).setAttr("dtype", t.dataType())
                    .setAttr("value", t).build().output(0);
        }
    }

    Output<String> stringConstant(byte[] value) {
        try (Tensor<String> t = Tensor.create(value, STRING)) {
            return graph.opBuilder("Const", scope.makeOpName("constant")).setAttr("dtype", t.dataType())
                    .setAttr("value", t).build().output(0);
        }
    }

    Output<Integer> constant(int value) {
        try (Tensor<Integer> t = Tensor.create(value, INT32)) {
            return graph.opBuilder("Const", scope.makeOpName("constant"))
                    .setAttr("dtype", DataType.INT32).setAttr("value", t)
                    .build().output(0);
        }
    }

    Output<Integer> constant(int[] value) {
        try (Tensor<Integer> t = Tensor.create(value, INT32)) {
            return graph.opBuilder("Const", scope.makeOpName("constant"))
                    .setAttr("dtype", DataType.INT32).setAttr("value", t)
                    .build().output(0);
        }
    }

    Output<Float> constant(float value) {
        try (Tensor<Float> t = Tensor.create(value, FLOAT)) {
            return graph.opBuilder("Const", scope.makeOpName("constant"))
                    .setAttr("dtype", DataType.FLOAT).setAttr("value", t)
                    .build().output(0);
        }
    }

    private <T> Output<T> binaryOp(String type, Output<T> in1, Output<T> in2) {
        return graph.opBuilder(type, type).addInput(in1).addInput(in2).build()
                .output(0);
    }

    private <T, U, V> Output<T> binaryOp3(String type, Output<U> in1,
            Output<V> in2) {
        return graph.opBuilder(type, type).addInput(in1).addInput(in2).build()
                .output(0);
    }

    private Graph graph;
    private Scope scope;
}
