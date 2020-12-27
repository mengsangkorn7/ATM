import java.util.Date;

public class Transaction {

    private double amount;  // amount of this one transaction
    private Date timestamp; // time and date of this transaction
    private String memo;    // a memo for this transaction
    private Account inAccount; // which account performed this transaction

    public Transaction(double amount, Account inAccount) {

        this.amount = amount;
        this.inAccount = inAccount;
        this.timestamp = new Date();    // new date/ current date
        this.memo = "";
    }

    public Transaction(double amount, String memo, Account inAccount) {

        // call the two-arg constructor first
        this(amount, inAccount);

        // set memo
        this.memo = memo;
    }

    public double getAmount() {
        return this.amount;
    }

    public String getSummaryLine() {
        if (this.amount >= 0) {
            return String.format("%s | $%.02f : %s\n",
                        this.timestamp.toString(), this.amount, this.memo);
        } else {
            return String.format("%s | $%.02f : %s\n",
                        this.timestamp.toString(), (-1*this.amount), this.memo);
        }
    }
}
