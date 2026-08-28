import java.util.ArrayList;
import java.util.List;

public class TreeBugLab {

    static class Node {
        int val;
        Node left, right;
        Node(int val) { this.val = val; }
    }

    static boolean buggySearch(Node node, int target) {
        if (node == null) return false;
        if (node.val == target) return true;
        return target < node.val ? buggySearch(node.right, target) : buggySearch(node.left, target);
    }

    static boolean fixedSearch(Node node, int target) {
        if (node == null) return false;
        if (node.val == target) return true;
        return target < node.val ? fixedSearch(node.left, target) : fixedSearch(node.right, target);
    }

    static void buggyInorder(Node node, List<Integer> list) {
        if (node == null) return;
        buggyInorder(node.right, list);
        list.add(node.val);
        buggyInorder(node.left, list);
    }

    static void fixedInorder(Node node, List<Integer> list) {
        if (node == null) return;
        fixedInorder(node.left, list);
        list.add(node.val);
        fixedInorder(node.right, list);
    }

    static Node buggyDelete(Node node, int val) {
        if (node == null) return null;
        if (val < node.val) node.left = buggyDelete(node.left, val);
        else if (val > node.val) node.right = buggyDelete(node.right, val);
        else {
            if (node.right == null) return null; 
            return node.right;
        }
        return node;
    }

    static Node fixedDelete(Node node, int val) {
        if (node == null) return null;
        if (val < node.val) node.left = fixedDelete(node.left, val);
        else if (val > node.val) node.right = fixedDelete(node.right, val);
        else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            Node min = node.right;
            while (min.left != null) min = min.left;
            node.val = min.val;
            node.right = fixedDelete(node.right, min.val);
        }
        return node;
    }

    static boolean buggyIsValidBST(Node node) {
        if (node == null) return true;
        if (node.left != null && node.left.val >= node.val) return false;
        if (node.right != null && node.right.val <= node.val) return false;
        return buggyIsValidBST(node.left) && buggyIsValidBST(node.right);
    }

    static boolean fixedIsValidBST(Node node, long min, long max) {
        if (node == null) return true;
        if (node.val <= min || node.val >= max) return false;
        return fixedIsValidBST(node.left, min, node.val) && fixedIsValidBST(node.right, node.val, max);
    }

    public static void main(String[] args) {
        System.out.println("=== 1. Search 方向相反 ===");
        Node bst1 = new Node(10); bst1.left = new Node(5);
        System.out.println("Buggy Search(5): " + buggySearch(bst1, 5) + " (預期 true, 實際 false)");
        System.out.println("Fixed Search(5): " + fixedSearch(bst1, 5));

        System.out.println("\n=== 2. Inorder 順序錯誤 ===");
        Node bst2 = new Node(10); bst2.left = new Node(5); bst2.right = new Node(15);
        List<Integer> bugList = new ArrayList<>(), fixList = new ArrayList<>();
        buggyInorder(bst2, bugList); fixedInorder(bst2, fixList);
        System.out.println("Buggy Inorder: " + bugList);
        System.out.println("Fixed Inorder: " + fixList);

        System.out.println("\n=== 3. Delete 遺失 child ===");
        Node bst3 = new Node(10); bst3.left = new Node(5);
        Node bugRoot = buggyDelete(bst3, 10);
        Node bst3Fix = new Node(10); bst3Fix.left = new Node(5);
        Node fixRoot = fixedDelete(bst3Fix, 10);
        System.out.println("Buggy Delete root: " + (bugRoot == null ? "null (遺失子節點)" : bugRoot.val));
        System.out.println("Fixed Delete root: " + (fixRoot == null ? "null" : fixRoot.val));

        System.out.println("\n=== 4. Validation 只檢查直接 child ===");
        Node invalidTree = new Node(10);
        invalidTree.right = new Node(15);
        invalidTree.right.left = new Node(6);
        System.out.println("Buggy IsValid: " + buggyIsValidBST(invalidTree) + " (誤判為合法)");
        System.out.println("Fixed IsValid: " + fixedIsValidBST(invalidTree, Long.MIN_VALUE, Long.MAX_VALUE));
    }
}