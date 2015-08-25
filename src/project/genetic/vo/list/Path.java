package project.genetic.vo.list;

import project.genetic.vo.coordinate.ICoordinate;
import project.genetic.vo.individual.Individual;

public class Path extends Individual<ICoordinate> {

	@Override
	public double getFitness() {
		if (isDirty) {
			fitness = 0;
			for (int i = 1; i < list.size(); i++) {
				fitness += list.get(i - 1).distanceFrom(list.get(i));
			}
			fitness += list.get(list.size() - 1).distanceFrom(list.get(0));
			isDirty = false;
		}
		return fitness;
	}

}
