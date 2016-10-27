package project.genetic.fitness;

import java.util.ArrayList;
import java.util.List;

import project.genetic.vo.Cloneable;
import project.genetic.vo.list.individual.Individual;

/**
 * java class definition for sorting path
 *
 * @author ABHILASHKUMARV
 */
public class Fitness<T extends Individual<? extends Cloneable>> {

    private Fitness() {
    }

    public static <E extends Individual<? extends Cloneable>> Fitness<E> getInstance() {
        return new Fitness<E>();
    }

    /**
     * order to sort list
     */
    private enum Order {
        ASC,
        DESC(-1);

        private int order;

        Order() {
            this(1);
        }

        Order(int order) {
            this.order = order;
        }

        @SuppressWarnings("rawtypes")
        public int compare(Individual a, Individual b) {
            return a.compareTo(b) * order;
        }
    }

    public interface Strategy<T> {
        /**
         * method generating sorting options and sorted path
         *
         * @param list  list of objects
         * @param order order in which to sort
         */
        void sort(List<T> list, Order order);
    }

    protected Strategy<T> getMergeSortStrategy() {

        return new Strategy<T>() {
            private Order order;
            private List<T> first, second;

            /**
             * MergeSort
             *
             * @param list list of objects
             * @param order order in which to sort
             */
            @Override
            public void sort(List<T> list, Order order) {
                this.order = order;
                mergeSort(list, 0, list.size() - 1);
            }

            private void mergeSort(List<T> list, int low, int high) {
                if (low < high) {
                    int mid = (high + low) / 2;
                    mergeSort(list, low, mid);
                    mergeSort(list, mid + 1, high);
                    merge(list, low, high);
                }
            }

            private void merge(List<T> list, int low, int high) {
                int mid = (high + low) / 2;

                first = new ArrayList<T>(list.subList(low, mid + 1));
                second = new ArrayList<T>(list.subList(mid + 1, high + 1));

                for (int i = 0, j = 0, k = low; k < high + 1; k++) {
                    if (i < first.size() && j < second.size()) {
                        if (order.compare(first.get(i), second.get(j)) == -1) {
                            list.set(k, second.get(j++));
                        } else {
                            list.set(k, first.get(i++));
                        }
                    } else if (i < first.size()) {
                        list.set(k, first.get(i++));
                    } else {
                        list.set(k, second.get(j++));
                    }
                }
            }
        };
    }

    public T getFittest(List<T> generation) {
        return sort(generation).get(0);
    }

    public List<T> sort(List<T> generation) {
        return sort(generation, getMergeSortStrategy());
    }

    public List<T> sort(List<T> generation, Strategy<T> strategy) {
        strategy.sort(generation, Order.ASC);
        return generation;
    }
}
