package mathutils.core;

import java.util.Map;

import mathutils.util.UnhashMap;

public class InputList<T, E> {

	private Map<Input<T, E>, E> inputs = new UnhashMap<Input<T, E>, E>();
	
	public void add(String name, E value) {
		inputs.put(new Input<T, E>(name), value);
	}
	
	public boolean contains(String name) {
		return inputs.containsKey(new Input<T, E>(name));
	}
	
}
