package org.tensorflow.shapechecker;

import org.checkerframework.framework.flow.CFAbstractStore;

public class Store extends CFAbstractStore<ShapeValue, Store> {
	
	protected Store(CFAbstractStore<ShapeValue, Store> other) {
		super(other);
	}

}
