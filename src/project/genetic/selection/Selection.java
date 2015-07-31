package project.genetic.selection;

import java.util.ArrayList;
import java.util.Random;

import project.genetic.fitness.Fitness;
import project.genetic.vo.AArrayList;

/**
 * java class definition providing selection capabilities
 * 
 * @author ABHILASHKUMARV
 * 
 */
public class Selection {

	private int populationSize;
	private Fitness fitness;
	private Random rand;

	public Selection(int populationSize, Fitness fitness) {
		this.populationSize = populationSize;
		this.fitness = fitness;
		this.rand = new Random();
	}

	/**
	 * method generating selection options and selecting path
	 * 
	 * @param generation
	 * @return selected path
	 */
	public ArrayList<Integer> select(AArrayList<Integer> generation) {
		ArrayList<Integer> selectedPath = null;

		int random = this.rand.nextInt(99);
		if (random % 2 == 0) {
			selectedPath = selection1(generation);
		} else {
			selectedPath = selection2(generation);
		}

		return selectedPath;
	}

	/**
	 * generates generation fitness and select path which makes the sum of
	 * fitness till that path
	 * 
	 * @param generation
	 * @return path
	 */
	private ArrayList<Integer> selection1(AArrayList<Integer> generation) {

		double generationFitness = 0;
		ArrayList<Integer> selectedPath = null;
		double max = 0;

		for (ArrayList<Integer> path : generation) {
			if (max < fitness.generateFitness(path)) {
				max = fitness.generateFitness(path);
			}
		}

		for (ArrayList<Integer> path : generation) {
			generationFitness += max - fitness.generateFitness(path) + 1;
		}

		int random = rand.nextInt((int) generationFitness);

		generationFitness = 0;
		for (ArrayList<Integer> path : generation) {
			generationFitness += max - fitness.generateFitness(path) + 1;
			if (generationFitness >= random) {
				selectedPath = path;
				break;
			}
		}

		return selectedPath;
	}

	/**
	 * creates a pool of randomly selected paths and selects the best path
	 * 
	 * @param generation
	 * @return path
	 */
	private ArrayList<Integer> selection2(AArrayList<Integer> generation) {

		int random = rand.nextInt(this.populationSize / 4) + 1;

		AArrayList<Integer> pool = new AArrayList<Integer>();
		ArrayList<Integer> selectedList = new ArrayList<Integer>();
		for (int i = 0; i < random; i++) {
			int randomPosition = rand.nextInt(populationSize);
			if (selectedList.contains(randomPosition)) {
				i--;
				continue;
			}
			pool.add(generation.get(randomPosition));
			selectedList.add(randomPosition);
		}

		return fitness.sortGeneration(pool).get(0);
	}

}
