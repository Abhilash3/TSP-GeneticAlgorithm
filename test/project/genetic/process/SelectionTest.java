package project.genetic.process;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;

import org.junit.Before;
import org.junit.Test;
import project.Mother;
import project.genetic.vo.coordinate.Coordinate;
import project.genetic.vo.individual.Individual;

import static org.junit.Assert.assertEquals;

public class SelectionTest {

    private Mockery mockery;

    @SuppressWarnings("rawtypes")
    private List _mockGeneration;
    private static Random _mockRandom;

    protected Selection<Individual<Coordinate>> testSelection;

    @Before
    public void setUp() throws Exception {
        mockery = new Mockery() {
            {
                setImposteriser(ClassImposteriser.INSTANCE);
            }
        };
        _mockGeneration = new ArrayList<>(Arrays.asList(Mother.getIndividualWithFitness(5),
                Mother.getIndividualWithFitness(9), Mother.getIndividualWithFitness(2),
                Mother.getIndividualWithFitness(3), Mother.getIndividualWithFitness(8),
                Mother.getIndividualWithFitness(10), Mother.getIndividualWithFitness(1),
                Mother.getIndividualWithFitness(6), Mother.getIndividualWithFitness(7),
                Mother.getIndividualWithFitness(4)));
        _mockRandom = mockery.mock(Random.class);
        testSelection = getSelection();
    }

    @Test
    public void selectionTournamentSelectionStrategy() {
        Selection.Strategy<Individual<Coordinate>> strategy = testSelection.getTournamentSelectionStrategy();

        mockery.checking(new Expectations() {
            {
                oneOf(_mockRandom).nextInt(8);
                will(returnValue(4));

                for (int i = 0; i < 6; i++) {
                    oneOf(_mockRandom).nextInt(10);
                    will(returnValue(i));
                }
            }
        });


        @SuppressWarnings({"rawtypes", "unchecked"})
        Individual child = strategy.select(_mockGeneration);

        assertEquals(Mother.getIndividualWithFitness(10), child);
    }

    @Test
    public void selectionRouletteWheelSelectionStrategy() {
        Selection.Strategy<Individual<Coordinate>> strategy = testSelection.getRouletteWheelSelectionStrategy();

        mockery.checking(new Expectations() {
            {
                oneOf(_mockRandom).nextDouble();
                will(returnValue(0.5));
            }
        });


        @SuppressWarnings({"rawtypes", "unchecked"})
        Individual child = strategy.select(_mockGeneration);

        assertEquals(Mother.getIndividualWithFitness(9), child);
    }

    @Test
    public void selectionRankSelectionStrategy() {
        Selection.Strategy<Individual<Coordinate>> strategy = testSelection.getRankSelectionStrategy();

        mockery.checking(new Expectations() {
            {
                oneOf(_mockRandom).nextInt(53);
                will(returnValue(30));
            }
        });


        @SuppressWarnings({"rawtypes", "unchecked"})
        Individual child = strategy.select(_mockGeneration);

        assertEquals(Mother.getIndividualWithFitness(6), child);
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
