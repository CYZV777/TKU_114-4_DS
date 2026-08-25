import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

class DeliveryOrder {
    private String id;
    private String address;
    public DeliveryOrder(String id, String address) {
        this.id = id;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + address;
    }
}

public class DeliveryWorkflowSystem {
    private Map<String, DeliveryOrder> orderMap = new HashMap<>();
    private Queue<DeliveryOrder> pendingQueue = new ArrayDeque<>();
    private Deque<DeliveryOrder> completedStack = new ArrayDeque<>();

    public void addOrder(String id, String address) {
        if (orderMap.containsKey(id)) {
            System.out.println("新增失敗: 配送編號 " + id + " 已存在！");
            return;
        }
        DeliveryOrder order = new DeliveryOrder(id, address);
        orderMap.put(id, order);
        pendingQueue.offer(order);
        System.out.println("新增訂單成功: " + order);
    }

    public void processOrder() {
        if (pendingQueue.isEmpty()) {
            System.out.println("處理失敗: 目前沒有等待配送的訂單。");
            return;
        }
        DeliveryOrder order = pendingQueue.poll();
        completedStack.push(order);
        System.out.println("完成配送: " + order);
    }

    public void undoLastCompleted() {
        if (completedStack.isEmpty()) {
            System.out.println("Undo 失敗: 沒有已完成的配送可復原。");
            return;
        }
        DeliveryOrder order = completedStack.pop();
        pendingQueue.offer(order);
        System.out.println("復原配送 (Undo): " + order + " 已放回等待配送佇列。");
    }

    public void queryOrder(String id) {
        if (orderMap.containsKey(id)) {
            System.out.println("查詢結果: " + orderMap.get(id));
        } else {
            System.out.println("查詢失敗: 找不到編號為 " + id + " 的訂單。");
        }
    }

    public void showStatistics() {
        System.out.println("\n========== 物流系統統計 ==========");
        System.out.println("總訂單數量: " + orderMap.size());
        System.out.println("等待配送數 (" + pendingQueue.size() + "): " + pendingQueue);
        System.out.println("已完成數量 (" + completedStack.size() + "): " + completedStack);
        System.out.println("=================================\n");
    }

    public static void main(String[] args) {
        DeliveryWorkflowSystem system = new DeliveryWorkflowSystem();

        system.addOrder("D001", "台北市大安區");
        system.addOrder("D002", "新北市板橋區");
        system.addOrder("D003", "台中市西屯區");
        
        system.addOrder("D001", "重複測試地址");

        system.showStatistics();

        system.processOrder();
        system.processOrder();

        system.showStatistics();

        system.undoLastCompleted();

        system.queryOrder("D002");
        system.queryOrder("D999");

        system.showStatistics();
    }
}