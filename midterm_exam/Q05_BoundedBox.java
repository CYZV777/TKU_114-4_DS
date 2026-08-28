import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Q05_BoundedBox<T extends Comparable<T>> {
    private final int capacity;
    private final List<T> items;

    public Q05_BoundedBox(int capacity) {
        if (capacity < 1) {
            throw new IllegalArgumentException("Capacity must be >= 1");
        }
        this.capacity = capacity;
        this.items = new ArrayList<>();
    }

    public boolean add(T value) {
        if (value == null || isFull()) {
            return false;
        }
        items.add(value);
        return true;
    }

    public int size() {
        return items.size();
    }

    public boolean isFull() {
        return items.size() >= capacity;
    }

    public T minimum() {
        if (items.isEmpty()) return null;
        T min = items.get(0);
        for (T item : items) {
            if (item.compareTo(min) < 0) {
                min = item;
            }
        }
        return min;
    }

    public T maximum() {
        if (items.isEmpty()) return null;
        T max = items.get(0);
        for (T item : items) {
            if (item.compareTo(max) > 0) {
                max = item;
            }
        }
        return max;
    }

    public int countGreaterThan(T threshold) {
        if (threshold == null) return 0;
        int count = 0;
        for (T item : items) {
            if (item.compareTo(threshold) > 0) {
                count++;
            }
        }
        return count;
    }

    public List<T> snapshot() {
        return new ArrayList<>(items);
    }

    public static void main(String[] args) {
        Q05_BoundedBox<Integer> box = new Q05_BoundedBox<>(3);
        System.out.println(box.add(40));
        System.out.println(box.add(10));
        System.out.println(box.add(30));
        System.out.println(box.add(20));
        System.out.println(box.minimum());
        System.out.println(box.maximum());
        System.out.println(box.countGreaterThan(25));
        System.out.println(box.snapshot());
    }
}