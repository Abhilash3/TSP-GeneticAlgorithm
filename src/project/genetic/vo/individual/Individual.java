package project.genetic.vo.individual;

import project.genetic.vo.ICloneable;
import project.genetic.vo.individual.gene.IGene;
import project.genetic.vo.list.CloneableList;
import project.genetic.vo.list.ICloneableList;
import project.genetic.vo.list.decorator.ImmutableListDecorator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static java.lang.String.format;

public abstract class Individual<E extends IGene> implements Iterable<E>, ICloneable, Comparable<Individual<E>> {

    protected double fitness = -1;
    private Integer hashcode;
    private String toString;

    ICloneableList<E> chromosome;

    public Individual(ICloneableList<E> chromosome) {
        this.chromosome = new ImmutableListDecorator<E>(new CloneableList<E>(chromosome));
    }

    public int size() {
        return chromosome.size();
    }

    public E get(int index) {
        return chromosome.get(index);
    }

    public List<E> toList() {
        return new ArrayList<>(chromosome);
    }

    @Override
    public Iterator<E> iterator() {
        return chromosome.iterator();
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
        return compareTo(o) == 0 && chromosome.equals(o.chromosome);
    }

    @Override
    public int hashCode() {
        if (hashcode == null) {
            int result = 17;
            result = 31 * result + chromosome.hashCode();
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
