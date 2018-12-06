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
        Map<V,Integer> inDegree = new TreeMap<>();

        for (V v : g.getVertexSet()) {
            inDegree.put(v,g.getInDegree(v));
                if(inDegree.get(v)==0)
                    q.offerFirst(v);
        }

        while (!q.isEmpty()) {
            V v = q.remove();
            ts.add(v);
            for (V w : g.getSuccessorVertexSet(v)){
                inDegree.put(w,(inDegree.get(w)-1));
                if(inDegree.get(w)==0)
                    q.offerFirst(w);
            }
        }

        if(ts.size() != g.getVertexSet().size()){
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
        DirectedGraph<String> g = new AdjacencyListDirectedGraph<>();
        g.addEdge("Unterhose", "Hose");
        g.addEdge("Unterhemd", "Hemd");
        g.addEdge("Socken", "Schuhe");
        g.addEdge("Hose", "Schuhe");
        g.addEdge("Hose", "Gürtel");
        g.addEdge("Hemd", "Pulli");
        g.addEdge("Gürtel", "Mantel");
        g.addEdge("Pulli", "Mantel");
        g.addEdge("Schuhe", "Handschuhe");
        g.addEdge("Mantel", "Schal");
        g.addEdge("Mütze", "Handschuhe");
        g.addEdge("Schal", "Handschuhe");
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
