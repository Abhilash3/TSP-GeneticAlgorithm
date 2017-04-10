package project.tsp;

import project.genetic.Genetic;
import project.genetic.vo.coordinate.Coordinate;
import project.genetic.vo.individual.Route;
import project.genetic.vo.list.CloneableList;
import project.genetic.vo.list.ICloneableList;
import project.genetic.vo.list.decorator.NoDuplicateListDecorator;
import project.ui.UI;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Random;

import static project.common.Constants.CityNumber;
import static project.common.Constants.TSP_FILE;

public class TSP {

    private static Random rand = new Random();

    public static void main(String[] args) {

        ICloneableList<Coordinate> coordinates = new NoDuplicateListDecorator<>(new CloneableList<Coordinate>());
        try {
            FileInputStream fileIn = new FileInputStream(TSP_FILE);
            ObjectInputStream in = new ObjectInputStream(fileIn);

            while (coordinates.size() < CityNumber) {
                coordinates.add((Coordinate) in.readObject());
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            coordinates = new NoDuplicateListDecorator<>(new CloneableList<Coordinate>());
            while (coordinates.size() < CityNumber) {
                coordinates.add(Coordinate.getCoordinate((rand.nextInt(55) + 5) * 10, (rand.nextInt(55) + 5) * 10));
            }
        }

        new Genetic<>(Route.class, coordinates, new UI(coordinates)).simulate();

    }
}
