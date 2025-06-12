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
	public void Kill() {
		tobedeleted = true;
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
			boolean result = !controller.AvailabletoSnakeMove(current.Add(new Coordinate(0, 1)), this);
			result = result && !controller.AvailabletoSnakeMove(current.Add(new Coordinate(0, -1)), this);
			result = result && !controller.AvailabletoSnakeMove(current.Add(new Coordinate(1, 0)), this);
			result = result && !controller.AvailabletoSnakeMove(current.Add(new Coordinate(-1, 0)), this);
			return result;
		}
		boolean result = !controller.AvailabletoSnakeMove(current.Add(lastmove), this);
		result = result && !controller.AvailabletoSnakeMove(current.Add(lastmove.TurnLeft()), this);
		result = result && !controller.AvailabletoSnakeMove(current.Add(lastmove.TurnRight()), this);
		return result;
	}
	
	public void Reverse() { //reverses snake
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
		//if there is no target finds target
		if (target == null)
			FindRandomTarget();
		//if it has played any move it chooses from 3 direction(in its random move :D and %80 goes same way)
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
		//if it has not played a move it plays random 4 direction(in its random move :D)
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
		//plays last move
		decidetoturn = false;
		lastmove = moveinprocess;
		Coordinate tempp = current.Copy();
		current = current.Add(lastmove);
		char currentdata = controller.game.board.GetCoor(current);
		if (currentdata == '1' || currentdata == '2' || currentdata == '3') 
		{
			//eats that treasure
			body.Add(tempp, currentdata);
			controller.game.board.SetCoor(current, ' ');
			if (currentdata == '1') {
				controller.game.computer.computer_score += 1;
			}
			else if (currentdata == '2') {
				controller.game.computer.computer_score += 4;
			}
			else {
				controller.game.computer.computer_score += 16;
			}
		}
		else 
		{
			//pushes its body
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
		if (currentdata == '@') {
			controller.game.board.SetCoor(current, ' ');
			controller.game.computer.computer_score += 10;
		}
	}
	
	private SLL SplitFrom(SLLNode node) {
		//makes previous next null and returns node as SLL
		SLLNode headnode = body.headnode;
		if (node == null || headnode == null)
			return null;

		if (headnode == node) {
			SLL newList = new SLL(headnode); 
			body.headnode = null; 
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
	
	private void Combine(Snake other , Coordinate collisioncoordinate) {
		//some precalculations
		int totalsize = body.Size() + other.body.Size();
		int toaddstart = 0;
		SLLNode tocheck = other.body.headnode;
		Coordinate lastdirection = new Coordinate(0, 0);
		while (tocheck.location.x != collisioncoordinate.x 
				&& tocheck.location.y != collisioncoordinate.y) {
			toaddstart++;
			SLLNode temp = tocheck;
			tocheck = tocheck.GetNext();
			lastdirection = tocheck.location.Minus(temp.location);
		}
		SLL otherbody =  new SLL(other.body.headnode);
		SLL thisbody = new SLL(body.headnode);
		SLL lastpart = new SLL(tocheck.GetNext());
		tocheck.SetNext(null);
		tobedeleted = true;
		int targetsize = totalsize - toaddstart - 1;
		SLL coordinatestoadd = new SLL();
		SLL alladdedcoordinates = new SLL();
		Coordinate lastcoor = tocheck.location;
		//finding way to add remaining parts
		while (coordinatestoadd.Size() < targetsize) {
			// tries every possible way
			int triedways = 0;
			Coordinate coortocheck = lastcoor.Add(lastdirection);
			boolean iscontains = controller.game.isAvailableToMove(coortocheck) 
					&& !alladdedcoordinates.Contains(coortocheck);
			while (triedways < 4 && !iscontains) {
				lastdirection = lastdirection.TurnRight();
				triedways++;
				coortocheck = lastcoor.Add(lastdirection);
				iscontains = controller.game.isAvailableToMove(coortocheck) 
						&& !alladdedcoordinates.Contains(coortocheck);
			}
			if (triedways == 4) {
				//if all ways are closed
				//if there is no way to add that much part both disappear
				if (coordinatestoadd.headnode == null) {
					other.tobedeleted = true;
					return;
				}
				coordinatestoadd.RemoveFirst();
				if (coordinatestoadd.headnode != null)
					lastcoor = coordinatestoadd.headnode.location;
				else 
					lastcoor = tocheck.location;
			}
			else {
				lastcoor = coortocheck;
				coordinatestoadd.Add(lastcoor, '-');
				alladdedcoordinates.Add(lastcoor, '-');
			}
		}
		/*try {
			Thread.sleep(50000);
		} catch (Exception e) {
			
		}*/
		//execute check
		/*while (coordinatestoadd.Size() >1) {
			Coordinate first = coordinatestoadd.headnode.location;
			coordinatestoadd.RemoveFirst();
			Coordinate second = coordinatestoadd.headnode.location;
			int result = first.x - second.x;
			if (result <0)
				result = -result;
			int res2 = first.y - second.y;
			if (res2 <0)
				res2 = -res2;
			while (result+res2 > 1)
				System.out.print("bruh");
		}*/
		//execute precalculations
		otherbody.Reverse();
		thisbody.Reverse();
		lastpart.Reverse();
		coordinatestoadd.Reverse();
		SLL newbody = new SLL();
		// execute results
		while (coordinatestoadd.Size() > 0) {
			if (lastpart.Size() > 0) {
				newbody.Add(coordinatestoadd.headnode.location, lastpart.headnode.value);
				lastpart.RemoveFirst();
			}
			else if (thisbody.Size() > 0){
				newbody.Add(coordinatestoadd.headnode.location, thisbody.headnode.value);
				thisbody.RemoveFirst();
			}
			else {
				System.out.println("there is a problem");
				System.out.println("there is a problem");
			}
			coordinatestoadd.RemoveFirst();
		}
		newbody.Reverse();
		while (otherbody.Size() > 0) {
			newbody.Add(otherbody.headnode.location, otherbody.headnode.value);
			otherbody.RemoveFirst();
		}
		//newbody.Reverse();
		other.body = newbody;
	}
	
	public void Collide(Snake snake , Coordinate collisioncoordinate) {
		if(snake.current.x == collisioncoordinate.x && snake.current.y == collisioncoordinate.y) {
			//delete both
			tobedeleted = true;
			snake.tobedeleted = true;
			return;
		}
		
		else if(snake.body.getData(collisioncoordinate) != null) {
			if(snake.body.getData(collisioncoordinate).value == '1') {
				//combining
				Combine(snake, collisioncoordinate);
			}
			else if(snake.body.getData(collisioncoordinate).value == '2' || snake.body.getData(collisioncoordinate).value == '3') {
				// splitting
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
		//enters randommode if it does not in randommode
		if (randommove == 0)
			randommove += 20;
	}
	private void FindRandomTarget() {
		boolean isFound = false;
		int trycount = 0; // this avaids infinite looop in case of there is no treasure
		if (controller.game.board != null && trycount < 1000000) {
			while(!isFound) {
				target.x = random.nextInt(55);
				target.y = random.nextInt(23);
				
				if(controller.game.board.GetCoor(target) == '1' 
						|| controller.game.board.GetCoor(target) == '2' 
						|| controller.game.board.GetCoor(target) == '3') {
					isFound = true;
				}
			}
			trycount++;
		}
	}
	public SLL getBody() {
	    return body;
	}
}
