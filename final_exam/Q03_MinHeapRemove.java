import java.util.ArrayList;
import java.util.List;

public class Q03_MinHeapRemove {
    private final List<Integer> heap = new ArrayList<>();

    public Q03_MinHeapRemove(List<Integer> values) {
        if (values != null) {
            for (Integer v : values) {
                if (v != null) {
                    heap.add(v);
                }
            }
            for (int i = (heap.size() / 2) - 1; i >= 0; i--) {
                bubbleDown(i);
            }
        }
    }

    public Integer removeMin() {
        if (heap.isEmpty()) {
            return null;
        }

        int minVal = heap.get(0);
        int lastVal = heap.remove(heap.size() - 1); 

        if (!heap.isEmpty()) {
            heap.set(0, lastVal);
            bubbleDown(0);
        }

        return minVal;
    }

    private void bubbleDown(int index) {
        int size = heap.size();
        while (index < size) {
            int smallest = index;
            int left = 2 * index + 1;
            int right = 2 * index + 2;

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

    public static void main(String[] args) {
        List<Integer> initialData = new ArrayList<>();
        initialData.add(9);
        initialData.add(null);
        initialData.add(4);
        initialData.add(7);
        initialData.add(1);

        Q03_MinHeapRemove minHeap = new Q03_MinHeapRemove(initialData);
        System.out.println("Heapify 後快照: " + minHeap.snapshot());
        System.out.println("peek 最小值: " + minHeap.peek());

        System.out.println("取出: " + minHeap.removeMin());
        System.out.println("取出後快照: " + minHeap.snapshot());

        System.out.println("取出: " + minHeap.removeMin());
        System.out.println("取出: " + minHeap.removeMin());
        System.out.println("取出: " + minHeap.removeMin());
        System.out.println("空 Heap 取出: " + minHeap.removeMin());
    }
}