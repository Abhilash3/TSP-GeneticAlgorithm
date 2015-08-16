package project.genetic;

import project.genetic.process.Crossover;
import project.genetic.fitness.Fitness;
import project.genetic.process.Mutation;
import project.genetic.process.Selection;
import project.genetic.vo.IGene;
import project.genetic.vo.Path;
import project.genetic.vo.coordinate.ICoordinate;
import project.genetic.vo.list.MagicList;
import project.ui.UI;

import java.util.List;
import java.util.Random;

/**
 * java class definition providing genetic capabilities
 * 
 * @author ABHILASHKUMARV
 * 
 */
public class Genetic {

	private final static int Generations = 3000;
	private final static boolean Elitism = true;

	protected static Random rand = new Random();

	protected Selection selection;
	protected Crossover crossover;
	protected Mutation mutation;
	protected Fitness fitness;

	private int cityNumber;
	private int populationSize;

	protected List<ICoordinate> coordinates;
	protected IGene<ICoordinate> bestPath;

	private UI ui;

	public Genetic(List<ICoordinate> coordinates) {
		this.coordinates = coordinates;
		this.cityNumber = coordinates.size();
		this.populationSize = cityNumber * 2;

		fitness = new Fitness();
		selection = new Selection(fitness);
		crossover = new Crossover();
		mutation = new Mutation();
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
	public IGene<ICoordinate> simulate() {

		List<IGene<ICoordinate>> generation = new MagicList<IGene<ICoordinate>>();
		List<IGene<ICoordinate>> newGeneration = new MagicList<IGene<ICoordinate>>();
		bestPath = new Path();

		for (int i = 0; i < populationSize; i++) {
			generation.add(getRandomPath());
		}
		generation = fitness.sortGeneration(generation);

		for (int i = 0; i < Generations; i++) {

			int elitismOffset = 0;
			if (Elitism) {
				newGeneration.add(generation.get(0));
				elitismOffset = 1;
			}
			for (int j = elitismOffset; j < populationSize; j++) {

				IGene<ICoordinate> parent1 = selection.select(generation);

				IGene<ICoordinate> parent2;
				do {
					parent2 = selection.select(generation);
				} while (parent2 == parent1);

				IGene<ICoordinate> child = crossover.cross(parent1, parent2);

				if (rand.nextInt(Generations) > i) {
					child = mutation.mutate(child);
				}

				newGeneration.add(child);

			}

			generation = fitness.sortGeneration(newGeneration);
			newGeneration = new MagicList<IGene<ICoordinate>>();

			bestPath = generation.get(0);

			if (ui != null)
				updateUI(generation, bestPath, i);
			else
				System.out.println(String.format("%s: %6s %s: %20s",
						"Generation", (i + 1), "Best Result",
						bestPath.getFitness()));
		}

		return bestPath;
	}

	private void updateUI(List<IGene<ICoordinate>> generation,
			IGene<ICoordinate> bestPath, int n) {

		ui.clearLines();
		ui.drawMap(bestPath);

		generation = fitness.sortGeneration(generation);

		List<Double> scores = new MagicList<Double>();
		scores.add(bestPath.getFitness());
		for (int j = 1; j < ui.Graphs; j++) {
			scores.add(generation.get(j * populationSize / (ui.Graphs - 1) - 1)
					.getFitness());
		}
		ui.updateGraph(scores);

		ui.setText(String.format("%s: %6s      %s: %.6f", "Generation",
				(n + 1), "Best Result", bestPath.getFitness()));
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * generate random paths to work
	 * 
	 * @return path
	 */
	protected Path getRandomPath() {
		Path path = new Path();
		for (int i = rand.nextInt(cityNumber); path.size() != cityNumber; i = rand
				.nextInt(cityNumber)) {
			ICoordinate iCoordinate = coordinates.get(i);
			if (!path.contains(iCoordinate)) {
				path.add(iCoordinate);
			}
		}
		return path;
	}
}
