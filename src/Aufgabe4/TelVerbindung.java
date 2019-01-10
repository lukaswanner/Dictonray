package Aufgabe4;

public class TelVerbindung implements Comparable<TelVerbindung> {

    //Verbindungskosten
    public int c;
    //StartKnoten
    public TelKnoten u;
    //EndKnoten
    public TelKnoten v;

    public TelVerbindung(int c,TelKnoten u,TelKnoten v) {
        this.c = c;
        this.u = u;
        this.v = v;
    }


    @Override
    public int compareTo(TelVerbindung o) {
        if(this.c < o.c) {
            return this.c;
        }else {
            return o.c;
        }
    }
}
