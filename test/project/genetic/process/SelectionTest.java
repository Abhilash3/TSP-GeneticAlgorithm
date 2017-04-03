package project.genetic.process;

import java.util.List;
import java.util.Random;

import junit.framework.TestCase;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;

import project.Mother;
import project.genetic.vo.coordinate.Coordinate;
import project.genetic.vo.individual.Individual;

public class SelectionTest extends TestCase {

    private Mockery mockery;

    @SuppressWarnings("rawtypes")
    private List _mockGeneration;
    private static Random _mockRandom;

    protected Selection<Individual<Coordinate>> testSelection;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        mockery = new Mockery() {
            {
                setImposteriser(ClassImposteriser.INSTANCE);
            }
        };
        _mockGeneration = mockery.mock(List.class);
        _mockRandom = mockery.mock(Random.class);
        testSelection = getSelection();
    }

    public void testSelectionTournamentSelectionStrategy() {
        Selection.Strategy<Individual<Coordinate>> strategy = testSelection.getTournamentSelectionStrategy();

        mockery.checking(new Expectations() {
            {
                allowing(_mockGeneration).size();
                will(returnValue(10));

                oneOf(_mockRandom).nextInt(8);
                will(returnValue(4));

                for (int i = 0; i < 6; i++) {
                    oneOf(_mockRandom).nextInt(10);
                    will(returnValue(i));
                }
                exactly(6).of(_mockGeneration).get(with(any(Integer.class)));
                will(onConsecutiveCalls(returnValue(Mother.getIndividualWithFitness(5)),
                        returnValue(Mother.getIndividualWithFitness(9)), returnValue(Mother.getIndividualWithFitness(2)),
                        returnValue(Mother.getIndividualWithFitness(3)), returnValue(Mother.getIndividualWithFitness(8)),
                        returnValue(Mother.getIndividualWithFitness(10))));
            }
        });


        @SuppressWarnings({"rawtypes", "unchecked"})
        Individual child = strategy.select(_mockGeneration);

        assertEquals(Mother.getIndividualWithFitness(10), child);
    }

    public void testSelectionRouletteWheelSelectionStrategy() {
        Selection.Strategy<Individual<Coordinate>> strategy = testSelection.getRouletteWheelSelectionStrategy();

        mockery.checking(new Expectations() {
            {
                allowing(_mockGeneration).size();
                will(returnValue(10));

                exactly(10).of(_mockGeneration).get(with(any(Integer.class)));
                will(onConsecutiveCalls(returnValue(Mother.getIndividualWithFitness(5)),
                        returnValue(Mother.getIndividualWithFitness(9)), returnValue(Mother.getIndividualWithFitness(2)),
                        returnValue(Mother.getIndividualWithFitness(3)), returnValue(Mother.getIndividualWithFitness(8)),
                        returnValue(Mother.getIndividualWithFitness(10)), returnValue(Mother.getIndividualWithFitness(1)),
                        returnValue(Mother.getIndividualWithFitness(6)), returnValue(Mother.getIndividualWithFitness(7)),
                        returnValue(Mother.getIndividualWithFitness(4))));

                oneOf(_mockRandom).nextDouble();
                will(returnValue(0.5));

                atMost(10).of(_mockGeneration).get(with(any(Integer.class)));
                will(onConsecutiveCalls(returnValue(Mother.getIndividualWithFitness(5)),
                        returnValue(Mother.getIndividualWithFitness(9)), returnValue(Mother.getIndividualWithFitness(2)),
                        returnValue(Mother.getIndividualWithFitness(3)), returnValue(Mother.getIndividualWithFitness(8)),
                        returnValue(Mother.getIndividualWithFitness(10)), returnValue(Mother.getIndividualWithFitness(1)),
                        returnValue(Mother.getIndividualWithFitness(6)), returnValue(Mother.getIndividualWithFitness(7)),
                        returnValue(Mother.getIndividualWithFitness(4))));
            }
        });


        @SuppressWarnings({"rawtypes", "unchecked"})
        Individual child = strategy.select(_mockGeneration);

        assertEquals(Mother.getIndividualWithFitness(9), child);
    }

    public void testSelectionRankSelectionStrategy() {
        Selection.Strategy<Individual<Coordinate>> strategy = testSelection.getRankSelectionStrategy();

        mockery.checking(new Expectations() {
            {
                allowing(_mockGeneration).size();
                will(returnValue(10));

                oneOf(_mockRandom).nextInt(54);
                will(returnValue(30));

                oneOf(_mockGeneration).get(with(8));
                will(returnValue(Mother.getIndividualWithFitness(1)));
            }
        });


        @SuppressWarnings({"rawtypes", "unchecked"})
        Individual child = strategy.select(_mockGeneration);

        assertEquals(Mother.getIndividualWithFitness(1), child);
    }

    private Selection<Individual<Coordinate>> getSelection() {
        return new Selection<Individual<Coordinate>>() {
            @Override
            protected Random getRandom() {
                return _mockRandom;
            }
        };
    }
}
