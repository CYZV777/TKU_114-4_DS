import java.util.*;

public class ServiceRequestSystem {

    static class Request implements Comparable<Request> {
        String id;
        int priority;
        String description;
        boolean isCancelled = false;

        public Request(String id, int priority, String description) {
            this.id = id;
            this.priority = priority;
            this.description = description;
        }

        @Override
        public int compareTo(Request o) {
            return Integer.compare(o.priority, this.priority);
        }

        @Override
        public String toString() {
            return String.format("[ID: %s, 優先級: %d, 內容: %s]", id, priority, description);
        }
    }

    private Map<String, Request> requestMap = new HashMap<>();
    private PriorityQueue<Request> pq = new PriorityQueue<>();

    public void addRequest(String id, int priority, String desc) {
        if (id == null || requestMap.containsKey(id)) return;
        Request req = new Request(id, priority, desc);
        requestMap.put(id, req);
        pq.offer(req);
    }

    public Request getRequest(String id) {
        return requestMap.get(id);
    }

    public boolean cancelRequest(String id) {
        if (!requestMap.containsKey(id)) {
            return false;
        }
        Request req = requestMap.remove(id);
        req.isCancelled = true;
        return true;
    }

    public Request pollNextRequest() {
        while (!pq.isEmpty()) {
            Request top = pq.poll();
            if (!top.isCancelled) {
                requestMap.remove(top.id);
                return top;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        ServiceRequestSystem sys = new ServiceRequestSystem();

        sys.addRequest("R101", 1, "冷氣一般檢查");
        sys.addRequest("R102", 5, "伺服器當機 (緊急)");
        sys.addRequest("R103", 3, "印表機卡紙");

        System.out.println("--- 查詢特定單號 ---");
        System.out.println("查詢 R102: " + sys.getRequest("R102"));

        System.out.println("\n--- 取消操作測試 (取消 R102) ---");
        boolean cancelled = sys.cancelRequest("R102");
        System.out.println("取消成功? " + cancelled);
        System.out.println("Map 查詢 R102 (應為 null): " + sys.getRequest("R102"));

        System.out.println("\n--- 依優先序依序取出處理 ---");
        Request next;
        while ((next = sys.pollNextRequest()) != null) {
            System.out.println("正在處理: " + next);
        }

        System.out.println("\n--- 邊界案例 ---");
        System.out.println("空佇列取單: " + sys.pollNextRequest());
        System.out.println("取消不存在單號: " + sys.cancelRequest("R999"));
    }
}