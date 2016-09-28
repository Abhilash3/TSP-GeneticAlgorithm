package project.genetic.process;

import junit.framework.TestCase;
import project.Mother;
import project.genetic.vo.coordinate.ICoordinate;
import project.genetic.vo.list.individual.Individual;
import project.genetic.vo.list.individual.Path;

public class CrossoverTest extends TestCase {

    protected Crossover<Individual<ICoordinate>> testCrossover;
	
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
	
	private void testStrategy(final Crossover.Strategy<Individual<ICoordinate>> strategy) {
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

    private Crossover<Individual<ICoordinate>> getCrossover() {
    	return new Crossover<Individual<ICoordinate>>(Path.class);
    }
}
