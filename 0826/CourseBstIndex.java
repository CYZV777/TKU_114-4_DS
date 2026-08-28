import java.util.ArrayList;
import java.util.List;

public class CourseBstIndex {

    static class Course {
        String courseCode;
        String courseName;
        int credit;

        public Course(String courseCode, String courseName, int credit) {
            this.courseCode = courseCode;
            this.courseName = courseName;
            this.credit = credit;
        }

        @Override
        public String toString() {
            return String.format("[%s] %s (%d credits)", courseCode, courseName, credit);
        }
    }

    static class Node {
        Course course;
        Node left, right;
        Node(Course course) { this.course = course; }
    }

    private Node root;

    public boolean add(Course course) {
        if (course == null || course.courseCode == null) return false;
        if (course.credit < 1 || course.credit > 6) return false;
        if (find(course.courseCode) != null) return false;

        root = insert(root, course);
        return true;
    }

    private Node insert(Node node, Course course) {
        if (node == null) return new Node(course);
        int cmp = course.courseCode.compareTo(node.course.courseCode);
        if (cmp < 0) node.left = insert(node.left, course);
        else if (cmp > 0) node.right = insert(node.right, course);
        return node;
    }

    public Course find(String courseCode) {
        Node cur = root;
        while (cur != null) {
            int cmp = courseCode.compareTo(cur.course.courseCode);
            if (cmp == 0) return cur.course;
            cur = cmp < 0 ? cur.left : cur.right;
        }
        return null;
    }

    public boolean updateCredit(String courseCode, int newCredit) {
        if (newCredit < 1 || newCredit > 6) return false;
        Course c = find(courseCode);
        if (c == null) return false;
        c.credit = newCredit;
        return true;
    }

    public boolean remove(String courseCode) {
        if (find(courseCode) == null) return false;
        root = deleteNode(root, courseCode);
        return true;
    }

    private Node deleteNode(Node node, String courseCode) {
        if (node == null) return null;
        int cmp = courseCode.compareTo(node.course.courseCode);
        if (cmp < 0) node.left = deleteNode(node.left, courseCode);
        else if (cmp > 0) node.right = deleteNode(node.right, courseCode);
        else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            Node min = node.right;
            while (min.left != null) min = min.left;
            node.course = min.course;
            node.right = deleteNode(node.right, min.course.courseCode);
        }
        return node;
    }

    public List<Course> codeRangeQuery(String lowCode, String highCode) {
        List<Course> result = new ArrayList<>();
        if (lowCode.compareTo(highCode) > 0) return result;
        rangeHelper(root, lowCode, highCode, result);
        return result;
    }

    private void rangeHelper(Node node, String low, String high, List<Course> result) {
        if (node == null) return;
        if (node.course.courseCode.compareTo(low) > 0) rangeHelper(node.left, low, high, result);
        if (node.course.courseCode.compareTo(low) >= 0 && node.course.courseCode.compareTo(high) <= 0) result.add(node.course);
        if (node.course.courseCode.compareTo(high) < 0) rangeHelper(node.right, low, high, result);
    }

    public void printSortedReport() {
        System.out.println("--- Course Sorted Report ---");
        inorderHelper(root);
        System.out.println("----------------------------");
    }

    private void inorderHelper(Node node) {
        if (node == null) return;
        inorderHelper(node.left);
        System.out.println(node.course);
        inorderHelper(node.right);
    }

    public static void main(String[] args) {
        CourseBstIndex index = new CourseBstIndex();
        System.out.println("Add CS102: " + index.add(new Course("CS102", "Data Structures", 3)));
        System.out.println("Add CS101: " + index.add(new Course("CS101", "Intro to CS", 2)));
        System.out.println("Add CS201: " + index.add(new Course("CS201", "Algorithms", 3)));
        System.out.println("Add Invalid Credit (0): " + index.add(new Course("CS300", "Invalid", 0)));
        System.out.println("Add Duplicate CS101: " + index.add(new Course("CS101", "Duplicate", 4)));

        index.printSortedReport();
        System.out.println("Update CS101 Credit to 4: " + index.updateCredit("CS101", 4));
        System.out.println("Range Query (CS101 ~ CS105): " + index.codeRangeQuery("CS101", "CS105"));
        System.out.println("Remove CS102: " + index.remove("CS102"));
        index.printSortedReport();
    }
}