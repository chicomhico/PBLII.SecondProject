package pbb_project2;

import enigma.core.Enigma;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.Random;

public class Board {
	public char[][] map;
	public enigma.console.Console cn = Enigma.getConsole();

	public char[][] loadMap(String fileName) {
		map = null;
		try {
			BufferedReader br = new BufferedReader(new FileReader(fileName));
			String line;
			int y = 0;
			int maxLength = 0;

			while ((line = br.readLine()) != null) {
				if (line.length() > maxLength) {
					maxLength = line.length();
				}
				y++;
			}

			map = new char[y][maxLength];

			br.close();
			br = new BufferedReader(new FileReader(fileName));
			y = 0;

			while ((line = br.readLine()) != null) {
				for (int x = 0; x < line.length(); x++) {
					map[y][x] = line.charAt(x);
				}
				y++;
			}

			br.close();

		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		return map;
	}

	public static Queue inputQueue() {
		Queue queue = new Queue(100);
		Random rnd = new Random();
		int x = 0;
		for (int i = 0; i < 15; i++) {
			x = rnd.nextInt(100) + 1;
			if (x >= 1 && x <= 50) {
				queue.enqueue(1);
			} else if (x >= 51 && x <= 75) {
				queue.enqueue(2);
			} else if (x >= 76 && x <= 88) {
				queue.enqueue(3);
			} else if (x >= 89 && x <= 97) {
				queue.enqueue("@");
			} else {
				queue.enqueue("S");
			}

		}
		return queue;
	}

}
