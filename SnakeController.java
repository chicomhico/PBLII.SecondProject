package pbb_project2;

import java.awt.Color;

import enigma.console.TextAttributes;

public class SnakeController {
	NumberSnake game;
	Snake[] snakes = new Snake[1000];
	private int top = 0;
	private long timer = 0;
	public SnakeController(NumberSnake game) {
		this.game = game;
	}
	public void PrintSnakes() {
		for (int i = 0; i < top; i++) {
			TextAttributes ta = new TextAttributes(Color.pink, Color.black);
			game.cn.getTextWindow().output(snakes[i].current.x, snakes[i].current.y, 'S', ta);
			SLLNode temp =snakes[i].body.headnode;
			while (temp != null) 
			{
				ta = new TextAttributes(Color.cyan, Color.BLACK);
				game.cn.getTextWindow().output(temp.location.x, temp.location.y, temp.value, ta);
				temp = temp.GetNext();
			}
		}
	}
	public boolean ContainCoordinate(Coordinate coordinate) {
		for (int i = 0; i < top; i++) {
			if (!snakes[i].Gettobedeleted()) {
				if (coordinate.x == snakes[i].current.x && coordinate.y == snakes[i].current.y)
					return true;
				if (snakes[i].body.Contains(coordinate))
					return true;
			}
		}
		return false;
	
	}
	public boolean TimeElapse(long elapsedtime) {
		timer += elapsedtime;
		if (timer>800) {
			timer -= 800;
			Playmove();
			return true;
		}
		return false;
	}
	public Snake AddSnake(Coordinate location) {
		snakes[top] = new Snake(new Coordinate(location.x, location.y), this); 
		top++;
		return snakes[top - 1];
	}
	void DeleteSnake(Snake snake) {
		int index = 0;
		while (snakes[index] != snake && index < top) {
			index++;
		}
		snakes[index] = snakes[top - 1];
		snakes[top - 1] = null;
		top--;
	}
	public boolean AvailabletoSnakeMove(Coordinate coordinate, Snake snake) {
		boolean result = game.isAvailableToMove(coordinate);
		if (!result) {
			result = IsFromAnotherSnake(coordinate, snake) != null;
		}
		return result;
	}
	private Snake IsFromAnotherSnake(Coordinate coordinate, Snake snake) {
		for (int i = 0; i < top; i++) {
			if (!snakes[i].Gettobedeleted()) {
				if (coordinate.x == snakes[i].current.x && coordinate.y == snakes[i].current.y)
					if (snakes[i] != snake)
						return snakes[i];
				if (snakes[i].body.Contains(coordinate))
					if (snakes[i] != snake)
						return snakes[i];
			}
		}
		return null;
	}
	private void DeleteToBeDeletedOnes() {
		Snake[] todeletelist = new Snake[1000];
		int todeletetop = 0;
		// checks for a trap
		int[][] directions = { {0, 1}, {1, 0}, {0, -1}, {-1, 0}, {-1, 1}, {1, -1}, {-1, -1}, {-1, -1} };
			for (int i = 0; i < top; i++) {
				for (int j = 0; j < directions.length; j++) {
				Coordinate coortoadd = new Coordinate(directions[j][0], directions[j][1]);
				Coordinate tocheck = snakes[i].current.Add(coortoadd);
				for (int k = 0; k < game.player.trapPositions.length; k++) {
					Coordinate other = game.player.trapPositions[k];
					if (tocheck.IsSame(other)) {
						todeletelist[todeletetop] = snakes[i];
						todeletetop++;
					}
				}
			}
		}
		for (int i = 0; i < top; i++) {
			if (snakes[i].Gettobedeleted()) {
				todeletelist[todeletetop] = snakes[i];
				todeletetop++;
			}	
		}
		for (int i = 0; i < todeletetop; i++) {
			DeleteSnake(todeletelist[i]);
		}
	}
	private void Playmove() {
		//plays move
		for (int i = 0; i < top; i++) {
			boolean flag = true;
			while (flag && !snakes[i].Gettobedeleted()) {
				if (snakes[i].IsStuck()) {
					snakes[i].Reverse();
					if (snakes[i].IsStuck()) {
						snakes[i].Kill();
					}
					flag = false;
				}
				else {
					Coordinate moverequest = snakes[i].GetMoveRequest();
					Coordinate targetcoor = moverequest.Add(snakes[i].current);
					Snake tocheck = IsFromAnotherSnake(targetcoor, snakes[i]);
					if (tocheck != null) {
						snakes[i].Collide(tocheck, targetcoor);
						flag = false;
					}
					else if (game.isAvailableToMove(moverequest.Add(snakes[i].current))) {
						snakes[i].AcceptRequest();
						flag = false;
					}
					else
						snakes[i].RejectRequest();
				}
			}
		}
		DeleteToBeDeletedOnes();
	}
}
