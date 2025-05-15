package pbb_project2;

public class Trap 
{
	private int x,y;
	private long startTime;
	private boolean active;
	
	public Trap()
	{
		active = false;
	}
	
	public boolean isActive()
	{
		return active;
	}
	
	public void Place(int x, int y)
	{
		this.x = x;
		this.y = y;
		this.startTime = NumberSnake.timer;
		active = true;
  }
	public boolean isTheTimeUp()
	{
		boolean timeisup = false;
		if (active == true && NumberSnake.timer - startTime >= 10)
		{
			timeisup = true;
		}
		
		return timeisup;
	}
	
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
}
