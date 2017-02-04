package project.genetic.process;

import junit.framework.TestCase;
import project.Mother;
import project.genetic.vo.coordinate.Coordinate;
import project.genetic.vo.list.individual.Individual;
import project.genetic.vo.list.individual.Route;

public class CrossoverTest extends TestCase {

    protected Crossover<Individual<Coordinate>> testCrossover;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        testCrossover = getCrossover();
    }

    public void testCrossoverNearestNeighbourStrategy() {
        testStrategy(testCrossover.getNearestNeighbourStrategy());
    }

    public void testCrossoverInitialRandomFromFirstRestFromSecondStrategy() {
        testStrategy(testCrossover.getInitialRandomFromFirstRestFromSecondStrategy());
    }

    public void testCrossoverFirstComeFirstServeStrategy() {
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
