class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;

    public TreeNode(int val) {
        this.val = val;
        this.left = null;
        this.right = null;
    }
}

class BinarySearchTree {
    TreeNode root;

    public void insert(int val) {
        root = insertRec(root, val);
    }

    private TreeNode insertRec(TreeNode current, int val) {
        if (current == null) {
            return new TreeNode(val);
        }
        if (val < current.val) {
            current.left = insertRec(current.left, val);
        } else if (val > current.val) {
            current.right = insertRec(current.right, val);
        }
        return current;
    }

    public boolean searchWithTrace(int target) {
        System.out.println("========== 搜尋目標值: " + target + " ==========");
        TreeNode current = root;
        int count = 0;

        while (current != null) {
            count++;
            System.out.printf("第 %d 次比較 -> 當前節點值: %d", count, current.val);

            if (target == current.val) {
                System.out.println(" | 方向: 找到目標 (Match)");
                System.out.println(">> 結果: 成功找到 " + target + "，共比較 " + count + " 次。\n");
                return true;
            } else if (target < current.val) {
                System.out.println(" | 方向: 往左子樹 (Left)");
                current = current.left;
            } else {
                System.out.println(" | 方向: 往右子樹 (Right)");
                current = current.right;
            }
        }

        System.out.println(">> 結果: 未找到目標 " + target + " (Missing Value)，共比較 " + count + " 次。\n");
        return false;
    }
}

public class BstSearchTrace {
    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();

        int[] nodes = {50, 30, 70, 20, 40, 60, 80};
        for (int node : nodes) {
            bst.insert(node);
        }

        System.out.println("【測試 1：搜尋 Root 節點】");
        bst.searchWithTrace(50);

        System.out.println("【測試 2：搜尋 Internal Node (內部節點)】");
        bst.searchWithTrace(30);

        System.out.println("【測試 3：搜尋 Leaf Node (葉節點)】");
        bst.searchWithTrace(80);

        System.out.println("【測試 4：搜尋 Missing Value (不存在的值)】");
        bst.searchWithTrace(25);
    }
}