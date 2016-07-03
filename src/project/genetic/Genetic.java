package project.genetic;

import static project.common.Constants.Elitism;
import static project.common.Constants.Generations;
import static project.common.Constants.Graphs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import project.genetic.fitness.Fitness;
import project.genetic.process.Crossover;
import project.genetic.process.Mutation;
import project.genetic.process.Selection;
import project.genetic.vo.coordinate.ICoordinate;
import project.genetic.vo.list.individual.Individual;
import project.genetic.vo.list.individual.Path;
import project.ui.UI;

/**
 * java class definition providing genetic capabilities
 * 
 * @author ABHILASHKUMARV
 * 
 */
public class Genetic {

	protected static Random rand = new Random();

	private int populationSize;

	protected final List<Double> scores = new ArrayList<Double>(Graphs);

	protected List<ICoordinate> coordinates;
	protected Individual<ICoordinate> bestPath;

	private UI ui;

	private static final String Format = "%s: %6s      %s: %.6f";

	public Genetic(List<ICoordinate> coordinates) {
		this.coordinates = coordinates;
		populationSize = coordinates.size() * 5;
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

		List<Individual<ICoordinate>> generation = new ArrayList<Individual<ICoordinate>>(populationSize);
		List<Individual<ICoordinate>> newGeneration = new ArrayList<Individual<ICoordinate>>(populationSize);

		populateFirstGeneration(generation);
		bestPath = Fitness.getFittest(generation);

		Individual<ICoordinate> parent1, parent2, child;
		for (int i = 1; i <= Generations; i++) {

			if (Elitism)
				newGeneration.add(bestPath);

			for (; newGeneration.size() != populationSize;) {

				parent1 = select(generation);
				parent2 = select(generation, parent1);

				child = crossover(parent1, parent2);
				newGeneration.add(child);

				if (newGeneration.size() != populationSize
						&& rand.nextInt(100) % 5 == 0) {
					child = mutate(child);
					newGeneration.add(child);
				}

			}

			generation = new ArrayList<Individual<ICoordinate>>(newGeneration);
			newGeneration = new ArrayList<Individual<ICoordinate>>(populationSize);
			bestPath = Fitness.getFittest(generation);

			if (ui != null)
				updateUI(generation, i);
			else
				System.out.println(String.format(Format, "Generation", i,
						"Best Result", bestPath.getFitness()));
		}

		return bestPath;
	}

	private Individual<ICoordinate> mutate(Individual<ICoordinate> child) {
		Individual<ICoordinate> mutate;
		do {
			mutate = Mutation.mutate(child);
		} while (child.equals(mutate));
		return mutate;
	}

	private Individual<ICoordinate> crossover(Individual<ICoordinate> parent1,
			Individual<ICoordinate> parent2) {
		return Crossover.cross(parent1, parent2);
	}

	private Individual<ICoordinate> select(List<Individual<ICoordinate>> generation,
								Individual<ICoordinate> parent1) {
		Individual<ICoordinate> parent2;
		do {
			parent2 = select(generation);
		} while (parent2.equals(parent1));
		return parent2;
	}

	private Individual<ICoordinate> select(List<Individual<ICoordinate>> generation) {
		return Selection.select(generation);
	}

	private void populateFirstGeneration(List<Individual<ICoordinate>> generation) {
		for (int i = 0; i < populationSize; i++) {
			generation.add(randomPath());
		}
	}
	
	protected Individual<ICoordinate> randomPath() {
		Collections.shuffle(coordinates);
		return new Path(coordinates);
	}

	private void updateUI(List<Individual<ICoordinate>> generation, int n) {

		ui.clearMapLines();
		ui.drawMap(bestPath);

		scores.clear();
		scores.add(bestPath.getFitness());
		for (int j = 1; j < Graphs; j++) {
            scores.add(generation.get(j * populationSize / (Graphs - 1) - 1).getFitness());
		}
		ui.updateGraph(scores);

		ui.setText(String.format(Format, "Generation", n, "Best Result",
				bestPath.getFitness()));
	}
}
