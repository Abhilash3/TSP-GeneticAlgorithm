package project.genetic.vo.individual;

import java.util.List;

import project.genetic.vo.list.CloneableList;
import project.genetic.vo.list.ImmutableList;
import project.genetic.vo.list.NoDuplicateList;
import project.genetic.vo.Cloneable;

import static java.lang.String.format;

public abstract class Individual<E extends Cloneable> extends CloneableList<E> implements Comparable<Individual<E>> {

    double fitness = -1;
    private Integer hashcode;
    private String toString;

    public Individual(List<E> list) {
        super(new NoDuplicateList<>(new ImmutableList<>(list)));
    }

    @Override
    public Object[] toArray() {
        throw new UnsupportedOperationException("toArray not supported");
    }

    @Override
    public <T> T[] toArray(T[] a) {
        throw new UnsupportedOperationException("toArray not supported");
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException("subList not supported");
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
        return compareTo(o) == 0 && super.equals(o);
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
