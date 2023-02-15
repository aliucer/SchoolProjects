import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
	AVLTree avl = new AVLTree();
	BST2 bst2 = new BST2();
	StringBuilder strBST = new StringBuilder();
	
	if(args.length!=2) {
		System.out.println("number of arguments is not 2");
		System.exit(1);
	}
	try {
		File myObj = new File(args[0]);
		Scanner sc = new Scanner(myObj);
		String parentIP = sc.next();
		bst2.addNode(parentIP);
		avl.insert(parentIP);
		while(sc.hasNext()) {
			String key = sc.next();
			switch (key) {
			
			case "ADDNODE":
				String addIP = sc.next();
				avl.insert(addIP);
				bst2.addNode(addIP);
				ArrayList<String> path = bst2.getPath(bst2.getRoot(), addIP);
				for(int i=0; i<path.size()-1; i++) {
					strBST.append(path.get(i) + ": New node being added with IP:" + addIP +  System.lineSeparator());
				}
				break;
				
			case "SEND":
				String from = sc.next();
				String to = sc.next();
				ArrayList<String> list2 = avl.send(from, to);
				avl.str.append(from + ": Sending message to: " + to + System.lineSeparator());
				for(int i=1; i<list2.size()-1; i++) {
					avl.str.append(list2.get(i) + ": Transmission from: " + list2.get(i-1) + " receiver: " + to + " sender:" + from + System.lineSeparator());
				}
				avl.str.append(to + ": Received message from: " + from + System.lineSeparator());
				ArrayList<String> list1 = bst2.send(from, to);
				strBST.append(from + ": Sending message to: " + to + System.lineSeparator());
				for(int i=1; i<list1.size()-1; i++) {
					strBST.append(list1.get(i) + ": Transmission from: " + list1.get(i-1) + " receiver: " + to + " sender:" + from + System.lineSeparator());
				}
				strBST.append(to + ": Received message from: " + from + System.lineSeparator());
				
				break;
				
			case "DELETE":
				String deleteIP = sc.next();
				ArrayList<String> list = bst2.delete(deleteIP);
				if(list.get(1)=="none") {
					strBST.append(list.get(2) + ": Leaf Node Deleted: " + list.get(0) + System.lineSeparator());
				}
				else if(list.get(1)=="right" || list.get(1)=="left") {
					strBST.append(list.get(2) + ": Node with single child Deleted: " + list.get(0) + System.lineSeparator());
				}
				else {
					strBST.append(list.get(1) + ": Non Leaf Node Deleted; removed: " + list.get(0) + " replaced: " + list.get(2) + System.lineSeparator());
				}
				avl.delete(deleteIP);
				break;
			default:
				break;
			}	
		}
		
		sc.close();
	} catch (FileNotFoundException e) {
		System.out.println("An error occurred.");
		e.printStackTrace();
	}
	String str5 = String.valueOf(avl.str);
	
	try {
		File myObj = new File(args[1] + "_BST.txt");
		FileWriter myWriter = new FileWriter(myObj);
		myWriter.write(String.valueOf(strBST));
		myWriter.close();
		File myObj2 = new File(args[1] + "_AVL.txt");
		FileWriter myWriter2 = new FileWriter(myObj2);
		myWriter2.write(str5);
		myWriter2.close();

	} catch (IOException e) {
		e.printStackTrace();
	}
	}
}
