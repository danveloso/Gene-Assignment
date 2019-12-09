/*Name: Daniel Veloso Tamargo
 * Date: 2019/12/12
 * Class: ICS04U1
 * Teacher: Mr.R
 * Project: Determining the highest probability that a particular gene, P, can mutate into another gene, Q,
 * within a certain number of mutations M?
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
public class Gene {
	public int maxLength;
	public   Node<String>[] validGenes;
	public   int maxMutations;
	public   int maxTests;
	public double mut1Chance = 0.02;
	public double mut2Chance = 0.06;
	public double mut3Chance = 0.08;
	public Node<String>[][] tests;
	ArrayQueue queue = new ArrayQueue();
	ArrayList<Node> gene2;
	public static void main(String[] args){
		Gene gene = new Gene();
		
		//importing all data from given file
		gene.setup(10,20000,20,10,"GENES.txt");
		
		for(int i = 0; i < gene.maxTests; i++) { //testing through each of the test cases
			double tempValue = gene.testing(gene.tests[i][0],gene.tests[i][1],gene.maxMutations);
			if(tempValue > 0) { //possible
				System.out.println("YES");
				System.out.println(tempValue);
			}else{ //impossible
				System.out.println("NO");
			}
			
		}
	}
	public double testing(Node starter, Node end, int maxMutations) {
		double chances = 0;
		ArrayQueue<Node> lineup = new ArrayQueue();
		starter.setChance(1);
		//start the queue with the starting node
		lineup.enqueue(starter);
		int currentMutations = 0;
		int minimutations = 0;
		int oneMutation = 1;
		while(!lineup.isEmpty()) { // as long as there are things in the queue
			if(currentMutations < maxMutations) { // if there has been less mutations than the max
					Node<Node> Mutation1 = null;
					ArrayList<Node> Mutation2s = new ArrayList();
					ArrayList<Node> Mutation3s = new ArrayList(); 
					
					//check all mutations for first in the queue	
					Mutation1 = Mutation1(lineup.peek());
					if(Mutation1 != null) {
						lineup.enqueue(Mutation1);
					}
					Mutation2s = Mutation2(lineup.peek());
					if(Mutation2s.size() > 0) {
						for(int i = 0; i < Mutation2s.size(); i++) {
							//put all returned nodes into the queue
							lineup.enqueue(Mutation2s.getNode(i));
						}
					}
					Mutation3s = Mutation3(lineup.peek());
					if(Mutation3s.size() > 0) {
						for(int i = 0; i < Mutation3s.size(); i++) {
							//put all returned values into the queue
							lineup.enqueue(Mutation3s.getNode(i));
						}
					}
					//if the current Node is the final node store it's chances
					if((lineup.peek().toString()).equals((end.toString()))) {
						double tempChances = lineup.getChance();
						if(chances < tempChances)//if the last chances are smaller than current chances, update chances
						chances = tempChances;
					}
					minimutations += 1	; //represents a *portion* of a mutation
					if(minimutations == oneMutation) {
						currentMutations++;
						minimutations = 0;
						oneMutation = 1 + Mutation2s.size() + Mutation3s.size();
					}
					lineup.dequeue();
			}
			if(currentMutations == maxMutations) {
				if((lineup.peek().toString()).equals((end.toString()))) {
					double tempChances = lineup.getChance();
					if(chances < tempChances)
						chances = tempChances;
				}
				lineup.dequeue();
			}
		}
		return chances;
	}
	//importing all data from a file
	public void setup(int length,int amount,int mutations,int maxtests, String file) {
		File file1 = new File("C:\\Users\\danie\\Documents\\DNA\\src\\" + file);
		Scanner scan;
		try {
			scan = new Scanner(file1);
			maxLength =  Integer.parseInt(scan.nextLine());
			int genes = Integer.parseInt(scan.nextLine());
			int diseases = Integer.parseInt(scan.nextLine());
			validGenes = new Node[genes + diseases];
			int i = 0;
			/*for the amount of genes scan the next line (and give it a likeliness of 1 (second part is relevant due to the Valid method
			 *returning *that* gene from the list, thus it needs a weight.
			 */
			for(i = 0; i < validGenes.length; i++) {
				validGenes[i] = new Node(scan.nextLine(),1);
				}
			maxMutations = Integer.parseInt(scan.nextLine());
			maxTests = Integer.parseInt(scan.nextLine());
			tests = new Node[maxTests][2];
			for(i = 0; i < maxTests; i++) {
				tests[i][0] = new Node(scan.next(),1);
				tests[i][1] = new Node(scan.next(),1);
				}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			}
		}
	
	public  Node<Node> Mutation1(Node starter){
		//1) The starting base chemical can be swapped with the ending base chemical. This mutation has a 0.02%
		//probability of occurring.		
		//Example: TCGA can mutate into ACGT
		String start = starter.toString();
		Node<Node> returned = null;
		String temp = "";
		if (start.length() <= 1) {
		    temp = temp + start;
		} else {
		    temp = temp + start.charAt(start.length() - 1) + start.substring(1, start.length() - 1) + start.charAt(0);
		}
		//isValid returns as null if the inputted gene is not valid
		Node<Node> temp2 = isValid(temp, validGenes);
		if(temp2 != null) {
			returned = new Node(temp2,starter.getChance()*mut1Chance);
		}
		return returned;
	}
	public ArrayList<Node> Mutation2(Node starter){
		//	2) Any two identical, adjacent base chemicals can be replaced with a single base chemical. This mutation
		//has a 0.06% probability of occurring.
		//Example: TCCGA can mutate into TAGA
		String temp = "";
		String start = starter.toString();
		String temp2 = start;
		for(int i = 0; i < (start.length() - 1); i++) {
			if(start.charAt(i) == start.charAt(i + 1)) {
				// all possibilities of this mutation
				temp = temp + " " + temp2.substring(0, i) + 'A' + temp2.substring(i+2, temp2.length());
				temp = temp + " " + temp2.substring(0, i) + 'C' + temp2.substring(i+2, temp2.length());
				temp = temp + " " + temp2.substring(0, i) + 'T' + temp2.substring(i+2, temp2.length());
				temp = temp + " " + temp2.substring(0, i) + 'G' + temp2.substring(i+2, temp2.length());
			}
		}
		ArrayList<Node> end = new ArrayList();
		//checking if the mutated genes are valid
		if(temp.length() >= 1) {
			String[] temp1 = temp.split(" ");
			for(int z = 0; z < temp1.length; z++) {//if its valid, add it to returned Node array
				Node<Node> temp3 = isValid(temp1[z], validGenes);
				if(temp3 != null) {
					temp3.setChance(starter.getChance()*mut2Chance);//percentage chance (previous chance * 0.02)
					end.addNode(temp3);
				}
			}
		}
		return end;
	}
	public ArrayList<Node> Mutation3(Node starter){
		//3) If the two base pairs G and T occur side by side in any order, another base chemical can be inserted in
		//between them as long as the overall length of the gene does not exceed L. This mutation has a 0.08%
		//probability of occurring.
		//Example: TCCTGA can mutate into TCCTCGA
		
		ArrayList<Node> returned = new ArrayList();
		String tests2 = starter.toString();
		if(tests2.length() < maxLength) {
			String temp = "";
			String temp2 = tests2;
			for(int i = 0; i < (tests2.length() - 1); i++) {
				//if G and T or T and G are together then put A,G,C,T in between that finding and add it to the temporary variable
				if((tests2.charAt(i) == 'G' && tests2.charAt(i + 1) == 'T') || (tests2.charAt(i) == 'T' && tests2.charAt(i + 1) == 'G')) {
					temp = temp + " " + temp2.substring(0, i + 1) + 'A' + temp2.substring(i + 1, temp2.length());
					temp = temp + " " + temp2.substring(0, i + 1) + 'G' + temp2.substring(i + 1, temp2.length());
					temp = temp + " " + temp2.substring(0, i + 1) + 'C' + temp2.substring(i + 1, temp2.length());
					temp = temp + " " + temp2.substring(0, i + 1) + 'T' + temp2.substring(i + 1, temp2.length());
				}
			}
			if(temp.length() > 1) {
			String[] temp1 = temp.split(" ");
			//check if each mutation is valid
			for(int z = 0; z < temp1.length; z++) {
				Node<Node> temp3 = isValid(temp1[z], validGenes);
				if(temp3 != null) {
					temp3.setChance(starter.getChance()*mut3Chance);
					returned.addNode(temp3);	
				}
			}
		}
	}
		return returned;
	}
	//checks if a given node is in the node array of valid genes
	public Node<Node> isValid(String check, Node[] checking) {
		int y = 0;
		Node<Node> valid = null;
		while(y < checking.length && valid == null) {
			if((checking[y].toString()).equals(check)){
				valid = checking[y];
			}
			y++;
			}
		return valid;
	}
}

