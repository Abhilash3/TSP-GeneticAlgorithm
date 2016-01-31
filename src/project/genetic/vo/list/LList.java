package project.genetic.vo.list;

import java.util.List;

public interface LList<E> extends List<List<E>> {

	void getAdd(int i, E e);

	void addEmpty();

	int getSize(int i);

	E get(int i, int j);

}
