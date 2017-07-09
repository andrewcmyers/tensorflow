package org.tensorflow.shapechecker.qual;

import static java.lang.annotation.ElementType.TYPE_PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;

import java.lang.annotation.Target;

import org.checkerframework.framework.qual.DefaultFor;
import org.checkerframework.framework.qual.SubtypeOf;
import org.checkerframework.framework.qual.TypeUseLocation;

@SubtypeOf({noshape.class, scalar.class, shape.class, shapeUnknown.class})
@Target({TYPE_USE, TYPE_PARAMETER})
@DefaultFor(value = {TypeUseLocation.LOWER_BOUND})
public @interface shapeBottom {

}
