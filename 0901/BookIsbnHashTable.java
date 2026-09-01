public class BookIsbnHashTable {
    // 鏈結節點：存放 ISBN (key) 與書名 (title)
    private static class Node {
        String isbn;
        String title;
        Node next;

        Node(String isbn, String title) {
            this.isbn = isbn;
            this.title = title;
        }
    }

    private Node[] table;
    private int size;
    private int capacity;

    public BookIsbnHashTable() {
        this(7); // 預設質數容量
    }

    public BookIsbnHashTable(int capacity) {
        this.capacity = capacity;
        this.table = new Node[capacity];
        this.size = 0;
    }

    // 雜湊函數
    private int hash(String isbn) {
        return Math.abs(isbn.hashCode()) % capacity;
    }

    // 新增或更新 (Put / Insert or Update)
    public void put(String isbn, String title) {
        int index = hash(isbn);
        Node current = table[index];

        // 若已存在相同 ISBN，更新書名
        while (current != null) {
            if (current.isbn.equals(isbn)) {
                current.title = title;
                return;
            }
            current = current.next;
        }

        // 不存在則建立新節點並插入鏈結前端
        Node newNode = new Node(isbn, title);
        newNode.next = table[index];
        table[index] = newNode;
        size++;
    }

    // 搜尋 (Search)
    public String get(String isbn) {
        int index = hash(isbn);
        Node current = table[index];
        while (current != null) {
            if (current.isbn.equals(isbn)) {
                return current.title;
            }
            current = current.next;
        }
        return null; // 找不到回傳 null
    }

    // 刪除 (Remove)
    public boolean remove(String isbn) {
        int index = hash(isbn);
        Node current = table[index];
        Node prev = null;

        while (current != null) {
            if (current.isbn.equals(isbn)) {
                if (prev == null) {
                    table[index] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return true;
            }
            prev = current;
            current = current.next;
        }
        return false;
    }

    // 取得資料筆數
    public int size() {
        return size;
    }

    // 計算負載因子 Load Factor (size / capacity)
    public double getLoadFactor() {
        return (double) size / capacity;
    }

    // 印出 Bucket 統計報告
    public void printBucketReport() {
        System.out.println("====== Bucket 狀態報告 ======");
        System.out.printf("總筆數: %d | 總 Bucket 數: %d | Load Factor: %.2f%n", size, capacity, getLoadFactor());
        for (int i = 0; i < capacity; i++) {
            System.out.print("Bucket [" + i + "]: ");
            Node current = table[i];
            if (current == null) {
                System.out.println("(empty)");
            } else {
                while (current != null) {
                    System.out.print("[" + current.isbn + " -> " + current.title + "]");
                    if (current.next != null) System.out.print(" -> ");
                    current = current.next;
                }
                System.out.println();
            }
        }
        System.out.println("==============================");
    }

    public static void main(String[] args) {
        BookIsbnHashTable bookTable = new BookIsbnHashTable(5);
        bookTable.put("978-0134685991", "Effective Java");
        bookTable.put("978-0132350884", "Clean Code");
        bookTable.put("978-0201633610", "Design Patterns");
        bookTable.put("978-0134685991", "Effective Java (3rd Edition)"); // 測試更新

        bookTable.printBucketReport();

        System.out.println("搜尋 978-0132350884: " + bookTable.get("978-0132350884"));
        bookTable.remove("978-0132350884");
        System.out.println("刪除後再搜尋: " + bookTable.get("978-0132350884"));

        bookTable.printBucketReport();
    }
}