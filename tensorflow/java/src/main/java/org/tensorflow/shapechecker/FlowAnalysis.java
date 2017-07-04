package org.tensorflow.shapechecker;

import java.util.List;
import java.util.Set;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.common.basetype.BaseTypeChecker;
import org.checkerframework.framework.flow.CFAbstractAnalysis;
import org.checkerframework.framework.type.GenericAnnotatedTypeFactory;
import org.checkerframework.javacutil.Pair;

public class FlowAnalysis extends CFAbstractAnalysis<ShapeValue, Store, ShapeTransfer> {

	public FlowAnalysis(BaseTypeChecker checker,
	    GenericAnnotatedTypeFactory<ShapeValue, Store, ShapeTransfer, ? extends CFAbstractAnalysis<ShapeValue, Store, ShapeTransfer>> factory,
	    List<Pair<VariableElement, ShapeValue>> fieldValues) {
		super(checker, factory, fieldValues);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Store createEmptyStore(boolean sequentialSemantics) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Store createCopiedStore(Store s) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public @Nullable ShapeValue createAbstractValue(Set<AnnotationMirror> annotations, TypeMirror underlyingType) {
		// TODO Auto-generated method stub
		System.out.println("asked to create abstract value from " + annotations + " on " + underlyingType);
		return null;
	}

}
