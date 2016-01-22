package project.genetic.process;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import project.genetic.fitness.Fitness;
import project.genetic.vo.list.individual.Individual;
import project.genetic.vo.coordinate.ICoordinate;

/**
 * java class definition providing selection capabilities
 * 
 * @author ABHILASHKUMARV
 * 
 */
public class Selection {

	private static Selection self;
	private static Fitness fitness;
	private static Random rand;
	private static List<Strategy> choices;
	private static int size;
	private static List<Individual<ICoordinate>> pool;
	private static Individual<ICoordinate> ind;

	public interface Strategy {

		/**
		 * method generating selection options and selecting path
		 * 
		 * @param generation
		 * @return selected path
		 */
		public Individual<ICoordinate> select(
				List<Individual<ICoordinate>> generation);
	}

	private Selection() {
		Selection.fitness = Fitness.getInstance();
		Selection.rand = new Random();

		prepareChoices();
	}

	public static Selection getInstance() {
		if (self == null) {
			self = new Selection();
		}
		return self;
	}

	private static void prepareChoices() {
		choices = new ArrayList<Strategy>();

		choices.add(new Strategy() {

			/**
			 * Roulette Wheel Selection
			 * 
			 * @param generation
			 * @return path
			 */
			@Override
			public Individual<ICoordinate> select(
					List<Individual<ICoordinate>> generation) {

				double max = 0;
				for (int i = 0; i < generation.size(); i++) {
					ind = generation.get(i);
					if (max < ind.getFitness())
						max = ind.getFitness();
				}

				pool = new ArrayList<Individual<ICoordinate>>();
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

		choices.add(new Strategy() {

			/**
			 * Tournament Selection
			 * 
			 * @param generation
			 * @return path
			 */
			@Override
			public Individual<ICoordinate> select(
					List<Individual<ICoordinate>> generation) {

				int random = rand.nextInt(generation.size() - 2) + 2;

				pool = new ArrayList<Individual<ICoordinate>>();
				for (; pool.size() != random;)
					pool.add(generation.get(rand.nextInt(generation.size())));

				return fitness.sort(pool).get(0);
			}
		});

		choices.add(new Strategy() {

			/**
			 * Rank Selection
			 * 
			 * @param generation
			 * @return path
			 */
			@Override
			public Individual<ICoordinate> select(
					List<Individual<ICoordinate>> generation) {

				int sum = 0, i = 1;
				for (; i <= generation.size(); i++)
					sum += i;

				int random = rand.nextInt(sum);
				sum = 0;
				i = 0;
				for (; i <= generation.size(); i++) {
					sum += i;
					if (sum > random)
						break;
				}

				return generation.get(i - 1);
			}
		});

		size = choices.size();
	}

	public static Strategy getStrategy() {
		return choices.get(rand.nextInt(size));
	}

	public Individual<ICoordinate> select(
			List<Individual<ICoordinate>> generation) {
		return getStrategy().select(generation);
	}
}
