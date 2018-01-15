package project.genetic.fitness;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import project.Mother;
import project.genetic.vo.coordinate.Coordinate;
import project.genetic.vo.individual.Individual;

import static org.junit.Assert.assertEquals;

public class FitnessTest {

    protected Fitness<Individual<Coordinate>> fitness;
    protected List<Individual<Coordinate>> generation;

    @Before
    public void setUp() throws Exception {

        fitness = Fitness.newInstance();
        generation = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            addToGeneration(generation, Double.parseDouble(i + 1 + ""));
        }
    }

    @SuppressWarnings("unchecked")
    private void addToGeneration(List<Individual<Coordinate>> generation, double fitness) {
        generation.add(Mother.getIndividualWithFitness(fitness));
    }

    @Test
    public void sort() {
        int size = generation.size();
        for (int i = 0; i < 100; i++) {

            Collections.shuffle(generation);
            fitness.sort(generation);

            assertEquals(size, generation.size());
            for (int j = 0; j < generation.size(); j++) {
                assertEquals(1000 - j + ".0", Double.toString(generation.get(j).getFitness()));
            }
        }
    }

}
