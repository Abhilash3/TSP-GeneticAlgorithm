package project;

import static project.common.Constants.CityNumber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import project.genetic.vo.Cloneable;
import project.genetic.vo.coordinate.Coordinate;
import project.genetic.vo.list.MagicList;
import project.genetic.vo.list.individual.Individual;
import project.genetic.vo.list.individual.Path;

public class Mother {

    private Mother() {
    }

    protected static int Cities = CityNumber;
    protected static int Population = 2 * Cities;

    protected static Random rand = new Random();

    public static List<Individual<Coordinate>> getGeneration() {
        List<Individual<Coordinate>> generation = new ArrayList<Individual<Coordinate>>();
        for (int i = 0; i < Population; i++) {
            generation.add(getPath());
        }
        return generation;
    }

    public static Path getPath() {
        return new Path(getCoordinates());
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Individual getIndividualWithFitness(final double fitness) {
        return new Individual<Cloneable>(Collections.EMPTY_LIST) {
            @Override
            public Individual<Cloneable> doClone() {
                return this;
            }

            @Override
            public double getFitness() {
                return fitness;
            }

            @Override
            protected double fitness(Cloneable e1, Cloneable e2) {
                return 0;
            }
        };
    }

    public static List<Coordinate> getCoordinates() {
        List<Coordinate> list = new MagicList<Coordinate>();
        for (; list.size() != Cities; ) {
            list.add(getCoordinate());
        }
        return list;
    }

    public static Coordinate getCoordinate() {
        return Coordinate.getCoordinate((rand.nextInt(55) + 5) * 10, (rand.nextInt(55) + 5) * 10);
    }

}
