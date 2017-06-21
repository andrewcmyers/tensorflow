package org.tensorflow;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.DoubleBuffer;
import java.util.Arrays;

public class TensorValue<T> extends Tensor<T> {
	  /**
	   * Create a tensor containing datatype t
	   * 
	   *  <p>Requires: t must match T
	   */
	  TensorValue(DataType t) {
	    dtype = t;
	  }
	  
	  public void writeTo(DoubleBuffer dst) {
		  if (dtype != DataType.DOUBLE) {
			  throw incompatibleBuffer(dst, dtype);
		  }
		  ByteBuffer src = buffer();
		  dst.put(src.asDoubleBuffer());
	  }
	  public void writeTo(ByteBuffer dst) {
		  ByteBuffer src = buffer();
		  dst.put(src);
	  }
	  /** Returns a string describing the type and shape of the Tensor. */
	  @Override
	  public String toString() {
	    return String.format("%s tensor with shape %s", dtype.toString(), Arrays.toString(shape().asArray()));
	  }
	  
	  @SuppressWarnings("unchecked") public <U> Tensor<U> expect(BaseType<U> type) {
		  DataType dt = type.dataType();
		  if (!dt.equals(dtype))
			  throw new IllegalArgumentException("Cannot cast from tensor of " + dtype + " to tensor of " + dt);
		  return ((Tensor<U>) this); 
	  }
	  
	  /** Returns the {@link DataType} of elements stored in the Tensor. */
	  public DataType dataType() {
	    return dtype;
	  }
	  
	  public int numDimensions() {
		  return shapeCopy.length;
	  }
	  public int numBytes() {
	    return buffer().remaining();
	  }
	  public int numElements() {
	    return numElements(shapeCopy);
	  }
	  public Shape shape() {
	    return new Shape(shapeCopy);
	  }
	  
	  @Override
	  public void close() {
	    if (nativeHandle != 0) {
	      delete(nativeHandle);
	      nativeHandle = 0;
	    }
	  }
	  
	  @Override public float floatValue() {
		  return scalarFloat(nativeHandle);
	  }
	  /**
	   * Create a Tensor object from a handle to the C TF_Tensor object.
	   *
	   * <p>Takes ownership of the handle.
	   */
	  static <T> TensorValue<T> fromHandle(long handle) {
	    TensorValue<T> t = new TensorValue<T>(DataType.fromC(dtype(handle)));
	    t.shapeCopy = shape(handle);
	    t.nativeHandle = handle;
	    return t;
	  }

	  long getNativeHandle() {
	    return nativeHandle;
	  }


	  ByteBuffer buffer() {
	    return buffer(nativeHandle).order(ByteOrder.nativeOrder());
	  }

	  private static IllegalArgumentException incompatibleBuffer(Buffer buf, DataType dataType) {
	    return new IllegalArgumentException(
	        String.format("cannot use %s with Tensor of type %s", buf.getClass().getName(), dataType));
	  }

	  private void throwExceptionIfTypeIsIncompatible(Object o) {
		    if (numDimensions(o) != numDimensions()) {
		      throw new IllegalArgumentException(
		          String.format(
		              "cannot copy Tensor with %d dimensions into an object with %d",
		              numDimensions(), numDimensions(o)));
		    }
		    if (dataTypeOf(o) != dtype) {
		      throw new IllegalArgumentException(
		          String.format(
		              "cannot copy Tensor with DataType %s into an object of type %s",
		              dtype.toString(), o.getClass().getName()));
		    }
		    long[] oShape = new long[numDimensions()];
		    fillShape(o, 0, oShape);
		    for (int i = 0; i < oShape.length; ++i) {
		      if (oShape[i] != shape().asArray()[i]) {
		        throw new IllegalArgumentException(
		            String.format(
		                "cannot copy Tensor with shape %s into object with shape %s",
		                Arrays.toString(shape().asArray()), Arrays.toString(oShape)));
		      }
		    }
		  }

	  long nativeHandle;
	  DataType dtype; // XXX could replace with: BaseType<T> type;
	  long[] shapeCopy = null;
	  

	  private static native long allocate(int dtype, long[] shape, long byteSize);

	  private static native long allocateScalarBytes(byte[] value);

	  private static native void delete(long handle);

	  private static native ByteBuffer buffer(long handle);

	  private static native int dtype(long handle);

	  private static native long[] shape(long handle);

	  private static native void setValue(long handle, Object value);

	  private static native float scalarFloat(long handle);

	  private static native double scalarDouble(long handle);

	  private static native int scalarInt(long handle);

	  private static native long scalarLong(long handle);

	  private static native boolean scalarBoolean(long handle);

	  private static native byte[] scalarBytes(long handle);

	  private static native void readNDArray(long handle, Object value);

}
