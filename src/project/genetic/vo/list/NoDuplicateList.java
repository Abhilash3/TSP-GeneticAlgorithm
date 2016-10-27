package project.genetic.vo.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import project.genetic.vo.Cloneable;
import project.genetic.vo.list.individual.Chromosome;

public final class NoDuplicateList<E extends Cloneable> extends ArrayList<E> implements Chromosome<E> {

    /**
     *
     */
    private static final long serialVersionUID = 2587729160448267835L;

    public NoDuplicateList() {
        super();
    }

    public NoDuplicateList(int i) {
        super(i);
    }

    public NoDuplicateList(List<E> list) {
        this(list.size());
        addAll(list);
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
            if (prevSize + 1 == size()) {
                count++;
            }
        }
        return count != 0;
    }

    @SuppressWarnings("unchecked")
    @Override
    public NoDuplicateList<E> doClone() {
        NoDuplicateList<E> clone = new NoDuplicateList<E>();
        for (int i = 0; i < size(); i++) {
            clone.add((E) get(i).doClone());
        }
        return clone;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof NoDuplicateList<?>)) {
            return false;
        }

        /**
         * Object has to be of NoDuplicateList; verified above
         */
        @SuppressWarnings("rawtypes")
        NoDuplicateList o = (NoDuplicateList) object;
        if (size() != o.size()) {
            return false;
        }
        for (int i = 0; i < size(); i++) {
            if (!get(i).equals(o.get(i))) {
                return false;
            }
        }
        return true;
    }

}
