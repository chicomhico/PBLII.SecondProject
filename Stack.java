package pbb_project2;
public class Stack {
	private Object[] elements;
	private int top;
	
	public Stack(int capacity) {
		elements = new Object[capacity];
		top = -1;
	}
	
	public void Push(Object data) {
		if(!isFull()) {
			top++;
			elements[top] = data;
		}
		else {
			System.out.println("Stack is full.");
		}
	}
	
	public Object Pop() {
		if(!isEmpty()) {
			Object temp = elements[top];
			elements[top] = null;
			top--;
			return temp;
		}
		else {
			System.out.println("Stack is empty.");
			return null;
		}
	}
	
	public Object Peek() {
		if(!isEmpty()) {
			return elements[top];
		}
		else {
			System.out.println("Stack is empty.");
			return null;
		}
	}
	
	public boolean isFull() {
		return (elements.length == top +1);
	}
	
	public boolean isEmpty() {
		if(top == -1) {
			elements = new Object[elements.length];
			return true;
		}
		else {
			return false;
		}
		
	}
	
	public int Size() {
		return (top+1);
	}
}
