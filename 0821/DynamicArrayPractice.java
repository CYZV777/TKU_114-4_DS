import java.util.Arrays;

class DynamicArray<T> {
    private Object[] data;
    private int size;

    public DynamicArray() {
        this(2);
    }

    public DynamicArray(int initialCapacity) {
        if (initialCapacity < 1) {
            initialCapacity = 1;
        }
        this.data = new Object[initialCapacity];
        this.size = 0;
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return data.length;
    }

    private void ensureCapacity() {
        if (size == data.length) {
            int newCapacity = data.length * 2;
            data = Arrays.copyOf(data, newCapacity);
            System.out.println("  [自動擴容] 容量已擴充至: " + newCapacity);
        }
    }

    public void add(T value) {
        add(size, value);
    }

    public void add(int index, T value) {
        if (index < 0 || index > size) {
            System.out.println("Add 失敗: 索引超出範圍 (index=" + index + ", size=" + size + ")");
            return;
        }
        ensureCapacity();
        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
        }
        data[index] = value;
        size++;
    }

    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (index < 0 || index >= size) {
            System.out.println("Get 失敗: 索引超出範圍 (index=" + index + ", size=" + size + ")");
            return null;
        }
        return (T) data[index];
    }

    @SuppressWarnings("unchecked")
    public T set(int index, T value) {
        if (index < 0 || index >= size) {
            System.out.println("Set 失敗: 索引超出範圍 (index=" + index + ", size=" + size + ")");
            return null;
        }
        T oldValue = (T) data[index];
        data[index] = value;
        return oldValue;
    }

    @SuppressWarnings("unchecked")
    public T remove(int index) {
        if (size == 0) {
            System.out.println("Remove 失敗: 結構為空，無法刪除！");
            return null;
        }
        if (index < 0 || index >= size) {
            System.out.println("Remove 失敗: 索引超出範圍 (index=" + index + ", size=" + size + ")");
            return null;
        }
        T removedValue = (T) data[index];
        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }
        data[size - 1] = null;
        size--;
        return removedValue;
    }

    public void printInfo() {
        System.out.print("動態陣列 [size=" + size + ", capacity=" + capacity() + "]: [");
        for (int i = 0; i < size; i++) {
            System.out.print(data[i]);
            if (i < size - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }
}

public class DynamicArrayPractice {
    public static void main(String[] args) {
        System.out.println("=== 測試 1: DynamicArray<Integer> ===");
        DynamicArray<Integer> intArray = new DynamicArray<>(2);

        intArray.remove(0);

        intArray.add(10);
        intArray.add(20);
        intArray.printInfo();
        intArray.add(30);
        intArray.printInfo();

        intArray.add(1, 15);
        intArray.printInfo();

        intArray.add(-1, 999);
        intArray.add(intArray.size() + 1, 999);

        System.out.println("get(2): " + intArray.get(2));
        System.out.println("set(2, 25): 舊值為 " + intArray.set(2, 25));
        intArray.printInfo();

        System.out.println("remove(1) 刪除元素: " + intArray.remove(1));
        intArray.printInfo();

        intArray.remove(-1);
        intArray.remove(intArray.size());

        System.out.println("\n=== 測試 2: DynamicArray<String> ===");
        DynamicArray<String> strArray = new DynamicArray<>(2);
        strArray.add("Java");
        strArray.add("Data Structure");
        strArray.add("Algorithm");
        strArray.printInfo();

        strArray.add(0, "First");
        strArray.printInfo();

        strArray.remove(strArray.size() - 1);
        strArray.printInfo();
    }
}