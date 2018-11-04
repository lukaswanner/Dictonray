package Aufgabe1;

public class testProgramm {

    public static void main(String[] args) {
        HashDictionary hd = new HashDictionary();
        System.out.println("Das sind die Daten von davor: " + hd.insert(13, "lukas"));
        System.out.println("Das sind die Daten von davor: " + hd.insert(12, "test"));
        System.out.println("Das sind die Daten von davor: " + hd.insert(54, "vanessa"));
        System.out.println("Das sind die Daten von davor: " + hd.insert(95, "lukas und vanessa"));
        System.out.println("Das sind die Daten von davor: " + hd.insert("lukas", "Ich glaub es klappt"));
        System.out.println("Das sind die Daten vom Eintrag Lukas: " + hd.search("lukas"));
        System.out.println("Das sind die alten Daten von Lukas: " + hd.insert("lukas", "Lukas ist cool"));
        System.out.println("Das sind die neuen Daten von Lukas: " + hd.search("lukas"));
        System.out.println(hd.insert(1230948, "jawohl !!!!!!!!!!"));
        System.out.println(hd.search(1230948));
        System.out.println("wir löschen nun eintrag lukas: " + hd.remove("lukas"));

    }

}
