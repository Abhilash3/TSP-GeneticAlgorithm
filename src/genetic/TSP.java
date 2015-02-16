package genetic;

import gui.MapComponent;
import genetic.crossover.Crossover;
import genetic.fitness.Fitness;
import genetic.mutation.Mutation;
import genetic.selection.Selection;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class TSP {

    public final static int CityNumber = 200;
    public final static int Generations = 5000;
    public final static int PopulationSize = 250;
    public final static boolean Elitism = true;
    
    public static Random rand = new Random();
    
    private static Selection selection = new Selection();
    private static Crossover crossover = new Crossover();
    private static Mutation mutation = new Mutation();
    private static Fitness fitness = new Fitness();
    
    private static ArrayList<ArrayList<Integer>> coordinates;
    public static ArrayList<ArrayList<Double>> distanceMatrix;
    
    private static MapComponent gui;

    public TSP(ArrayList<ArrayList<Integer>> coordinates) {
        TSP.coordinates = coordinates;
        generateDistanceMatrix();
        createGUI();
    }
    
    private void createGUI() {
        
        JFrame frame = new JFrame("Simulation");
        gui = new MapComponent();
        frame.getContentPane().add("Center", gui);
        frame.setPreferredSize(new Dimension(1250, 650));
        gui.setBackground(Color.WHITE);
        gui.setForeground(Color.BLACK);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private void generateDistanceMatrix() {

        distanceMatrix = new ArrayList<ArrayList<Double>>();

        for (int i = 0; i < coordinates.size(); i++) {

            distanceMatrix.add(new ArrayList<Double>());

            for (int j = 0; j < coordinates.size(); j++) {

                int point1X = coordinates.get(i).get(0);
                int point1Y = coordinates.get(i).get(1);
                int point2X = coordinates.get(j).get(0);
                int point2Y = coordinates.get(j).get(1);
                double distance = Math.hypot(point1X - point2X, point1Y - point2Y);

                distanceMatrix.get(i).add(distance);
            }
        }
    }

    public ArrayList<Integer> simulate() {

        System.out.println("Starting Simulation: ");
        
        for (ArrayList<Integer> city : coordinates) {
            gui.draw(city.get(0), city.get(1));
        }

        ArrayList<ArrayList<Integer>> generation = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> bestPath = new ArrayList<Integer>();

        for (int i = 0; i < PopulationSize; i++) {
        	generation.add(getRandomPath());
        }

        generation = fitness.sortGeneration(generation);
        bestPath = generation.get(0);
        
        for (int i = 1; i < CityNumber; i++) {
            gui.draw(coordinates.get(bestPath.get(i - 1)).get(0),
                    coordinates.get(bestPath.get(i - 1)).get(1),
                    coordinates.get(bestPath.get(i)).get(0),
                    coordinates.get(bestPath.get(i)).get(1));
        }
        
        ArrayList<ArrayList<Integer>> newGeneration = new ArrayList<ArrayList<Integer>>();

        System.out.format("\n%s: %6s %s", "Generation", 1, "         ");
        System.out.format("%s: %20s", "Best Result", fitness.generateFitness(generation.get(0)));
        System.out.format("%10s: %s", "      Best Path", generation.get(0));

        for (int i = 1; i < Generations; i++) {
        	
        	int elitismOffset = 0;
            if (Elitism) {
                newGeneration.add(generation.get(0));
                elitismOffset = 1;
            }

            for (int j = elitismOffset; j < PopulationSize; j++) {
                
                ArrayList<Integer> parent1 = selection.select(generation);
                ArrayList<Integer> parent2 = selection.select(generation);
                ArrayList<Integer> child = crossover.cross(parent1, parent2);
                
                if (rand.nextInt(100) % 20 == 0) {
                    child = mutation.mutate(child);
                }
                
                newGeneration.add(child);

            }

            generation = fitness.sortGeneration(newGeneration);
            newGeneration = new ArrayList<ArrayList<Integer>>();
            
            if (fitness.generateFitness(bestPath) > fitness.generateFitness(generation.get(0))) {
            	bestPath = generation.get(0);
            }
            
            gui.clearLines();
            for (int j = 1; j < CityNumber; j++) {
                gui.draw(coordinates.get(generation.get(0).get(j - 1)).get(0),
                        coordinates.get(generation.get(0).get(j - 1)).get(1),
                        coordinates.get(generation.get(0).get(j)).get(0),
                        coordinates.get(generation.get(0).get(j)).get(1));
            }

            System.out.format("\n%s: %6s %s", "Generation", (i + 1), "         ");
            System.out.format("%s: %20s", "Best Result", fitness.generateFitness(generation.get(0)));
            System.out.format("%10s: %s", "      Best Path", generation.get(0));
            
        }
        
        return bestPath;
    }

    private ArrayList<Integer> getRandomPath() {
        ArrayList<Integer> path = new ArrayList<Integer>();
        path.add(0);
        for (int i = rand.nextInt(CityNumber - 1) + 1; path.size() != CityNumber; i = rand.nextInt(CityNumber - 1) + 1) {
            if (!path.contains(i)) {
                path.add(i);
            }
        }
        return path;
    }
}
