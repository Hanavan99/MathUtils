package mathutils.core;

public class Input<T, E> extends Expression<T, E> {

	private final String name;
	
	public Input(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public T evaluate(InputList<T, E> inputs) {
		return null;
	}

	@Override
	public boolean equals(Object other) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String toString(InputList<T, E> inputs) {
		// TODO Auto-generated method stub
		return null;
	}

}
