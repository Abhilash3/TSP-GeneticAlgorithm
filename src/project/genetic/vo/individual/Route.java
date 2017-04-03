package project.genetic.vo.individual;

import java.util.List;

import project.genetic.vo.coordinate.Coordinate;
import project.genetic.util.Coordinates;

public final class Route extends OrderedIndividual<Coordinate> {

    public Route(List<Coordinate> list) {
        super(list);
    }

    @Override
    protected double fitness(Coordinate e1, Coordinate e2) {
        return Coordinates.distance(e1, e2);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Route doClone() {
        return new Route(super.doClone());
    }

}
