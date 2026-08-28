public class OrderManagementBst {

    public enum Status {
        PENDING, PAID, CANCELLED
    }

    static class Order {
        int orderId;
        String customer;
        double amount;
        Status status;

        public Order(int orderId, String customer, double amount, Status status) {
            this.orderId = orderId;
            this.customer = customer;
            this.amount = amount;
            this.status = status;
        }

        @Override
        public String toString() {
            return String.format("Order[ID=%d, Customer='%s', Amount=%.2f, Status=%s]",
                    orderId, customer, amount, status);
        }
    }

    static class Node {
        Order order;
        Node left, right;
        Node(Order order) { this.order = order; }
    }

    private Node root;

    public boolean add(Order order) {
        if (order == null || order.amount < 0) return false;
        if (find(order.orderId) != null) return false;
        root = insert(root, order);
        return true;
    }

    private Node insert(Node node, Order order) {
        if (node == null) return new Node(order);
        if (order.orderId < node.order.orderId) node.left = insert(node.left, order);
        else if (order.orderId > node.order.orderId) node.right = insert(node.right, order);
        return node;
    }

    public Order find(int orderId) {
        Node cur = root;
        while (cur != null) {
            if (orderId == cur.order.orderId) return cur.order;
            cur = orderId < cur.order.orderId ? cur.left : cur.right;
        }
        return null;
    }

    public boolean updateStatus(int orderId, Status newStatus) {
        Order order = find(orderId);
        if (order == null || newStatus == null) return false;
        order.status = newStatus;
        return true;
    }

    public boolean cancel(int orderId) {
        return updateStatus(orderId, Status.CANCELLED);
    }

    public boolean remove(int orderId) {
        Order order = find(orderId);
        if (order == null || order.status != Status.CANCELLED) {
            return false;
        }
        root = deleteNode(root, orderId);
        return true;
    }

    private Node deleteNode(Node node, int orderId) {
        if (node == null) return null;
        if (orderId < node.order.orderId) node.left = deleteNode(node.left, orderId);
        else if (orderId > node.order.orderId) node.right = deleteNode(node.right, orderId);
        else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            Node min = node.right;
            while (min.left != null) min = min.left;
            node.order = min.order;
            node.right = deleteNode(node.right, min.order.orderId);
        }
        return node;
    }

    public void idRangeReport(int lowId, int highId) {
        System.out.printf("--- Order Report for ID Range [%d, %d] ---%n", lowId, highId);
        if (lowId <= highId) {
            rangeHelper(root, lowId, highId);
        }
        System.out.println("------------------------------------------");
    }

    private void rangeHelper(Node node, int low, int high) {
        if (node == null) return;
        if (node.order.orderId > low) rangeHelper(node.left, low, high);
        if (node.order.orderId >= low && node.order.orderId <= high) System.out.println(node.order);
        if (node.order.orderId < high) rangeHelper(node.right, low, high);
    }

    public double totalAmount() {
        return sumAmount(root);
    }

    private double sumAmount(Node node) {
        if (node == null) return 0.0;
        return node.order.amount + sumAmount(node.left) + sumAmount(node.right);
    }

    public static void main(String[] args) {
        OrderManagementBst system = new OrderManagementBst();

        system.add(new Order(1002, "Bob", 250.0, Status.PENDING));
        system.add(new Order(1001, "Alice", 120.0, Status.PAID));
        system.add(new Order(1003, "Charlie", 500.0, Status.CANCELLED));

        System.out.println("Add Negative Amount: " + system.add(new Order(1004, "David", -50.0, Status.PENDING)));

        System.out.printf("Total Order Amount: $%.2f%n", system.totalAmount());
        system.idRangeReport(1001, 1003);

        System.out.println("Remove PENDING Order 1002: " + system.remove(1002));

        system.cancel(1002);
        System.out.println("Remove CANCELLED Order 1002: " + system.remove(1002));

        system.idRangeReport(1000, 2000);
    }
}