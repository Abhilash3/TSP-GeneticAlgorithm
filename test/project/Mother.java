package project;

import static project.common.Constants.CityNumber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import project.genetic.vo.coordinate.Coordinate;
import project.genetic.vo.coordinate.ICoordinate;
import project.genetic.vo.list.MagicList;
import project.genetic.vo.list.individual.Individual;
import project.genetic.vo.list.individual.Path;

public class Mother {

    private Mother() {
    }

    protected static int Cities = CityNumber;
    protected static int Population = 2 * Cities;

    protected static Random rand = new Random();

    public static List<Individual<ICoordinate>> getGeneration() {
        List<Individual<ICoordinate>> generation = new ArrayList<Individual<ICoordinate>>();
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
        return new Individual(Collections.EMPTY_LIST) {
            @Override
            protected double fitness() {
                return fitness;
            }

            @Override
            public Individual doClone() {
                return this;
            }
        };
    }

    public static List<ICoordinate> getCoordinates() {
        List<ICoordinate> list = new MagicList<ICoordinate>();
        for (; list.size() != Cities; ) {
            list.add(getCoordinate());
        }
        return list;
    }

    public static Coordinate getCoordinate() {
        return Coordinate.getCoordinate((rand.nextInt(55) + 5) * 10, (rand.nextInt(55) + 5) * 10);
    }

}
