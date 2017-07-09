package org.tensorflow.shapechecker;

import java.util.Collection;
import java.util.Set;

import javax.lang.model.element.AnnotationMirror;

import org.checkerframework.common.basetype.BaseTypeChecker;
import org.checkerframework.framework.type.GenericAnnotatedTypeFactory;
import org.checkerframework.framework.type.QualifierHierarchy;
import org.checkerframework.framework.util.GraphQualifierHierarchy;
import org.checkerframework.framework.util.MultiGraphQualifierHierarchy.MultiGraphFactory;
import org.checkerframework.javacutil.AnnotationUtils;
import org.tensorflow.shapechecker.qual.shapeBottom;
import org.tensorflow.shapechecker.qual.shape;
import org.tensorflow.shapechecker.qual.noshape;
import org.tensorflow.shapechecker.qual.scalar;
import org.tensorflow.shapechecker.qual.anyshape;
import org.tensorflow.shapechecker.qual.shapeUnknown;

import com.sun.javafx.geom.Shape;

public class ShapeAnnotatedTypeFactory extends GenericAnnotatedTypeFactory<ShapeValue,ShapeStore,ShapeTransfer,ShapeAnalysis> {
	
	public final AnnotationMirror SHAPE, SCALAR, NOSHAPE, ANYSHAPE, UNKNOWN;
	
	public ShapeAnnotatedTypeFactory(BaseTypeChecker checker) {
		super(checker);

		SHAPE = AnnotationUtils.fromClass(elements,  shape.class);
		ANYSHAPE = AnnotationUtils.fromClass(elements,  anyshape.class);
		NOSHAPE = AnnotationUtils.fromClass(elements,  noshape.class);
		SCALAR = AnnotationUtils.fromClass(elements,  scalar.class);
		UNKNOWN = AnnotationUtils.fromClass(elements,  shapeUnknown.class);
		
		super.postInit();

	}
	
	@Override
	public QualifierHierarchy createQualifierHierarchy(MultiGraphFactory factory) {
		return new ShapeQualifierHierarchy(factory, AnnotationUtils.fromClass(elements, shapeBottom.class));
	}
	
	class ShapeQualifierHierarchy extends GraphQualifierHierarchy {

		public ShapeQualifierHierarchy(MultiGraphFactory f,
				AnnotationMirror bottom) {
			super(f, bottom);
		}
		
		String shapeValue(AnnotationMirror a) {
			return AnnotationUtils.getElementValue(a, "value", String.class, false);
		}
		
		@Override public boolean isSubtype(AnnotationMirror subAnno,
				AnnotationMirror superAnno) {
			
			//System.err.println("XXX Testing " + subAnno + " <: " + superAnno);
			//System.err.println("Annotation types " + subAnno.getAnnotationType() + "," + SHAPE.getAnnotationType());
			if (AnnotationUtils.areSameIgnoringValues(subAnno, SHAPE)) {
				
				 if (superAnno.equals(ANYSHAPE)) return true;
				 if (superAnno.equals(NOSHAPE)) return false;
				 if (superAnno.equals(SCALAR)) return false;
				 if (superAnno.equals(UNKNOWN)) {
					 // TODO record constraint
					 return true;
				 }
				 if (AnnotationUtils.areSameIgnoringValues(superAnno, SHAPE)) {
					 String subshape = shapeValue(subAnno), supershape = shapeValue(superAnno);
					 if (subshape.equals(supershape)) return true; // hack: textually equal!
					 System.err.println("Comparing two shapes: " + subshape + " and " + supershape);
					 return true;
				 }
				 throw new Error("don't know how to compare " + subAnno + " and " + superAnno);	
			}
		
			return super.isSubtype(subAnno, superAnno);
		}

	}
}
