package project.genetic.vo.list.individual;

import project.genetic.vo.Cloneable;

import java.util.List;

public abstract class OrderedIndividual<E extends Cloneable> extends Individual<E> {

    public OrderedIndividual(List<E> list) {
        super(list);
    }

    @Override
    public double getFitness() {
        if (fitness == -1) {
            fitness = 0;
            for (int i = 1; i < list.size(); i++) {
                fitness += fitness(list.get(i - 1), list.get(i));
            }
            fitness += fitness(list.get(list.size() - 1), list.get(0));
            fitness = 1 / fitness;
        }
        return fitness;
    }

    protected abstract double fitness(E e1, E e2);
}
