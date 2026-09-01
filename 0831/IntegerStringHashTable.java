import java.util.LinkedList;

public class IntegerStringHashTable {

    static class Entry {
        int key;
        String value;

        Entry(int key, String value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return key + "->" + value;
        }
    }

    private LinkedList<Entry>[] table;
    private int capacity;
    private int size;

    @SuppressWarnings("unchecked")
    public IntegerStringHashTable(int capacity) {
        this.capacity = capacity;
        this.size = 0;
        this.table = new LinkedList[capacity];
        for (int i = 0; i < capacity; i++) {
            this.table[i] = new LinkedList<>();
        }
    }

    public IntegerStringHashTable() {
        this(10);
    }

    private int hash(int key) {
        return Math.floorMod(key, capacity);
    }

    public int size() {
        return size;
    }

    public void put(int key, String value) {
        int idx = hash(key);
        LinkedList<Entry> bucket = table[idx];

        for (Entry entry : bucket) {
            if (entry.key == key) {
                entry.value = value;
                return;
            }
        }

        bucket.add(new Entry(key, value));
        size++;
    }

    public String get(int key) {
        int idx = hash(key);
        for (Entry entry : table[idx]) {
            if (entry.key == key) {
                return entry.value;
            }
        }
        return null;
    }

    public boolean containsKey(int key) {
        return get(key) != null;
    }

    public String remove(int key) {
        int idx = hash(key);
        LinkedList<Entry> bucket = table[idx];

        for (int i = 0; i < bucket.size(); i++) {
            Entry entry = bucket.get(i);
            if (entry.key == key) {
                bucket.remove(i);
                size--;
                return entry.value;
            }
        }
        return null;
    }

    public void bucketReport() {
        System.out.println("=== Hash Table Bucket Report (Total Size: " + size + ") ===");
        for (int i = 0; i < capacity; i++) {
            System.out.println("Bucket " + i + " (len=" + table[i].size() + "): " + table[i]);
        }
    }

    public static void main(String[] args) {
        IntegerStringHashTable ht = new IntegerStringHashTable(5);

        System.out.println("=== 測試 put 與覆蓋更新 ===");
        ht.put(1, "Alice");
        ht.put(6, "Bob");
        ht.put(11, "Charlie");
        ht.put(2, "David");
        ht.put(1, "Alice_Updated");

        ht.bucketReport();

        System.out.println("\n=== 測試 get 與 containsKey ===");
        System.out.println("get(1): " + ht.get(1));
        System.out.println("get(6): " + ht.get(6));
        System.out.println("containsKey(2): " + ht.containsKey(2));
        System.out.println("containsKey(99): " + ht.containsKey(99));

        System.out.println("\n=== 測試 remove ===");
        System.out.println("remove(6) 回傳: " + ht.remove(6));
        System.out.println("remove(99) 不存在: " + ht.remove(99));

        ht.bucketReport();
    }
}