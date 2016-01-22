package project.genetic.vo.list.individual;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import project.genetic.vo.list.MagicList;

public abstract class Individual<E> implements List<E> {

	protected Chromosome<E> list;
	protected double fitness = -1;

	private Integer hashcode;
	private String toString;

	public Individual(List<E> list) {
		super();
		this.list = new MagicList<E>(list);
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
		throw new UnsupportedOperationException("Add not supproted");
	}

	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException("Remove not supproted");
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return list.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		throw new UnsupportedOperationException("AddAll not supproted");
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		throw new UnsupportedOperationException("AddAll not supproted");
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException("RemoveAll not supproted");
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException("RetainAll not supproted");
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException("Clear not supproted");
	}

	@Override
	public E get(int index) {
		return list.get(index);
	}

	@Override
	public E set(int index, E element) {
		throw new UnsupportedOperationException("Set not supproted");
	}

	@Override
	public void add(int index, E element) {
		throw new UnsupportedOperationException("Add not supproted");
	}

	@Override
	public E remove(int index) {
		throw new UnsupportedOperationException("Remove not supproted");
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

	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (this == object)
			return true;
		if (!(object instanceof Individual))
			return false;

		/**
		 * Object has to be of Individual; verified above
		 */
		@SuppressWarnings("unchecked")
		Individual<E> o = (Individual<E>) object;
		return Double.compare(getFitness(), o.getFitness()) == 0;
	}

	@Override
	public int hashCode() {
		if (hashcode == null) {
			int result = 17;
			result = 31 * result + list.hashCode();
			result = (int) (31 * result + fitness);
			hashcode = result;
		}
		return hashcode;
	}

	@Override
	public String toString() {
		if (toString == null) {
			toString = new StringBuilder().append("[Individual: ")
					.append(getFitness()).append("]").toString();
		}
		return toString;
	}

	public double getFitness() {
		if (fitness < 0) {
			fitness = fitness();
		}
		return fitness;
	}

	protected abstract double fitness();

}
