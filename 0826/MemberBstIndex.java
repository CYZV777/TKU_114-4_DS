public class MemberBstIndex {

    static class Member {
        int memberId;
        String name;
        String email;

        public Member(int memberId, String name, String email) {
            this.memberId = memberId;
            this.name = name;
            this.email = email;
        }

        @Override
        public String toString() {
            return String.format("Member[ID=%d, Name='%s', Email='%s']", memberId, name, email);
        }
    }

    static class Node {
        int key;
        Member data;
        Node left, right;

        Node(Member data) {
            this.key = data.memberId;
            this.data = data;
        }
    }

    private Node root;

    public boolean add(Member member) {
        if (member == null || member.email == null || member.email.trim().isEmpty()) {
            return false;
        }
        if (find(member.memberId) != null) {
            return false;
        }
        root = insert(root, member);
        return true;
    }

    private Node insert(Node node, Member member) {
        if (node == null) return new Node(member);
        if (member.memberId < node.key) node.left = insert(node.left, member);
        else if (member.memberId > node.key) node.right = insert(node.right, member);
        return node;
    }

    public Member find(int memberId) {
        Node cur = root;
        while (cur != null) {
            if (memberId == cur.key) return cur.data;
            cur = memberId < cur.key ? cur.left : cur.right;
        }
        return null;
    }

    public boolean updateEmail(int memberId, String newEmail) {
        if (newEmail == null || newEmail.trim().isEmpty()) return false;
        Member m = find(memberId);
        if (m == null) return false;
        m.email = newEmail;
        return true;
    }

    public boolean remove(int memberId) {
        if (find(memberId) == null) return false;
        root = deleteNode(root, memberId);
        return true;
    }

    private Node deleteNode(Node node, int key) {
        if (node == null) return null;
        if (key < node.key) node.left = deleteNode(node.left, key);
        else if (key > node.key) node.right = deleteNode(node.right, key);
        else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            Node min = node.right;
            while (min.left != null) min = min.left;
            node.key = min.key;
            node.data = min.data;
            node.right = deleteNode(node.right, min.key);
        }
        return node;
    }

    public void inorderReport() {
        System.out.println("--- Member Inorder Report ---");
        inorderHelper(root);
        System.out.println("-----------------------------");
    }

    private void inorderHelper(Node node) {
        if (node == null) return;
        inorderHelper(node.left);
        System.out.println(node.data);
        inorderHelper(node.right);
    }

    public static void main(String[] args) {
        MemberBstIndex index = new MemberBstIndex();

        System.out.println("Add M102: " + index.add(new Member(102, "Alice", "alice@tku.edu.tw")));
        System.out.println("Add M101: " + index.add(new Member(101, "Bob", "bob@tku.edu.tw")));
        System.out.println("Add M103: " + index.add(new Member(103, "Charlie", "charlie@tku.edu.tw")));

        System.out.println("Add Duplicate ID 101: " + index.add(new Member(101, "Bob2", "bob2@tku.edu.tw")));
        System.out.println("Add Blank Email: " + index.add(new Member(104, "David", "   ")));

        index.inorderReport();

        System.out.println("Update Email M101: " + index.updateEmail(101, "bob_new@tku.edu.tw"));
        System.out.println("Update Email M101 (Blank): " + index.updateEmail(101, "  "));

        System.out.println("Remove M102: " + index.remove(102));

        index.inorderReport();
    }
}