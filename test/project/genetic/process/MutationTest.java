package project.genetic.process;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;

import org.junit.Before;
import org.junit.Test;
import project.Mother;
import project.genetic.vo.coordinate.Coordinate;
import project.genetic.vo.individual.Individual;
import project.genetic.vo.individual.Route;

import java.util.Random;

import static org.junit.Assert.assertEquals;

public class MutationTest {

    private Mockery mockery;
    protected Mutation<Route, Coordinate> testMutation;
    private static Random _mockRandom;

    @Before
    public void setUp() throws Exception {
        mockery = new Mockery() {
            {
                setImposteriser(ClassImposteriser.INSTANCE);
            }
        };
        testMutation = getMutation();
        _mockRandom = mockery.mock(Random.class);
    }

    @Test
    public void mutationFirstStrategy() {
        final Route ind = Mother.getPath();
        Mutation.Strategy<Route> strategy = testMutation.getSwapTwoCitiesStrategy();

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

    @Test
    public void mutationSecondStrategy() {
        final Route ind = Mother.getPath();
        Mutation.Strategy<Route> strategy = testMutation.getSwapAdjacentCitiesStrategy();

        Individual<Coordinate> child = strategy.mutate(ind);

        assertEquals(ind.size(), child.size());
        for (int i = 0; i < ind.size(); i += 2) {
            assertEquals(ind.get(i), child.get(i + 1));
            assertEquals(ind.get(i + 1), child.get(i));
        }
    }

    @Test
    public void mutationThirdStrategy() {
        final Route ind = Mother.getPath();
        Mutation.Strategy<Route> strategy = testMutation.getReverseCityOrderStrategy();

        mockery.checking(new Expectations() {
            {
                exactly(2).of(_mockRandom).nextInt(ind.size() - 2);
                will(onConsecutiveCalls(returnValue(4), returnValue(7)));
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

    private Mutation<Route, Coordinate> getMutation() {
        return new Mutation<Route, Coordinate>(Route.class) {
            @Override
            protected Random getRandom() {
                return _mockRandom;
            }
        };
    }
}
