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
public class ConcreteEdgesGraph<L> implements Graph<L> {
    
    private final Set<L> vertices = new HashSet<>();
    private final List<Edge<L>> edges = new ArrayList<>();
    
    // Abstraction function:
    //   TODO
    // Representation invariant:
    //   TODO
    // Safety from rep exposure:
    //   TODO
    
    // constructor
    public ConcreteEdgesGraph() {
    }

    // TODO checkRep
    
    @Override public boolean add(L vertex) {
        return vertices.add(vertex);
    }
    
    @Override public int set(L source, L target, int weight) {
        if (!(vertices.contains(source) && vertices.contains(target))) {
            return 0;
        }
        Edge<L> edge = new Edge<>(source, target, weight);
        if (weight == 0) {
            int i = edges.indexOf(edge);
            int result = edges.get(i).getWeight();
            edges.remove(edge);
            return result;
        }

        if (edges.contains(edge)) {
            int i = 0;
            for (Edge<L> e : edges) {
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
    
    @Override public boolean remove(L vertex) {
        if (!vertices.contains(vertex)) {
            return false;
        }
        vertices.remove(vertex);
        edges.removeIf(e -> e.containsVertex(vertex));
        return true;
    }
    
    @Override public Set<L> vertices() {
        return Collections.unmodifiableSet(vertices);
    }
    
    @Override public Map<L, Integer> sources(L target) {
        Map<L, Integer> map = new HashMap<>();
        for (Edge<L> e : edges) {
            if (e.containsSource(target)) {
                map.put(e.getSource(), e.getWeight());
            }
        }
        return map;
    }
    
    @Override public Map<L, Integer> targets(L source) {
        Map<L, Integer> map = new HashMap<>();
        for (Edge<L> e : edges) {
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
        ConcreteEdgesGraph<L> that = (ConcreteEdgesGraph<L>) obj;
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
        for (L v : vertices) {
            sb.append(v);
            sb.append(" ");
        }
        sb.append("\n");
        String b = "There are " + this.edges.size() + " edge in this graph.Here are them:\n";
        sb.append(b);
        for (Edge<L> e : edges) {
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
class Edge<L> {
    // fields
    private final L source;
    private final L target;
    private final int weight;
    
    // Abstraction function:
    //   TODO
    // Representation invariant:
    //   TODO
    // Safety from rep exposure:
    //   TODO
    
    // constructor
    public Edge(L source, L target, int weight) {
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
        Edge<L> that = (Edge<L>) obj;
        return  this.source.equals(that.source)
                && this.target.equals(that.target);
    }
// TODO checkRep
    
    // methods
    public boolean containsVertex(L vertex) {
        return vertex.equals(this.source) || vertex.equals(this.target);
    }

    public boolean containsSource(L target) {
        return target.equals(this.target);
    }

    public boolean containsTarget(L source) {
        return source.equals(this.source);
    }

    public L getSource() {
        return source;
    }

    public L getTarget() {
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
