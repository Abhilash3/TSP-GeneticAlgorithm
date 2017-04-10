package project.genetic.metadata;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StrategyHelper {

    private StrategyHelper() {
        throw new UnsupportedOperationException("Util Class");
    }

    @SuppressWarnings("unchecked")
    public static <E, F> List<F> retrieveStrategies(E e) {
        List<F> strategies = new ArrayList<>();
        for(Method method : e.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(StrategyProvider.class)) {
                try {
                    method.setAccessible(true);
                    strategies.add((F) method.invoke(e));
                } catch (IllegalAccessException | InvocationTargetException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return strategies;
    }

    public static <E, F> F retrieveStrategy(E e) {
        List<F> strategies = retrieveStrategies(e);
        Collections.shuffle(strategies);
        return strategies.get(0);
    }

}
