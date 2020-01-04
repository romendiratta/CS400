import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * This runs a set of tests against a list ADT.
 * @author Rohan Mendiratta
 * @param <T> Data Structure to test
 */
abstract class DataStructureADTTest<T extends DataStructureADT<String,String>> {

	private T dataStructureInstance;

	protected abstract T createInstance();

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		dataStructureInstance = createInstance();
	}

	@AfterEach
	void tearDown() throws Exception {
		dataStructureInstance = null;
	}


	@Test
	void test00_empty_ds_size(){
		// Checks if DS is empty
		if (dataStructureInstance.size() != 0)
			fail("data structure should be empty, with size=0, but size= "
				+dataStructureInstance.size());
	}

	@Test
	void test01_after_insert_one_size_is_one() {
		// Inserts an element into DS
		dataStructureInstance.insert("Test Key", "Test Value");

		// Checks that size of DS is correct
		if (dataStructureInstance.size() != 1) {
			fail("data structure should have 1 element, with size=1, but size=" +
				dataStructureInstance.size());
		}
	}

	@Test
	void test02_after_insert_one_remove_one_size_is_0() {
		// Inserts an element into the DS
		dataStructureInstance.insert("Test Key", "Test Value");
		//Removes element from DS
		dataStructureInstance.remove("Test Key");
		// Checks that data structure is empty
		if (dataStructureInstance.size() != 0) {
			fail("data structure should have 0 elements after insert and remove, but size=" +
				dataStructureInstance.size());
		}
	}

	@Test
	void test03_duplicate_exception_is_thrown() {
		try {
			// inserts one element into DS
			dataStructureInstance.insert("Key1", "Value1");
			// inserts second element into DS
			dataStructureInstance.insert("Key2", "Value2");
			// inserts a duplicate element
			dataStructureInstance.insert("Key1", "Value3");

			// fail if no exception is thrown
			fail("expected a runtime exception, but was not thrown");

		}
		// catches exception
		catch (RuntimeException e){

		}
	}

	@Test
	void test04_remove_returns_false_when_key_not_present() {
		// inserts one element into DS
		dataStructureInstance.insert("Key1", "Value1");
		// inserts second element into DS
		dataStructureInstance.insert("Key2", "Value2");
		// inserts a third element into DS
		dataStructureInstance.insert("Key3", "Value3");

		// attempts to remove a key that doesn't exist
		boolean result = dataStructureInstance.remove("Key4");
		if (result) {
			fail("expected false when removing a key that doesn't exists, but got " + result);
		}
	}

	@Test
	void test05_insert_two_remove_one_size_is_1(){
		// inserts first two elements into DS
		dataStructureInstance.insert("Key1", "Value1");
		dataStructureInstance.insert("Key2", "Value2");

		//removes element from DS
		dataStructureInstance.remove("Key2");

		// checks for correct size
		if(dataStructureInstance.size() != 1) {
			fail("expected size=1 after two insert and one remove, but size=" +
				dataStructureInstance.size());
		}
	}

	@Test
	void test06_check_valid_element_is_returned_on_get(){
		Object result = null;
		// inserts first few elements
		dataStructureInstance.insert("Key1", "Value1");
		dataStructureInstance.insert("Key2", "Value2");
		dataStructureInstance.insert("Key3", "Value3");
		dataStructureInstance.insert("Key4", "Value4");
		dataStructureInstance.insert("Key5", "Value5");
		dataStructureInstance.insert("Key6", "Value6");

		result = dataStructureInstance.get("Key4");
		if(result == null) {
			fail("expected to get an element when searching for existing key, but got nothing");
		}
	}
	@Test
	void test07_check_insert_does_not_accept_null_key(){
		try {
			dataStructureInstance.insert(null, null);
			fail("expected an exception when inserting null key, but none were thrown");
		}
		catch (IllegalArgumentException e){}
	}

	@Test
	void test08_contains_returns_false_with_null_key(){
		// inserts first few elements
		dataStructureInstance.insert("Key1", "Value1");
		dataStructureInstance.insert("Key2", "Value2");
		dataStructureInstance.insert("Key3", "Value3");
		dataStructureInstance.insert("Key4", "Value4");
		dataStructureInstance.insert("Key5", "Value5");
		dataStructureInstance.insert("Key6", "Value6");

		if (dataStructureInstance.contains(null)) {
			fail("expected false when searching for a null key, but got true");
		}
	}

	@Test
	void test09_remove_does_not_accept_null_key(){
		// inserts first few elements
		dataStructureInstance.insert("Key1", "Value1");
		dataStructureInstance.insert("Key2", "Value2");
		dataStructureInstance.insert("Key3", "Value3");
		dataStructureInstance.insert("Key4", "Value4");
		dataStructureInstance.insert("Key5", "Value5");

		try {
			dataStructureInstance.remove(null);
			fail("expected exception when removing a null key, but none were thrown");
		}
		catch (IllegalArgumentException e) {}
	}

	@Test
	void test10_contain_returns_false_with_wrong_key(){
		// inserts first few elements
		dataStructureInstance.insert("Key1", "Value1");
		dataStructureInstance.insert("Key2", "Value2");
		dataStructureInstance.insert("Key3", "Value3");
		dataStructureInstance.insert("Key4", "Value4");
		dataStructureInstance.insert("Key5", "Value5");

		if(dataStructureInstance.contains("wrong key")) {
			fail("expected false when searching for the wrong key, but got true");
		}
	}

	@Test
	void test11_check_add_and_remove_of_many_items() {
		// inserts 500 items into DS
		for(int i = 0; i < 1000 ;i++) {
			dataStructureInstance.insert("Key" + i, "Value" + i);
		}
		if(dataStructureInstance.size() != 1000) {
			fail("added 500 items to DS, but size=" + dataStructureInstance.size());
		}
		//removes 500 items from DS
		for(int i = 0; i < 1000; i++) {
			dataStructureInstance.remove("Key" + i);
		}

		// checks that the DS is empty
		if(dataStructureInstance.size() != 0) {
			fail("added and deleted 500 items expected size=0; but size=" +
				dataStructureInstance.size());
		}
	}

	@Test
	void test12_add_remove_add_duplicate_key(){
		// inserts first element
		dataStructureInstance.insert("Key1", "Value2");
		// removes element
		dataStructureInstance.remove("Key1");

		try{
			// adds same key back after removal
			dataStructureInstance.insert("Key1", "Value2");
		}
		catch (RuntimeException e){
			fail("encounterd an exception, when adding non duplicate key");
		}
	}
}
