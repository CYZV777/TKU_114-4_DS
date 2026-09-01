import java.util.Arrays;
import java.util.NoSuchElementException;

public class ArrayMinHeap {
    private int[] data;
    private int size;
    private static final int DEFAULT_CAPACITY = 4;

    public ArrayMinHeap() {
        this.data = new int[DEFAULT_CAPACITY];
        this.size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void add(int val) {
        if (size == data.length) {
            resize();
        }
        data[size] = val;
        siftUp(size);
        size++;
    }

    public int peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }
        return data[0];
    }

    public int remove() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }
        int minVal = data[0];
        data[0] = data[size - 1];
        size--;
        if (size > 0) {
            siftDown(0);
        }
        return minVal;
    }

    public void snapshot() {
        int[] currentElements = Arrays.copyOf(data, size);
        System.out.println("Size: " + size + ", Capacity: " + data.length + " -> " + Arrays.toString(currentElements));
    }

    private void resize() {
        int[] newData = new int[data.length * 2];
        System.arraycopy(data, 0, newData, 0, data.length);
        data = newData;
    }

    private void siftUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (data[index] < data[parent]) {
                swap(index, parent);
                index = parent;
            } else {
                break;
            }
        }
    }

    private void siftDown(int index) {
        while (true) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int smallest = index;

            if (left < size && data[left] < data[smallest]) {
                smallest = left;
            }
            if (right < size && data[right] < data[smallest]) {
                smallest = right;
            }

            if (smallest != index) {
                swap(index, smallest);
                index = smallest;
            } else {
                break;
            }
        }
    }

    private void swap(int i, int j) {
        int temp = data[i];
        data[i] = data[j];
        data[j] = temp;
    }

    public static void main(String[] args) {
        ArrayMinHeap minHeap = new ArrayMinHeap();
        int[] testData = {45, 12, 89, 34, 7, 23, 56, 91, 3, 67, 18, 5, 82, 29, 14, 77, 61, 2, 49, 38};

        System.out.println("=== 測試加入 20 筆資料與動態擴容 ===");
        for (int val : testData) {
            minHeap.add(val);
            minHeap.snapshot();
        }

        System.out.println("\n目前最小值 (peek): " + minHeap.peek());

        System.out.println("\n=== 依序移除所有元素（非遞減驗證）===");
        while (!minHeap.isEmpty()) {
            System.out.print(minHeap.remove() + " ");
        }
        System.out.println();
    }
}