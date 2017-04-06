package project.genetic.vo.list.decorator;

import project.genetic.vo.Cloneable;
import project.genetic.vo.list.ICloneableList;

import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;

public final class ImmutableListDecorator<E extends Cloneable> extends ListDecorator<E> {

    public ImmutableListDecorator(ICloneableList<E> list) {
        super(list);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected <T> T[] processArray(T[] arr) {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = ((Cloneable) arr[i]).doClone();
        }
        return arr;
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
    public E get(int index) {
        return list.get(index).doClone();
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
    public boolean add(E e) {
        throw new UnsupportedOperationException("Add not supported");
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException("Remove not supported");
    }

    @Override
    protected Iterator<E> iterator(final Iterator<E> iterator) {
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
    protected ListIterator<E> listIterator(final ListIterator<E> iterator) {
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

    @SuppressWarnings("unchecked")
    @Override
    public ImmutableListDecorator<E> doClone() {
        return new ImmutableListDecorator<>((ICloneableList<E>) list.doClone());
    }
}
