public class OrderBstSystem {

    static class Order {
        int orderId;
        String customer;
        double amount;

        public Order(int orderId, String customer, double amount) {
            this.orderId = orderId;
            this.customer = customer;
            this.amount = amount;
        }

        @Override
        public String toString() {
            return String.format("[訂單號: %d | 客戶: %s | 金額: %.1f]", orderId, customer, amount);
        }
    }

    static class Node {
        Order order;
        Node left, right;
        public Node(Order order) { this.order = order; }
    }

    static class OrderSystem {
        Node root;

        public void add(Order order) {
            root = addRec(root, order);
        }

        private Node addRec(Node current, Order order) {
            if (current == null) return new Node(order);
            if (order.orderId < current.order.orderId) {
                current.left = addRec(current.left, order);
            } else if (order.orderId > current.order.orderId) {
                current.right = addRec(current.right, order);
            }
            return current;
        }

        public Order find(int orderId) {
            Node current = root;
            while (current != null) {
                if (orderId == current.order.orderId) return current.order;
                current = (orderId < current.order.orderId) ? current.left : current.right;
            }
            return null;
        }

        public void cancel(int orderId) {
            root = cancelRec(root, orderId);
        }

        private Node cancelRec(Node current, int orderId) {
            if (current == null) return null;
            if (orderId < current.order.orderId) {
                current.left = cancelRec(current.left, orderId);
            } else if (orderId > current.order.orderId) {
                current.right = cancelRec(current.right, orderId);
            } else {
                if (current.left == null) return current.right;
                if (current.right == null) return current.left;

                Node successor = current.right;
                while (successor.left != null) successor = successor.left;
                current.order = successor.order;
                current.right = cancelRec(current.right, successor.order.orderId);
            }
            return current;
        }

        public boolean updateAmount(int orderId, double newAmount) {
            Order o = find(orderId);
            if (o != null) {
                o.amount = newAmount;
                return true;
            }
            return false;
        }

        public void rangeReport(int lowId, int highId) {
            System.out.println("--- 訂單範圍報表 [" + lowId + " ~ " + highId + "] ---");
            rangeReportRec(root, lowId, highId);
        }

        private void rangeReportRec(Node node, int low, int high) {
            if (node == null) return;
            if (node.order.orderId >= low) rangeReportRec(node.left, low, high);
            if (node.order.orderId >= low && node.order.orderId <= high) System.out.println(node.order);
            if (node.order.orderId <= high) rangeReportRec(node.right, low, high);
        }

        public void summary() {
            int count = countOrders(root);
            double total = sumAmounts(root);
            System.out.println("=========== 系統摘要 ===========");
            System.out.println("總訂單數量: " + count);
            System.out.printf("總營收金額: %.1f%n", total);
            System.out.println("================================");
        }

        private int countOrders(Node node) {
            if (node == null) return 0;
            return 1 + countOrders(node.left) + countOrders(node.right);
        }

        private double sumAmounts(Node node) {
            if (node == null) return 0.0;
            return node.order.amount + sumAmounts(node.left) + sumAmounts(node.right);
        }
    }

    public static void main(String[] args) {
        OrderSystem system = new OrderSystem();

        system.add(new Order(1003, "Alice", 1200.0));
        system.add(new Order(1001, "Bob", 450.0));
        system.add(new Order(1005, "Charlie", 3200.0));
        system.add(new Order(1002, "David", 890.0));
        system.add(new Order(1004, "Eva", 1500.0));

        system.rangeReport(1001, 1004);

        System.out.println("\n更新訂單 1002 金額為 999.0...");
        system.updateAmount(1002, 999.0);

        System.out.println("取消 (刪除) 訂單 1003...");
        system.cancel(1003);

        System.out.println();
        system.summary();
    }
}