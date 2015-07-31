package project.genetic.crossover;

import java.util.ArrayList;
import java.util.Random;

import project.genetic.vo.DistanceMatrix;

/**
 * java class definition providing crossover capabilities
 * 
 * @author ABHILASHKUMARV
 * 
 */
public class Crossover {

	private int cityNumber;
	private DistanceMatrix distanceMatrix;
	private Random rand;

	public Crossover(int cityNumber, DistanceMatrix distanceMatrix) {
		this.cityNumber = cityNumber;
		this.distanceMatrix = distanceMatrix;
		this.rand = new Random();
	}

	/**
	 * method generating crossover options and generating child path
	 * 
	 * @param path1
	 * @param path2
	 * @return childPath
	 */
	public ArrayList<Integer> cross(ArrayList<Integer> path1,
			ArrayList<Integer> path2) {

		ArrayList<Integer> childPath;

		int random = rand.nextInt(99);
		if (random % 3 == 1) {
			childPath = crossover1(path1, path2);
		} else if (random % 3 == 2) {
			childPath = crossover2(path1, path2);
		} else {
			childPath = crossover3(path1, path2);
		}

		return childPath;
	}

	/**
	 * first city from path1, get nth city from both paths and select whichever
	 * is closer to n-1th city in child path
	 * 
	 * @param path1
	 * @param path2
	 * @return child path
	 */
	private ArrayList<Integer> crossover1(ArrayList<Integer> path1,
			ArrayList<Integer> path2) {

		ArrayList<Integer> path3 = new ArrayList<Integer>();
		path3.add(path1.get(0));

		for (Integer i = 1; i < this.cityNumber; i++) {

			Integer city1 = path1.get(i);
			Integer city2 = path2.get(i);

			if (!path3.contains(city1) && !path3.contains(city2)) {
				Double distance1 = this.distanceMatrix.get(path1.get(i - 1),
						city1);
				Double distance2 = this.distanceMatrix.get(path2.get(i - 1),
						city2);
				if (distance1 < distance2) {
					path3.add(city1);
				} else {
					path3.add(city2);
				}
			} else if (!path3.contains(city1)) {
				path3.add(city1);
			} else if (!path3.contains(city2)) {
				path3.add(city2);
			} else {
				for (Integer city : path1) {
					if (!path3.contains(city)) {
						path3.add(city);
						break;
					}
				}
			}
		}

		return path3;
	}

	/**
	 * selects first random no of cities from path1, rest from path 2
	 * 
	 * @param path1
	 * @param path2
	 * @return child path
	 */
	private ArrayList<Integer> crossover2(ArrayList<Integer> path1,
			ArrayList<Integer> path2) {

		ArrayList<Integer> path3 = new ArrayList<Integer>();

		int random = rand.nextInt(this.cityNumber - 1);

		for (int i = 0; i < random; i++) {
			path3.add(path1.get(i));
		}
		for (int i = random; path3.size() != this.cityNumber; i++) {
			if (!path3.contains(path2.get(i))) {
				path3.add(path2.get(i));
			} else {
				for (Integer city : path2) {
					if (!path3.contains(city)) {
						path3.add(city);
						break;
					}
				}
			}
		}

		return path3;
	}

	/**
	 * selects cities from both paths in whichever order they come with no
	 * duplication
	 * 
	 * @param path1
	 * @param path2
	 * @return
	 */
	private ArrayList<Integer> crossover3(ArrayList<Integer> path1,
			ArrayList<Integer> path2) {

		ArrayList<Integer> path3 = new ArrayList<Integer>();

		for (int i = 0; path3.size() != this.cityNumber; i++) {
			if (!path3.contains(path1.get(i))) {
				path3.add(path1.get(i));
			}
			if (!path3.contains(path2.get(i))) {
				path3.add(path2.get(i));
			}
		}

		return path3;
	}

}
