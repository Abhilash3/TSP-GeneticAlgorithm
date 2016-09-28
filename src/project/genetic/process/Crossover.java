package project.genetic.process;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import project.genetic.vo.coordinate.Coordinates;
import project.genetic.vo.coordinate.ICoordinate;
import project.genetic.vo.list.MagicList;
import project.genetic.vo.list.individual.Individual;

/**
 * java class definition providing crossover capabilities
 *
 * @author ABHILASHKUMARV
 */
public class Crossover<T extends Individual<ICoordinate>> {

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

    public static <E extends Individual<ICoordinate>> Crossover<E> getInstance(Class<? extends E> clazz) {
        return new Crossover<E>(clazz);
    }

    protected Strategy<T> getNearestNeighbourStrategy() {
        return new Strategy<T>() {
            private List<ICoordinate> list;
            private ICoordinate city, city1, city2;

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

                list = new ArrayList<ICoordinate>();
                list.add(path1.get(0));

                for (int i = 1; i < path1.size(); i++) {

                    city1 = path1.get(i);
                    city2 = path2.get(i);

                    if (!list.contains(city1) && !list.contains(city2)) {
                        city = list.get(i - 1);
                        double distance1 = distance(city, city1);
                        double distance2 = distance(city, city2);
                        if (Double.compare(distance1, distance2) > 0) {
                            list.add(city2);
                        } else {
                            list.add(city1);
                        }
                    } else if (!list.contains(city1)) {
                        list.add(city1);
                    } else if (!list.contains(city2)) {
                        list.add(city2);
                    } else {
                        for (int j = 0; j < path1.size(); j++) {
                            city = path1.get(j);
                            if (!list.contains(city)) {
                                list.add(city);
                                break;
                            }
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

            private double distance(ICoordinate city1, ICoordinate city2) {
                return Coordinates.distance(city1, city2);
            }
        };
    }

    protected Strategy<T> getInitialRandomFromFirstRestFromSecondStrategy() {
        return new Strategy<T>() {
            private List<ICoordinate> list;
            private ICoordinate city1, city2;

            /**
             * selects first random no of cities from path1, rest from path2
             *
             * @param path1 parent 1
             * @param path2 parent 2
             * @return child
             */
            @Override
            public T cross(T path1, T path2) {

                list = new MagicList<ICoordinate>();

                int random = getRandom().nextInt(path1.size() - 1) + 1;
                for (int i = 0; i < random; i++) {
                    list.add(path1.get(i));
                }

                for (int i = random; list.size() != path1.size(); i++) {
                    city1 = path2.get(i);
                    if (!list.contains(city1)) {
                        list.add(city1);
                    } else {
                        for (int j = 0; j < random; j++) {
                            city2 = path2.get(j);
                            if (!list.contains(city2)) {
                                list.add(city2);
                                break;
                            }
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
            private List<ICoordinate> list;
            private ICoordinate city1, city2;

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

                list = new ArrayList<ICoordinate>();

                for (int i = 0; list.size() != path1.size(); i++) {
                    city1 = path1.get(i);
                    if (!list.contains(city1))
                        list.add(city1);

                    if (list.size() == path1.size())
                        break;

                    city2 = path2.get(i);
                    if (!list.contains(city2))
                        list.add(city2);
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
        return getStrategy().cross(parent1, parent2);
    }
}
