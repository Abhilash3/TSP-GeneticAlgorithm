package project.genetic.fitness;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import project.genetic.vo.AArrayList;
import project.genetic.vo.DistanceMatrix;

/**
 * java class definition for generating fitness for a path
 * 
 * @author ABHILASHKUMARV
 * 
 */
public class Fitness {

	private int cityNumber;
	private DistanceMatrix distanceMatrix;
	private boolean isHamiltonian;

	public Fitness(int cityNumber, DistanceMatrix distanceMatrix,
			boolean isHamiltonian) {
		this.cityNumber = cityNumber;
		this.distanceMatrix = distanceMatrix;
		this.isHamiltonian = isHamiltonian;
	}

	/**
	 * selects which type of fitness to generate
	 * 
	 * @param path
	 * @return fitness
	 */
	public double generateFitness(ArrayList<Integer> path) {
		double fitness;
		if (isHamiltonian) {
			fitness = generateHamiltonianFitness(path);
		} else {
			fitness = generateCircularFitness(path);
		}
		return fitness;
	}

	/**
	 * sorts the generation base on the comparator
	 * 
	 * @param generation
	 * @return sorted generation
	 */
	public AArrayList<Integer> sortGeneration(AArrayList<Integer> generation) {
		Collections.sort(generation, this.getComparator());
		return generation;
	}

	/**
	 * generates fitness from the start location to end location
	 * 
	 * @param path
	 * @return hamiltonian fitness
	 */
	protected Double generateHamiltonianFitness(ArrayList<Integer> path) {
		Double fitness = (double) 0;
		for (int i = 1; i < this.cityNumber - 1; i++) {
			fitness += this.distanceMatrix.get(path.get(i - 1), path.get(i));
		}
		return fitness;
	}

	/**
	 * generates fitness from start location to end location and then coming
	 * back to start location
	 * 
	 * @param path
	 * @return circular fitness
	 */
	protected Double generateCircularFitness(ArrayList<Integer> path) {
		return generateHamiltonianFitness(path)
				+ this.distanceMatrix.get(path.get(this.cityNumber - 1),
						path.get(0));
	}

	/**
	 * get the comparator required to sort the generation
	 * 
	 * @return comparator
	 */
	protected Comparator<ArrayList<Integer>> getComparator() {
		return new Comparator<ArrayList<Integer>>() {
			@Override
			public int compare(ArrayList<Integer> o1, ArrayList<Integer> o2) {
				return (int) ((generateFitness(o1) - generateFitness(o2)) * 1000);
			}
		};
	}

}
