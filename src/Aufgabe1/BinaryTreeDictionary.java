package Aufgabe1;

import java.util.Iterator;

public class BinaryTreeDictionary<K extends Comparable<? super K>, V> implements Dictionary<K, V>  {

    private Node<K,V> root = null;
    private V oldValue;

    @Override
    public V insert(K key, V value) {
        root = insertR(key,value,root);
        return oldValue;
    }

    @Override
    public V search(K key) {
        return searchR(key,root);
    }

    @Override
    public V remove(K key) {
        root = removeR(key,root);
        return oldValue;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return null;
    }



    //------------------------zusätliche Klassen und Hilfsmethoden-------------------------

    //Klassen
     private static class Node<K,V> {
        private K key;
        private V value;
        private Node<K,V> left;
        private Node<K,V> right;
        private Node(K k,V v) {
            key = k;
            value = v;
            left = null;
            right = null;
        }
    }

     private static class MinEntry<K,V> {
        private K key;
        private V value;
     }

    //Methoden
    private V searchR(K key,Node<K,V> p) {
        if(p == null)
            return null;
        else if(key.compareTo(p.key) < 0)
            return searchR(key,p.left);
        else if(key.compareTo(p.key)>0)
            return searchR(key,p.right);
        else
            return p.value;
    }

    private Node<K,V> insertR(K key,V value,Node<K,V> p){
        if(p == null){
            p = new Node(key,value);
            oldValue = null;
        }
        else if(key.compareTo(p.key) < 0)
            p.left = insertR(key,value,p.left);
        else if(key.compareTo(p.key)>0)
            p.right = insertR(key,value,p.right);
        else {
            oldValue = p.value;
            p.value = value;
        }
        return p;
    }

    private Node<K,V> removeR(K key,Node<K,V> p) {
        if( p == null) {oldValue =null;}
        else if(key.compareTo(p.key) < 0)
            p.left = removeR(key,p.left);
        else if(key.compareTo(p.key) > 0)
            p.right = removeR(key,p.right);
        else if(p.left == null || p.right == null) {
            //p muss gelöscht werden
            //und hat ein oder kein Kind
            oldValue = p.value;
            p = (p.left != null) ? p.left : p.right;
        }else{
            //p muss gelöscht werden und haz zwei kinder
            MinEntry<K,V> min = new MinEntry<>();
            p.right = getRemMinR(p.right,min);
            oldValue = p.value;
            p.key = min.key;
            p.value = min.value;
        }
        return p;
    }

    private Node<K,V> getRemMinR(Node<K,V> p,MinEntry<K,V> min){
        assert p != null;
        if(p.left == null) {
            min.key = p.key;
            min.value = p.value;
            p = p.right;
        }else
            p.left = getRemMinR(p.left,min);
        return p;
    }

}
