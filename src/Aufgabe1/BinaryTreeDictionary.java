package Aufgabe1;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class BinaryTreeDictionary<K extends Comparable<? super K>, V> implements Dictionary<K, V>  {

    private Entry<K,V> root = null;
    private V oldValue;

    @Override
    public V insert(K key, V value) {
        root = insertR(key,value,root);
        if(root != null) {
            root.parent = null;
        }
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
    public Iterator<Entry<K, V>> iterator() { return new BinaryIterator(root);}

    private class BinaryIterator implements Iterator {
        private Entry next;

        public BinaryIterator(Entry root){
            next = root;
            if(next == null){
                return;
            }
            while (next.left != null) {
                next = next.left;
            }
        }

        @Override
        public boolean hasNext() {
            return next != null;

        }

        @Override
        public Entry<K,V> next() {
            if(!hasNext()) throw new NoSuchElementException();
            Entry r = next;

            if(next.right != null) {
                next = next.right;
                while (next.left != null) {
                    next = next.left;
                }
                return r;
            }

            while (true) {
                if(next.parent == null) {
                    next = null;
                    return r;
                }
                if(next.parent.left == next){
                    next = next.parent;
                    return r;
                }
                next = next.parent;
            }


        }
    }


    //------------------------zusätliche Klassen und Hilfsmethoden-------------------------

    //Klassen
     private static class Entry<K,V> {
        private Entry<K,V> parent;
        private K key;
        private V value;
        private Entry<K,V> left;
        private Entry<K,V> right;
        private Entry(K k,V v) {
            key = k;
            value = v;
            left = null;
            right = null;
            parent=null;
        }
    }

     private static class MinEntry<K,V> {
        private K key;
        private V value;
     }

    //Methoden
    private V searchR(K key,Entry<K,V> p) {
        if(p == null)
            return null;
        else if(key.compareTo(p.key) < 0)
            return searchR(key,p.left);
        else if(key.compareTo(p.key)>0)
            return searchR(key,p.right);
        else
            return p.value;
    }

    private Entry<K,V> insertR(K key,V value,Entry<K,V> p){
        if(p == null){
            p = new Entry(key,value);
            oldValue = null;
        }
        else if(key.compareTo(p.key) < 0) {
            p.left = insertR(key, value, p.left);
            if(p.left != null) {
                p.left.parent = p;
            }
        } else if(key.compareTo(p.key)>0) {
            p.right = insertR(key, value, p.right);
            if(p.right != null) {
                p.right.parent = p;
            }
        } else {
            oldValue = p.value;
            p.value = value;
        }
        return p;
    }

    private Entry<K,V> removeR(K key,Entry<K,V> p) {
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

    private Entry<K,V> getRemMinR(Entry<K,V> p,MinEntry<K,V> min){
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
