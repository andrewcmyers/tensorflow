package org.tensorflow.shapechecker.qual;

import static java.lang.annotation.ElementType.TYPE_PARAMETER;
import static java.lang.annotation.ElementType.TYPE_USE;

import java.lang.annotation.Target;

import org.checkerframework.framework.qual.DefaultQualifierInHierarchy;
import org.checkerframework.framework.qual.ImplicitFor;
import org.checkerframework.framework.qual.SubtypeOf;
import org.tensorflow.Tensor;
import org.tensorflow.Output;

// An unknown but actual shape (to be solved for?)
@ImplicitFor(typeNames = {Tensor.class, Output.class})
@SubtypeOf(shape.class)
@Target({TYPE_USE, TYPE_PARAMETER})
public @interface shapeUnknown {

}
