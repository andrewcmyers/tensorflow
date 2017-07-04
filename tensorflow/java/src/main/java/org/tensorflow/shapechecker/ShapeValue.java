package org.tensorflow.shapechecker;

import java.util.Set;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.type.TypeMirror;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.framework.flow.CFAbstractAnalysis;
import org.checkerframework.framework.flow.CFAbstractValue;

public class ShapeValue extends CFAbstractValue<ShapeValue> {

	public ShapeValue(CFAbstractAnalysis<ShapeValue, ?, ?> analysis, Set<AnnotationMirror> annotations,
	    TypeMirror underlyingType) {
		super(analysis, annotations, underlyingType);

	}
	
	@Override
	public ShapeValue mostSpecific(@Nullable ShapeValue other, @Nullable ShapeValue backup) {
		return super.mostSpecific(other, backup);
	}

}
