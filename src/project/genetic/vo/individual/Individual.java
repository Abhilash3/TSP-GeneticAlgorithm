package project.genetic.vo.individual;

import project.genetic.vo.list.CloneableList;
import project.genetic.vo.list.ICloneableList;
import project.genetic.vo.Cloneable;
import project.genetic.vo.list.decorator.ImmutableListDecorator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.lang.String.format;

public abstract class Individual<E extends Cloneable> implements Iterable<E>, Cloneable, Comparable<Individual<E>> {

    double fitness = -1;
    private Integer hashcode;
    private String toString;

    protected ICloneableList<E> list;

    public Individual(ICloneableList<E> list) {
        this.list = new ImmutableListDecorator<>(new CloneableList<>(list));
    }

    public int size() {
        return list.size();
    }

    public E get(int index) {
        return list.get(index);
    }

    public List<E> toList() {
        return new ArrayList<>(list);
    }

    @Override
    public Iterator<E> iterator() {
        return list.iterator();
    }

    @Override
    public final boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        if (this == object) {
            return true;
        }

        @SuppressWarnings("unchecked")
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
        if (toString == null) toString = format("[Individual: %s]", getFitness());
        return toString;
    }

    public abstract double getFitness();

}
