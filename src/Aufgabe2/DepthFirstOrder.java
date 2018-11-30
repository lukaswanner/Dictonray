// O. Bittel;
// 22.02.2017
package Aufgabe2;

import java.util.*;

/**
 * Klasse für Tiefensuche.
 *
 * @param <V> Knotentyp.
 * @author Oliver Bittel
 * @since 22.02.2017
 */
public class DepthFirstOrder<V> {

    private List<V> preOrder = new LinkedList<>();
    private final List<V> postOrder = new LinkedList<>();
    private final DirectedGraph<V> myGraph;
    private List<V> besucht = new LinkedList<>();
    private int numberOfDFTrees = 0;
    private V root;
    // ...

    /**
     * Führt eine Tiefensuche für g durch.
     *
     * @param g gerichteter Graph.
     */
    public DepthFirstOrder(DirectedGraph<V> g) {
        myGraph = g;
        for (V v : myGraph.getVertexSet()) {
            if (!besucht.contains(v)) {
                visitDF(v, myGraph);
                numberOfDFTrees++;
            }
        }
    }

    /**
     * Liefert eine nicht modifizierbare Liste (unmodifiable view) mit einer
     * Pre-Order-Reihenfolge zurück.
     *
     * @return Pre-Order-Reihenfolge der Tiefensuche.
     */
    public List<V> preOrder() {
        besucht.clear();
        preOrder.clear();
        for (V v : myGraph.getVertexSet()) {
            if (!besucht.contains(v)) {
                visitDF(v, myGraph);
                numberOfDFTrees++;
            }
        }

        return Collections.unmodifiableList(preOrder);
    }

    private void visitDF(V v, DirectedGraph<V> g) {
        if (!preOrder.contains(v))
            preOrder.add(v);
        besucht.add(v);
        for (V succ : g.getSuccessorVertexSet(v)) {
            if (!besucht.contains(succ)) {
                visitDF(succ, g);
            }
        }
    }


    /**
     * Liefert eine nicht modifizierbare Liste (unmodifiable view) mit einer
     * Post-Order-Reihenfolge zurück.
     *
     * @return Post-Order-Reihenfolge der Tiefensuche.
     */
    public List<V> postOrder() {
        besucht.clear();
        postOrder.clear();
        for (V v : myGraph.getVertexSet()) {
            visitPost(v, myGraph);

        }

        return Collections.unmodifiableList(postOrder);
    }

    private void visitPost(V v, DirectedGraph<V> g) {
        besucht.add(v);
        for (V succ : g.getSuccessorVertexSet(v)) {
            if (!besucht.contains(succ)) {
                visitPost(succ, g);
            }
        }
        if (!postOrder.contains(v))
            postOrder.add(v);
    }

    /**
     * @return Anzahl der Bäume des Tiefensuchwalds.
     */
    public int numberOfDFTrees() {
        return numberOfDFTrees;
    }

    public static void main(String[] args) {
        DirectedGraph<Integer> g = new AdjacencyListDirectedGraph<>();
        g.addEdge(1, 2);        //1 ------> 2
        g.addEdge(2, 5);        //2 ------> 5
        g.addEdge(5, 1);        //5 ------> 1
        g.addEdge(2, 6);        //2 ------> 6
        g.addEdge(3, 7);        //3 ------> 7
        g.addEdge(4, 3);        //4 ------> 3
        g.addEdge(4, 6);        //4 ------> 6
        //g.addEdge(7,3);
        g.addEdge(7, 4);        //7 ------> 4


        DepthFirstOrder<Integer> dfs = new DepthFirstOrder<>(g);

        System.out.println(dfs.numberOfDFTrees());    // 2
        System.out.println(dfs.preOrder());        // [1, 2, 5, 6, 3, 7, 4]
        System.out.println(dfs.postOrder());        // [5, 6, 2, 1, 4, 7, 3]

    }
}
