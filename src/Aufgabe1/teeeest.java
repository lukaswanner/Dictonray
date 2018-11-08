/*
 * Test der verscheidenen Dictionary-Implementierungen
 *
 * O. Bittel
 * 6.7.2017
 */
package Aufgabe1;

/**
 * Static test methods for different Dictionary implementations.
 * @author oliverbittel
 */
public class teeeest {

    /**
     * @param args not used.
     */
    public static void main(String[] args)  {

        testSortedArrayDictionary();
        testHashDictionary();
    }

    private static void testSortedArrayDictionary() {
        Dictionary<String, String> dict = new SortedArrayDictionary<>();
        testDict(dict);
    }

    private static void testHashDictionary() {
        Dictionary<String, String> dict = new HashDictionary<>();
        testDict(dict);
    }



    private static void testDict(Dictionary<String, String> dict) {
        System.out.println("===== New Test Case ========================");
        System.out.println("test " + dict.getClass());
        System.out.println(dict.insert("gehen", "go") == null);		// true
        String s = new String("gehen");
        System.out.println(dict.search(s) != null);					// true
        System.out.println(dict.search(s).equals("go"));			// true
        System.out.println(dict.insert(s, "walk").equals("go"));	// true
        System.out.println(dict.search("gehen").equals("walk"));	// true
        System.out.println(dict.remove("gehen").equals("walk"));	// true
        System.out.println(dict.remove("gehen") == null);			// true
        dict.insert("starten", "start");
        dict.insert("gehen", "go");
        dict.insert("schreiben", "write");
        dict.insert("reden", "say");
        dict.insert("arbeiten", "work");
        dict.insert("lesen", "read");
        dict.insert("singen", "sing");
        dict.insert("schwimmen", "swim");
        dict.insert("rennen", "run");
        dict.insert("beten", "pray");
        dict.insert("tanzen", "dance");
        dict.insert("schreien", "cry");
        dict.insert("tauchen", "dive");
        dict.insert("fahren", "drive");
        dict.insert("spielen", "play");
        dict.insert("planen", "plan");
        for (Dictionary.Entry<String, String> e : dict) {
            System.out.println(e.getKey() + ": " + e.getValue() + " search: " + dict.search(e.getKey()));
        }
    }

}