package pbb_project2;


public class SLL {
	SLLNode headnode;
	public SLL() {
		
	}
	public SLL(Coordinate headdata, char headvalue) {
		headnode = new SLLNode(headdata, headvalue);
	}
	public SLL(SLLNode headnode) {
		this.headnode = headnode;
	}
	public void Add(Coordinate data, char value) {
		SLLNode nodetoadd = new SLLNode(data, value);
		nodetoadd.SetNext(headnode);
		headnode = nodetoadd;
	}
	public SLLNode getData(Coordinate c) {
		if (c == null)
			return null;
		SLLNode temp = headnode;
		
		while(temp != null) {
			if(temp.location.x == c.x && temp.location.y == c.y) {
				return temp;
			}
			temp = temp.GetNext();
		}
		return null;
	}
	public boolean Contains(Coordinate data) {
		if (data == null)
			return false;
		SLLNode activenode = headnode;
		
		while (activenode != null) {
			if (activenode.location.x == data.x && activenode.location.y == data.y)
				return true;
			activenode = activenode.GetNext();
		}
		return false;
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
	public void RemoveFirst() {
		headnode = headnode.GetNext();
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
	public int Size() {
		int result = 0;
		SLLNode tocheck = headnode;
		while (tocheck != null) {
			result ++;
			tocheck = tocheck.GetNext();
		}
		return result;
	}
	private SLLNode Reverseform() {
		if (headnode == null) {
			return null;
		}
		SLL result = new SLL();
		SLLNode toadd = headnode;
		while (toadd != null) {
			result.Add(toadd.location, toadd.value);
			toadd = toadd.GetNext();
		}
		return result.headnode;
	}
	public void Reverse() {
		headnode = Reverseform();
	}
}
