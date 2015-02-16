package genetic.fitness;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import genetic.TSP;

public class Fitness {

	public double generateFitness(ArrayList<Integer> path) {
		double fitness = 0;
		for (int i = 1; i < TSP.CityNumber - 1; i++) {
			fitness += TSP.distanceMatrix.get(path.get(i - 1)).get(path.get(i));
		}
		return fitness + TSP.distanceMatrix.get(TSP.CityNumber - 1).get(0);
	}
	
	public ArrayList<ArrayList<Integer>> sortGeneration(ArrayList<ArrayList<Integer>> generation) {
		Collections.sort(generation, new FitnessComparator());
		return generation;
	}
	
	private class FitnessComparator implements Comparator<ArrayList<Integer>> {
		@Override
		public int compare(ArrayList<Integer> o1, ArrayList<Integer> o2) {
			return (int) (generateFitness(o1) - generateFitness(o2));
		}
	}

}
