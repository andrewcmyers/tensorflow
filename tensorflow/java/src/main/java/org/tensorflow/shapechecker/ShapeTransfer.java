package org.tensorflow.shapechecker;

import org.checkerframework.framework.flow.CFAbstractAnalysis;
import org.checkerframework.framework.flow.CFAbstractTransfer;
import org.checkerframework.framework.flow.CFStore;
import org.checkerframework.framework.flow.CFTransfer;
import org.checkerframework.framework.flow.CFValue;

public class ShapeTransfer extends CFAbstractTransfer<ShapeValue, Store, ShapeTransfer> {

	public ShapeTransfer(CFAbstractAnalysis<ShapeValue, Store, ShapeTransfer> analysis) {
		super(analysis);
		// TODO Auto-generated constructor stub
	}



}
