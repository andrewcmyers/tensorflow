package org.tensorflow.examples;

import static org.tensorflow.Types.*;

import java.util.function.Supplier;

import org.tensorflow.DataType;
import org.tensorflow.Graph;
import org.tensorflow.Output;
import org.tensorflow.Tensor;
import org.tensorflow.Types;
import org.tensorflow.op.Scope;
import org.tensorflow.op.Tensors;

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

    Output<TFFloat> div(Output<TFFloat> x, Output<TFFloat> y) {
        return binaryOp("Div", x, y);
    }

    <T> Output<T> sub(Output<T> x, Output<T> y) {
        return binaryOp("Sub", x, y);
    }

    <T> Output<TFFloat> resizeBilinear(Output<T> images, Output<TFInt32> size) {
        return binaryOp3("ResizeBilinear", images, size);
    }

    <T> Output<T> expandDims(Output<T> input, Output<TFInt32> dim) {
        return binaryOp3("ExpandDims", input, dim);
    }

    <T, U> Output<U> cast(Output<T> value, Class<U> type) {
        DataType dtype = Types.dataType(type);
        return graph.opBuilder("Cast", "Cast").addInput(value)
                .setAttr("DstT", dtype).build().output(0);
    }

    Output<TFUInt8> decodeJpeg(Output<TFString> contents, long channels) {
        return graph.opBuilder("DecodeJpeg", "DecodeJpeg").addInput(contents)
                .setAttr("channels", channels).build().output(0);
    }

    <T> Output<T> constant(Object value, Class<T> type) {
        try (Tensor<T> t = Tensor.create(value, type)) {
            return graph.opBuilder("Const", scope.makeOpName("constant")).setAttr("dtype", t.dataType())
                    .setAttr("value", t).build().output(0);
        }
    }

    Output<TFString> stringConstant(byte[] value) {
        try (Tensor<TFString> t = Tensors.create(value)) {
            return graph.opBuilder("Const", scope.makeOpName("constant")).setAttr("dtype", t.dataType())
                    .setAttr("value", t).build().output(0);
        }
    }

    Output<TFInt32> constant(int value) {
        try (Tensor<TFInt32> t = Tensor.create(value, INT32)) {
            return graph.opBuilder("Const", scope.makeOpName("constant"))
                    .setAttr("dtype", DataType.INT32).setAttr("value", t)
                    .build().output(0);
        }
    }

    Output<TFInt32> constant(int[] value) {
        try (Tensor<TFInt32> t = Tensor.create(value, INT32)) {
            return graph.opBuilder("Const", scope.makeOpName("constant"))
                    .setAttr("dtype", DataType.INT32).setAttr("value", t)
                    .build().output(0);
        }
    }

    Output<TFFloat> constant(float value) {
        try (Tensor<TFFloat> t = Tensor.create(value, FLOAT)) {
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
