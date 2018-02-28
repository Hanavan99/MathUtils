package mathutils.util;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Stores key/value pairs using {@code ArrayList}s. This map does not use
 * {@code hashCode()} to compare objects, only {@code equals()}.
 * 
 * @author Hanavan Kuhn
 *
 * @param <K>
 *            The type of key used to identify values
 * @param <V>
 *            The type of value being stored
 */
public class UnhashMap<K, V> implements Map<K, V> {

	private ArrayList<K> keys = new ArrayList<K>();
	private ArrayList<V> values = new ArrayList<V>();

	public V put(K key, V value) {
		if (keys.contains(key)) {
			int index = keys.indexOf(key);
			V old = values.get(index);
			values.remove(index);
			values.add(index, value);
			return old;
		} else {
			keys.add(key);
			values.add(value);
			return null;
		}

	}

	public V get(Object key) {
		for (int i = 0; i < keys.size(); i++) {
			if (keys.get(i) != null && keys.get(i).equals(key)) {
				return values.get(i);
			}
		}
		return null;
	}

	@Override
	public int size() {
		return keys.size();
	}

	@Override
	public boolean isEmpty() {
		return keys.isEmpty();
	}

	@Override
	public boolean containsKey(Object key) {
		return keys.contains(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return values.contains(value);
	}

	@Override
	public V remove(Object key) {
		if (keys.contains(key)) {
			int index = keys.indexOf(key);
			V val = values.get(index);
			keys.remove(index);
			values.remove(index);
			return val;
		} else {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void putAll(Map<? extends K, ? extends V> m) {
		Iterator<?> itr = m.entrySet().iterator();
		while (itr.hasNext()) {
			Entry<?, ?> entry = (Entry<?, ?>) itr.next();
			keys.add((K) entry.getKey());
			values.add((V) entry.getValue());
		}
	}

	@Override
	public void clear() {
		keys.clear();
		values.clear();
	}

	@Override
	public Set<K> keySet() {
		return new ListSet<K>(keys, false);
	}

	@Override
	public Collection<V> values() {
		return values;
	}

	@Override
	public Set<Entry<K, V>> entrySet() {
		Set<Entry<K, V>> entries = new ListSet<Entry<K, V>>();
		for (K key : keys) {
			entries.add(new SimpleEntry<K, V>(key, values.get(keys.indexOf(key))));
		}
		return entries;
	}

}
