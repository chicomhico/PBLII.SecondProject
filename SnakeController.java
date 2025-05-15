package pbb_project2;

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
			game.cn.getTextWindow().output(snakes[i].current.y, snakes[i].current.x, 'S');
		}
	}
	public boolean ContainCoordinate(Coordinate coordinate) {
		for (int i = 0; i < top; i++) {
			if (coordinate.x == snakes[i].current.y && coordinate.y == snakes[i].current.x)
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
		snakes[top] = new Snake(new Coordinate(location.y, location.x), this); 
		top++;
	}
	private void Playmove() {
		for (int i = 0; i < top; i++) {
			boolean flag = true;
			while (flag) {
				Coordinate coordinate = snakes[i].GetMoveRequest();
				if (game.isAvailableToMove(coordinate.Add(snakes[i].current))) {
					snakes[i].AcceptRequest();
					flag = false;
				}
				snakes[i].RejectRequest();
			}
		}
	}
}
