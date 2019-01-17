package Aufgabe4;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class UnionFind {

    public int[] arr;

    public UnionFind(int n) {
        arr = new int[n];
        for (int i = 0; i < arr.length ; i++) {
            arr[i] = -1;
        }
    }

    public int find(int e) {
      while (arr[e] >= 0)
          e = arr[e];
      return e;
    }

    public int size() {
        int i = 0;
        for (int j = 0; j < arr.length; j++) {
            if(arr[i] < 0 )
                i++;
        }
        return i;
    }

    public  void union(int s1,int s2) {
        if(arr[s1] >= 0 || arr[s2] >= 0)
            return;
        if(s1 == s2)
            return;

        if(-arr[s1] < -arr[s2]){ //Größe von s1 < als Größe s2
            arr[s1] = s2; //Größe von s2 wächst um Größe von s1
        }else {
            if(-arr[s1] == -arr[s2])
                arr[s1]--;
            arr[s2] = s1;
        }

    }

    public static void main(String[] args) {
        UnionFind un = new UnionFind(6);
        un.arr[3] = -1;
        un.arr[5] = -2;
        un.union(3,5);
        un.union(2,5);
        System.out.println(un.arr[3]);
        System.out.println(un.find(5));

    }
}
