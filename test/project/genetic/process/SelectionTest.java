package project.genetic.process;

import java.util.List;
import java.util.Random;

import junit.framework.TestCase;
import org.jmock.Expectations;
import org.jmock.Mockery;
import org.jmock.lib.legacy.ClassImposteriser;

import project.Mother;
import project.genetic.vo.list.individual.Individual;

public class SelectionTest extends TestCase {
	
	private Mockery mockery;
	
	@SuppressWarnings("rawtypes")
	private List _mockGeneration;
	private static Random _mockRandom;
	
	protected TestSelection testSelection;
	
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
		testSelection = new TestSelection();
	}
	
	public void testSelectionFirstStrategy() {
		Selection.Strategy strategy = testSelection.getTestStrategy(0);
		
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


		@SuppressWarnings({ "rawtypes", "unchecked" })
		Individual child = strategy.select(_mockGeneration);
		
		assertEquals(Mother.getIndividualWithFitness(9), child);
	}
	
	public void testSelectionSecondStrategy() {
		Selection.Strategy strategy = testSelection.getTestStrategy(1);
		
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


		@SuppressWarnings({ "rawtypes", "unchecked" })
		Individual child = strategy.select(_mockGeneration);
		
		assertEquals(Mother.getIndividualWithFitness(10), child);
	}
	
	public void testSelectionThirdStrategy() {
		Selection.Strategy strategy = testSelection.getTestStrategy(2);
		
		mockery.checking(new Expectations() {
			{
				allowing(_mockGeneration).size();
				will(returnValue(10));
				
				oneOf(_mockRandom).nextInt(54);
				will(returnValue(30));
				
				oneOf(_mockGeneration).get(with(8));
				will(onConsecutiveCalls(returnValue(Mother.getIndividualWithFitness(6))));
			}
		});


		@SuppressWarnings({ "rawtypes", "unchecked" })
		Individual child = strategy.select(_mockGeneration);
		
		assertEquals(Mother.getIndividualWithFitness(6), child);
	}

	private static class TestSelection extends Selection {
		static {
			self = new TestSelection();
		}
		
		protected Selection.Strategy getTestStrategy(int n) {
			return strategies.get(n);
		}
		
		@Override
		protected Random getRandom() {
			return _mockRandom;
		}
	}
}
