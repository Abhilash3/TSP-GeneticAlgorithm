package genetic;

import gui.Graph;
import gui.Map;
import genetic.crossover.Crossover;
import genetic.fitness.Fitness;
import genetic.mutation.Mutation;
import genetic.selection.Selection;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class TSP {

    public final static int CityNumber = 100;
    public final static int Generations = 5000;
    public final static int PopulationSize = 100;
    public final static boolean Elitism = false;
    
    public static Random rand = new Random();
    
    private Selection selection = new Selection();
    private Crossover crossover = new Crossover();
    private Mutation mutation = new Mutation();
    private Fitness fitness = new Fitness();
    
    private ArrayList<ArrayList<Integer>> coordinates;
    public static ArrayList<ArrayList<Double>> distanceMatrix;
    
    private Map map;
    private Graph graph;
    private JTextField textField;

    public TSP(ArrayList<ArrayList<Integer>> coordinates) {
        this.coordinates = coordinates;
        generateDistanceMatrix();
        createGUI();
    }
    
    private void createGUI() {
        
        JFrame frame = new JFrame("Simulation");
        
        map = new Map();
        map.setPreferredSize(new Dimension(700, 650));
        
        graph = new Graph();
        graph.setPreferredSize(new Dimension(650, 650));
        
        textField = new JTextField();
        textField.setFont(new Font(textField.getFont().getName(), Font.BOLD, textField.getFont().getSize()));
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setEditable(false);
        textField.setPreferredSize(new Dimension(650, 50));
        
        frame.getContentPane().add(map, BorderLayout.CENTER);
        frame.getContentPane().add(graph, BorderLayout.LINE_END);
        frame.getContentPane().add(textField, BorderLayout.PAGE_END);
        frame.setPreferredSize(new Dimension(1350, 700));
        
        map.setBackground(Color.WHITE);
        map.setForeground(Color.BLACK);
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
        
        for (ArrayList<Integer> city : coordinates) {
        	map.draw(city.get(0), city.get(1));
        }

        ArrayList<ArrayList<Integer>> generation = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> bestPath = new ArrayList<Integer>();

        for (int i = 0; i < PopulationSize; i++) {
        	generation.add(getRandomPath());
        }

        generation = fitness.sortGeneration(generation);
        bestPath = generation.get(0);
        
        graph.add(fitness.generateFitness(generation.get(0)));        
        
        for (int i = 1; i < CityNumber; i++) {
        	map.draw(coordinates.get(bestPath.get(i - 1)).get(0),
                    coordinates.get(bestPath.get(i - 1)).get(1),
                    coordinates.get(bestPath.get(i)).get(0),
                    coordinates.get(bestPath.get(i)).get(1));
        }
        
        ArrayList<ArrayList<Integer>> newGeneration = new ArrayList<ArrayList<Integer>>();

        textField.setText(String.format("%s: %6s %s", "Generation", 1, "         ") + 
        		String.format("%s: %20s", "Best Result", fitness.generateFitness(generation.get(0))));

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
                
                if (rand.nextInt(99) % 10 == 0) {
                    child = mutation.mutate(child);
                }
                
                newGeneration.add(child);

            }

            generation = fitness.sortGeneration(newGeneration);
            newGeneration = new ArrayList<ArrayList<Integer>>();
            
            graph.add(fitness.generateFitness(generation.get(0)));
            
            if (fitness.generateFitness(bestPath) > fitness.generateFitness(generation.get(0))) {
            	bestPath = generation.get(0);
            	
            	map.clearLines();
                for (int j = 1; j < CityNumber; j++) {
                	map.draw(coordinates.get(bestPath.get(j - 1)).get(0),
                            coordinates.get(bestPath.get(j - 1)).get(1),
                            coordinates.get(bestPath.get(j)).get(0),
                            coordinates.get(bestPath.get(j)).get(1));
                }
            }

            textField.setText(String.format("%s: %6s %s", "Generation", (i + 1), "         ") + 
            		String.format("%s: %20s", "Best Result", fitness.generateFitness(bestPath)));
            
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
