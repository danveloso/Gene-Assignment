public class ArrayQueue<T> {
    private int length = 0;
    private double chance = 0;
    private ArrayList array = new ArrayList();

    public void setChance(double n) {
    	array.setChance(0,n);
    	chance = n;
    }
    public double getChance() {
    	//System.out.println("ArrayQueue chance: " + array.getChance(0));
    	return array.getChance(0);
    }
    public void enqueue(Node<T> node){
    	chance = node.getChance();
        array.addNode(node);
        length++;
    }

    
    public Node<T> dequeue(){
        if (array.size() <= 0) return null;
        Node temp = array.getFirstNode();
        array.removeNode(0);
        length--;
        return temp;
    }

    
    public Node<T> peek(){
        return array.getFirstNode();
    }

    
    public int size(){
        return length;
    }

    
    public boolean isEmpty(){
        return length == 0;
    }
}