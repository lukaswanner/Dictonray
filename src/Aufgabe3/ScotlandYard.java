package Aufgabe3;

import java.io.FileNotFoundException;

import Aufgabe3.sim.*;

import java.awt.Color;
import java.io.IOException;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import Aufgabe2.*;

/**
 * Kürzeste Wege im Scotland-Yard Spielplan mit A* und Dijkstra.
 *
 * @author Oliver Bittel
 * @since 27.01.2015
 */
public class ScotlandYard {

    /**
     * Fabrikmethode zur Erzeugung eines gerichteten Graphens für den Scotland-Yard-Spielplan.
     * <p>
     * Liest die Verbindungsdaten von der Datei ScotlandYard_Kanten.txt.
     * Für die Verbindungen werden folgende Gewichte angenommen:
     * U-Bahn = 5, Taxi = 2 und Bus = 3.
     * Falls Knotenverbindungen unterschiedliche Beförderungsmittel gestatten,
     * wird das billigste Beförderungsmittel gewählt.
     * Bei einer Vebindung von u nach v wird in den gerichteten Graph sowohl
     * eine Kante von u nach v als auch von v nach u eingetragen.
     *
     * @return Gerichteter und Gewichteter Graph für Scotland-Yard.
     * @throws FileNotFoundException
     */
    public static DirectedGraph<Integer> getGraph() throws FileNotFoundException {

        DirectedGraph<Integer> sy_graph = new AdjacencyListDirectedGraph<>();
        Scanner in = new Scanner(new File("C:\\Users\\Lukas\\IdeaProjects\\WS18_Java\\src\\Aufgabe3\\ScotlandYard_Kanten.txt"));
        while (in.hasNext()) {
            String input = in.nextLine();
            String[] inArray = input.split(" ");
            if (inArray[2].equals("UBahn"))
                if (sy_graph.getWeight(Integer.parseInt(inArray[0]), Integer.parseInt(inArray[1])) >= 5)
                    sy_graph.addEdge(Integer.parseInt(inArray[0]), Integer.parseInt(inArray[1]), 5);
            if (inArray[2].equals("Taxi"))
                if (sy_graph.getWeight(Integer.parseInt(inArray[0]), Integer.parseInt(inArray[1])) >= 2)
                    sy_graph.addEdge(Integer.parseInt(inArray[0]), Integer.parseInt(inArray[1]), 2);
            if (inArray[2].equals("Bus"))
                if (sy_graph.getWeight(Integer.parseInt(inArray[0]), Integer.parseInt(inArray[1])) >= 3)
                    sy_graph.addEdge(Integer.parseInt(inArray[0]), Integer.parseInt(inArray[1]), 3);
        }
        return sy_graph;
    }


    /**
     * Fabrikmethode zur Erzeugung einer Heuristik für die Schätzung
     * der Distanz zweier Knoten im Scotland-Yard-Spielplan.
     * Die Heuristik wird für A* benötigt.
     * <p>
     * Liest die (x,y)-Koordinaten (Pixelkoordinaten) aller Knoten von der Datei
     * ScotlandYard_Knoten.txt in eine Map ein.
     * Die zurückgelieferte Heuristik-Funktion estimatedCost
     * berechnet einen skalierten Euklidischen Abstand.
     *
     * @return Heuristik für Scotland-Yard.
     * @throws FileNotFoundException
     */
    public static Heuristic<Integer> getHeuristic() throws FileNotFoundException {
        return new ScotlandYardHeuristic();
    }

    /**
     * Scotland-Yard Anwendung.
     *
     * @param args wird nicht verewendet.
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {

        DirectedGraph<Integer> syGraph = getGraph();

        Heuristic<Integer> syHeuristic = null; // Dijkstra
        //Heuristic<Integer> syHeuristic = getHeuristic(); // A*

        //System.out.println(syGraph.getWeight(78, 79));
        //System.out.println(syGraph.getWeight(65, 82));

        ShortestPath<Integer> sySp = new ShortestPath<Integer>(syGraph, syHeuristic);

        sySp.searchShortestPath(65, 157);
        System.out.println("Distance = " + sySp.getDistance()); // 9.0
        System.out.println("Weg = " + sySp.getShortestPath());

        sySp.searchShortestPath(1, 175);
        System.out.println("Distance = " + sySp.getDistance()); // 25.0

        sySp.searchShortestPath(1, 173);
        System.out.println("Distance = " + sySp.getDistance()); // 22.0
/*

        SYSimulation sim;
        try {
            sim = new SYSimulation();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        sySp.setSimulator(sim);
        sim.startSequence("Shortest path from 1 to 173");

        //sySp.searchShortestPath(65,157); // 9.0
        //sySp.searchShortestPath(1,175); //25.0

        sySp.searchShortestPath(1, 173); //22.0
        // bei Heuristik-Faktor von 1/10 wird nicht der optimale Pfad produziert.
        // bei 1/30 funktioniert es.

        System.out.println("Distance = " + sySp.getDistance());
        List<Integer> sp = sySp.getShortestPath();

        int a = -1;
        for (int b : sp) {
            if (a != -1)
                sim.drive(a, b, Color.RED.darker());
            sim.visitStation(b);
            a = b;
        }

        sim.stopSequence();

*/
    }

}

class ScotlandYardHeuristic implements Heuristic<Integer> {
    private Map<Integer, Point> coord; // Ordnet jedem Knoten seine Koordinaten zu

    private static class Point {
        int x;
        int y;

        Point(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }

    public ScotlandYardHeuristic() throws FileNotFoundException {
        // ...
    }

    public double estimatedCost(Integer u, Integer v) {
        // ...
        return 0;
    }
}

