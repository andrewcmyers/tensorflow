package org.tensorflow.shapechecker.qual;

import org.checkerframework.framework.qual.DefaultQualifierInHierarchy;
import org.checkerframework.framework.qual.ImplicitFor;
import org.checkerframework.framework.qual.SubtypeOf;
import org.tensorflow.Tensor;
import org.tensorflow.Output;

// A shape to be solved for
@ImplicitFor(typeNames = {Tensor.class, Output.class})
@DefaultQualifierInHierarchy
@SubtypeOf(scalar.class)
public @interface someshape {

}
