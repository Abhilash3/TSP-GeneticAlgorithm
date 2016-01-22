package project.genetic.process;

import java.util.HashSet;
import java.util.Set;

import project.Mother;
import project.genetic.vo.coordinate.ICoordinate;
import project.genetic.vo.list.individual.Individual;
import junit.framework.TestCase;

public class CrossoverTest extends TestCase {
	
	protected Crossover crossover;
	protected Individual<ICoordinate> parent1;
	protected Individual<ICoordinate> parent2;
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		crossover = Crossover.getInstance();
		parent1 = Mother.getPath();
		parent2 = Mother.getPath();
	}
	
	public void testCrossoverProcess() {
		Individual<ICoordinate> child;
		
		for (int i = 0; i < 100; i++) {
			child = crossover.cross(parent1, parent2);
			assertNotNull(child);
			assertNotSame(child, parent1);
			assertNotSame(child, parent2);
		}
	}
	
	public void testCrossoverStrategy() {
		Set<Crossover.Strategy> choices = new HashSet<Crossover.Strategy>();
		
		for (int i = 0; i < 10; i++) {
			choices.add(Crossover.getStrategy());
		}
		
		assertEquals(3, choices.size());
	}

}
