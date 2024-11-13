package MapInterface;

import java.util.*;

public class MyMap<K, V> implements MapInf304<K, V>{
    private int hash(K key) {
        return Math.abs(key.hashCode()) % capacity;
    }
    private static class Entry<K, V>{
        private K key;
        private V value;
        Entry<K, V> next;
        public Entry(K key, V value){
            this.key = key;
            this.value = value;
        }
    }
    private Entry<K, V>[] table;
    private int size;
    private int capacity;
    public MyMap(int capacity){
        this.capacity = capacity;
        this.table = new Entry[capacity];
        this.size = 0;
    }
    @Override
    public V put(K key, V value) {
        int index = hash(key);
        Entry<K, V> current = table[index];
        while (current != null) {
            if (current.key.equals(key)) {
                current.value = value;
                return current.value;
            }
            current = current.next;
        }
        Entry<K, V> newEntry = new Entry<>(key, value);
        newEntry.next = table[index];
        table[index] = newEntry;
        size++;
        return newEntry.value;
    }

    @Override
    public V get(K key) {
        int index = hash(key);
        Entry<K, V> current = table[index];
        while(current != null){
            if(current.key.equals(key)){
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    @Override
    public V remove(K key) {
        int index = hash(key);
        Entry<K, V> current = table[index];
        Entry<K, V> previous = null;
        while (current != null) {
            if (current.key.equals(key)) {
                if (previous == null) {
                    table[index] = current.next;
                } else {
                    previous.next = current.next;
                }
                size--;
                return current.value;
            }
            previous = current;
            current = current.next;
        }
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        int index = hash(key);
        if(table[index] != null){
            Entry<K, V> current = table[index];
            while(current != null){
                if(current.key.equals(key)){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public Collection<V> values() {
        ArrayList<V> values = new ArrayList<>();
        for(Entry<K, V> entry : table){
            while (entry != null){
                values.add(entry.value);
                entry = entry.next;
            }
        }
        return values;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keySet = new HashSet<>();
        for (Entry<K, V> entry : table) {
            while (entry != null) {
                keySet.add(entry.key);
                entry = entry.next;
            }
        }
        return keySet;
    }
}
