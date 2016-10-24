package project.tsp;

import static project.common.Constants.CityNumber;
import static project.common.Constants.TSP_FILE;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import project.genetic.Genetic;
import project.genetic.vo.coordinate.Coordinate;
import project.ui.UI;

public class TSP {

    private static Random rand = new Random();

    public static void main(String[] args) {

        List<Coordinate> coordinates = new ArrayList<Coordinate>();
        FileInputStream fileIn;
        ObjectInputStream in;
        try {
            fileIn = new FileInputStream(TSP_FILE);
            in = new ObjectInputStream(fileIn);

            Coordinate city;
            for (; coordinates.size() < CityNumber; ) {
                city = (Coordinate) in.readObject();
                if (!coordinates.contains(city))
                    coordinates.add(city);
            }
            in.close();
            fileIn.close();
        } catch (Exception e) {
            e.printStackTrace();
            coordinates = new ArrayList<Coordinate>();
            for (; coordinates.size() < CityNumber; ) {
                coordinates.add(Coordinate.getCoordinate(
                        (rand.nextInt(55) + 5) * 10,
                        (rand.nextInt(55) + 5) * 10));
            }
        }

        new Genetic(coordinates, new UI(coordinates)).simulate();

    }
}
