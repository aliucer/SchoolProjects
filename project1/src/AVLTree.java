import java.util.ArrayList;
import java.util.Collections;

public class AVLTree extends BST {
	StringBuilder str = new StringBuilder();
	public AVLTree() {
	}
	
	public AVLTree(String objects) {
		super(objects);
	}

	@Override
	protected AVLTreeNode createNewNode(String e) {
		return new AVLTreeNode(e);
	}

	@Override
	public boolean insert(String e) {
		boolean successful = super.insert(e);
		if (!successful)
			return false;
		else {

			ArrayList<String> path = pathString(e);
			for(int i=0; i<path.size()-1; i++) {
				str.append(path.get(i) + ": New node being added with IP:" + e +  System.lineSeparator());
			}
			balancePath(e);
		}

		return true;
	}

	private void updateHeight(AVLTreeNode node) {
		if (node.left == null && node.right == null)
			node.height = 0;
		else if (node.left == null)
			node.height = 1 + ((AVLTreeNode)(node.right)).height;
		else if (node.right == null)
			node.height = 1 + ((AVLTreeNode)(node.left)).height;
		else
			node.height = 1 +
			Math.max(((AVLTreeNode)(node.right)).height,
					((AVLTreeNode)(node.left)).height);
	}

	private void balancePath(String e) {
		java.util.ArrayList<TreeNode> path = path(e);
		for (int i = path.size() - 1; i >= 0; i--) {
			AVLTreeNode A = (AVLTreeNode)(path.get(i));
			updateHeight(A);
			AVLTreeNode parentOfA = (A == root) ? null :
				(AVLTreeNode)(path.get(i - 1));

			switch (balanceFactor(A)) {
			case -2:
				if (balanceFactor((AVLTreeNode)A.left) <= 0) {
					balanceLL(A, parentOfA); // Perform LL rotation
					str.append("Rebalancing: right rotation" + System.lineSeparator());
				}
				else {
					balanceLR(A, parentOfA); // Perform LR rotation
					str.append("Rebalancing: left-right rotation" + System.lineSeparator());
				}
				break;
			case +2:
				if (balanceFactor((AVLTreeNode)A.right) >= 0) {
					balanceRR(A, parentOfA); // Perform RR rotation
					str.append("Rebalancing: left rotation" + System.lineSeparator());
				}
				else {
					balanceRL(A, parentOfA); // Perform RL rotation
					str.append("Rebalancing: right-left rotation" + System.lineSeparator());
				}
			}
		}
	}

	private int balanceFactor(AVLTreeNode node) {
		if (node.right == null)
			return -node.height;
		else if (node.left == null)
			return +node.height;
		else
			return ((AVLTreeNode)node.right).height -
					((AVLTreeNode)node.left).height;
	}

	private void balanceLL(TreeNode A, TreeNode parentOfA) {
		TreeNode B = A.left;

		if (A == root) {
			root = B;
		}
		else {
			if (parentOfA.left == A) {
				parentOfA.left = B;
			}
			else {
				parentOfA.right = B;
			}
		}

		A.left = B.right; 
		B.right = A; 
		updateHeight((AVLTreeNode)A);
		updateHeight((AVLTreeNode)B);
	}

	private void balanceLR(TreeNode A, TreeNode parentOfA) {
		TreeNode B = A.left;
		TreeNode C = B.right;

		if (A == root) {
			root = C;
		}
		else {
			if (parentOfA.left == A) {
				parentOfA.left = C;
			}
			else {
				parentOfA.right = C;
			}
		}

		A.left = C.right;
		B.right = C.left;
		C.left = B;
		C.right = A;
		updateHeight((AVLTreeNode)A);
		updateHeight((AVLTreeNode)B);
		updateHeight((AVLTreeNode)C);
	}

	private void balanceRR(TreeNode A, TreeNode parentOfA) {
		TreeNode B = A.right;

		if (A == root) {
			root = B;
		}
		else {
			if (parentOfA.left == A) {
				parentOfA.left = B;
			}
			else {
				parentOfA.right = B;
			}
		}

		A.right = B.left;
		B.left = A;
		updateHeight((AVLTreeNode)A);
		updateHeight((AVLTreeNode)B);
	}

	private void balanceRL(TreeNode A, TreeNode parentOfA) {
		TreeNode B = A.right; 
		TreeNode C = B.left; 

		if (A == root) {
			root = C;
		}
		else {
			if (parentOfA.left == A) {
				parentOfA.left = C;
			}
			else {
				parentOfA.right = C;
			}
		}

		A.right = C.left;
		B.left = C.right;
		C.left = A;
		C.right = B;

		updateHeight((AVLTreeNode)A);
		updateHeight((AVLTreeNode)B);
		updateHeight((AVLTreeNode)C);
	}

	@Override
	public boolean delete(String element) {
		if (root == null)
			return false;

		TreeNode parent = null;
		TreeNode current = root;
		while (current != null) {
			if (element.compareTo(current.element) < 0) {
				parent = current;
				current = current.left;
			}
			else if (element.compareTo(current.element) > 0) {
				parent = current;
				current = current.right;
			}
			else
				break;
		}

		if (current == null)
			return false;

		if(current.left==null && current.right==null) {
			if(parent==null) {
				clear();
			}
			else {
				if (element.compareTo(parent.element) < 0)
					parent.left = null;
				else {
					parent.right = null;
				}
				str.append(parent.element + ": Leaf Node Deleted: " + element + System.lineSeparator());
				balancePath(parent.element);
			}
		}
		else if (current.left == null) {
			if (parent == null) {
				root = current.right;
			}
			else {
				if (element.compareTo(parent.element) < 0)
					parent.left = current.right;
				else
					parent.right = current.right;

				str.append(parent.element + ": Node with single child Deleted: " + element + System.lineSeparator());

				balancePath(parent.element);
			}
		}
		else if(current.right == null) {

			if (parent == null) {
				root = current.left;
			}
			else {
				if (element.compareTo(parent.element) < 0)
					parent.left = current.left;
				else
					parent.right = current.left;

				str.append(parent.element + ": Node with single child Deleted: " + element + System.lineSeparator());

				balancePath(parent.element);
			}

		}
		else {
			TreeNode parentOfRightMost = current;
			TreeNode rightMost = current.right;

			while (rightMost.left != null) {
				parentOfRightMost = rightMost;
				rightMost = rightMost.left;
			}
			String temp = current.element;
			current.element = rightMost.element;

			if (parentOfRightMost.left == rightMost)
				parentOfRightMost.left = rightMost.right;
			else
				parentOfRightMost.right = rightMost.right; 
			if(parent!=null) {
				str.append(parent.element + ": Non Leaf Node Deleted; removed: " + temp + " replaced: " + rightMost.element + System.lineSeparator());
			}
			else {
				
			}
			balancePath(parentOfRightMost.element);
		}

		size--;
		return true;
	}

	protected static class AVLTreeNode
	extends BST.TreeNode {
		protected int height = 0;

		public AVLTreeNode(String o) {
			super(o);
		}
	}

	public ArrayList<String> send(String senderIP, String receiverIP){
		return send(senderIP,receiverIP,root);
	}
	private ArrayList<String> send(String senderIP, String receiverIP, TreeNode node) {
		if(senderIP.compareTo(node.element)>0 && receiverIP.compareTo(node.element)>0) {
			return send(senderIP, receiverIP, node.right);
		}
		else if(senderIP.compareTo(node.element)<0 && receiverIP.compareTo(node.element)<0) {
			return send(senderIP, receiverIP, node.left);
		}
		else {
			ArrayList<String> list = new ArrayList<>();
			ArrayList<String> path1 = pathFromNode(node, senderIP);
			Collections.reverse(path1);
			ArrayList<String> path2 = pathFromNode(node,receiverIP);
			list.addAll(path1);
			path2.remove(0);
			list.addAll(path2);
			return list;
		}
	}
}
