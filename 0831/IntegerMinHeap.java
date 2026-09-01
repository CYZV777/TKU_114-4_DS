import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class IntegerMinHeap {
    private List<Integer> heap;

    public IntegerMinHeap() {
        this.heap = new ArrayList<>();
    }

    public int size() {
        return heap.size();
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    public int peek() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }
        return heap.get(0);
    }

    public void add(int val) {
        heap.add(val);
        siftUp(heap.size() - 1);
    }

    public int removeMin() {
        if (isEmpty()) {
            throw new NoSuchElementException("Heap is empty");
        }

        int minVal = heap.get(0);
        int lastVal = heap.remove(heap.size() - 1);

        if (!heap.isEmpty()) {
            heap.set(0, lastVal);
            siftDown(0);
        }

        return minVal;
    }

    private void siftUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (heap.get(index) < heap.get(parentIndex)) {
                swap(index, parentIndex);
                index = parentIndex;
            } else {
                break;
            }
        }
    }

    private void siftDown(int index) {
        int size = heap.size();
        while (true) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int smallest = index;

            if (left < size && heap.get(left) < heap.get(smallest)) {
                smallest = left;
            }
            if (right < size && heap.get(right) < heap.get(smallest)) {
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
        int temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    public static void main(String[] args) {
        IntegerMinHeap minHeap = new IntegerMinHeap();
        int[] data = {40, 10, 30, 50, 20, 10, 60};

        System.out.println("=== 測試加入資料 ===");
        for (int num : data) {
            minHeap.add(num);
        }
        System.out.println("目前元素數量 (size): " + minHeap.size());
        System.out.println("目前最小值 (peek): " + minHeap.peek());

        System.out.println("\n=== 依序移除最小值（驗證非遞減順序）===");
        int prev = Integer.MIN_VALUE;
        boolean isNonDecreasing = true;

        while (!minHeap.isEmpty()) {
            int current = minHeap.removeMin();
            System.out.print(current + " ");

            if (current < prev) {
                isNonDecreasing = false;
            }
            prev = current;
        }
        System.out.println();
        System.out.println("移除順序驗證是否為非遞減: " + isNonDecreasing);

        System.out.println("\n=== 測試空 Heap 拋出 NoSuchElementException ===");
        try {
            minHeap.peek();
        } catch (NoSuchElementException e) {
            System.out.println("成功捕捉 peek() 異常: " + e.getMessage());
        }

        try {
            minHeap.removeMin();
        } catch (NoSuchElementException e) {
            System.out.println("成功捕捉 removeMin() 異常: " + e.getMessage());
        }
    }
}