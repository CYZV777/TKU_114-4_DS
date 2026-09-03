import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Q11_BstHashDirectory {

    private static class Node {
        int id;
        Node left;
        Node right;

        Node(int id) {
            this.id = id;
        }
    }

    private Node root;
    private final Map<Integer, String> idToName = new HashMap<>();

    public boolean add(int id, String name) {
        if (id <= 0 || name == null) {
            return false;
        }
        String trimmed = name.trim();
        if (trimmed.isEmpty()) {
            return false;
        }

        if (idToName.containsKey(id)) {
            return false;
        }

        root = insertBst(root, id);
        idToName.put(id, trimmed);
        return true;
    }

    private Node insertBst(Node current, int id) {
        if (current == null) {
            return new Node(id);
        }
        if (id < current.id) {
            current.left = insertBst(current.left, id);
        } else if (id > current.id) {
            current.right = insertBst(current.right, id);
        }
        return current;
    }

    public String findName(int id) {
        return idToName.get(id);
    }

    public boolean remove(int id) {
        if (!idToName.containsKey(id)) {
            return false;
        }

        root = removeBst(root, id);
        idToName.remove(id);
        return true;
    }

    private Node removeBst(Node current, int id) {
        if (current == null) {
            return null;
        }

        if (id < current.id) {
            current.left = removeBst(current.left, id);
        } else if (id > current.id) {
            current.right = removeBst(current.right, id);
        } else {
            if (current.left == null) {
                return current.right;
            }
            if (current.right == null) {
                return current.left;
            }

            Node minNode = findMin(current.right);
            current.id = minNode.id;
            current.right = removeBst(current.right, minNode.id);
        }
        return current;
    }

    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    
    public List<Integer> idsBetween(int low, int high) {
        List<Integer> result = new ArrayList<>();
        
        if (low > high) {
            return result;
        }
        inorderRange(root, low, high, result);
        return result;
    }

    private void inorderRange(Node node, int low, int high, List<Integer> result) {
        if (node == null) {
            return;
        }
        
        if (node.id > low) {
            inorderRange(node.left, low, high, result);
        }
        
        if (node.id >= low && node.id <= high) {
            result.add(node.id);
        }
        
        if (node.id < high) {
            inorderRange(node.right, low, high, result);
        }
    }

    public int size() {
        return idToName.size();
    }

    
    public static void main(String[] args) {
        Q11_BstHashDirectory directory = new Q11_BstHashDirectory();

        
        System.out.println("新增 20: " + directory.add(20, " Alice ")); // true
        System.out.println("新增 10: " + directory.add(10, "Bob"));     // true
        System.out.println("新增 30: " + directory.add(30, "Charlie")); // true
        System.out.println("重複新增 20: " + directory.add(20, "Dan"));  // false
        System.out.println("無效 id <= 0: " + directory.add(0, "Eve")); // false
        System.out.println("無效 name 空白: " + directory.add(40, "   ")); // false

        
        System.out.println("查詢 20 的名字: " + directory.findName(20)); // "Alice"
        System.out.println("查詢不存在的 99: " + directory.findName(99)); // null
        System.out.println("目前 size: " + directory.size()); // 3

        
        directory.add(5, "Frank");
        directory.add(15, "Grace");
        System.out.println("區間 [10, 25]: " + directory.idsBetween(10, 25)); // [10, 15, 20]
        System.out.println("low > high 邊界測試: " + directory.idsBetween(30, 10)); // []

        
        System.out.println("刪除根節點 20: " + directory.remove(20)); // true
        System.out.println("刪除後查 20: " + directory.findName(20)); // null
        System.out.println("刪除後的 size: " + directory.size());     // 4
        System.out.println("刪除後的區間 [5, 30]: " + directory.idsBetween(5, 30)); // [5, 10, 15, 30]
    }
}