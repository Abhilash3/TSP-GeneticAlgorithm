package project.genetic.process;

import junit.framework.TestCase;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;

import project.Mother;
import project.genetic.vo.coordinate.Coordinate;
import project.genetic.vo.individual.Individual;
import project.genetic.vo.individual.Route;

import java.util.Random;

public class MutationTest extends TestCase {

    private Mockery mockery;
    protected Mutation<Individual<Coordinate>> testMutation;
    private static Random _mockRandom;

    @Override
    public void setUp() throws Exception {
        mockery = new Mockery() {
            {
                setImposteriser(ClassImposteriser.INSTANCE);
            }
        };
        testMutation = getMutation();
        _mockRandom = mockery.mock(Random.class);
    }

    public void testMutationFirstStrategy() {
        final Individual<Coordinate> ind = Mother.getPath();
        Mutation.Strategy<Individual<Coordinate>> strategy = testMutation.getSwapTwoCitiesStrategy();

        mockery.checking(new Expectations() {
            {
                exactly(2).of(_mockRandom).nextInt(ind.size() - 2);
                will(onConsecutiveCalls(returnValue(5), returnValue(8)));
            }
        });

        Individual<Coordinate> child = strategy.mutate(ind);

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
        final Individual<Coordinate> ind = Mother.getPath();
        Mutation.Strategy<Individual<Coordinate>> strategy = testMutation.getSwapAdjacentCitiesStrategy();

        Individual<Coordinate> child = strategy.mutate(ind);

        assertEquals(ind.size(), child.size());
        for (int i = 0; i < ind.size(); i += 2) {
            assertEquals(ind.get(i), child.get(i + 1));
            assertEquals(ind.get(i + 1), child.get(i));
        }
    }

    public void testMutationThirdStrategy() {
        final Individual<Coordinate> ind = Mother.getPath();
        Mutation.Strategy<Individual<Coordinate>> strategy = testMutation.getReverseCityOrderStrategy();

        mockery.checking(new Expectations() {
            {
                exactly(2).of(_mockRandom).nextInt(ind.size() - 1);
                will(onConsecutiveCalls(returnValue(5), returnValue(8)));
            }
        });

        Individual<Coordinate> child = strategy.mutate(ind);

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

    private Mutation<Individual<Coordinate>> getMutation() {
        return new Mutation<Individual<Coordinate>>(Route.class) {
            @Override
            protected Random getRandom() {
                return _mockRandom;
            }
        };
    }
}
