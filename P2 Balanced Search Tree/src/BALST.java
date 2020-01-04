import java.util.*;

/**
 *
 * Class to implement a BalanceSearchTree. Can be of type AVL or Red-Black.
 * Note which tree you implement here and as a comment when you submit.
 *
 * @author Rohan Mendiratta
 *
 * @param <K> is the generic type of key
 * @param <V> is the generic type of value
 */
public class BALST<K extends Comparable<K>, V> implements BALSTADT<K, V> {

	/**
	 * Node class which is used to build the BST
	 * @param <K>
	 * @param <V>
	 */
	class Node<K,V> {

		K key;
		V value;
		Node<K,V> left;
		Node<K,V> right;
		int balanceFactor;
		int height;


		/**
		 * @param key
		 * @param value
		 * @param leftChild
		 * @param rightChild
		 */
		Node(K key, V value, Node<K,V> leftChild, Node<K,V> rightChild) {
			this.key = key;
			this.value = value;
			this.left = leftChild;
			this.right = rightChild;
			this.height = 0;
			this.balanceFactor = 0;
		}

		Node(K key, V value) { this(key,value,null,null); }

	}

	private Node<K, V> root; // holds root node
	private int numKeys; // holds number of keys in the structure

	public BALST() {
		this.root = null;
		this.numKeys = 0;
	}

	/**
	 * Returns the key that is in the root node of this BST.
	 * If root is null, returns null.
	 * @return key found at root node, or null
	 */
	@Override
	public K getKeyAtRoot() {
		if (this.root == null) {
			return null;
		}
		else {
			return root.key;
		}
	}

	/**
	 * Tries to find a node with a key that matches the specified key.
	 * If a matching node is found, it returns the returns the key that is in the left child.
	 * If the left child of the found node is null, returns null.
	 *
	 * @param key A key to search for
	 * @return The key that is in the left child of the found key
	 *
	 * @throws IllegalNullKeyException if key argument is null
	 * @throws KeyNotFoundException if key is not found in this BST
	 */
	@Override
	public K getKeyOfLeftChildOf(K key) throws IllegalNullKeyException, KeyNotFoundException {
		Node<K, V> current = this.root; // starts at root node

		if(this.root == null) {
			throw new IllegalNullKeyException();
		}

		// moves through the tree looking for the key
		while(current != null) {
			if(current.key.compareTo(key) > 0) {
				current = current.left;
			} else if (current.key.compareTo(key) < 0) {
				current = current.right;
			}
			else if(current.key.compareTo(key) == 0) {
				return current.left.key;
			}
		}
		throw new KeyNotFoundException();
	}

	/**
	 * Tries to find a node with a key that matches the specified key.
	 * If a matching node is found, it returns the returns the key that is in the right child.
	 * If the right child of the found node is null, returns null.
	 *
	 * @param key A key to search for
	 * @return The key that is in the right child of the found key
	 *
	 * @throws IllegalNullKeyException if key is null
	 * @throws KeyNotFoundException if key is not found in this BST
	 */
	@Override
	public K getKeyOfRightChildOf(K key) throws IllegalNullKeyException, KeyNotFoundException {
		Node<K, V> current = this.root; // starts at root

		if(this.root == null) {
			throw new IllegalNullKeyException();
		}

		// moves through tree looking for the correct key
		while(current != null) {
			if(current.key.compareTo(key) > 0) {
				current = current.left;
			} else if (current.key.compareTo(key) < 0) {
				current = current.right;
			}
			else if(current.key.compareTo(key) == 0) {
				return current.right.key;
			}
		}
		throw new KeyNotFoundException();
	}

	/**
	 * Returns the height of this BST.
	 * H is defined as the number of levels in the tree.
	 *
	 * If root is null, return 0
	 * If root is a leaf, return 1
	 * Else return 1 + max( height(root.left), height(root.right) )
	 *
	 * Examples:
	 * A BST with no keys, has a height of zero (0).
	 * A BST with one key, has a height of one (1).
	 * A BST with two keys, has a height of two (2).
	 * A BST with three keys, can be balanced with a height of two(2)
	 *                        or it may be linear with a height of three (3)
	 * ... and so on for tree with other heights
	 *
	 * @return the number of levels that contain keys in this BINARY SEARCH TREE
	 */
	@Override
	public int getHeight() {
		return getHeight(root);
	}

	/**
	 * gets the Height at a specified node in the tree
	 * @param node node to get height at
	 * @return the height from the node
	 */
	private int getHeight(Node<K, V> node) {
		if (node == null) {
			return 0;
		}
		else {
			int leftChild = getHeight(node.left);
			int rightChild = getHeight(node.right);
			if(rightChild>leftChild) {
				return rightChild + 1;
			}
			else {
				return leftChild + 1;
			}
		}
	}

	/**
	 * Returns the keys of the data structure in sorted order.
	 * In the case of binary search trees, the visit order is: L V R
	 *
	 * If the SearchTree is empty, an empty list is returned.
	 *
	 * @return List of Keys in-order
	 */
	@Override
	public List<K> getInOrderTraversal() {
		List<K> preOrder = new ArrayList<K>();
		Stack<Node<K,V>> s = new Stack<Node<K,V>>();
		Node<K,V> current = this.root;

		if (this.root == null) {
			return preOrder;
		}

		while (true) {
			// go to the left and add everything to the stack
			while (current != null) {
				s.push(current);
				current = current.left;
			}
			// exits at completion
			if (s.isEmpty()) {
				return preOrder;
			}
			// pop the element from the stack and adds it to the list
			current = s.pop();
			preOrder.add(current.key);
			current = current.right;
		}


	}

	/**
	 * Returns the keys of the data structure in pre-order traversal order.
	 * In the case of binary search trees, the order is: V L R
	 *
	 * If the SearchTree is empty, an empty list is returned.
	 *
	 * @return List of Keys in pre-order
	 */
	@Override
	public List<K> getPreOrderTraversal() {
		List<K> preOrder = new ArrayList<K>();
		Stack<Node<K,V>> s = new Stack<Node<K,V>>();
		Node<K,V> current = this.root;
		if (this.root == null) {
			return preOrder;
		}

		while(true) {
			// adds to the stack moving to the left
			while(current != null) {
				preOrder.add(current.key);
				s.push(current);
				current = current.left;
			}

			if(s.isEmpty()) {
				return preOrder;
			}
			// pop the element from the stack and adds it to the list
			current = s.pop();
			current = current.right;
		}
	}

	/**
	 * Returns the keys of the data structure in post-order traversal order.
	 * In the case of binary search trees, the order is: L R V
	 *
	 * If the SearchTree is empty, an empty list is returned.
	 *
	 * @return List of Keys in post-order
	 */
	@Override
	public List<K> getPostOrderTraversal() {
		List<K> preOrder = new ArrayList<>();
		Stack<Node<K,V>> s = new Stack<>();
		Stack<Node<K,V>> s2 = new Stack<>();

		if (this.root == null) {
			return preOrder;
		}

		s.push(this.root);

		// goes through adding to the stack
		while(s.isEmpty() == false) {
			Node<K,V> current = s.pop();
			s2.push(current);
			if(current.left != null) {
				s.push(current.left);
			}
			if(current.right != null) {
				s.push(current.right);
			}
		}
		// pops off the stack and adds to the list
		while(s2.isEmpty() == false){
			preOrder.add(s2.pop().key);
		}
		return preOrder;
	}

	/**
	 * Returns the keys of the data structure in level-order traversal order.
	 *
	 * The root is first in the list, then the keys found in the next level down,
	 * and so on.
	 *
	 * If the SearchTree is empty, an empty list is returned.
	 *
	 * @return List of Keys in level-order
	 */
	@Override
	public List<K> getLevelOrderTraversal() {
		List<K> preOrder = new ArrayList<K>();
		Queue<Node<K,V>> queue = new LinkedList<>();

		if (this.root == null) {
			return preOrder;
		}
		queue.add(this.root);

		// goes through the BST adding to the queue
		while(!queue.isEmpty()) {

			Node<K,V> current = queue.poll();
			preOrder.add(current.key);

			if (current.left != null) {
				queue.add(current.left);
			}

			if (current.right != null) {
				queue.add(current.right);
			}

		}

		return preOrder;
	}

	/**
	 * Add the key,value pair to the data structure and increase the number of keys.
	 * If key is null, throw IllegalNullKeyException;
	 * If key is already in data structure, throw DuplicateKeyException();
	 * Do not increase the num of keys in the structure, if key,value pair is not added.
	 */
	@Override
	public void insert(K key, V value) throws IllegalNullKeyException, DuplicateKeyException {
		if (key == null) {
			throw new IllegalNullKeyException();
		} else if (this.contains(key)) {
			throw new DuplicateKeyException();
		}
		root = insertHelper(key, value, root);
		numKeys++;

	}

	/**
	 *
	 * This method is a helper method in order to place a new node into the binary tree
	 * and to rebalance it after
	 * @param key key to bed added
	 * @param value value to be added
	 * @param node node to be added to
	 * @return
	 */
	private Node<K, V> insertHelper(K key, V value, Node<K,V> node){
		if (node == null) {
			node = new Node<K, V> (key, value, null, null);
			this.numKeys++;
		}
		else {
			int n = key.compareTo(node.key);
			if(n < 0) {
				node.left = insertHelper(key,value, node.left);
			}
			else if(n > 0) {
				node.right = insertHelper(key,value, node.right);
			}
		}

		int balanceNumber = getBalanceValue(node);
		if(balanceNumber < -1) {
			int n = key.compareTo(node.right.key);
			if (n<0) {
				node.right = rightRotation(node.right);
				node = leftRotation(node);
			}
			else if(n>0){
				node = leftRotation(node);
			}
		}else if(balanceNumber > 1) {
			int n = key.compareTo(node.left.key);
			if (n>0) {
				node.left = leftRotation(node.left);
				node = rightRotation(node);
			}
			else if(n<0){
				node = rightRotation(node);
			}
		}
		return node;
	}

	/**
	 *
	 * This determines the balance value of the tree at a specified node
	 *
	 * @param currentNode node to get the balance value at
	 * @return
	 */
	private int getBalanceValue(Node<K, V> currentNode) {
		if (currentNode == null) {
			return 0;
		}else if(currentNode.left == null && currentNode.right == null) {
			return 1;
		}else {
			return getHeight(currentNode.left)- getHeight(currentNode.right);
		}
	}

	/**
	 * Helper method to rotate the tree to the left
	 * @param node pivot node
	 * @return parent node
	 */
	private Node<K, V> leftRotation(Node<K, V> node) {
		Node<K, V> newRootNode = node.right;
		Node<K, V> temp = newRootNode.left;

		newRootNode.left = node;
		node.right = temp;

		newRootNode.height = getHeight(newRootNode);
		node.height = getHeight(node);

		return newRootNode;
	}

	/**
	 * This method allows the tree to rotate to the right
	 * @param node pivot node
	 * @return parent node
	 */
	private Node<K, V> rightRotation(Node<K, V> node) {
		Node<K,V> newRootNode = node.left;
		Node<K,V> temp = newRootNode.right;

		newRootNode.right = node;
		node.left = temp;

		newRootNode.height = getHeight(newRootNode);
		node.height = getHeight(node);

		return newRootNode;
	}

	/**
	 * If key is found, remove the key,value pair from the data structure and decrease num keys.
	 * If key is not found, do not decrease the number of keys in the data structure.
	 * If key is null, throw IllegalNullKeyException
	 * If key is not found, throw KeyNotFoundException().
	 */
	@Override
	public boolean remove(K key) throws IllegalNullKeyException, KeyNotFoundException {
		if(key == null) {
			throw new IllegalNullKeyException();
		}
		if(!this.contains(key)){
			throw new KeyNotFoundException();
		}
		root = removeHelper(root, key);
		numKeys--;
		return true;
	}

	/**
	 * Helper method to remove from the BST tree
	 * @param node noode to move from
	 * @param key key to remove
	 * @return parent node
	 */
	private Node<K,V> removeHelper(Node<K,V> node, K key) {
		// checks for null root
		if (node == null)
			return node;

		int cmp = key.compareTo(node.key); // does comparison

		// if key is smaller it is in left subtree
		if (cmp < 0) {
			node.left = removeHelper(node.left, key);
		}

		// if key is bigger it is in right subtree
		else if (cmp > 0) {
			node.right = removeHelper(node.right, key);
		}

		// key to be deleted is found
		else
		{
			// node with only one child or no child
			if ((node.left == null) || (node.right == null))
			{
				Node temp = null;
				if (temp == node.left)
					temp = node.right;
				else
					temp = node.left;

				// No child case
				if (temp == null)
				{
					temp = node;
					node = null;
				}
				else // One child case
					node = temp;
			}
			else
			{

				// node with two children
				Node temp = getInOrderSuccessor(node.right);

				// Copy the inorder successor's
				node.key = (K)temp.key;

				// Delete the inorder successor
				node.right = removeHelper(node.right, (K)temp.key);
			}
		}

		// if tree only has one node
		if (node == null)
			return node;

		// updates height of node
		node.height = Math.max(getHeight(node.left), getHeight(node.right)) + 1;

		// get balance factor
		int balance = getBalance(node);

		// rebalances the tree
		// right rotate
		if (balance > 1 && getBalance(node.left) >= 0)
			return rightRotation(node);

		// left right rotate
		if (balance > 1 && getBalance(node.left) < 0)
		{
			node.left = leftRotation(node.left);
			return rightRotation(node);
		}

		// right rotate
		if (balance < -1 && getBalance(node.right) <= 0)
			return leftRotation(node);

		// right left rotate
		if (balance < -1 && getBalance(node.right) > 0)
		{
			node.right = rightRotation(node.right);
			return leftRotation(node);
		}

		return node;
	}

	/**
	 * This method allows for the left most node to be returned in the binary tree
	 *
	 * @param node to traverse from
	 * @return parent node
	 */
	private Node<K,V> getInOrderSuccessor(Node<K,V> node)
	{
		Node<K,V> current = node;

		// moves through the BST to get leftmost leaf
		while (current.left != null) {
			current = current.left;
		}
		return current;
	}

	/**
	 *
	 * This checks the difference between the heights of the left and right sides
	 *
	 * @param node node to get balance of
	 * @return balance of node
	 */
	private int getBalance(Node<K,V> node) {
		if(node == null) {
			return 0;
		}
		return getHeight(node.left) - getHeight(node.right);
	}

	/**
	 *  Returns the value associated with the specified key
	 *
	 * Does not remove key or decrease number of keys
	 * If key is null, throw IllegalNullKeyException
	 * If key is not found, throw KeyNotFoundException().
	 */
	@Override
	public V get(K key) throws IllegalNullKeyException, KeyNotFoundException {
		Node<K, V> current = this.root;

		// checks for null key
		if(key == null) {
			throw new IllegalNullKeyException();
		}

		// moves through the BST looking for a match
		while(current != null) {
			if(current.key.compareTo(key) > 0) {
				current = current.left;
			} else if (current.key.compareTo(key) < 0) {
				current = current.right;
			}
			else if(current.key.compareTo(key) == 0) {
				return current.value;
			}
		}
		throw new KeyNotFoundException();

	}

	/**
	 * Returns true if the key is in the data structure
	 * If key is null, throw IllegalNullKeyException
	 * Returns false if key is not null and is not present
	 */
	@Override
	public boolean contains(K key) throws IllegalNullKeyException {
		// checks for null key
		if(key == null) {
			throw new IllegalNullKeyException();
		}
		// gets list of keys
		List<K> list =  getLevelOrderTraversal();

		// checks if list contains key
		if(list.contains(key)){
			return true;
		}

		return false;
	}

	/**
	 *  Returns the number of key,value pairs in the data structure
	 */
	@Override
	public int numKeys() {
		return this.numKeys;
	}

	/**
	 * Prints the tree out
	 */
	@Override
	public void print() {
		printHelper(root, 0, 10);
	}

	/**
	 * Helper method to print the tree
	 * @param root node to print from
	 * @param numSpaces current spacing
	 * @param height height of the node
	 */
	private void printHelper(Node root, int numSpaces, int height)
	{
		// return when no more nodes
		if (root == null) {
			return;
		}

		// increase distance between levels
		numSpaces += height;

		// print right child first
		printHelper(root.right, numSpaces, height);
		System.out.println();

		// print current node after adding spacing
		for (int i = height; i < numSpaces; i++) {
			System.out.print(' ');
		}

		System.out.print(root.key);

		// print left child
		System.out.println();
		printHelper(root.left, numSpaces, height);
	}

}

