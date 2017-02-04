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
import project.genetic.vo.coordinate.Coordinate;
import project.genetic.vo.list.individual.Individual;
import project.genetic.vo.list.individual.Route;
import project.ui.UI;

/**
 * java class definition providing genetic capabilities
 *
 * @author ABHILASHKUMARV
 */
public class Genetic {

    protected static Random rand = new Random();

    private int populationSize;

    protected final List<Double> scores = new ArrayList<Double>(Graphs);

    protected List<Coordinate> coordinates;
    protected Individual<Coordinate> bestPath;

    private UI ui;

    private static final String Format = "%s: %6s      %s: %.6f";

    public Genetic(List<Coordinate> coordinates) {
        this.coordinates = coordinates;
        populationSize = coordinates.size() * 5;
    }

    public Genetic(List<Coordinate> coordinates, UI ui) {
        this(coordinates);
        this.ui = ui;
    }

    /**
     * simulate the genetic process
     *
     * @return
     */
    public Individual<Coordinate> simulate() {

        List<Individual<Coordinate>> generation = new ArrayList<Individual<Coordinate>>(populationSize);
        List<Individual<Coordinate>> newGeneration = new ArrayList<Individual<Coordinate>>(populationSize);

        populateFirstGeneration(generation);
        bestPath = Fitness.<Individual<Coordinate>>getInstance().getFittest(generation);

        Individual<Coordinate> parent1, parent2, child;
        for (int i = 1; i <= Generations; i++) {

            if (Elitism)
                newGeneration.add(bestPath);

            for (; newGeneration.size() != populationSize; ) {

                parent1 = select(generation);
                parent2 = select(generation, parent1);

                child = crossover(parent1, parent2);
                newGeneration.add(child);

                if (newGeneration.size() != populationSize && rand.nextInt(100) % 5 == 0) {
                    child = mutate(child);
                    newGeneration.add(child);
                }

            }

            generation = new ArrayList<Individual<Coordinate>>(newGeneration);
            newGeneration = new ArrayList<Individual<Coordinate>>(populationSize);
            bestPath = Fitness.<Individual<Coordinate>>getInstance().getFittest(generation);

            if (ui != null)
                updateUI(generation, i);
            else
                System.out.println(String.format(Format, "Generation", i, "Best Result", bestPath.getFitness()));
        }

        return bestPath;
    }

    private Individual<Coordinate> mutate(Individual<Coordinate> child) {
        Individual<Coordinate> mutate;
        do {
            mutate = Mutation.<Individual<Coordinate>>getInstance(Route.class).mutate(child);
        } while (child.equals(mutate));
        return mutate;
    }

    private Individual<Coordinate> crossover(Individual<Coordinate> parent1,
                                              Individual<Coordinate> parent2) {
        return Crossover.<Individual<Coordinate>>getInstance(Route.class).cross(parent1, parent2);
    }

    private Individual<Coordinate> select(List<Individual<Coordinate>> generation,
                                           Individual<Coordinate> parent1) {
        Individual<Coordinate> parent2;
        do {
            parent2 = select(generation);
        } while (parent2.equals(parent1));
        return parent2;
    }

    private Individual<Coordinate> select(List<Individual<Coordinate>> generation) {
        return Selection.<Individual<Coordinate>>getInstance().select(generation);
    }

    private void populateFirstGeneration(List<Individual<Coordinate>> generation) {
        for (int i = 0; i < populationSize; i++) {
            generation.add(randomPath());
        }
    }

    protected Individual<Coordinate> randomPath() {
        Collections.shuffle(coordinates);
        return new Route(coordinates);
    }

    private void updateUI(List<Individual<Coordinate>> generation, int n) {

        ui.clearMapLines();
        ui.drawMap(bestPath);

        scores.clear();
        scores.add(1 / bestPath.getFitness());
        for (int j = 1; j < Graphs; j++) {
            scores.add(1 / generation.get(j * populationSize / (Graphs - 1) - 1).getFitness());
        }
        ui.updateGraph(scores);

        ui.setText(String.format(Format, "Generation", n, "Best Result", 1 / bestPath.getFitness()));
    }
}
