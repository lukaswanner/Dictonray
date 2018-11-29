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
    }

    /**
     * Liefert eine nicht modifizierbare Liste (unmodifiable view) mit einer
     * Pre-Order-Reihenfolge zurück.
     *
     * @return Pre-Order-Reihenfolge der Tiefensuche.
     */
    public List<V> preOrder() {

        for (V v : myGraph.getVertexSet()){
            visitDF(v,myGraph);
        }

        return Collections.unmodifiableList(preOrder);
    }

    private void visitDF(V v, DirectedGraph g) {

        if(!preOrder.contains(v))
            preOrder.add(v);

        for (Object o : g.getSuccessorVertexSet(v)) {
            V w = (V) o;
            if (!preOrder.contains(w)){
                visitDF(w,g);
            }
        }
    }


    private void visitPost(V v, DirectedGraph g) {

        for (Object o : g.getSuccessorVertexSet(v)) {
            V w = (V) o;
            if (!preOrder.contains(w)){
                visitDF(w,g);
                preOrder.add(v);
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

        for (V v : myGraph.getVertexSet()){
            visitPost(v,myGraph);
        }

        return Collections.unmodifiableList(postOrder);
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


        //System.out.println(dfs.numberOfDFTrees());    // 2
        System.out.println(dfs.preOrder());        // [1, 2, 5, 6, 3, 7, 4]
        //  System.out.println(g.getVertexSet());
        System.out.println(dfs.postOrder());        // [5, 6, 2, 1, 4, 7, 3]


    }
}
