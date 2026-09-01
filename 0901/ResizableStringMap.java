public class ResizableStringMap {
    private static class Node {
        String key;
        String value;
        Node next;

        Node(String key, String value) {
            this.key = key;
            this.value = value;
        }
    }

    private Node[] table;
    private int size;
    private static final double LOAD_FACTOR_THRESHOLD = 0.75;

    public ResizableStringMap() {
        this(5);
    }

    public ResizableStringMap(int initialCapacity) {
        table = new Node[initialCapacity];
        size = 0;
    }

    private int hash(String key) {
        return Math.abs(key.hashCode()) % table.length;
    }

    public void put(String key, String value) {
        int index = hash(key);
        Node current = table[index];

        while (current != null) {
            if (current.key.equals(key)) {
                current.value = value;
                return;
            }
            current = current.next;
        }

        Node newNode = new Node(key, value);
        newNode.next = table[index];
        table[index] = newNode;
        size++;

        if ((double) size / table.length > LOAD_FACTOR_THRESHOLD) {
            resize();
        }
    }

    public String get(String key) {
        int index = hash(key);
        Node current = table[index];
        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }

    private void resize() {
        Node[] oldTable = table;
        int newCapacity = oldTable.length * 2 + 1;
        table = new Node[newCapacity];
        size = 0;

        for (Node head : oldTable) {
            Node current = head;
            while (current != null) {
                put(current.key, current.value);
                current = current.next;
            }
        }
    }

    public int size() {
        return size;
    }

    public static void main(String[] args) {
        ResizableStringMap map = new ResizableStringMap(3);
        map.put("A", "Alpha");
        map.put("B", "Beta");
        map.put("C", "Gamma");
        map.put("D", "Delta");

        System.out.println("Get A: " + map.get("A"));
        System.out.println("Get C: " + map.get("C"));
        System.out.println("Total size: " + map.size());
    }
}