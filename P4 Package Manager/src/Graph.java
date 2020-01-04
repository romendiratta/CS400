import java.util.*;


/**
 * Filename:   Graph.java
 * Project:    P4 Package Manager
 * Authors:    Rohan Mendiratta
 * Email:      rmendiratta@wisc.edu
 *
 * Directed and unweighted graph implementation
 */

public class Graph implements GraphADT {


	HashMap<String, HashSet<String>> directedGraph; // digraph reference
	int numVertices; // number of vertices
	int numEdges; // number of edges


	/*
	 * Default no-argument constructor
	 */
	public Graph() {
		directedGraph = new HashMap<>();
		numVertices = 0;
		numEdges = 0;
	}


	/**
	 * Add new vertex to the graph.
	 *
	 * If vertex is null or already exists,
	 * method ends without adding a vertex or
	 * throwing an exception.
	 *
	 * Valid argument conditions:
	 * 1. vertex is non-null
	 * 2. vertex is not already in the graph
	 */
	public void addVertex(String vertex) {
		// checks for null or duplicate vertex
		if (vertex == null || directedGraph.containsKey(vertex)){
			return;
		}

		// add new vertex
		directedGraph.put(vertex, new HashSet<>());
		numVertices++;
	}



	/**
	 * Remove a vertex and all associated
	 * edges from the graph.
	 *
	 * If vertex is null or does not exist,
	 * method ends without removing a vertex, edges,
	 * or throwing an exception.
	 *
	 * Valid argument conditions:
	 * 1. vertex is non-null
	 * 2. vertex is not already in the graph
	 */
	public void removeVertex(String vertex) {
		// checks for null vertex and that it exists in the graph
		if(vertex == null || !directedGraph.containsKey(vertex)) {
			return;
		}

		// remove the vertex
		directedGraph.remove(vertex);
		numVertices--;

		// remove any edges containing the vertex from the adjacent lists
		Set vertices = directedGraph.keySet();
		Iterator iterator = vertices.iterator();
		while(iterator.hasNext()) {
			String currentVertex = (String) iterator.next();
			if(directedGraph.get(currentVertex).contains(vertex)) {
				directedGraph.get(currentVertex).remove(vertex);
				numEdges--;
			}
		}
	}

	/**
	 * Add the edge from vertex1 to vertex2
	 * to this graph.  (edge is directed and unweighted)
	 * If either vertex does not exist,
	 * add vertex, and add edge, no exception is thrown.
	 * If the edge exists in the graph,
	 * no edge is added and no exception is thrown.
	 *
	 * Valid argument conditions:
	 * 1. neither vertex is null
	 * 2. both vertices are in the graph
	 * 3. the edge is not in the graph
	 */
	public void addEdge(String vertex1, String vertex2) {
		// check for null vertices
		if(vertex1 == null || vertex2 == null) {
			return;
		}
		// check that that both edges exist in the graph
		if(!directedGraph.containsKey(vertex1) && !directedGraph.containsKey(vertex2)){
			return;
		}

		// checks for duplicate edge
		if(directedGraph.containsKey(vertex1)) {
			if(directedGraph.get(vertex1).contains(vertex2)){
				return;
			}
		}

		// if both vertices exists, add an edge from v1 to v2
		if(directedGraph.containsKey(vertex1) && directedGraph.containsKey(vertex2)){
			directedGraph.get(vertex1).add(vertex2);
			numEdges++;
		}

		// if vertex1 exists, add an edge from v1 to v2, and  add v2 to the vertices
		if(directedGraph.containsKey(vertex1) && !directedGraph.containsKey(vertex2)) {
			directedGraph.get(vertex1).add(vertex2);
			numEdges++;
			directedGraph.put(vertex2, new HashSet<>());
			numVertices++;
		}

		// if vertex2 exists, add v1 to the vertices, add an edge from v1 to v2
		if(!directedGraph.containsKey(vertex1) && directedGraph.containsKey(vertex2)) {
			directedGraph.put(vertex1, new HashSet<>());
			numVertices++;
			directedGraph.get(vertex1).add(vertex2);
			numEdges++;
		}


	}

	/**
	 * Remove the edge from vertex1 to vertex2
	 * from this graph.  (edge is directed and unweighted)
	 * If either vertex does not exist,
	 * or if an edge from vertex1 to vertex2 does not exist,
	 * no edge is removed and no exception is thrown.
	 *
	 * Valid argument conditions:
	 * 1. neither vertex is null
	 * 2. both vertices are in the graph
	 * 3. the edge from vertex1 to vertex2 is in the graph
	 */
	public void removeEdge(String vertex1, String vertex2) {
		// check for null vertices
		if (vertex1 == null || vertex2 == null){
			return;
		}

		// checks that vertex1 is in the graph
		if(!directedGraph.containsKey(vertex1)) {
			return;
		}

		// removes edge if it exists
		if(directedGraph.get(vertex1).contains(vertex2)){
			directedGraph.get(vertex1).remove(vertex2);
			numEdges--;
		}

	}

	/**
	 * Returns a Set that contains all the vertices
	 *
	 */
	public Set<String> getAllVertices() {
		return directedGraph.keySet();
	}

	/**
	 * Get all the neighbor (adjacent) vertices of a vertex
	 *
	 */
	public List<String> getAdjacentVerticesOf(String vertex) {
		Set set = directedGraph.get(vertex);
		return new ArrayList<>(set);
	}

	/**
	 * Returns the number of edges in this graph.
	 */
	public int size() {
		return numEdges;
	}

	/**
	 * Returns the number of vertices in this graph.
	 */
	public int order() {
		return numVertices;
	}
}
