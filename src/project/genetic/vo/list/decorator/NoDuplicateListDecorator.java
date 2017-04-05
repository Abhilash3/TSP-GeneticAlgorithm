package project.genetic.vo.list.decorator;

import project.genetic.vo.Cloneable;
import project.genetic.vo.list.ICloneableList;

import java.util.Collection;

public class NoDuplicateListDecorator<E extends Cloneable> extends ListDecorator<E> {

    public NoDuplicateListDecorator(ICloneableList<E> list) {
        super(list);
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
    public NoDuplicateListDecorator<E> doClone() {
        return new NoDuplicateListDecorator<>((ICloneableList<E>) list.doClone());
    }
}
