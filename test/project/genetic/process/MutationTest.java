package project.genetic.process;

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
	
	public void testMutationFirstStrategy() {
		testStrategy(testMutation.getTestStrategy(0));
	}
	
	public void testMutationSecondStrategy() {
		testStrategy(testMutation.getTestStrategy(1));
	}
	
	public void testMutationThirdStrategy() {
		testStrategy(testMutation.getTestStrategy(2));
	}
	
	public void testMutationForthStrategy() {
		testStrategy(testMutation.getTestStrategy(3));
	}
	
	private void testStrategy(final Mutation.Strategy strategy) {
		Individual<ICoordinate> ind;
		Individual<ICoordinate> child;
	
		for (int i = 0; i < 1000; i++) {
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
