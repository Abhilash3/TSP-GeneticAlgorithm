package project;

import static project.common.Constants.CityNumber;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import project.genetic.vo.Cloneable;
import project.genetic.vo.coordinate.Coordinate;
import project.genetic.vo.list.NoDuplicateList;
import project.genetic.vo.individual.Individual;
import project.genetic.vo.individual.Route;

public class Mother {

    private Mother() {
        throw new UnsupportedOperationException("Util Class");
    }

    private static int Cities = CityNumber;
    private static int Population = 2 * Cities;

    private static Random rand = new Random();

    public static List<Individual<Coordinate>> getGeneration() {
        List<Individual<Coordinate>> generation = new ArrayList<Individual<Coordinate>>();
        for (int i = 0; i < Population; i++) {
            generation.add(getPath());
        }
        return generation;
    }

    public static Route getPath() {
        return new Route(getCoordinates());
    }

    @SuppressWarnings("unchecked")
    public static Individual getIndividualWithFitness(final double myFitness) {
        return new Individual(Collections.EMPTY_LIST) {
            @Override
            public Individual<Cloneable> doClone() {
                return this;
            }

            @Override
            public double getFitness() {
                return myFitness;
            }
        };
    }

    public static List<Coordinate> getCoordinates() {
        List<Coordinate> list = new NoDuplicateList<Coordinate>();
        for (; list.size() != Cities; ) {
            list.add(getCoordinate());
        }
        return list;
    }

    public static Coordinate getCoordinate() {
        return Coordinate.getCoordinate((rand.nextInt(55) + 5) * 10, (rand.nextInt(55) + 5) * 10);
    }

}
