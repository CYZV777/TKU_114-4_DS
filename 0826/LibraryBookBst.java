import java.util.ArrayList;
import java.util.List;

public class LibraryBookBst {

    static class Book {
        String isbn;
        String title;
        String author;
        boolean available;

        public Book(String isbn, String title, String author) {
            this.isbn = isbn;
            this.title = title;
            this.author = author;
            this.available = true;
        }

        @Override
        public String toString() {
            return String.format("ISBN: %s | Title: %-20s | Author: %-15s | Status: %s",
                    isbn, title, author, (available ? "Available" : "Borrowed"));
        }
    }

    static class Node {
        Book book;
        Node left, right;
        Node(Book book) { this.book = book; }
    }

    private Node root;

    public boolean add(Book book) {
        if (book == null || book.isbn == null) return false;
        if (find(book.isbn) != null) return false;
        root = insert(root, book);
        return true;
    }

    private Node insert(Node node, Book book) {
        if (node == null) return new Node(book);
        int cmp = book.isbn.compareTo(node.book.isbn);
        if (cmp < 0) node.left = insert(node.left, book);
        else if (cmp > 0) node.right = insert(node.right, book);
        return node;
    }

    public Book find(String isbn) {
        Node cur = root;
        while (cur != null) {
            int cmp = isbn.compareTo(cur.book.isbn);
            if (cmp == 0) return cur.book;
            cur = cmp < 0 ? cur.left : cur.right;
        }
        return null;
    }

    public boolean borrow(String isbn) {
        Book book = find(isbn);
        if (book == null || !book.available) return false;
        book.available = false;
        return true;
    }

    public boolean returnBook(String isbn) {
        Book book = find(isbn);
        if (book == null || book.available) return false;
        book.available = true;
        return true;
    }

    public boolean remove(String isbn) {
        Book book = find(isbn);
        if (book == null || !book.available) {
            return false;
        }
        root = deleteNode(root, isbn);
        return true;
    }

    private Node deleteNode(Node node, String isbn) {
        if (node == null) return null;
        int cmp = isbn.compareTo(node.book.isbn);
        if (cmp < 0) node.left = deleteNode(node.left, isbn);
        else if (cmp > 0) node.right = deleteNode(node.right, isbn);
        else {
            if (node.left == null) return node.right;
            if (node.right == null) return node.left;
            Node min = node.right;
            while (min.left != null) min = min.left;
            node.book = min.book;
            node.right = deleteNode(node.right, min.book.isbn);
        }
        return node;
    }

    public List<Book> rangeQuery(String lowIsbn, String highIsbn) {
        List<Book> res = new ArrayList<>();
        if (lowIsbn.compareTo(highIsbn) > 0) return res;
        rangeHelper(root, lowIsbn, highIsbn, res);
        return res;
    }

    private void rangeHelper(Node node, String low, String high, List<Book> res) {
        if (node == null) return;
        if (node.book.isbn.compareTo(low) > 0) rangeHelper(node.left, low, high, res);
        if (node.book.isbn.compareTo(low) >= 0 && node.book.isbn.compareTo(high) <= 0) res.add(node.book);
        if (node.book.isbn.compareTo(high) < 0) rangeHelper(node.right, low, high, res);
    }

    public void inorderReport() {
        System.out.println("=== Library Book Inorder Report ===");
        inorderHelper(root);
        System.out.println("===================================");
    }

    private void inorderHelper(Node node) {
        if (node == null) return;
        inorderHelper(node.left);
        System.out.println(node.book);
        inorderHelper(node.right);
    }

    public static void main(String[] args) {
        LibraryBookBst lib = new LibraryBookBst();
        lib.add(new Book("978-0131103627", "C Programming", "K&R"));
        lib.add(new Book("978-0201633610", "Design Patterns", "GoF"));
        lib.add(new Book("978-0134685991", "Effective Java", "Bloch"));

        lib.inorderReport();

        System.out.println("Borrow Effective Java: " + lib.borrow("978-0134685991"));

        System.out.println("Remove Borrowed Book (Effective Java): " + lib.remove("978-0134685991"));

        lib.returnBook("978-0134685991");
        System.out.println("Remove Returned Book (Effective Java): " + lib.remove("978-0134685991"));

        lib.inorderReport();
    }
}