package project.genetic.process;

import java.util.HashSet;
import java.util.Set;

import junit.framework.TestCase;
import project.Mother;
import project.genetic.vo.coordinate.ICoordinate;
import project.genetic.vo.list.individual.Individual;

public class MutationTest extends TestCase {
	
	protected Individual<ICoordinate> ind;
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		ind = Mother.getPath();
	}
	
	public void testMutationStrategy() {
		Set<Mutation.Strategy> choices = new HashSet<Mutation.Strategy>();

		Individual<ICoordinate> child;
		for (int i = 0; i < 1000; i++) {
			Mutation.Strategy strategy = Mutation.getStrategy();
			child = strategy.mutate(ind);
			assertEquals(ind.size(), child.size());
			choices.add(strategy);
		}
		
		assertEquals(Mutation.strategies.size(), choices.size());
	}
}
