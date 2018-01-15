package project.genetic.vo.individual;

import project.genetic.util.Coordinates;
import project.genetic.vo.coordinate.Coordinate;
import project.genetic.vo.list.CloneableList;
import project.genetic.vo.list.ICloneableList;

public final class Route extends Individual<Coordinate> {

    public Route(ICloneableList<Coordinate> list) {
        super(list);
    }

    public double getFitness() {
        if (fitness == -1) {
            fitness = 0;
            for (int i = 1; i < size(); i++) {
                fitness += get(i - 1).value(get(i));
            }
            fitness += get(size() - 1).value(get(0));
            fitness = 1 / fitness;
        }
        return fitness;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Route doClone() {
        return new Route(chromosome.doClone());
    }
}
