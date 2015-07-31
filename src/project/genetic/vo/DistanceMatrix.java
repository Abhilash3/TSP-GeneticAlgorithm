package project.genetic.vo;

import java.util.ArrayList;

/**
 * java class definition for distance matrix
 * @author ABHILASHKUMARV
 *
 */
@SuppressWarnings("serial")
public class DistanceMatrix extends AArrayList<Double> {
	
	private ArrayList<City> coordinates;
	private int cityNumber;
	
	public DistanceMatrix(ArrayList<City> coordinates) {
		this.coordinates = coordinates;
		this.cityNumber = coordinates.size();
		this.generate();
	}

	public Double get(int i, int j) {
		if (i == j) {
			return (double) 0;
		} else if (i > j) {
			return super.get(i, j);
		} else {
			return super.get(j, i);
		}
	}
	
	/**
	 * method generating the distance matrix based on coordinates passed
	 */
	public void generate() {
		for (int i = 0; i < cityNumber; i++) {
			this.add(new ArrayList<Double>());
			for(int j = 0; j < i; j++) {
				this.get(i).add(coordinates.get(i).distanceFrom(coordinates.get(j)));
			}
		}		
	}
	
}
