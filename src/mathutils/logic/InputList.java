package mathutils.logic;

import java.util.ArrayList;

public class InputList {

	private ArrayList<Input> inputs = new ArrayList<Input>();
	
	public void add(char c, boolean v) {
		inputs.add(new Input(c));
	}
	
	
	
}
