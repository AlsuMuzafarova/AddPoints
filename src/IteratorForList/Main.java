package IteratorForList;


import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> list = new LinkedList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        Iterator<Integer> iterator = list.iterator();
        iterator.next();
        iterator.remove();
        iterator.next();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    }
}
