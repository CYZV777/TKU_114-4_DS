import java.util.ArrayList;
import java.util.List;

public class CollisionBucketReport {

    private static int hash(int key, int bucketCount) {
        return Math.floorMod(key, bucketCount);
    }

    public static void generateReport(int[] keys, int bucketCount) {
        if (bucketCount <= 0) {
            System.out.println("Bucket 數量必須大於 0");
            return;
        }

        List<List<Integer>> buckets = new ArrayList<>(bucketCount);
        for (int i = 0; i < bucketCount; i++) {
            buckets.add(new ArrayList<>());
        }

        if (keys != null) {
            for (int key : keys) {
                int index = hash(key, bucketCount);
                buckets.get(index).add(key);
            }
        }

        int totalCollisions = 0;
        int maxChainLength = 0;

        System.out.println("=== Bucket 分佈清單 ===");
        for (int i = 0; i < bucketCount; i++) {
            List<Integer> bucket = buckets.get(i);
            int size = bucket.size();

            System.out.println("Bucket " + i + " (size: " + size + "): " + bucket);

            if (size > 1) {
                totalCollisions += (size - 1);
            }
            if (size > maxChainLength) {
                maxChainLength = size;
            }
        }

        System.out.println("\n=== 統計報告 ===");
        System.out.println("總 Collision 數量: " + totalCollisions);
        System.out.println("最長 Chain 長度: " + maxChainLength);
    }

    public static void main(String[] args) {
        int bucketCount = 5;
        int[] testKeys = {10, -3, 15, 22, -8, 10, 7, 0, -5};

        System.out.println("--- 測試正常資料 ---");
        generateReport(testKeys, bucketCount);

        System.out.println("\n--- 測試空輸入 (null) ---");
        generateReport(null, bucketCount);
    }
}