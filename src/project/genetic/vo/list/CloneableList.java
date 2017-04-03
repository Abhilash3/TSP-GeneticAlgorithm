package project.genetic.vo.list;

import project.genetic.vo.Cloneable;

import java.util.*;

public class CloneableList<E extends Cloneable> implements ICloneableList<E> {

    protected List<E> list;

    public CloneableList() {
        this.list = new ArrayList<>();
    }

    public CloneableList(int i) {
        this.list = new ArrayList<>(i);
    }

    public CloneableList(List<E> list) {
        this.list = new ArrayList<>(list);
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
    public final Iterator<E> iterator() {
        return iterator(list.iterator());
    }

    protected Iterator<E> iterator(final Iterator<E> iterator) {
        return new Iterator<E>() {
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public E next() {
                return iterator.next();
            }

            @Override
            public void remove() {
                iterator.remove();
            }
        };
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
        return list.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return list.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return list.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return list.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return list.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return list.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return list.retainAll(c);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public E get(int index) {
        return list.get(index);
    }

    @Override
    public E set(int index, E element) {
        return list.set(index, element);
    }

    @Override
    public void add(int index, E element) {
        list.add(index, element);
    }

    @Override
    public E remove(int index) {
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
    public final ListIterator<E> listIterator() {
        return listIterator(list.listIterator());
    }

    @Override
    public final ListIterator<E> listIterator(int index) {
        return listIterator(list.listIterator(index));
    }

    protected ListIterator<E> listIterator(final ListIterator<E> iterator) {
        return new ListIterator<E>() {
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public E next() {
                return iterator.next();
            }

            @Override
            public boolean hasPrevious() {
                return iterator.hasPrevious();
            }

            @Override
            public E previous() {
                return iterator.previous();
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
                iterator.remove();
            }

            @Override
            public void set(E e) {
                iterator.set(e);
            }

            @Override
            public void add(E e) {
                iterator.add(e);
            }
        };
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return list.subList(fromIndex, toIndex);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object object) {
        return object != null && (this == object || object.getClass() == getClass()
                && list.equals(((CloneableList<E>) object).list));
    }

    @SuppressWarnings("unchecked")
    @Override
    public CloneableList<E> doClone() {
        List<E> clone = new ArrayList<>(size());
        for (E e : list) {
            clone.add(e.doClone());
        }
        return new CloneableList<>(clone);
    }
}
