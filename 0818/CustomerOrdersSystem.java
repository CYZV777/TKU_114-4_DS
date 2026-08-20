import java.util.Arrays;

class Customer {
    private String customerId;
    private String name;

    public Customer(String customerId, String name) {
        this.customerId = (customerId == null || customerId.isEmpty()) ? "Unknown" : customerId;
        this.name = (name == null || name.isEmpty()) ? "Unknown" : name;
    }

    public String getCustomerId() {
        return this.customerId;
    }

    public String getName() {
        return this.name;
    }

    @Override
    public String toString() {
        return name + " (" + customerId + ")";
    }
}

class OrderItem {
    private String name;
    private double unitPrice;
    private int quantity;

    public OrderItem(String name, double unitPrice, int quantity) {
        this.name = (name == null || name.isEmpty()) ? "Unknown" : name;
        this.unitPrice = (unitPrice < 0) ? 0 : unitPrice;
        this.quantity = (quantity < 0) ? 0 : quantity;
    }

    public String getName() {
        return this.name;
    }

    public double getUnitPrice() {
        return this.unitPrice;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public double getSubtotal() {
        return this.unitPrice * this.quantity;
    }

    @Override
    public String toString() {
        return name + " x" + quantity + " (單價: " + unitPrice + ", 小計: " + getSubtotal() + ")";
    }
}

class CustomerOrder {
    private String orderId;
    private Customer customer;
    private OrderItem[] items;

    public CustomerOrder(String orderId, Customer customer, OrderItem[] items) {
        this.orderId = (orderId == null || orderId.isEmpty()) ? "Unknown" : orderId;
        this.customer = customer;
        this.items = (items == null) ? new OrderItem[0] : Arrays.copyOf(items, items.length);
    }

    public double totalAmount() {
        double total = 0;
        for (OrderItem item : this.items) {
            if (item != null) {
                total += item.getSubtotal();
            }
        }
        return total;
    }

    public int totalQuantity() {
        int count = 0;
        for (OrderItem item : this.items) {
            if (item != null) {
                count += item.getQuantity();
            }
        }
        return count;
    }

    public String summary() {
        String customerInfo = (customer != null) ? customer.toString() : "Unknown";
        StringBuilder sb = new StringBuilder();
        sb.append("=== 訂單摘要 ===\n");
        sb.append("訂單編號: ").append(orderId).append("\n");
        sb.append("顧客資訊: ").append(customerInfo).append("\n");
        sb.append("品項清單:\n");
        for (OrderItem item : items) {
            if (item != null) {
                sb.append(" - ").append(item).append("\n");
            }
        }
        sb.append("總購買數量: ").append(totalQuantity()).append("\n");
        sb.append("訂單總額: ").append(totalAmount());
        return sb.toString();
    }
}

public class CustomerOrdersSystem {
    public static void main(String[] args) {
        Customer customer = new Customer("C001", "張三");

        OrderItem[] items = {
            new OrderItem("鍵盤", 1200, 1),
            new OrderItem("滑鼠", 600, 2),
            new OrderItem("滑鼠墊", 150, 3)
        };

        CustomerOrder order = new CustomerOrder("ORD-2026-001", customer, items);

        System.out.println(order.summary());
    }
}