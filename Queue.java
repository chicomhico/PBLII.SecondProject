package pbb_project2;

public class Queue {
	private int rear, front;
	private Object[] elements;
	private int count;

	public Queue(int capacity) {
		elements = new Object[capacity];
		rear = -1;
		front = 0;
		count = 0;
	}

	public void enqueue(Object data) {
		if (!isFull()) {
			rear = (rear + 1) % elements.length;
			elements[rear] = data;
			count++;
		}
	}

	public Object dequeue() {
		if (isEmpty()) return null;
		Object retData = elements[front];
		elements[front] = null;
		front = (front + 1) % elements.length;
		count--;
		return retData;
	}

	public Object peek() {
		if (isEmpty()) return null;
		return elements[front];
	}

	public boolean isEmpty() {
		return count == 0;
	}

	public boolean isFull() {
		return count == elements.length;
	}

	public int size() {
		return count;
	}
	public Object get(int index) {
	    if (index < 0 || index >= count) return null;
	    int realIndex = (front + index) % elements.length;
	    return elements[realIndex];
	}
}

