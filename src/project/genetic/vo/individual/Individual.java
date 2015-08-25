package project.genetic.vo.individual;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class Individual<E> implements List<E> {
	
	protected Chromosome<E> list;
	protected double fitness;
	protected boolean isDirty;
	
	public Individual() {
		list = new MagicList<E>();
		fitness = 0;
		isDirty = false;
	}
	
	public Individual(List<E> list) {
		super();
		this.list.addAll(list);
	}

	@Override
	public int size() {
		return list.size();
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return list.contains(o);
	}

	@Override
	public Iterator<E> iterator() {
		return list.iterator();
	}

	@Override
	public Object[] toArray() {
		return list.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return list.toArray(a);
	}

	@Override
	public boolean add(E e) {
		if (list.contains(e))
			return false;
		isDirty = true;
		return list.add(e);
	}

	@Override
	public boolean remove(Object o) {
		if (list.contains(o))
			return false;
		isDirty = true;
		return list.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return list.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		if (list.containsAll(c))
			return false;
		isDirty = true;
		return list.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		if (list.containsAll(c))
			return false;
		isDirty = true;
		return list.addAll(index, c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		isDirty = true;
		return list.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		isDirty = true;
		return list.retainAll(c);
	}

	@Override
	public void clear() {
		isDirty = false;
		fitness = 0;
		list.clear();
	}

	@Override
	public E get(int index) {
		return list.get(index);
	}

	@Override
	public E set(int index, E element) {
		isDirty = true;
		return list.set(index, element);
	}

	@Override
	public void add(int index, E element) {
		isDirty = true;
		list.add(index, element);
	}

	@Override
	public E remove(int index) {
		isDirty = true;
		return list.remove(index);
	}

	@Override
	public int indexOf(Object o) {
		return list.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return list.lastIndexOf(o);
	}

	@Override
	public ListIterator<E> listIterator() {
		return list.listIterator();
	}

	@Override
	public ListIterator<E> listIterator(int index) {
		return list.listIterator(index);
	}

	@Override
	public List<E> subList(int fromIndex, int toIndex) {
		return list.subList(fromIndex, toIndex);
	}
	
	public double getFitness() {
		throw new UnsupportedOperationException();
	}

}
