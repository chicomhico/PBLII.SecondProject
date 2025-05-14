package pbb_project2;

import enigma.core.Enigma;
import enigma.console.TextAttributes;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

public class Board {
	NumberSnake nSnake=new NumberSnake();
	public char[][] map;
	public static enigma.console.Console cn = Enigma.getConsole();
	int lastProcessedTime = -1;
	public static Queue queue = new Queue(100);
	public static int srob=0;

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

			map = new char[maxLength][y];

			br.close();
			br = new BufferedReader(new FileReader(fileName));
			y = 0;

			while ((line = br.readLine()) != null) {
				for (int x = 0; x < maxLength; x++) {
					if (x < line.length()) {
						map[x][y] = line.charAt(x);
					} else {
						map[x][y] = ' ';
					}
				}
				y++;
			}

			br.close();

		} catch (IOException e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
		}
		for(int i=0;i<30;i++) {
			map=firstElements();
		}
		
		return map;
	}

	public static Queue inputQueue() {
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

	public void displayInputQueue() {
		Random rnd = new Random();
		int startX = 61;
		int startY = 2;
		int x=0;
		int y=0;
		
		cn.getTextWindow().setCursorPosition(startX,startY);
		System.out.print("Input");
		cn.getTextWindow().setCursorPosition(startX,startY+1);
		System.out.print("<<<<<<<<<<<<<<<");
		cn.getTextWindow().setCursorPosition(startX,startY+2);
		int size = queue.size();
		for (int i = 0; i < size; i++) {
			System.out.print(queue.peek());
			queue.enqueue(queue.dequeue());
		}
		cn.getTextWindow().setCursorPosition(startX,startY+3);
		System.out.print("<<<<<<<<<<<<<<<");
		
		if (NumberSnake.timer % 2 == 0 && NumberSnake.timer != lastProcessedTime && NumberSnake.timer !=0) {
			lastProcessedTime = NumberSnake.timer;
			boolean flag = true;
			while(flag) {
				x = rnd.nextInt(map.length);
				y = rnd.nextInt(map[0].length);
				if(map[x][y] == ' ') {
					Object obj = queue.peek();
					char ch;
					if (obj instanceof Integer) {
						ch = (char) ('0' + (Integer) obj);
					} else {
						ch = obj.toString().charAt(0);
					}
					if(ch=='S') srob++; 
					map[x][y] = ch;
					flag = false;
				}
			}
			queue.dequeue();
			queue=updateInput();
		}
	}

	public void displayTimer(int time) {
		cn.getTextWindow().setCursorPosition(61, 8);
		System.out.print("Time    : " + time);
	}

	public void printMap() {
		for (int y = 0; y < map[0].length; y++) {
			for (int x = 0; x < map.length; x++) {
				char c = map[x][y];
				if (c == '\u0000') c = ' ';
				Color fg = Color.BLACK;
				Color bg = Color.WHITE;
				if (c == 'P' || c == '@') fg = Color.BLUE;
				if (c == 'C' || c == 'S') { fg = Color.WHITE; bg = Color.RED; }
				cn.getTextWindow().output(x, y, c, new TextAttributes(fg, bg));
			}
		}
	}
	
	public Queue updateInput() {
		Random rnd = new Random();
		int x = rnd.nextInt(100) + 1;
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
		return queue;
	}

	public char[][] firstElements() {
		char inp = ' ';
		boolean flag =true;
		Random rnd = new Random();
		
		int a = rnd.nextInt(100) + 1;
		if (a >= 1 && a <= 50) {
			inp = '1';
		} else if (a >= 51 && a <= 75) {
			inp = '2';
		} else if (a >= 76 && a <= 88) {
			inp = '3';
		} else if (a >= 89 && a <= 97) {
			inp = '@';
		} else {
			inp = 'S';
			srob++;
		}
		while(flag) {
			int x = rnd.nextInt(map.length);
			int y = rnd.nextInt(map[0].length);
			if(map[x][y] == ' ') {
				map[x][y] = inp;
				flag = false;
			}
		}
		return map;
	}
}
