/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 * 
 * <p>PS2 instructions: you MUST NOT add constructors, fields, or non-@Test
 * methods to this class, or change the spec of {@link #emptyInstance()}.
 * Your tests MUST only obtain Graph instances by calling emptyInstance().
 * Your tests MUST NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {
    
    // Testing strategy
    //   TODO

    
    /**
     * Overridden by implementation-specific test classes.
     * 
     * @return a new empty graph of the particular implementation being tested
     */
    public abstract Graph<String> emptyInstance();
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testInitialVerticesEmpty() {
        // TODO you may use, change, or remove this test
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), emptyInstance().vertices());
    }
    
    // TODO other tests for instance methods of Graph
    @Test
    public void testSimpleAddAndRemoveVertexAndEdge() {
        Graph<String> graph = emptyInstance();
        graph.add("a");
        graph.add("b");
        graph.add("c");
        graph.add("d");
        graph.add("e");
        graph.add("f");
        graph.remove("c");
        assertEquals("expected 5 vertex but got " + graph.vertices().size(), 5, graph.vertices().size());
        graph.set("a", "b", 2);
        graph.set("a", "d", 1);
        graph.set("d", "e", 3);
        graph.set("e", "f", 4);
        graph.set("b", "f", 7);
        graph.set("b", "e", 5);
        graph.set("a", "d", 0);
        assertEquals("expected 2 vertex but got " + graph.targets("b").size(), 2, graph.targets("b").size());
        assertEquals("expected 0 vertex but got " + graph.targets("f").size(), 0, graph.targets("f").size());
        assertEquals("expected 2 vertex but got " + graph.sources("f").size(), 2, graph.sources("f").size());
        assertEquals("expected 0 vertex but got " + graph.sources("a").size(), 0, graph.sources("a").size());
        graph.remove("e");
        assertEquals("expected 4 vertex but got " + graph.vertices().size(), 4, graph.vertices().size());
        assertEquals("expected 1 vertex but got " + graph.targets("b").size(), 1, graph.targets("b").size());
        assertTrue("expected true but got " + graph.targets("b").containsKey("f"), graph.targets("b").containsKey("f"));
        assertEquals("expected 1 vertex but got " + graph.sources("f").size(), 1, graph.sources("f").size());
        assertTrue("expected true but got " + graph.sources("f").containsKey("b"), graph.sources("f").containsKey("b"));
    }

    @Test
    public void removeNoneExistVertexAndEdge() {
        Graph<String> g = emptyInstance();
        g.add("a");
        g.add("b");
        g.add("c");
        boolean flag = g.remove("d");
        assertFalse("expected false because d is not exist", flag);
        g.set("a", "b", 2);
        g.set("a", "c", 3);
        int a = g.set("a", "d", 34);
        assertEquals("expected 0 because there was no such edge but got " + a, 0, a);

    }
}
