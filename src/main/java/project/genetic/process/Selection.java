package project.genetic.process;

import project.genetic.fitness.Fitness;
import project.genetic.metadata.StrategyHelper;
import project.genetic.metadata.StrategyProvider;
import project.genetic.vo.individual.Individual;
import project.genetic.vo.individual.gene.IGene;
import project.genetic.vo.list.CloneableList;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Selection<T extends Individual<K>, K extends IGene> {

    Selection() {
    }

    public static <E extends Individual<F>, F extends IGene> Selection<E, F> newInstance() {
        return new Selection<>();
    }

    public interface Strategy<T> {
        T select(List<T> generation);
    }

    @StrategyProvider
    Strategy<T> getRouletteWheelSelectionStrategy() {
        return new Strategy<T>() {
            private T ind;

            @Override
            public T select(List<T> generation) {

                double max = Collections.max(generation, Comparator.comparingDouble(T::getFitness)).getFitness();

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
    Strategy<T> getTournamentSelectionStrategy() {
        return generation -> {
            int size = generation.size();
            int random = getRandom().nextInt(size - 2) + 2;

            CloneableList<T> pool = new CloneableList<>();
            while (pool.size() != random) {
                pool.add(generation.get(getRandom().nextInt(size)));
            }

            return Fitness.<T>newInstance().getFittest(pool);
        };
    }

    @StrategyProvider
    Strategy<T> getRankSelectionStrategy() {
        return (generation) -> {
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
        };
    }

    protected Random getRandom() {
        return new Random();
    }

    private Strategy<T> getStrategy() {
        return StrategyHelper.retrieveStrategy(this);
    }

    public T select(List<T> generation) {
        return select(generation, getStrategy());
    }

    private T select(List<T> generation, Strategy<T> strategy) {
        return strategy.select(generation);
    }
}
