import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

//@SuppressWarnings("rawtypes")
public class BALSTTest {

    BALST<String, String> balst1;
    BALST<Integer, String> balst2;

    /**
     * @throws java.lang.Exception
     */
    @BeforeEach
    void setUp() throws Exception {
        balst1 = createInstance();
        balst2 = createInstance2();
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterEach
    void tearDown() throws Exception {
        balst1 = null;
        balst2 = null;
    }

    protected BALST<String, String> createInstance() {
        return new BALST<String, String>();
    }

    protected BALST<Integer, String> createInstance2() {
        return new BALST<Integer, String>();
    }

    /**
     * Insert three values in sorted order and then check the root, left, and right keys to see if
     * rebalancing occurred.
     */
    @Test
    void testBALST_001_insert_sorted_order_simple() {
        try {
            balst2.insert(10, "10");
            if (!balst2.getKeyAtRoot().equals(10)) fail("avl insert at root does not work");

            balst2.insert(20, "20");
            if (!balst2.getKeyOfRightChildOf(10).equals(20)) fail("avl insert to right child of root does not work");

            balst2.insert(30, "30");
            Integer k = balst2.getKeyAtRoot();
            if (!k.equals(20)) fail("avl rotate does not work");

            // IF rebalancing is working,
            // the tree should have 20 at the root
            // and 10 as its left child and 30 as its right child

            Assert.assertEquals(balst2.getKeyAtRoot(), new Integer(20));
            Assert.assertEquals(balst2.getKeyOfLeftChildOf(20), new Integer(10));
            Assert.assertEquals(balst2.getKeyOfRightChildOf(20), new Integer(30));


        } catch (Exception e) {
            e.printStackTrace();
            fail("Unexpected exception AVL 001: " + e.getMessage());
        }
    }

    /**
     * Insert three values in reverse sorted order and then check the root, left, and right keys to
     * see if rebalancing occurred in the other direction.
     */
    @Test
    void testBALST_002_insert_reversed_sorted_order_simple() {
        try {
            balst2.insert(30, "30");
            if (!balst2.getKeyAtRoot().equals(30)) fail("avl insert at root does not work");

            balst2.insert(20, "20");
            if (!balst2.getKeyOfLeftChildOf(30).equals(20))
                fail("avl insert to right child of root does not work");

            balst2.insert(10, "10");
            Integer k = balst2.getKeyAtRoot();
            if (!k.equals(20)) fail("avl rotate does not work");

            // IF rebalancing is working,
            // the tree should have 20 at the root
            // and 10 as its left child and 30 as its right child

            Assert.assertEquals(balst2.getKeyAtRoot(), new Integer(20));
            Assert.assertEquals(balst2.getKeyOfLeftChildOf(20), new Integer(10));
            Assert.assertEquals(balst2.getKeyOfRightChildOf(20), new Integer(30));


        } catch (Exception e) {
            e.printStackTrace();
            fail("Unexpected exception AVL 002: " + e.getMessage());
        }

    }

    /**
     * Insert three values so that a right-left rotation is needed to fix the balance.
     * <p>
     * Example: 10-30-20
     * <p>
     * Then check the root, left, and right keys to see if rebalancing occurred in the other
     * direction.
     */
    @Test
    void testBALST_003_insert_smallest_largest_middle_order_simple() {
        try {
            balst2.insert(10, "10");
            if (!balst2.getKeyAtRoot().equals(10)) fail("avl insert at root does not work");

            balst2.insert(30, "30");
            if (!balst2.getKeyOfRightChildOf(10).equals(30))
                fail("avl insert to right child of root does not work");

            balst2.insert(20, "20");
            Integer k = balst2.getKeyAtRoot();
            if (!k.equals(20)) fail("avl rotate does not work");

            // IF rebalancing is working,
            // the tree should have 20 at the root
            // and 10 as its left child and 30 as its right child

            Assert.assertEquals(balst2.getKeyAtRoot(), new Integer(20));
            Assert.assertEquals(balst2.getKeyOfLeftChildOf(20), new Integer(10));
            Assert.assertEquals(balst2.getKeyOfRightChildOf(20), new Integer(30));


        } catch (Exception e) {
            e.printStackTrace();
            fail("Unexpected exception AVL 003: " + e.getMessage());
        }

    }

    /**
     * Insert five values values so that a left-right rotation is
     * needed to fix the balance.
     *
     * Example: 30-10-20
     *
     * Then check the root, left, and right keys to see if rebalancing
     * occurred in the other direction.
     */
    @Test
    void testBALST_004_insert_largest_smallest_middle_order_simple() {
        try {
            balst2.insert(30, "30");
            if (!balst2.getKeyAtRoot().equals(30)) fail("avl insert at root does not work");

            balst2.insert(10, "10");
            if (!balst2.getKeyOfLeftChildOf(30).equals(10))
                fail("avl insert to right child of root does not work");

            balst2.insert(20, "20");
            Integer k = balst2.getKeyAtRoot();
            if (!k.equals(20)) fail("avl rotate does not work");

            // IF rebalancing is working,
            // the tree should have 20 at the root
            // and 10 as its left child and 30 as its right child

            Assert.assertEquals(balst2.getKeyAtRoot(), new Integer(20));
            Assert.assertEquals(balst2.getKeyOfLeftChildOf(20), new Integer(10));
            Assert.assertEquals(balst2.getKeyOfRightChildOf(20), new Integer(30));


        } catch (Exception e) {
            e.printStackTrace();
            fail("Unexpected exception AVL 003: " + e.getMessage());
        }
    }

    /**
     * Insert five values to force rotation with a larger tree.
     *
     * Then check the root, left, and right keys to see if rebalancing occurred in the other
     * direction.
     * Also checks that inOrder traversal matches
     */
    @Test
    void testBALST_005_insert_5_elements_in_sorted_order() {
        try {
            balst2.insert(10, "10");
            if (!balst2.getKeyAtRoot().equals(10)) fail("avl insert at root does not work");

            balst2.insert(20, "20");
            if (!balst2.getKeyOfRightChildOf(10).equals(20))
                fail("avl insert to right child of root does not work");

            balst2.insert(30, "30");
            Integer k = balst2.getKeyAtRoot();
            if (!k.equals(20)) fail("avl rotate does not work");

            balst2.insert(40, "40");

            balst2.insert(50, "50");

            List<Integer> expectedList = Arrays.asList(10, 20, 30, 40, 50);
            List<Integer> list = balst2.getInOrderTraversal();

            if(!expectedList.equals(list)) {
                fail("Test: 005 AVL structure is incorrect ");
            }


        } catch (Exception e) {
            e.printStackTrace();
            fail("Unexpected exception AVL 005: " + e.getMessage());
        }
    }

    /**
     * Insert five values in reverse sorted order to force a rebalance

     * Then check the root, left, and right keys to see if rebalancing occurred in the other
     * direction.
     * Also checks in order traversal against expected.
     */
    @Test
    void testBALST_006_insert_5_elements_in_reverse_sorted_order() {
        try {
            balst2.insert(50, "50");
            if (!balst2.getKeyAtRoot().equals(50)) fail("avl insert at root does not work");

            balst2.insert(40, "40");
            if (!balst2.getKeyOfLeftChildOf(50).equals(40))
                fail("avl insert to right child of root does not work");

            balst2.insert(30, "30");
            Integer k = balst2.getKeyAtRoot();
            if (!k.equals(40)) fail("avl rotate does not work");

            balst2.insert(20, "40");

            balst2.insert(10, "50");

            List<Integer> expectedList = Arrays.asList(10,20,30,40,50);
            List<Integer> list = balst2.getInOrderTraversal();

            if(!expectedList.equals(list)) {
                fail("Test: 006 AVL structure is incorrect ");
            }


        } catch (Exception e) {
            e.printStackTrace();
            fail("Unexpected exception AVL 006: " + e.getMessage());
        }
    }

    /**
     * Inserts values and check that get returns the correct integer value.
     */
    @Test
    void testBALST_007_check_get_integer() {
        try {
            balst2.insert(20, "20");

            balst2.insert(10, "10");

            balst2.insert(30, "30");

            balst2.insert(40, "40");

            if(!balst2.get(20).equals("20")) {
                fail("Test: 007 get method failed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            fail("Unexpected exception AVL 007: " + e.getMessage());
        }
    }

    /**
     * Inserts values and check that get returns the correct string value.
     */
    @Test
    void testBALST_008_check_get_string() {
        try {
            balst1.insert("20", "20");

            balst1.insert("10", "10");

            balst1.insert("30", "30");

            balst1.insert("40", "40");

            if(!balst1.get("20").equals("20")) {
                fail("Test: 008 get method failed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            fail("Unexpected exception AVL 008: " + e.getMessage());
        }
    }

    /**
     * Inserts values and checks that removing from the tree works.
     */
    @Test
    void testBALST_000_test_remove() {
        try {
            balst2.insert(20, "20");

            balst2.insert(10, "10");

            balst2.insert(30, "30");

            balst2.insert(40, "40");

            balst2.remove(30);

            List<Integer> expected = Arrays.asList(10,20,40);
            List<Integer> list = balst2.getInOrderTraversal();


            if(!list.equals(expected)) {
                fail("remove failed 008");
            }

        } catch (Exception e) {
            e.printStackTrace();
            fail("Unexpected exception AVL 008: " + e.getMessage());
        }
    }

}


