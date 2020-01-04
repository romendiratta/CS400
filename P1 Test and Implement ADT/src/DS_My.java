/**
 * This class is a List Data Structure implemented using arrays.
 * @author Rohan Mendiratta
 * @version 1.0
 */
public class DS_My implements DataStructureADT {

	/**
	 * This is a helper class to hold data inserted into the List
	 * @param <K> Key
	 * @param <V> Value
	 */
    private class Element<K extends Comparable<K>, V>{

        private K key;
        private V value;

        private Element(K key, V value){
            this.key = key;
            this.value = value;
        }

        private K getKey(){
            return this.key;
        }

        private V getValue() {
            return this.value;
        }

    }

	Element[] store; // data store
	int size; // size of list

    public DS_My() {
        store = new Element[0];
        size = 0;
    }

	/**
	 * Adds a (key,value) pair to the List
	 * @param k key
	 * @param v value
	 */
    @Override
    public void insert(Comparable k, Object v) {
    	// checks for null key
    	if(k == null) {
    		throw new IllegalArgumentException("null key");
		}
		// checks for duplicate keys
        if (contains(k)){
            throw new RuntimeException("duplicate key");
        }
        // copies old store into larger array
        Element[] newStore = new Element[size + 1];
        for(int i = 0; i < store.length; i++) {
            newStore[i] = store[i];
        }
        size++;
        // adds the new element to the end of the list
        newStore[size - 1] = new Element(k, v);
        store = newStore;
    }

	/**
	 * Removes an element from the list.
	 * @param k key
	 * @return true if element is removed, else false
	 */
	@Override
    public boolean remove(Comparable k) {
		// checks for a null key
        if (k == null) {
            throw new IllegalArgumentException("null key");
        }
        boolean match = false;
        int index = -1;
        // checks the list for a matching element
        for(int i = 0; i < store.length; i++) {
            if(store[i].getKey().equals(k)) {
                match = true;
                index = i;
                size --;
            }
        }

        if(!match) {
            return false;
        }

        Element[] newStore = new Element[size];
        int newStoreIdx = -1;
        // copies store into new store and skips element to be removed
        for(int i = 0; i < store.length; i++) {
            newStoreIdx++;
            if(i == index) {
                newStoreIdx--;
                continue;
            }
            else {
                newStore[newStoreIdx] = store[i];
            }
        }
        store = newStore;
        return true;
    }

	/**
	 * Checks if the list contains a specified key
	 * @param k key
	 * @return true if match found, else false
	 */
	@Override
    public boolean contains(Comparable k) {
        for (int i = 0; i < store.length; i++) {
            if(store[i].getKey().equals(k)){
                return true;
            }
        }
        return false;
    }

	/**
	 * Gets an element based on a specific key
	 * @param k key
	 * @return element found
	 */
	@Override
    public Object get(Comparable k) {
    	if(k == null) {
    		throw new IllegalArgumentException("null key");
		}
		for (int i =0; i < store.length; i++) {
			if (store[i].getKey().equals(k)){
				return store[i];
			}
		}
		return null;
    }

	/**
	 * Gets the size of the list
	 * @return size
	 */
	@Override
    public int size() {
        return size;
    }

}
