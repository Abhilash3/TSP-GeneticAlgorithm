package project.genetic.fitness;

import java.util.ArrayList;
import java.util.Collections;
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
		return getRankedIndividuals(generation, Collections.singletonList(1)).get(0);
	}

	public List<Individual<ICoordinate>> getRankedIndividuals(
			List<Individual<ICoordinate>> generation, List<Integer> ranks) {
		if (ranks.size() == 0)
			return new ArrayList<Individual<ICoordinate>>(0);
		
		int max = Collections.max(ranks);
		if (max < 1 || generation.size() < max)
			return new ArrayList<Individual<ICoordinate>>(0);

		List<Individual<ICoordinate>> list = 
				new ArrayList<Individual<ICoordinate>>(max + 1);

		list.add(generation.get(0));
		Individual<ICoordinate> path;
		boolean added = false;
		for (int i = 1; i < generation.size(); i++,added = false) {
			path = generation.get(i);

			for (int j = 0; j < list.size(); j++)
				if (Double.compare(list.get(j).getFitness(), path.getFitness()) == 1) {
					list.add(j, path);
					added = true;
					if (i >= max)
						list.remove(list.size() - 1);
					break;
				}

			if (i < max && !added)
				list.add(path);
		}

		List<Individual<ICoordinate>> items = 
				new ArrayList<Individual<ICoordinate>>(ranks.size());
		for (int i = 0; i < ranks.size(); i++) {
			items.add(list.get(ranks.get(i) - 1));
		}

		return items;
	}
}
