package project.genetic.fitness;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import project.genetic.vo.coordinate.ICoordinate;
import project.genetic.vo.list.individual.Individual;

/**
 * java class definition for sorting path
 * 
 * @author ABHILASHKUMARV
 * 
 */
public class Fitness {

	protected static List<Strategy> strategies;

	private static Random rand = new Random();

	private Fitness() {}

    /**
     * order to sort list
     */
	private enum Order {
        ASC {
            @SuppressWarnings("rawtypes")
			@Override
            public int compare(Individual a, Individual b) {
                return a.compareTo(b);
            }
        },
        DESC {
            @SuppressWarnings("rawtypes")
			@Override
            public int compare(Individual a, Individual b) {
                return ASC.compare(a, b) * -1;
            }
        };

        @SuppressWarnings("rawtypes")
		public abstract int compare(Individual a, Individual b);
    }

	public interface Strategy {
        /**
         * method generating sorting options and sorted path
         *
         * @param list list of objects
         * @param order order in which to sort
         */
		void sort(List<Individual<ICoordinate>> list, Order order);
	}

	static {
		prepareStrategies();
	}

	private static void prepareStrategies() {
		strategies = new ArrayList<Strategy>();

		strategies.add(new Strategy() {
			private Order order;
			private List<Individual<ICoordinate>> first, second;

            /**
             * MergeSort
             *
             * @param list list of objects
             * @param order order in which to sort
             */
			@Override
			public void sort(List<Individual<ICoordinate>> list, Order order) {
				this.order = order;
				mergeSort(list, 0, list.size() - 1);
			}

			private void mergeSort(List<Individual<ICoordinate>> list, int low, int high) {
				if (low < high) {
					int mid = (high + low) / 2;
					mergeSort(list, low, mid);
					mergeSort(list, mid + 1, high);
					merge(list, low, high);
				}
			}

			private void merge(List<Individual<ICoordinate>> list, int low, int high) {
				int mid = (high + low) / 2;

				first = new ArrayList<Individual<ICoordinate>>(list.subList(low, mid + 1));
				second = new ArrayList<Individual<ICoordinate>>(list.subList(mid + 1, high + 1));

				for (int i = 0, j = 0, k = low; k < high + 1; k++) {
					if (i < first.size() && j < second.size()) {
						if (order.compare(first.get(i), second.get(j)) == 1) {
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
		});
	}

	protected static Strategy getStrategy() {
		return strategies.get(rand.nextInt(strategies.size()));
	}

	public static Individual<ICoordinate> getFittest(List<Individual<ICoordinate>> generation) {
		return sort(generation).get(0);
	}

	public static List<Individual<ICoordinate>> sort(List<Individual<ICoordinate>> generation) {
		getStrategy().sort(generation, Order.ASC);
		return generation;
	}
}
