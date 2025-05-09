package pbb_project2;


import enigma.core.Enigma;

public class NumberSnake {
	public static int timer = 0;
	public static Queue q = new Queue(100);
	public static enigma.console.Console cn = Enigma.getConsole("Number Snakes",100,30,24,1);
	public static Queue queue = Board.inputQueue();
	static int lastProcessedTime = -1;
	public static double timerCounter = 0;
	public static void main(String[] args) throws Exception {
		boolean flag = true;
		Board board = new Board();
		Player player = new Player(board);
		Computer computer = new Computer(new Coordinate(4, 4));
		Stack path = computer.findPath(new Coordinate(9, 9));
		int lenght = path.Size();
		int num = 0;
		for (int i = 0; i < lenght; i++) {
			Thread.sleep(200);
			player.Interface();
			player.drawAll();
			Coordinate cor = (Coordinate)path.Pop();
			String output = "" + (num+4);
			Player.mapData[cor.x][cor.y] = output.charAt(0);
			num = (num + 1)%6;
		}
		Player.mapData[4][4] = 'C';
		while(flag) {
			player.Interface();
			player.drawAll();
			board.displayInputQueue(); 
			board.displayTimer(timer); 
			player.update();
			if(Player.energy!=0) {
				Thread.sleep(100);
				
				computer.current = (Coordinate)path.Pop();
				timerCounter++;
			}
			else {
				Thread.sleep(400);
				timerCounter+=2.5;
			}
			
			if (timerCounter == 10) { 
				timer++;
				timerCounter = 0;
			}
		}
	}
		
	

}
