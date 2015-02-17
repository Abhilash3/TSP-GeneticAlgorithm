package coordinate;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class CoordinateGenerator {

	public static void main(String[] args) {
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("coordinates.txt", false)));
        	for (int i = 0; i < 5000; i++) {
        		out.print((new Random().nextInt(115) + 5) * 10 + " ");
        		out.print((new Random().nextInt(55) + 5) * 10 + "\n");
            }
		    out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
