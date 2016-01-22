package project.genetic.fitness;

import java.util.List;

import project.Mother;
import project.genetic.vo.coordinate.ICoordinate;
import project.genetic.vo.list.individual.Individual;
import junit.framework.TestCase;

public class FitnessTest extends TestCase {
	
	protected Fitness fitness;
	protected List<Individual<ICoordinate>> generation;
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		fitness = Fitness.getInstance();
		generation = Mother.getGeneration();
	}
	
	public void testFitnessSorting() {
		
		for (int i = 0; i < 100; i++, generation = Mother.getGeneration()) {
			long start = System.nanoTime();
			fitness.sort(generation);
			long first = System.nanoTime() - start;
	
			start = System.nanoTime();
			fitness.sort(generation);
			long second = System.nanoTime() - start;
			
			assertTrue(second < first);
		}
	}

}
