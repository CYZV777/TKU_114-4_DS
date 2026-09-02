import java.util.*;

public class DataStructureDecisionReport {

    public static class Decision {
        int id;
        String requirement;
        String choice;
        String reason;
        String bigO;

        public Decision(int id, String requirement, String choice, String reason, String bigO) {
            this.id = id;
            this.requirement = requirement;
            this.choice = choice;
            this.reason = reason;
            this.bigO = bigO;
        }

        @Override
        public String toString() {
            return String.format("[%02d] 需求: %s%n     -> 選擇: %s%n     -> 理由: %s%n     -> 主要 Big-O: %s%n",
                    id, requirement, choice, reason, bigO);
        }
    }

    public static List<Decision> generateReport(List<String> customQueries) {
        List<Decision> list = new ArrayList<>();
        if (customQueries == null || customQueries.isEmpty()) {
            return list;
        }

        Map<String, Decision> db = new LinkedHashMap<>();
        db.put("1", new Decision(1, "高頻隨機存取 (Random Access)", "動態陣列 (ArrayList)", "連續記憶體位址支援常數時間下標讀取", "O(1) 讀取"));
        db.put("2", new Decision(2, "頻繁在頭尾進行插入/刪除", "雙向鏈結串列 (LinkedList / ArrayDeque)", "指標重整即可完成插入刪除，不需搬移元素", "O(1) 兩端操作"));
        db.put("3", new Decision(3, "先到先服務排隊機制 (FIFO)", "佇列 (Queue / ArrayDeque)", "符合先進先出排程語意", "O(1) enqueue / dequeue"));
        db.put("4", new Decision(4, "瀏覽器回上一頁 / 函式呼叫堆疊 (LIFO)", "堆疊 (Stack / ArrayDeque)", "符合後進先出機制", "O(1) push / pop"));
        db.put("5", new Decision(5, "根據唯一鍵 (Key) 快速查找", "雜湊表 (HashMap)", "Hash 雜湊定址，提供平均常數時間查找", "O(1) 平均查找"));
        db.put("6", new Decision(6, "資料需動態維持排序狀態", "紅黑平衡二元搜尋樹 (TreeMap / TreeSet)", "平衡樹結構在插入後自動重平衡維持順序", "O(log N) 搜尋/插入"));
        db.put("7", new Decision(7, "優先權排程 / 動態取最大值或最小值", "二元堆積 (PriorityQueue / Binary Heap)", "Heap 性質可保證根節點永遠為極值", "O(1) peek, O(log N) poll"));
        db.put("8", new Decision(8, "稀疏圖形 (Sparse Graph) 鄰居存取", "鄰接表 (Adjacency List)", "空間與點邊總數成正比，走訪不浪費空間", "O(V + E) 空間/走訪"));
        db.put("9", new Decision(9, "高密度圖 (Dense Graph) 快速查兩點有無相連", "鄰接矩陣 (Adjacency Matrix)", "二維陣列下標直接比對", "O(1) 邊查詢"));
        db.put("10", new Decision(10, "前綴字串搜尋 / 自動補全 (Autocomplete)", "字典樹 (Trie)", "共用共同前綴，搜尋時間僅與字串長度相關", "O(L) L 為字串長度"));
        db.put("11", new Decision(11, "動態集合合併與連通性判斷 (Cycle Detection)", "並查集 (Union-Find / Disjoint Set)", "配合路徑壓縮與按秩合併，幾乎等同常數時間", "O(α(N)) 反阿克曼函數"));
        db.put("12", new Decision(12, "百萬大數據快速去重 / 集合成員測試 (容許誤判)", "布隆過濾器 (Bloom Filter)", "利用 BitMap 與多組 Hash，極省記憶體", "O(k) k 為 Hash 數量"));

        for (String q : customQueries) {
            if (db.containsKey(q)) {
                list.add(db.get(q));
            }
        }
        return list;
    }

    public static void main(String[] args) {
        System.out.println("=== 12 組資料結構選擇決策報告 ===");
        List<String> all12 = Arrays.asList("1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12");
        List<Decision> report = generateReport(all12);
        for (Decision d : report) {
            System.out.print(d);
        }

        System.out.println("=== 邊界案例測試 ===");
        System.out.println("查詢無效編號 [99]: " + generateReport(Collections.singletonList("99")));
        System.out.println("查詢空清單 []: " + generateReport(Collections.emptyList()));
        System.out.println("傳入 null: " + generateReport(null));
    }
}