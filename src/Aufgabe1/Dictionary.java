package Aufgabe1;

public interface Dictionary<K, V> {

    V insert(K key, V value);
    //If given key isnt in the data set yet insert it and return null
    //If the key is given already override it with the given value and return the old key

    V search(K key);
    //returns the value corresponding to the key
    //if there is no fitting key return null

    V remove(K key);
    //remove the given key(if given) and returns the old data
    //if the key isn't given return null

    class Entry<K, V> {
        K key; //the key value which you use to find the Entry
        V value; //the actual value which is stored in the Entry


        public Entry(K key,V value) { //Constructor to create an Entry
            this.key = key;
            this.value = value;
            //set the given values from the construstor equal with the class
        }
    }

}
