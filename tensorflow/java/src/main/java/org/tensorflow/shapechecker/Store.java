package org.tensorflow.shapechecker;

import org.checkerframework.framework.flow.CFAbstractStore;

public class Store extends CFAbstractStore<ShapeValue, Store> {
	
	protected Store(CFAbstractStore<ShapeValue, Store> other) {
		super(other);
	}

	@Override
	public boolean hasDOToutput() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String toDOToutput() {
		// TODO Auto-generated method stub
		return null;
	}

}
