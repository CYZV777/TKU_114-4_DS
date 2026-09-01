import java.util.ArrayList;
import java.util.List;

public class MaxHeapInsertTrace {
    private List<Integer> heap;

    public MaxHeapInsertTrace() {
        this.heap = new ArrayList<>();
    }

    public void add(int val) {
        heap.add(val);
        siftUp(heap.size() - 1);
    }

    public int peekMax() {
        if (heap.isEmpty()) {
            throw new IllegalStateException("Heap is empty");
        }
        return heap.get(0);
    }

    public void snapshot() {
        System.out.println(heap.toString());
    }

    private void siftUp(int index) {
        while (index > 0) {
            int parentIndex = (index - 1) / 2;
            if (heap.get(index) > heap.get(parentIndex)) {
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

    public static void main(String[] args) {
        MaxHeapInsertTrace maxHeap = new MaxHeapInsertTrace();
        int[] testData = {25, 40, 10, 50, 30, 50};

        System.out.println("=== 開始插入測試資料並追蹤狀態 ===");
        for (int num : testData) {
            System.out.print("加入 " + num + " -> ");
            maxHeap.add(num);
            maxHeap.snapshot();
        }

        System.out.println("\n完成時 Root (peekMax): " + maxHeap.peekMax());
    }
}