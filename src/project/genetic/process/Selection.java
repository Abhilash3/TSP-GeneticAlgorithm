package project.genetic.process;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import project.genetic.fitness.Fitness;
import project.genetic.vo.Cloneable;
import project.genetic.vo.list.individual.Individual;

/**
 * java class definition providing selection capabilities
 * 
 * @author ABHILASHKUMARV
 * 
 */
public class Selection<T extends Individual<? extends Cloneable>> {
	
	protected Selection() {}
	
	public static <E extends Individual<? extends Cloneable>> Selection<E> getInstance() {
		return new Selection<E>();
	}

	public interface Strategy<T> {

		/**
		 * method generating selection options and selecting path
		 * 
		 * @param generation generation pool
		 * @return selection
		 */
		T select(List<T> generation);
	}
	
	protected Strategy<T> getRouletteWheelSelectionStrategy() {
		return new Strategy<T>() {
			private T ind;

			/**
			 * Roulette Wheel Selection
			 * 
			 * @param generation generation pool
			 * @return selection
			 */
			@Override
			public T select(List<T> generation) {

				double max = 0, indFitness;
				for (int i = 0; i < generation.size(); i++) {
					ind = generation.get(i);
					indFitness = ind.getFitness();
					if (max < indFitness) {
						max = indFitness;
					}
				}
				
				double random = getRandom().nextDouble() * max;
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
		};
	}
	
	protected Strategy<T> getTournamentSelectionStrategy() {
		return new Strategy<T>() {
			private List<T> pool;

			/**
			 * Tournament Selection
			 * 
			 * @param generation generation pool
			 * @return selection
			 */
			@Override
			public T select(List<T> generation) {

				int size = generation.size();
				int random = getRandom().nextInt(size - 2) + 2;

				pool = new ArrayList<T>();
				while (pool.size() != random) {
					pool.add(generation.get(getRandom().nextInt(size)));
				}

				return Fitness.<T>getInstance().getFittest(pool);
			}
		};
	}
	
	protected Strategy<T> getRankSelectionStrategy() {
		return new Strategy<T>() {

			/**
			 * Rank Selection
			 * 
			 * @param generation generation pool
			 * @return selection
			 */
			@Override
			public T select(List<T> generation) {

				int sum = 0, i = 1;
				while (i <= generation.size()) {
					sum += i++;
				}

				int random = getRandom().nextInt(sum - 1) + 1;
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
		};
	}
	
	protected Random getRandom() {
		return new Random();
	}

	protected Strategy<T> getStrategy() {
		List<Strategy<T>> strategies = Arrays.asList(getRankSelectionStrategy(),
				getRouletteWheelSelectionStrategy(), getTournamentSelectionStrategy());
		
		Collections.shuffle(strategies);
		return strategies.get(0);
	}

	public T select(List<T> generation) {
		return getStrategy().select(generation);
	}
}
