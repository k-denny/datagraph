package com.datagraph.core.engine.job;

import com.datagraph.common.api.Operator;
import dagger.Module;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Denny Joseph on 4/29/16.
 */

@Module
public class DAG<T extends Operator> {

    protected Map<T, Set<T>> graph;

    public DAG() {
        this.graph = new HashMap<T, Set<T>>();
    }

    public void reset() {
        this.graph = new HashMap<T, Set<T>>();
    }

    public void addNode(T task) {
        _addNode(task, this.graph);
    }

    private void _addNode(T task, Map<T, Set<T>> _graph) {
        if (!_graph.containsKey(task))
            _graph.put(task, new HashSet<T>());
    }

    public void addEdge(T src, T dest) {
        Map<T, Set<T>> graphCopy = new HashMap<>(this.graph);
        _addEdge(graphCopy, src, dest);
        _addEdge(this.graph, src, dest);

    }

    private void _addEdge(Map<T, Set<T>> _graph, T src, T dest) {
        _addNode(src, _graph);
        _addNode(dest, _graph);
        _graph.get(src).add(dest);
    }

    public Set<T>  getTop(Map<T, Set<T>> _graph) {
        Set<T> allDeps = _graph.entrySet().stream().map(e -> e.getValue()).
                        flatMap(edges -> edges.stream()).collect(Collectors.toSet());
        Set<T> topNodes = _graph.keySet().stream().filter(node -> !allDeps.contains(node))
                .collect(Collectors.toSet());
        return topNodes;

    }

    public Set<T> getEdges(T node) {

        System.out.println("Node : " + node.hashCode());
        return this.graph.get(node);
    }

    public Set<T> getDependencies(T dest) {
        Set<T> dependencies = new HashSet<>();
        for(T node : this.graph.keySet()) {
            Set<T> edges = this.graph.get(node);
            if(edges.contains(dest)) {
                dependencies.add(node);
            }
        }
        return dependencies;
    }

    public Set<T> getLeaves() {
        Set<T> allLeaves = this.graph.entrySet().stream().filter(e -> e.getValue().size()==0)
                .map(e->e.getKey()).collect(Collectors.toSet());
        return allLeaves;
    }

    public void remmoveNode(T node) {
        this.graph.remove(node);
        this.graph.values().stream().map(t -> t.remove(node));
    }

    public void topologicalSort(Map<T, Set<T>> _graph)  throws DAGException{
        Set<T> allTop = getTop(_graph);
        if(allTop.size() == 0 && _graph.size()>0) {
            throw new DAGException("Dag failed");
        }
        for(T node : allTop)
            _graph.remove(node);
        if(_graph.size() > 0)
            topologicalSort(_graph);

    }


    @Override
    public String toString() {
        return "DAG{" +
                "graph=" + graph +
                '}';
    }


}
