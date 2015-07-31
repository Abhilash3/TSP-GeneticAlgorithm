package project.coordinate;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class CoordinateGenerator {

	public static void main(String[] args) {
		Random rand = new Random();
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(
					new FileWriter("coordinates.txt", false)));
			for (int i = 0; i < 5000; i++) {
				out.print((rand.nextInt(55) + 5) * 10 + " ");
				out.println((rand.nextInt(55) + 5) * 10);
			}
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
