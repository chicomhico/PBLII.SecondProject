package pbb_project2;

public class SLLNode {
	Coordinate data;
	char value;
	private SLLNode nextnode;
	public SLLNode(Coordinate data, char value) {
		this.data = data;
		this.value = value;
	}
	public SLLNode GetNext() {
		return nextnode;
	}
	public void SetNext(SLLNode node) {
		nextnode = node;
	}
}
