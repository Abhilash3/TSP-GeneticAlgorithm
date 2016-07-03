package project.genetic.process;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;
import project.Mother;
import project.genetic.vo.coordinate.ICoordinate;
import project.genetic.vo.list.individual.Individual;

public class MutationTest extends TestCase {

	protected TestMutation testMutation;
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		testMutation = new TestMutation();
	}
	
	public void testMutationStrategy() {
		Individual<ICoordinate> ind;
		Mutation.Strategy strategy;

		Individual<ICoordinate> child;
		for (int i = 0; i < 1000; i++) {
			strategy = testMutation.getTestStrategy(i % Mutation.strategies.size());
			ind = Mother.getPath();

			child = strategy.mutate(ind);
			assertEquals(ind.size(), child.size());
		}
	}

	private class TestMutation extends Mutation {
		protected Mutation.Strategy getTestStrategy(int n) {
			return strategies.get(n);
		}
	}
}
