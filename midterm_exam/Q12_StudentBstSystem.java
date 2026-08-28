import java.util.ArrayList;
import java.util.List;

public class Q12_StudentBstSystem {
    public static class Student {
        private final int id;
        private final String name;
        private int score;

        public Student(int id, String name, int score) {
            if (id <= 0) {
                throw new IllegalArgumentException("Student id must be > 0");
            }
            if (name == null || name.isBlank()) {
                throw new IllegalArgumentException("Student name cannot be null or blank");
            }
            this.id = id;
            this.name = name;
            this.score = Math.max(0, Math.min(100, score));
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getScore() {
            return score;
        }

        void setScore(int score) {
            this.score = Math.max(0, Math.min(100, score));
        }

        @Override
        public String toString() {
            return id + " | " + name + " | " + score;
        }
    }

    private static class Node {
        Student student;
        Node left;
        Node right;

        Node(Student student) {
            this.student = student;
        }
    }

    private Node root;

    public boolean add(Student student) {
        if (student == null || find(student.getId()) != null) {
            return false;
        }
        root = insert(root, student);
        return true;
    }

    private Node insert(Node node, Student student) {
        if (node == null) return new Node(student);
        if (student.getId() < node.student.getId()) {
            node.left = insert(node.left, student);
        } else if (student.getId() > node.student.getId()) {
            node.right = insert(node.right, student);
        }
        return node;
    }

    public Student find(int id) {
        Node curr = root;
        while (curr != null) {
            if (id == curr.student.getId()) return curr.student;
            else if (id < curr.student.getId()) curr = curr.left;
            else curr = curr.right;
        }
        return null;
    }

    public boolean updateScore(int id, int score) {
        Student st = find(id);
        if (st == null) {
            return false;
        }
        st.setScore(score);
        return true;
    }

    public boolean remove(int id) {
        if (find(id) == null) {
            return false;
        }
        root = removeNode(root, id);
        return true;
    }

    private Node removeNode(Node node, int id) {
        if (node == null) return null;

        if (id < node.student.getId()) {
            node.left = removeNode(node.left, id);
        } else if (id > node.student.getId()) {
            node.right = removeNode(node.right, id);
        } else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;

            Node successor = findMin(node.right);
            node.student = successor.student;
            node.right = removeNode(node.right, successor.student.getId());
        }
        return node;
    }

    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public List<Student> studentsBetween(int lowId, int highId) {
        List<Student> result = new ArrayList<>();
        if (lowId > highId) return result;
        rangeHelper(root, lowId, highId, result);
        return result;
    }

    private void rangeHelper(Node node, int low, int high, List<Student> result) {
        if (node == null) return;
        if (node.student.getId() > low) {
            rangeHelper(node.left, low, high, result);
        }
        if (node.student.getId() >= low && node.student.getId() <= high) {
            result.add(node.student);
        }
        if (node.student.getId() < high) {
            rangeHelper(node.right, low, high, result);
        }
    }

    public List<Student> inorder() {
        List<Student> result = new ArrayList<>();
        inorderHelper(root, result);
        return result;
    }

    private void inorderHelper(Node node, List<Student> result) {
        if (node == null) return;
        inorderHelper(node.left, result);
        result.add(node.student);
        inorderHelper(node.right, result);
    }

    public static void main(String[] args) {
        Q12_StudentBstSystem system = new Q12_StudentBstSystem();
        system.add(new Q12_StudentBstSystem.Student(300, "Mina", 78));
        system.add(new Q12_StudentBstSystem.Student(100, "Leo", 84));
        system.add(new Q12_StudentBstSystem.Student(500, "Nora", 105));
        system.add(new Q12_StudentBstSystem.Student(200, "Ivy", 69));

        System.out.println(system.updateScore(200, 88));
        System.out.println(system.studentsBetween(150, 500));
        System.out.println(system.remove(300));
        System.out.println(system.inorder());
    }
}