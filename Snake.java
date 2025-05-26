package pbb_project2;

import java.util.Random;

public class Snake {
	private Random random = new Random();
	private boolean decidetoturn = false;
	private int randommove = 0;
	
	private Coordinate moveinprocess;
	private Coordinate lastmove;
	private Coordinate target = new Coordinate(0,0);
	
	Coordinate current;
	SnakeController controller;
	public Snake(Coordinate location, SnakeController controller) {
		current = location.Copy();
		this.controller = controller;
		FindRandomTarget();
	}
	private static int Sign(int input) {
		if (input > 0)
			return 1;
		if (input < 0)
			return -1;
		return 0;
	}
	public boolean IsStuck() {
		if (lastmove == null)
			return false;
		boolean result = !controller.game.isAvailableToMove(current.Add(lastmove));
		result = result && !controller.game.isAvailableToMove(current.Add(lastmove.TurnLeft()));
		result = result && !controller.game.isAvailableToMove(current.Add(lastmove.TurnRight()));
		return result;
	}
	public void Reverse() {
		lastmove = lastmove.Negate();
	}
	public Coordinate GetMoveRequest() {
		if (target == null)
			FindRandomTarget();
		if (lastmove != null) {
			if (randommove == 0) {
				int difference = Sign(target.x-current.x);
				if (difference == 0) {
					difference = Sign(target.y-current.y);
					moveinprocess = new Coordinate(0, difference);
					return moveinprocess;
				}
				moveinprocess = new Coordinate(difference, 0);
				return moveinprocess;
			}
			int randomnumber = random.nextInt(100);
			if ((randomnumber < 80 && !decidetoturn && lastmove != null)||randomnumber < 1) {
				moveinprocess = lastmove.Copy();
				return moveinprocess;
			}
			decidetoturn = true;
			randomnumber = random.nextInt(100);
			if (randomnumber < 50){
				moveinprocess = lastmove.TurnRight();
				return moveinprocess;
			}
			moveinprocess = lastmove.TurnLeft();
			return moveinprocess;
		}
		int randomint = random.nextInt(4);
		if (randomint == 0)
			moveinprocess = new Coordinate(0, 1);
		if (randomint == 1)
			moveinprocess = new Coordinate(0, -1);
		if (randomint == 2)
			moveinprocess = new Coordinate(1, 0);
		if (randomint == 3)
			moveinprocess = new Coordinate(-1, 0);
		return moveinprocess;
	}
	public void AcceptRequest() {
		decidetoturn = false;
		lastmove = moveinprocess;
		current = current.Add(lastmove);
	}
	public void RejectRequest() {
		randommove += 20;
	}
	private void FindRandomTarget() {
		boolean isFound = false;
		if (controller.game.board != null) {
			while(!isFound) {
				target.x = random.nextInt(55);
				target.y = random.nextInt(23);
				
				if(controller.game.board.GetCoor(target) == '1' 
						|| controller.game.board.GetCoor(target) == '2' 
						|| controller.game.board.GetCoor(target) == '3') {
					isFound = true;
				}
			}
		}
	}
}
