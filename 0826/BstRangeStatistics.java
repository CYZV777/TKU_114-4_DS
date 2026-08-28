import java.util.ArrayList;
import java.util.List;

public class BstRangeStatistics {

    static class Node {
        int val;
        Node left, right;
        Node(int val) { this.val = val; }
    }

    public static List<Integer> valuesBetween(Node root, int low, int high) {
        List<Integer> result = new ArrayList<>();
        if (low > high) return result;
        collectValues(root, low, high, result);
        return result;
    }

    private static void collectValues(Node node, int low, int high, List<Integer> result) {
        if (node == null) return;
        if (node.val > low) {
            collectValues(node.left, low, high, result);
        }
        if (node.val >= low && node.val <= high) {
            result.add(node.val);
        }
        if (node.val < high) {
            collectValues(node.right, low, high, result);
        }
    }

    public static int countBetween(Node root, int low, int high) {
        if (root == null || low > high) return 0;
        int count = 0;
        if (root.val >= low && root.val <= high) count++;
        if (root.val > low) count += countBetween(root.left, low, high);
        if (root.val < high) count += countBetween(root.right, low, high);
        return count;
    }

    public static int sumBetween(Node root, int low, int high) {
        if (root == null || low > high) return 0;
        int sum = 0;
        if (root.val >= low && root.val <= high) sum += root.val;
        if (root.val > low) sum += sumBetween(root.left, low, high);
        if (root.val < high) sum += sumBetween(root.right, low, high);
        return sum;
    }

    public static void test(Node root, int low, int high) {
        System.out.printf("Range [%d, %d] -> Values: %s, Count: %d, Sum: %d%n",
                low, high, valuesBetween(root, low, high), countBetween(root, low, high), sumBetween(root, low, high));
    }

    public static void main(String[] args) {
        Node root = new Node(10);
        root.left = new Node(5);
        root.right = new Node(15);
        root.left.left = new Node(3);
        root.left.right = new Node(7);
        root.right.right = new Node(18);
        test(root, 20, 30);
        test(root, 15, 5);
    }
}