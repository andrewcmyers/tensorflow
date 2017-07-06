package org.tensorflow.shapechecker.qual;

import static java.lang.annotation.ElementType.TYPE_PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;

import java.lang.annotation.Target;

import org.checkerframework.framework.qual.SubtypeOf;

@SubtypeOf(anyshape.class)
@Target({TYPE_USE, TYPE_PARAMETER})
public @interface shape { String value(); }
