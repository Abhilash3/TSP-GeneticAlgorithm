package project.genetic.process;

import org.junit.Before;
import org.junit.Test;
import project.Mother;
import project.genetic.vo.individual.Route;

import static org.junit.Assert.assertEquals;

public class CrossoverTest {

    protected Crossover<Route> testCrossover;

    @Before
    public void setUp() throws Exception {
        testCrossover = getCrossover();
    }

    @Test
    public void crossoverNearestNeighbourStrategy() {
        testStrategy(testCrossover.getNearestNeighbourStrategy());
    }

    @Test
    public void crossoverInitialRandomFromFirstRestFromSecondStrategy() {
        testStrategy(testCrossover.getInitialRandomFromFirstRestFromSecondStrategy());
    }

    @Test
    public void crossoverFirstComeFirstServeStrategy() {
        testStrategy(testCrossover.getFirstComeFirstServeStrategy());
    }

    private void testStrategy(final Crossover.Strategy<Route> strategy) {
        Route parent1;
        Route parent2;
        Route child;

        for (int i = 0; i < 1000; i++) {
            parent1 = Mother.getPath();
            parent2 = Mother.getPath();

            child = strategy.cross(parent1, parent2);

            assertEquals(parent1.size(), child.size());
            assertEquals(parent2.size(), child.size());
        }
    }

    private Crossover<Route> getCrossover() {
        return new Crossover<>(Route.class);
    }
}
