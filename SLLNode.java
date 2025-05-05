package pbb_project2;

public class SLLNode {
	Object data;
	SLLNode nextnode;
	public SLLNode(Object data) {
		this.data = data;
	}
	public SLLNode GetNext() {
		return nextnode;
	}
	public void SetNext(SLLNode node) {
		nextnode = node;
	}
}
