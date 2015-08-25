package project.genetic;

import project.genetic.process.Crossover;
import project.genetic.fitness.Fitness;
import project.genetic.process.Mutation;
import project.genetic.process.Selection;
import project.genetic.vo.individual.Individual;
import project.genetic.vo.coordinate.ICoordinate;
import project.genetic.vo.list.Path;
import project.ui.UI;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * java class definition providing genetic capabilities
 * 
 * @author ABHILASHKUMARV
 * 
 */
public class Genetic {

	private final static int Generations = 5000;
	private final static boolean Elitism = true;

	protected static Random rand = new Random();

	protected Selection selection;
	protected Crossover crossover;
	protected Mutation mutation;
	protected Fitness fitness;
	private UI ui;

	private int cityNumber;
	private int populationSize;

	protected List<ICoordinate> coordinates;
	protected Individual<ICoordinate> bestPath;

	private Class<? extends Individual<ICoordinate>> cl;

	public Genetic(List<ICoordinate> coordinates,
			Class<? extends Individual<ICoordinate>> cl) {
		this.coordinates = coordinates;
		this.cityNumber = coordinates.size();
		this.populationSize = cityNumber * 3;
		this.cl = cl;

		fitness = Fitness.getInstance();
		selection = Selection.getInstance();
		crossover = Crossover.getInstance();
		mutation = Mutation.getInstance();
	}

	public Genetic(List<ICoordinate> coordinates,
			Class<? extends Individual<ICoordinate>> cl, UI ui) {
		this(coordinates, cl);
		this.ui = ui;
	}

	/**
	 * simulate the genetic process
	 * 
	 * @return
	 */
	public Individual<ICoordinate> simulate() {

		List<Individual<ICoordinate>> generation = new ArrayList<Individual<ICoordinate>>();
		List<Individual<ICoordinate>> newGeneration = new ArrayList<Individual<ICoordinate>>();
		bestPath = new Path();

		for (int i = 0; i < populationSize; i++) {
			generation.add(getRandomPath());
		}
		generation = fitness.sortGeneration(generation);
		bestPath = generation.get(0);

		for (int i = 0; i < Generations; i++) {

			if (Elitism)
				newGeneration.add(bestPath);

			for (; newGeneration.size() != populationSize;) {

				Individual<ICoordinate> parent1 = selection.select(generation);

				Individual<ICoordinate> parent2;
				do {
					parent2 = selection.select(generation);
				} while (parent2 == parent1);

				Individual<ICoordinate> child = crossover.cross(parent1,
						parent2);

				if (rand.nextInt(Generations) > i) {
					child = mutation.mutate(child);
				}

				if (newGeneration.contains(child))
					continue;
				newGeneration.add(child);

			}

			generation = fitness.sortGeneration(newGeneration);
			newGeneration = new ArrayList<Individual<ICoordinate>>();

			bestPath = generation.get(0);

			if (ui != null)
				updateUI(generation, i);
			else
				System.out.println(String.format("%s: %6s %s: %20s",
						"Generation", (i + 1), "Best Result",
						bestPath.getFitness()));
		}

		return bestPath;
	}

	private void updateUI(List<Individual<ICoordinate>> generation, int n) {

		ui.clearMapLines();
		ui.drawMap(copy(bestPath));

		List<Double> scores = new ArrayList<Double>();
		scores.add(bestPath.getFitness());
		for (int j = 1; j < ui.Graphs; j++) {
			scores.add(generation.get(j * populationSize / (ui.Graphs - 1) - 1)
					.getFitness());
		}
		ui.updateGraph(scores);

		ui.setText(String.format("%s: %6s      %s: %.6f", "Generation",
				(n + 1), "Best Result", bestPath.getFitness()));
	}

	private List<ICoordinate> copy(List<ICoordinate> list) {
		List<ICoordinate> copyList = new ArrayList<ICoordinate>();
		for (ICoordinate iCoordinate : list) {
			copyList.add(copy(iCoordinate));
		}
		return copyList;
	}

	private ICoordinate copy(ICoordinate iCoordinate) {
		ICoordinate copyICoordinate = null;
		try {
			copyICoordinate = iCoordinate.getClass()
					.getConstructor(Integer.class, Integer.class)
					.newInstance(iCoordinate.getX(), iCoordinate.getY());
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		}
		return copyICoordinate;
	}

	/**
	 * generate random paths to work
	 * 
	 * @return path
	 */
	protected Individual<ICoordinate> getRandomPath() {
		Individual<ICoordinate> path = null;
		try {
			path = cl.getConstructor().newInstance();
		} catch (InstantiationException | IllegalAccessException
				| IllegalArgumentException | SecurityException
				| InvocationTargetException | NoSuchMethodException e) {
			e.printStackTrace();
		}
		for (; path.size() != cityNumber;) {
			path.add(coordinates.get(rand.nextInt(cityNumber)));
		}
		return path;
	}
}
