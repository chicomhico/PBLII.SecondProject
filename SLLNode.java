package pbb_project2;

public class SLLNode {
	Coordinate data;
	private SLLNode nextnode;
	public SLLNode(Coordinate data) {
		this.data = data;
	}
	public SLLNode GetNext() {
		return nextnode;
	}
	public void SetNext(SLLNode node) {
		nextnode = node;
	}
}
