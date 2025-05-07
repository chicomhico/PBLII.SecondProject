package pbb_project2;

public class Computer {
	public Computer(Coordinate start) {
		current = start;
	}
	Coordinate current;
	public Stack findPath(Coordinate targetCoordinate) {
		Stack path = new Stack(100);
		Coordinate currentCoordinate = new Coordinate(current.x, current.y);
		while(currentCoordinate.x != targetCoordinate.x ||currentCoordinate.y != targetCoordinate.y) {
			path.Push(currentCoordinate);
			
			if(Player.mapData[currentCoordinate.x + 1][currentCoordinate.y] != '#' && 
					Player.mapData[currentCoordinate.x + 1][currentCoordinate.y] != '^') {
				currentCoordinate.x++;
			}
			else if(Player.mapData[currentCoordinate.x][currentCoordinate.y - 1] != '#' && 
					Player.mapData[currentCoordinate.x][currentCoordinate.y - 1] != '^') {
				currentCoordinate.y--;				
			}
			else if(Player.mapData[currentCoordinate.x - 1][currentCoordinate.y] != '#' &&
					Player.mapData[currentCoordinate.x - 1][currentCoordinate.y] != '^') {
				currentCoordinate.x--;
			}
			else if(Player.mapData[currentCoordinate.x][currentCoordinate.y + 1] != '#' && 
					Player.mapData[currentCoordinate.x][currentCoordinate.y + 1] != '^') {
				currentCoordinate.y++;
			}
			
			// etrafındaki tüm kareler ya duvar ya da daha önce o kareden geçmişse else'e giriyor
			else {
				currentCoordinate = (Coordinate)path.Pop();
			}
			System.out.println(currentCoordinate.x + " , " + currentCoordinate.y);
			/*else {
				if(Player.mapData[currentCoordinate.x + 1][currentCoordinate.y] != '#') {
					currentCoordinate.x++;
				}
				else if(Player.mapData[currentCoordinate.x][currentCoordinate.y - 1] != '#') {
					currentCoordinate.y--;
				}
				else if(Player.mapData[currentCoordinate.x - 1][currentCoordinate.y] != '#') {
					currentCoordinate.x--;
				}
				else {
					currentCoordinate.y++;
				}
			}*/
			
			//Daha önce ordan geçmiş mi diye kontrol etmek için	
			Player.mapData[currentCoordinate.x][currentCoordinate.y] = '^'; 
			
		}
		
		return path;
		
		
	}
}
