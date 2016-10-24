package project.genetic.vo.list.individual;

import java.util.List;

import project.genetic.vo.coordinate.Coordinate;
import project.genetic.vo.coordinate.Coordinates;

public final class Path extends Individual<Coordinate> {

    public Path(List<Coordinate> list) {
        super(list);
    }

    @Override
    protected double fitness(Coordinate e1, Coordinate e2) {
        return Coordinates.distance(e1, e2);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Path doClone() {
        return new Path((List<Coordinate>) list.doClone());
    }

}
