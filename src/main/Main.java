package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import genetic.TSP;
import gui.vo.City;

public class Main {

    private static String TSP_FILE = "coordinates.txt";
    
    public static void main(String[] args) {
        
        ArrayList<City> coordinates = new ArrayList<City>();
        BufferedReader br = null;
        
        try {
            br = new BufferedReader(new FileReader(new File(TSP_FILE)));
            String line;
            for (int i = 0; (line = br.readLine()) != null
                    && i < TSP.CityNumber; i++) {
                String[] split = line.split(" ");
                if (split.length != 2) {
                    throw new Exception();
                } else {
                    coordinates.add(new City(Integer.parseInt(split[0].trim()),
                            Integer.parseInt(split[1].trim())));
                }
            }
            if (coordinates.size() != TSP.CityNumber) {
                throw new Exception();
            }
        } catch (Exception e) {
            e.printStackTrace();
            coordinates = new ArrayList<City>();
            for (int i = 0; i < TSP.CityNumber; i++) {
                coordinates.add(new City((TSP.rand.nextInt(55) + 5) * 10,
                        (TSP.rand.nextInt(55) + 5) * 10));
            }
        } finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    
        new TSP(coordinates).simulate();
    }
}