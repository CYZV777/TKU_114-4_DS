public class SkewedBstReport {

    static class Node {
        int val;
        Node left, right;

        public Node(int val) {
            this.val = val;
            this.left = null;
            this.right = null;
        }
    }

    static class CustomBst {
        Node root;

        public void insert(int val) {
            root = insertRec(root, val);
        }

        private Node insertRec(Node current, int val) {
            if (current == null) {
                return new Node(val);
            }
            if (val < current.val) {
                current.left = insertRec(current.left, val);
            } else if (val > current.val) {
                current.right = insertRec(current.right, val);
            }
            return current;
        }

        public int size() {
            return sizeRec(root);
        }

        private int sizeRec(Node node) {
            if (node == null) return 0;
            return 1 + sizeRec(node.left) + sizeRec(node.right);
        }

        public int height() {
            return heightRec(root);
        }

        private int heightRec(Node node) {
            if (node == null) return 0;
            return 1 + Math.max(heightRec(node.left), heightRec(node.right));
        }

        public int searchCount(int target) {
            int count = 0;
            Node current = root;
            while (current != null) {
                count++;
                if (target == current.val) {
                    return count;
                } else if (target < current.val) {
                    current = current.left;
                } else {
                    current = current.right;
                }
            }
            return count;
        }
    }

    public static void main(String[] args) {
        int[] sortedData = {10, 20, 30, 40, 50, 60, 70};
        CustomBst skewedTree = new CustomBst();
        for (int val : sortedData) {
            skewedTree.insert(val);
        }

        int[] balancedData = {40, 20, 60, 10, 30, 50, 70};
        CustomBst balancedTree = new CustomBst();
        for (int val : balancedData) {
            balancedTree.insert(val);
        }

        int target1 = 70;
        int target2 = 75;

        System.out.println("==================== BST 比較報告 ====================");
        System.out.println(String.format("%-15s | %-6s | %-6s | %-12s | %-12s", 
                "樹類型", "Size", "Height", "搜尋 " + target1 + " 比較", "搜尋 " + target2 + " 比較"));
        System.out.println("---------------------------------------------------------------");
        
        System.out.println(String.format("%-15s | %-6d | %-6d | %-16d | %-16d", 
                "單斜樹 (Skewed)", skewedTree.size(), skewedTree.height(), 
                skewedTree.searchCount(target1), skewedTree.searchCount(target2)));

        System.out.println(String.format("%-15s | %-6d | %-6d | %-16d | %-16d", 
                "平衡樹 (Balanced)", balancedTree.size(), balancedTree.height(), 
                balancedTree.searchCount(target1), balancedTree.searchCount(target2)));
    }
}