package project.genetic.process;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import project.Mother;
import project.genetic.vo.coordinate.ICoordinate;
import project.genetic.vo.list.individual.Individual;
import junit.framework.TestCase;

public class SelectionTest extends TestCase {
	
	private List<Individual<ICoordinate>> generation;
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		generation = Mother.getGeneration();
	}
	
	public void testSelectionStrategy() {
		Set<Selection.Strategy> choices = new HashSet<Selection.Strategy>();

		Individual<ICoordinate> child;
		for (int i = 0; i < 1000; i++) {
			Selection.Strategy strategy = Selection.getStrategy();
			child = strategy.select(generation);
			assertTrue(generation.contains(child));
			choices.add(strategy);
		}
		
		assertEquals(Selection.strategies.size(), choices.size());
	}
}
