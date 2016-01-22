package project.genetic.fitness;

import java.util.Collections;
import java.util.Comparator;
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
	private static Comparator<Individual<ICoordinate>> comparator;

	private Fitness() {
		comparator = new Comparator<Individual<ICoordinate>>() {
			@Override
			public int compare(Individual<ICoordinate> o1,
					Individual<ICoordinate> o2) {
				if (o1 == null && o2 == null)
					return 0;
				if (o1 == null)
					return -1;
				if (o2 == null)
					return 1;
				return (int) (o1.getFitness() - o2.getFitness());
			}
		};
	}

	public static Fitness getInstance() {
		if (self == null) {
			self = new Fitness();
		}
		return self;
	}

	/**
	 * sorts the generation base on the comparator
	 * 
	 * @param generation
	 * @return sorted generation
	 */
	public List<Individual<ICoordinate>> sort(
			List<Individual<ICoordinate>> generation) {

		try {
			Collections.sort(generation, comparator);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return generation;
	}
}
