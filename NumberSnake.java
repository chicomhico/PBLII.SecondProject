package pbb_project2;


import enigma.core.Enigma;

public class NumberSnake {
	public static int timer = 0;
	public static Queue q = new Queue(100);
	public static enigma.console.Console cn = Enigma.getConsole("Number Snakes");
	public static Queue queue = Board.inputQueue();
	static int lastProcessedTime = -1;
	public static double timerCounter = 0;
	public static void main(String[] args) throws Exception {
		boolean flag = true;
		Board board = new Board();
		Player player = new Player(board);
		
		
		
		while(flag) {
			player.Interface();
			player.drawAll();
			board.displayInputQueue(); 
			board.displayTimer(timer); 
			player.update();
			if(Player.energy!=0) {
				Thread.sleep(100);
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
