package org.tensorflow.shapechecker;

import org.checkerframework.framework.flow.CFAbstractStore;

public class ShapeStore extends CFAbstractStore<ShapeValue, ShapeStore> {
	
	protected ShapeStore(ShapeAnalysis analysis, boolean sequentialSemantics) {
		super(analysis, sequentialSemantics);
//		System.err.println("XXX created a ShapeStore");
	}
	
	protected ShapeStore(ShapeStore other) {
		super(other);
//		System.err.println("XXX copied a ShapeStore");
	}

}
