package project.genetic.mutation;

import java.util.ArrayList;
import java.util.Random;

/**
 * java class definition providing mutation capabilities
 * 
 * @author ABHILASHKUMARV
 * 
 */
public class Mutation {

	private int cityNumber;
	private Random rand;

	public Mutation(int cityNumber) {
		this.cityNumber = cityNumber;
		this.rand = new Random();
	}

	/**
	 * method generating mutation options and mutating path
	 * 
	 * @param path
	 * @return mutated path
	 */
	public ArrayList<Integer> mutate(ArrayList<Integer> path) {

		ArrayList<Integer> mutatedPath;

		int random = rand.nextInt(99);
		if (random % 4 == 0) {
			mutatedPath = mutation1(path);
		} else if (random % 4 == 1) {
			mutatedPath = mutation2(path);
		} else if (random % 4 == 2) {
			mutatedPath = mutation3(path);
		} else {
			mutatedPath = mutation4(path);
		}

		return mutatedPath;
	}

	/**
	 * [0,1,2,3,4,5,6,7,8,9] --> [9,8,7,6,5,4,3,2,1,0]
	 * 
	 * @param path
	 * @return path
	 */
	private ArrayList<Integer> mutation1(ArrayList<Integer> path) {

		for (int i = 0, j = this.cityNumber - 2; i < j; i++, j--) {
			Integer temp = path.get(i);
			path.set(i, path.get(j));
			path.set(j, temp);
		}

		return path;
	}

	/**
	 * [0,1,2,3,4,5,6,7,8,9] --> 5 --> [5,6,7,8,9,0,1,2,3,4]
	 * 
	 * @param path
	 * @return path
	 */
	private ArrayList<Integer> mutation2(ArrayList<Integer> path) {

		int random = rand.nextInt(this.cityNumber - 1) + 1;

		ArrayList<Integer> subPath = new ArrayList<Integer>(path.subList(0,
				random));
		path.removeAll(subPath);
		path.addAll(path.size(), subPath);

		return path;
	}

	/**
	 * [0,1,2,3,4,5,6,7,8,9] --> [0,2,1,4,3,6,5,8,7,9]
	 * 
	 * @param path
	 * @return path
	 */
	private ArrayList<Integer> mutation3(ArrayList<Integer> path) {

		for (int i = 0; i < this.cityNumber - 1; i += 2) {
			Integer temp = path.get(i);
			path.set(i, path.get(i + 1));
			path.set(i + 1, temp);
		}

		return path;
	}

	/**
	 * [0,1,2,3,4,5,6,7,8,9] --> 4, 6 --> [0,1,2,3,4,7,6,5,8,9]
	 * 
	 * @param path
	 * @return path
	 */
	private ArrayList<Integer> mutation4(ArrayList<Integer> path) {

		int random1 = rand.nextInt(this.cityNumber - 1);
		int random2 = rand.nextInt(this.cityNumber - 1);

		for (int i = Math.min(random1, random2), j = Math.max(random1, random2); i < j; i++, j--) {
			Integer temp = path.get(i);
			path.set(i, path.get(j));
			path.set(j, temp);
		}

		return path;
	}

}
