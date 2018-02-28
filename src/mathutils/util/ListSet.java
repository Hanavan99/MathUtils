package mathutils.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ListSet<T> implements Set<T> {

	private List<T> set;

	public ListSet() {
		set = new ArrayList<T>();
	}
	
	@SuppressWarnings("unchecked")
	public ListSet(List<T> set, boolean deepCopy) {
		if (deepCopy) {
			try {
				this.set = set.getClass().newInstance();
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
				throw new IllegalArgumentException();
			}
			for (T item : set) {
				if (!set.contains(item)) {
					set.add(item);
				}
			}
		} else {
			this.set = set;
		}
	}

	@Override
	public int size() {
		return set.size();
	}

	@Override
	public boolean isEmpty() {
		return set.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return set.contains(o);
	}

	@Override
	public Iterator<T> iterator() {
		return set.iterator();
	}

	@Override
	public Object[] toArray() {
		return set.toArray();
	}

	@Override
	public <U> U[] toArray(U[] a) {
		return set.toArray(a);
	}

	@Override
	public boolean add(T e) {
		if (set.contains(e)) {
			return false;
		} else {
			return set.add(e);
		}
	}

	@Override
	public boolean remove(Object o) {
		return set.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return set.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends T> c) {
		return set.addAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return set.retainAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return set.removeAll(c);
	}

	@Override
	public void clear() {
		set.clear();
	}

}
