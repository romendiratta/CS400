import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/** 
 * Test HashTable class implementation to ensure that required 
 * functionality works for all cases.
 * @author Rohan Mendiratta
 *
 */
public class BookHashTableTest {

    // Default name of books data file
    public static final String BOOKS = "books.csv";

    // Empty hash tables that can be used by tests
    static BookHashTable bookObject;
    static ArrayList<Book> bookTable;

    static final int INIT_CAPACITY = 2;
    static final double LOAD_FACTOR_THRESHOLD = 0.49;
       
    static Random RNG = new Random(0);  // seeded to make results repeatable (deterministic)

    /** Create a large array of keys and matching values for use in any test */
    @BeforeAll
    public static void beforeClass() throws Exception{
        bookTable = BookParser.parse(BOOKS);
    }
    
    /** Initialize empty hash table to be used in each test */
    @BeforeEach
    public void setUp() throws Exception {
         bookObject = new BookHashTable(INIT_CAPACITY,LOAD_FACTOR_THRESHOLD);
    }

    /** Not much to do, just make sure that variables are reset     */
    @AfterEach
    public void tearDown() throws Exception {
        bookObject = null;
    }

    /** inserts all the elements of bookTable into the bookObject */
    private void insertMany(ArrayList<Book> bookTable)
        throws IllegalNullKeyException, DuplicateKeyException {
        for (int i=0; i < bookTable.size(); i++ ) {
            bookObject.insert(bookTable.get(i).getKey(), bookTable.get(i));
        }
    }

    /**
     * Tests that a HashTable is empty upon initialization
     */
    @Test
    public void test000_collision_scheme() {
        if (bookObject == null)
        	fail("Gg");
    	int scheme = bookObject.getCollisionResolutionScheme();
        if (scheme < 1 || scheme > 9) 
            fail("collision resolution must be indicated with 1-9");
    }

    /**
     * Tests that a HashTable is empty upon initialization
     */
    @Test
    public void test000_IsEmpty() {
        //"size with 0 entries:"
        assertEquals(0, bookObject.numKeys());
    }

    /**
     * Tests that a HashTable is not empty after adding one (key,book) pair
     * @throws DuplicateKeyException 
     * @throws IllegalNullKeyException 
     */
    @Test
    public void test001_IsNotEmpty() throws IllegalNullKeyException, DuplicateKeyException {
    	bookObject.insert(bookTable.get(0).getKey(),bookTable.get(0));
        String expected = ""+1;
        //"size with one entry:"
        assertEquals(expected, ""+bookObject.numKeys());
    }
    
    /**
    * Test if the hash table  will be resized after adding two (key,book) pairs
    * given the load factor is 0.49 and initial capacity to be 2.
    */
    @Test 
    public void test002_Resize() throws IllegalNullKeyException, DuplicateKeyException {
    	bookObject.insert(bookTable.get(0).getKey(),bookTable.get(0));
    	int cap1 = bookObject.getCapacity(); 
    	bookObject.insert(bookTable.get(1).getKey(),bookTable.get(1));
    	int cap2 = bookObject.getCapacity();
    	
        //"size with one entry:"
        assertTrue(cap2 > cap1 & cap1 ==2);
    }

    /**
     * Test if getting an element from the hash table works using the book's key after inserting
     * many elements
     */
    @Test
    public void test003_InsertAndGetElementFromTable() throws IllegalNullKeyException,
        DuplicateKeyException {
        insertMany(bookTable);

        // checks that the first 50 books are equal to getting the first books get from the table
        try {
            for(int i = 0; i < 50; i ++) {
                assertEquals(bookTable.get(i), bookObject.get(bookTable.get(i).getKey()));
            }
        }

        catch(KeyNotFoundException e){
            fail("Tried to get key, but KeyNotRound exception was thrown");
        }
    }

    /**
     * Tests that adding and removing from the hash table returns valid results
     */
    @Test
    public void test004_TestRemoveFromTable() throws IllegalNullKeyException, DuplicateKeyException{

        // inserts a few keys into the table
        bookObject.insert(bookTable.get(1).getKey(), bookTable.get(1));
        bookObject.insert(bookTable.get(2).getKey(), bookTable.get(2));
        bookObject.insert(bookTable.get(3).getKey(), bookTable.get(3));
        bookObject.insert(bookTable.get(4).getKey(), bookTable.get(4));
        bookObject.insert(bookTable.get(5).getKey(), bookTable.get(5));

        //removes a couple from the table
        bookObject.remove(bookTable.get(1).getKey());
        bookObject.remove(bookTable.get(4).getKey());

        // check that number of keys is correct
        assertEquals(3, bookObject.numKeys());

        try {
            bookObject.get(bookTable.get(1).getKey());
            fail("Expected KeyNotFound exception, but none was thrown");
        }
            catch (KeyNotFoundException e){}


    }

    /**
     * Inserts multiple unique keys into the table with the same value
     */
    @Test
    public void test005_InsertSameValue() throws IllegalNullKeyException, DuplicateKeyException {
        try {
            bookObject.insert(bookTable.get(0).getKey(), bookTable.get(0));
            bookObject.insert(bookTable.get(1).getKey(), bookTable.get(0));
            bookObject.insert(bookTable.get(2).getKey(), bookTable.get(0));
        }
        catch (Exception e) {
            fail("Inserting unique key with same values threw an unexpected exception");
        }

    }

    /**
     * Tests inserting many and removing them all
     */
    @Test
    public void test006_InsertManyAndRemoveAll() throws IllegalNullKeyException, DuplicateKeyException{

            insertMany(bookTable);

            for(int i = 0; i < bookTable.size(); i++) {
            bookObject.remove(bookTable.get(i).getKey());
        }
    }
}
