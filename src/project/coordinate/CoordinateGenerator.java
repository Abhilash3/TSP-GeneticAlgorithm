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
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < 5000; i++) {
				int x = (rand.nextInt(55) + 5) * 10;
				int y = (rand.nextInt(55) + 5) * 10;
				if (sb.indexOf(x + " " + y) > 0) {
					i--;
					continue;
				}
				sb.append(x + " " + y + "\n");
			}
			out.print(sb);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
