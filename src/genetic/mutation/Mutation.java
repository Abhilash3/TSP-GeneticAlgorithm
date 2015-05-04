package genetic.mutation;

import genetic.TSP;
import java.util.ArrayList;

public class Mutation {

    public ArrayList<Integer> mutate(ArrayList<Integer> path) {

        ArrayList<Integer> mutatedPath;

        int random = TSP.rand.nextInt(99);
        if (random % 4 == 0) {
            mutatedPath = mutation1(path);
        } else if (random % 4 == 1) {
            mutatedPath = mutation2(path);
        } else if (random % 4 == 2) {
            mutatedPath = mutation3(path);
        } else {
            mutatedPath = mutation4(path);
        }

        return mutatedPath;
    }

    // [0,1,2,3,4,5,6,7,8,9] --> [0,9,8,7,6,5,4,3,2,1]
    private ArrayList<Integer> mutation1(ArrayList<Integer> path) {

        int city = path.remove(0);
        for(int i = 0, j = TSP.CityNumber - 2; i < j; i++, j--) {
            Integer temp = path.get(i);
            path.set(i, path.get(j));
            path.set(j, temp);
        }
        path.add(0, city);

        return path;
    }

    // [0,1,2,3,4,5,6,7,8,9] --> 5 --> [0,6,7,8,9,1,2,3,4,5]
    private ArrayList<Integer> mutation2(ArrayList<Integer> path) {
        int city = path.remove(0);

        int random = TSP.rand.nextInt(TSP.CityNumber - 2) + 1;

        ArrayList<Integer> subPath = new ArrayList<Integer>(path.subList(0, random));
        path.removeAll(subPath);
        path.addAll(path.size(), subPath);

        path.add(0, city);
        return path;
    }

    // [0,1,2,3,4,5,6,7,8,9] --> [0,2,1,4,3,6,5,8,7,9]
    private ArrayList<Integer> mutation3(ArrayList<Integer> path) {

        int city = path.remove(0);
        for(int i = 0; i < TSP.CityNumber - 2; i += 2) {
            Integer temp = path.get(i);
            path.set(i, path.get(i + 1));
            path.set(i + 1, temp);
        }
        path.add(0, city);

        return path;
    }

    // [0,1,2,3,4,5,6,7,8,9] --> 4, 6 --> [0,1,2,3,4,7,6,5,8,9]
    private ArrayList<Integer> mutation4(ArrayList<Integer> path) {

        int city = path.remove(0);

        int random1 = TSP.rand.nextInt(TSP.CityNumber - 1);
        int random2 = TSP.rand.nextInt(TSP.CityNumber - 1);

        for(int i = Math.min(random1, random2), j = Math.max(random1, random2); i < j; i++, j--) {
            Integer temp = path.get(i);
            path.set(i, path.get(j));
            path.set(j, temp);
        }
        path.add(0, city);

        return path;
    }

}
