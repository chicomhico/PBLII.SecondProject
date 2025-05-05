package pbb_project2;

public class SLL {
	SLLNode headnode;
	public SLL(Object headdata) {
		headnode = new SLLNode(headdata);
	}
	public void Add(Object data) {
		SLLNode nodetoadd = new SLLNode(data);
		nodetoadd = headnode.GetNext();
		headnode.SetNext(nodetoadd);
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
	public boolean Contains(Object data) {
		if (data == null)
			return false;
		SLLNode activenode = headnode;
		while (activenode != null) {
			if (activenode.data == data)
				return true;
			activenode = activenode.GetNext();
		}
		return false;
	}
	public void Remove(SLLNode node) {
		
	}
}
