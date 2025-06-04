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
			if (coordinate.x == snakes[i].current.x && coordinate.y == snakes[i].current.y)
				return true;
			if (snakes[i].body.Contains(coordinate))
				return true;
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
	public void AddSnake(Coordinate location) {
		snakes[top] = new Snake(new Coordinate(location.x, location.y), this); 
		top++;
	}
	private void DeleteSnake(int index) {
		snakes[index] = snakes[top];
		snakes[top] = null;
		top--;
	}
	private void DeleteSnake(Snake snake) {
		int index = 0;
		while (snakes[index] != snake) {
			index++;
		}
		snakes[index] = snakes[top];
		snakes[top] = null;
		top--;
	}
	private Snake IsFromAnotherSnake(Coordinate coordinate, Snake snake) {
		for (int i = 0; i < top; i++) {
			if (coordinate.x == snakes[i].current.x && coordinate.y == snakes[i].current.y)
				if (snakes[i] != snake)
					return snake;
			if (snakes[i].body.Contains(coordinate))
				if (snakes[i] != snake)
					return snake;
		}
		return null;
	}
	private void Playmove() {
		for (int i = 0; i < top; i++) {
			boolean flag = true;
			while (flag) {
				if (snakes[i].IsStuck()) {
					snakes[i].Reverse();
					flag = false;
				}
				else {
					Coordinate coordinate = snakes[i].GetMoveRequest();
					Snake tocheck = IsFromAnotherSnake(coordinate.Add(snakes[i].current), snakes[i]);
					if (tocheck != null) {
						snakes[i].Collide(tocheck);
						if(tocheck.Gettobedeleted()) 
							DeleteSnake(tocheck);
						if(snakes[i].Gettobedeleted()) 
							DeleteSnake(i);
						flag = false;
					}
					if (game.isAvailableToMove(coordinate.Add(snakes[i].current))) {
						snakes[i].AcceptRequest();
						flag = false;
					}
					snakes[i].RejectRequest();
				}
			}
		}
	}
}
