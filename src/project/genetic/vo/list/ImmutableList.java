package project.genetic.vo.list;

import project.genetic.vo.Cloneable;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class ImmutableList<E extends Cloneable> extends CloneableList<E> {

    public ImmutableList() {
        super();
    }

    public ImmutableList(int i) {
        super(i);
    }

    public ImmutableList(List<E> list) {
        super(list);
    }

    @Override
    public final Object[] toArray() {
        return cloneContents(super.toArray());
    }

    @Override
    public final <T> T[] toArray(T[] a) {
        return cloneContents(super.toArray(a));
    }

    @SuppressWarnings("unchecked")
    protected final <T> T[] cloneContents(T[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = ((Cloneable) arr[i]).doClone();
        }
        return arr;
    }

    @Override
    public final boolean addAll(Collection<? extends E> c) {
        throw new UnsupportedOperationException("AddAll not supported");
    }

    @Override
    public final boolean addAll(int index, Collection<? extends E> c) {
        throw new UnsupportedOperationException("AddAll not supported");
    }

    @Override
    public final E get(int index) {
        return list.get(index).doClone();
    }

    @Override
    public final boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException("RemoveAll not supported");
    }

    @Override
    public final boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException("RetainAll not supported");
    }

    @Override
    public final void clear() {
        throw new UnsupportedOperationException("Clear not supported");
    }

    @Override
    public final E set(int index, E element) {
        throw new UnsupportedOperationException("Set not supported");
    }

    @Override
    public final void add(int index, E element) {
        throw new UnsupportedOperationException("Add not supported");
    }

    @Override
    public final E remove(int index) {
        throw new UnsupportedOperationException("Remove not supported");
    }

    @Override
    public final boolean add(E e) {
        throw new UnsupportedOperationException("Add not supported");
    }

    @Override
    public final boolean remove(Object o) {
        throw new UnsupportedOperationException("Remove not supported");
    }

    @Override
    protected final Iterator<E> iterator(final Iterator<E> iterator) {
        return new Iterator<E>() {
            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public E next() {
                return iterator.next().doClone();
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException("Remove not supported");
            }
        };
    }

    @Override
    protected final ListIterator<E> listIterator(final ListIterator<E> iterator) {
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
}
