import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class CollectionChoiceReport {

    public static void main(String[] args) {
        System.out.println("需求 1: 保留搜尋紀錄且允許重複");
        System.out.println("  Interface: List");
        System.out.println("  Implementation: ArrayList");
        List<String> searchHistory = new ArrayList<>();
        searchHistory.add("Java 教學");
        searchHistory.add("資料結構");
        searchHistory.add("Java 教學");
        System.out.println("  操作結果 (搜尋歷史): " + searchHistory);
        System.out.println("--------------------------------------------------");

        System.out.println("需求 2: 保存不重複會員編號");
        System.out.println("  Interface: Set");
        System.out.println("  Implementation: HashSet");
        Set<String> memberIds = new HashSet<>();
        memberIds.add("M001");
        memberIds.add("M002");
        memberIds.add("M001");
        System.out.println("  操作結果 (會員編號集合): " + memberIds);
        System.out.println("--------------------------------------------------");

        System.out.println("需求 3: 以學號查詢成績");
        System.out.println("  Interface: Map");
        System.out.println("  Implementation: HashMap");
        Map<String, Integer> studentScores = new HashMap<>();
        studentScores.put("S101", 95);
        studentScores.put("S102", 88);
        System.out.println("  操作結果 (查詢 S101 成績): " + studentScores.get("S101") + " 分");
        System.out.println("--------------------------------------------------");

        System.out.println("需求 4: 依到達順序處理列印工作 (FIFO)");
        System.out.println("  Interface: Queue");
        System.out.println("  Implementation: ArrayDeque");
        Queue<String> printQueue = new ArrayDeque<>();
        printQueue.offer("文件 A.pdf");
        printQueue.offer("文件 B.docx");
        System.out.println("  操作結果 (處理列印工作): " + printQueue.poll());
        System.out.println("  剩餘列印工作: " + printQueue);
        System.out.println("--------------------------------------------------");

        System.out.println("需求 5: 復原最近操作 (LIFO)");
        System.out.println("  Interface: Deque");
        System.out.println("  Implementation: ArrayDeque");
        Deque<String> undoStack = new ArrayDeque<>();
        undoStack.push("輸入文字");
        undoStack.push("變更字體");
        System.out.println("  操作結果 (復原最近操作): " + undoStack.pop());
        System.out.println("  剩餘操作歷程: " + undoStack);
        System.out.println("--------------------------------------------------");
    }
}