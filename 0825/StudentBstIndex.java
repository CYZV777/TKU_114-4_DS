public class StudentBstIndex {

    static class Student {
        int studentId;
        String name;

        public Student(int studentId, String name) {
            this.studentId = studentId;
            this.name = name;
        }

        @Override
        public String toString() {
            return "[" + studentId + ", " + name + "]";
        }
    }

    static class Node {
        Student data;
        Node left, right;

        public Node(Student data) {
            this.data = data;
        }
    }

    static class StudentBst {
        Node root;

        public boolean insert(Student student) {
            if (search(student.studentId) != null) {
                System.out.println("新增失敗：學號 " + student.studentId + " 已存在！");
                return false;
            }
            root = insertRec(root, student);
            return true;
        }

        private Node insertRec(Node current, Student student) {
            if (current == null) return new Node(student);
            if (student.studentId < current.data.studentId) {
                current.left = insertRec(current.left, student);
            } else if (student.studentId > current.data.studentId) {
                current.right = insertRec(current.right, student);
            }
            return current;
        }

        public Student search(int studentId) {
            Node current = root;
            while (current != null) {
                if (studentId == current.data.studentId) return current.data;
                current = (studentId < current.data.studentId) ? current.left : current.right;
            }
            return null;
        }

        public void delete(int studentId) {
            root = deleteRec(root, studentId);
        }

        private Node deleteRec(Node current, int studentId) {
            if (current == null) return null;

            if (studentId < current.data.studentId) {
                current.left = deleteRec(current.left, studentId);
            } else if (studentId > current.data.studentId) {
                current.right = deleteRec(current.right, studentId);
            } else {
                if (current.left == null) return current.right;
                if (current.right == null) return current.left;
                
                Node successor = findMin(current.right);
                current.data = successor.data;
                current.right = deleteRec(current.right, successor.data.studentId);
            }
            return current;
        }

        private Node findMin(Node node) {
            while (node.left != null) node = node.left;
            return node;
        }

        public void inorder() {
            inorderRec(root);
            System.out.println();
        }

        private void inorderRec(Node node) {
            if (node != null) {
                inorderRec(node.left);
                System.out.print(node.data + " ");
                inorderRec(node.right);
            }
        }
    }

    public static void main(String[] args) {
        StudentBst bst = new StudentBst();

        bst.insert(new Student(102, "Alice"));
        bst.insert(new Student(101, "Bob"));
        bst.insert(new Student(105, "Charlie"));
        bst.insert(new Student(103, "David"));
        bst.insert(new Student(101, "Duplicate Bob"));

        System.out.print("目前學生名冊 (Inorder): ");
        bst.inorder();

        System.out.println("查詢 103: " + bst.search(103));
        
        System.out.println("刪除 102 後：");
        bst.delete(102);
        bst.inorder();
    }
}