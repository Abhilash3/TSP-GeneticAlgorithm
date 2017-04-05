package project.genetic.process;

import org.junit.Before;
import org.junit.Test;
import project.Mother;
import project.genetic.vo.coordinate.Coordinate;
import project.genetic.vo.individual.Individual;
import project.genetic.vo.individual.Route;

import static org.junit.Assert.assertEquals;

public class CrossoverTest {

    protected Crossover<Individual<Coordinate>> testCrossover;

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

    private void testStrategy(final Crossover.Strategy<Individual<Coordinate>> strategy) {
        Individual<Coordinate> parent1;
        Individual<Coordinate> parent2;
        Individual<Coordinate> child;

        for (int i = 0; i < 1000; i++) {
            parent1 = Mother.getPath();
            parent2 = Mother.getPath();

            child = strategy.cross(parent1, parent2);

            assertEquals(parent1.size(), child.size());
            assertEquals(parent2.size(), child.size());
        }
    }

    private Crossover<Individual<Coordinate>> getCrossover() {
        return new Crossover<Individual<Coordinate>>(Route.class);
    }
}
