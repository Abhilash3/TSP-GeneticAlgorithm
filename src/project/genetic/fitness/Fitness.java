package project.genetic.fitness;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import project.genetic.vo.IGene;
import project.genetic.vo.coordinate.ICoordinate;

/**
 * java class definition for generating fitness for a path
 * 
 * @author ABHILASHKUMARV
 * 
 */
public class Fitness {

	/**
	 * sorts the generation base on the comparator
	 * 
	 * @param generation
	 * @return sorted generation
	 */
	public List<IGene<ICoordinate>> sortGeneration(
			List<IGene<ICoordinate>> generation) {
		Collections.sort(generation, getComparator());
		return generation;
	}

	public Comparator<IGene<ICoordinate>> getComparator() {
		return new Comparator<IGene<ICoordinate>>() {
			@Override
			public int compare(IGene<ICoordinate> o1, IGene<ICoordinate> o2) {
				if (o1 == null || o2 == null)
					return 0;
				return (int) (o1.getFitness() - o2.getFitness());
			}
		};
	}
}
