package coordinate;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class CoordinateGenerator {

	public static void main(String[] args) {
		PrintWriter out;
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter("coordinates.txt", true)));
        	for (int i = 0; i < 5000; i++) {
        		out.print((new Random().nextInt(115) + 5) * 10 + " ");
        		out.print((new Random().nextInt(55) + 5) * 10 + "\n");
            }
		    out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
