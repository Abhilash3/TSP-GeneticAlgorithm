package project.genetic.process;

import java.util.Collections;
import java.util.List;
import java.util.Random;

import project.genetic.fitness.Fitness;
import project.genetic.vo.IGene;
import project.genetic.vo.coordinate.ICoordinate;
import project.genetic.vo.list.MagicList;

/**
 * java class definition providing selection capabilities
 * 
 * @author ABHILASHKUMARV
 * 
 */
public class Selection {

	private Fitness fitness;
	private Random rand;

	private List<Choices> choices;
	private int size;

	public interface Choices {

		/**
		 * method generating selection options and selecting path
		 * 
		 * @param generation
		 * @return selected path
		 */
		public IGene<ICoordinate> select(List<IGene<ICoordinate>> generation);
	}

	public Selection(Fitness fitness) {
		this.fitness = fitness;
		this.rand = new Random();

		prepareChoices();
	}

	private void prepareChoices() {
		choices = new MagicList<Choices>();

		choices.add(new Choices() {

			/**
			 * Roulette Wheel Selection
			 * 
			 * @param generation
			 * @return path
			 */
			@Override
			public IGene<ICoordinate> select(List<IGene<ICoordinate>> generation) {

				double generationFitness = 0;
				double max = 0;

				for (IGene<ICoordinate> path : generation) {
					if (max < path.getFitness()) {
						max = path.getFitness();
					}
				}

				for (IGene<ICoordinate> path : generation) {
					generationFitness += max - path.getFitness() + 1;
				}

				int random = rand.nextInt((int) generationFitness);

				generationFitness = 0;
				IGene<ICoordinate> selectedPath = null;
				for (IGene<ICoordinate> path : generation) {
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
			public IGene<ICoordinate> select(List<IGene<ICoordinate>> generation) {

				int random = rand.nextInt(generation.size()) + 1;

				List<IGene<ICoordinate>> pool = new MagicList<IGene<ICoordinate>>();
				List<Integer> selectedList = new MagicList<Integer>();
				for (int i = 0; i < random; i++) {
					int randomPosition = rand.nextInt(generation.size());
					if (selectedList.contains(randomPosition)) {
						i--;
						continue;
					}
					pool.add(generation.get(randomPosition));
					selectedList.add(randomPosition);
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
			public IGene<ICoordinate> select(List<IGene<ICoordinate>> generation) {

				Collections.sort(generation, fitness.getComparator());
				Collections.reverse(generation);

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

				Collections.reverse(generation);
				return generation.get(i - 1);
			}
		});

		size = choices.size();
	}

	public Choices getChoice() {
		return choices.get(rand.nextInt(size));
	}

	public IGene<ICoordinate> select(List<IGene<ICoordinate>> generation) {
		return getChoice().select(generation);
	}
}
