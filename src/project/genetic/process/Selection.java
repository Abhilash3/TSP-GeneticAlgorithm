package project.genetic.process;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import project.genetic.fitness.Fitness;
import project.genetic.vo.coordinate.ICoordinate;
import project.genetic.vo.list.MagicList;
import project.genetic.vo.list.individual.Individual;

/**
 * java class definition providing selection capabilities
 * 
 * @author ABHILASHKUMARV
 * 
 */
public class Selection {

	protected static List<Strategy> strategies;
	
	private static Random rand = new Random();

	public interface Strategy {

		/**
		 * method generating selection options and selecting path
		 * 
		 * @param generation generation pool
		 * @return selection
		 */
		Individual<ICoordinate> select(List<Individual<ICoordinate>> generation);
	}

	static {
		prepareStrategies();
	}

	private static void prepareStrategies() {
		strategies = new ArrayList<Strategy>();

		strategies.add(new Strategy() {
			private List<Individual<ICoordinate>> pool;
			private Individual<ICoordinate> ind;

			/**
			 * Roulette Wheel Selection
			 * 
			 * @param generation generation pool
			 * @return selection
			 */
			@Override
			public Individual<ICoordinate> select(List<Individual<ICoordinate>> generation) {

				double max = 0;
				for (int i = 0; i < generation.size(); i++) {
					ind = generation.get(i);
					if (max < ind.getFitness())
						max = ind.getFitness();
				}

				pool = new MagicList<Individual<ICoordinate>>();
				for (int i = 0; i < generation.size(); i++) {
					ind = generation.get(i);
					int random = (int) ((max - ind.getFitness()) * 100 / max) + 1;
					for (int j = 0; j < random; j++)
						pool.add(ind);
				}
				Collections.shuffle(pool);
				return pool.get(rand.nextInt(pool.size()));

			}
		});

		strategies.add(new Strategy() {
			private List<Individual<ICoordinate>> pool;

			/**
			 * Tournament Selection
			 * 
			 * @param generation generation pool
			 * @return selection
			 */
			@Override
			public Individual<ICoordinate> select(List<Individual<ICoordinate>> generation) {

				int size = generation.size();
				int random = rand.nextInt(size - 2) + 2;

				pool = new ArrayList<Individual<ICoordinate>>();
				while (pool.size() != random) {
					pool.add(generation.get(rand.nextInt(size)));
				}

				return Fitness.getFittest(pool);
			}
		});

		strategies.add(new Strategy() {

			/**
			 * Rank Selection
			 * 
			 * @param generation generation pool
			 * @return selection
			 */
			@Override
			public Individual<ICoordinate> select(List<Individual<ICoordinate>> generation) {

				int sum = 0, i = 1;
				while (i <= generation.size()) {
					sum += i++;
				}

				int random = rand.nextInt(sum - 1) + 1;
				sum = 0;
				i = 0;
				while (i < generation.size()) {
					sum += i++;
					if (sum > random) {
						break;
					}
				}

				return generation.get(i - 1);
			}
		});
	}

	protected static Strategy getStrategy() {
		return strategies.get(rand.nextInt(strategies.size()));
	}

	public static Individual<ICoordinate> select(List<Individual<ICoordinate>> generation) {
		return getStrategy().select(generation);
	}
}
