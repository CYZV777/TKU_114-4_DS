class Transaction {
    private int sequence;
    private String type;
    private int amount;

    public Transaction(int sequence, String type, int amount) {
        this.sequence = sequence;
        this.type = (type == null || type.isEmpty()) ? "UNKNOWN" : type;
        this.amount = amount;
    }

    public int getSequence() {
        return this.sequence;
    }

    public String getType() {
        return this.type;
    }

    public int getAmount() {
        return this.amount;
    }

    @Override
    public String toString() {
        return "#" + sequence + " [" + type + "] 金額: " + amount;
    }
}

class Wallet {
    private String walletId;
    private String owner;
    private int balance;
    private Transaction[] transactions;
    private int count;

    public Wallet(String walletId, String owner, int balance, int capacity) {
        this.walletId = (walletId == null || walletId.isEmpty()) ? "Unknown" : walletId;
        this.owner = (owner == null || owner.isEmpty()) ? "Unknown" : owner;
        this.balance = (balance < 0) ? 0 : balance;
        this.transactions = new Transaction[capacity > 0 ? capacity : 10];
        this.count = 0;
    }

    public int getBalance() {
        return this.balance;
    }

    private boolean recordTransaction(String type, int amount) {
        if (count >= transactions.length) {
            return false;
        }
        transactions[count] = new Transaction(count + 1, type, amount);
        count++;
        return true;
    }

    public boolean deposit(int amount) {
        if (amount <= 0 || count >= transactions.length) {
            return false;
        }
        balance += amount;
        recordTransaction("DEPOSIT", amount);
        return true;
    }

    public boolean pay(int amount) {
        if (amount <= 0 || balance < amount || count >= transactions.length) {
            return false;
        }
        balance -= amount;
        recordTransaction("PAY", amount);
        return true;
    }

    public boolean transferTo(Wallet target, int amount) {
        if (target == null || target == this) {
            return false;
        }
        if (amount <= 0 || this.balance < amount) {
            return false;
        }
        if (this.count >= this.transactions.length || target.count >= target.transactions.length) {
            return false;
        }

        this.balance -= amount;
        target.balance += amount;

        this.recordTransaction("TRANSFER_OUT", amount);
        target.recordTransaction("TRANSFER_IN", amount);
        return true;
    }

    public Transaction findTransaction(int sequence) {
        for (int i = 0; i < count; i++) {
            if (transactions[i].getSequence() == sequence) {
                return transactions[i];
            }
        }
        return null;
    }

    public int totalByType(String type) {
        int total = 0;
        for (int i = 0; i < count; i++) {
            if (transactions[i].getType().equalsIgnoreCase(type)) {
                total += transactions[i].getAmount();
            }
        }
        return total;
    }

    public void printStatement() {
        System.out.println("=== 帳單明細: " + owner + " (" + walletId + ") ===");
        System.out.println("目前餘額: " + balance);
        System.out.println("交易紀錄 (" + count + "/" + transactions.length + "):");
        for (int i = 0; i < count; i++) {
            System.out.println("  " + transactions[i]);
        }
        System.out.println();
    }
}

public class WalletHistoryManager {
    public static void main(String[] args) {
        Wallet w1 = new Wallet("W01", "Alice", 1000, 5);
        Wallet w2 = new Wallet("W02", "Bob", 500, 5);

        w1.deposit(300);
        w1.pay(200);
        w1.transferTo(w2, 400);

        System.out.println("查詢 w1 序號 2 交易: " + w1.findTransaction(2));
        System.out.println("查詢 w1 序號 9 交易: " + w1.findTransaction(9));
        System.out.println("w1 轉出總金額: " + w1.totalByType("TRANSFER_OUT"));
        System.out.println("w2 轉入總金額: " + w2.totalByType("TRANSFER_IN"));
        System.out.println();

        w1.printStatement();
        w2.printStatement();
    }
}