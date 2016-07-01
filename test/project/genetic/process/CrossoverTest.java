package project.genetic.process;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;
import project.Mother;
import project.genetic.vo.coordinate.ICoordinate;
import project.genetic.vo.list.individual.Individual;

public class CrossoverTest extends TestCase {
	
	protected Individual<ICoordinate> parent1;
	protected Individual<ICoordinate> parent2;
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		parent1 = Mother.getPath();
		parent2 = Mother.getPath();
	}
	
	public void testCrossoverStrategy() {
		Set<Crossover.Strategy> choices = new HashSet<Crossover.Strategy>();

		Individual<ICoordinate> child;
		for (int i = 0; i < 1000; i++) {
			Crossover.Strategy strategy = Crossover.getStrategy();
			child = strategy.cross(parent1, parent2);
			assertEquals(parent1.size(), child.size());
			assertEquals(parent2.size(), child.size());
			choices.add(strategy);
		}
		
		assertEquals(Crossover.strategies.size(), choices.size());
	}
}
