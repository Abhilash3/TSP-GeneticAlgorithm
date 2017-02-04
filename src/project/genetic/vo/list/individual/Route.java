package project.genetic.vo.list.individual;

import java.util.List;

import project.genetic.vo.coordinate.Coordinate;
import project.genetic.vo.coordinate.Coordinates;

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
        return new Route(list.doClone());
    }

}
