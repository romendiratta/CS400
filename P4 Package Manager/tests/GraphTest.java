import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * FileName: GraphTest.java
 * Project: P4 Package Manager
 * Author: Eohan Mendiratta
 *
 * Test Graph class implementation to ensure that required
 * functionality works for all cases.
 * @author Rohan Mendiratta
 *
 *
 */
public class GraphTest {

	static Graph directedGraph;

	/** Initialize new Graph object before each test */
	@BeforeEach
	public void setUp() throws Exception {
		directedGraph = new Graph();
	}
	/** Tear down the graph object after  */
	@AfterEach
	public void tearDown() throws Exception {
		directedGraph = null;
	}

	/**
	 * Check that the size of the graph is correct after instantiation.
	 */
	@Test
	public void test00_checkEmptyGraphSize() {
		assertEquals(0, directedGraph.order());
		assertEquals(0, directedGraph.size());
	}

	/**
	 * Check that after adding one vertex the order is correct.
	 */
	@Test
	public void test001_checkAddingVertex() {
		directedGraph.addVertex("vertex1");
		assertEquals(1, directedGraph.order());
		Set set = new HashSet();
		set.add("vertex1");
		assertEquals(set, directedGraph.getAllVertices());
	}

	/**
	 * Checks adding two vertices and an edge
	 */
	@Test
	public void test002_checkAddingVerticesAndEdges() {
		directedGraph.addVertex("vertex1");
		directedGraph.addVertex("vertex2");
		assertEquals(2, directedGraph.order());
		assertEquals(0, directedGraph.size());
		directedGraph.addEdge("vertex1", "vertex2");
		assertEquals(1, directedGraph.size());
	}

	/**
	 * Checks adding two vertices and an edge
	 */
	@Test
	public void test003_checkAdjacentVertices() {
		directedGraph.addVertex("vertex1");
		directedGraph.addVertex("vertex2");
		directedGraph.addEdge("vertex1", "vertex2");
		List<String> list =  new ArrayList<>();
		list.add("vertex2");
		assertEquals(list, directedGraph.getAdjacentVerticesOf("vertex1"));
	}

	/**
	 * Checks adding two vertices and an edge
	 */
	@Test
	public void test004_addEdgeWithOneVertex() {
		directedGraph.addVertex("vertex1");
		directedGraph.addEdge("vertex1", "vertex2");
		assertEquals(2, directedGraph.order());
		assertEquals(1, directedGraph.size());
	}

	/**
	 * Checks removing an edge vertex, that is a dependency
	 */
	@Test
	public void test005_checkRemovingVertexWithDependency() {
		directedGraph.addVertex("vertex1");
		directedGraph.addEdge("vertex1", "vertex2");
		directedGraph.removeVertex("vertex2");
		assertEquals(1, directedGraph.order());
		assertEquals(0, directedGraph.size());
		List expectedList = new ArrayList();
		List list = directedGraph.getAdjacentVerticesOf("vertex1");
		assertEquals(expectedList, list);
	}

	/**
	 * Checks adding two vertices and removing the edge
	 */
	@Test
	public void test006_checkRemovingEdge() {
		directedGraph.addVertex("vertex1");
		directedGraph.addEdge("vertex1", "vertex2");
		directedGraph.removeEdge("vertex1","vertex2");
		assertEquals(2, directedGraph.order());
		assertEquals(0, directedGraph.size());
	}
}
