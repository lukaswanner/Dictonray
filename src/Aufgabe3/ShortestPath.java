// O. Bittel;
// 18.10.2011

package Aufgabe3;


import Aufgabe2.*;
import Aufgabe3.sim.*;

import java.util.*;
// ...

/**
 * Kürzeste Wege in Graphen mit A*- und Dijkstra-Verfahren.
 *
 * @param <V> Knotentyp.
 * @author Oliver Bittel
 * @since 27.01.2015
 */
public class ShortestPath<V> {

    SYSimulation sim = null;

    Map<V, Double> dist = new HashMap<>(); // Distanz für jeden Knoten
    Map<V, V> pred = new HashMap<>(); // Vorgänger für jeden Knoten
    V start;
    V end;
    DirectedGraph<V> dg = null;
    Heuristic<V> h = null;

    /**
     * Berechnet im Graph g kürzeste Wege nach dem A*-Verfahren.
     * Die Heuristik h schätzt die Kosten zwischen zwei Knoten ab.
     * Wird h = null gewählt, dann ist das Verfahren identisch mit dem Dijkstra-Verfahren.
     *
     * @param g Gerichteter Graph
     * @param h Heuristik. Falls h == null, werden kürzeste Wege nach
     *          dem Dijkstra-Verfahren gesucht.
     */
    public ShortestPath(DirectedGraph<V> g, Heuristic<V> h) {
        this.dg = g;
        this.h = h;
    }

    /**
     * Diese Methode sollte nur verwendet werden,
     * wenn kürzeste Wege in Scotland-Yard-Plan gesucht werden.
     * Es ist dann ein Objekt für die Scotland-Yard-Simulation zu übergeben.
     * <p>
     * Ein typische Aufruf für ein SYSimulation-Objekt sim sieht wie folgt aus:
     * <p><blockquote><pre>
     *    if (sim != null)
     *       sim.visitStation((Integer) v, Color.blue);
     * </pre></blockquote>
     *
     * @param sim SYSimulation-Objekt.
     */
    public void setSimulator(SYSimulation sim) {
        this.sim = sim;
    }

    /**
     * Sucht den kürzesten Weg von Starknoten s zum Zielknoten g.
     * <p>
     * Falls die Simulation mit setSimulator(sim) aktiviert wurde, wird der Knoten,
     * der als nächstes aus der Kandidatenliste besucht wird, animiert.
     *
     * @param s Startknoten
     * @param g Zielknoten
     */
    public void searchShortestPath(V s, V g) {
        start = s;
        end = g;
        for (V v : dg.getVertexSet()) {
            dist.put(v, Double.POSITIVE_INFINITY);
            pred.put(v,null);
        }

        LinkedList<V> kl = new LinkedList<>();

        dist.replace(s, 0.0);
        kl.add(s);

        while (!kl.isEmpty()) {
            V v = null;
            double vdistance = Double.MAX_VALUE;
            for (V selected : kl) {
                if (h == null) {
                    if (dist.get(selected) < vdistance) {
                        v = selected;
                        vdistance = dist.get(v);
                    }
                } else {
                    //OUTPUT
                    System.out.println("selected ist :" + selected.toString() +" und die Distanz ist :" + dist.get(selected)+ " und die Heuristic ist :" + h.estimatedCost(selected,g));


                    if (dist.get(selected) + h.estimatedCost(selected, g) < vdistance) {
                        v = selected;
                        vdistance = dist.get(selected) + h.estimatedCost(selected, g);
                    }
                }
            }

            //OUTPUT
            if(h != null)
                System.out.println("V ist: " + v.toString() +  " und die Distanz von v ist :" + dist.get(v)+ " und die Heuristic ist :" + h.estimatedCost(v,g));

            //OUTPUT
            if (dg.getVertexSet().size() < 20)
                System.out.println("Besuche Knoten " + v.toString() + " mit d = " + dist.get(v));


            kl.remove(v);


            if (h != null && v.equals(g)) {
                dist.replace(g, dist.get(v));
                return;
            }

            for (V w : dg.getSuccessorVertexSet(v)) {
                if (!kl.contains(w) && Double.isInfinite(dist.get(w)))
                    kl.add(w);
                if (dist.get(v) + dg.getWeight(v, w) < dist.get(w)) {
                    pred.put(w, v);
                    dist.replace(w, dist.get(v) + dg.getWeight(v, w));
                }

            }

        }
    }


    /**
     * Liefert einen kürzesten Weg von Startknoten s nach Zielknoten g.
     * Setzt eine erfolgreiche Suche von searchShortestPath(s,g) voraus.
     *
     * @return kürzester Weg als Liste von Knoten.
     * @throws IllegalArgumentException falls kein kürzester Weg berechnet wurde.
     */
    public List<V> getShortestPath() {
        LinkedList<V> path = new LinkedList<>();
        V selected = end;
        while (selected != start) {
            path.add(selected);
            selected = pred.get(selected);
        }
        path.add(start);
        LinkedList<V> result = new LinkedList<>();
        while (path.size() > 0) {
            result.add(path.removeLast());
        }
        return result;
    }

    /**
     * Liefert die Länge eines kürzesten Weges von Startknoten s nach Zielknoten g zurück.
     * Setzt eine erfolgreiche Suche von searchShortestPath(s,g) voraus.
     *
     * @return Länge eines kürzesten Weges.
     * @throws IllegalArgumentException falls kein kürzester Weg berechnet wurde.
     */
    public double getDistance() {
        // ...
        return dist.get(end);
    }


}
