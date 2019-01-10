package Aufgabe4;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;

public class TelNet {

    //Leistungsbegrenzungswert
    public int lbg;
    //TelKnoten-Liste
    public HashMap<Integer,TelKnoten> TelKnotenVerbindung = new HashMap<>();
    public PriorityQueue<TelVerbindung> TelVerbindungen = new PriorityQueue<>();

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
