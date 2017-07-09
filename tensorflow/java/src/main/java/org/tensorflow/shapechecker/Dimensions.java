package org.tensorflow.shapechecker;

import java.util.ArrayList;
import java.util.List;

public class Dimensions {
	final List<DimOrId> dims; // null indicates scalar

	Dimensions(List<DimOrId> dims) {
		this.dims = new ArrayList<>(dims);
	}

	Dimensions() {
		dims = null;
	}
	
	public String toString() {
		return dims.toString();
	}
}
