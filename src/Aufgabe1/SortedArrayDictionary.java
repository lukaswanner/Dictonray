package Aufgabe1;

public class SortedArrayDictionary<K extends Comparable<? super K>, V> implements Dictionary<K, V> {

    private static final int DEF_CAPACITY = 16;
    private int size;
    private Entry<K, V>[] data;

    public SortedArrayDictionary() {
        size = 0;
        data = new Entry[DEF_CAPACITY];
    }

    public void ensureCapacity(int newCapacity) {
        if (newCapacity < size) {
            return;
        }
        Entry[] old = data;
        data = new Entry[newCapacity];
        System.arraycopy(old, 0, data, 0, size);
    }

    private int searchKey(K key) {
        int li = 0;
        int re = size - 1;

        while (re >= li) {
            int m = (li + re) / 2;
            if (key.compareTo(data[m].key) < 0) {
                re = m - 1;
            } else if (key.compareTo(data[m].key) > 0) {
                li = m + 1;
            } else {
                return m;
            }
        }
        return -1;
    }

    //most simple search con : takes too long with big dataset
    private int simplesearchKey(K key) {
        for (int i = 0; i < size; i++) {
            if (data[i].key.equals(key)) {
                return i;
            }
        }
        return -1; //not found
    }

    @Override
    public V search(K key) {
        int i = searchKey(key);
        if (i >= 0) {
            return data[i].value;
        } else {
            return null;
        }
    }

    @Override
    public V insert(K key, V value) {
        int i = searchKey(key);
        //if key is already in the data set
        if (i >= 0) {
            V oldvalue = data[i].value;
            data[i].value = value;
            return oldvalue;
        }
        //if key isn't already in the data set
        if (data.length == size) {
            ensureCapacity(2 * size);
        }
        int j = size - 1;
        //shift everything starting from the back one position forward
        while (j >= 0 && key.compareTo(data[j].key) < 0) {
            data[j + 1] = data[j];
            j--;
        }
        //insert new key at the created space for it
        data[j + 1] = new Entry<K, V>(key, value);
        size++;
        return null;

    }


    @Override
    public V remove(K key) {
        int i = searchKey(key);
        if (i == -1)
            return null;
        V r = data[i].value;
        for (int j = i; j < size - 1; j++)
            data[j] = data[j + 1];
        data[--size] = null;
        return r;

    }
}
