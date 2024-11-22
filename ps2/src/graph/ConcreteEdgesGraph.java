/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import java.util.*;

/**
 * An implementation of Graph.
 * 
 * <p>PS2 instructions: you MUST use the provided rep.
 */
public class ConcreteEdgesGraph implements Graph<String> {
    
    private final Set<String> vertices = new HashSet<>();
    private final List<Edge> edges = new ArrayList<>();
    
    // Abstraction function:
    //   TODO
    // Representation invariant:
    //   TODO
    // Safety from rep exposure:
    //   TODO
    
    // constructor
    public ConcreteEdgesGraph() {}

    // TODO checkRep
    
    @Override public boolean add(String vertex) {
        return vertices.add(vertex);
    }
    
    @Override public int set(String source, String target, int weight) {
        if (!(vertices.contains(source) && vertices.contains(target))) {
            return 0;
        }
        Edge edge = new Edge(source, target, weight);
        if (weight == 0) {
            int i = edges.indexOf(edge);
            int result = edges.get(i).getWeight();
            edges.remove(edge);
            return result;
        }

        if (edges.contains(edge)) {
            int i = 0;
            for (Edge e : edges) {
                if (e.equals(edge)) {
                    break;
                }
                i++;
            }
            if (edges.get(i).getWeight() == weight) {
                return weight;
            } else {
                int result = edges.get(i).getWeight();
                edges.set(i, edge);
                return result;
            }
        }
        edges.add(edge);
        return 0;
    }
    
    @Override public boolean remove(String vertex) {
        if (!vertices.contains(vertex)) {
            return false;
        }
        vertices.remove(vertex);
        edges.removeIf(e -> e.containsVertex(vertex));
        return true;
    }
    
    @Override public Set<String> vertices() {
        return Collections.unmodifiableSet(vertices);
    }
    
    @Override public Map<String, Integer> sources(String target) {
        Map<String, Integer> map = new HashMap<>();
        for (Edge e : edges) {
            if (e.containsSource(target)) {
                map.put(e.getSource(), e.getWeight());
            }
        }
        return map;
    }
    
    @Override public Map<String, Integer> targets(String source) {
        Map<String, Integer> map = new HashMap<>();
        for (Edge e : edges) {
            if (e.containsTarget(source)) {
                map.put(e.getTarget(), e.getWeight());
            }
        }
        return map;
    }

    @Override
    public int hashCode() {
        return vertices.hashCode() + edges.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        ConcreteEdgesGraph that = (ConcreteEdgesGraph) obj;
        return this.vertices.hashCode() == that.vertices.hashCode()
                && this.edges.hashCode() == that.edges.hashCode()
                && this.vertices.size() == that.vertices.size()
                && this.edges.size() == that.edges.size();
    }

    // toString()
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String a = "There are " + this.vertices.size() + " vertex in this graph.Here are them:\n";
        sb.append(a);
        for (String v : vertices) {
            sb.append(v);
            sb.append(" ");
        }
        sb.append("\n");
        String b = "There are " + this.edges.size() + " edge in this graph.Here are them:\n";
        sb.append(b);
        for (Edge e : edges) {
            sb.append(e.toString());
        }
        return sb.toString();
    }
}

/**
 * TODO specification
 * Immutable.
 * This class is internal to the rep of ConcreteEdgesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Edge {
    // fields
    private final String source;
    private final String target;
    private final int weight;
    
    // Abstraction function:
    //   TODO
    // Representation invariant:
    //   TODO
    // Safety from rep exposure:
    //   TODO
    
    // constructor
    public Edge(String source, String target, int weight) {
        this.source = source;
        this.target = target;
        this.weight = weight;
    }

    @Override
    public int hashCode() {
        return source.hashCode() + target.hashCode() + weight;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        Edge that = (Edge) obj;
        return  this.source.equals(that.source)
                && this.target.equals(that.target);
    }
// TODO checkRep
    
    // methods
    public boolean containsVertex(String vertex) {
        return vertex.equals(this.source) || vertex.equals(this.target);
    }

    public boolean containsSource(String target) {
        return target.equals(this.target);
    }

    public boolean containsTarget(String source) {
        return source.equals(this.source);
    }

    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }

    public int getWeight() {
        return weight;
    }

    // toString()
    @Override
    public String toString() {
        return source + "->" + target + " weight:" + weight + "\n";
    }
}
