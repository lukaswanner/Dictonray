// O. Bittel;
// 05-09-2018

package Aufgabe2;

import Aufgabe1.Dictionary;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Klasse für Bestimmung aller strengen Komponenten.
 * Kosaraju-Sharir Algorithmus.
 *
 * @param <V> Knotentyp.
 * @author Oliver Bittel
 * @since 22.02.2017
 */
public class StrongComponents<V> {
    // comp speichert fuer jede Komponente die zughörigen Knoten.
    // Die Komponenten sind numeriert: 0, 1, 2, ...
    // Fuer Beispielgraph in Aufgabenblatt 2, Abb3:
    // Component 0: 5, 6, 7,
    // Component 1: 8,
    // Component 2: 1, 2, 3,
    // Component 3: 4,

    private final Map<Integer, Set<V>> comp = new TreeMap<>();
    private Set<V> visited = new HashSet<>();
    private Deque<V> stack = new ArrayDeque<>();

    /**
     * Ermittelt alle strengen Komponenten mit
     * dem Kosaraju-Sharir Algorithmus.
     *
     * @param g gerichteter Graph.
     */
    public StrongComponents(DirectedGraph<V> g) {
        for (V v : g.getVertexSet()) {
            if (!visited.contains(v))
                dfs(g, v);
        }

        g = g.invert();
        visited.clear();
        int i = 0;
        while (!stack.isEmpty()) {
            V v = stack.poll();
            if (!visited.contains(v)) {
                Set<V> set = new HashSet<>();
                dfsreverse(g, v, visited, set);
                comp.put(i++, set);
            }
        }

    }

    public void dfsreverse(DirectedGraph<V> g, V v, Set<V> visited, Set<V> set) {
        visited.add(v);
        set.add(v);
        for (V succ : g.getSuccessorVertexSet(v)) {
            if (!visited.contains(succ))
                dfsreverse(g, succ, visited, set);
        }

    }

    public void dfs(DirectedGraph<V> g, V v) {

        visited.add(v);

        for (V succ : g.getSuccessorVertexSet(v)) {
            if (!visited.contains(succ))
                dfs(g, succ);
        }
        stack.offerFirst(v);

    }

    /**
     * @return Anzahl der strengen Komponeneten.
     */
    public int numberOfComp() {
        return comp.size();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (Map.Entry<Integer, Set<V>> entry : comp.entrySet()) {
            result.append("Component " + entry.getKey() + " :" + entry.getValue().toString() + " \n");
        }
        return result.toString();
    }

    /**
     * Liest einen gerichteten Graphen von einer Datei ein.
     *
     * @param fn Dateiname.
     * @return gerichteter Graph.
     * @throws FileNotFoundException
     */
    public static DirectedGraph<Integer> readDirectedGraph(File fn) throws FileNotFoundException {
        DirectedGraph<Integer> g = new AdjacencyListDirectedGraph<>();
        Scanner sc = new Scanner(fn);
        sc.nextInt();
        sc.nextInt();
        while (sc.hasNextInt()) {
            int v = sc.nextInt();
            int w = sc.nextInt();
            g.addEdge(v, w);
        }
        return g;
    }

    private static void test1() {
        DirectedGraph<String> gg = new AdjacencyListDirectedGraph<>();

        gg.addEdge("A", "B");
        gg.addEdge("B", "C");
        gg.addEdge("C", "A");

        gg.addEdge("B", "D");

        gg.addEdge("D", "E");
        gg.addEdge("E", "F");
        gg.addEdge("F", "D");

        gg.addEdge("G", "F");

        gg.addEdge("G", "H");
        gg.addEdge("H", "I");
        gg.addEdge("I", "J");
        gg.addEdge("J", "G");

        gg.addEdge("J", "K");

        StrongComponents<String> scc = new StrongComponents<>(gg);

        System.out.println(scc);

        System.out.println("----------------------------------------");

        DirectedGraph<Integer> g = new AdjacencyListDirectedGraph<>();
        g.addEdge(1, 2);
        g.addEdge(1, 3);
        g.addEdge(2, 1);
        g.addEdge(2, 3);
        g.addEdge(3, 1);

        g.addEdge(1, 4);
        g.addEdge(5, 4);

        g.addEdge(5, 7);
        g.addEdge(6, 5);
        g.addEdge(7, 6);

        g.addEdge(7, 8);
        g.addEdge(8, 2);

        StrongComponents<Integer> sc = new StrongComponents<>(g);


        System.out.println(sc.numberOfComp());  // 4

        System.out.println(sc);
        // Component 0: 5, 6, 7,
        // Component 1: 8,
        // Component 2: 1, 2, 3,
        // Component 3: 4,
    }

    private static void test2() throws FileNotFoundException {
        DirectedGraph<Integer> g = readDirectedGraph(new File("/home/student/IdeaProjects/WS18-19/src/Aufgabe2/mediumDG.txt"));
        System.out.println(g.getNumberOfVertexes());
        System.out.println(g.getNumberOfEdges());
        System.out.println(g);

        System.out.println("");

        StrongComponents<Integer> sc = new StrongComponents<>(g);
        System.out.println(sc.numberOfComp());  // 10
        System.out.println(sc);

    }

    public static void main(String[] args) throws FileNotFoundException {
        test1();
        System.out.println("-----------------------Test2--------------------------");
        test2();
    }
}
