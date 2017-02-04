package project.genetic.vo.list;

import project.genetic.vo.Cloneable;
import project.genetic.vo.list.individual.Chromosome;

import java.util.ArrayList;
import java.util.List;

public class CloneableList<E extends Cloneable> extends ArrayList<E> implements Chromosome<E> {

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
        CloneableList<E> clone = new CloneableList<E>();
        for (int i = 0; i < size(); i++) {
            clone.add(get(i).doClone());
        }
        return clone;
    }

    @Override
    public boolean equals(Object object) {
        return object != null && (this == object || object.getClass() == getClass() && super.equals(object));
    }
}
