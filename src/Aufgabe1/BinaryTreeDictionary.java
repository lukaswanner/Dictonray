package Aufgabe1;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class BinaryTreeDictionary<K extends Comparable<? super K>, V> implements Dictionary<K, V> {

    private Node<K, V> root = null;
    private V oldValue;
    private int Anzahl = 0;
    private int size= 0 ;

    @Override
    public V insert(K key, V value) {
        root = insertR(key, value, root);
        if (root != null) {
            root.parent = null;
        }
        return oldValue;
    }

    @Override
    public V search(K key) {
        return searchR(key, root);
    }

    @Override
    public V remove(K key) {
        root = removeR(key, root);
        return oldValue;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new BinaryIterator(root);
    }

    private class BinaryIterator implements Iterator {
        private Node next;

        public BinaryIterator(Node root) {
            next = root;
            if (next == null) {
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
        public Entry<K, V> next() {
            if (!hasNext()) throw new NoSuchElementException();
            Node r = next;

            if (next.right != null) {
                next = next.right;
                while (next.left != null) {
                    next = next.left;
                }
                K key = (K) r.key;
                V value = (V) r.value;
                Entry<K, V> e = new Entry<>(key, value);
                return e;
            }

            while (true) {
                if (next.parent == null) {
                    next = null;
                    K key = (K) r.key;
                    V value = (V) r.value;
                    Entry<K, V> e = new Entry<>(key, value);
                    return e;
                }
                if (next.parent.left == next) {
                    next = next.parent;
                    K key = (K) r.key;
                    V value = (V) r.value;
                    Entry<K, V> e = new Entry<>(key, value);
                    return e;
                }
                next = next.parent;
            }


        }
    }

    public void prettyPrint() {
        System.out.println(root.key + "/" + root.value);
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

    private void prettyPrintR(Node<K, V> p) {
        System.out.print("|__: " + p.key + "/" + p.value);
        System.out.print(" Parent ----> " + p.parent.key + "/" + p.parent.value + "\n");

        if (p.right != null || p.left != null) {
            if (p.left == null) {
                Anzahl++;
                for (int i = 0; i < Anzahl; i++) {
                    System.out.printf("\t");
                }
                System.out.printf("|__#%n");
                Anzahl--;
            } else {
                Anzahl++;
                for (int i = 0; i < Anzahl; i++) {
                    System.out.printf("\t");
                }

                prettyPrintR(p.left);
                Anzahl--;
            }
            if (p.right == null) {
                Anzahl++;
                for (int i = 0; i < Anzahl; i++) {
                    System.out.printf("\t");
                }
                System.out.printf("|__#%n");
                Anzahl--;
            } else {
                Anzahl++;
                for (int i = 0; i < Anzahl; i++) {
                    System.out.printf("\t");
                }
                prettyPrintR(p.right);
                Anzahl--;
            }
        }
    }


    //------------------------zusätliche Klassen und Hilfsmethoden-------------------------

    //Klassen
    private static class Node<K, V> {
        private Node<K, V> parent;
        private int height;
        private K key;
        private V value;
        private Node<K, V> left;
        private Node<K, V> right;

        private Node(K k, V v) {
            height = 0;
            key = k;
            value = v;
            left = null;
            right = null;
            parent = null;
        }
    }

    private static class MinNode<K, V> {
        private K key;
        private V value;
    }

    //Methoden
    private V searchR(K key, Node<K, V> p) {
        if (p == null)
            return null;
        else if (key.compareTo(p.key) < 0)
            return searchR(key, p.left);
        else if (key.compareTo(p.key) > 0)
            return searchR(key, p.right);
        else
            return p.value;
    }

    private Node<K, V> insertR(K key, V value, Node<K, V> p) {
        if (p == null) {
            p = new Node(key, value);
            size++;
            oldValue = null;
        } else if (key.compareTo(p.key) < 0) {
            p.left = insertR(key, value, p.left);
            if (p.left != null) {
                p.left.parent = p;
            }
        } else if (key.compareTo(p.key) > 0) {
            p.right = insertR(key, value, p.right);
            if (p.right != null) {
                p.right.parent = p;
            }
        } else {
            oldValue = p.value;
            p.value = value;
        }
        p = balance(p);
        return p;
    }

    private Node<K, V> removeR(K key, Node<K, V> p) {
        if (p == null) {
            oldValue = null;
        } else if (key.compareTo(p.key) < 0)
            p.left = removeR(key, p.left);
        else if (key.compareTo(p.key) > 0)
            p.right = removeR(key, p.right);
        else if (p.left == null || p.right == null) {
            //p muss gelöscht werden
            //und hat ein oder kein Kind
            oldValue = p.value;
            p = (p.left != null) ? p.left : p.right;
            size--;
        } else {
            //p muss gelöscht werden und haz zwei kinder
            MinNode<K, V> min = new MinNode<>();
            p.right = getRemMinR(p.right, min);
            oldValue = p.value;
            p.key = min.key;
            p.value = min.value;
            size--;
        }
        p = balance(p);
        return p;
    }

    private int getHeight(Node<K, V> p) {
        if (p == null)
            return -1;
        else
            return p.height;
    }

    private int getBalance(Node<K, V> p) {
        if (p == null)
            return 0;
        else
            return getHeight(p.right) - getHeight(p.left);
    }

    private Node<K, V> balance(Node<K, V> p) {
        if (p == null)
            return null;
        p.height = Math.max(getHeight(p.left), getHeight(p.right)) + 1;
        if (getBalance(p) == -2) {
            if (getBalance(p.left) <= 0)
                p = rotateRight(p);
            else
                p = rotateLeftRight(p);
        } else if (getBalance(p) == +2) {
            if (getBalance(p.right) >= 0)
                p = rotateLeft(p);
            else
                p = rotateRightLeft(p);
        }
        return p;
    }

    private Node<K, V> rotateRight(Node<K, V> p) {
        assert p.left != null;
        Node<K, V> q = p.left;
        q.parent = p.parent;
        p.parent = q;
        p.left = q.right;
        q.right = p;
        if (p.left != null)
            p.left.parent = p;
        p.height = Math.max(getHeight(p.left), getHeight(p.right)) + 1;
        q.height = Math.max(getHeight(q.left), getHeight(q.right)) + 1;
        return q;
    }

    private Node<K, V> rotateLeft(Node<K, V> p) {
        assert p.right != null;
        Node<K, V> q = p.right;
        q.parent = p.parent;
        p.parent = q;
        p.right = q.left;
        q.left = p;
        if (p.right != null)
            p.right.parent = p;
        p.height = Math.max(getHeight(p.left), getHeight(p.right)) + 1;
        q.height = Math.max(getHeight(q.left), getHeight(q.right)) + 1;
        return q;
    }

    private Node<K, V> rotateLeftRight(Node<K, V> p) {
        assert p.left != null;
        p.left = rotateLeft(p.left);
        return rotateRight(p);
    }

    private Node<K, V> rotateRightLeft(Node<K, V> p) {
        assert p.right != null;
        p.right = rotateRight(p.right);
        return rotateLeft(p);
    }

    private Node<K, V> getRemMinR(Node<K, V> p, MinNode<K, V> min) {
        assert p != null;
        if (p.left == null) {
            min.key = p.key;
            min.value = p.value;
            p = p.right;
        } else
            p.left = getRemMinR(p.left, min);
        p = balance(p);
        return p;
    }

}
