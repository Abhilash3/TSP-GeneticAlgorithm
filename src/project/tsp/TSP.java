package project.tsp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import project.genetic.Genetic;
import project.genetic.vo.City;

public class TSP {

	public final static int CityNumber = 50;
	private static String TSP_FILE = "coordinates.txt";
	private static Random rand = new Random();

	public static void main(String[] args) {

		ArrayList<City> coordinates = new ArrayList<City>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(new File(TSP_FILE)));
			String line;
			for (int i = 0; (line = br.readLine()) != null && i < CityNumber; i++) {
				String[] split = line.split(" ");
				if (split.length != 2) {
					throw new Exception();
				} else {
					City city = new City(Integer.parseInt(split[0].trim()),
							Integer.parseInt(split[1].trim()));
					coordinates.add(city);
					System.out.println("City " + i + ": " + city);
				}
			}
			if (coordinates.size() != CityNumber) {
				throw new Exception(
						"Coordinates are not equal to required city number");
			}
		} catch (Exception e) {
			coordinates = new ArrayList<City>();
			for (int i = 0; i < CityNumber; i++) {
				coordinates.add(new City((rand.nextInt(55) + 5) * 10, (rand
						.nextInt(55) + 5) * 10));
			}
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		new Genetic(coordinates).simulate();

	}
}