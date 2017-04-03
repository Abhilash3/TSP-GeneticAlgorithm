package project.genetic.vo.list;

import java.util.Collection;
import java.util.List;

import project.genetic.vo.Cloneable;

public class NoDuplicateList<E extends Cloneable> extends CloneableList<E> {

    public NoDuplicateList() {
        super();
    }

    public NoDuplicateList(int i) {
        super(i);
    }

    public NoDuplicateList(List<E> list) {
        this (list == null ? 0 : list.size());
        if (list != null) {
            addAll(list);
        }
    }

    @Override
    public void add(int n, E e) {
        if (!contains(e)) {
            super.add(n, e);
        }
    }

    @Override
    public boolean add(E e) {
        return !contains(e) && super.add(e);
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

    @Override
    public E set(int index, E element) {
        return !contains(element) ? list.set(index, element) : null;
    }

    @SuppressWarnings("unchecked")
    @Override
    public NoDuplicateList<E> doClone() {
        return new NoDuplicateList<>(super.doClone());
    }

}
