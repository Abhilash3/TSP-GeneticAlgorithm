package project.genetic.process;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import project.genetic.vo.Cloneable;
import project.genetic.vo.list.NoDuplicateList;
import project.genetic.vo.individual.Individual;

/**
 * java class definition providing crossover capabilities
 *
 * @author ABHILASHKUMARV
 */
public class Crossover<T extends Individual<? extends Cloneable>> {

    private Class<? extends T> clazz;

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

    protected Crossover(Class<? extends T> clazz) {
        this.clazz = clazz;
    }

    public static <E extends Individual<? extends Cloneable>> Crossover<E> getInstance(Class<? extends E> clazz) {
        return new Crossover<>(clazz);
    }

    protected Strategy<T> getNearestNeighbourStrategy() {
        return new Strategy<T>() {
            private List<Cloneable> list;
            private Cloneable city, city1, city2;

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

                list = new NoDuplicateList<>();
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

                try {
                    return clazz.getConstructor(List.class).newInstance(list);
                } catch (InstantiationException | IllegalAccessException
                        | IllegalArgumentException | InvocationTargetException
                        | NoSuchMethodException | SecurityException e) {
                    e.printStackTrace();
                    return null;
                }
            }

            private double factor(Cloneable city1, Cloneable city2) {
                try {
                    Method factorMethod = clazz.getDeclaredMethod("fitness", city1.getClass(), city2.getClass());
                    factorMethod.setAccessible(true);

                    T obj = clazz.getConstructor(List.class).newInstance(Collections.emptyList());
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
            private List<Cloneable> list;

            /**
             * selects first random no of cities from path1, rest from path2
             *
             * @param path1 parent 1
             * @param path2 parent 2
             * @return child
             */
            @Override
            public T cross(T path1, T path2) {

                list = new NoDuplicateList<>();

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

                try {
                    return clazz.getConstructor(List.class).newInstance(list);
                } catch (InstantiationException | IllegalAccessException
                        | IllegalArgumentException | InvocationTargetException
                        | NoSuchMethodException | SecurityException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }

    protected Strategy<T> getFirstComeFirstServeStrategy() {
        return new Strategy<T>() {
            private List<Cloneable> list;

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

                list = new NoDuplicateList<>();

                for (int i = 0; list.size() != path1.size(); i++) {
                    list.add(path1.get(i));
                    if (list.size() == path1.size()) {
                        break;
                    }
                    list.add(path2.get(i));
                }

                try {
                    return clazz.getConstructor(List.class).newInstance(list);
                } catch (InstantiationException | IllegalAccessException
                        | IllegalArgumentException | InvocationTargetException
                        | NoSuchMethodException | SecurityException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }

    protected Random getRandom() {
        return new Random();
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
