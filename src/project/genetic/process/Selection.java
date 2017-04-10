package project.genetic.process;

import project.genetic.fitness.Fitness;
import project.genetic.metadata.StrategyHelper;
import project.genetic.metadata.StrategyProvider;
import project.genetic.vo.Cloneable;
import project.genetic.vo.individual.Individual;
import project.genetic.vo.list.CloneableList;
import project.genetic.vo.list.ICloneableList;

import java.util.List;
import java.util.Random;

/**
 * java class definition providing selection capabilities
 *
 * @author ABHILASHKUMARV
 */
public class Selection<T extends Individual<K>, K extends Cloneable> {

    protected Selection() {
    }

    public static <E extends Individual<F>, F extends Cloneable> Selection<E, F> getInstance() {
        return new Selection<>();
    }

    public interface Strategy<T> {

        /**
         * method generating selection options and selecting path
         *
         * @param generation generation pool
         * @return selection
         */
        T select(List<T> generation);
    }

    @StrategyProvider
    protected Strategy<T> getRouletteWheelSelectionStrategy() {
        return new Strategy<T>() {
            private T ind;

            /**
             * Roulette Wheel Selection
             *
             * @param generation generation pool
             * @return selection
             */
            @Override
            public T select(List<T> generation) {

                double max = 0;
                for (T individual : generation) {
                    if (max < individual.getFitness()) {
                        max = individual.getFitness();
                    }
                }

                double random = getRandom().nextDouble() * max;
                double sum = 0;
                for (int i = 0; sum <= random; i++) {
                    ind = generation.get(i);
                    sum += ind.getFitness();
                }
                return ind;

            }
        };
    }

    @StrategyProvider
    protected Strategy<T> getTournamentSelectionStrategy() {
        return new Strategy<T>() {
            private ICloneableList<T> pool;

            /**
             * Tournament Selection
             *
             * @param generation generation pool
             * @return selection
             */
            @Override
            public T select(List<T> generation) {

                int size = generation.size();
                int random = getRandom().nextInt(size - 2) + 2;

                pool = new CloneableList<>();
                while (pool.size() != random) {
                    pool.add(generation.get(getRandom().nextInt(size)));
                }

                return Fitness.<T>getInstance().getFittest(pool);
            }
        };
    }

    @StrategyProvider
    protected Strategy<T> getRankSelectionStrategy() {
        return new Strategy<T>() {

            /**
             * Rank Selection
             *
             * @param generation generation pool
             * @return selection
             */
            @Override
            public T select(List<T> generation) {

                int sum = 0, i = 1;
                while (i <= generation.size()) {
                    sum += i++;
                }

                int random = getRandom().nextInt(sum - 2) + 2;
                sum = 0;
                i = 1;
                while (sum <= random) {
                    sum += i++;
                }

                return generation.get(i - 2);
            }
        };
    }

    protected Random getRandom() {
        return new Random();
    }

    protected Strategy<T> getStrategy() {
        return StrategyHelper.retrieveStrategy(this);
    }

    public T select(List<T> generation) {
        return select(generation, getStrategy());
    }

    private T select(List<T> generation, Strategy<T> strategy) {
        return strategy.select(generation);
    }
}
