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
	protected TestSelection testSelection;
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		generation = Mother.getGeneration();
		testSelection = new TestSelection();
	}
	
	public void testSelectionStrategy() {
		Individual<ICoordinate> child;
		Selection.Strategy strategy;

		for (int i = 0; i < 1000; i++) {
			strategy = testSelection.getTestStrategy(i % Selection.strategies.size());

			child = strategy.select(generation);

			assertTrue(generation.contains(child));
		}
	}

	private class TestSelection extends Selection {
		protected Selection.Strategy getTestStrategy(int n) {
			return strategies.get(n);
		}
	}
}
