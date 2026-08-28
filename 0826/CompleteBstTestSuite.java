import java.util.ArrayList;
import java.util.List;

public class CompleteBstTestSuite {

    static class Node {
        int val;
        Node left, right;
        Node(int val) { this.val = val; }
    }

    static class BST {
        Node root;

        boolean add(int val) {
            if (find(val)) return false;
            root = insert(root, val);
            return true;
        }

        private Node insert(Node node, int val) {
            if (node == null) return new Node(val);
            if (val < node.val) node.left = insert(node.left, val);
            else if (val > node.val) node.right = insert(node.right, val);
            return node;
        }

        boolean find(int val) {
            Node cur = root;
            while (cur != null) {
                if (val == cur.val) return true;
                cur = val < cur.val ? cur.left : cur.right;
            }
            return false;
        }

        boolean remove(int val) {
            if (!find(val)) return false;
            root = deleteNode(root, val);
            return true;
        }

        private Node deleteNode(Node node, int val) {
            if (node == null) return null;
            if (val < node.val) node.left = deleteNode(node.left, val);
            else if (val > node.val) node.right = deleteNode(node.right, val);
            else {
                if (node.left == null) return node.right;
                if (node.right == null) return node.left;
                Node min = node.right;
                while (min.left != null) min = min.left;
                node.val = min.val;
                node.right = deleteNode(node.right, min.val);
            }
            return node;
        }

        List<Integer> range(int low, int high) {
            List<Integer> res = new ArrayList<>();
            rangeHelper(root, low, high, res);
            return res;
        }

        private void rangeHelper(Node node, int low, int high, List<Integer> res) {
            if (node == null) return;
            if (node.val > low) rangeHelper(node.left, low, high, res);
            if (node.val >= low && node.val <= high) res.add(node.val);
            if (node.val < high) rangeHelper(node.right, low, high, res);
        }

        boolean isBST() {
            return checkBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
        }

        private boolean checkBST(Node node, long min, long max) {
            if (node == null) return true;
            if (node.val <= min || node.val >= max) return false;
            return checkBST(node.left, min, node.val) && checkBST(node.right, node.val, max);
        }
    }

    private static int totalChecks = 0;
    private static int passedChecks = 0;

    public static void check(String description, boolean condition) {
        totalChecks++;
        if (condition) {
            passedChecks++;
            System.out.printf("[PASS] %02d: %s%n", totalChecks, description);
        } else {
            System.out.printf("[FAIL] %02d: %s%n", totalChecks, description);
        }
    }

    public static void main(String[] args) {
        BST bst = new BST();

        check("Empty tree find", !bst.find(10));
        check("Empty tree remove", !bst.remove(10));
        check("Empty tree invariant", bst.isBST());
        check("Empty tree range query", bst.range(0, 100).isEmpty());

        check("Add root (50)", bst.add(50));
        check("Find root (50)", bst.find(50));
        check("Root isBST invariant", bst.isBST());

        check("Add duplicate root (50)", !bst.add(50));

        check("Add node 30", bst.add(30));
        check("Add node 70", bst.add(70));
        check("Add node 20", bst.add(20));
        check("Add node 40", bst.add(40));
        check("Add node 60", bst.add(60));
        check("Add node 80", bst.add(80));
        check("Add duplicate 30", !bst.add(30));

        check("Find missing key 99", !bst.find(99));
        check("Remove missing key 99", !bst.remove(99));

        check("Remove leaf 20", bst.remove(20));
        check("Find removed leaf 20", !bst.find(20));
        check("Invariant after removing leaf", bst.isBST());

        bst.add(65);
        check("Remove node with one child (60)", bst.remove(60));
        check("Child 65 still exists", bst.find(65));
        check("Invariant after removing one-child node", bst.isBST());

        check("Remove node with two children (50)", bst.remove(50));
        check("Find removed 50", !bst.find(50));
        check("Invariant after removing two-children node", bst.isBST());

        List<Integer> r1 = bst.range(30, 70);
        check("Range [30, 70] size correct", r1.size() == 4); // 30, 40, 65, 70
        List<Integer> r2 = bst.range(100, 200);
        check("Range [100, 200] empty check", r2.isEmpty());
        List<Integer> r3 = bst.range(70, 30);
        check("Range invalid bounds [70, 30] empty check", r3.isEmpty());

        System.out.printf("%nTotal: %d, Passed: %d, Failed: %d%n", totalChecks, passedChecks, totalChecks - passedChecks);
    }
}