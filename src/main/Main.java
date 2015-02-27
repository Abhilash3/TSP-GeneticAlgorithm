package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import genetic.TSP;

public class Main {
	
	private static String TSP_FILE = "coordinates.txt";

    public static void main(String[] args) {

        ArrayList<ArrayList<Integer>> coordinates = new ArrayList<ArrayList<Integer>>();
        BufferedReader br = null;

        try {
        	br = new BufferedReader(new FileReader(new File(TSP_FILE)));
        	String line;
        	for(int i = 0; (line = br.readLine()) != null && i < TSP.CityNumber; i++) {
        		String[] split = line.split(" ");
        		coordinates.add(new ArrayList<Integer>());
        		if (split.length != 2) {
        			throw new Exception();
        		} else {
        			coordinates.get(i).add(Integer.parseInt(split[0].trim()));
        			coordinates.get(i).add(Integer.parseInt(split[1].trim()));
        		}
        	}
        	if (coordinates.size() != TSP.CityNumber) {
        		throw new Exception();
        	}
        } catch (Exception e) {
        	coordinates = new ArrayList<ArrayList<Integer>>();
        	for (int i = 0; i < TSP.CityNumber; i++) {
                coordinates.add(new ArrayList<Integer>());
                coordinates.get(i).add((TSP.rand.nextInt(55) + 5) * 10);
                coordinates.get(i).add((TSP.rand.nextInt(55) + 5) * 10);
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
