public class TreeShapeComparison {

    static class Node {
        int val;
        Node left, right;
        Node(int val) { this.val = val; }
    }

    static class BST {
        Node root;

        void insert(int val) {
            root = insertNode(root, val);
        }

        private Node insertNode(Node node, int val) {
            if (node == null) return new Node(val);
            if (val < node.val) node.left = insertNode(node.left, val);
            else if (val > node.val) node.right = insertNode(node.right, val);
            return node;
        }

        int height() {
            return getHeight(root);
        }

        private int getHeight(Node node) {
            if (node == null) return 0;
            return 1 + Math.max(getHeight(node.left), getHeight(node.right));
        }

        int searchComparisons(int target) {
            int comps = 0;
            Node cur = root;
            while (cur != null) {
                comps++;
                if (target == cur.val) return comps;
                cur = target < cur.val ? cur.left : cur.right;
            }
            return comps;
        }
    }

    public static void main(String[] args) {
        int[] baseKeys = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15}; // 15 個 key

        int[] asc = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        int[] desc = {15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        int[] balanced = {8, 4, 12, 2, 6, 10, 14, 1, 3, 5, 7, 9, 11, 13, 15};

        BST bstAsc = new BST();
        BST bstDesc = new BST();
        BST bstBal = new BST();

        for (int k : asc) bstAsc.insert(k);
        for (int k : desc) bstDesc.insert(k);
        for (int k : balanced) bstBal.insert(k);

        int missingKey = 100;

        printStats("升冪樹 (Ascending)", bstAsc, baseKeys, missingKey);
        printStats("降冪樹 (Descending)", bstDesc, baseKeys, missingKey);
        printStats("平衡樹 (Balanced)", bstBal, baseKeys, missingKey);
    }

    private static void printStats(String title, BST bst, int[] keys, int missingKey) {
        int totalSearchComps = 0;
        for (int k : keys) {
            totalSearchComps += bst.searchComparisons(k);
        }
        int missingKeyComps = bst.searchComparisons(missingKey);

        System.out.println("=== " + title + " ===");
        System.out.println("Tree Height                   : " + bst.height());
        System.out.println("Total Search Comparisons (15) : " + totalSearchComps);
        System.out.printf("Average Search Comparisons    : %.2f%n", (totalSearchComps / 15.0));
        System.out.println("Missing Key (" + missingKey + ") Comparisons : " + missingKeyComps);
        System.out.println();
    }
}