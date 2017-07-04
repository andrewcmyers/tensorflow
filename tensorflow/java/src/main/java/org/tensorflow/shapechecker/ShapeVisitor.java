package org.tensorflow.shapechecker;

import org.checkerframework.common.basetype.BaseTypeChecker;
import org.checkerframework.common.basetype.BaseTypeVisitor;

public class ShapeVisitor extends BaseTypeVisitor<ShapeAnnotatedTypeFactory> {	
	public ShapeVisitor(BaseTypeChecker checker) {
		super(checker);
	}
}
