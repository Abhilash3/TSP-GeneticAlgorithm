package project.genetic.process;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import project.genetic.vo.individual.Individual;
import project.genetic.vo.coordinate.ICoordinate;
import project.genetic.vo.list.Path;

/**
 * java class definition providing crossover capabilities
 * 
 * @author ABHILASHKUMARV
 * 
 */
public class Crossover {

	private static Crossover self;

	private static Random rand;

	private static List<Choice> choices;
	private static int size;

	public interface Choice {

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
		prepareChoices();
	}

	public static Crossover getInstance() {
		if (self == null) {
			self = new Crossover();
		}
		return self;
	}

	private static void prepareChoices() {
		choices = new ArrayList<Choice>();

		choices.add(new Choice() {

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

				Individual<ICoordinate> path3 = new Path();
				path3.add(path1.get(0));

				for (Integer i = 1; i < path1.size(); i++) {

					ICoordinate city1 = path1.get(i);
					ICoordinate city2 = path2.get(i);

					if (!path3.contains(city1) && !path3.contains(city2)) {
						Double distance1 = path1.get(i - 1).distanceFrom(city1);
						Double distance2 = path2.get(i - 1).distanceFrom(city2);
						if (distance1 < distance2) {
							path3.add(city1);
						} else {
							path3.add(city2);
						}
					} else if (!path3.contains(city1)) {
						path3.add(city1);
					} else if (!path3.contains(city2)) {
						path3.add(city2);
					} else {
						for (ICoordinate city : path1) {
							if (!path3.contains(city)) {
								path3.add(city);
								break;
							}
						}
					}
				}

				return path3;
			}
		});

		choices.add(new Choice() {

			/**
			 * selects first random no of cities from path1, rest from path 2
			 * 
			 * @param path1
			 * @param path2
			 * @return child path
			 */
			@Override
			public Individual<ICoordinate> cross(Individual<ICoordinate> path1,
					Individual<ICoordinate> path2) {
				Individual<ICoordinate> path3 = new Path();
				int size = path1.size();

				int random = rand.nextInt(size - 1);

				for (int i = 0; i < random; i++) {
					path3.add(path1.get(i));
				}
				for (int i = random; path3.size() != size; i++) {
					if (!path3.contains(path2.get(i))) {
						path3.add(path2.get(i));
					} else {
						for (ICoordinate city : path2) {
							if (!path3.contains(city)) {
								path3.add(city);
								break;
							}
						}
					}
				}

				return path3;
			}
		});

		choices.add(new Choice() {

			/**
			 * selects cities from both paths in whichever order they come with
			 * no duplication
			 * 
			 * @param path1
			 * @param path2
			 * @return
			 */
			@SuppressWarnings("unchecked")
			@Override
			public Individual<ICoordinate> cross(Individual<ICoordinate> path1,
					Individual<ICoordinate> path2) {
				Individual<ICoordinate> path3 = null;
				try {
					path3 = path1.getClass().newInstance();
				} catch (InstantiationException | IllegalAccessException e) {
					e.printStackTrace();
				}

				for (int i = 0; path3.size() != path1.size(); i++) {
					path3.add(path1.get(i));
					path3.add(path2.get(i));
				}

				return path3;
			}
		});

		size = choices.size();
	}

	public static Choice getChoice() {
		return choices.get(rand.nextInt(size));
	}

	public Individual<ICoordinate> cross(Individual<ICoordinate> parent1,
			Individual<ICoordinate> parent2) {
		return getChoice().cross(parent1, parent2);
	}
}
