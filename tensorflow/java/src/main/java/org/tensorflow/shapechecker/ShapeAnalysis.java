package org.tensorflow.shapechecker;

import java.util.List;
import java.util.Set;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.common.basetype.BaseTypeChecker;
import org.checkerframework.framework.flow.CFAbstractAnalysis;
import org.checkerframework.javacutil.Pair;

public class ShapeAnalysis extends CFAbstractAnalysis<ShapeValue, ShapeStore, ShapeTransfer> {

	public ShapeAnalysis(BaseTypeChecker checker,
	    ShapeAnnotatedTypeFactory factory,
	    List<Pair<VariableElement, ShapeValue>> fieldValues) {
		super(checker, factory, fieldValues);
		// System.err.println("XXX Created ShapeAnalysis ");
	}

	@Override
	public ShapeStore createEmptyStore(boolean sequentialSemantics) {
		return new ShapeStore(this, sequentialSemantics);
	}

	@Override
	public ShapeStore createCopiedStore(ShapeStore s) {
		return new ShapeStore(s);
	}

	@Override
	public @Nullable ShapeValue createAbstractValue(Set<AnnotationMirror> annotations, TypeMirror underlyingType) {
//		System.err.println("Created abstract value from " + underlyingType + " + " + annotations);
		return new ShapeValue(this, annotations, underlyingType);
	}

}
