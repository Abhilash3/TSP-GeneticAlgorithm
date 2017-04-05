package project.tsp;

import static project.common.Constants.CityNumber;
import static project.common.Constants.TSP_FILE;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.List;
import java.util.Random;

import project.genetic.Genetic;
import project.genetic.vo.coordinate.Coordinate;
import project.genetic.vo.list.NoDuplicateList;
import project.ui.UI;

public class TSP {

    private static Random rand = new Random();

    public static void main(String[] args) {

        List<Coordinate> coordinates = new NoDuplicateList<>();
        try {
            FileInputStream fileIn = new FileInputStream(TSP_FILE);
            ObjectInputStream in = new ObjectInputStream(fileIn);

            while (coordinates.size() < CityNumber) {
                coordinates.add((Coordinate) in.readObject());
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
            coordinates = new NoDuplicateList<>();
            while (coordinates.size() < CityNumber) {
                coordinates.add(Coordinate.getCoordinate((rand.nextInt(55) + 5) * 10, (rand.nextInt(55) + 5) * 10));
            }
        }

        new Genetic(coordinates, new UI(coordinates)).simulate();

    }
}
