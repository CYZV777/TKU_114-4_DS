public class BstDuplicateCounter {

    static class Node {
        int key;
        int count;
        Node left, right;

        public Node(int key) {
            this.key = key;
            this.count = 1;
            this.left = null;
            this.right = null;
        }
    }

    static class DuplicateBst {
        Node root;

        public void insert(int key) {
            root = insertRec(root, key);
        }

        private Node insertRec(Node current, int key) {
            if (current == null) {
                return new Node(key);
            }

            if (key == current.key) {
                current.count++;
            } else if (key < current.key) {
                current.left = insertRec(current.left, key);
            } else {
                current.right = insertRec(current.right, key);
            }

            return current;
        }

        public void inorder() {
            inorderRec(root);
            System.out.println();
        }

        private void inorderRec(Node node) {
            if (node != null) {
                inorderRec(node.left);
                System.out.print(node.key + "(" + node.count + ") ");
                inorderRec(node.right);
            }
        }
    }

    public static void main(String[] args) {
        DuplicateBst bst = new DuplicateBst();

        int[] data = {50, 30, 20, 40, 70, 60, 80, 30, 50, 50, 20};

        System.out.print("插入數列: ");
        for (int val : data) {
            System.out.print(val + " ");
            bst.insert(val);
        }
        System.out.println("\n");

        System.out.println("Inorder 走訪輸出結果 [key(count)]：");
        bst.inorder();
    }
}