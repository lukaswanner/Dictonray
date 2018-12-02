// O. Bittel;
// 22.02.2017

package Aufgabe2;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Klasse zur Erstellung einer topologischen Sortierung.
 *
 * @param <V> Knotentyp.
 * @author Oliver Bittel
 * @since 22.02.2017
 */
public class TopologicalSort<V> {
    private List<V> ts = new LinkedList<>(); // topologisch sortierte Folge
    // ...

    /**
     * Führt eine topologische Sortierung für g durch.
     *
     * @param g gerichteter Graph.
     */
    public TopologicalSort(DirectedGraph<V> g) {
        int[] inDegree = new int[g.getVertexSet().size() + 1];
        Queue<V> q = new LinkedList<>();
        for (V v : g.getVertexSet()) {
            inDegree[Integer.parseInt(v.toString())] = g.getInDegree(v);
            if (inDegree[Integer.parseInt(v.toString())] == 0) {
                q.add(v);
            }

        }

        while (!q.isEmpty()) {
            V v = q.remove();
            ts.add(v);
            for (V w : g.getSuccessorVertexSet(v)) {
                if (--inDegree[Integer.parseInt(w.toString())] == 0) {
                    q.add(w);
                }
            }
        }

        if (ts.size() != g.getVertexSet().size()) {
            ts.clear();
        }





    }

    /**
     * Liefert eine nicht modifizierbare Liste (unmodifiable view) zurück,
     * die topologisch sortiert ist.
     *
     * @return topologisch sortierte Liste
     */
    public List<V> topologicalSortedList() {
        return Collections.unmodifiableList(ts);
    }


    public static void main(String[] args) {
        DirectedGraph<Integer> g = new AdjacencyListDirectedGraph<>();
        g.addEdge(1, 2);
        g.addEdge(2, 3);
        g.addEdge(3, 4);
        g.addEdge(3, 5);
        g.addEdge(4, 6);
        g.addEdge(5, 6);
        g.addEdge(6, 7);
        System.out.println(g);

        TopologicalSort<Integer> ts = new TopologicalSort<>(g);

        if (ts.topologicalSortedList() != null) {
            System.out.println(ts.topologicalSortedList()); // [1, 2, 3, 4, 5, 6, 7]
        }
    }
}
