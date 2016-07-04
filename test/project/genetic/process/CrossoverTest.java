package project.genetic.process;

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
	
	public void testCrossoverFirstStrategy() {
		testStrategy(testCrossover.getTestStrategy(0));
	}
	
	public void testCrossoverSecondStrategy() {
		testStrategy(testCrossover.getTestStrategy(1));
	}
	
	public void testCrossoverThirdStrategy() {
		testStrategy(testCrossover.getTestStrategy(2));
	}
	
	private void testStrategy(final Crossover.Strategy strategy) {
        Individual<ICoordinate> parent1;
        Individual<ICoordinate> parent2;
		Individual<ICoordinate> child;

		for (int i = 0; i < 1000; i++) {
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
