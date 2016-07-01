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
		addToGeneration(generation, 16.0);
		addToGeneration(generation, 3.0);
		addToGeneration(generation, 11.0);
		addToGeneration(generation, 1.0);
		addToGeneration(generation, 5.0);
		addToGeneration(generation, 20.0);
		addToGeneration(generation, 15.0);
		addToGeneration(generation, 14.0);
		addToGeneration(generation, 2.0);
		addToGeneration(generation, 10.0);
		addToGeneration(generation, 4.0);
		addToGeneration(generation, 13.0);
		addToGeneration(generation, 8.0);
		addToGeneration(generation, 19.0);
		addToGeneration(generation, 6.0);
		addToGeneration(generation, 17.0);
		addToGeneration(generation, 7.0);
		addToGeneration(generation, 12.0);
		addToGeneration(generation, 9.0);
		addToGeneration(generation, 18.0);
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
			assertEquals(1.0, generation.get(0).getFitness());
			assertEquals(2.0, generation.get(1).getFitness());
			assertEquals(3.0, generation.get(2).getFitness());
			assertEquals(4.0, generation.get(3).getFitness());
			assertEquals(5.0, generation.get(4).getFitness());
			assertEquals(6.0, generation.get(5).getFitness());
			assertEquals(7.0, generation.get(6).getFitness());
			assertEquals(8.0, generation.get(7).getFitness());
			assertEquals(9.0, generation.get(8).getFitness());
			assertEquals(10.0, generation.get(9).getFitness());
			assertEquals(11.0, generation.get(10).getFitness());
			assertEquals(12.0, generation.get(11).getFitness());
			assertEquals(13.0, generation.get(12).getFitness());
			assertEquals(14.0, generation.get(13).getFitness());
			assertEquals(15.0, generation.get(14).getFitness());
			assertEquals(16.0, generation.get(15).getFitness());
			assertEquals(17.0, generation.get(16).getFitness());
			assertEquals(18.0, generation.get(17).getFitness());
			assertEquals(19.0, generation.get(18).getFitness());
			assertEquals(20.0, generation.get(19).getFitness());
			
			Collections.shuffle(generation);
		}
	}

}
