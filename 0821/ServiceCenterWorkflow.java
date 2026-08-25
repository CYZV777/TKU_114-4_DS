import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

class ServiceTicket {
    private String id;
    private String description;

    public ServiceTicket(String id, String description) {
        this.id = id;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "[" + id + ": " + description + "]";
    }
}

public class ServiceCenterWorkflow {
    private Map<String, ServiceTicket> ticketMap = new HashMap<>();
    private Deque<ServiceTicket> waitingQueue = new ArrayDeque<>();
    private Deque<ServiceTicket> completedStack = new ArrayDeque<>();
    private Set<String> idSet = new HashSet<>();

    public boolean createTicket(String id, String description) {
        if (idSet.contains(id)) {
            System.out.println("建立失敗: 票券編號 " + id + " 已存在！");
            return false;
        }
        ServiceTicket ticket = new ServiceTicket(id, description);
        idSet.add(id);
        ticketMap.put(id, ticket);
        waitingQueue.offerLast(ticket);
        System.out.println("成功建立票券: " + ticket);
        return true;
    }

    public void processNext() {
        if (waitingQueue.isEmpty()) {
            System.out.println("處理失敗: 目前等待 Queue 為空！");
            return;
        }
        ServiceTicket ticket = waitingQueue.pollFirst();
        completedStack.push(ticket);
        System.out.println("完成服務處理: " + ticket);
    }

    public boolean cancelWaiting(String id) {
        Iterator<ServiceTicket> iterator = waitingQueue.iterator();
        while (iterator.hasNext()) {
            ServiceTicket ticket = iterator.next();
            if (ticket.getId().equals(id)) {
                iterator.remove();
                ticketMap.remove(id);
                idSet.remove(id);
                System.out.println("已取消等待票券: " + ticket);
                return true;
            }
        }
        System.out.println("取消失敗: 等待佇列中找不到編號 " + id + "（可能不存在或已被處理）");
        return false;
    }

    public void undoLastCompletion() {
        if (completedStack.isEmpty()) {
            System.out.println("Undo 失敗: 沒有已完成的紀錄可復原！");
            return;
        }
        ServiceTicket ticket = completedStack.pop();
        waitingQueue.offerFirst(ticket);
        System.out.println("復原完成歷程 (Undo): " + ticket + " 已放回等待 Queue 最前端");
    }

    public ServiceTicket findById(String id) {
        return ticketMap.get(id);
    }

    public void printSummary() {
        System.out.println("\n===== 服務中心當前狀態 =====");
        System.out.println("總登記票券數: " + ticketMap.size());
        System.out.println("等待佇列 (" + waitingQueue.size() + "): " + waitingQueue);
        System.out.println("完成歷程 (" + completedStack.size() + "): " + completedStack);
        System.out.println("==========================\n");
    }

    public static void main(String[] args) {
        ServiceCenterWorkflow center = new ServiceCenterWorkflow();

        System.out.println("--- 測試 1: 建立票券與重複 ID 檢查 ---");
        center.createTicket("T01", "開戶業務");
        center.createTicket("T02", "申請信用卡");
        center.createTicket("T03", "外匯換匯");
        center.createTicket("T01", "重複 ID 測試");

        center.printSummary();

        System.out.println("--- 測試 2: 處理票券 (FIFO) ---");
        center.processNext();
        center.processNext();

        center.printSummary();

        System.out.println("--- 測試 3: 取消操作 ---");
        center.cancelWaiting("T99");
        center.cancelWaiting("T01");
        center.cancelWaiting("T03");

        center.printSummary();

        System.out.println("--- 測試 4: 連續兩次 Undo ---");
        center.undoLastCompletion();
        center.undoLastCompletion();
        center.undoLastCompletion();

        center.printSummary();

        System.out.println("--- 測試 5: 處理復原後的 Queue 及空 Queue 測試 ---");
        center.processNext();
        center.processNext();
        center.processNext();

        System.out.println("\n--- 測試 6: 依 ID 查詢 ---");
        System.out.println("查詢 T01: " + center.findById("T01"));
        System.out.println("查詢 T03 (已被取消): " + center.findById("T03"));
    }
}