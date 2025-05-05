package pbb_project2;

public class Computer {
	Player player;
	public Computer(Player player) {
		this.player = player;
	}
	public Stack findPath(Coordinate currentCoordinate, Coordinate targetCoordinate) {
		Stack path = new Stack(5000);
		
		while(currentCoordinate != targetCoordinate) {
			path.Push(currentCoordinate);
			
			if(player.mapData[currentCoordinate.x + 1][currentCoordinate.y] != '#' && 
					player.mapData[currentCoordinate.x + 1][currentCoordinate.y] != '^') {
				currentCoordinate.x++;
			}
			else if(player.mapData[currentCoordinate.x][currentCoordinate.y - 1] != '#' && 
					player.mapData[currentCoordinate.x][currentCoordinate.y - 1] != '^') {
				currentCoordinate.y--;				
			}
			else if(player.mapData[currentCoordinate.x - 1][currentCoordinate.y] != '#' &&
					player.mapData[currentCoordinate.x - 1][currentCoordinate.y] != '^') {
				currentCoordinate.x--;
			}
			else if(player.mapData[currentCoordinate.x][currentCoordinate.y + 1] != '#' && 
					player.mapData[currentCoordinate.x][currentCoordinate.y + 1] != '^') {
				currentCoordinate.y++;
			}
			
			// etrafındaki tüm kareler ya duvar ya da daha önce o kareden geçmişse else'e giriyor
			// currentcoordinate = stack.Pop();
			else {
				if(player.mapData[currentCoordinate.x + 1][currentCoordinate.y] != '#') {
					currentCoordinate.x++;
				}
				else if(player.mapData[currentCoordinate.x][currentCoordinate.y - 1] != '#') {
					currentCoordinate.y--;
				}
				else if(player.mapData[currentCoordinate.x - 1][currentCoordinate.y] != '#') {
					currentCoordinate.x--;
				}
				else {
					currentCoordinate.y++;
				}
			}
			
			//Daha önce ordan geçmiş mi diye kontrol etmek için	
			player.mapData[currentCoordinate.x][currentCoordinate.y] = '^'; 
			
		}
		
		return path;
		
		
	}
}
