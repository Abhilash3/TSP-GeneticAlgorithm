package project.genetic;

import project.genetic.process.Crossover;
import project.genetic.fitness.Fitness;
import project.genetic.process.Mutation;
import project.genetic.process.Selection;
import project.genetic.vo.list.individual.Individual;
import project.genetic.vo.list.individual.Path;
import project.genetic.vo.coordinate.ICoordinate;
import project.ui.UI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static project.common.Constants.Generations;
import static project.common.Constants.Elitism;
import static project.common.Constants.Graphs;

/**
 * java class definition providing genetic capabilities
 * 
 * @author ABHILASHKUMARV
 * 
 */
public class Genetic {

	protected static Random rand = new Random();
	protected static Fitness fitness = Fitness.getInstance();
	protected static Selection selection = Selection.getInstance();
	protected static Crossover crossover = Crossover.getInstance();
	protected static Mutation mutation = Mutation.getInstance();

	private int cityNumber;
	private int populationSize;

	protected List<Double> scores;
	protected List<ICoordinate> coordinates;
	protected Individual<ICoordinate> bestPath;

	private UI ui;

	private static final String Format = "%s: %6s      %s: %.6f";

	public Genetic(List<ICoordinate> coordinates) {
		this.coordinates = coordinates;
		this.cityNumber = coordinates.size();
		this.populationSize = cityNumber * (cityNumber - 10);
	}

	public Genetic(List<ICoordinate> coordinates, UI ui) {
		this(coordinates);
		this.ui = ui;
	}

	/**
	 * simulate the genetic process
	 * 
	 * @return
	 */
	public Individual<ICoordinate> simulate() {

		List<Individual<ICoordinate>> generation = new ArrayList<Individual<ICoordinate>>(
				populationSize);
		List<Individual<ICoordinate>> newGeneration = new ArrayList<Individual<ICoordinate>>(
				populationSize);

		for (int i = 0; i < populationSize; i++) {
			generation.add(getRandomPath());
		}
		generation = fitness.sort(generation);
		bestPath = generation.get(0);

		Individual<ICoordinate> parent1, parent2, child;
		for (int i = 1; i <= Generations; i++) {

			if (Elitism)
				newGeneration.add(bestPath);

			for (; newGeneration.size() != populationSize;) {

				do {
					parent1 = selection.select(generation);
				} while (parent1 == null);

				do {
					parent2 = selection.select(generation);
				} while (parent2 == null || parent1.equals(parent2));

				child = crossover.cross(parent1, parent2);
				newGeneration.add(child);

				if (newGeneration.size() != populationSize
						&& rand.nextInt(100) % 10 == 0) {
					child = mutation.mutate(child);
					newGeneration.add(child);
				}

			}

			generation = fitness.sort(newGeneration);
			newGeneration = new ArrayList<Individual<ICoordinate>>(
					populationSize);

			bestPath = generation.get(0);

			if (ui != null)
				updateUI(generation, i);
			else
				System.out.println(String.format(Format, "Generation", i,
						"Best Result", bestPath.getFitness()));
		}

		return bestPath;
	}

	private void updateUI(List<Individual<ICoordinate>> generation, int n) {

		ui.clearMapLines();
		ui.drawMap(bestPath);

		scores = new ArrayList<Double>(Graphs);
		scores.add(bestPath.getFitness());
		for (int j = 1; j < Graphs; j++) {
			scores.add(generation.get(j * populationSize / (Graphs - 1) - 1)
					.getFitness());
		}
		ui.updateGraph(scores);

		ui.setText(String.format(Format, "Generation", n, "Best Result",
				bestPath.getFitness()));
	}

	/**
	 * generate random paths to work
	 * 
	 * @return path
	 */
	protected Individual<ICoordinate> getRandomPath() {
		Collections.shuffle(coordinates);
		return new Path(coordinates);
	}
}
