package Aufgabe1;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class BinaryTreeDictionary<K extends Comparable<? super K>, V> implements Dictionary<K, V>  {

    private Node<K,V> root = null;
    private V oldValue;
    private int Anzahl=0;

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
    public Iterator<Entry<K,V>> iterator() { return new BinaryIterator(root);}

    private class BinaryIterator implements Iterator {
        private Node next;

        public BinaryIterator(Node root){
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
            Node r = next;

            if(next.right != null) {
                next = next.right;
                while (next.left != null) {
                    next = next.left;
                }
                K key = (K)r.key;
                V value = (V) r.value;
                Entry<K,V> e = new Entry<>(key,value);
                return e;
            }

            while (true) {
                if(next.parent == null) {
                    next = null;
                    K key = (K)r.key;
                    V value = (V) r.value;
                    Entry<K,V> e = new Entry<>(key,value);
                    return e;
                }
                if(next.parent.left == next){
                    next = next.parent;
                    K key = (K)r.key;
                    V value = (V) r.value;
                    Entry<K,V> e = new Entry<>(key,value);
                    return e;
                }
                next = next.parent;
            }


        }
    }

    public void prettyPrint() {
        System.out.println(root.value);
        if (root.left != null || root.right != null) {
            if (root.left == null) {
                System.out.printf("|__#%n");
            } else {
                prettyPrintR(root.left);
            }
            if (root.right == null) {
                System.out.printf("|__#%n");
            } else {
                prettyPrintR(root.right);
            }
        }
    }

    private void prettyPrintR(Node<K,V> p) {

        System.out.println("|__" + p.value);

        if (p.right != null || p.left != null) {
            if (p.left == null) {
                Anzahl++;
                for (int i = 0; i <Anzahl ; i++) {
                    System.out.printf("\t");
                }
                System.out.printf("|__#%n");
                Anzahl--;
            } else {
                Anzahl++;
                for (int i = 0; i <Anzahl ; i++) {
                    System.out.printf("\t");
                }

                prettyPrintR(p.left);
                Anzahl--;
            }
            if (p.right == null) {
                Anzahl++;
                for (int i = 0; i <Anzahl ; i++) {
                    System.out.printf("\t");
                }
                System.out.printf("|__#%n");
                Anzahl--;
            } else {
                Anzahl++;
                for (int i = 0; i <Anzahl ; i++) {
                    System.out.printf("\t");
                }
                prettyPrintR(p.right);
                Anzahl--;
            }
        }
    }


    //------------------------zusätliche Klassen und Hilfsmethoden-------------------------

    //Klassen
     private static class Node<K,V> {
        private Node<K,V> parent;
        private K key;
        private V value;
        private Node<K,V> left;
        private Node<K,V> right;
        private Node(K k,V v) {
            key = k;
            value = v;
            left = null;
            right = null;
            parent=null;
        }
    }

     private static class MinNode<K,V> {
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
            MinNode<K,V> min = new MinNode<>();
            p.right = getRemMinR(p.right,min);
            oldValue = p.value;
            p.key = min.key;
            p.value = min.value;
        }
        return p;
    }

    private Node<K,V> getRemMinR(Node<K,V> p,MinNode<K,V> min){
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
