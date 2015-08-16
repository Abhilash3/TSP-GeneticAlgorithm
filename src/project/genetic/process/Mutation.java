package project.genetic.process;

import java.util.ArrayList;
import java.util.Random;

import project.genetic.vo.IGene;
import project.genetic.vo.Path;
import project.genetic.vo.coordinate.ICoordinate;

/**
 * java class definition providing mutation capabilities
 * 
 * @author ABHILASHKUMARV
 * 
 */
public class Mutation {

	private Random rand;

	private ArrayList<Choice> choices;
	private int size;

	public interface Choice {

		/**
		 * method generating mutation options and mutating path
		 * 
		 * @param path
		 * @return mutated path
		 */
		public IGene<ICoordinate> mutate(IGene<ICoordinate> path);
	}

	public Mutation() {
		this.rand = new Random();

		prepareChoices();
	}

	private void prepareChoices() {
		choices = new ArrayList<Choice>();

		choices.add(new Choice() {

			/**
			 * [0,1,2,3,4,5,6,7,8,9] --> [9,8,7,6,5,4,3,2,1,0]
			 * 
			 * @param path
			 * @return path
			 */
			@Override
			public IGene<ICoordinate> mutate(IGene<ICoordinate> path) {

				for (int i = 0, j = path.size() - 2; i < j; i++, j--) {
					ICoordinate temp = path.get(i);
					path.set(i, path.get(j));
					path.set(j, temp);
				}
				return path;
			}
		});

		choices.add(new Choice() {

			/**
			 * [0,1,2,3,4,5,6,7,8,9] --> 5 --> [5,6,7,8,9,0,1,2,3,4]
			 * 
			 * @param path
			 * @return path
			 */
			@Override
			public IGene<ICoordinate> mutate(IGene<ICoordinate> path) {

				int random = rand.nextInt(path.size() - 1) + 1;

				Path subPath = new Path();
				subPath.addAll(path.subList(0, random));
				path.removeAll(subPath);
				path.addAll(path.size(), subPath);

				return path;
			}
		});

		choices.add(new Choice() {

			/**
			 * [0,1,2,3,4,5,6,7,8,9] --> [0,2,1,4,3,6,5,8,7,9]
			 * 
			 * @param path
			 * @return path
			 */
			@Override
			public IGene<ICoordinate> mutate(IGene<ICoordinate> path) {

				for (int i = 0; i < path.size() - 1; i += 2) {
					ICoordinate temp = path.get(i);
					path.set(i, path.get(i + 1));
					path.set(i + 1, temp);
				}

				return path;
			}
		});

		choices.add(new Choice() {

			/**
			 * [0,1,2,3,4,5,6,7,8,9] --> 4, 6 --> [0,1,2,3,4,7,6,5,8,9]
			 * 
			 * @param path
			 * @return path
			 */
			@Override
			public IGene<ICoordinate> mutate(IGene<ICoordinate> path) {

				int random1 = rand.nextInt(path.size() - 1);
				int random2 = rand.nextInt(path.size() - 1);

				for (int i = Math.min(random1, random2), j = Math.max(random1,
						random2); i < j; i++, j--) {
					ICoordinate temp = path.get(i);
					path.set(i, path.get(j));
					path.set(j, temp);
				}

				return path;
			}
		});

		size = choices.size();
	}

	public Choice getChoice() {
		return choices.get(rand.nextInt(size));
	}

	public IGene<ICoordinate> mutate(IGene<ICoordinate> path) {
		return getChoice().mutate(path);
	}

}