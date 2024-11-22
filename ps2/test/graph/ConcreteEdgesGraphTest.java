/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Tests for ConcreteEdgesGraph.
 * 
 * This class runs the GraphInstanceTest tests against ConcreteEdgesGraph, as
 * well as tests for that particular implementation.
 * 
 * Tests against the Graph spec should be in GraphInstanceTest.
 */
public class ConcreteEdgesGraphTest extends GraphInstanceTest {
    
    /*
     * Provide a ConcreteEdgesGraph for tests in GraphInstanceTest.
     */
    @Override public Graph<String> emptyInstance() {
        return new ConcreteEdgesGraph();
    }
    
    /*
     * Testing ConcreteEdgesGraph...
     */
    
    // Testing strategy for ConcreteEdgesGraph.toString()
    //   TODO
    
    // TODO tests for ConcreteEdgesGraph.toString()
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
     * Testing Edge...
     */
    
    // Testing strategy for Edge
    //   TODO
    
    // tests for operations of Edge
    @Test
    public void testInstance() {
        super.testSimpleAddAndRemoveVertexAndEdge();
        super.removeNoneExistVertexAndEdge();
        super.testAddExistsVertex();
        super.testAddExistsEdge();
        super.testSetReturn();
    }
}
