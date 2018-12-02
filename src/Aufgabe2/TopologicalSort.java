// O. Bittel;
// 22.02.2017

package Aufgabe2;

import java.util.*;

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
        Deque<V> q = new LinkedList<>();
        Set<V> visited = new HashSet<>();

        for (V v : g.getVertexSet()) {
            if (visited.contains(v))
                continue;
            topUtil(q, visited, v, g);
        }
        while (!q.isEmpty()) {
            ts.add(q.remove());
        }

    }

    public void topUtil(Deque<V> q, Set<V> visited, V v, DirectedGraph<V> g) {
        visited.add(v);

        for (V w : g.getSuccessorVertexSet(v)) {
            if (visited.contains(w))
                continue;
            topUtil(q, visited, w, g);
        }
        q.offerFirst(v);
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
        DirectedGraph<String> g = new AdjacencyListDirectedGraph<>();
        g.addEdge("A", "C");
        g.addEdge("B", "C");
        g.addEdge("C", "E");
        g.addEdge("E", "F");
        g.addEdge("B", "D");
        g.addEdge("F", "G");
        g.addEdge("G", "H");
        System.out.println(g);

        TopologicalSort<String> ts = new TopologicalSort<>(g);

        if (ts.topologicalSortedList() != null) {
            System.out.println(ts.topologicalSortedList()); // [1, 2, 3, 4, 5, 6, 7]
        }

        System.out.println("--------------------------------------------");

        DirectedGraph<Integer> gg = new AdjacencyListDirectedGraph<>();
        gg.addEdge(1, 2);
        gg.addEdge(2, 3);
        gg.addEdge(3, 4);
        gg.addEdge(3, 5);
        gg.addEdge(4, 6);
        gg.addEdge(5, 6);
        gg.addEdge(6, 7);
        System.out.println(gg);

        TopologicalSort<Integer> tss = new TopologicalSort<>(gg);

        if (tss.topologicalSortedList() != null) {
            System.out.println(tss.topologicalSortedList()); // [1, 2, 3, 4, 5, 6, 7]
        }
    }
}
