package project.genetic;

import project.gui.UI;
import project.genetic.crossover.Crossover;
import project.genetic.fitness.Fitness;
import project.genetic.mutation.Mutation;
import project.genetic.selection.Selection;
import project.genetic.vo.AArrayList;
import project.genetic.vo.City;
import project.genetic.vo.DistanceMatrix;

import java.util.ArrayList;
import java.util.Random;

/**
 * java class definition providing genetic capabilities
 * 
 * @author ABHILASHKUMARV
 * 
 */
public class Genetic {

	private final static int Generations = Integer.MAX_VALUE;
	private final static int PopulationSize = 100;
	private final static boolean Elitism = true;

	protected static Random rand = new Random();

	protected Selection selection;
	protected Crossover crossover;
	protected Mutation mutation;
	protected Fitness fitness;

	protected ArrayList<City> coordinates;
	protected DistanceMatrix distanceMatrix;
	protected ArrayList<Integer> bestPath;
	private int cityNumber;

	private UI ui;

	public Genetic(ArrayList<City> coordinates) {
		this.coordinates = coordinates;
		this.cityNumber = coordinates.size();
		ui = new UI(coordinates);

		distanceMatrix = new DistanceMatrix(coordinates);

		fitness = new Fitness(cityNumber, distanceMatrix, false);
		selection = new Selection(cityNumber, fitness);
		crossover = new Crossover(cityNumber, distanceMatrix);
		mutation = new Mutation(cityNumber);
	}

	/**
	 * simulate the genetic process
	 * 
	 * @return
	 */
	public ArrayList<Integer> simulate() {

		AArrayList<Integer> generation = new AArrayList<Integer>();
		AArrayList<Integer> newGeneration = new AArrayList<Integer>();
		ArrayList<Integer> bestPath = new ArrayList<Integer>();
		ArrayList<Double> scores = new ArrayList<Double>();

		for (int i = 0; i < PopulationSize; i++) {
			generation.add(getRandomPath());
		}
		generation = fitness.sortGeneration(generation);

		for (int i = 0; i < Generations; i++) {

			int elitismOffset = 0;
			if (Elitism) {
				newGeneration.add(generation.get(0));
				elitismOffset = 1;
			}

			for (int j = elitismOffset; j < PopulationSize; j++) {

				ArrayList<Integer> parent1 = selection.select(generation);
				ArrayList<Integer> parent2 = selection.select(generation);
				ArrayList<Integer> child = crossover.cross(parent1, parent2);

				if (rand.nextInt(Generations - 1) > i) {
					child = mutation.mutate(child);
				}

				newGeneration.add(child);

			}

			generation = fitness.sortGeneration(newGeneration);
			newGeneration = new AArrayList<Integer>();

			bestPath = generation.get(0);

			ui.clearLines();
			ui.drawMap(coordinates, bestPath);

			scores = new ArrayList<Double>();
			scores.add(fitness.generateFitness(generation.get(0)));
			for (int j = 1; j < ui.Graphs; j++) {
				scores.add(fitness.generateFitness(generation.get(j
						* PopulationSize / (ui.Graphs - 1) - 1)));
			}
			ui.updateGraph(scores);

			ui.setText(String.format("%s: %6s %s: %20s",
					"Generation", (i + 1), "Best Result",
					fitness.generateFitness(bestPath)));

		}
		
		return bestPath;
	}

	/**
	 * generate random paths to work
	 * 
	 * @return path
	 */
	protected ArrayList<Integer> getRandomPath() {
		ArrayList<Integer> path = new ArrayList<Integer>();
		for (int i = rand.nextInt(cityNumber); path.size() != cityNumber; i = rand
				.nextInt(cityNumber)) {
			if (!path.contains(i)) {
				path.add(i);
			}
		}
		return path;
	}
}
