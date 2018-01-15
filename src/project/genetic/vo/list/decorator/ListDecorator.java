package project.genetic.vo.list.decorator;

import project.genetic.vo.ICloneable;
import project.genetic.vo.list.CloneableList;
import project.genetic.vo.list.ICloneableList;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import static java.lang.String.format;

public class ListDecorator<E extends ICloneable> implements ICloneableList<E> {

    protected CloneableList<E> list;

    ListDecorator(CloneableList<E> list) {
        this.list = list;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public final Iterator<E> iterator() {
        return iterator(list.iterator());
    }

    protected Iterator<E> iterator(final Iterator<E> iterator) {
        return iterator;
    }

    @Override
    public Object[] toArray() {
        return processArray(list.toArray());
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return processArray(list.toArray(a));
    }

    @SuppressWarnings("unchecked")
    protected <T> T[] processArray(T[] arr) {
        return arr;
    }

    @Override
    public boolean add(E e) {
        return list.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return list.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return list.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return list.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        return list.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return list.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return list.retainAll(c);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public E get(int index) {
        return list.get(index);
    }

    @Override
    public E set(int index, E element) {
        return list.set(index, element);
    }

    @Override
    public void add(int index, E element) {
        list.add(index, element);
    }

    @Override
    public E remove(int index) {
        return list.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    @Override
    public final ListIterator<E> listIterator() {
        return listIterator(list.listIterator());
    }

    @Override
    public final ListIterator<E> listIterator(int index) {
        return listIterator(list.listIterator(index));
    }

    protected ListIterator<E> listIterator(final ListIterator<E> iterator) {
        return iterator;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return list.subList(fromIndex, toIndex);
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object object) {
        return object != null && (this == object || object.getClass() == getClass()
                && list.equals(((ListDecorator<E>) object).list));
    }

    @Override
    public String toString() {
        return format("%s [ %s ]", getClass().getSimpleName(), list.toString());
    }

    @SuppressWarnings("unchecked")
    @Override
    public ListDecorator<E> doClone() {
        return new ListDecorator<>(list.doClone());
    }
}
