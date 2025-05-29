package pbb_project2;


public class SLL {
	private SLLNode headnode;
	public SLL() {
		
	}
	public SLL(Coordinate headdata) {
		headnode = new SLLNode(headdata);
	}
	public SLL(SLLNode headnode) {
		this.headnode = headnode;
	}
	public void Add(Coordinate data) {
		SLLNode nodetoadd = new SLLNode(data);
		nodetoadd.SetNext(headnode);
		headnode = nodetoadd;
	}
	public boolean Contains(SLLNode node) {
		if (node == null)
			return false;
		SLLNode activenode = headnode;
		while (activenode != null) {
			if (activenode == node)
				return true;
			activenode = activenode.GetNext();
		}
		return false;
	}
	public boolean Contains(Coordinate data) {
		if (data == null)
			return false;
		SLLNode activenode = headnode;
		while (activenode != null) {
			if (activenode.data.x == data.x && activenode.data.y == data.y)
				return true;
			activenode = activenode.GetNext();
		}
		return false;
	}
	public void Remove(SLLNode node) {
		if (node == null)
			return;
		if (headnode == node) {
			headnode = headnode.GetNext();
			return;
		}
		if (headnode == null) {
			return;
		}
		SLLNode previous = headnode;
		SLLNode activenode = headnode.GetNext();
		while (activenode != null) {
			if (activenode == node) {
				previous.SetNext(activenode.GetNext());
				return;
			}
			previous = activenode;
			activenode = activenode.GetNext();
		}
	}
	public void Combine(SLL other) {
		if (other == null)
			return;
		SLLNode activenode = headnode;
		while (activenode != null) {
			if (activenode.GetNext() == null)
				activenode.SetNext(other.headnode);
		}
	}
}
