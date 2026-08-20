class Book {
    private String id;
    private String title;
    private double price;
    private int stock;

    public Book(String id, String title, double price, int stock) {
        if (id == null || id.trim().isEmpty()) {
            this.id = "Unknown";
        } else {
            this.id = id;
        }

        if (title == null || title.trim().isEmpty()) {
            this.title = "Unknown";
        } else {
            this.title = title;
        }

        if (price < 0) {
            this.price = 0;
        } else {
            this.price = price;
        }

        if (stock < 0) {
            this.stock = 0;
        } else {
            this.stock = stock;
        }
    }

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public double getPrice() {
        return this.price;
    }

    public int getStock() {
        return this.stock;
    }

    public double getTotalValue() {
        return this.price * this.stock;
    }

    @Override
    public String toString() {
        return "書號: " + this.id + ", 書名: " + this.title + ", 價格: " + this.price + ", 庫存: " + this.stock;
    }
}

public class BookArrayReport {
    public static void main(String[] args) {
        Book[] books = {
            new Book("B001", "Java 程式設計", 580, 5),
            new Book("B002", "資料結構", 650, 2),
            new Book("B003", "Python 程式設計", 720, 8),
            new Book("B004", "系統分析與設計", 490, 1)
        };

        System.out.println("=== 1. 所有書籍 ===");
        for (Book book : books) {
            System.out.println(book);
        }

        System.out.println("\n=== 2. 庫存總價值 ===");
        double totalInventoryValue = 0;
        for (Book book : books) {
            totalInventoryValue += book.getTotalValue();
        }
        System.out.println("庫存總價值: " + totalInventoryValue);

        System.out.println("\n=== 3. 價格最高的書 ===");
        Book mostExpensive = books[0];
        for (int i = 1; i < books.length; i++) {
            if (books[i].getPrice() > mostExpensive.getPrice()) {
                mostExpensive = books[i];
            }
        }
        System.out.println(mostExpensive);

        System.out.println("\n=== 4. 庫存小於或等於 3 的書 ===");
        for (Book book : books) {
            if (book.getStock() <= 3) {
                System.out.println(book);
            }
        }
    }
}