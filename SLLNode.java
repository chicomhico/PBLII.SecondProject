package pbb_project2;

public class SLLNode {
	Coordinate location;
	char value;
	private SLLNode nextnode;
	public SLLNode(Coordinate data, char value) {
		this.location = data.Copy();
		this.value = value;
	}
	public SLLNode GetNext() {
		return nextnode;
	}
	public void SetNext(SLLNode node) {
		nextnode = node;
	}
	public Coordinate getLocation()
	{
		return location;
	}
}
