import java.util.Arrays;
import java.util.List;

public class HeapPropertyValidator {

    public static boolean isMinHeap(List<Integer> list) {
        if (list == null) return false;
        if (list.size() <= 1) return true;

        int n = list.size();
        for (int i = 0; i <= (n - 2) / 2; i++) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;

            if (left < n && list.get(i) > list.get(left)) {
                return false;
            }
            if (right < n && list.get(i) > list.get(right)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isMaxHeap(List<Integer> list) {
        if (list == null) return false;
        if (list.size() <= 1) return true;

        int n = list.size();
        for (int i = 0; i <= (n - 2) / 2; i++) {
            int left = 2 * i + 1;
            int right = 2 * i + 2;

            if (left < n && list.get(i) < list.get(left)) {
                return false;
            }
            if (right < n && list.get(i) < list.get(right)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        List<Integer> minHeapData = Arrays.asList(10, 20, 15, 30, 40);
        List<Integer> maxHeapData = Arrays.asList(50, 40, 30, 25, 10);
        List<Integer> invalidData = Arrays.asList(10, 50, 5, 20);

        System.out.println("=== Min Heap 驗證 ===");
        System.out.println("minHeapData: " + isMinHeap(minHeapData));
        System.out.println("invalidData: " + isMinHeap(invalidData));
        System.out.println("null 測試: " + isMinHeap(null));
        System.out.println("empty 測試: " + isMinHeap(List.of()));
        System.out.println("\n=== Max Heap 驗證 ===");
        System.out.println("maxHeapData: " + isMaxHeap(maxHeapData));
        System.out.println("invalidData: " + isMaxHeap(invalidData));
    }
}