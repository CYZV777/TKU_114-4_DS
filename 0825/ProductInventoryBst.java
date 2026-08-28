public class ProductInventoryBst {

    static class Product {
        int id;
        String name;
        int stock;

        public Product(int id, String name, int stock) {
            this.id = id;
            this.name = name;
            this.stock = stock;
        }

        @Override
        public String toString() {
            return String.format("[ID: %d | %s | 庫存: %d]", id, name, stock);
        }
    }

    static class Node {
        Product data;
        Node left, right;

        public Node(Product data) {
            this.data = data;
        }
    }

    static class InventoryBst {
        Node root;

        public void insert(Product product) {
            root = insertRec(root, product);
        }

        private Node insertRec(Node current, Product product) {
            if (current == null) return new Node(product);
            if (product.id < current.data.id) {
                current.left = insertRec(current.left, product);
            } else if (product.id > current.data.id) {
                current.right = insertRec(current.right, product);
            }
            return current;
        }

        public Product find(int id) {
            Node current = root;
            while (current != null) {
                if (id == current.data.id) return current.data;
                current = (id < current.data.id) ? current.left : current.right;
            }
            return null;
        }

        public void restock(int id, int amount) {
            Product p = find(id);
            if (p != null) {
                p.stock += amount;
                System.out.println("補貨成功 -> " + p);
            } else {
                System.out.println("補貨失敗：找不到商品 ID " + id);
            }
        }

        public void deductStock(int id, int amount) {
            Product p = find(id);
            if (p != null) {
                if (p.stock >= amount) {
                    p.stock -= amount;
                    System.out.println("扣庫存成功 -> " + p);
                } else {
                    System.out.println("扣庫存失敗：商品 " + id + " 庫存不足 (現有: " + p.stock + ")");
                }
            } else {
                System.out.println("扣庫存失敗：找不到商品 ID " + id);
            }
        }

        public void delete(int id) {
            root = deleteRec(root, id);
        }

        private Node deleteRec(Node current, int id) {
            if (current == null) return null;
            if (id < current.data.id) {
                current.left = deleteRec(current.left, id);
            } else if (id > current.data.id) {
                current.right = deleteRec(current.right, id);
            } else {
                if (current.left == null) return current.right;
                if (current.right == null) return current.left;

                Node successor = current.right;
                while (successor.left != null) successor = successor.left;
                current.data = successor.data;
                current.right = deleteRec(current.right, successor.data.id);
            }
            return current;
        }

        public void inorderReport() {
            System.out.println("=========== 庫存清單報表 ===========");
            inorderRec(root);
            System.out.println("====================================");
        }

        private void inorderRec(Node node) {
            if (node != null) {
                inorderRec(node.left);
                System.out.println(node.data);
                inorderRec(node.right);
            }
        }
    }

    public static void main(String[] args) {
        InventoryBst bst = new InventoryBst();

        bst.insert(new Product(201, "鍵盤", 10));
        bst.insert(new Product(105, "滑鼠", 25));
        bst.insert(new Product(302, "螢幕", 5));

        bst.inorderReport();

        bst.restock(105, 10);
        bst.deductStock(201, 3);
        bst.deductStock(302, 10);

        bst.delete(105);
        bst.inorderReport();
    }
}