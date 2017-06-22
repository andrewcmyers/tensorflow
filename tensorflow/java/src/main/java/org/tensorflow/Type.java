package org.tensorflow;

/**
 * A Type<T> keeps track of the base data type of a tensor in a way that allows the Java type system
 * to use it for type checking. There is a separate Type<T> object for each of the possible data types,
 * corresponding to the cases of the enum DataType.
 * 
 * @param <T>
 */
public class Type<T> {
	private Type(T[] dummy_array, DataType dtype) {
		this.dtype = dtype;
		this.info = dummy_array;
	}
	private final T[] info; // must be length 1 containing default value of T
	private final DataType dtype; // must match T
	
	/** Get the default value of this base type (0 or null) */
	public T defaultValue() {
		return info[0];
	}
	
	/** Get the default value of this base type as a scalar tensor. */
	public Tensor<T> defaultScalar() {
		return Tensor.create(defaultValue(), this);
	}
	
	/** Convert to the equivalent DataType. */
	public DataType dataType() {
		return dtype;
	}
	
	// XXX Name these to match Java or TF conventions? Currently following TF.
	// XXX There is potential for confusion regardless of how it's done.
	// XXX Alternatively a new set of classes named Float, Int32, Int64, Uint8 etc. could be created inside
	// XXX tensorflow. We don't really need to use the Java classes, though it is convenient for defaultValue()
	// XXX and defaultScalar().
	public static final Type<Float> FLOAT = new Type<Float>(new Float[]{0f}, DataType.FLOAT);
	public static final Type<Integer> INT32 = new Type<Integer>(new Integer[]{0}, DataType.INT32);
	public static final Type<Long> INT64 = new Type<Long>(new Long[]{0L}, DataType.INT64);
	public static final Type<Double> DOUBLE = new Type<Double>(new Double[]{0d}, DataType.DOUBLE);
	public static final Type<Boolean> BOOL = new Type<Boolean>(new Boolean[]{false}, DataType.BOOL);
	public static final Type<String> STRING = new Type<String>(new String[]{null}, DataType.STRING);
	public static final Type<Byte> UINT8 = new Type<Byte>(new Byte[]{0}, DataType.UINT8);	
}
