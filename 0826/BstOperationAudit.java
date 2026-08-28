import java.util.ArrayList;
import java.util.List;

public class BstOperationAudit {

    static class Node {
        int val;
        Node left, right;
        Node(int val) { this.val = val; }
    }

    private Node root;

    public boolean add(int val) {
        if (contains(root, val)) return false;
        root = insert(root, val);
        return true;
    }

    private Node insert(Node node, int val) {
        if (node == null) return new Node(val);
        if (val < node.val) node.left = insert(node.left, val);
        else if (val > node.val) node.right = insert(node.right, val);
        return node;
    }

    public boolean remove(int val) {
        if (!contains(root, val)) return false;
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
            Node successor = getMin(node.right);
            node.val = successor.val;
            node.right = deleteNode(node.right, successor.val);
        }
        return node;
    }

    private Node getMin(Node node) {
        while (node.left != null) node = node.left;
        return node;
    }

    private boolean contains(Node node, int val) {
        if (node == null) return false;
        if (val == node.val) return true;
        return val < node.val ? contains(node.left, val) : contains(node.right, val);
    }

    public int size() { return count(root); }
    private int count(Node node) {
        return node == null ? 0 : 1 + count(node.left) + count(node.right);
    }

    public int height() { return getHeight(root); }
    private int getHeight(Node node) {
        return node == null ? 0 : 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }

    public boolean isValid() { return checkValid(root, Long.MIN_VALUE, Long.MAX_VALUE); }
    private boolean checkValid(Node node, long min, long max) {
        if (node == null) return true;
        if (node.val <= min || node.val >= max) return false;
        return checkValid(node.left, min, node.val) && checkValid(node.right, node.val, max);
    }

    public List<Integer> inorder() {
        List<Integer> list = new ArrayList<>();
        inorderHelper(root, list);
        return list;
    }

    private void inorderHelper(Node node, List<Integer> list) {
        if (node == null) return;
        inorderHelper(node.left, list);
        list.add(node.val);
        inorderHelper(node.right, list);
    }

    public void auditAdd(int val) {
        boolean res = add(val);
        auditPrint("ADD " + val, res);
    }

    public void auditRemove(int val) {
        boolean res = remove(val);
        auditPrint("REMOVE " + val, res);
    }

    private void auditPrint(String op, boolean res) {
        System.out.printf("Operation: %-12s | Result: %-5s | Inorder: %-20s | Size: %d | Height: %d | Valid: %s%n",
                op, res, inorder(), size(), height(), isValid());
    }

    public static void main(String[] args) {
        BstOperationAudit bst = new BstOperationAudit();
        bst.auditAdd(50);
        bst.auditAdd(30);
        bst.auditAdd(70);
        bst.auditAdd(20);
        bst.auditAdd(40);
        bst.auditAdd(60);
        bst.auditAdd(80);

        bst.auditAdd(30);

        bst.auditRemove(999);

        bst.auditRemove(20);

        bst.auditAdd(65);
        bst.auditRemove(60);

        bst.auditRemove(50);
    }
}