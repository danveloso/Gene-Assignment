import java.util.Arrays;

public class ArrayList<T> {

    private T data;
    private int size = 10000000;
    private int length = 0;
    private double chance = 1;
    private Node[] array = new Node[size];

    public void setChance(int i, double n) {
    	array[i].setChance(n);
    	chance = n;
    }
    public double getChance(int i) {
    	//System.out.println("ArrayList chance: " + array[i].getChance());
    	return array[i].getChance();
    }
    public void addNode(Node n){
        array[length++] = new Node(n,n.getChance());
    }
    
    public Node removeNode(int i){
        for (int loop = i; loop < length - 1; loop++) array[loop] = array[loop + 1];
        length = Math.max(length - 1, 0);
        return null; //Successfully removed
    }

    
    public Node getNode(int i){
        return array[i];
    }

    
    public Node getFirstNode(){
        return array[0];
    }

    
    public Node getLastNode(){
        if (length == 0) return null;
        return array[length-1];
    }

    
    public int size(){
        return length;
    }
}