public class BstShapeExperiment {

    static class Node {
        int val;
        Node left, right;
        public Node(int val) { this.val = val; }
    }

    static class CustomBst {
        Node root;

        public void insert(int val) { root = insertRec(root, val); }

        private Node insertRec(Node current, int val) {
            if (current == null) return new Node(val);
            if (val < current.val) current.left = insertRec(current.left, val);
            else if (val > current.val) current.right = insertRec(current.right, val);
            return current;
        }

        public int height() { return heightRec(root); }

        private int heightRec(Node node) {
            if (node == null) return 0;
            return 1 + Math.max(heightRec(node.left), heightRec(node.right));
        }

        public int searchCount(int target) {
            int count = 0;
            Node current = root;
            while (current != null) {
                count++;
                if (target == current.val) return count;
                current = (target < current.val) ? current.left : current.right;
            }
            return count;
        }
    }

    public static void main(String[] args) {
        int[] sortedSeq   = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        int[] reverseSeq  = {15, 14, 13, 12, 11, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        int[] balancedSeq = {8, 4, 12, 2, 6, 10, 14, 1, 3, 5, 7, 9, 11, 13, 15};

        CustomBst tree1 = buildTree(sortedSeq);
        CustomBst tree2 = buildTree(reverseSeq);
        CustomBst tree3 = buildTree(balancedSeq);

        System.out.println("=================== 樹形實驗結果比較 (15 個節點) ===================");
        System.out.printf("%-18s | %-8s | %-16s | %-16s%n", "插入順序", "樹高 (H)", "總搜尋比較次數", "平均搜尋比較次數");
        System.out.println("---------------------------------------------------------------------");
        
        printStats("升序 (Sorted)", tree1, sortedSeq);
        printStats("降序 (Reverse)", tree2, sortedSeq);
        printStats("平衡 (Balanced)", tree3, sortedSeq);
    }

    private static CustomBst buildTree(int[] arr) {
        CustomBst bst = new CustomBst();
        for (int v : arr) bst.insert(v);
        return bst;
    }

    private static void printStats(String name, CustomBst tree, int[] elements) {
        int totalCompares = 0;
        for (int val : elements) {
            totalCompares += tree.searchCount(val);
        }
        double avg = (double) totalCompares / elements.length;
        System.out.printf("%-18s | %-8d | %-16d | %-16.2f%n", name, tree.height(), totalCompares, avg);
    }
}