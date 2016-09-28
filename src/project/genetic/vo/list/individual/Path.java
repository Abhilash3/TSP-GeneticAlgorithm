package project.genetic.vo.list.individual;

import java.util.List;

import project.genetic.vo.coordinate.Coordinates;
import project.genetic.vo.coordinate.ICoordinate;

public final class Path extends Individual<ICoordinate> {
	
	public Path(List<ICoordinate> list) {
		super(list);
	}

	@Override
	protected double fitness() {
		double fitness = 0;
		for (int i = 1; i < list.size(); i++) {
			fitness += Coordinates.distance(list.get(i - 1), list.get(i));
		}
		fitness += Coordinates.distance(list.get(list.size() - 1), list.get(0));
		return 1 / fitness;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Path doClone() {
		return new Path((List<ICoordinate>) list.doClone());
	}

}
