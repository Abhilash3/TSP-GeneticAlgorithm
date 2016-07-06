package project.genetic.process;

import junit.framework.TestCase;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;
import project.Mother;
import project.genetic.vo.coordinate.ICoordinate;
import project.genetic.vo.list.individual.Individual;

import java.util.Random;

public class MutationTest extends TestCase {

    private Mockery mockery;
	protected TestMutation testMutation;
	private static Random _mockRandom;

    @Override
	public void setUp() throws Exception {
        mockery = new Mockery() {
            {
                setImposteriser(ClassImposteriser.INSTANCE);
            }
        };
		testMutation = new TestMutation();
		_mockRandom = mockery.mock(Random.class);
	}

	public void testMutationFirstStrategy() {
        final Individual<ICoordinate> ind = Mother.getPath();
        Mutation.Strategy strategy = testMutation.getTestStrategy(0);

        mockery.checking(new Expectations() {
            {
                exactly(2).of(_mockRandom).nextInt(ind.size() - 2);
                will(onConsecutiveCalls(returnValue(5), returnValue(8)));
            }
        });

        Individual<ICoordinate> child = strategy.mutate(ind);

        assertEquals(ind.size(), child.size());
        assertEquals(ind.get(6), child.get(9));
        assertEquals(ind.get(9), child.get(6));
        for (int i = 0; i < ind.size(); i++) {
            if (!(i == 6 || i == 9)) {
                assertEquals(ind.get(i), child.get(i));
            }
        }
	}

	public void testMutationSecondStrategy() {
        final Individual<ICoordinate> ind = Mother.getPath();
        Mutation.Strategy strategy = testMutation.getTestStrategy(1);

        Individual<ICoordinate> child = strategy.mutate(ind);

        assertEquals(ind.size(), child.size());
        for (int i = 0; i < ind.size(); i += 2) {
            assertEquals(ind.get(i), child.get(i + 1));
            assertEquals(ind.get(i + 1), child.get(i));
        }
	}

	public void testMutationThirdStrategy() {
        final Individual<ICoordinate> ind = Mother.getPath();
        Mutation.Strategy strategy = testMutation.getTestStrategy(2);

        mockery.checking(new Expectations() {
            {
                exactly(2).of(_mockRandom).nextInt(ind.size() - 1);
                will(onConsecutiveCalls(returnValue(5), returnValue(8)));
            }
        });

        Individual<ICoordinate> child = strategy.mutate(ind);

        assertEquals(ind.size(), child.size());
        assertEquals(ind.get(5), child.get(8));
        assertEquals(ind.get(6), child.get(7));
        assertEquals(ind.get(7), child.get(6));
        assertEquals(ind.get(8), child.get(5));
        for (int i = 0; i < ind.size(); i++) {
            if (i < 5 || i > 8) {
                assertEquals(ind.get(i), child.get(i));
            }
        }
	}

	private static class TestMutation extends Mutation {
        static {
            self = new TestMutation();
        }

		protected Mutation.Strategy getTestStrategy(int n) {
			return strategies.get(n);
		}

        @Override
        protected Random getRandom() {
            return _mockRandom;
        }
	}
}
