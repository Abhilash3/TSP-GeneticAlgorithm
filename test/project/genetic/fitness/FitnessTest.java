package project.genetic.fitness;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import project.Mother;
import project.genetic.vo.coordinate.Coordinate;
import project.genetic.vo.individual.Individual;
import junit.framework.TestCase;

public class FitnessTest extends TestCase {

    protected Fitness<Individual<Coordinate>> fitness;
    protected List<Individual<Coordinate>> generation;

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        fitness = Fitness.getInstance();
        generation = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            addToGeneration(generation, Double.parseDouble("" + (i + 1)));
        }
    }

    @SuppressWarnings("unchecked")
    private void addToGeneration(List<Individual<Coordinate>> generation, double fitness) {
        generation.add(Mother.getIndividualWithFitness(fitness));
    }

    public void testSort() {

        int size = generation.size();
        for (int i = 0; i < 100; i++) {

            Collections.shuffle(generation);
            fitness.sort(generation);

            assertEquals(size, generation.size());
            for (int j = 0; j < generation.size(); j++) {
                assertEquals(10 - j + ".0", Double.toString(generation.get(j).getFitness()));
            }
        }
    }

}
