import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Q04_ChainedHashTable {

    private static class Entry {
        int key;
        String value;

        Entry(int key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    private final List<List<Entry>> table;
    private final int bucketCount;
    private int size;

    public Q04_ChainedHashTable(int bucketCount) {
        if (bucketCount <= 0) {
            throw new IllegalArgumentException("bucketCount must be greater than 0");
        }
        this.bucketCount = bucketCount;
        this.table = new ArrayList<>(bucketCount);
        for (int i = 0; i < bucketCount; i++) {
            table.add(new LinkedList<>());
        }
        this.size = 0;
    }

    private int hash(int key) {
        return Math.floorMod(key, bucketCount);
    }

    public void put(int key, String value) {
        int index = hash(key);
        List<Entry> chain = table.get(index);

        for (Entry entry : chain) {
            if (entry.key == key) {
                entry.value = value;
                return;
            }
        }

        chain.add(new Entry(key, value));
        size++;
    }

    public String get(int key) {
        int index = hash(key);
        List<Entry> chain = table.get(index);
        for (Entry entry : chain) {
            if (entry.key == key) {
                return entry.value;
            }
        }
        return null;
    }

    public boolean remove(int key) {
        int index = hash(key);
        List<Entry> chain = table.get(index);
        for (int i = 0; i < chain.size(); i++) {
            if (chain.get(i).key == key) {
                chain.remove(i);
                size--;
                return true;
            }
        }
        return false;
    }

    public int size() {
        return size;
    }

    public int longestChain() {
        int maxLen = 0;
        for (List<Entry> chain : table) {
            if (chain.size() > maxLen) {
                maxLen = chain.size();
            }
        }
        return maxLen;
    }

    public static void main(String[] args) {
        Q04_ChainedHashTable ht = new Q04_ChainedHashTable(5);

        ht.put(-1, "NegativeOne");
        ht.put(4, "Four");
        ht.put(9, "Nine");

        System.out.println("查詢 key 4: " + ht.get(4));
        System.out.println("查詢 key -1: " + ht.get(-1));
        System.out.println("最長鏈結長度: " + ht.longestChain());
        System.out.println("目前總筆數: " + ht.size());

        ht.put(4, "UpdatedFour");
        System.out.println("更新後 key 4: " + ht.get(4));
        System.out.println("更新後總筆數: " + ht.size());

        // 測試刪除
        boolean removed = ht.remove(4);
        System.out.println("刪除 key 4: " + removed);
        System.out.println("刪除後查詢 key 4: " + ht.get(4));
        System.out.println("刪除後最長鏈結長度: " + ht.longestChain());
    }
}