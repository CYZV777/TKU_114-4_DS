import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class LowestKPriceTracker {

    public static List<Integer> getLowestKPrices(Integer[] prices, int k) {
        if (k <= 0 || prices == null) {
            return new ArrayList<>();
        }

        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

        for (Integer price : prices) {
            if (price == null || price < 0) {
                continue;
            }

            if (maxHeap.size() < k) {
                maxHeap.offer(price);
            } else if (price < maxHeap.peek()) {
                maxHeap.poll();
                maxHeap.offer(price);
            }
        }

        List<Integer> result = new ArrayList<>();
        while (!maxHeap.isEmpty()) {
            result.add(maxHeap.poll());
        }
        Collections.reverse(result);

        return result;
    }

    public static void main(String[] args) {
        Integer[] priceList = {50, null, 10, -5, 30, 20, 15, null, 40, -1, 5};
        int k = 4;

        System.out.println("=== Top-" + k + " 最低價格測試 ===");
        List<Integer> result = getLowestKPrices(priceList, k);
        System.out.println("最低 " + k + " 個價格（遞增）: " + result);

        System.out.println("k <= 0 測試: " + getLowestKPrices(priceList, 0));
    }
}