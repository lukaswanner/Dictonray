package Aufgabe1;

import java.io.*;
import java.util.Scanner;

public class tui {

    public static void tuui() throws IOException {
        System.out.println("geben sie was ein: ");
        Scanner in = new Scanner(System.in);
        String[] a2 = null;
        int lines = 0;
        int size = 0;
        File file =  new File("/home/student/Schreibtisch/dtengl.txt");
        Dictionary<String,String> dict = new SortedArrayDictionary<>();
        while (true) {
            String input = in.nextLine();
            String[] a = input.split(" ");
            if(a[0].equals("create")) {
                if(a.length == 2 && a[1].contains("hash")) {
                    dict = new HashDictionary();
                    System.out.println("verwenden nun HashDictionary");
                }else{
                    dict = new SortedArrayDictionary<>();
                }
            }else if(a[0].equals("read")) {
                    if (a.length == 2) {
                        file = new File(a[1]);
                    }else if(a.length == 3) {
                        file = new File(a[2]);
                        lines = Integer.parseInt(a[1]);
                    }
                BufferedReader br = new BufferedReader(new FileReader(file));
                String st;
                if(a.length <= 2) {
                    while ((st = br.readLine()) != null) {
                        a2 = st.split(" ");
                        dict.insert(a2[0], a2[1]);
                    }
                }else {
                    while ((st = br.readLine()) != null && lines > 0) {
                        a2 = st.split(" ");
                        dict.insert(a2[0], a2[1]);
                        lines = lines -1;
                    }
                }
                System.out.println("erfolgreich eingelesen");


            }else if(a[0].equals("exit")){
                System.out.println("fertig");
                return;
            }else if(a[0].equals("p")){
                for (Dictionary.Entry<String, String> e : dict) {
                    size++;
                    System.out.println(" KEY: " + e.getKey() + ": " + "VALUE: "+e.getValue());
                }
                System.out.println("Insgesammt sind " + size + " Wörter im Wörterbuch");
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
                System.out.println("suchen nach 8000 einträgen (nicht erfolgreich) " +perf.search3(dict)/1000000 + " ms");
            }else if(a[0].equals("perf16")){
                performance perf = new performance();
                System.out.println("einfügen von 16000 einträgen " + perf.insert2(dict)/1000000 + " ms");
                System.out.println("suchen nach 16000 einträgen " +perf.search2(dict)/1000000 + " ms");
            }


        }
    }

    public static void main(String[] args) throws IOException {
        tuui();
    }

}
