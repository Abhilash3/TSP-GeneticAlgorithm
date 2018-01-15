package project.genetic.vo.list;

import project.genetic.vo.ICloneable;

import java.util.ArrayList;
import java.util.List;

public class CloneableList<E extends ICloneable> extends ArrayList<E> implements ICloneableList<E> {

    public CloneableList() {
        super();
    }

    public CloneableList(int i) {
        super(i);
    }

    public CloneableList(List<E> list) {
        super(list);
    }

    @SuppressWarnings("unchecked")
    @Override
    public CloneableList<E> doClone() {
        CloneableList<E> clone = new CloneableList<>(size());
        for (E e : this) {
            clone.add(e.doClone());
        }
        return clone;
    }
}
