package Aufgabe1;

import java.util.Scanner;

public class tui {

    public static void tuui() {
        System.out.println("gib was ein : ");
        Scanner in = new Scanner(System.in);
        Dictionary<String,String> dict = new SortedArrayDictionary<>();
        while (true) {
            String input = in.nextLine();
            char first = input.charAt(0);
            String[] a = input.split(" ");
            if(a[0].contains("create") && first == 'c') {
                if(a[1].equals("Hash")) {
                    dict = new HashDictionary();
                    System.out.println("verwenden nun HashDictionary");
                }else{
                    dict = new SortedArrayDictionary<>();
                }
            }else if(a[0].contains("read") && first == 'r' && a.length == 3) {
                dict.insert(a[1],a[2]);
                System.out.println("erfolgreich eingelesen");
            }else if(a[0].equals("exit") && first == 'e'){
                System.out.println("fertig");
                return;
            }else if(a[0].equals("p")){
                for (Dictionary.Entry<String, String> e : dict) {
                    System.out.println(" KEY: " + e.getKey() + ": " + "VALUE: "+e.getValue());
                }
            }else if(a[0].equals("s")){
                System.out.println(dict.search(a[1]));
            }else if(a[0].equals("r")){
                System.out.println(dict.remove(a[1]));
            }else if(a[0].equals("i")) {
                System.out.println(dict.insert(a[1],a[2]));
                System.out.println("erfolgreich eingetragen");
            }else if(a[0].equals("perf8")){
                performance perf = new performance();
                System.out.println("einfügen von 8000 einträgen " + perf.insert1(dict)/1000000 + " ms");
                System.out.println("suchen nach 8000 einträgen " +perf.search1(dict)/1000000 + " ms");
            }else if(a[0].equals("perf16")){
                performance perf = new performance();
                System.out.println("einfügen von 16000 einträgen " + perf.insert1(dict)/1000000 + " ms");
                System.out.println("suchen nach 16000 einträgen " +perf.search1(dict)/1000000 + " ms");
            }


        }
    }

    public static void main(String[] args) {
        tuui();
    }

}
