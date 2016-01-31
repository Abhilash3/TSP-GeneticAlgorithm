package project.genetic.process;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import project.genetic.vo.coordinate.Coordinates;
import project.genetic.vo.coordinate.ICoordinate;
import project.genetic.vo.list.individual.Individual;
import project.genetic.vo.list.individual.Path;

/**
 * java class definition providing crossover capabilities
 * 
 * @author ABHILASHKUMARV
 * 
 */
public class Crossover {

	private static Crossover self;
	private static Random rand;
	private static List<Strategy> strategies;
	private static ICoordinate city1, city2;
	private static List<ICoordinate> list;
	
	private static int size;

	public interface Strategy {

		/**
		 * method generating crossover options and generating child path
		 * 
		 * @param path1
		 * @param path2
		 * @return childPath
		 */
		public Individual<ICoordinate> cross(Individual<ICoordinate> path1,
				Individual<ICoordinate> path2);
	}

	private Crossover() {
		Crossover.rand = new Random();
		prepareStrategies();
		size = strategies.size();
	}

	public static Crossover getInstance() {
		if (self == null) {
			self = new Crossover();
		}
		return self;
	}

	private static void prepareStrategies() {
		strategies = new ArrayList<Strategy>();

		strategies.add(new Strategy() {

			/**
			 * first city from path1, get nth city from both paths and select
			 * whichever is closer to n-1th city in child path
			 * 
			 * @param path1
			 * @param path2
			 * @return child path
			 */
			@Override
			public Individual<ICoordinate> cross(Individual<ICoordinate> path1,
					Individual<ICoordinate> path2) {
				
				list = new ArrayList<ICoordinate>();
				list.add(path1.get(0));
				
				for (int i = 1; i < path1.size(); i++) {

					city1 = path1.get(i);
					city2 = path2.get(i);

					if (!list.contains(city1) && !list.contains(city2)) {
						double distance1 = Coordinates.distance(path1.get(i - 1), city1);
						double distance2 = Coordinates.distance(path2.get(i - 1), city2);
						if (Double.compare(distance1, distance2) > 0) {
							list.add(city2);
						} else {
							list.add(city1);
						}
					} else if (!list.contains(city1)) {
						list.add(city1);
					} else if (!list.contains(city2)) {
						list.add(city2);
					} else {
						for (int j = 0; j < path1.size(); j++) {
							city1 = path1.get(j);
							if (!list.contains(city1)) {
								list.add(city1);
								break;
							}
						}
					}
				}

				return new Path(list);
			}
		});

		strategies.add(new Strategy() {

			/**
			 * selects first random no of cities from path1, rest from path2
			 * 
			 * @param path1
			 * @param path2
			 * @return child path
			 */
			@Override
			public Individual<ICoordinate> cross(Individual<ICoordinate> path1,
					Individual<ICoordinate> path2) {

				int random = rand.nextInt(path1.size() - 1) + 1;
				list = new ArrayList<ICoordinate>();
				
				for (int i = 0; i < random; i++) {
					list.add(path1.get(i));
				}
				
				for (int i = random; list.size() != path1.size(); i++) {
					city1 = path2.get(i);
					if (!list.contains(city1)) {
						list.add(city1);
					} else {
						for (int j = 0; j < random; j++) {
							city2 = path2.get(j);
							if (!list.contains(city2)) {
								list.add(city2);
								break;
							}
						}
					}
				}

				return new Path(list);
			}
		});

		strategies.add(new Strategy() {

			/**
			 * selects cities from both paths in whichever order they come with
			 * no duplication
			 * 
			 * @param path1
			 * @param path2
			 * @return
			 */
			@Override
			public Individual<ICoordinate> cross(Individual<ICoordinate> path1,
					Individual<ICoordinate> path2) {

				list = new ArrayList<ICoordinate>();
				
				for (int i = 0; list.size() != path1.size(); i++) {
					city1 = path1.get(i);
					if (!list.contains(city1))
						list.add(city1);
					
					if (list.size() == path1.size())
						break;
					
					city2 = path2.get(i);
					if (!list.contains(city2))
						list.add(city2);
				}

				return new Path(list);
			}
		});
	}

	public static Strategy getStrategy() {
		return strategies.get(rand.nextInt(size));
	}

	public Individual<ICoordinate> cross(Individual<ICoordinate> parent1,
			Individual<ICoordinate> parent2) {
		return getStrategy().cross(parent1, parent2);
	}
}
