package Customer;

public class Customer {
    private String name;
    private double balance;

    public Customer(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public void deductBalance(double amount) {
        if (balance < amount) {
            throw new RuntimeException("Insufficient balance");
        }
        balance -= amount;
    }

    public void addBalance(double amount) {
        balance += amount;
    }
}
