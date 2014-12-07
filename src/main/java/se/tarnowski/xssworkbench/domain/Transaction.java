package se.tarnowski.xssworkbench.domain;

public class Transaction {
    private final long customerId;
    private final String description;
    private final long amount;

    public Transaction(long customerId, String description, long amount) {
        this.description = description;
        this.customerId = customerId;
        this.amount = amount;
    }

    public long getCustomerId() {
        return customerId;
    }

    public String getDescription() {
        return description;
    }

    public long getAmount() {
        return amount;
    }
}
