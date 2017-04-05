package project.genetic.vo.individual;

import project.genetic.vo.Cloneable;
import project.genetic.vo.list.ICloneableList;

public abstract class OrderedIndividual<E extends Cloneable> extends Individual<E> {

    public OrderedIndividual(ICloneableList<E> list) {
        super(list);
    }

    @Override
    public double getFitness() {
        if (fitness == -1) {
            fitness = 0;
            for (int i = 1; i < size(); i++) {
                fitness += fitness(get(i - 1), get(i));
            }
            fitness += fitness(get(size() - 1), get(0));
            fitness = 1 / fitness;
        }
        return fitness;
    }

    protected abstract double fitness(E e1, E e2);
}
