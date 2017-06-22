package org.tensorflow;

/**
 * A BaseType<T> keeps track of the base data type of a tensor in a way that allows the Java type system
 * to use it for type checking. There is a separate BaseType<T> object for each of the possible data types,
 * corresponding to the cases of the enum DataType.
 * 
 * @param <T>
 */
public class BaseType<T> {
	private BaseType(T[] dummy_array, DataType dtype) {
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
	// XXX tensorflow. We don't really need to use the Java classes.
	public static final BaseType<Float> FLOAT = new BaseType<Float>(new Float[]{0f}, DataType.FLOAT);
	public static final BaseType<Integer> INT32 = new BaseType<Integer>(new Integer[]{0}, DataType.INT32);
	public static final BaseType<Long> INT64 = new BaseType<Long>(new Long[]{0L}, DataType.INT64);
	public static final BaseType<Double> DOUBLE = new BaseType<Double>(new Double[]{0d}, DataType.DOUBLE);
	public static final BaseType<Boolean> BOOL = new BaseType<Boolean>(new Boolean[]{false}, DataType.BOOL);
	public static final BaseType<String> STRING = new BaseType<String>(new String[]{null}, DataType.STRING);
	public static final BaseType<Byte> UINT8 = new BaseType<Byte>(new Byte[]{0}, DataType.UINT8);	
}
