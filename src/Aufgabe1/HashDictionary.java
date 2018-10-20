package Aufgabe1;

import java.util.LinkedList;

public class HashDictionary<K, V> implements Dictionary<K, V> {

    private int m = 41; //set the size of the array
    private LinkedList[] arr = new LinkedList[m];//create an linkedlist array to store the entrys later

    public HashDictionary() {
    }

    private int hash(K key) { //this method is used to hash all sorts of hashable keys
        int adr = key.hashCode(); //store the hased key in adr

        //if the adr is somehow negative just invert it
        if (adr < 0) {
            adr = -adr;
        }

        //use the modulo operator to divide the given hashed key so it fits into our array
        return adr % m;
    }

    @Override
    //TODO ensureCapasity irgendwie noch implimentieren falls an arr[stelle].size size > 2 ist
    //TODO fragen ob ich hierfür primzahlen nutzen soll oder ob des mit normalen geht
    public V insert(K key, V value) {
        int hashedKey = hash(key); //save the hashed key
        System.out.println(hashedKey);
        boolean filled = searchArray(hashedKey); //see if there is already an object at arr[hashedkey]
        // System.out.println(filled);
        if (filled) { //if filled is true that means we need to search further to see if our key is already in use
            if (search(key).equals(null)) { //if search returns null that means there is no entry with our key yet
                Entry<K, V> E = new Entry<>(key, value); //create our entry
                arr[hashedKey].add(E); //insert our entry
                return null;
            } else {
                //System.out.println("wir haben bereits ein objekt hier yay ! ");
                int position = searchPosition(hashedKey, key); //get the position
                if (position > 0) { //if we find an object
                    Object o = arr[hashedKey].get(position);
                    V oldValue = (V) ((Entry) o).value; // get the old value
                    arr[hashedKey].remove(position); //remove the object
                    Entry<K, V> E = new Entry<>(key, value); //create our entry
                    arr[hashedKey].add(E); //insert our entry
                    return oldValue;
                }
                Entry<K, V> E = new Entry<>(key, value); //create our entry
                arr[hashedKey].add(E); //insert our entry
                return null;
            }
        } else { //if not just insert an Entry at the hashedKey position
            //System.out.println("wir sind hier");
            Entry<K, V> E = new Entry<>(key, value); //create an Entry with our key and value
            arr[hashedKey].add(E); //insert our entry
            return null;
        }
    }

    @Override
    public V search(K key) {
        int hashedKey = hash(key); //save the hashed key
        boolean filled = searchArray(hashedKey); //see if there is even an object with the hashed key in our array
        //System.out.println("in userer suche haben wir filled auf:" + filled);
        if (filled) { //hurray if filled is true we only need to search further if our actually key is there as well
            int position = searchPosition(hashedKey, key);
            //System.out.println("Das ist unserer position : " + position + " das ist unsere position im array: " + hashedKey);
            if (position > 0) {
                Object o = arr[hashedKey].get(position);
                return (V) ((Entry) o).value;
            }
        }
        return null;
    }

    @Override
    public V remove(K key) {
        int hashedKey = hash(key); //get the hashed value from the key
        boolean filled = searchArray(hashedKey); //see if there is even an entry at that position

        if (filled) { //that means we have a potential fit
            int position = searchPosition(hashedKey, key); //get the position of our entry

            if (position > 0) { //we found our entry
                Object o = arr[hashedKey].get(position); //save our entry for the return later
                arr[hashedKey].remove(position); //remove our entry
                return (V) ((Entry) o).value; //return the deleted value
            }
        }

        return null;
    }


    //------------------------------smaller methods for the search insert function below ----------------------------------------------------------

    //this method is used to find out wether the key is already in our array or not
    private boolean searchArray(int key) {
        try {
            if (arr[key].getFirst() != null) { //if the first object at the position of the hashed key isnt null there is a object already there
                return true;                   //return true because there is already an object
            }
        } catch (NullPointerException e) {
            arr[key] = new LinkedList();
            arr[key].add(key);
            return false;
        }
        return false;
    }

    private int searchPosition(int hashedKey, K key) {
        for (int i = 1; i < arr[hashedKey].size(); i++) {
            Object o = arr[hashedKey].get(i); //now we get the entry at the hashedKey position and get the object at position i in the linked list at arr[hashedkey]
            if (o instanceof Entry) {          //now we see the object o is an actual Entry which always should be the case
                if (((Entry) o).key.equals(key)) { //now we see if the Entry at position i has the same key as our given key
                    return i;
                }
            }

        }
        return 0;
    }

}
