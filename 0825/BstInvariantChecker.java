public class BstInvariantChecker {

    static class Node {
        int val;
        Node left, right;

        public Node(int val) {
            this.val = val;
            this.left = null;
            this.right = null;
        }
    }

    public static boolean isValidBST(Node root) {
        return validate(root, null, null);
    }

    private static boolean validate(Node node, Integer min, Integer max) {
        if (node == null) {
            return true;
        }

        if ((min != null && node.val <= min) || (max != null && node.val >= max)) {
            return false;
        }

        return validate(node.left, min, node.val) && validate(node.right, node.val, max);
    }

    public static void main(String[] args) {
        Node validTree = new Node(20);
        validTree.left = new Node(10);
        validTree.right = new Node(30);
        validTree.left.left = new Node(5);
        validTree.left.right = new Node(15);
        validTree.right.left = new Node(25);
        validTree.right.right = new Node(35);

        Node invalidTree1 = new Node(20);
        invalidTree1.left = new Node(10);
        invalidTree1.right = new Node(30);
        invalidTree1.left.right = new Node(25);

        Node invalidTree2 = new Node(20);
        invalidTree2.left = new Node(10);
        invalidTree2.right = new Node(30);
        invalidTree2.right.left = new Node(15);

        Node invalidTree3 = new Node(50);
        invalidTree3.left = new Node(30);
        invalidTree3.right = new Node(70);
        invalidTree3.left.right = new Node(40);
        invalidTree3.right.left = new Node(50);

        System.out.println("【BST Invariant Boundary 驗證結果】");
        System.out.println("1. 合法 BST 驗證結果: " + isValidBST(validTree));
        System.out.println("2. 違規樹 1 (左子樹深層 25 > 根節點 20): " + isValidBST(invalidTree1));
        System.out.println("3. 違規樹 2 (右子樹深層 15 < 根節點 20): " + isValidBST(invalidTree2));
        System.out.println("4. 違規樹 3 (右子樹深層 50 == 根節點 50): " + isValidBST(invalidTree3));
    }
}