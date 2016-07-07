package project.genetic.process;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import project.genetic.fitness.Fitness;
import project.genetic.vo.coordinate.ICoordinate;
import project.genetic.vo.list.individual.Individual;

/**
 * java class definition providing selection capabilities
 * 
 * @author ABHILASHKUMARV
 * 
 */
public class Selection {

	protected static Selection self;
	protected static List<Strategy> strategies;

	public interface Strategy {

		/**
		 * method generating selection options and selecting path
		 * 
		 * @param generation generation pool
		 * @return selection
		 */
		Individual<ICoordinate> select(List<Individual<ICoordinate>> generation);
	}

	private void prepareStrategies() {
		strategies = new ArrayList<Strategy>();

		strategies.add(new Strategy() {
			private Individual<ICoordinate> ind;

			/**
			 * Roulette Wheel Selection
			 * 
			 * @param generation generation pool
			 * @return selection
			 */
			@Override
			public Individual<ICoordinate> select(List<Individual<ICoordinate>> generation) {

				double max = 0, indFitness;
				for (int i = 0; i < generation.size(); i++) {
					ind = generation.get(i);
					indFitness = ind.getFitness();
					if (max < indFitness) {
						max = indFitness;
					}
				}
				
				double random = self.getRandom().nextDouble() * max;
				double sum = 0;
				for (int i = 0 ; i < generation.size(); i++) {
					ind = generation.get(i);
					sum += ind.getFitness();
					if (sum > random) {
						break;
					}
				}
				return ind;

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
				int random = self.getRandom().nextInt(size - 2) + 2;

				pool = new ArrayList<Individual<ICoordinate>>();
				while (pool.size() != random) {
					pool.add(generation.get(self.getRandom().nextInt(size)));
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

				int random = self.getRandom().nextInt(sum - 1) + 1;
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
	
	protected Selection() {
		prepareStrategies();
	}
	
	public static Selection getInstance() {
		if (self == null) {
			self = new Selection();
		}
		return self;
	}
	
	protected Random getRandom() {
		return new Random();
	}

	protected Strategy getStrategy() {
		return strategies.get(getRandom().nextInt(strategies.size()));
	}

	public Individual<ICoordinate> select(List<Individual<ICoordinate>> generation) {
		return getStrategy().select(generation);
	}
}
