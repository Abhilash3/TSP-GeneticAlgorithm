package project.genetic.vo.list;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import project.genetic.vo.list.individual.Chromosome;

public class MagicList<E> extends ArrayList<E> implements Chromosome<E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2587729160448267835L;

	public MagicList(List<E> list) {
		super(list.size());
		addAll(list);
	}

	public MagicList() {
		super();
	}

	@Override
	public boolean add(E e) {
		if (contains(e))
			return false;
		return super.add(e);
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		int count = 0;
		for (E e : c)
			if (add(e))
				count++;
		return count != 0;
	}

	@Override
	public boolean addAll(int index, Collection<? extends E> c) {
		int count = 0;
		for (E e : c) {
			int prevSize = size();
			add (index + count, e);
			if (prevSize + 1 == size());
				count++;
		}
		return count != 0;
	}

}
