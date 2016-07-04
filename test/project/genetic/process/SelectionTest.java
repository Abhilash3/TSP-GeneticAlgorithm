package project.genetic.process;

import java.util.List;

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
	
	public void testSelectionFirstStrategy() {
		testStrategy(testSelection.getTestStrategy(0));
	}
	
	public void testSelectionSecondStrategy() {
		testStrategy(testSelection.getTestStrategy(1));
	}
	
	public void testSelectionThirdStrategy() {
		testStrategy(testSelection.getTestStrategy(2));
	}
	
	private void testStrategy(final Selection.Strategy strategy) {
		Individual<ICoordinate> child;
		
		for (int i = 0; i < 1000; i++) {
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
