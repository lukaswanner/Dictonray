package Aufgabe1;

import java.util.Scanner;

public class tui {

    public static void tuui() {
        System.out.println("gib was ein : ");
        Scanner in = new Scanner(System.in);
        Dictionary dict = new SortedArrayDictionary();
        while (true) {
            String input = in.nextLine();
            char first = input.charAt(0);
            String[] a = input.split(" ");
            if(a[0].contains("create") && first == 'c') {
                if(a[1].equals("Hashdictionary")) {
                    dict = new HashDictionary();
                    System.out.println("verwenden nun HashDictionary");
                }
            }else if(a[0].contains("read") && first == 'r' && a.length == 3) {
                dict.insert(a[1],a[2]);
                System.out.println("erfolgreich eingelesen");
            }else if(a[0].equals("exit") && first == 'e'){
                System.out.println("fertig");
                return;
            }else if(a[0].equals("p")){
                while (dict.iterator().hasNext()){
                    System.out.println(dict.iterator().next());
                }
            }else if(a[0].equals("s")){
                Object o = dict.search(a[1]);
                System.out.println(o.toString());
            }else if(a[0].equals("r")){
                dict.remove(a[1]);
                System.out.println("gelöscht");
            }else if(a[0].equals("i")) {
                dict.insert(a[1],a[2]);
                System.out.println("erfolgreich eingetragen");
            }


        }
    }

    public static void main(String[] args) {
        tuui();
    }

}
