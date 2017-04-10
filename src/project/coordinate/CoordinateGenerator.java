package project.coordinate;

import project.genetic.vo.coordinate.Coordinate;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Random;

import static project.common.Constants.TSP_FILE;

public class CoordinateGenerator {

    public static void main(String[] args) {
        Random rand = new Random();
        try {
            FileOutputStream fileOut = new FileOutputStream(TSP_FILE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            for (int i = 0; i < 5000; i++) {
                out.writeObject(Coordinate.getCoordinate((rand.nextInt(55) + 5) * 10, (rand.nextInt(55) + 5) * 10));
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
