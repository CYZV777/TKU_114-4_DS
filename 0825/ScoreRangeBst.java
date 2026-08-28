public class ScoreRangeBst {

    static class Node {
        int score;
        int studentId;
        String name;
        Node left, right;

        public Node(int score, int studentId, String name) {
            this.score = score;
            this.studentId = studentId;
            this.name = name;
        }

        @Override
        public String toString() {
            return String.format("[分數: %d | 學號: %d | %s]", score, studentId, name);
        }
    }

    static class ScoreBst {
        Node root;

        public void insert(int score, int studentId, String name) {
            root = insertRec(root, score, studentId, name);
        }

        private Node insertRec(Node current, int score, int studentId, String name) {
            if (current == null) return new Node(score, studentId, name);

            if (score < current.score || (score == current.score && studentId < current.studentId)) {
                current.left = insertRec(current.left, score, studentId, name);
            } else if (score > current.score || (score == current.score && studentId > current.studentId)) {
                current.right = insertRec(current.right, score, studentId, name);
            }
            return current;
        }

        public void printRange(int lowScore, int highScore) {
            System.out.println("--- 查詢分數區間 [" + lowScore + " ~ " + highScore + "] ---");
            if (lowScore > highScore) {
                System.out.println("無效區間 (low > high)");
                return;
            }
            printRangeRec(root, lowScore, highScore);
        }

        private void printRangeRec(Node node, int low, int high) {
            if (node == null) return;

            if (node.score >= low) {
                printRangeRec(node.left, low, high);
            }

            if (node.score >= low && node.score <= high) {
                System.out.println(node);
            }

            if (node.score <= high) {
                printRangeRec(node.right, low, high);
            }
        }
    }

    public static void main(String[] args) {
        ScoreBst bst = new ScoreBst();

        bst.insert(85, 1001, "Alice");
        bst.insert(92, 1002, "Bob");
        bst.insert(70, 1003, "Charlie");
        bst.insert(85, 1004, "David");
        bst.insert(60, 1005, "Eva");
        bst.insert(85, 999,  "Frank");

        bst.printRange(70, 90);
    }
}