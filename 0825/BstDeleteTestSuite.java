public class BstDeleteTestSuite {

    static class Node {
        int val;
        Node left, right;

        public Node(int val) {
            this.val = val;
        }
    }

    static class CustomBst {
        Node root;

        public void insert(int val) {
            root = insertRec(root, val);
        }

        private Node insertRec(Node current, int val) {
            if (current == null) return new Node(val);
            if (val < current.val) current.left = insertRec(current.left, val);
            else if (val > current.val) current.right = insertRec(current.right, val);
            return current;
        }

        public void delete(int key) {
            root = deleteRec(root, key);
        }

        private Node deleteRec(Node root, int key) {
            if (root == null) return null;
            if (key < root.val) {
                root.left = deleteRec(root.left, key);
            } else if (key > root.val) {
                root.right = deleteRec(root.right, key);
            } else {
                if (root.left == null) return root.right;
                if (root.right == null) return root.left;

                Node successor = root.right;
                while (successor.left != null) successor = successor.left;
                root.val = successor.val;
                root.right = deleteRec(root.right, successor.val);
            }
            return root;
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
    }

    public static void main(String[] args) {
        CustomBst bst = new CustomBst();

        System.out.println("【測試 1：空樹刪除】");
        bst.delete(10);
        System.out.print("Inorder: "); bst.printInorder();

        System.out.println("\n【測試 2：只有單一根節點刪除】");
        bst.insert(50);
        bst.delete(50);
        System.out.print("刪除 50 後 Inorder: "); bst.printInorder();

        System.out.println("\n【測試 3：刪除不存在的值】");
        bst.insert(30);
        bst.delete(99);
        System.out.print("刪除 99 後 Inorder: "); bst.printInorder();

        System.out.println("\n【測試 4：Root 只有一個 Child】");
        bst.insert(40);
        bst.delete(30);
        System.out.print("刪除 30 後 Inorder: "); bst.printInorder();

        System.out.println("\n【測試 5：Root 有兩個 Children】");
        bst.insert(20);
        bst.insert(60);
        bst.delete(40);
        System.out.print("刪除 40 後 Inorder: "); bst.printInorder();

        System.out.println("\n【測試 6：連續刪除至空樹】");
        bst.delete(20);
        bst.delete(60);
        System.out.print("全數刪除後 Inorder: "); bst.printInorder();
    }
}