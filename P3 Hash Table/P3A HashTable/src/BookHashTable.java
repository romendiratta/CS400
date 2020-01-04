import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/** HashTable implementation that uses:
 * Bucket Chaining: Array of ArrayLists
 * Java's built in hashCode algorithm
 * A String type key and Book type value
 * @author Rohan Mendiratta
 */
public class BookHashTable implements HashTableADT<String, Book> {

    /** The initial capacity and load factor that is used if none is specified user */
    static final int DEFAULT_CAPACITY = 101;
    static final double DEFAULT_LOAD_FACTOR_THRESHOLD = 0.75;

    private int capacity; // capacity of Hash Table
    private double loadFactorThreshold; // Load factor threshold of Hash Table
    private int numKeys; // number of keys in the table
    private ArrayList[] hashTable; // table

    /**
     * Class to hold the key, value pair stored in each hash table location's ArrayList
     */
    class Element{
        String key; // key val
        Book value; // book associated with key
        Element(String key, Book value) {
            this.key = key;
            this.value = value;
        }
    }

    /**
     * REQUIRED default no-arg constructor
     * Uses default capacity and sets load factor threshold 
     * for the newly created hash table.
     */
    public BookHashTable() {
        this(DEFAULT_CAPACITY,DEFAULT_LOAD_FACTOR_THRESHOLD);
        this.hashTable = new ArrayList[DEFAULT_CAPACITY];
    }
    
    /**
     * Creates an empty hash table with the specified capacity 
     * and load factor.
     * @param initialCapacity number of elements table should hold at start.
     * @param loadFactorThreshold the ratio of items/capacity that causes table to resize and rehash
     */
    public BookHashTable(int initialCapacity, double loadFactorThreshold) {
        this.capacity = initialCapacity;
        this.loadFactorThreshold = loadFactorThreshold;
        this.hashTable = new ArrayList[initialCapacity];
    }

    /**
     * Returns the load factor for this hash table that determines when to increase the capacity
     * of this hash table
     * @return the load factor
     */
    @Override
    public double getLoadFactorThreshold() {
        return this.loadFactorThreshold;
    }

    /**
     * Capacity is the size of the hash table array
     * @return the current capacity.
     */
    @Override
    public int getCapacity() {
        return this.capacity;
    }

    /**
     * Returns the collision resolution scheme used for this hash table.
     * @return Bucket Chaining: Array of Array Lists
     */
    @Override
    public int getCollisionResolutionScheme() {
        return 4;
    }

    /**
     * Method to get a hash index for the table
     * @param key the key to be hashed
     * @return the index into the hash table
     */
    private int getHash(String key){
        return Math.abs(key.hashCode() % capacity);
    }

    /**
     * Add the key,value pair to the data structure and increase the number of keys.
     * @param key key of the book
     * @param value book to be added to hash table
     * @throws IllegalNullKeyException If key is null
     * @throws DuplicateKeyException If key is already in the hash table
     */
    @Override
    public void insert(String key, Book value) throws IllegalNullKeyException, DuplicateKeyException {
        // check if key is null
        if(key == null) {
            throw new IllegalNullKeyException();
        }

        // checks if key is already in the table
        if(checkIfKeyInTable(key)) {
            throw new DuplicateKeyException();
        }

        // check for resizing
        if(numKeys / (double)capacity > loadFactorThreshold) {
            reHash();
        }

        // get hash index
        int index = getHash(key);

        // make ArrayList if none exists
        if (hashTable[index] == null) {
            hashTable[index] = new ArrayList<Element>();
        }

        // add to table
        ArrayList<Element> bookList = hashTable[index];
        bookList.add(new Element(key, value));
        numKeys++;


    }

    /**
     * Rehashes the current table to one with a bigger size
     * @throws IllegalNullKeyException if key is nul
     * @throws DuplicateKeyException if duplicate key is added
     */
    private void reHash()throws IllegalNullKeyException, DuplicateKeyException{
        // increase capacity
        capacity = 2 * capacity + 1;
        // create temp hash table
        BookHashTable tempHashTable = new BookHashTable(capacity, loadFactorThreshold);
        // rehash and add elements into new table
        for(int i = 0; i < hashTable.length; i ++) {
            if(hashTable[i] != null){
                ArrayList<Element> bookList = hashTable[i];
                for(int j = 0; j < bookList.size(); j++)
                    tempHashTable.insert(bookList.get(j).key, bookList.get(j).value);
            }
        }
        this.hashTable = tempHashTable.hashTable;
    }

    /**
     * Checks if the key is in the hashtable
     * @param key key to be searched for
     * @return true if found, else false
     */
    private boolean checkIfKeyInTable(String key){
        try{
            this.get(key);
            return true;
        }
        catch (KeyNotFoundException e){
            return false;
        }
        catch (Exception e){}
        return false;
    }

    /**
     * If Book is found, remove the Book from the hash table
     * Decrease number of keys.
     * @param key key to be removed
     * @return true if key removed, else false
     * @throws IllegalNullKeyException If key is null,
     */
    @Override
    public boolean remove(String key) throws IllegalNullKeyException {
        // check for null key
        if(key == null) {
            throw new IllegalNullKeyException();
        }
        // check if key is in table
        if(!checkIfKeyInTable(key)) {
            return false;
        }

        // get hash index
        int index = getHash(key);

        // get ArrayList at correct index
        ArrayList<Element> bookList =  hashTable[index];

        // search through ArrayList and remove element
        for(int i = 0; i < bookList.size(); i++) {
            if(bookList.get(i).key.equals(key) ) {
                bookList.remove(i);
                if(bookList.size() == 0) {
                    hashTable[index] = null;
                }
                break;
            }
        }
        numKeys--;
        return true;
    }

    /**
     * Returns the Book associated with the specified key
     * @param key key of the book
     * @return Book associated with the key
     * @throws IllegalNullKeyException If key is null
     * @throws KeyNotFoundException If key is not found,
     */
    @Override
    public Book get(String key) throws IllegalNullKeyException, KeyNotFoundException {
        if(key == null) {
            throw new IllegalNullKeyException();
        }

        // get hash index
        int index = getHash(key);

        // if index is null, key does not exist
        if(hashTable[index] == null) {
            throw new KeyNotFoundException();
        }

        // search through ArrayList for the key
        ArrayList<Element> bookList = hashTable[index];
        for(int i = 0; i < bookList.size(); i++) {
            if(bookList.get(i).key.equals(key)) {
                return bookList.get(i).value;
            }
        }
        throw new KeyNotFoundException();
    }

    /**
     * Gets the number of keys in the hash table
     * @return number of keys in the hash table
     */
    @Override
    public int numKeys() {
        return this.numKeys;
    }

}