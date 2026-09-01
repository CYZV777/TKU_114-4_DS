public class StudentIdHashAnalysis {

    static class AnalysisResult {
        int bucketCount;
        int totalItems;
        int totalCollisions;
        int maxChainLength;
        double avgChainLength;
        int[] bucketCounts;

        public void printSummary() {
            System.out.println("========================================");
            System.out.println("Bucket 數量: " + bucketCount);
            System.out.println("資料總筆數: " + totalItems);
            System.out.println("各 Bucket 筆數: ");
            for (int i = 0; i < bucketCount; i++) {
                System.out.print("[" + i + "]:" + bucketCounts[i] + " ");
                if ((i + 1) % 5 == 0) System.out.println();
            }
            if (bucketCount % 5 != 0) System.out.println();
            System.out.println("----------------------------------------");
            System.out.println("總 Collision 次數 : " + totalCollisions);
            System.out.println("最大 Chain 長度   : " + maxChainLength);
            System.out.printf("平均 Chain 長度   : %.2f%n", avgChainLength);
            System.out.println("========================================");
        }
    }

    private static int hash(String key, int bucketCount) {
        return Math.floorMod(key.hashCode(), bucketCount);
    }

    public static AnalysisResult analyze(String[] studentIds, int bucketCount) {
        AnalysisResult result = new AnalysisResult();
        result.bucketCount = bucketCount;
        result.totalItems = (studentIds != null) ? studentIds.length : 0;
        result.bucketCounts = new int[bucketCount];

        if (studentIds != null) {
            for (String id : studentIds) {
                int idx = hash(id, bucketCount);
                result.bucketCounts[idx]++;
            }
        }

        int totalCollisions = 0;
        int maxChain = 0;
        int sumNonEmptyChains = 0;
        int nonEmptyBuckets = 0;

        for (int count : result.bucketCounts) {
            if (count > 0) {
                nonEmptyBuckets++;
                sumNonEmptyChains += count;
            }
            if (count > 1) {
                totalCollisions += (count - 1);
            }
            if (count > maxChain) {
                maxChain = count;
            }
        }

        result.totalCollisions = totalCollisions;
        result.maxChainLength = maxChain;
        result.avgChainLength = (nonEmptyBuckets == 0) ? 0.0 : (double) sumNonEmptyChains / nonEmptyBuckets;

        return result;
    }

    public static void main(String[] args) {
        String[] studentIds = {
            "41041001", "41041002", "41041003", "41041015", "41041028",
            "41041033", "41041047", "41041050", "41041062", "41041075",
            "41041088", "41041091", "41041103", "41041114", "41041120"
        };

        int bucketCount1 = 5;
        int bucketCount2 = 11;

        System.out.println("【分析一：Bucket Count = " + bucketCount1 + "】");
        AnalysisResult report1 = analyze(studentIds, bucketCount1);
        report1.printSummary();

        System.out.println("\n【分析二：Bucket Count = " + bucketCount2 + "】");
        AnalysisResult report2 = analyze(studentIds, bucketCount2);
        report2.printSummary();

        System.out.println("\n=== 比較結果 ===");
        System.out.println("1. 擴大 Bucket 數量 (5 -> 11) 使 Collision 次數由 " 
                + report1.totalCollisions + " 降為 " + report2.totalCollisions + " 次。");
        System.out.println("2. 最長 Chain 長度由 " + report1.maxChainLength + " 縮短為 " + report2.maxChainLength + "。");
    }
}