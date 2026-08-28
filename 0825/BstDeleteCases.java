public class BstDeleteCases {

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

        public void delete(int val) {
            root = deleteRec(root, val);
        }

        private Node deleteRec(Node root, int key) {
            if (root == null) return null;

            if (key < root.val) {
                root.left = deleteRec(root.left, key);
            } else if (key > root.val) {
                root.right = deleteRec(root.right, key);
            } else {
                if (root.left == null && root.right == null) {
                    return null;
                }
                if (root.left == null) {
                    return root.right;
                } else if (root.right == null) {
                    return root.left;
                }

                root.val = minValue(root.right);
                root.right = deleteRec(root.right, root.val);
            }
            return root;
        }

        private int minValue(Node node) {
            int minv = node.val;
            while (node.left != null) {
                minv = node.left.val;
                node = node.left;
            }
            return minv;
        }

        public int size() {
            return sizeRec(root);
        }

        private int sizeRec(Node node) {
            if (node == null) return 0;
            return 1 + sizeRec(node.left) + sizeRec(node.right);
        }

        public boolean isValid() {
            return isValidRec(root, null, null);
        }

        private boolean isValidRec(Node node, Integer min, Integer max) {
            if (node == null) return true;
            if ((min != null && node.val <= min) || (max != null && node.val >= max)) {
                return false;
            }
            return isValidRec(node.left, min, node.val) && isValidRec(node.right, node.val, max);
        }

        public void printInorder() {
            printInorderRec(root);
            System.out.println();
        }

        private void printInorderRec(Node node) {
            if (node != null) {
                printInorderRec(node.left);
                System.out.print(node.val + " ");
                printInorderRec(node.right);
            }
        }

        public void displayStatus(String operation) {
            System.out.println("【" + operation + "】");
            System.out.print("Inorder 走訪: ");
            printInorder();
            System.out.println("Size: " + size());
            System.out.println("Valid Result: " + isValid());
            System.out.println("----------------------------------------");
        }
    }

    public static void main(String[] args) {
        CustomBst bst = new CustomBst();

        int[] data = {50, 30, 70, 20, 40, 80, 75};
        for (int val : data) {
            bst.insert(val);
        }

        bst.displayStatus("初始 BST 狀態");

        bst.delete(20);
        bst.displayStatus("Case 1: 刪除 Leaf Node (20)");

        bst.delete(80);
        bst.displayStatus("Case 2: 刪除 Single-Child Node (80)");

        bst.delete(50);
        bst.displayStatus("Case 3: 刪除 Two-Child Node (50)");
    }
}