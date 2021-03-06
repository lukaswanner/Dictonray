package Aufgabe1;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class HashDictionary<K, V> implements Dictionary<K, V> {

    private int size= 0;
    private int m = 41; //set the size of the array
    private LinkedList<Entry<K, V>>[] arr = new LinkedList[m];//create an linkedlist array to store the entrys later

    public HashDictionary() {
        for (int i = 0; i < arr.length; i++) {
            arr[i] = new LinkedList<Entry<K, V>>();
        }
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
    public V insert(K key, V value) {

        int hashedKey = hash(key); //save the hashed key
        int position = searchPosition(hashedKey, key); //get the position

        if (position > -1) { //if we find an object
            V oldValue = arr[hashedKey].get(position).getValue(); // get the old value
            arr[hashedKey].remove(position); //remove the object
            Entry<K, V> E = new Entry<>(key, value); //create our entry
            arr[hashedKey].add(E); //insert our entry
            return oldValue;
        }
        if (size()/m >= 2) {
            ensureCapacity(key, value);
            return null;
        }
        Entry<K, V> E = new Entry<>(key, value); //create our entry
        arr[hashedKey].add(E); //insert our entry
        size++;
        return null;

    }


    @Override
    public V search(K key) {
        int hashedKey = hash(key); //save the hashed key
        int position = searchPosition(hashedKey, key);
        if (position > -1) {
            V value = arr[hashedKey].get(position).getValue();
            return value;
        }

        return null;
    }

    @Override
    public V remove(K key) {
        int hashedKey = hash(key); //get the hashed value from the key
        int position = searchPosition(hashedKey, key); //get the position of our entry
        if (position > -1) { //we found our entry
            V value = arr[hashedKey].get(position).getValue(); //save our entry for the return later
            arr[hashedKey].remove(position); //remove our entry
            size --;
            return value; //return the deleted value
        }
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new HashIterator();

    }

    private class HashIterator implements Iterator<Entry<K,V>> {

        int currInd = 0;
        Iterator<Entry<K,V>> currListIt = arr[0].iterator();
        public Entry<K,V> next() {
            if(!hasNext()) throw new NoSuchElementException();
            return currListIt.next();
        }

        @Override
        public boolean hasNext() {
            if(currListIt.hasNext())
                return true;
            while (++currInd < arr.length) {
                currListIt = arr[currInd].iterator();
                if(currListIt.hasNext())
                    return true;
            }
            return false;
        }
    }


    //------------------------------smaller methods for the search insert function below ----------------------------------------------------------

    //this method is used to find out wether the key is already in our array or not
    /*private boolean searchArray(int key) {
        try {
            if (arr[key].getFirst() != null) { //if the first object at the position of the hashed key isnt null there is a object already there
                return true;                   //return true because there is already an object
            }
        } catch (NullPointerException e) {
            arr[key] = new LinkedList();
            return false;
        }
        return false;
    }*/

    private int searchPosition(int hashedKey, K key) {
        for (int i = 0; i < arr[hashedKey].size(); i++) {
            if (arr[hashedKey].get(i).getKey().equals(key)) {
                return i;
            }
        }
        return -1;
    }

    private boolean isPrime(int num) {
        if (num < 2) return false;
        if (num == 2) return true;
        if (num % 2 == 0) return false;
        for (int i = 3; i * i <= num; i += 2)
            if (num % i == 0) return false;
        return true;
    }

    private void ensureCapacity(K Key, V Value) {
        m = 2 * m;
        while (!isPrime(m)) {
            m = m + 1;
        }
        LinkedList<Entry<K, V>>[] newArr = new LinkedList[m];
        for (int i = 0; i < newArr.length; i++) {
            newArr[i] = new LinkedList<Entry<K, V>>();
        }
        LinkedList<Entry<K, V>>[] oldArr = arr;
        //set the old arr to the new one
        arr = newArr;
        insert(Key, Value);
        for (int i = 0; i < oldArr.length; i++) {
            for (int j = 0; j < oldArr[i].size(); j++) {
                K key = oldArr[i].get(j).getKey(); //get the key
                V value = oldArr[i].get(j).getValue(); //get the value
                insert(key, value); //insert both in the new arr

            }
        }
    }

}