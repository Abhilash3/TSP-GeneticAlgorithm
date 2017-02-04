package project.genetic.vo.list.individual;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import project.genetic.vo.list.NoDuplicateList;
import project.genetic.vo.Cloneable;

public abstract class Individual<E extends Cloneable> implements List<E>, Cloneable, Comparable<Individual<E>> {

    protected Chromosome<E> list;

    protected double fitness = -1;
    private Integer hashcode;
    private String toString;

    public Individual(List<E> list) {
        this.list = new NoDuplicateList<E>(list);
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
        final Iterator<E> iterator = list.iterator();
        return new Iterator<E>() {
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public E next() {
                return iterator.next().doClone();
            }
        };
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
        return listIterator(list.listIterator());
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        return listIterator(list.listIterator(index));
    }

    private ListIterator<E> listIterator(final ListIterator<E> iterator) {
        return new ListIterator<E>() {
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public E next() {
                return iterator.next().doClone();
            }

            @Override
            public boolean hasPrevious() {
                return iterator.hasPrevious();
            }

            @Override
            public E previous() {
                return iterator.previous().doClone();
            }

            @Override
            public int nextIndex() {
                return iterator.nextIndex();
            }

            @Override
            public int previousIndex() {
                return iterator.previousIndex();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Remove not supported");
            }

            @Override
            public void set(E e) {
                throw new UnsupportedOperationException("Set not supported");
            }

            @Override
            public void add(E e) {
                throw new UnsupportedOperationException("Add not supported");
            }
        };
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("subList not supported");
    }

    @Override
    public final boolean equals(Object object) {
        if (object == null) {
            return false;
        }
        if (this == object) {
            return true;
        }
        if (!(object.getClass() == getClass())) {
            return false;
        }

        /*
         * Object has to be of Individual; verified above
         */
        @SuppressWarnings("rawtypes")
        Individual o = (Individual) object;
        return compareTo(o) == 0 && list.equals(o.list);
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
    public final int compareTo(Individual individual) {
        return Double.compare(getFitness(), individual.getFitness());
    }

    @Override
    public String toString() {
        if (toString == null) {
            toString = "[Individual: " + getFitness() + "]";
        }
        return toString;
    }

    public abstract double getFitness();

}
