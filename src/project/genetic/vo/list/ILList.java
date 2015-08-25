package project.genetic.vo.list;

import java.util.List;

public interface ILList<E> extends List<List<E>> {

	public void getAdd(int i, E e);

	public void addEmpty();

	public int getSize(int i);

	public E get(int i, int j);

}
