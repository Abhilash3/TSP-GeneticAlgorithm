package project.genetic.process;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import project.genetic.vo.list.MagicList;
import project.genetic.vo.list.individual.Individual;
import project.genetic.vo.list.individual.Path;
import project.genetic.vo.coordinate.ICoordinate;

/**
 * java class definition providing mutation capabilities
 * 
 * @author ABHILASHKUMARV
 * 
 */
public class Mutation {

	private static Mutation self;
	private static Random rand;
	private static List<Strategy> choices;
	private static int size;
	private static List<ICoordinate> list;

	public interface Strategy {

		/**
		 * method generating mutation options and mutating path
		 * 
		 * @param path
		 * @return mutated path
		 */
		public Individual<ICoordinate> mutate(Individual<ICoordinate> path);
	}

	private Mutation() {
		Mutation.rand = new Random();
		prepareChoices();
	}

	public static Mutation getInstance() {
		if (self == null) {
			self = new Mutation();
		}
		return self;
	}

	private static void prepareChoices() {
		choices = new ArrayList<Strategy>();

		choices.add(new Strategy() {

			/**
			 * [0,1,2,3,4,5,6,7,8,9] --> [9,8,7,6,5,4,3,2,1,0]
			 * 
			 * @param path
			 * @return path
			 */
			@Override
			public Individual<ICoordinate> mutate(Individual<ICoordinate> path) {

				list = new MagicList<ICoordinate>();
				for (int i = 0; i < path.size(); i++) {
					list.add(0, path.get(i));
				}
				return new Path(list);
			}
		});

		choices.add(new Strategy() {

			/**
			 * [0,1,2,3,4,5,6,7,8,9] --> 5 --> [5,6,7,8,9,0,1,2,3,4]
			 * 
			 * @param path
			 * @return path
			 */
			@Override
			public Individual<ICoordinate> mutate(Individual<ICoordinate> path) {

				list = new MagicList<ICoordinate>();
				int random = rand.nextInt(path.size() - 1) + 1;
				for (int i = 0; i < random; i++) {
					list.add(path.get(i));
				}

				for (int i = random, j = 0; i < path.size(); i++, j++) {
					list.add(j, path.get(i));
				}

				return new Path(list);
			}
		});

		choices.add(new Strategy() {

			/**
			 * [0,1,2,3,4,5,6,7,8,9] --> [1,0,3,2,5,4,7,6,9,8]
			 * 
			 * @param path
			 * @return path
			 */
			@Override
			public Individual<ICoordinate> mutate(Individual<ICoordinate> path) {

				list = new MagicList<ICoordinate>();
				for (int i = 1; i < path.size(); i += 2) {
					list.add(path.get(i));
					list.add(path.get(i - 1));
				}
				if (path.size() % 2 == 1)
					list.add(path.get(path.size() - 1));

				return new Path(list);
			}
		});

		choices.add(new Strategy() {

			/**
			 * [0,1,2,3,4,5,6,7,8,9] --> 4, 6 --> [0,1,2,3,6,5,4,7,8,9]
			 * 
			 * @param path
			 * @return path
			 */
			@Override
			public Individual<ICoordinate> mutate(Individual<ICoordinate> path) {

				list = new MagicList<ICoordinate>();
				int random1 = rand.nextInt(path.size() - 1);
				int random2 = rand.nextInt(path.size() - 1);
				
				int min = Math.min(random1, random2);
				int max = Math.max(random1, random2);

				for (int i = 0; i < min; i++) {
					list.add(path.get(i));
				}

				for (int i = max - 1; i >= min; i--) {
					list.add(path.get(i));
				}

				for (int i = max; i < path.size(); i++) {
					list.add(path.get(i));
				}

				return new Path(list);
			}
		});

		size = choices.size();
	}

	public static Strategy getStrategy() {
		return choices.get(rand.nextInt(size));
	}

	public Individual<ICoordinate> mutate(Individual<ICoordinate> path) {
		return getStrategy().mutate(path);
	}

}
