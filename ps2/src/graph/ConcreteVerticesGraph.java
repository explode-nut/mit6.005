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
public class ConcreteVerticesGraph implements Graph<String> {
    
    private final List<Vertex> vertices = new ArrayList<>();
    
    // Abstraction function:
    //   TODO
    // Representation invariant:
    //   TODO
    // Safety from rep exposure:
    //   TODO
    
    // constructor
    public ConcreteVerticesGraph() {}

    // TODO checkRep

    @Override public boolean add(String vertex) {
        for (Vertex v : vertices) {
            if (vertex.equals(v.getContent())) {
                return false;
            }
        }
        return vertices.add(new Vertex(vertex));
    }
    
    @Override public int set(String source, String target, int weight) {
        boolean first = false;
        boolean second = false;
        int i = 0;
        int p = -1;
        for (Vertex v : vertices) {
            if (source.equals(v.getContent())) {
                first = true;
                p = i;
            }
            if (target.equals(v.getContent())) {
                second = true;
            }
            if (first && second) {
                break;
            }
            i++;
        }

        if (first && second) {
            for (Vertex v : vertices) {
                if (p == 0) {
                    return v.setEdge(target, weight);
                }
                p--;
            }
        }
        return 0;
    }
    
    @Override public boolean remove(String vertex) {
        boolean result = false;
        int i = 0;
        int p = -1;
        for (Vertex v : vertices) {
            Map<String, Integer> targets = v.getTargets();
            if (vertex.equals(v.getContent())) {
                p = i;
            }
            if (targets.containsKey(vertex)) {
                int j = v.setEdge(vertex, 0);
                result = j != 0;
            }
            i++;
        }
        if (p != -1) {
            vertices.remove(p);
            result = true;
        }
        return result;
    }
    
    @Override public Set<String> vertices() {
        Set<String> result = new HashSet<>();
        for (Vertex v : vertices) {
            result.add(v.getContent());
        }
        return result;
    }
    
    @Override public Map<String, Integer> sources(String target) {
        Map<String, Integer> result = new HashMap<>();
        for (Vertex vertex : vertices) {
            Map<String, Integer> targets = vertex.getTargets();
            if (targets.containsKey(target)) {
                result.put(vertex.getContent(), targets.get(target));
            }
        }
        return result;
    }
    
    @Override public Map<String, Integer> targets(String source) {
        for (Vertex v : vertices) {
            if (source.equals(v.getContent())) {
                return v.getTargets();
            }
        }
        return Collections.emptyMap();
    }
    
    // toString()
    @Override
    public String toString() {
        int i = 0;
        StringBuilder sb = new StringBuilder();
        StringBuilder edgeSB = new StringBuilder();
        String vertex = "There are " + this.vertices.size() + " vertices in this graph:\n";
        sb.append(vertex);
        for (Vertex v : vertices) {
            i += v.getTargets().size();
            sb.append(v.getContent());
            sb.append(" ");
            edgeSB.append(v.edgesString());
        }
        sb.append("\n");
        String edge = "There are " + i + " edge in this graph:\n";
        sb.append(edge);
        sb.append(edgeSB);

        return sb.toString();
    }
}

/**
 * TODO specification
 * Mutable.
 * This class is internal to the rep of ConcreteVerticesGraph.
 * 
 * <p>PS2 instructions: the specification and implementation of this class is
 * up to you.
 */
class Vertex {
    // fields
    private final String content;
    private final Map<String, Integer> targets = new HashMap<>();;
    
    // Abstraction function:
    //   TODO
    // Representation invariant:
    //   TODO
    // Safety from rep exposure:
    //   TODO


    @Override
    public int hashCode() {
        return content.hashCode() + targets.hashCode() + targets.size();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        Vertex that = (Vertex) obj;
        return this.content.equals(that.content)
                && this.targets.equals(that.targets);
    }

    // constructor
    public Vertex(String content) {
        this.content = content;
    }


    // TODO checkRep
    
    // methods
    public String getContent() {
        return content;
    }

    public Map<String, Integer> getTargets() {
        return Collections.unmodifiableMap(targets);
    }

    public String edgesString() {
        StringBuilder sb = new StringBuilder();
        this.targets.forEach((k, v) -> {
            String e = this.content + "->" + k + " weight:" + v + "\n";
            sb.append(e);
        });
        return sb.toString();
    }

    public int setEdge(String t, int w) {
        int result = 0;
        if (this.targets.containsKey(t)) {
            result = this.targets.get(t);
        }
        if (w == 0) {
            this.targets.remove(t);
        } else {
            this.targets.put(t, w);
        }
        return result;
    }


    // toString()
    @Override
    public String toString() {
        return this.content +
                "\n" +
                "Here are " +
                this.content +
                "'s edges:\n" +
                this.edgesString();
    }
}
