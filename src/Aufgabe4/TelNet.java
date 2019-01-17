package Aufgabe4;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class TelNet {

    //Leistungsbegrenzungswert
    public int lbg;
    //TelKnoten-Liste
    public HashMap<Integer,TelKnoten> TelKnotenVerbindung = new HashMap<>();
    public List<TelVerbindung> TelVerbindungen = new LinkedList<>();

    public TelNet(int lbg) {
        this.lbg = lbg;
    }

    public boolean addTelKnoten(int x,int y){
        TelKnoten Knoten = new TelKnoten(x,y);
        if(TelKnotenVerbindung.containsValue(Knoten)){
            return false;
        }
        TelKnotenVerbindung.put(TelKnotenVerbindung.size(),Knoten);
        return true;
    }

    public boolean computeOptTelNet(){
        return true;
    }

    public void drawOptTelNet(int xMax,int yMax){

    }

    public void generateRandomTelNet(int n,int xMax,int yMax){

    }

    public LinkedList<TelVerbindung> getOptTelNet() {

        UnionFind forest = new UnionFind(TelKnotenVerbindung.size());
        PriorityQueue<TelVerbindung> verbindungen = new PriorityQueue<>();
        for (int i = 0; i < TelVerbindungen.size() ; i++) {
            verbindungen.add(TelVerbindungen.get(i));
        }
        List<TelVerbindung> minSpanTree;

        while(forest.size() != 1 && !verbindungen.isEmpty()) {

        }

        return null;

    }

    public int getOptTelNetKosten(){
        return 0;
    }

    public int size() {
        return getOptTelNet().size();
    }

    public static void main(String[] args){

    }


}
