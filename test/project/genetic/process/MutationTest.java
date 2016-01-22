package project.genetic.process;

import java.util.HashSet;
import java.util.Set;

import project.Mother;
import project.genetic.vo.coordinate.ICoordinate;
import project.genetic.vo.list.individual.Individual;
import junit.framework.TestCase;

public class MutationTest extends TestCase {
	
	protected Mutation mutation;
	protected Individual<ICoordinate> ind;
	
	@Override
	public void setUp() throws Exception {
		super.setUp();
		mutation = Mutation.getInstance();
		ind = Mother.getPath();
	}
	
	public void testMutationProcess() {
		Set<Individual<ICoordinate>> set = new HashSet<Individual<ICoordinate>>();
		Individual<ICoordinate> child;
		
		for (int i = 0; i < 100; i++) {
			child = mutation.mutate(ind);
			set.add(child);
			assertNotNull(child);
			assertNotSame(child, ind);
		}
		assertTrue(set.size() > 25);
	}
	
	public void testMutationStrategy() {
		Set<Mutation.Strategy> choices = new HashSet<Mutation.Strategy>();
		
		for (int i = 0; i < 20; i++) {
			choices.add(Mutation.getStrategy());
		}
		
		assertEquals(4, choices.size());
	}

}
