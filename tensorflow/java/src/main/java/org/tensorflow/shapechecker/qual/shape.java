package org.tensorflow.shapechecker.qual;

import static java.lang.annotation.ElementType.TYPE_PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.checkerframework.framework.qual.SubtypeOf;

/**
 * The shape of a tensor whose shape is specified symbolically.
 * {@literal @}shape("2,3") means a 2 x 3 tensor of rank 2.
 * {@literal @}shape("a,b") where a and b are integral variables means an a x b rank-2 tensor
 * {@literal @}shape("data") where data has an array type means a tensor with same dimensions as the array.
 * {@literal @}shape("t") where t is a tensor means a tensor with the same shape as t.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@SubtypeOf(anyshape.class)
@Target({TYPE_USE, TYPE_PARAMETER})
public @interface shape { String value(); }
