package project.genetic.vo.list.individual;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import project.genetic.vo.list.MagicList;
import project.genetic.vo.Cloneable;

public abstract class Individual<E extends Cloneable> implements List<E>, Cloneable, Comparable<Individual<E>> {

	protected Chromosome<E> list;
	
	private double fitness = -1;
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
		throw new UnsupportedOperationException("toArray not supported");
	}

	@Override
	public <T> T[] toArray(T[] a) {
		throw new UnsupportedOperationException("toArray not supported");
	}

	@Override
	public boolean add(E e) {
		throw new UnsupportedOperationException("Add not supported");
	}

	@Override
	public boolean remove(Object o) {
		throw new UnsupportedOperationException("Remove not supported");
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return list.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		throw new UnsupportedOperationException("AddAll not supported");
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		throw new UnsupportedOperationException("AddAll not supported");
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException("RemoveAll not supported");
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException("RetainAll not supported");
	}

	@Override
	public void clear() {
		throw new UnsupportedOperationException("Clear not supported");
	}

	@Override
	public E get(int index) {
		return list.get(index).doClone();
	}

	@Override
	public E set(int index, E element) {
		throw new UnsupportedOperationException("Set not supported");
	}

	@Override
	public void add(int index, E element) {
		throw new UnsupportedOperationException("Add not supported");
	}

	@Override
	public E remove(int index) {
		throw new UnsupportedOperationException("Remove not supported");
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
		throw new UnsupportedOperationException("subList not supported");
	}

	@Override
	public boolean equals(Object object) {
		if (object == null)
			return false;
		if (this == object)
			return true;
		if (!(object instanceof Individual<?>))
			return false;

		/**
		 * Object has to be of Individual; verified above
		 */
		@SuppressWarnings("rawtypes")
		Individual o = (Individual) object;
		if (compareTo(o) == 0) {
			return list.equals(o.list);
		}
		return false;
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
	public int compareTo(@SuppressWarnings("rawtypes") Individual individual) {
        return Double.compare(getFitness(), individual.getFitness());
    }

	@Override
	public String toString() {
		if (toString == null) {
			toString = "[Individual: " + getFitness() + "]";
		}
		return toString;
	}

	public double getFitness() {
		if (fitness == -1) {
			fitness = fitness();
		}
		return fitness;
	}

	protected abstract double fitness();

}
