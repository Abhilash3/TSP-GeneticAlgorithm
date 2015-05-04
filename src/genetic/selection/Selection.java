package genetic.selection;

import java.util.ArrayList;

import genetic.TSP;
import genetic.fitness.Fitness;

public class Selection {

    private static Fitness fitness = new Fitness();

    public ArrayList<Integer> select(ArrayList<ArrayList<Integer>> generation) {
        ArrayList<Integer> selectedPath = null;

        int random = TSP.rand.nextInt(99);
        if (random % 2 == 0) {
            selectedPath = selection1(generation);
        } else {
            selectedPath = selection2(generation);
        }

        return selectedPath;
    }

    private ArrayList<Integer> selection1(ArrayList<ArrayList<Integer>> generation) {

        double generationFitness = 0;
        ArrayList<Integer> selectedPath = null;
        double max = 0;

        for(ArrayList<Integer> path : generation) {
            if (max < fitness.generateFitness(path)) {
                max = fitness.generateFitness(path);
            }
        }

        for(ArrayList<Integer> path : generation) {
            generationFitness += max - fitness.generateFitness(path) + 1;
        }

        int random = TSP.rand.nextInt((int) generationFitness);

        generationFitness = 0;
        for(ArrayList<Integer> path : generation) {
            generationFitness += max - fitness.generateFitness(path) + 1;
            if (generationFitness >= random) {
                selectedPath = path;
                break;
            }
        }

        return selectedPath;
    }

    private ArrayList<Integer> selection2(ArrayList<ArrayList<Integer>> generation) {

        int random = TSP.rand.nextInt(TSP.PopulationSize) + 1;

        ArrayList<ArrayList<Integer>> pool = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> selectedList = new ArrayList<Integer>();
        for(int i = 0; i < random; i++) {
            int randomPosition = TSP.rand.nextInt(TSP.PopulationSize);
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
