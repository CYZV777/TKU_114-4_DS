class DigitalWallet {
    private String walletId;
    private String owner;
    private double balance;
    private int transactionCount;

    public DigitalWallet(String walletId, String owner, double balance) {
        this.walletId = (walletId == null || walletId.isEmpty()) ? "Unknown" : walletId;
        this.owner = (owner == null || owner.isEmpty()) ? "Unknown" : owner;
        this.balance = (balance < 0) ? 0 : balance;
        this.transactionCount = 0;
    }

    public boolean deposit(double amount) {
        if (amount <= 0) return false;
        this.balance += amount;
        this.transactionCount++;
        return true;
    }

    public boolean pay(double amount) {
        if (amount <= 0 || amount > this.balance) return false;
        this.balance -= amount;
        this.transactionCount++;
        return true;
    }

    public boolean refund(double amount) {
        if (amount <= 0) return false;
        this.balance += amount;
        this.transactionCount++;
        return true;
    }

    @Override
    public String toString() {
        return "ID: " + walletId + ", Owner: " + owner + ", Balance: " + balance + ", Count: " + transactionCount;
    }
}

public class DigitalWalletSystem {
    public static void main(String[] args) {
        DigitalWallet wallet = new DigitalWallet("W01", "User", 1000);

        System.out.println(wallet.deposit(500));
        System.out.println(wallet.pay(300));
        System.out.println(wallet.pay(2000));
        System.out.println(wallet.deposit(-50));
        System.out.println(wallet.refund(200));

        System.out.println(wallet);
    }
}