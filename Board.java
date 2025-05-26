package pbb_project2;


import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
import java.util.prefs.NodeChangeEvent;

import enigma.console.TextAttributes;

public class Board {
	private char[][] map; // sadece treasure kullanılmamış trap ve duvarlar
	int maplengtfirst;
	int maplengtsecond;
	public Queue queue = new Queue(100);
	public int srob=0;
	private long timer;
	
	private NumberSnake game;
	public char GetCoor(Coordinate coor) {
		return map[coor.y][coor.x];
	}
	public void SetCoor(Coordinate coor, char set) {
		map[coor.y][coor.x] = set;
	}

	public Board(NumberSnake game) {
		this.game = game;
		loadMap("map.txt");
		inputQueue();
		displayInputQueue();
	}
	public boolean TimeElapse(long elapsedtime) {
		timer += elapsedtime;
		if (timer > 1000) {
			displayInputQueue();
			timer -= 1000;
			InputFromQueue();
			return true;
		}
		return false;
	}
	private void loadMap(String fileName) {
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
				for (int x = 0; x < maxLength; x++) {
					if (x < line.length()) {
						map[y][x] = line.charAt(x);
					} else {
						map[y][x] = ' ';
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
		maplengtfirst = map.length;
		maplengtsecond = map[0].length;
	}

	private void inputQueue() {
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
	}

	private void displayInputQueue() {
		int startX = 61;
		int startY = 2;
		
			game.cn.getTextWindow().setCursorPosition(startX,startY);
			System.out.print("Input");
			game.cn.getTextWindow().setCursorPosition(startX,startY+1);
			System.out.print("<<<<<<<<<<<<<<<");
			game.cn.getTextWindow().setCursorPosition(startX,startY+2);
			int size = queue.size();
			for (int i = 0; i < size; i++) {
				System.out.print(queue.peek());
				queue.enqueue(queue.dequeue());
			}
			game.cn.getTextWindow().setCursorPosition(startX,startY+3);
			System.out.print("<<<<<<<<<<<<<<<");
	}
	private void InputFromQueue(){
		Random rnd = new Random();
		boolean flag = true;
		    while(flag) {
		    int x = rnd.nextInt(55);
		    int y = rnd.nextInt(23);
		    if(map[y][x] == ' ') {
		        Object obj = queue.peek();
		        char ch;
		        if (obj instanceof Integer) {
		            ch = (char) ('0' + (Integer) obj);
		        } else {
		            ch = obj.toString().charAt(0);
		        }
		        if(ch=='S') {
		        	game.snakecontroller.AddSnake(new Coordinate(x, y));
		        	srob++;
		        } 
		        else
		        	map[y][x] = ch;
		        flag = false;
		    }
		  
		}
		
		queue.dequeue();
		UpdateQueue();
	}

	public void displayTimer(int time) {
		game.cn.getTextWindow().setCursorPosition(61, 8);
		System.out.print("Time    : " + time);
	}

	public void printMap() {
		//game.cn.getTextWindow().setCursorPosition(0, 0);
		for (int y = 0; y < map.length; y++) {
			for (int x = 0; x < map[y].length; x++) {
				char c = map[y][x];
				if (c == '\u0000') c = ' ';
				if (c == '#') {
					TextAttributes ta = new TextAttributes(Color.blue, Color.green);
					game.cn.getTextWindow().output(x, y, c, ta);
				}
				else if (c == '1' || c == '2' || c == '3'){
					TextAttributes ta = new TextAttributes(Color.yellow, Color.black);
					game.cn.getTextWindow().output(x, y, c, ta);
				}
				else {
					TextAttributes ta = new TextAttributes(Color.white, Color.black);
					game.cn.getTextWindow().output(x, y, c, ta);
				}
			}
		}
	}
	
	public void UpdateQueue() {
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
		        int x = rnd.nextInt(55);
		        int y = rnd.nextInt(23);
		        if(map[y][x] == ' ') {
		        	if (inp == 'S')
		        		game.snakecontroller.AddSnake(new Coordinate(x, y));
		        	else
		        		map[y][x] = inp;
		           
		            flag = false;
		        }
		        
		    }
		
		return map;
	}
	
}
