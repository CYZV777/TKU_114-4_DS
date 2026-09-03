import java.util.ArrayList;
import java.util.List;

public class Q02_MinHeapInsert {
    private final List<Integer> heap = new ArrayList<>();

    public void add(int value) {
        heap.add(value);
        bubbleUp(heap.size() - 1);
    }

    private void bubbleUp(int index) {
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

    private void swap(int i, int j) {
        int temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    public Integer peek() {
        if (heap.isEmpty()) {
            return null;
        }
        return heap.get(0);
    }

    public int size() {
        return heap.size();
    }

    public List<Integer> snapshot() {
        return new ArrayList<>(heap);
    }

    public boolean isValidMinHeap() {
        for (int i = 0; i < heap.size(); i++) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;

            if (left < heap.size() && heap.get(i) > heap.get(left)) {
                return false;
            }
            if (right < heap.size() && heap.get(i) > heap.get(right)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Q02_MinHeapInsert minHeap = new Q02_MinHeapInsert();

        System.out.println("空 Heap peek: " + minHeap.peek());

        minHeap.add(10);
        minHeap.add(4);
        minHeap.add(15);
        minHeap.add(2);
        minHeap.add(4);

        System.out.println("最小值 (peek): " + minHeap.peek());
        System.out.println("Heap 大小: " + minHeap.size());
        System.out.println("內部快照 (snapshot): " + minHeap.snapshot());
        System.out.println("是否為有效 Min-Heap: " + minHeap.isValidMinHeap());
    }
}