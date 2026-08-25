import java.util.ArrayDeque;
import java.util.Deque;

class Customer {
    private String id;
    private String name;

    public Customer(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "[" + id + "] " + name;
    }
}

public class CounterWaitingQueue {
    private Deque<Customer> queue = new ArrayDeque<>();

    public void addCustomer(String id, String name) {
        Customer customer = new Customer(id, name);
        queue.offerLast(customer);
        System.out.println("顧客加入排隊: " + customer);
        showWaitingCount();
    }

    public void peekNext() {
        if (queue.isEmpty()) {
            System.out.println("查看下一位: 目前無任何顧客等候。");
        } else {
            System.out.println("下一位即將服務的顧客: " + queue.peekFirst());
        }
    }

    public void serveNext() {
        if (queue.isEmpty()) {
            System.out.println("服務失敗: 目前隊列為空，沒有等候的顧客！");
            return;
        }
        Customer served = queue.pollFirst();
        System.out.println("正在服務顧客: " + served);
        showWaitingCount();
    }

    public void showWaitingCount() {
        System.out.println("  當前等候人數: " + queue.size() + " 人，隊列: " + queue);
        System.out.println("----------------------------------------");
    }

    public static void main(String[] args) {
        CounterWaitingQueue counter = new CounterWaitingQueue();

        System.out.println("=== 櫃台等候 Queue 測試 ===");

        counter.peekNext();
        counter.serveNext();

        counter.addCustomer("C01", "王大明");
        counter.addCustomer("C02", "李小華");
        counter.addCustomer("C03", "張美麗");

        counter.peekNext();

        counter.serveNext();
        counter.peekNext();
        counter.serveNext();

        counter.addCustomer("C04", "陳建國");

        counter.serveNext();
        counter.serveNext();
        counter.serveNext();
    }
}