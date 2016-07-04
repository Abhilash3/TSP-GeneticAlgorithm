package project.genetic.fitness;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import project.Mother;
import project.genetic.vo.coordinate.ICoordinate;
import project.genetic.vo.list.individual.Individual;
import junit.framework.TestCase;

public class FitnessTest extends TestCase {
	
	protected List<Individual<ICoordinate>> generation;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		
		generation = new ArrayList<Individual<ICoordinate>>();
		for (int i = 0; i < 100; i++) {
			addToGeneration(generation, Double.parseDouble(""+(i + 1)));
		}
	}
	
	@SuppressWarnings("unchecked")
	private void addToGeneration(List<Individual<ICoordinate>> generation, double fitness) {
		generation.add(Mother.getIndividualWithFitness(fitness));
	}
	
	@Test
	public void testGetRankedIndividuals() {

		int size = generation.size();
		for (int i = 0; i < 100; i++) {
			
			Fitness.sort(generation);

			assertEquals(size, generation.size());
			for (int j = 0; j < generation.size(); j++) {
				assertEquals(j + 1+".0", Double.toString(generation.get(j).getFitness()));
			}			
			Collections.shuffle(generation);
		}
	}

}
