package project.genetic.process;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;
import project.Mother;
import project.genetic.vo.coordinate.ICoordinate;
import project.genetic.vo.list.individual.Individual;

public class CrossoverTest extends TestCase {

    protected TestCrossover testCrossover;
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
        testCrossover = new TestCrossover();
	}
	
	public void testCrossoverStrategy() {
        Individual<ICoordinate> parent1;
        Individual<ICoordinate> parent2;
		Individual<ICoordinate> child;
        Crossover.Strategy strategy;

		for (int i = 0; i < 1000; i++) {
            strategy = testCrossover.getTestStrategy(i % Crossover.strategies.size());
            parent1 = Mother.getPath();
            parent2 = Mother.getPath();

            child = strategy.cross(parent1, parent2);

            assertEquals(parent1.size(), child.size());
            assertEquals(parent2.size(), child.size());
        }
	}

    private class TestCrossover extends Crossover {
        protected Crossover.Strategy getTestStrategy(int n) {
            return strategies.get(n);
        }
    }
}
