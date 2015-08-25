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
@SuppressWarnings("serial")
public class AArrayList<E> extends ArrayList<List<E>> implements ILList<E> {

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
