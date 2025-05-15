package pbb_project2;

public class Coordinate {
	public int x;
	public int y;
	
	Coordinate(int x, int y){
		this.x = x;
		this.y = y;
	}
	public Coordinate Add(Coordinate coor) {
		return new Coordinate(x + coor.x, y + coor.y);
	}
	public Coordinate Copy() {
		return new Coordinate(x, y);
	}
	public Coordinate TurnRight() {
		return new Coordinate(-y, x);
	}
	public Coordinate TurnLeft() {
		return new Coordinate(y, -x);
	}
}
