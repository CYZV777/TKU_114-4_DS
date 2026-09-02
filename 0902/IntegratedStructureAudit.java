import java.util.*;

public class IntegratedStructureAudit {

    static class AuditCase {
        String caseId;
        String scenario;
        String chosenDS;

        public AuditCase(String caseId, String scenario, String chosenDS) {
            this.caseId = caseId;
            this.scenario = scenario;
            this.chosenDS = chosenDS;
        }
    }

    static class AuditResult {
        String caseId;
        boolean isAppropriate;
        String recommendedDS;
        String diagnosticReason;

        public void print() {
            System.out.printf("[%s] 使用結構: %-12s | 審核結果: %s%n", 
                caseId, (recommendedDS.equals(diagnosticReason) ? "" : ""), isAppropriate ? "PASS (合理)" : "FAIL (不合理)");
            if (!isAppropriate) {
                System.out.println("   -> 建議替換為: " + recommendedDS);
            }
            System.out.println("   -> 診斷分析: " + diagnosticReason);
            System.out.println();
        }
    }

    public static AuditResult audit(AuditCase testCase) {
        if (testCase == null || testCase.scenario == null || testCase.chosenDS == null) {
            AuditResult r = new AuditResult();
            r.caseId = "UNKNOWN";
            r.isAppropriate = false;
            r.recommendedDS = "None";
            r.diagnosticReason = "[邊界異常] 傳入空案例或未定義需求。";
            return r;
        }

        AuditResult res = new AuditResult();
        res.caseId = testCase.caseId;
        String s = testCase.scenario.toLowerCase();
        String c = testCase.chosenDS.toLowerCase();

        if (s.contains("fifo") || s.contains("排隊") || s.contains("先到先服務")) {
            check(res, c.contains("queue"), "Queue", "先進先出情境應使用佇列，提供 O(1) 頭尾排程。");
        } else if (s.contains("優先權") || s.contains("動態極值") || s.contains("排程")) {
            check(res, c.contains("heap") || c.contains("priorityqueue"), "Heap", "隨時需要最高優先級者，應使用堆積在 O(1) 取得極值。");
        } else if (s.contains("快速查詢") || s.contains("key-value") || s.contains("id查找")) {
            check(res, c.contains("hash"), "Hash Table", "ID 快速查找應依賴雜湊表，具有平均 O(1) 搜尋時間。");
        } else if (s.contains("依賴關係") || s.contains("路徑") || s.contains("網絡") || s.contains("先修")) {
            check(res, c.contains("graph"), "Graph", "多對多關聯與連通性搜尋，必須使用圖結構表達點與邊。");
        } else if (s.contains("區間搜尋") || s.contains("排序維持")) {
            check(res, c.contains("bst") || c.contains("tree"), "BST", "動態維持全序關係或範圍查詢，應使用平衡二元搜尋樹。");
        } else if (s.contains("線性存取") || s.contains("下標存取")) {
            check(res, c.contains("list"), "List", "純線性紀錄或隨機讀取適用 List。");
        } else {
            res.isAppropriate = false;
            res.recommendedDS = "Unknown";
            res.diagnosticReason = "情境描述不足以比對標準結構。";
        }
        return res;
    }

    private static void check(AuditResult res, boolean match, String standard, String reason) {
        res.isAppropriate = match;
        res.recommendedDS = standard;
        res.diagnosticReason = reason;
    }

    public static void main(String[] args) {
        List<AuditCase> cases = Arrays.asList(
            new AuditCase("C01", "印表機工作排隊 (先到先服務 FIFO)", "List"),               // 誤用
            new AuditCase("C02", "VIP 顧客急件處理 (動態取得最高優先權)", "PriorityQueue (Heap)"), // 合理
            new AuditCase("C03", "學生學號 ID 快速查詢基本資料", "Hash Table"),               // 合理
            new AuditCase("C04", "捷運路線網絡轉乘與路徑規劃", "BST")                         // 誤用
        );

        System.out.println("=== 結構選擇診斷審核報告 ===");
        for (AuditCase ac : cases) {
            audit(ac).print();
        }

        System.out.println("=== 邊界案例審查 ===");
        audit(new AuditCase("EMPTY_CASE", null, null)).print();
    }
}