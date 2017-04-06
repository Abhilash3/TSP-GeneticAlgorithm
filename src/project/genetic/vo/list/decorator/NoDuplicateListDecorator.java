package project.genetic.vo.list.decorator;

import project.genetic.vo.Cloneable;
import project.genetic.vo.list.ICloneableList;

import java.util.Collection;
import java.util.Iterator;
import java.util.ListIterator;

public final class NoDuplicateListDecorator<E extends Cloneable> extends ListDecorator<E> {

    public NoDuplicateListDecorator(ICloneableList<E> list) {
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
                iterator.remove();
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

    @SuppressWarnings("unchecked")
    @Override
    public void add(int n, E e) {
        if (!contains(e)) {
            super.add(n, (E) e.doClone());
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean add(E e) {
        return !contains(e) && super.add((E) e.doClone());
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        int count = 0;
        for (E e : c) {
            if (add(e)) {
                count++;
            }
        }
        return count != 0;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        int count = 0;
        for (E e : c) {
            int prevSize = size();
            add(index + count, e);
            if (size() == prevSize + 1) {
                count++;
            }
        }
        return count != 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public E set(int index, E element) {
        return !contains(element) ? list.set(index, (E) element.doClone()) : null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public NoDuplicateListDecorator<E> doClone() {
        return new NoDuplicateListDecorator<>((ICloneableList<E>) list.doClone());
    }
}
