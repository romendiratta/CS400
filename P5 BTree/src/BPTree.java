import java.util.*;

/**
 * Implementation of a B+ tree to allow efficient access to
 * many different indexes of a large data set. 
 * BPTree objects are created for each type of index
 * needed by the program.  BPTrees provide an efficient
 * range search as compared to other types of data structures
 * due to the ability to perform log_m N lookups and
 * linear in-order traversals of the data items.
 * 
 * @author Rohan Mendiratta (rmendiratta@wisc.edu)
 *
 *
 * @param <K> key - expect a string that is the type of id for each item
 * @param <V> value - expect a user-defined type that stores all data for a food item
 */
public class BPTree<K extends Comparable<K>, V> implements BPTreeADT<K, V> {

    // Root of the tree
    private Node root;

    // Stores the number of keys in the tree
    private int numKeys;
    
    // Branching factor is the number of children nodes 
    // for internal nodes of the tree
    private int branchingFactor;
    
    
    /**
     * Public constructor
     * 
     * @param branchingFactor 
     */
    public BPTree(int branchingFactor) {
        if (branchingFactor <= 2) {
            throw new IllegalArgumentException(
               "Illegal branching factor: " + branchingFactor);
        }

        this.branchingFactor = branchingFactor;
        root = new LeafNode();
    }


    /**
     * Inserts the key and value in the appropriate nodes in the tree
     * If the key is null, throw IllegalArgumentException
     *
     * Note: key-value pairs with duplicate keys can be inserted into the tree.
     *
     * @param key
     * @param value
     */
    @Override
    public void insert(K key, V value) {
        if(key == null) {
            throw new IllegalArgumentException();
        }
        root.insert(key, value);
    }


    /**
     * Gets the values that satisfy the given range
     * search arguments.
     *
     * Value of comparator can be one of these:
     * "<=", "==", ">="
     *
     * Example:
     *     If given key = 2.5 and comparator = ">=":
     *         return all the values with the corresponding
     *      keys >= 2.5
     *
     * If key is null or not found, return empty list.
     * If comparator is null, empty, or not according
     * to required form, return empty list.
     *
     * @param key to be searched
     * @param comparator is a string
     * @return list of values that are the result of the
     * range search; if nothing found, return empty list
     */
    @Override
    public List<V> rangeSearch(K key, String comparator) {
        List<V> result =  new ArrayList<>();
        // runs get methods when looking for equal.
        if (comparator.equals("==")) {
            V val = this.get(key);
            if(val != null) {
                result.add(val);
                return result;
            }
        }
        // runs range search if looking for comparison
        else {
            return root.rangeSearch(key, comparator);
        }

        return result;
    }

    /**
     * Returns the value of the first leaf with a matching key.
     * If key is null, return null.
     * If key is not found, return null.
     *
     * @param key to find
     * @return value of the first leaf matching key
     */
     @Override
     public V get(K key) {
         // if root is an internal node
         if(numKeys > branchingFactor - 1) {
             InternalNode node = (InternalNode)root;
             return node.get(key);
         }
         // if root is a leaf node
         else {
             LeafNode node = (LeafNode)root;
             return node.get(key);
         }
     }

    /**
     * Return the number of leaves in the tree.
     *
     * @return number of leaves
     */
     @Override
     public int size() {
         return numKeys;
     }

    /**
     * Returns a string representation for the tree
     * This method is provided to students in the implementation.
     * @return a string representation
     */
    @Override
    public String toString() {
        Queue<List<Node>> queue = new LinkedList<List<Node>>();
        queue.add(Arrays.asList(root));
        StringBuilder sb = new StringBuilder();
        while (!queue.isEmpty()) {
            Queue<List<Node>> nextQueue = new LinkedList<List<Node>>();
            while (!queue.isEmpty()) {
                List<Node> nodes = queue.remove();
                sb.append('{');
                Iterator<Node> it = nodes.iterator();
                while (it.hasNext()) {
                    Node node = it.next();
                    sb.append(node.toString());
                    if (it.hasNext())
                        sb.append(", ");
                    if (node instanceof BPTree.InternalNode)
                        nextQueue.add(((InternalNode) node).children);
                }
                sb.append('}');
                if (!queue.isEmpty())
                    sb.append(", ");
                else {
                    sb.append('\n');
                }
            }
            queue = nextQueue;
        }
        return sb.toString();
    }
    
    
    /**
     * This abstract class represents any type of node in the tree
     * This class is a super class of the LeafNode and InternalNode types.
     * 
     * @author Rohan Mendiratta (rmendiratta@wisc.edu)
     */
    private abstract class Node {
        
        // List of keys
        List<K> keys;
        
        /**
         * Package constructor
         */
        Node() {
            this.keys = new ArrayList<>();
        }
        
        /**
         * Inserts key and value in the appropriate leaf node 
         * and balances the tree if required by splitting
         *  
         * @param key
         * @param value
         */
        abstract void insert(K key, V value);

        /**
         * Gets the first leaf key of the tree
         * 
         * @return key
         */
        abstract K getFirstLeafKey();
        
        /**
         * Gets the new sibling created after splitting the node
         * 
         * @return Node
         */
        abstract Node split();
        
        /*
         * (non-Javadoc)
         * @see BPTree#rangeSearch(java.lang.Object, java.lang.String)
         */
        abstract List<V> rangeSearch(K key, String comparator);

        /**
         * 
         * @return boolean
         */
        abstract boolean isOverflow();
        
        public String toString() {
            return keys.toString();
        }

        /**
         * Gets the insert location within the leaf node
         * @param key to be added to the leaf node
         * @return index of where key should be added
         */
        int getInsertLocation(K key){
            if(keys.size() == 0){
                return 0;
            }
            for (int i = 0; i < keys.size(); i++) {

                if (keys.get(i).compareTo(key) > 0) {
                    return i;
                }
            }
            return keys.size();
        }



    } // End of abstract class Node
    
    /**
     * This class represents an internal node of the tree.
     * This class is a concrete sub class of the abstract Node class
     * and provides implementation of the operations
     * required for internal (non-leaf) nodes.
     * 
     * @author sapan
     */
    private class InternalNode extends Node {

        // List of children nodes
        List<Node> children;
        
        /**
         * Package constructor
         */
        InternalNode() {
            super();
            this.children = new ArrayList<>();
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#getFirstLeafKey()
         */
        K getFirstLeafKey() {
            return this.children.get(0).getFirstLeafKey();
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#isOverflow()
         */
        boolean isOverflow() {
            return children.size() > branchingFactor;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#insert(java.lang.Comparable, java.lang.Object)
         */
        void insert(K key, V value) {
            // inserts child into tree
            Node child = getChild(key);
            child.insert(key, value);

            // check if there is overflow of child node
            if (child.isOverflow()) {
                Node sibling = child.split();
                insertChild(sibling.getFirstLeafKey(), sibling);
                linkSiblingNode(key);
            }
            // check if there is overflow of the root
            if (root.isOverflow()) {
                Node sibling = split();
                InternalNode newRoot = new InternalNode();
                newRoot.keys.add(sibling.getFirstLeafKey());
                newRoot.children.add(this);
                newRoot.children.add(sibling);
                root = newRoot;
            }
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#split()
         */
        Node split() {
            int start = keys.size() / 2 + 1;
            int end = keys.size();

            InternalNode sibling = new InternalNode();

            // adds keys and children to sibling node
            sibling.keys.addAll(keys.subList(start, end));
            sibling.children.addAll(children.subList(start, end + 1));

            // removes keys and children from original node
            keys.subList(start - 1, end).clear();
            children.subList(start, end + 1).clear();

            return sibling;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#rangeSearch(java.lang.Comparable, java.lang.String)
         */
        List<V> rangeSearch(K key, String comparator) {
            List<V> results = new ArrayList<>();
            Node node = getLeafNode(key);
            // runs a range search in the positive direction
            if(comparator.equals(">=")) {
                while((node) != null) {
                    results.addAll(node.rangeSearch(key, comparator));
                    node = ((LeafNode)node).next;
                }
            }
            // runs a range search in the negative direction
            else if(comparator.equals("<=")) {
                while((node) != null) {
                    results.addAll(0, node.rangeSearch(key, comparator));
                    node = ((LeafNode) node).previous;
                }
            }
            return results;
        }

        /**
         * Gets the correct child based on the index
         * @param key key to compare against
         * @return child node
         */
        Node getChild(K key) {
            int childIndex = getInsertLocation(key);
            return children.get(childIndex);
        }

        /**
         * Gets the leaf node where a key should be placed.
         * @param key key to compare against
         * @return leaf node where the key belongs
         */
        Node getLeafNode(K key) {
            Node node = root;
            while(!node.getClass().equals(BPTree.LeafNode.class)) {
                node = ((InternalNode)node).getChild(key);
            }
            return node;
        }


        /**
         * Gets the leaf node where a value should be placed and finds the value corresponding to
         * the key
         * @param key key to be compared
         * @return value associated with the key.
         */
        V get(K key) {
            LeafNode node = (LeafNode)this.getLeafNode(key);
            return node.get(key);
        }

        /**
         * Gets the parent node given a key.
         * @param key key to be compared against.
         * @return parent node of a given key.
         */
        Node getParentNode(K key){
            Node node = root;
            Node previousNode = null;
            // iterate through the tree until a leaf is found
            while(!node.getClass().equals(BPTree.LeafNode.class)) {
                previousNode = node;
                node = ((InternalNode)node).getChild(key);
            }
            return previousNode;
        }

        /**
         * Links two nodes together based on a key
         * @param key key to be comapred against
         */
        void linkSiblingNode(K key){
            // get the parent node of the key
            InternalNode parent = (InternalNode)getParentNode(key);
            // link all of the keys in the node
            for (int i = 0; i < parent.children.size() - 1; i++) {
                LeafNode prev = (LeafNode) parent.children.get(i);
                LeafNode next = (LeafNode) parent.children.get(i + 1);
                prev.next = next;
                next.previous = prev;
            }
        }

        /**
         * Inserts a child into a node
         * @param key key to be inserted
         * @param child child to be inserted associated with the key
         */
        void insertChild(K key, Node child) {
            int insertLocation = getInsertLocation(key);
            keys.add(insertLocation, key);
            children.add(insertLocation + 1, child);
        }


    } // End of class InternalNode
    
    
    /**
     * This class represents a leaf node of the tree.
     * This class is a concrete sub class of the abstract Node class
     * and provides implementation of the operations that
     * required for leaf nodes.
     * 
     * @author Rohan Mendiratta(rmendiratta@wisc.edu)
     */
    private class LeafNode extends Node {

        // List of values
        List<V> values;
        
        // Reference to the next leaf node
        LeafNode next;
        
        // Reference to the previous leaf node
        LeafNode previous;
        
        /**
         * Package constructor
         */
        LeafNode() {
            super();
            this.values = new ArrayList<>();
        }

        /**
         * (non-Javadoc)
         * @see BPTree.Node#getFirstLeafKey()
         */
        K getFirstLeafKey() {
            return this.keys.get(0);
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#isOverflow()
         */
        boolean isOverflow() {
            return values.size() > branchingFactor - 1;
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#insert(Comparable, Object)
         */
        void insert(K key, V value) {
            // insert key
            int insertLocation = getInsertLocation(key);
            keys.add(insertLocation, key);
            values.add(insertLocation, value);
            numKeys++;

            // check if there is overflow at the root
            if (root.isOverflow()) {
                Node sibling = split();
                InternalNode newRoot = new InternalNode();
                newRoot.keys.add(sibling.getFirstLeafKey());
                newRoot.children.add(this);
                newRoot.children.add(sibling);
                root = newRoot;
            }
        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#split()
         */
        Node split() {
            LeafNode sibling = new LeafNode();
            int start = (this.keys.size() + 1) / 2;
            int end = this.keys.size();

            // adds keys and values to sibling
            sibling.keys.addAll(keys.subList(start, end));
            sibling.values.addAll(values.subList(start, end));

            // removes keys and values from original node
            keys.subList(start, end).clear();
            values.subList(start, end).clear();

            this.next = sibling;
            sibling.previous = this;
            return sibling;

        }
        
        /**
         * (non-Javadoc)
         * @see BPTree.Node#rangeSearch(Comparable, String)
         */
        List<V> rangeSearch(K key, String comparator) {
            List results = new ArrayList<V>();

            // iterate through a node checking for keys in range
            if(comparator.equals(">=")) {
                for (int i = 0; i < this.keys.size(); i++) {
                    if(this.keys.get(i).compareTo(key) >= 0) {
                        results.add(this.values.get(i));
                    }
                }
            }
            // iterate through a node checking for keys in range
            else if(comparator.equals("<=")) {
                for (int i = 0; i < this.keys.size(); i++) {
                    if(this.keys.get(i).compareTo(key) <= 0) {
                        results.add(this.values.get(i));
                    }
                }
            }
            return results;
        }

        /**
         * Gets the value for a key
         * @param key key to be compared against
         * @return value associated with the key
         */
        V get(K key) {
            for(int i = 0; i < this.keys.size(); i++) {
                if(this.keys.get(i).equals(key)){
                    return this.values.get(i);
                }
            }
            return null;
        }

    } // End of class LeafNode


    
    /**
     * Contains a basic test scenario for a BPTree instance.
     * It shows a simple example of the use of this class
     * and its related types.
     * 
     * @param args
     */
    public static void main(String[] args) {
        // create empty BPTree with branching factor of 3
        BPTree<Double, Double> bpTree = new BPTree<>(3);

        // create a pseudo random number generator
        Random rnd1 = new Random();

        // some value to add to the BPTree
        Double[] dd = {0.0d, 0.5d, 0.2d, 0.8d};

        // build an ArrayList of those value and add to BPTree also
        // allows for comparing the contents of the ArrayList 
        // against the contents and functionality of the BPTree
        // does not ensure BPTree is implemented correctly
        // just that it functions as a data structure with
        // insert, rangeSearch, and toString() working.
        List<Double> list = new ArrayList<>();
        for (int i = 0; i < 400; i++) {
            Double j = dd[rnd1.nextInt(4)];
            list.add(j);
            bpTree.insert(j, j);
            System.out.println("\n\nTree structure:\n" + bpTree.toString());
        }
        List<Double> filteredValues = bpTree.rangeSearch(0.2d, ">=");
        System.out.println("Filtered values: " + filteredValues.toString());
    }

} // End of class BPTree
