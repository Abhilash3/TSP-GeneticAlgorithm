package project.genetic.process;

import project.genetic.metadata.StrategyHelper;
import project.genetic.metadata.StrategyProvider;
import project.genetic.vo.Cloneable;
import project.genetic.vo.individual.Individual;
import project.genetic.vo.list.CloneableList;
import project.genetic.vo.list.ICloneableList;
import project.genetic.vo.list.decorator.NoDuplicateListDecorator;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;

/**
 * java class definition providing mutation capabilities
 *
 * @author ABHILASHKUMARV
 */
public class Mutation<T extends Individual<K>, K extends Cloneable> {

    protected Class<T> clazz;

    protected Mutation(Class<T> clazz) {
        this.clazz = clazz;
    }

    public static <E extends Individual<F>, F extends Cloneable> Mutation<E, F> getInstance(Class<E> clazz) {
        return new Mutation<>(clazz);
    }

    public interface Strategy<T> {

        /**
         * method generating mutation options and mutating path
         *
         * @param path individual
         * @return mutated path
         */
        T mutate(T path);
    }

    @StrategyProvider
    protected Strategy<T> getSwapTwoCitiesStrategy() {
        return new Strategy<T>() {
            private ICloneableList<K> list;

            /**
             * [0,1,2,3,4,5,6,7,8,9] --> 2, 8 --> [0,8,2,3,4,5,6,7,1,9]
             *
             * @param path individual
             * @return mutated path
             */
            @Override
            public T mutate(T path) {

                list = getNoDublicateList();
                int random1, random2;

                do {
                    random1 = getRandom().nextInt(path.size() - 2) + 1;
                    random2 = getRandom().nextInt(path.size() - 2) + 1;
                } while (random1 == random2);

                int min = Math.min(random1, random2);
                int max = Math.max(random1, random2);

                for (int i = 0; i < min; i++) {
                    list.add(path.get(i));
                }
                list.add(path.get(max));
                for (int i = min + 1; i < max; i++) {
                    list.add(path.get(i));
                }
                list.add(path.get(min));
                for (int i = max + 1; i < path.size(); i++) {
                    list.add(path.get(i));
                }

                return initialize(list);
            }
        };
    }

    @StrategyProvider
    protected Strategy<T> getSwapAdjacentCitiesStrategy() {
        return new Strategy<T>() {
            private ICloneableList<K> list;

            /**
             * [0,1,2,3,4,5,6,7,8,9] --> [1,0,3,2,5,4,7,6,9,8]
             *
             * @param path individual
             * @return mutated path
             */
            @Override
            public T mutate(T path) {

                list = getNoDublicateList();
                for (int i = 1; i < path.size(); i += 2) {
                    list.add(path.get(i));
                    list.add(path.get(i - 1));
                }
                if (path.size() % 2 == 1) {
                    list.add(path.get(path.size() - 1));
                }

                return initialize(list);
            }
        };
    }

    @StrategyProvider
    protected Strategy<T> getReverseCityOrderStrategy() {
        return new Strategy<T>() {
            private ICloneableList<K> list;

            /**
             * [0,1,2,3,4,5,6,7,8,9] --> 2, 8 --> [0,1,7,6,5,4,3,2,8,9]
             *
             * @param path individual
             * @return mutated path
             */
            @Override
            public T mutate(T path) {

                list = getNoDublicateList();
                int random1, random2;

                do {
                    random1 = getRandom().nextInt(path.size() - 2) + 1;
                    random2 = getRandom().nextInt(path.size() - 2) + 1;
                } while (random1 == random2);

                int min = Math.min(random1, random2);
                int max = Math.max(random1, random2);

                for (int i = 0; i < min; i++) {
                    list.add(path.get(i));
                }

                for (int i = max; i >= min; i--) {
                    list.add(path.get(i));
                }

                for (int i = max + 1; i < path.size(); i++) {
                    list.add(path.get(i));
                }

                return initialize(list);
            }
        };
    }

    protected Random getRandom() {
        return new Random();
    }

    private ICloneableList<K> getNoDublicateList() {
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
        return StrategyHelper.retrieveStrategy(this);
    }

    public T mutate(T path) {
        return mutate(path, getStrategy());
    }

    private T mutate(T path, Strategy<T> strategy) {
        return strategy.mutate(path);
    }

}
