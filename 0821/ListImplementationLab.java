import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ListImplementationLab {

    public static void testListOperations(List<Integer> list, String listType) {
        System.out.println("=== 測試 " + listType + " ===");

        list.add(10);
        list.add(20);
        list.add(30);
        System.out.println("尾端新增 (10, 20, 30): " + list);

        list.add(1, 15);
        System.out.println("指定 index 1 插入 15: " + list);

        int target = 20;
        int foundIndex = list.indexOf(target);
        System.out.println("搜尋 " + target + ": " + (foundIndex != -1 ? "找到，索引為 " + foundIndex : "未找到"));

        int removedVal = list.remove(2);
        System.out.println("刪除 index 2 的元素 (" + removedVal + "): " + list);

        int sum = 0;
        for (int num : list) {
            sum += num;
        }
        System.out.println("計算總和: " + sum);
        System.out.println("最終內容: " + list);
        System.out.println();
    }

    public static void main(String[] args) {
        List<Integer> arrayList = new ArrayList<>();
        List<Integer> linkedList = new LinkedList<>();

        testListOperations(arrayList, "ArrayList");
        testListOperations(linkedList, "LinkedList");

        printCostAnalysis();
    }

    public static void printCostAnalysis() {
        System.out.println("=== ArrayList 與 LinkedList 內部成本差異說明 ===");
        System.out.println("1. 尾端新增 (add):");
        System.out.println("   - ArrayList: O(1) 均攤，容量不足時需重新配置陣列並複製。");
        System.out.println("   - LinkedList: 恆為 O(1)，直接配置新節點並更新 tail 指標。");
        System.out.println("2. 指定位置插入 / 刪除 (add(i, v) / remove(i)):");
        System.out.println("   - ArrayList: O(n)，需平移目標位置後續的所有元素。");
        System.out.println("   - LinkedList: O(n)，插入刪除本身為 O(1)，但需走訪 O(n) 到達指定位置。");
        System.out.println("3. 隨機存取 (get(i)):");
        System.out.println("   - ArrayList: O(1)，支援透過索引直接定址。");
        System.out.println("   - LinkedList: O(n)，需從頭/尾指標依序走訪。");
        System.out.println("4. 走訪與加總 (Traversal & Sum):");
        System.out.println("   - ArrayList: 記憶體連續，快取親和性 (Cache Locality) 佳，常數時間快。");
        System.out.println("   - LinkedList: 每個節點分散且包含指標，額外記憶體開銷大且快取效率較低。");
        System.out.println("==============================================");
    }
}