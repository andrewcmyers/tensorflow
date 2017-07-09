package org.tensorflow.shapechecker;
import org.checkerframework.common.basetype.BaseTypeChecker;
import org.checkerframework.framework.qual.RelevantJavaTypes;
import org.tensorflow.Output;
import org.tensorflow.Tensor;

@RelevantJavaTypes({Tensor.class, Output.class})
public class ShapeChecker extends BaseTypeChecker {
}
