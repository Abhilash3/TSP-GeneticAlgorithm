package project.genetic;

import project.genetic.fitness.Fitness;
import project.genetic.process.Crossover;
import project.genetic.process.Mutation;
import project.genetic.process.Selection;
import project.genetic.vo.coordinate.Coordinate;
import project.genetic.vo.individual.Individual;
import project.genetic.vo.individual.gene.IGene;
import project.genetic.vo.list.ICloneableList;
import project.ui.UI;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static project.common.Constants.*;

/**
 * java class definition providing genetic capabilities
 *
 * @author ABHILASHKUMARV
 */
public class Genetic<E extends Individual<F>, F extends IGene> {

    private static Random rand = new Random();

    private int populationSize;

    private final List<Double> scores = new ArrayList<>(Graphs);

    private Class<E> clazz;
    private ICloneableList<Coordinate> coordinates;
    private E bestPath;

    private UI ui;

    private static final String Format = "%s: %6s      %s: %.6f";

    public Genetic(Class<E> clazz, ICloneableList<Coordinate> coordinates) {
        this.clazz = clazz;
        this.coordinates = coordinates;
        populationSize = coordinates.size() * 5;
    }

    public Genetic(Class<E> clazz, ICloneableList<Coordinate> coordinates, UI ui) {
        this(clazz, coordinates);
        this.ui = ui;
    }

    /**
     * simulate the genetic process
     *
     * @return the best path simulated by the process
     */
    public E simulate() {

        List<E> generation = new ArrayList<>(populationSize);
        List<E> newGeneration = new ArrayList<>(populationSize);

        populateFirstGeneration(generation);
        bestPath = Fitness.<E>newInstance().getFittest(generation);

        E parent1, parent2, child;
        for (int i = 1; i <= Generations; i++) {

            if (Elitism)
                newGeneration.add(bestPath);

            for (; newGeneration.size() != populationSize; ) {

                parent1 = select(generation);
                parent2 = select(generation, parent1);

                child = crossover(parent1, parent2);
                newGeneration.add(child);

                if (newGeneration.size() != populationSize && rand.nextInt(100) % 5 == 0) {
                    newGeneration.add(mutate(child));
                }

            }

            generation = new ArrayList<>(newGeneration);
            newGeneration = new ArrayList<>(populationSize);
            bestPath = Fitness.<E>newInstance().getFittest(generation);

            if (ui != null)
                updateUI(generation, i);
            else
                System.out.println(String.format(Format, "Generation", i, "Best Result", bestPath.getFitness()));
        }

        return bestPath;
    }

    private E mutate(E child) {
        E mutate;
        do {
            mutate = Mutation.newInstance(clazz).mutate(child);
        } while (child.equals(mutate));
        return mutate;
    }

    private E crossover(E parent1, E parent2) {
        return Crossover.newInstance(clazz).cross(parent1, parent2);
    }

    private E select(List<E> generation, E parent1) {
        E parent2;
        do {
            parent2 = select(generation);
        } while (parent2.equals(parent1));
        return parent2;
    }

    private E select(List<E> generation) {
        return Selection.<E, F>newInstance().select(generation);
    }

    private void populateFirstGeneration(List<E> generation) {
        while (generation.size() < populationSize) {
            generation.add(random());
        }
    }

    private E random() {
        Collections.shuffle(coordinates);
        try {
            return clazz.getConstructor(ICloneableList.class).newInstance(coordinates);
        } catch (InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void updateUI(List<E> generation, int n) {

        ui.clearMapLines();
        // this is a problem
        ui.drawMap((List<Coordinate>) bestPath.toList());

        scores.clear();
        scores.add(1 / bestPath.getFitness());
        for (int j = 1; j < Graphs; j++) {
            scores.add(1 / generation.get(j * populationSize / (Graphs - 1) - 1).getFitness());
        }
        ui.updateGraph(scores);

        ui.setText(String.format(Format, "Generation", n, "Best Result", 1 / bestPath.getFitness()));
    }
}
