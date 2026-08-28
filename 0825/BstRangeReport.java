public class BstRangeReport {

    static class Node {
        int val;
        Node left, right;

        public Node(int val) {
            this.val = val;
            this.left = null;
            this.right = null;
        }
    }

    static class RangeBst {
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

        public Integer findMin() {
            if (root == null) return null;
            Node current = root;
            while (current.left != null) {
                current = current.left;
            }
            return current.val;
        }

        public Integer findMax() {
            if (root == null) return null;
            Node current = root;
            while (current.right != null) {
                current = current.right;
            }
            return current.val;
        }

        public void printRange(int low, int high) {
            System.out.print("範圍 [" + low + ", " + high + "] 的節點: ");
            
            if (low > high) {
                System.out.println("無效範圍 (low > high)");
                return;
            }

            printRangeRec(root, low, high);
            System.out.println();
        }

        private void printRangeRec(Node node, int low, int high) {
            if (node == null) return;

            if (node.val > low) {
                printRangeRec(node.left, low, high);
            }

            if (node.val >= low && node.val <= high) {
                System.out.print(node.val + " ");
            }

            if (node.val < high) {
                printRangeRec(node.right, low, high);
            }
        }
    }

    public static void main(String[] args) {
        RangeBst bst = new RangeBst();

        int[] data = {50, 30, 70, 20, 40, 60, 80};
        for (int val : data) {
            bst.insert(val);
        }

        System.out.println("====== 最小值與最大值測試 ======");
        System.out.println("最小值 (Min): " + bst.findMin());
        System.out.println("最大值 (Max): " + bst.findMax());
        System.out.println();

        System.out.println("====== 範圍查詢測試 ======");
        bst.printRange(30, 70);

        bst.printRange(20, 40);

        bst.printRange(25, 65);

        bst.printRange(70, 30);
    }
}