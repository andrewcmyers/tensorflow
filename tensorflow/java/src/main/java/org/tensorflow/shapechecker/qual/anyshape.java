package org.tensorflow.shapechecker.qual;

import static java.lang.annotation.ElementType.TYPE_PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.checkerframework.framework.qual.SubtypeOf;

/**
 * A shape about which nothing is known statically. Root of the
 * qualifier hierarchy.
 */
@Retention(RetentionPolicy.RUNTIME)
@SubtypeOf({})
@Target({TYPE_USE, TYPE_PARAMETER})
public @interface anyshape {
}