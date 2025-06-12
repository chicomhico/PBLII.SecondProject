package pbb_project2;
import java.util.Random;

public class Computer {
	public Computer(Coordinate start , NumberSnake game) {
		current = start;
		this.game = game;
		ClearLastPath();
	}
	NumberSnake game;//link to the game
	private Random random = new Random();
	private Coordinate target = new Coordinate(0,0);
	Coordinate current;
	private Stack CurrentPath = new Stack(100000);
	private int timer = 0;
	private boolean[][] isvisited = new boolean[55][23];
	public int computer_score = 200;
	public Coordinate GetTarget() {
		return target.Copy();
	}
	public boolean TimeElapse(long elapsedtime) {
		//result says does the map changed
		timer += elapsedtime;
		if (timer > 400) {
			timer -= 400;
			PlayMove();
			//collecting treasure
			if (game.board.GetCoor(current) == '·')
				game.board.SetCoor(current, ' ');
			if(game.board.GetCoor(current) == '1') {
				computer_score += 1;
				game.board.SetCoor(current, ' ');
			}
			else if(game.board.GetCoor(current) == '2') {
				computer_score += 4;
				game.board.SetCoor(current, ' ');
			}
			else if(game.board.GetCoor(current) == '3') {
				computer_score += 16;
				game.board.SetCoor(current, ' ');
			}
			else if(game.board.GetCoor(current) == '@') {
				computer_score += 50;
				game.board.SetCoor(current, ' ');
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
			//tries every way
			if(game.isAvailableToMove(new Coordinate(currentCoordinate.x + 1,currentCoordinate.y)) && 
					!isvisited[currentCoordinate.x + 1][currentCoordinate.y]) {
				currentCoordinate.x++;
				path.Push(currentCoordinate);
			}
			else if(game.isAvailableToMove(new Coordinate(currentCoordinate.x ,currentCoordinate.y + 1)) && 
					!isvisited[currentCoordinate.x][currentCoordinate.y + 1]) {
				currentCoordinate.y++;		
				path.Push(currentCoordinate);
			}
			else if(game.isAvailableToMove(new Coordinate(currentCoordinate.x - 1,currentCoordinate.y)) &&
					!isvisited[currentCoordinate.x - 1][currentCoordinate.y]) {
				currentCoordinate.x--;
				path.Push(currentCoordinate);
			}
			else if(game.isAvailableToMove(new Coordinate(currentCoordinate.x ,currentCoordinate.y - 1)) && 
					!isvisited[currentCoordinate.x][currentCoordinate.y - 1]) {
				currentCoordinate.y--;
				path.Push(currentCoordinate);
			}
			
			
			
			// if all the surrounding squares are either walls or have already been visited, it goes into the else block.
			else {
				//if there is no path returns
				if (path.isEmpty()) {
					CurrentPath = path;
					//deletes signature of isvisited
					ClearLastPath();
					return;
				}
				path.Pop();
				if (!path.isEmpty())
					currentCoordinate = (Coordinate)path.Peek();
			}
			isvisited[currentCoordinate.x][currentCoordinate.y] = true; 
			
		}
		CurrentPath = path;
		//deletes signature of isvisited
		ClearLastPath();
	}
	private void PlayMove() {
		//if target has eaten or computer gets target finds new one
		if (!(game.board.GetCoor(target) == '1' || game.board.GetCoor(target) == '2' 
				|| game.board.GetCoor(target) == '3') || CurrentPath.isEmpty()) {
			while(!CurrentPath.isEmpty()) {
				Coordinate coord = (Coordinate) CurrentPath.Pop();
				if (game.board.GetCoor(coord) == '·') {
					game.board.SetCoor(coord, ' ');
				}
			}
			findRandomTarget();
			findPath(target);
			// if there is no way to go it waits for 1 computer tick
			if (CurrentPath.isEmpty()) {
				return;
			}
		}
		//reads first element of the path
		Stack memoryStack = new Stack(1000);
		Coordinate last = null;
		while (!CurrentPath.isEmpty()) {
			last = (Coordinate)CurrentPath.Pop();
			memoryStack.Push(last);
		}
		
		if(game.isAvailableToMove(last)) {
			memoryStack.Pop();
			current = last; 
		}
		while (!memoryStack.isEmpty()) {
			last = (Coordinate)memoryStack.Pop();
			CurrentPath.Push(last);
		}
		
		//shows path
		while(!CurrentPath.isEmpty()) {
			Coordinate coord = (Coordinate) CurrentPath.Pop();
			if (game.board.GetCoor(coord) == ' ') {
				game.board.SetCoor(coord, '·');
			}
			memoryStack.Push(coord);
		}
		//makes every normal
		while (!memoryStack.isEmpty()) {
			last = (Coordinate)memoryStack.Pop();
			CurrentPath.Push(last);
		}
		
	}
	
	private void findRandomTarget() {
		boolean isFound = false;
		int trycount = 0;//avoids infinite loop in case of there is no treasure
		while(!isFound && trycount < 1000000) {
			target.x = random.nextInt(55);
			target.y = random.nextInt(23);
			
			if(game.board.GetCoor(target) == '1' || game.board.GetCoor(target) == '2' 
					|| game.board.GetCoor(target) == '3') {
				isFound = true;
			}
			trycount++;
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
