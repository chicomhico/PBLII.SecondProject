package pbb_project2;


import java.awt.Color;

import enigma.console.TextAttributes;
import enigma.core.Enigma;

public class NumberSnake {
	public static int timer = 0;
	public Queue q = new Queue(100);
	public enigma.console.Console cn = Enigma.getConsole("Number Snakes",100,30,24,1);
	static int lastProcessedTime = -1;
	public static double timerCounter = 0;
	Board board;
	Player player;
	Computer computer;
	SnakeController snakecontroller;
	public void main() throws Exception {
		//starts instances
		snakecontroller = new SnakeController(this);
		board = new Board(this);
		player = new Player(this);
		boolean flag = true;
		computer = new Computer(new Coordinate(4, 4),this);
		long previoustime = System.currentTimeMillis();
		long timertimer = 0;
		board.displayTimer(timer);
		drawAll();
		while(flag && !player.isdead) {
			long currenttime = System.currentTimeMillis();
			long difference = currenttime - previoustime;
			// that is for speeding the game up
			difference *= 1;
			//updates every element and gets does the map changed
			Boolean ismapchanged = player.TimeElapse(difference);
			ismapchanged = ismapchanged || board.TimeElapse(difference);
			ismapchanged = ismapchanged || computer.TimeElapse(difference);
			ismapchanged = ismapchanged || snakecontroller.TimeElapse(difference);
			//if map has changed than prints the map
			if (ismapchanged) {
				drawAll();
			}
			//board time increase
			timertimer += difference;
			
			if (timertimer > 1000) {
				timertimer -= 1000;
				timer++;
				board.displayTimer(timer);
			}
			previoustime = currenttime;
			//waits a bit
			Thread.sleep(1);

			player.PlaceTrap();
			player.updateTraps();
		}
		new HighScoreTable(player.GetScore(), this);
		/*while(flag) {
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
		}*/
	}
	public boolean isAvailableToMove(Coordinate coordinate) {
		if (board.GetCoor(coordinate) == '#')
			return false;
		if (coordinate.x == player.position.x && coordinate.y == player.position.y)
			return false;
		if (coordinate.x == computer.current.x && coordinate.y == computer.current.y)
			return false;
		if (snakecontroller.ContainCoordinate(coordinate))
			return false;
		return true;
	}
	public void drawAll() {
        board.printMap();
        TextAttributes ta = new TextAttributes(Color.yellow, Color.red);
        //prints computer target
        cn.getTextWindow().output(computer.GetTarget().x, computer.GetTarget().y
        		, board.GetCoor(computer.GetTarget()), ta);
        ta = new TextAttributes(Color.green, Color.black);
        //prints player
        cn.getTextWindow().output(player.position.x, player.position.y, player.symbol, ta);
        ta = new TextAttributes(Color.red, Color.black);
        //prints computer
        cn.getTextWindow().output(computer.current.x, computer.current.y, 'C', ta);
        //prints snakes
        snakecontroller.PrintSnakes();
        cn.getTextWindow().output(55, 23, ' ');
    }

}
