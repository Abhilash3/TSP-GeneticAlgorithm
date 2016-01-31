package project.genetic.fitness;

import java.util.ArrayList;
import java.util.List;

import project.genetic.vo.coordinate.ICoordinate;
import project.genetic.vo.list.individual.Individual;

/**
 * java class definition for sorting path
 * 
 * @author ABHILASHKUMARV
 * 
 */
public class Fitness {

	private static Fitness self;

	private Fitness() {}

	public static Fitness getInstance() {
		if (self == null) {
			self = new Fitness();
		}
		return self;
	}

	/**
	 * Gets the Individual with the best fitness
	 * 
	 * @param generation
	 * @return Individual with best fitness
	 */
	public Individual<ICoordinate> getFittest(
			List<Individual<ICoordinate>> generation) {

		return getRankedIndividual(generation, 1);

	}

	/**
	 * Gets the nth ranked Individual from generation
	 * in terms of fitness
	 * 
	 * @param generation
	 * @param rank
	 * @return Individual with nth ranked fitness
	 */
	public Individual<ICoordinate> getRankedIndividual(
			List<Individual<ICoordinate>> generation, int rank) {
		if (rank < 1 || generation.size() < rank)
			return null;

		List<Individual<ICoordinate>> list = new ArrayList<Individual<ICoordinate>>(
				rank + 1);

		list.add(generation.get(0));
		for (int i = 1; i < generation.size(); i++) {
			Individual<ICoordinate> path = generation.get(i);

			if (i < rank) {
				int size = generation.size();
				for (int j = 0; j < i; j++) {
					if (Double.compare(list.get(j).getFitness(),
							path.getFitness()) == 1) {
						list.add(j, path);
						break;
					}
				}
				if (size == generation.size()) {
					list.add(path);
				}
			} else {
				for (int j = 0; j < list.size(); j++) {
					if (Double.compare(list.get(j).getFitness(),
							path.getFitness()) == 1) {
						list.add(j, path);
						list.remove(list.size() - 1);
						break;
					}
				}
			}
		}

		return list.get(rank - 1);
	}
}
