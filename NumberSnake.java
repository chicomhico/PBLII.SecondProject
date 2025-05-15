package pbb_project2;


import enigma.core.Enigma;

public class NumberSnake {
	public static int timer = 0;
	public Queue q = new Queue(100);
	public enigma.console.Console cn = Enigma.getConsole("Number Snakes",100,30,24,1);
	static int lastProcessedTime = -1;
	public static double timerCounter = 0;
	public char[][] mapData;
	Board board;
	Player player;
	Computer computer;
	public void main() throws Exception {
		board = new Board(this);
		player = new Player(this);
		mapData = board.CopyMap();
		boolean flag = true;
		computer = new Computer(new Coordinate(4, 4),this);
		long previoustime = System.currentTimeMillis();
		long timertimer = 0;
		board.displayTimer(timer);
		drawAll();
		while(flag) {
			long currenttime = System.currentTimeMillis();
			long difference = currenttime - previoustime;
			Boolean ismapchanged = player.TimeElapse(difference);
			ismapchanged = ismapchanged || board.TimeElapse(difference);
			ismapchanged = ismapchanged || computer.TimeElapse(difference);
			if (ismapchanged) {
				drawAll();
			}
			timertimer += difference;
			
			if (timertimer > 1000) {
				timertimer -= 1000;
				timer++;
				board.displayTimer(timer);
			}
			previoustime = currenttime;
			Thread.sleep(1);

			player.PlaceTrap();
			player.updateTraps();
		}
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
		if (board.map[coordinate.x][coordinate.y] == '#')
			return false;
		if (coordinate.x == player.x && coordinate.y == player.y)
			return false;
		if (coordinate.x == computer.current.x && coordinate.y == computer.current.y)
			return false;
		return true;
	}
	public void drawAll() {
        for (int yy = 0; yy < board.map.length; yy++) {
            for (int xx = 0; xx < board.map[yy].length; xx++) {
                cn.getTextWindow().output(xx, yy, board.map[yy][xx]);
            }
        }
        cn.getTextWindow().output(player.x, player.y, player.symbol);
        cn.getTextWindow().output(computer.current.y, computer.current.x, 'C');
        cn.getTextWindow().output(55, 23, ' ');
    }

}
