package pbb_project2;
import java.util.Random;

public class Computer {
	public Computer(Coordinate start , NumberSnake game) {
		current = start;
		this.game = game;
		ClearLastPath();
	}
	NumberSnake game;
	private Random random = new Random();
	private Coordinate target = new Coordinate(0,0);
	Coordinate current;
	private Stack CurrentPath = new Stack(100000);
	private Stack memoryStack = new Stack(1000);
	private int timer = 0;
	private boolean[][] isvisited = new boolean[23][55];
	public int computer_score = 0;
	public boolean TimeElapse(long elapsedtime) {
		timer += elapsedtime;
		if (timer > 400) {
			timer -= 400;
			PlayMove();
			if (game.board.map[current.x][current.y] == '·')
				game.board.map[current.x][current.y] = ' ';
			if(game.board.map[current.x][current.y] == '1') {
				computer_score += 1;
				game.board.map[current.x][current.y] = ' ';
			}
			else if(game.board.map[current.x][current.y] == '2') {
				computer_score += 4;
				game.board.map[current.x][current.y] = ' ';
			}
			else if(game.board.map[current.x][current.y] == '3') {
				computer_score += 16;
				game.board.map[current.x][current.y] = ' ';
			}
			else if(game.board.map[current.x][current.y] == '@') {
				computer_score += 50;
				game.board.map[current.x][current.y] = ' ';
			}
			return true;
		}
		return false;
	}
	private void findPath(Coordinate targetCoordinate) {
		Stack path = new Stack(1000);
		Coordinate currentCoordinate = new Coordinate(current.x, current.y);
		while(currentCoordinate.x != targetCoordinate.x ||currentCoordinate.y != targetCoordinate.y) {
			currentCoordinate = new Coordinate(currentCoordinate.x, currentCoordinate.y);
			if(game.isAvailableToMove(new Coordinate(currentCoordinate.x ,currentCoordinate.y + 1)) && 
					!isvisited[currentCoordinate.x][currentCoordinate.y + 1]) {
				currentCoordinate.y++;
				path.Push(currentCoordinate);
			}
			else if(game.isAvailableToMove(new Coordinate(currentCoordinate.x + 1,currentCoordinate.y)) && 
					!isvisited[currentCoordinate.x + 1][currentCoordinate.y]) {
				currentCoordinate.x++;
				path.Push(currentCoordinate);
			}
			
			else if(game.isAvailableToMove(new Coordinate(currentCoordinate.x ,currentCoordinate.y - 1)) && 
					!isvisited[currentCoordinate.x][currentCoordinate.y - 1]) {
				currentCoordinate.y--;		
				path.Push(currentCoordinate);
			}
			
			
			else if(game.isAvailableToMove(new Coordinate(currentCoordinate.x - 1,currentCoordinate.y)) &&
					!isvisited[currentCoordinate.x - 1][currentCoordinate.y]) {
				currentCoordinate.x--;
				path.Push(currentCoordinate);
			}
			
			// etrafındaki tüm kareler ya duvar ya da daha önce o kareden geçmişse else'e giriyor
			else {
				path.Pop();
				if (!path.isEmpty())
					currentCoordinate = (Coordinate)path.Peek();
			}
			isvisited[currentCoordinate.x][currentCoordinate.y] = true; 
			
		}
		CurrentPath = path;
		ClearLastPath();
	}
	private void PlayMove() {
		if (!(game.board.map[target.x][target.y] == '1' || game.board.map[target.x][target.y] == '2' 
				|| game.board.map[target.x][target.y] == '3') || CurrentPath.isEmpty()) {
			findRandomTarget();
			findPath(target);
		}
		memoryStack = new Stack(1000);
		Coordinate last = null;
		while (!CurrentPath.isEmpty()) {
			last = (Coordinate)CurrentPath.Pop();
			memoryStack.Push(last);
		}
		
		if(game.board.map[last.x][last.y] != '#') {
			memoryStack.Pop();
			current = last; 
		}
		while (!memoryStack.isEmpty()) {
			last = (Coordinate)memoryStack.Pop();
			CurrentPath.Push(last);
		}
		
		while(!CurrentPath.isEmpty()) {
			Coordinate coord = (Coordinate) CurrentPath.Pop();
			if (game.board.map[coord.x][coord.y] == ' ') {
				game.board.map[coord.x][coord.y] = '·';
			}
			memoryStack.Push(coord);
		}
		while (!memoryStack.isEmpty()) {
			last = (Coordinate)memoryStack.Pop();
			CurrentPath.Push(last);
		}
		
	}
	
	private void findRandomTarget() {
		boolean isFound = false;
		
		while(!isFound) {
			target.x = random.nextInt(23);
			target.y = random.nextInt(55);
			
			if(game.board.map[target.x][target.y] == '1' || game.board.map[target.x][target.y] == '2' 
					|| game.board.map[target.x][target.y] == '3') {
				isFound = true;
			}
		}
	}
	
	private void ClearLastPath() {
		for(int i = 0; i < isvisited.length; i++) {
			for(int j = 0; j < isvisited[i].length; j++) {
				isvisited[i][j] = false;
			}
		}
		
		
		
		
		
		
		
	}
}
