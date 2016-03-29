package project.genetic.fitness;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import project.Mother;
import project.genetic.vo.coordinate.ICoordinate;
import project.genetic.vo.list.individual.Individual;
import junit.framework.TestCase;

public class FitnessTest extends TestCase {
	
	protected Fitness fitness;
	protected List<Individual<ICoordinate>> generation;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		fitness = Fitness.getInstance();
		generation = new ArrayList<Individual<ICoordinate>>();
		generation.add(Mother.getIndividual(3.0));
		generation.add(Mother.getIndividual(1.0));
		generation.add(Mother.getIndividual(5.0));
		generation.add(Mother.getIndividual(2.0));
		generation.add(Mother.getIndividual(4.0));
		generation.add(Mother.getIndividual(8.0));
		generation.add(Mother.getIndividual(6.0));
		generation.add(Mother.getIndividual(10.0));
		generation.add(Mother.getIndividual(7.0));
		generation.add(Mother.getIndividual(9.0));
	}
	
	@Test
	public void testGetRankedIndividuals() {		
		List<Integer> ranks = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
		
		List<Individual<ICoordinate>> items;
		for (int i = 0; i < 100; i++) {
			items = fitness.getRankedIndividuals(generation, ranks);
			
			assertEquals(ranks.size(), items.size());
			assertEquals(1.0, items.get(0).getFitness());
			assertEquals(2.0, items.get(1).getFitness());
			assertEquals(3.0, items.get(2).getFitness());
			assertEquals(4.0, items.get(3).getFitness());
			assertEquals(5.0, items.get(4).getFitness());
			assertEquals(6.0, items.get(5).getFitness());
			assertEquals(7.0, items.get(6).getFitness());
			assertEquals(8.0, items.get(7).getFitness());
			assertEquals(9.0, items.get(8).getFitness());
			assertEquals(10.0, items.get(9).getFitness());
			
			Collections.shuffle(generation);
		}
	}

}
