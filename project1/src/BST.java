import java.util.ArrayList;

public class BST {
	protected TreeNode root;
	protected int size = 0;

	public BST() {
	}

	public BST(String[] objects) {
		for (int i = 0; i < objects.length; i++)
			insert(objects[i]);
	}
	public BST(String object) {
		insert(object);
	}

	public boolean search(String e) {
		TreeNode current = root; 

		while (current != null) {
			if (e.compareTo(current.element) < 0) {
				current = current.left;
			}
			else if (e.compareTo(current.element) > 0) {
				current = current.right;
			}
			else
				return true;
		}

		return false;
	}

	public boolean insert(String e) {
		if (root == null)
			root = createNewNode(e);
		else {
			TreeNode parent = null;
			TreeNode current = root;
			while (current != null)
				if (e.compareTo(current.element) < 0) {
					parent = current;
					current = current.left;
				}
				else if (e.compareTo(current.element) > 0) {
					parent = current;
					current = current.right;
				}
				else
					return false;
			if (e.compareTo(parent.element) < 0)
				parent.left = createNewNode(e);
			else
				parent.right = createNewNode(e);
		}

		size++;
		return true;
	}

	protected TreeNode createNewNode(String e) {
		return new TreeNode(e);
	}

	/*public void inorder() {
    inorder(root);
  }

  protected void inorder(TreeNode root) {
    if (root == null) return;
    inorder(root.left);
    System.out.print(root.element + " ");
    inorder(root.right);
  }

  public void postorder() {
    postorder(root);
  }

  protected void postorder(TreeNode root) {
    if (root == null) return;
    postorder(root.left);
    postorder(root.right);
    System.out.print(root.element + " ");
  }

  public void preorder() {
    preorder(root);
  }

  protected void preorder(TreeNode root) {
    if (root == null) return;
    System.out.print(root.element + " ");
    preorder(root.left);
    preorder(root.right);
  }*/

	public static class TreeNode implements Comparable<TreeNode> {
		public String element;
		public TreeNode left;
		public TreeNode right;

		public TreeNode(String e) {
			element = e;
		}

		@Override
		public int compareTo(BST.TreeNode o) {
			return element.compareTo(o.element);
		}
	}

	public int getSize() {
		return size;
	}
	public TreeNode getRoot() {
		return root;
	}

	public java.util.ArrayList<TreeNode> path(String e) {
		java.util.ArrayList<TreeNode> list =
				new java.util.ArrayList<>();
		TreeNode current = root;

		while (current != null) {
			list.add(current);
			if (e.compareTo(current.element) < 0) {
				current = current.left;
			}
			else if (e.compareTo(current.element) > 0) {
				current = current.right;
			}
			else
				break;
		}

		return list;
	}
	public ArrayList<String> pathFromNode(TreeNode node, String e) {
		java.util.ArrayList<String> list =
				new java.util.ArrayList<>();
		TreeNode current = node;

		while (current != null) {
			list.add(current.element);
			if (e.compareTo(current.element) < 0) {
				current = current.left;
			}
			else if (e.compareTo(current.element) > 0) {
				current = current.right;
			}
			else
				break;
		}

		return list;
	}
	public java.util.ArrayList<String> pathString(String e){
		java.util.ArrayList<String> list =
				new java.util.ArrayList<>();
		TreeNode current = root;

		while (current != null) {
			list.add(current.element);
			if (e.compareTo(current.element) < 0) {
				current = current.left;
			}
			else if (e.compareTo(current.element) > 0) {
				current = current.right;
			}
			else
				break;
		}

		return list;

	}

	public boolean delete(String e) {
		TreeNode parent = null;
		TreeNode current = root;
		while (current != null) {
			if (e.compareTo(current.element) < 0) {
				parent = current;
				current = current.left;
			}
			else if (e.compareTo(current.element) > 0) {
				parent = current;
				current = current.right;
			}
			else
				break;
		}

		if (current == null)
			return false;

		if (current.right == null) {
			if (parent == null) {
				root = current.left;
			}
			else {
				if (e.compareTo(parent.element) < 0)
					parent.left = current.left;
				else
					parent.right = current.left;
			}
		}
		else {
			TreeNode parentOfRightMost = current;
			TreeNode rightMost = current.right;

			while (rightMost.left != null) {
				parentOfRightMost = rightMost;
				rightMost = rightMost.left;
			}
			current.element = rightMost.element;

			if (parentOfRightMost.left == rightMost)
				parentOfRightMost.left = rightMost.right;
			else
				parentOfRightMost.right = rightMost.right;     
		}

		size--;
		return true; 
	}

	public void clear() {
		root = null;
		size = 0;
	}
}
