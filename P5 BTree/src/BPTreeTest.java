import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;



/**
 * Test BPTree class implementation to ensure that required
 * functionality works for all cases.
 * @author Rohan Mendiratta
 *
 */
public class BPTreeTest {

	BPTree bpTree; // BPTree used for testing

	/**
	 * Initialize empty BPTree to be used in each test
	 */
	@BeforeEach
	public void setUp() throws Exception {
	}

	/**
	 * Reset BPTree after each use
	 */
	@AfterEach
	public void tearDown() throws Exception {
		this.bpTree = null;
	}

	/**
	 * Tests that an empty tree is created correctly.
	 */
	@Test
	public void test00_createEmptyTree_b3(){
		this.bpTree = new BPTree(3);
		String expected = "{[]}\n";
		assertEquals(expected, bpTree.toString());
	}

	/**
	 * Test that a single insertion behaves correctly.
	 */
	@Test
	public void test01_insertOne_b3(){
		this.bpTree = new BPTree(3);
		String expected = "{[1]}\n";
		bpTree.insert(1, "Value1");
		assertEquals(expected, bpTree.toString());
	}

	/**
	 * Tests that the tree splits correctly with a branching factor of 3.
	 */
	@Test
	public void test02_forceSplit_b3(){
		this.bpTree = new BPTree(3);
		String expected = "{[3]}\n" + "{[1, 2], [3]}\n";
		bpTree.insert(1,1);
		bpTree.insert(2,2);
		bpTree.insert(3,3);
		assertEquals(expected, bpTree.toString());
	}

	/**
	 * Tests that the tree splits correctly with a branching factor of 4.
	 */
	@Test
	public void test03_forceSplit_b4(){
		this.bpTree = new BPTree(4);
		String expected = "{[3]}\n" + "{[1, 2], [3, 4]}\n";
		bpTree.insert(1,1);
		bpTree.insert(2,2);
		bpTree.insert(3,3);
		bpTree.insert(4,4);
		assertEquals(expected, bpTree.toString());
	}

	/**
	 * Tests that the tree splits correctly with a branching factor of 5.
	 */
	@Test
	public void test04_forceSplit_b5(){
		this.bpTree = new BPTree(5);
		String expected = "{[4]}\n" + "{[1, 2, 3], [4, 5]}\n";
		bpTree.insert(1,1);
		bpTree.insert(2,2);
		bpTree.insert(3,3);
		bpTree.insert(4,4);
		bpTree.insert(5,5);
		assertEquals(expected, bpTree.toString());
	}

	/**
	 * Tests that the tree splits correctly, when adding multiple keys to an internal node with a
	 * branching factor of 3
	 */
	@Test
	public void test05_forceSplit_b3(){
		this.bpTree = new BPTree(3);
		String expected = "{[3]}\n" + "{[1, 2], [3, 4]}\n";
		bpTree.insert(1,1);
		bpTree.insert(2,2);
		bpTree.insert(3,3);
		bpTree.insert(4,4);
		assertEquals(expected, bpTree.toString());
	}

	/**
	 * Tests that the tree splits correctly, when adding multiple keys to an internal node with a
	 * branching factor of 3
	 */
	@Test
	public void test06_add10_b3(){
		this.bpTree = new BPTree(3);
		String expected = "{[5]}\n" + "{[3], [7, 9]}\n" +
			"{[1, 2], [3, 4]}, {[5, 6], [7, 8], [9, 10]}\n";
		for(int i = 1; i < 11; i++) {
			bpTree.insert(i,i);
		}
		assertEquals(expected, bpTree.toString());
	}

	/**
	 * Tests the range search functionality after adding 10 elements to the tree.
	 */
	@Test
	public void test07_add10_rangeSearch_biggerThan5_b3(){
		this.bpTree = new BPTree(3);
		List expected = new ArrayList();
		expected.add(5);
		expected.add(6);
		expected.add(7);
		expected.add(8);
		expected.add(9);
		expected.add(10);
		for(int i = 1; i < 11; i++) {
			bpTree.insert(i,i);
		}
		assertEquals(expected, bpTree.rangeSearch(5, ">="));
	}

	/**
	 * Tests the range search functionality after adding 10 elements to the tree.
	 */
	@Test
	public void test08_add10_rangeSearch_lessThan5_b3(){
		this.bpTree = new BPTree(3);
		List expected = new ArrayList();
		expected.add(1);
		expected.add(2);
		expected.add(3);
		expected.add(4);
		expected.add(5);
		for(int i = 1; i < 11; i++) {
			bpTree.insert(i,i);
		}
		assertEquals(expected, bpTree.rangeSearch(5, "<="));
	}

	/**
	 * Tests the get functionality after adding 10 elements to the tree.
	 */
	@Test
	public void test09_add10_get_b3(){
		this.bpTree = new BPTree(3);
		int expected = 5;
		for(int i = 1; i < 11; i++) {
			bpTree.insert(i,i);
		}
		assertEquals(expected, this.bpTree.get(5));
	}

	/**
	 * Tests the get functionality after adding 20 elements to the tree in random order
	 */
	@Test
	public void test10_add20_randomOrder(){
		this.bpTree = new BPTree(3);
		Double[] dd = {1.0, 1.4, 0.0, 0.8, 0.9, 0.0, 2.6, 1.4, 1.0, 2.6, 0.5, 0.2, 0.0, 1.0,
			2.6, 0.2, 0.5, 1.4, 1.0, 0.2};
		List expected = new ArrayList();

		String expectedTree = "{[0.8, 1.0]}\n" +
			"{[0.2], [1.0], [2.6]}\n" +
			"{[0.0, 0.2], [0.5]}, {[0.9], [1.0]}, {[1.4, 1.4], [2.6]}\n" +
			"{[0.0, 0.0], [0.0, 0.2], [0.2]}, {[0.2, 0.5], [0.5]}, {[0.8], " +
			"[0.9, 1.0]}, {[1.0], [1.0]}, {[1.0, 1.4], [1.4], [1.4, 2.6]}, {[2.6], [2.6]}\n";
		for(int i = 0; i < dd.length; i++) {
			expected.add(dd[i]);
			bpTree.insert(dd[i], dd[i]);

		}
		Collections.sort(expected);
//		assertEquals(expectedTree, this.bpTree.toString());
		assertEquals(expected, this.bpTree.rangeSearch(0d, ">="));
	}

	/**
	 * Tests the get functionality after adding 20 elements to the tree in random order
	 */
	@Test
	public void test11_add20_randomOrder(){
		this.bpTree = new BPTree(3);
		Double[] dd = {1.0, 1.4, 0.0, 0.8, 0.9, 0.0, 2.6, 1.4, 1.0, 2.6, 0.5, 0.2, 0.0, 1.0,
			2.6, 0.2, 0.5, 1.4, 1.0, 0.2};
		List expected = new ArrayList();
		for(int i = 0; i < dd.length; i++) {
			expected.add(dd[i]);
			bpTree.insert(dd[i], dd[i]);

		}
		Collections.sort(expected);
		assertEquals(expected, this.bpTree.rangeSearch(20d, "<="));
	}
}




