package pbb_project2;

import java.util.Random;

public class Snake {
	private Random random = new Random();
	private boolean decidetoturn = false;
	private int randommove = 0;
	
	SLL body = new SLL();
	
	private boolean tobedeleted = false;
	
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
	public boolean Gettobedeleted() {
		return tobedeleted;
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
		{
			boolean result = !controller.game.isAvailableToMove(current.Add(new Coordinate(0, 1)));
			result = result && !controller.game.isAvailableToMove(current.Add(new Coordinate(0, -1)));
			result = result && !controller.game.isAvailableToMove(current.Add(new Coordinate(1, 0)));
			result = result && !controller.game.isAvailableToMove(current.Add(new Coordinate(-1, 0)));
			return result;
		}
		boolean result = !controller.game.isAvailableToMove(current.Add(lastmove));
		result = result && !controller.game.isAvailableToMove(current.Add(lastmove.TurnLeft()));
		result = result && !controller.game.isAvailableToMove(current.Add(lastmove.TurnRight()));
		return result;
	}
	
	public void Reverse() { //buraya reversenin kalanı
		Coordinate tempp = current.Copy();
		lastmove = null;
		SLLNode toprocess = body.headnode;
		Coordinate toassign = tempp;
		while (toprocess != null) 
		{
			Coordinate temp = toprocess.location;
			toprocess.location = toassign;
			toassign = temp;
			toprocess = toprocess.GetNext();
		}
		current = toassign;
		body.Reverse();
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
		Coordinate tempp = current.Copy();
		current = current.Add(lastmove);
		//buraya yemesi ve bodynin ilerlemesi yapılacak
		char currentdata = controller.game.board.GetCoor(current);
		if (currentdata == '1' || currentdata == '2' || currentdata == '3') 
		{
			body.Add(tempp, currentdata);
			controller.game.board.SetCoor(current, ' ');
		}
		else 
		{
			SLLNode toprocess = body.headnode;
			Coordinate toassign = tempp;
			while (toprocess != null) 
			{
				Coordinate temp = toprocess.location;
				toprocess.location = toassign;
				toassign = temp;
				toprocess = toprocess.GetNext();
			}
		}
	}
	
	private SLL SplitFrom(SLLNode node) {
		SLLNode headnode = body.headnode;
		if (node == null || headnode == null)
			return null;

		if (headnode == node) {
			SLL newList = new SLL(headnode); 
			headnode = null; 
			return newList;
		}

		
		SLLNode temp = headnode;
		while (temp != null && temp.GetNext() != null) {
			if (temp.GetNext() == node) {
				SLL newList = new SLL(node);      
				temp.SetNext(null);              
				return newList;
			}
			temp = temp.GetNext();
		}

		return null;
	}
	public void Collide(Snake snake , Coordinate collisioncoordinate) {
		if(snake.current.x == collisioncoordinate.x && snake.current.y == collisioncoordinate.y) {
			//silinme
			tobedeleted = true;
			snake.tobedeleted = true;
			System.out.print("deleted both");
			return;
		}
		
		else if(snake.body.getData(collisioncoordinate) != null) {
			if(snake.body.getData(collisioncoordinate).value == '1') {
				//birleşme
				if (false) {
				SLLNode snake2 = body.headnode;
				SLLNode snake1 = snake.body.getData(collisioncoordinate);
				while(snake2 != null) {
					snake1.SetNext(snake2);
					snake1 = snake1.GetNext();
					snake2 = snake2.GetNext();
				}
				tobedeleted = true;}
			}
			else if(snake.body.getData(collisioncoordinate).value == '2' || snake.body.getData(collisioncoordinate).value == '3') {
				// bölünme
				SLLNode colNode = snake.body.getData(collisioncoordinate);
			    
			    SLL splited = snake.SplitFrom(colNode);
			    
			    Snake addedsnake = controller.AddSnake(colNode.location.Copy());
			    
			    addedsnake.body = splited;
			    
			    addedsnake.Reverse();

			    this.Reverse();

			}
		}
		
		

	}
	public void RejectRequest() {
		if (randommove == 0)
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
