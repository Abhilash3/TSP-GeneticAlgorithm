package project;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import project.genetic.vo.coordinate.Coordinate;
import project.genetic.vo.coordinate.ICoordinate;
import project.genetic.vo.list.MagicList;
import project.genetic.vo.list.individual.Individual;
import project.genetic.vo.list.individual.Path;
import static project.common.Constants.CityNumber;

public class Mother {

	protected static int Cities = CityNumber;
	protected static int Population = 2 * Cities;

	protected static Random rand = new Random();

	public static List<Individual<ICoordinate>> getGeneration() {
		List<Individual<ICoordinate>> generation = new ArrayList<Individual<ICoordinate>>();
		for (int i = 0; i < Population; i++) {
			generation.add(getPath());
		}
		return generation;
	}

	public static List<ICoordinate> getCoordinates() {
		List<ICoordinate> list = new MagicList<ICoordinate>();
		for (; list.size() != Cities; ) {
			list.add(getCoordinate());
		}
		return list;
	}

	public static Path getPath() {
		return new Path(getCoordinates());
	}

	public static Coordinate getCoordinate() {
		return Coordinate.getCoordinate((rand.nextInt(55) + 5) * 10,
				(rand.nextInt(55) + 5) * 10);
	}

}
