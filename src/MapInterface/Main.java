package MapInterface;

public class Main {
    public static void main(String[] args) {
        MapInf304<Integer, Integer> map = new MyMap<>(4);
        map.put(33, 4);
        map.put(34, 6);
        map.put(2, 5);
        map.put(1, 6);
        System.out.println(map.keySet());
        System.out.println(map.containsKey(2));
        System.out.println(map.containsKey(3));
        System.out.println(map.get(1));
        System.out.println(map.remove(33));
        System.out.println(map.keySet());
        System.out.println(map.values());
    }
}
