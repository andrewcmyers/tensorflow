package org.tensorflow.shapechecker.qual;

import static java.lang.annotation.ElementType.TYPE_PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;

import java.lang.annotation.Target;

import org.checkerframework.framework.qual.SubtypeOf;

/**
 * A shape about which nothing is known statically
 *
 */
@SubtypeOf({})
@Target({TYPE_USE, TYPE_PARAMETER})
public @interface anyshape {

}
