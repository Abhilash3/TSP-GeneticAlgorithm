package project.genetic.process;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import project.genetic.fitness.Fitness;
import project.genetic.vo.individual.Individual;
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

	private static List<Choices> choices;
	private static int size;

	public interface Choices {

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
		choices = new ArrayList<Choices>();

		choices.add(new Choices() {

			/**
			 * Roulette Wheel Selection
			 * 
			 * @param generation
			 * @return path
			 */
			@Override
			public Individual<ICoordinate> select(
					List<Individual<ICoordinate>> generation) {

				double generationFitness = 0;
				double max = 0;

				for (Individual<ICoordinate> path : generation) {
					if (max < path.getFitness()) {
						max = path.getFitness();
					}
				}

				for (Individual<ICoordinate> path : generation) {
					generationFitness += max - path.getFitness() + 1;
				}

				int random = rand.nextInt((int) generationFitness);

				generationFitness = 0;
				Individual<ICoordinate> selectedPath = null;
				for (Individual<ICoordinate> path : generation) {
					generationFitness += max - path.getFitness() + 1;
					if (generationFitness >= random) {
						selectedPath = path;
						break;
					}
				}

				return selectedPath;
			}
		});

		choices.add(new Choices() {

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

				List<Individual<ICoordinate>> pool = new ArrayList<Individual<ICoordinate>>();
				for (int i = 0; i < random; i++) {
					int randomPosition = rand.nextInt(generation.size());
					Individual<ICoordinate> ind = generation.get(randomPosition);
					if (pool.contains(ind)) {
						i--;
						continue;
					}
					pool.add(ind);
				}

				return fitness.sortGeneration(pool).get(0);
			}
		});

		choices.add(new Choices() {

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

	public static Choices getChoice() {
		return choices.get(rand.nextInt(size));
	}

	public Individual<ICoordinate> select(
			List<Individual<ICoordinate>> generation) {
		return getChoice().select(generation);
	}
}
