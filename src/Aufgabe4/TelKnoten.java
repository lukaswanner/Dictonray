package Aufgabe4;

public class TelKnoten {

    public int x;
    public int y;

    public TelKnoten(int x,int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        String knoten = "("+x+"/"+y+")";
        return knoten;
    }

}
