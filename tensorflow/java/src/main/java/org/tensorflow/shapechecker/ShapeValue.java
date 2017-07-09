package org.tensorflow.shapechecker;

import java.io.StringBufferInputStream;
import java.io.StringReader;
import java.util.Set;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.type.TypeMirror;

import org.checkerframework.checker.nullness.qual.Nullable;
import org.checkerframework.framework.flow.CFAbstractAnalysis;
import org.checkerframework.framework.flow.CFAbstractValue;
import org.checkerframework.javacutil.AnnotationUtils;
import org.tensorflow.shapechecker.qual.shape;

import java_cup.runtime.Symbol;

public class ShapeValue extends CFAbstractValue<ShapeValue> {
	
	Dimensions dimensions;
	

	public ShapeValue(CFAbstractAnalysis<ShapeValue, ?, ?> analysis, Set<AnnotationMirror> annotations,
	    TypeMirror underlyingType) {
		super(analysis, annotations, underlyingType);
		//System.err.println("XXX Creating a ShapeValue from " + annotations);
		if (annotations.size() > 1) {
			throw new Error("more than one annotation: " + annotations);
		}
		if (annotations.size() == 1) {
			AnnotationMirror am = annotations.iterator().next();
			if (AnnotationUtils.areSameByClass(am, shape.class)) {
				String s = AnnotationUtils.getElementValue(am, "value", String.class, false);
				dimensions = ShapeParser.parseDimensions(s);
			}
		
		}
	}
	
	@Override
	public ShapeValue mostSpecific(@Nullable ShapeValue other, @Nullable ShapeValue backup) {
		return super.mostSpecific(other, backup);
	}

}
