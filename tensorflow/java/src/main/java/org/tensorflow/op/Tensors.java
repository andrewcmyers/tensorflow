package org.tensorflow.op;
import static org.tensorflow.Types.*;
import org.tensorflow.Tensor;
import org.tensorflow.Types;

/**
 * Convenience methods for creating Tensors of popular types, with type checking.
 */
public class Tensors {
  public static Tensor<TFString> create(byte[] data) {
    return Tensor.create(data);
  }
  public static Tensor<TFString> create(String data) {
    return Tensor.create(data);
  }
    public static Tensor<TFFloat> create(float data) {
    return Tensor.create(data, Types.FLOAT);
  }
  public static Tensor<TFFloat> create(float[] data) {
    return Tensor.create(data, Types.FLOAT);
  }
  public static Tensor<TFFloat> create(float[][] data) {
    return Tensor.create(data, Types.FLOAT);
  }
  public static Tensor<TFFloat> create(float[][][] data) {
    return Tensor.create(data, Types.FLOAT);
  }
  public static Tensor<TFFloat> create(float[][][][] data) {
    return Tensor.create(data, Types.FLOAT);
  }
  public static Tensor<TFFloat> create(float[][][][][] data) {
    return Tensor.create(data, Types.FLOAT);
  }
  public static Tensor<TFFloat> create(float[][][][][][] data) {
    return Tensor.create(data, Types.FLOAT);
  }
  public static Tensor<TFDouble> create(double data) {
    return Tensor.create(data, Types.DOUBLE);
  }
  public static Tensor<TFDouble> create(double[] data) {
    return Tensor.create(data, Types.DOUBLE);
  }
  public static Tensor<TFDouble> create(double[][] data) {
    return Tensor.create(data, Types.DOUBLE);
  }
  public static Tensor<TFDouble> create(double[][][] data) {
    return Tensor.create(data, Types.DOUBLE);
  }
  public static Tensor<TFDouble> create(double[][][][] data) {
    return Tensor.create(data, Types.DOUBLE);
  }
  public static Tensor<TFDouble> create(double[][][][][] data) {
    return Tensor.create(data, Types.DOUBLE);
  }
  public static Tensor<TFDouble> create(double[][][][][][] data) {
    return Tensor.create(data, Types.DOUBLE);
  }
  public static Tensor<TFInt32> create(int data) {
    return Tensor.create(data, Types.INT32);
  }
  public static Tensor<TFInt32> create(int[] data) {
    return Tensor.create(data, Types.INT32);
  }
  public static Tensor<TFInt32> create(int[][] data) {
    return Tensor.create(data, Types.INT32);
  }
  public static Tensor<TFInt32> create(int[][][] data) {
    return Tensor.create(data, Types.INT32);
  }
  public static Tensor<TFInt32> create(int[][][][] data) {
    return Tensor.create(data, Types.INT32);
  }
  public static Tensor<TFInt32> create(int[][][][][] data) {
    return Tensor.create(data, Types.INT32);
  }
  public static Tensor<TFInt32> create(int[][][][][][] data) {
    return Tensor.create(data, Types.INT32);
  }
  public static Tensor<TFInt16> create(short data) {
    return Tensor.create(data, Types.INT16);
  }
  public static Tensor<TFInt16> create(short[] data) {
    return Tensor.create(data, Types.INT16);
  }
  public static Tensor<TFInt16> create(short[][] data) {
    return Tensor.create(data, Types.INT16);
  }
  public static Tensor<TFInt16> create(short[][][] data) {
    return Tensor.create(data, Types.INT16);
  }
  public static Tensor<TFInt16> create(short[][][][] data) {
    return Tensor.create(data, Types.INT16);
  }
  public static Tensor<TFInt16> create(short[][][][][] data) {
    return Tensor.create(data, Types.INT16);
  }
  public static Tensor<TFInt16> create(short[][][][][][] data) {
    return Tensor.create(data, Types.INT16);
  }
  public static Tensor<TFInt64> create(long data) {
    return Tensor.create(data, Types.INT64);
  }
  public static Tensor<TFInt64> create(long[] data) {
    return Tensor.create(data, Types.INT64);
  }
  public static Tensor<TFInt64> create(long[][] data) {
    return Tensor.create(data, Types.INT64);
  }
  public static Tensor<TFInt64> create(long[][][] data) {
    return Tensor.create(data, Types.INT64);
  }
  public static Tensor<TFInt64> create(long[][][][] data) {
    return Tensor.create(data, Types.INT64);
  }
  public static Tensor<TFInt64> create(long[][][][][] data) {
    return Tensor.create(data, Types.INT64);
  }
  public static Tensor<TFInt64> create(long[][][][][][] data) {
    return Tensor.create(data, Types.INT64);
  }
  public static Tensor<TFBool> create(boolean data) {
    return Tensor.create(data, Types.BOOL);
  }
  public static Tensor<TFBool> create(boolean[] data) {
    return Tensor.create(data, Types.BOOL);
  }
  public static Tensor<TFBool> create(boolean[][] data) {
    return Tensor.create(data, Types.BOOL);
  }
  public static Tensor<TFBool> create(boolean[][][] data) {
    return Tensor.create(data, Types.BOOL);
  }
  public static Tensor<TFBool> create(boolean[][][][] data) {
    return Tensor.create(data, Types.BOOL);
  }
  public static Tensor<TFBool> create(boolean[][][][][] data) {
    return Tensor.create(data, Types.BOOL);
  }
  public static Tensor<TFBool> create(boolean[][][][][][] data) {
    return Tensor.create(data, Types.BOOL);
  }
}

