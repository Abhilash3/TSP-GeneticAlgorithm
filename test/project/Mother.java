package project;

import static project.common.Constants.CityNumber;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import project.genetic.vo.ICloneable;
import project.genetic.vo.coordinate.Coordinate;
import project.genetic.vo.individual.Individual;
import project.genetic.vo.individual.Route;
import project.genetic.vo.individual.gene.IGene;
import project.genetic.vo.list.CloneableList;
import project.genetic.vo.list.ICloneableList;
import project.genetic.vo.list.decorator.NoDuplicateListDecorator;

public class Mother {

    private Mother() {
        throw new UnsupportedOperationException("Util Class");
    }

    private static int Cities = CityNumber;
    private static int Population = 2 * Cities;

    private static Random rand = new Random();

    public static List<Individual<Coordinate>> getGeneration() {
        List<Individual<Coordinate>> generation = new ArrayList<>();
        for (int i = 0; i < Population; i++) {
            generation.add(getPath());
        }
        return generation;
    }

    public static Route getPath() {
        return new Route(getCoordinates());
    }

    @SuppressWarnings("unchecked")
    public static Individual getIndividualWithFitness(final double fitness) {
        return new Individual(new CloneableList()) {
            @Override
            public Individual<IGene> doClone() {
                return this;
            }

            @Override
            public double getFitness() {
                return fitness;
            }

            @Override
            public double fitness(IGene a, IGene b) {
                return 0;
            }
        };
    }

    public static ICloneableList<Coordinate> getCoordinates() {
        ICloneableList<Coordinate> list = new NoDuplicateListDecorator<>(new CloneableList<Coordinate>());
        while (list.size() != Cities) {
            list.add(getCoordinate());
        }
        return list;
    }

    public static Coordinate getCoordinate() {
        return Coordinate.newCoordinate((rand.nextInt(55) + 5) * 10, (rand.nextInt(55) + 5) * 10);
    }

}
