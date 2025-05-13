package pbb_project2;
import java.util.Random;

public class Computer {
	public Computer(Coordinate start) {
		current = start;
	}
	Random random = new Random();
	Coordinate target = new Coordinate(0,0);
	Coordinate current;
	Stack CurrentPath = new Stack(100000);
	Stack memoryStack = new Stack(100000);
	public Stack findPath(Coordinate targetCoordinate) {
		Stack path = new Stack(1000);
		Coordinate currentCoordinate = new Coordinate(current.x, current.y);
		while(currentCoordinate.x != targetCoordinate.x ||currentCoordinate.y != targetCoordinate.y) {
			currentCoordinate = new Coordinate(currentCoordinate.x, currentCoordinate.y);
			if(Player.mapData[currentCoordinate.x + 1][currentCoordinate.y] != '#' && 
					Player.mapData[currentCoordinate.x + 1][currentCoordinate.y] != '^') {
				currentCoordinate.x++;
				path.Push(currentCoordinate);
			}
			else if(Player.mapData[currentCoordinate.x][currentCoordinate.y - 1] != '#' && 
					Player.mapData[currentCoordinate.x][currentCoordinate.y - 1] != '^') {
				currentCoordinate.y--;		
				path.Push(currentCoordinate);
			}
			else if(Player.mapData[currentCoordinate.x - 1][currentCoordinate.y] != '#' &&
					Player.mapData[currentCoordinate.x - 1][currentCoordinate.y] != '^') {
				currentCoordinate.x--;
				path.Push(currentCoordinate);
			}
			else if(Player.mapData[currentCoordinate.x][currentCoordinate.y + 1] != '#' && 
					Player.mapData[currentCoordinate.x][currentCoordinate.y + 1] != '^') {
				currentCoordinate.y++;
				path.Push(currentCoordinate);
			}
			
			// etrafındaki tüm kareler ya duvar ya da daha önce o kareden geçmişse else'e giriyor
			else {
				path.Pop();
				currentCoordinate = (Coordinate)path.Peek();
			}
			Player.mapData[currentCoordinate.x][currentCoordinate.y] = '^'; 
		}
		return path;
	}
	public void PlayMove() {
		findRandomTarget();
		CurrentPath = findPath(target);
		Coordinate last = new Coordinate(0,0);
		while (!CurrentPath.isEmpty()) {
			last = (Coordinate)CurrentPath.Pop();
			memoryStack.Push(last);
		}
		memoryStack.Pop();
		current = last; // bu koda orası engel mi diye bakmalı
		while (!memoryStack.isEmpty()) {
			last = (Coordinate)memoryStack.Pop();
			CurrentPath.Push(last);
		}
	}
	
	private void findRandomTarget() {
		boolean isFound = false;
		
		while(!isFound) {
			target.x = random.nextInt(55);
			target.y = random.nextInt(23);
			
			if(Player.mapData[target.x][target.y] == '1' || Player.mapData[target.x][target.y] == '2' 
					|| Player.mapData[target.x][target.y] == '3') {
				isFound = true;
			}
		}
	}
}
