package Aufgabe4;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class UnionFind {

    public TreeMap<Integer,TelKnoten>[] arr;

    public UnionFind(int n) {
        arr = new TreeMap[n];
        for(int i = 0;i < arr.length;i++){
            TelKnoten k = new TelKnoten(2,3);
            arr[i] = new TreeMap<Integer, TelKnoten>();
            arr[i].put(i,k);
        }
        arr[5].put(3,new TelKnoten(4,99));
        arr[5].put(2,new TelKnoten(3,98));
        arr[5].put(1,new TelKnoten(2,97));
    }

    public int find(int e) {
        int pos = -1;
        for (int i = 0; i < arr.length;i++) {
            for(Map.Entry<Integer,TelKnoten> entry : arr[i].entrySet()) {
                System.out.println("Arr an der Stelle " + i + " "+entry.getKey() + " => " + entry.getValue());
            }
        }
        return pos;
    }

    public int size() {
        return arr.length;
    }

    public  void union(int s1,int s2) {

    }

    public static void main(String[] args) {
        UnionFind un = new UnionFind(6);

        un.find(5);
        System.out.println("");
        System.out.println(un.arr[5].entrySet());

    }
}
