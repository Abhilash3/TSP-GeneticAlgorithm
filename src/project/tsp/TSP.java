package project.tsp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import project.genetic.Genetic;
import project.genetic.vo.coordinate.City;
import project.genetic.vo.coordinate.ICoordinate;
import project.genetic.vo.list.Path;
import project.ui.UI;

public class TSP {

	public final static int CityNumber = 30;
	private static String TSP_FILE = "coordinates.txt";
	private static Random rand = new Random();

	public static void main(String[] args) {

		List<ICoordinate> coordinates = new ArrayList<ICoordinate>();
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(new File(TSP_FILE)));
			String line;
			for (int i = 0; (line = br.readLine()) != null && i < CityNumber; i++) {
				String[] split = line.split(" ");
				ICoordinate city = new City(Integer.parseInt(split[0].trim()),
						Integer.parseInt(split[1].trim()));
				if (!coordinates.contains(city))
					coordinates.add(city);
			}
			br.close();
			if (coordinates.size() > CityNumber) {
				System.out
						.println("Coordinates are not equal to required city number");
				for (; coordinates.size() < CityNumber;) {
					coordinates.add(new City((rand.nextInt(55) + 5) * 10, (rand
							.nextInt(55) + 5) * 10));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		UI ui = new UI(coordinates);
		Genetic genetic = new Genetic(coordinates, Path.class, ui);
		genetic.simulate();

	}
}
