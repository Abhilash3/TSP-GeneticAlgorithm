package project.genetic.vo.list;

import java.util.ArrayList;
import java.util.List;

/**
 * java class definition for ArrayList<ArrayList<E>
 * 
 * @author ABHILASHKUMARV
 * 
 * @param <E>
 */
public class AArrayList<E> extends ArrayList<List<E>> implements LList<E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6899383745320325588L;

	@Override
	public E get(int i, int j) {
		return this.get(i).get(j);
	}

	@Override
	public void getAdd(int i, E e) {
		this.get(i).add(e);
	}

	@Override
	public void addEmpty() {
		this.add(new ArrayList<E>());
	}

	@Override
	public int getSize(int i) {
		return this.get(i).size();
	}

}
