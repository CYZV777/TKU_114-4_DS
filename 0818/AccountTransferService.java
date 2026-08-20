class Account {
    private String id;
    private String owner;
    private int balance;

    public Account(String id, String owner, int balance) {
        this.id = (id == null || id.isEmpty()) ? "Unknown" : id;
        this.owner = (owner == null || owner.isEmpty()) ? "Unknown" : owner;
        this.balance = (balance < 0) ? 0 : balance;
    }

    public int getBalance() {
        return this.balance;
    }

    public void withdraw(int amount) {
        this.balance -= amount;
    }

    public void deposit(int amount) {
        this.balance += amount;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Owner: " + owner + ", Balance: " + balance;
    }
}

class TransferService {
    public static boolean transfer(Account source, Account target, int amount) {
        if (source == null || target == null) {
            return false;
        }
        if (source == target) {
            return false;
        }
        if (amount <= 0 || source.getBalance() < amount) {
            return false;
        }

        source.withdraw(amount);
        target.deposit(amount);
        return true;
    }
}

public class AccountTransferService {
    public static void main(String[] args) {
        Account accA = new Account("A01", "Alice", 1000);
        Account accB = new Account("B01", "Bob", 500);

        System.out.println("成功轉帳: " + TransferService.transfer(accA, accB, 300));
        System.out.println("餘額不足: " + TransferService.transfer(accA, accB, 2000));
        System.out.println("同帳戶轉帳: " + TransferService.transfer(accA, accA, 100));
        System.out.println("null 目標: " + TransferService.transfer(accA, null, 100));

        System.out.println("\n=== 最終帳戶狀態 ===");
        System.out.println(accA);
        System.out.println(accB);
    }
}