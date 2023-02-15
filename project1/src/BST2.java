import java.util.ArrayList;
import java.util.Collections;

public class BST2 {
	public Node root;
	public String addNode(String IP){
		if(root==null) {
			root = new Node(IP);
			return IP;
		}

		Node parent = null;
		Node current = root;
		while(current!=null) {
			if(IP.compareTo(current.IP)>0) {
				parent = current;
				current = current.right;
			}
			else if(IP.compareTo(current.IP)<0){
				parent = current;
				current = current.left;
			}
			else {
				return null;
			}
		}
		if(IP.compareTo(parent.IP)>0) {
			parent.right = new Node(IP);
		}
		else {
			parent.left = new Node(IP);
		}
		return IP;
	}

	public ArrayList<String> delete(String IP) {
		ArrayList<String> list = new ArrayList<>();
		Node parent = null;
		Node current = root;
		while(current!=null) {
			if(IP.compareTo(current.IP)>0) {
				parent = current;
				current = current.right;
			}
			else if(IP.compareTo(current.IP)<0){
				parent = current;
				current = current.left;
			}
			else {
				list.add(current.IP);
				if(current.right==null && current.left!=null) {
					if(current.compareTo(parent)<0) {
						parent.left = current.left;
					}
					else {
						parent.right = current.left;
					}
					list.add("left");
					list.add(parent.IP);
					break;
				}

				else if(current.right!=null && current.left==null) {
					if(current.compareTo(parent)<0) {
						parent.left = current.right;
					}
					else {
						parent.right = current.right;
					}
					list.add("right");
					list.add(parent.IP);
					break;
				}
				else if(current.right==null && current.left==null) {
					if(current.compareTo(parent)<0) {
						parent.left = current.left;
					}
					else {
						parent.right = current.left;
					}
					list.add("none");
					list.add(parent.IP);
					break;
				}
				else {
					list.add(parent.IP);
					Node parentRightOfLeftMost = current;
					Node rightOfLeftMost = current.right;

					while(rightOfLeftMost.left!=null) {
						parentRightOfLeftMost = rightOfLeftMost;
						rightOfLeftMost = rightOfLeftMost.left;
					}
					current.IP = rightOfLeftMost.IP;
					list.add(rightOfLeftMost.IP);
					if(parentRightOfLeftMost==current) {
						parentRightOfLeftMost.right = rightOfLeftMost.right;
					}
					else {
						parentRightOfLeftMost.left = rightOfLeftMost.right;
					}
				}
				break;


			}
		}
		return list;
	}
	public Node getNode(String IP) {
		Node current = root;
		while(current!=null) {
			if((current.IP).compareTo(IP)<0) {
				current = current.right;
			}
			else if((current.IP).compareTo(IP)>0) {
				current = current.left;
			}
			else {
				break;
			}
		}
		return current;
	}
	public ArrayList<String> send(String senderIP, String receiverIP){
		return send(senderIP,receiverIP,root);
	}
	private ArrayList<String> send(String senderIP, String receiverIP, Node node) {
		if(senderIP.compareTo(node.IP)>0 && receiverIP.compareTo(node.IP)>0) {
			return send(senderIP, receiverIP, node.right);
		}
		else if(senderIP.compareTo(node.IP)<0 && receiverIP.compareTo(node.IP)<0) {
			return send(senderIP, receiverIP, node.left);
		}
		else {
			ArrayList<String> list = new ArrayList<>();
			ArrayList<String> path1 = getPath(node, senderIP);
			Collections.reverse(path1);
			ArrayList<String> path2 = getPath(node, receiverIP);
			list.addAll(path1);
			path2.remove(0);
			list.addAll(path2);
			return list;
		}
	}
	public ArrayList<String> getPath(Node from, String to){
		ArrayList<String> list = new ArrayList<>();
		while(from!=null) {
			list.add(from.IP);
			if(to.compareTo(from.IP)>0) {
				from = from.right;
			}
			else if(to.compareTo(from.IP)<0){
				from = from.left;
			}
			else {
				break;
			}
		}
		return list;
	}
	public ArrayList<String> getPath(String to){
		
		return getPath(root,to);
	}

	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}

}
class Node implements Comparable<Node>{
	Node left;
	Node right;
	String IP;
	public int height = 0;
	public Node(Node left, Node right, String iP) {
		this.left = left;
		this.right = right;
		IP = iP;
	}
	public Node(String iP) {
		IP = iP;
	}
	@Override
	public int compareTo(Node o) {
		return IP.compareTo(o.IP);
	}
}
