package genetic;

import gui.Graph;
import gui.Map;
import gui.vo.City;
import genetic.crossover.Crossover;
import genetic.fitness.Fitness;
import genetic.mutation.Mutation;
import genetic.selection.Selection;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class TSP {
    
    public final static int CityNumber = 50;
    public final static int Generations = 3000;
    public final static int PopulationSize = 100;
    public final static boolean Elitism = false;
    public final static int Graphs = 1;
    
    public static Random rand = new Random();
    
    private Selection selection = new Selection();
    private Crossover crossover = new Crossover();
    private Mutation mutation = new Mutation();
    private Fitness fitness = new Fitness();
    
    private ArrayList<City> coordinates;
    public static ArrayList<ArrayList<Double>> distanceMatrix;
    
    private Map map;
    private Graph graph;
    private JTextField textField;
    
    public TSP(ArrayList<City> coordinates) {
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
        textField.setFont(new Font(textField.getFont().getName(), Font.BOLD,
                textField.getFont().getSize()));
        textField.setHorizontalAlignment(JTextField.CENTER);
        textField.setEditable(false);
        textField.setPreferredSize(new Dimension(650, 50));
        
        frame.getContentPane().add(map, BorderLayout.LINE_START);
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
        
        for (int i = 0; i < CityNumber; i++) {
        
            distanceMatrix.add(new ArrayList<Double>());
            
            for (int j = 0; j < CityNumber; j++) {
                
                int point1X = coordinates.get(i).getX();
                int point1Y = coordinates.get(i).getY();
                int point2X = coordinates.get(j).getX();
                int point2Y = coordinates.get(j).getY();
                double distance = Math.hypot(point1X - point2X, point1Y
                        - point2Y);
                
                distanceMatrix.get(i).add(distance);
            }
        }
    }
    
    public ArrayList<Integer> simulate() {
        
        PrintWriter probLog = null;
        PrintWriter resLog = null;
        
        try {
            probLog = new PrintWriter(new BufferedWriter(new FileWriter(
                    "ProbLog.txt", false)));
            resLog = new PrintWriter(new BufferedWriter(new FileWriter(
                    "ResLog.txt", false)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
        
        for (City city : coordinates) {
            map.drawPoint(city);
        }
        
        ArrayList<ArrayList<Integer>> generation = new ArrayList<ArrayList<Integer>>();
        ArrayList<Integer> bestPath = new ArrayList<Integer>();
        
        for (int i = 0; i < PopulationSize; i++) {
            generation.add(getRandomPath());
        }
        
        generation = fitness.sortGeneration(generation);
        bestPath = generation.get(0);

        ArrayList<Double> scores = new ArrayList<Double>();
        scores.add(fitness.generateFitness(generation.get(0)));
        for (int i = 1; i < Graphs; i++) {
            scores.add(fitness.generateFitness(generation.get(i * PopulationSize / (Graphs - 1) - 1)));
        }
        graph.add(scores);
        
        for (City city : coordinates) {
            map.drawLine(city);
        }
        
        ArrayList<ArrayList<Integer>> newGeneration = new ArrayList<ArrayList<Integer>>();
        
        textField.setText(String.format("%s: %6s %s %s: %20s", "Generation", 1,
                "         ", "Best Result",
                fitness.generateFitness(generation.get(0))));
        
        float[] arr = new float[2];
        
        for (int i = 1; i < Generations; i++) {
            
            int elitismOffset = 0;
            if (Elitism) {
                newGeneration.add(generation.get(0));
                elitismOffset = 1;
            }
            
            arr[0] = 0;
            arr[1] = 0;
            
            for (int j = elitismOffset; j < PopulationSize; j++) {
                
                ArrayList<Integer> parent1 = selection.select(generation);
                ArrayList<Integer> parent2 = selection.select(generation);
                ArrayList<Integer> child = crossover.cross(parent1, parent2);
                
                if (rand.nextInt(Generations - 1) > i) {
                    arr[0] += 1;
                    child = mutation.mutate(child);
                }
                arr[1] += 1;
                
                newGeneration.add(child);
                
            }
            
            generation = fitness.sortGeneration(newGeneration);
            newGeneration = new ArrayList<ArrayList<Integer>>();
            
            scores = new ArrayList<Double>();
            scores.add(fitness.generateFitness(generation.get(0)));
            for (int j = 1; j < Graphs; j++) {
                scores.add(fitness.generateFitness(generation.get(j * PopulationSize / (Graphs - 1) - 1)));
            }
            graph.add(scores);
            
            bestPath = generation.get(0);
            
            map.clearLines();
            for (int j = 0; j < CityNumber; j++) {
                map.drawLine(coordinates.get(bestPath.get(j)));
            }
            
            textField.setText(String.format("%s: %6s %s %s: %.2f %s %s: %20s",
                    "Generation", (i + 1), "         ", "Mutation Probability",
                    100 * arr[0] / arr[1], "         ", "Best Result",
                    fitness.generateFitness(bestPath)));
            
            probLog.println(arr[0] / arr[1] + " " + (arr[1] - arr[0]) / arr[1]);
            resLog.println((i + 1) + "\t\t"
                    + sdf.format(Calendar.getInstance().getTime()) + "\t\t"
                    + fitness.generateFitness(bestPath));
            
        }
        
        probLog.close();
        resLog.close();
        return bestPath;
    }
    
    private ArrayList<Integer> getRandomPath() {
        ArrayList<Integer> path = new ArrayList<Integer>();
        path.add(0);
        for (int i = rand.nextInt(CityNumber - 1) + 1; path.size() != CityNumber; i = rand
                .nextInt(CityNumber - 1) + 1) {
            if (!path.contains(i)) {
                path.add(i);
            }
        }
        return path;
    }
}
