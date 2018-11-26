// O. Bittel;
// 19.03.2018

package Aufgabe2;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.Set;

/**
 * Implementierung von DirectedGraph mit einer doppelten TreeMap
 * für die Nachfolgerknoten und einer einer doppelten TreeMap
 * für die Vorgängerknoten.
 * <p>
 * Beachte: V muss vom Typ Comparable&lt;V&gt; sein.
 * <p>
 * Entspicht einer Adjazenzlisten-Implementierung
 * mit schnellem Zugriff auf die Knoten.
 *
 * @param <V> Knotentyp.
 * @author Oliver Bittel
 * @since 19.03.2018
 */
public class AdjacencyListDirectedGraph<V> implements DirectedGraph<V> {

    // doppelte Map für die Nachfolgerknoten:
    private final Map<V, Map<V, Double>> succ = new TreeMap<>();

    // doppelte Map für die Vorgängerknoten:
    private final Map<V, Map<V, Double>> pred = new TreeMap<>();

    private int numberEdge = 0;

    @Override
    public boolean addVertex(V v) {
        if (!succ.containsKey(v)) {
            succ.put(v, new TreeMap<>());
            pred.put(v, new TreeMap<>());
            return true;
        }
        return false;
    }

    @Override
    public boolean addEdge(V v, V w, double weight) {
        if (succ.containsKey(v) == false) //Falls die Knoten noch nicht da sind erstellen wir sie erst
            addVertex(v);
        if (succ.containsKey(w) == false)
            addVertex(w);

        if (!succ.get(v).containsKey(w)) {
            succ.get(v).put(w, weight);
            pred.get(w).put(v, weight);
            numberEdge++;
            return true;
        } else {
            succ.get(v).replace(w, weight);
            pred.get(w).replace(v, weight);
            return false;
        }
    }

    @Override
    public boolean addEdge(V v, V w) {
        if (succ.containsKey(v) == false) //Falls die Knoten noch nicht da sind erstellen wir sie erst
            addVertex(v);
        if (succ.containsKey(w) == false)
            addVertex(w);

        if (!succ.get(v).containsKey(w)) { //Dann wird geprüft ob es die Verbindung schon gibt
            succ.get(v).put(w, 1.0);
            pred.get(w).put(v, 1.0);
            numberEdge++;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean containsVertex(V v) {
        if (succ.containsKey(v)) //Falls vorhanden
            return true;
        return false;
    }

    @Override
    public boolean containsEdge(V v, V w) {
        if (succ.get(v).containsKey(w))
            return true;
        return false;
    }

    @Override
    public double getWeight(V v, V w) {
        if (succ.get(v).containsKey(w))
            return succ.get(v).get(w);
        return 0;
    }

    //TODO in und out nachfragen ob richtig
    @Override
    public int getInDegree(V v) {
        if (pred.containsKey(v))
            return pred.get(v).size();
        throw new IllegalArgumentException("Nicht vorhanden");
    }

    @Override
    public int getOutDegree(V v) {
        if (succ.containsKey(v))
            return succ.get(v).size();
        throw new IllegalArgumentException("Nicht vorhanden");
    }

    @Override
    public Set<V> getVertexSet() {
        return Collections.unmodifiableSet(succ.keySet()); // nicht modifizierbare Sicht
    }

    @Override
    public Set<V> getPredecessorVertexSet(V v) {
        if (pred.containsKey(v))
            return pred.get(v).keySet();
        throw new IllegalArgumentException("Nicht vorhanden");
    }

    @Override
    public Set<V> getSuccessorVertexSet(V v) {
        if (succ.containsKey(v))
            return succ.get(v).keySet();
        throw new IllegalArgumentException("Knoten nicht vorhanden");
    }

    @Override
    public int getNumberOfVertexes() {
        return succ.size();
    }

    @Override
    public int getNumberOfEdges() {
        return numberEdge;
    }

    @Override
    public DirectedGraph<V> invert() {
        DirectedGraph DG = new AdjacencyListDirectedGraph();
        for (Map.Entry<V, Map<V, Double>> entry : succ.entrySet()) {
            DG.addVertex(entry.getKey());
            for (Map.Entry<V, Double> entry1 : entry.getValue().entrySet()) {
                DG.addEdge(entry1.getKey(), entry.getKey());
            }
        }
        DG.toString();
        return DG;
    }


    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (Map.Entry<V, Map<V, Double>> entry : succ.entrySet()) {
            for (Map.Entry<V, Double> entry1 : entry.getValue().entrySet())
                result.append(entry.getKey() + " - - - > " + entry1.getKey() + " weight = " + entry1.getValue() + "\n");
        }
        return result.toString();
    }


    public static void main(String[] args) {
        DirectedGraph<Integer> g = new AdjacencyListDirectedGraph<>();
        g.addEdge(1, 2);
        g.addEdge(2, 5);
        g.addEdge(5, 1);
        g.addEdge(2, 6);
        g.addEdge(3, 7);
        g.addEdge(4, 3);
        g.addEdge(4, 6);
        g.addEdge(7, 4);


        System.out.println(g.getNumberOfVertexes());    // 7
        System.out.println(g.getNumberOfEdges());        // 8
        System.out.println(g.getVertexSet());    // 1, 2, ..., 7
        System.out.println(g);
        // 1 --> 2 weight = 1.0
        // 2 --> 5 weight = 1.0
        // 2 --> 6 weight = 1.0
        // 3 --> 7 weight = 1.0
        // ...


        System.out.println("");
        System.out.println(g.getOutDegree(2));                // 2
        System.out.println(g.getSuccessorVertexSet(2));    // 5, 6
        System.out.println(g.getInDegree(6));                // 2
        System.out.println(g.getPredecessorVertexSet(6));    // 2, 4

        System.out.println("");
        System.out.println(g.containsEdge(1, 2));    // true
        System.out.println(g.containsEdge(2, 1));    // false
        System.out.println(g.getWeight(1, 2));    // 1.0
        g.addEdge(1, 2, 5.0);
        System.out.println(g.getWeight(1, 2));    // 5.0


        System.out.println("");
        System.out.println(g.invert());
        // 1 --> 5 weight = 1.0
        // 2 --> 1 weight = 1.0
        // 3 --> 4 weight = 1.0
        // 4 --> 7 weight = 1.0
        // ...

        Set<Integer> s = g.getSuccessorVertexSet(2);
        System.out.println(s);

        s.remove(5);    // Laufzeitfehler! Warum?

    }
}
