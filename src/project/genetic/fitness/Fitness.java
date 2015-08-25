package project.genetic.fitness;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import project.genetic.vo.coordinate.ICoordinate;
import project.genetic.vo.individual.Individual;

/**
 * java class definition for generating fitness for a path
 * 
 * @author ABHILASHKUMARV
 * 
 */
public class Fitness {

	private static Fitness self;

	private Fitness() {
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
	public List<Individual<ICoordinate>> sortGeneration(
			List<Individual<ICoordinate>> generation) {
		Collections.sort(generation, getComparator());
		return generation;
	}

	public Comparator<Individual<ICoordinate>> getComparator() {
		return new Comparator<Individual<ICoordinate>>() {
			@Override
			public int compare(Individual<ICoordinate> o1,
					Individual<ICoordinate> o2) {
				return (int) (o1.getFitness() - o2.getFitness());
			}
		};
	}
}
