package project.genetic.process;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import project.genetic.vo.Cloneable;
import project.genetic.vo.list.CloneableList;
import project.genetic.vo.list.ICloneableList;
import project.genetic.vo.individual.Individual;
import project.genetic.vo.list.decorator.NoDuplicateListDecorator;

/**
 * java class definition providing crossover capabilities
 *
 * @author ABHILASHKUMARV
 */
public class Crossover<T extends Individual<K>, K extends Cloneable> {

    private Class<T> clazz;

    public interface Strategy<T> {

        /**
         * method generating crossover options and generating child path
         *
         * @param path1 parent 1
         * @param path2 parent 2
         * @return child
         */
        T cross(T path1, T path2);
    }

    protected Crossover(Class<T> clazz) {
        this.clazz = clazz;
    }

    public static <E extends Individual<F>, F extends Cloneable> Crossover<E, F> getInstance(Class<E> clazz) {
        return new Crossover<>(clazz);
    }

    protected Strategy<T> getNearestNeighbourStrategy() {
        return new Strategy<T>() {
            private ICloneableList<K> list;
            private K city, city1, city2;

            /**
             * first city from path1, get nth city from both paths and select
             * whichever is closer to n-1th city in child path
             *
             * @param path1 parent 1
             * @param path2 parent 2
             * @return child
             */
            @Override
            public T cross(T path1, T path2) {

                list = getNoDuplicateList();
                list.add(path1.get(0));

                for (int i = 1; i < path1.size(); i++) {

                    city1 = path1.get(i);
                    city2 = path2.get(i);

                    if (!list.contains(city1) && !list.contains(city2)) {
                        city = list.get(i - 1);
                        if (Double.compare(factor(city, city1), factor(city, city2)) > 0) {
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
            }

            private double factor(K city1, K city2) {
                try {
                    Method factorMethod = clazz.getDeclaredMethod("fitness", city1.getClass(), city2.getClass());
                    factorMethod.setAccessible(true);

                    T obj = clazz.getConstructor(ICloneableList.class).newInstance(new CloneableList<>());
                    return (Double) factorMethod.invoke(obj, city1, city2);
                } catch (InstantiationException | IllegalAccessException
                        | IllegalArgumentException | InvocationTargetException
                        | NoSuchMethodException | SecurityException e) {
                    e.printStackTrace();
                    return Double.POSITIVE_INFINITY;
                }
            }
        };
    }

    protected Strategy<T> getInitialRandomFromFirstRestFromSecondStrategy() {
        return new Strategy<T>() {
            private ICloneableList<K> list;

            /**
             * selects first random no of cities from path1, rest from path2
             *
             * @param path1 parent 1
             * @param path2 parent 2
             * @return child
             */
            @Override
            public T cross(T path1, T path2) {

                list = getNoDuplicateList();

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
            }
        };
    }

    protected Strategy<T> getFirstComeFirstServeStrategy() {
        return new Strategy<T>() {
            private ICloneableList<K> list;

            /**
             * selects cities from both paths in whichever order they come with
             * no duplication
             *
             * @param path1 parent 1
             * @param path2 parent 2
             * @return child
             */
            @Override
            public T cross(T path1, T path2) {

                list = getNoDuplicateList();

                for (int i = 0; list.size() != path1.size(); i++) {
                    list.add(path1.get(i));
                    if (list.size() == path1.size()) {
                        break;
                    }
                    list.add(path2.get(i));
                }

                return initialize(list);
            }
        };
    }

    protected Random getRandom() {
        return new Random();
    }

    private ICloneableList<K> getNoDuplicateList() {
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

    protected Strategy<T> getStrategy() {
        List<Strategy<T>> strategies = Arrays.asList(getNearestNeighbourStrategy(),
                getInitialRandomFromFirstRestFromSecondStrategy(), getFirstComeFirstServeStrategy());

        Collections.shuffle(strategies);
        return strategies.get(0);
    }

    public T cross(T parent1, T parent2) {
        return cross(parent1, parent2, getStrategy());
    }

    private T cross(T parent1, T parent2, Strategy<T> strategy) {
        return strategy.cross(parent1, parent2);
    }
}
