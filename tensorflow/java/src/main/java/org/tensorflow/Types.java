// GENERATED FILE. Edits to this file will be lost -- edit the .tmpl file instead.
package org.tensorflow;

import java.util.HashMap;
import java.util.Map;

/**
 * A Type<T> keeps track of the base data type of a tensor in a way that allows the Java type system
 * to use it for type checking. There is a separate Type<T> object for each of the possible data types,
 * corresponding to the cases of the enum DataType.
 * 
 * @param <T>
 */
public class Types<T> {

	private static final Map<Class<?>, Integer> typeCodes = new HashMap<>();
	/** Convert to the equivalent DataType. */

	public static DataType dataType(Class<?> c) {
		Integer code = typeCodes.get(c);
		if (code == null) throw new IllegalArgumentException("Class " + c + " is not a Tensorflow type.");
		return DataType.fromC(code.intValue());
	}
	
	private static final Map<Class<?>, Object> scalars = new HashMap<>();
	
	public static Object defaultScalar(Class<?> c) {
		return scalars.get(c);
	}
	
	/**
	 * A marker interface for classes representing Tensorflow types.
	 */
	interface TFType {}
	
	// The following classes represent Tensorflow types when used as type parameters to
	// types such as Tensor and Output.
  // 32-bit single precision floating point
  public static class TFFloat implements TFType {}
  public static final Class<TFFloat> FLOAT = TFFloat.class;
  { typeCodes.put(FLOAT, 1); }
  { scalars.put(FLOAT, 0f); }

  // 64-bit double precision floating point
  public static class TFDouble implements TFType {}
  public static final Class<TFDouble> DOUBLE = TFDouble.class;
  { typeCodes.put(DOUBLE, 2); }
  { scalars.put(DOUBLE, 0.0); }

  // 32-bit signed integer
  public static class TFInt32 implements TFType {}
  public static final Class<TFInt32> INT32 = TFInt32.class;
  { typeCodes.put(INT32, 3); }
  { scalars.put(INT32, 0); }

  // 8-bit unsigned integer
  public static class TFUInt8 implements TFType {}
  public static final Class<TFUInt8> UINT8 = TFUInt8.class;
  { typeCodes.put(UINT8, 4); }

  // 16-bit signed integer
  public static class TFInt16 implements TFType {}
  public static final Class<TFInt16> INT16 = TFInt16.class;
  { typeCodes.put(INT16, 5); }
  { scalars.put(INT16, (short)0); }

  // 8-bit signed integer
  public static class TFInt8 implements TFType {}
  public static final Class<TFInt8> INT8 = TFInt8.class;
  { typeCodes.put(INT8, 6); }
  { scalars.put(INT8, (byte)0); }

  // a sequence of bytes
  public static class TFString implements TFType {}
  public static final Class<TFString> STRING = TFString.class;
  { typeCodes.put(STRING, 7); }

  // single-precision complex
  public static class TFComplex64 implements TFType {}
  public static final Class<TFComplex64> COMPLEX64 = TFComplex64.class;
  { typeCodes.put(COMPLEX64, 8); }

  // 64-bit signed integer
  public static class TFInt64 implements TFType {}
  public static final Class<TFInt64> INT64 = TFInt64.class;
  { typeCodes.put(INT64, 9); }
  { scalars.put(INT64, 0L); }

  // Boolean
  public static class TFBool implements TFType {}
  public static final Class<TFBool> BOOL = TFBool.class;
  { typeCodes.put(BOOL, 10); }
  { scalars.put(BOOL, false); }

  // quantized int8
  public static class TFQInt8 implements TFType {}
  public static final Class<TFQInt8> QINT8 = TFQInt8.class;
  { typeCodes.put(QINT8, 11); }

  // quantized uint8
  public static class TFQUInt8 implements TFType {}
  public static final Class<TFQUInt8> QUINT8 = TFQUInt8.class;
  { typeCodes.put(QUINT8, 12); }

  // quantized int32
  public static class TFQInt32 implements TFType {}
  public static final Class<TFQInt32> QINT32 = TFQInt32.class;
  { typeCodes.put(QINT32, 13); }

  // float32 truncated to 16 bits. Only for cast ops.
  public static class TFBFloat16 implements TFType {}
  public static final Class<TFBFloat16> BFLOAT16 = TFBFloat16.class;
  { typeCodes.put(BFLOAT16, 14); }

  // quantized int16
  public static class TFQInt16 implements TFType {}
  public static final Class<TFQInt16> QINT16 = TFQInt16.class;
  { typeCodes.put(QINT16, 15); }

  // quantized uint16
  public static class TFQUInt16 implements TFType {}
  public static final Class<TFQUInt16> QUINT16 = TFQUInt16.class;
  { typeCodes.put(QUINT16, 16); }

  // 16-bit unsigned integer
  public static class TFUInt16 implements TFType {}
  public static final Class<TFUInt16> UINT16 = TFUInt16.class;
  { typeCodes.put(UINT16, 17); }

  // double-precision complex
  public static class TFComplex128 implements TFType {}
  public static final Class<TFComplex128> COMPLEX128 = TFComplex128.class;
  { typeCodes.put(COMPLEX128, 18); }

  public static class TFHalf implements TFType {}
  public static final Class<TFHalf> HALF = TFHalf.class;
  { typeCodes.put(HALF, 19); }

  public static class TFResource implements TFType {}
  public static final Class<TFResource> RESOURCE = TFResource.class;
  { typeCodes.put(RESOURCE, 20); }


}
