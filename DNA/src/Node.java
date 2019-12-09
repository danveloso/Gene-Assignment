import java.util.ArrayList;

public class Node<T>{
	private T data;
	private Node<T> next;
	private Node<T> prev;
	private double chance = 1;
	public Node(T n, double chances)
	{
		this.data = n;
		chance = chances;
	}
	
	public void setChance(double n) {
		chance = chance * n;
	}
	public double getChance() {
		//System.out.println("Node chance: " + chance);
		return chance;
	}
	public T getValue() {
		return data;
	}

	
	public void setValue(T node) {
		data = node;
	}

	
	public void setNext(Node<T> n) {
		next = n;
	}

	
	public void setPrev(Node<T> n) {
		prev = n;
	}

	
	public Node getNext() {
		return next;
	}

	
	public Node getPrev() {
		return prev;
	}

	public String toString()
	{
		return data.toString();
	}
}