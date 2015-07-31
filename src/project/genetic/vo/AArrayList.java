package project.genetic.vo;

import java.util.ArrayList;

/**
 * java class definition for ArrayList<ArrayList<E>
 * 
 * @author ABHILASHKUMARV
 * 
 * @param <E>
 */
@SuppressWarnings("serial")
public class AArrayList<E> extends ArrayList<ArrayList<E>> {

	/**
	 * method combining 2D get statements in one method
	 * 
	 * @param i
	 * @param j
	 * @return E
	 */
	public E get(int i, int j) {
		return this.get(i).get(j);
	}

}
