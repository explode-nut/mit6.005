/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import org.junit.Test;

/**
 * Tests for ConcreteVerticesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteVerticesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteVerticesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteVerticesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteVerticesGraph<>();
    }
    
    /*
     * Testing ConcreteVerticesGraph...
     */
    
    // Testing strategy for ConcreteVerticesGraph.toString()
    //   TODO
    
    // TODO tests for ConcreteVerticesGraph.toString()
    @Test
    public void testToString() {
        Graph<String> graph = emptyInstance();
        graph.add("a");
        graph.add("b");
        graph.add("c");
        graph.add("d");
        graph.add("e");
        graph.add("f");
        graph.remove("c");
        graph.set("a", "b", 2);
        graph.set("a", "d", 1);
        graph.set("d", "e", 3);
        graph.set("e", "f", 4);
        graph.set("b", "f", 7);
        graph.set("b", "e", 5);
        graph.set("a", "d", 0);
        graph.set("b", "e", 4);
        graph.remove("e");
        System.out.println(graph);
    }
    
    /*
     * Testing Vertex...
     */
    
    // Testing strategy for Vertex
    //   TODO
    
    // TODO tests for operations of Vertex
    @Test
    public void testInstance() {
        super.testSimpleAddAndRemoveVertexAndEdge();
        super.removeNoneExistVertexAndEdge();
        super.testAddExistsVertex();
        super.testAddExistsEdge();
        super.testSetReturn();
    }
}
