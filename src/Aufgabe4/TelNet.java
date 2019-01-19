package Aufgabe4;

import java.util.*;

public class TelNet {

    //Leistungsbegrenzungswert
    public int lbg;
    //TelKnoten-Liste
    public HashMap<Integer, TelKnoten> TelKnoten = new HashMap<>();
    //Liste aller Verbindungen
    public LinkedList<TelVerbindung> edges = new LinkedList<>();
    //min aufspannender Baum
    LinkedList<TelVerbindung> minSpanTree = new LinkedList<>();

    public TelNet(int lbg) {
        this.lbg = lbg;
    }

    public boolean addTelKnoten(int x, int y) {
        TelKnoten Knoten = new TelKnoten(x, y);
        for (int i = 0; i < TelKnoten.size(); i++) {
            if (TelKnoten.get(i).x == x && TelKnoten.get(i).y == y) {
                return false;
            }
        }
        TelKnoten.put(TelKnoten.size(), Knoten);
        if (TelKnoten.size() > 1) {
            for (int i = 0; i < TelKnoten.size(); i++) {
                int dist = Math.abs(x - TelKnoten.get(i).x) + Math.abs(y - TelKnoten.get(i).y);
                if (dist < lbg)
                    addEdge(dist, x, y, TelKnoten.get(i).x, TelKnoten.get(i).y);
            }
        }
        return true;
    }

    public void addEdge(int cost, int x, int y, int x2, int y2) {
        edges.add(new TelVerbindung(cost, new TelKnoten(x, y), new TelKnoten(x2, y2)));
    }

    public boolean computeOptTelNet() {
        if(getOptTelNet() == null) {
            return false;
        }
        return true;
    }

    public void drawOptTelNet(int xMax, int yMax) {

    }

    public void generateRandomTelNet(int n, int xMax, int yMax) {

    }

    public LinkedList<TelVerbindung> getOptTelNet() {
        minSpanTree.clear();
        System.out.println(TelKnoten.size());
        System.out.println(edges.size());

        UnionFind forest = new UnionFind(TelKnoten.size());
        PriorityQueue<TelVerbindung> pq = new PriorityQueue<>(TelVerbindung::compareTo);
        for (int i = 0; (i) < edges.size(); i++) {
            pq.add(edges.get(i));
        }


        for (int i = 0; i < TelKnoten.size(); i++) {
            System.out.println(TelKnoten.get(i).toString());
        }


        while (forest.size() != 1 && !pq.isEmpty()) {
            TelVerbindung tv = pq.poll();
            int u = -1;
            int v = -1;
            for (Map.Entry<Integer, TelKnoten> entry : TelKnoten.entrySet()) {
                if (entry.getValue().x == tv.u.x && entry.getValue().y == tv.u.y) {
                    u = entry.getKey();
                }
                if (entry.getValue().x == tv.v.x && entry.getValue().y == tv.v.y) {
                    v = entry.getKey();
                }
            }
            int t1 = forest.find(u);
            int t2 = forest.find(v);

            if (t1 != t2) {
                forest.union(t1, t2);
                minSpanTree.add(tv);
            }
        }

        if (pq.isEmpty()) {
            System.out.println(forest.size());
        }
        if (pq.isEmpty() && forest.size() != 1) {
            return null;
        }

        return minSpanTree;

    }

    public int getOptTelNetKosten() {
        int cost = -1;
        if(minSpanTree.size() > 0) {
            cost = 0;
            for (TelVerbindung tv : minSpanTree) {
                cost += tv.c;
            }
        }
        return cost;
    }

    public int size() {
        return getOptTelNet().size();
    }

    public static void main(String[] args) {

        TelNet net = new TelNet(7);
        net.addTelKnoten(1, 1);
        net.addTelKnoten(3, 1);
        net.addTelKnoten(4, 2);
        net.addTelKnoten(3, 4);
        net.addTelKnoten(7, 5);
        net.addTelKnoten(2, 6);
        net.addTelKnoten(4, 7);
        LinkedList<TelVerbindung> mylist = net.getOptTelNet();
        for (TelVerbindung tl : mylist){
            System.out.println(tl.toString());
        }
        System.out.println(net.getOptTelNetKosten());
    }


}
