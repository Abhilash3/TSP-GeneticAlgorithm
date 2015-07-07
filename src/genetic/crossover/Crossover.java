package genetic.crossover;

import genetic.TSP;
import java.util.ArrayList;

public class Crossover {
    
    public ArrayList<Integer> cross(ArrayList<Integer> path1, ArrayList<Integer> path2) {
        
        ArrayList<Integer> path;
        
        int random = TSP.rand.nextInt(99);
        if (random % 3 == 1) {
            path = crossover1(path1, path2);
        } else if (random % 3 == 2) {
            path = crossover2(path1, path2);
        } else {
            path = crossover3(path1, path2);
        }
        
        return path;
    }
    
    private ArrayList<Integer> crossover1(ArrayList<Integer> path1, ArrayList<Integer> path2) {

        ArrayList<Integer> path3 = new ArrayList<Integer>();
        path3.add(path1.get(0));

        for (Integer i = 1; i < TSP.CityNumber; i++) {

            Integer city1 = path1.get(i);
            Integer city2 = path2.get(i);

            if (!path3.contains(city1) && !path3.contains(city2)) {
                Double distance1 = TSP.distanceMatrix.get(path1.get(i - 1)).get(city1);
                Double distance2 = TSP.distanceMatrix.get(path2.get(i - 1)).get(city2);
                if (distance1 < distance2) {
                    path3.add(city1);
                } else {
                    path3.add(city2);
                }
            } else if (!path3.contains(city1)) {
                path3.add(city1);
            } else if (!path3.contains(city2)) {
                path3.add(city2);
            } else {
                for (Integer city : path1) {
                    if (!path3.contains(city)) {
                        path3.add(city);
                        break;
                    }
                }
            }
        }

        return path3;
    }
    
    private ArrayList<Integer> crossover2(ArrayList<Integer> path1, ArrayList<Integer> path2) {
        
        ArrayList<Integer> path3 = new ArrayList<Integer>();
        path3.add(path1.get(0));

        int random = TSP.rand.nextInt(TSP.CityNumber - 2) + 1;
        
        for (int i = 1; i < random; i++) {
            path3.add(path1.get(i));
        }
        for (int i = random; path3.size() != TSP.CityNumber; i++) {
            if (!path3.contains(path2.get(i))) {
                path3.add(path2.get(i));
            } else {
                for (Integer city : path2) {
                    if (!path3.contains(city)) {
                        path3.add(city);
                        break;
                    }
                }
            }
        }
        
        return path3;
    }
    
    private ArrayList<Integer> crossover3(ArrayList<Integer> path1, ArrayList<Integer> path2) {
        
        ArrayList<Integer> path3 = new ArrayList<Integer>();
        path3.add(path1.get(0));
        
        for(int i = 1; path3.size() != TSP.CityNumber; i++) {
            if (!path3.contains(path1.get(i))) {
                path3.add(path1.get(i));
            }
            if (!path3.contains(path2.get(i))) {
                path3.add(path2.get(i));
            }
        }
        
        return path3;
    }
    
}
