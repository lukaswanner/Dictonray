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
        if (getOptTelNet() == null) {
            return false;
        }
        return true;
    }

    public void drawOptTelNet(int xMax, int yMax) {
        StdDraw.setCanvasSize(xMax,yMax);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.filledSquare(0,0,xMax);
        StdDraw.setXscale(0, xMax);
        StdDraw.setYscale(0, yMax);
        StdDraw.setPenRadius(0.003);

        for (TelVerbindung tv : minSpanTree) {
            StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.filledRectangle(tv.v.x, tv.v.y, 2.5, 2.5);
            StdDraw.filledRectangle(tv.u.x, tv.u.y, 2.5, 2.5);
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.filledCircle(tv.v.x, tv.v.y, 1.5);
            StdDraw.filledCircle(tv.u.x, tv.u.y, 1.5);
            StdDraw.line(tv.u.x, tv.u.y, tv.v.x, tv.u.y);
            StdDraw.line(tv.v.x, tv.u.y, tv.v.x, tv.v.y);
        }
        StdDraw.show();
    }

    public void generateRandomTelNet(int n, int xMax, int yMax) {
        TelKnoten.clear();
        edges.clear();
        for (int i = 0; i < n; i++) {
            addTelKnoten((int) (Math.random() * ((xMax - 1) + 1)) + 1, (int) (Math.random() * ((yMax - 1) + 1)) + 1);
        }
        if(getOptTelNet() != null) {
            drawOptTelNet(xMax,yMax);
        }
    }

    public LinkedList<TelVerbindung> getOptTelNet() {
        minSpanTree.clear();

        UnionFind forest = new UnionFind(TelKnoten.size());
        PriorityQueue<TelVerbindung> pq = new PriorityQueue<>(TelVerbindung::compareTo);
        for (int i = 0; (i) < edges.size(); i++) {
            pq.add(edges.get(i));
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

        if (pq.isEmpty() && forest.size() != 1) {
            return null;
        }

        return minSpanTree;

    }

    public int getOptTelNetKosten() {
        int cost = -1;
        if (minSpanTree.size() > 0) {
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

        TelNet net = new TelNet(100);
        net.addTelKnoten(10, 10);
        net.addTelKnoten(30, 10);
        net.addTelKnoten(40, 20);
        net.addTelKnoten(30, 40);
        net.addTelKnoten(70, 50);
        net.addTelKnoten(20, 60);
        net.addTelKnoten(40, 70);
        LinkedList<TelVerbindung> mylist = net.getOptTelNet();
        for (TelVerbindung tl : mylist) {
            System.out.println(tl.toString());
        }
        System.out.println(net.getOptTelNetKosten());
        net.generateRandomTelNet(1080,1920,1080);
    }


}
