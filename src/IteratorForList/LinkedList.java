package IteratorForList;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList<T> {

    private Node<T> head;
    private int size;
    private int modCount;

    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    public LinkedList() {
        this.head = null;
        this.size = 0;
    }

    public void addFirst(T data) {
        Node<T> newNode = new Node<>(data);
        newNode.next = head;
        head = newNode;
        size++;
        modCount++;
    }

    public void addLast(T data) {
        if (head == null) {
            addFirst(data);
            return;
        }
        Node<T> current = head;
        while (current.next != null) {
            current = current.next;
        }
        current.next = new Node<>(data);
        size++;
        modCount++;
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    public T removeFirst() {
        if (head == null) {
            return null;
        }
        T removedData = head.data;
        head = head.next;
        size--;
        modCount++;
        return removedData;
    }

    public T removeLast() {
        if (head == null) {
            return null;
        }
        if (head.next == null) {
            return removeFirst();
        }
        Node<T> current = head;
        while (current.next.next != null) {
            current = current.next;
        }
        T removedData = current.next.data;
        current.next = null;
        size--;
        modCount++;
        return removedData;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public int size() {
        return size;
    }

    public class linkedListIterator implements Iterator<T> {

        private Node<T> current;
        private Node<T> prev;
        private int expectedModCount;

        public linkedListIterator() {
            this.current = head;
            this.prev = null;
            this.expectedModCount = modCount;
        }

        @Override
        public boolean hasNext() {
            return current.next != null;
        }

        @Override
        public T next() {
            if (expectedModCount == modCount) {
                if (hasNext()){
                    current = current.next;
                    prev = current;
                    return current.data;
                }
                else{
                    throw new NoSuchElementException();
                }
            }
            else {
                throw new ConcurrentModificationException();
            }
        }

        @Override
        public void remove() {
            if (expectedModCount != modCount) {
                throw new ConcurrentModificationException();
            }
            else{
                if (prev != null && prev.next != null){
                    prev.next = current;
                }
                else {
                    head = current.next;
                }
                expectedModCount++;
                modCount++;
            }
        }
        public linkedListIterator iterator() {
            return new linkedListIterator();
        }
    }
}
