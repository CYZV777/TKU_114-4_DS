public class TraversalSelector {

    static class Node {
        String val;
        Node left, right;
        Node(String val) { this.val = val; }
        Node(String val, Node left, Node right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public static String toPrefix(Node root) {
        if (root == null) return "";
        String left = toPrefix(root.left);
        String right = toPrefix(root.right);
        return root.val + (left.isEmpty() ? "" : " " + left) + (right.isEmpty() ? "" : " " + right);
    }

    public static String toInfix(Node root) {
        if (root == null) return "";
        if (root.left == null && root.right == null) return root.val;
        return "(" + toInfix(root.left) + " " + root.val + " " + toInfix(root.right) + ")";
    }

    public static String toPostfix(Node root) {
        if (root == null) return "";
        String left = toPostfix(root.left);
        String right = toPostfix(root.right);
        return (left.isEmpty() ? "" : left + " ") + (right.isEmpty() ? "" : right + " ") + root.val;
    }

    public static void main(String[] args) {
        Node root = new Node("*",
                new Node("+", new Node("A"), new Node("B")),
                new Node("-", new Node("C"), new Node("D"))
        );

        System.out.println("Prefix  : " + toPrefix(root));
        System.out.println("Infix   : " + toInfix(root));
        System.out.println("Postfix : " + toPostfix(root));
    }
}