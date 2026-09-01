import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class TopSellingProducts {

    static class Product {
        String id;
        int sales;

        public Product(String id, int sales) {
            this.id = id;
            this.sales = sales;
        }

        @Override
        public String toString() {
            return id + "(" + sales + ")";
        }
    }

    public static List<Product> getTopKProducts(List<Product> inputList, int k) {
        if (k <= 0 || inputList == null || inputList.isEmpty()) {
            return new ArrayList<>();
        }

        Map<String, Integer> salesMap = new HashMap<>();
        for (Product p : inputList) {
            if (p != null && p.id != null) {
                salesMap.put(p.id, salesMap.getOrDefault(p.id, 0) + p.sales);
            }
        }

        PriorityQueue<Product> minHeap = new PriorityQueue<>((a, b) -> {
            if (a.sales != b.sales) {
                return Integer.compare(a.sales, b.sales);
            }
            return b.id.compareTo(a.id);
        });

        for (Map.Entry<String, Integer> entry : salesMap.entrySet()) {
            Product current = new Product(entry.getKey(), entry.getValue());
            if (minHeap.size() < k) {
                minHeap.offer(current);
            } else {
                Product top = minHeap.peek();
                boolean shouldReplace = false;

                if (current.sales > top.sales) {
                    shouldReplace = true;
                } else if (current.sales == top.sales && current.id.compareTo(top.id) < 0) {
                    shouldReplace = true;
                }

                if (shouldReplace) {
                    minHeap.poll();
                    minHeap.offer(current);
                }
            }
        }

        List<Product> result = new ArrayList<>(minHeap);
        result.sort((a, b) -> {
            if (a.sales != b.sales) {
                return Integer.compare(b.sales, a.sales);
            }
            return a.id.compareTo(b.id);
        });

        return result;
    }

    public static void main(String[] args) {
        List<Product> salesRecords = new ArrayList<>();
        salesRecords.add(new Product("itemB", 50));
        salesRecords.add(new Product("itemA", 30));
        salesRecords.add(new Product("itemC", 80));
        salesRecords.add(new Product("itemA", 70));
        salesRecords.add(new Product("itemD", 50));
        salesRecords.add(new Product("itemE", 10));

        int k = 3;
        System.out.println("=== Top-" + k + " 熱門商品測試 ===");
        List<Product> topK = getTopKProducts(salesRecords, k);
        System.out.println("結果: " + topK);
    }
}