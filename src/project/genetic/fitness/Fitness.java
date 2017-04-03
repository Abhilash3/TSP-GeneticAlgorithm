package project.genetic.fitness;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import project.genetic.vo.Cloneable;
import project.genetic.vo.individual.Individual;

/**
 * java class definition for sorting path
 *
 * @author ABHILASHKUMARV
 */
public class Fitness<T extends Individual<? extends Cloneable>> {

    private Fitness() {
    }

    public static <E extends Individual<? extends Cloneable>> Fitness<E> getInstance() {
        return new Fitness<>();
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

    protected Strategy<T> getQuickSortStrategy() {

        return new Strategy<T>() {
            private Order order;

            @Override
            public void sort(List<T> list, Order order) {
                this.order = order;
                quickSort(list, 0, list.size() - 1);
            }

            private void quickSort(List<T> list, int min, int max) {
                T pivot = list.get((max + min) / 2);

                int i = min, j = max;
                while (i <= j) {
                    while(order.compare(pivot, list.get(i)) < 0) {
                        i++;
                    }
                    while(order.compare(pivot, list.get(j)) > 0) {
                        j--;
                    }

                    if (i <= j) {
                        T temp = list.get(i);
                        list.set(i++, list.get(j));
                        list.set(j--, temp);
                    }
                }
                if (min < j) {
                    quickSort(list, min, j);
                }
                if (i < max) {
                    quickSort(list, i, max);
                }
            }
        };
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

                first = new ArrayList<>(list.subList(low, mid + 1));
                second = new ArrayList<>(list.subList(mid + 1, high + 1));

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

    protected Strategy<T> getStrategy() {
        List<Strategy<T>> strategies = Arrays.asList(getMergeSortStrategy(), getQuickSortStrategy());

        Collections.shuffle(strategies);
        return strategies.get(0);
    }

    public List<T> sort(List<T> generation) {
        return sort(generation, getStrategy());
    }

    public List<T> sort(List<T> generation, Strategy<T> strategy) {
        strategy.sort(generation, Order.ASC);
        return generation;
    }
}
