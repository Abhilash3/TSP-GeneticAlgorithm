package project.genetic.process;

import project.genetic.metadata.StrategyHelper;
import project.genetic.metadata.StrategyProvider;
import project.genetic.vo.individual.Individual;
import project.genetic.vo.individual.gene.IGene;
import project.genetic.vo.list.CloneableList;
import project.genetic.vo.list.ICloneableList;
import project.genetic.vo.list.decorator.NoDuplicateListDecorator;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;

public class Crossover<T extends Individual<K>, K extends IGene> {

    private Class<T> clazz;

    public interface Strategy<T> {
        T cross(T path1, T path2);
    }

    Crossover(Class<T> clazz) {
        this.clazz = clazz;
    }

    public static <E extends Individual<F>, F extends IGene> Crossover<E, F> newInstance(Class<E> clazz) {
        return new Crossover<>(clazz);
    }

    @StrategyProvider
    Strategy<T> getNearestNeighbourStrategy() {
        return (path1, path2) -> {
            K city, city1, city2;
            ICloneableList<K> list = newIgnoringDuplicatesList();
            list.add(path1.get(0));

            for (int i = 1; i < path1.size(); i++) {

                city1 = path1.get(i);
                city2 = path2.get(i);

                if (!list.contains(city1) && !list.contains(city2)) {
                    city = list.get(i - 1);
                    if (Double.compare(city.value(city1), city.value(city2)) > 0) {
                        list.add(city2);
                    } else {
                        list.add(city1);
                    }
                } else if (!list.add(city1) && !list.add(city2)) {
                    int j = 0;
                    while (!list.add(path1.get(j))) {
                        j++;
                    }
                }
            }

            return initialize(list);
        };
    }

    @StrategyProvider
    Strategy<T> getInitialRandomFromFirstRestFromSecondStrategy() {
        return (path1, path2) -> {
            ICloneableList<K> list = newIgnoringDuplicatesList();

            int random = getRandom().nextInt(path1.size() - 1) + 1;
            for (int i = 0; i < random; i++) {
                list.add(path1.get(i));
            }

            for (int i = random; list.size() != path1.size(); i++) {
                if (!list.add(path2.get(i))) {
                    int j = 0;
                    while (!list.add(path2.get(j))) {
                        j++;
                    }
                }
            }

            return initialize(list);
        };
    }

    @StrategyProvider
    Strategy<T> getFirstComeFirstServeStrategy() {
        return (path1, path2) -> {
            ICloneableList<K> list = newIgnoringDuplicatesList();

            for (int i = 0; list.size() != path1.size(); i++) {
                list.add(path1.get(i));
                if (list.size() == path1.size()) {
                    break;
                }
                list.add(path2.get(i));
            }

            return initialize(list);
        };
    }

    private Random getRandom() {
        return new Random();
    }

    private ICloneableList<K> newIgnoringDuplicatesList() {
        return new NoDuplicateListDecorator<>(new CloneableList<K>());
    }

    private T initialize(ICloneableList<K> list) {
        try {
            return clazz.getConstructor(ICloneableList.class).newInstance(list);
        } catch (InstantiationException | IllegalAccessException
                | IllegalArgumentException | InvocationTargetException
                | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
            return null;
        }
    }

    private Strategy<T> getStrategy() {
        return StrategyHelper.retrieveStrategy(this);
    }

    public T cross(T parent1, T parent2) {
        return cross(parent1, parent2, getStrategy());
    }

    private T cross(T parent1, T parent2, Strategy<T> strategy) {
        return strategy.cross(parent1, parent2);
    }
}
