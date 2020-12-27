import java.util.ArrayList;

public class Account {

    private String name;
    private String uuid;    // account ID number
    private User holder;    // User object who own this account
    private ArrayList<Transaction> transactions;    // list of transaction for this account

    public Account(String name, User holder, Bank theBank){
        this.name = name;
        this.holder = holder;

        // get a new account UUID
        this.uuid = theBank.getNewAccountUUID();

        // init transactions
        this.transactions = new ArrayList<Transaction>();
    }

    public String getUUID() {
        return this.uuid;
    }

    public String getSummaryLine(){
        // get the account's balance
        double balance = this.getBalance();

        // format the summary line, depending on whether the balance is negative
        if(balance >= 0) {
            return String.format("%s : $%.02f : %s", this.uuid, balance, this.name);
        } else {
            return String.format("%s : $(%.02f) : %s", this.uuid, balance, this.name);
        }

    }

    public double getBalance() {
        double balance = 0;
        for (Transaction t : this.transactions) {
            balance += t.getAmount();
        }
        return balance;
    }

    public void printTransHistory() {
        System.out.printf("\nTransaction history for account %s:\n", this.uuid);
        for (int t = this.transactions.size()-1; t >= 0; t--) {
            System.out.print(this.transactions.get(t).getSummaryLine());
        }
        System.out.println();
    }

    /**
     * Add a a new transaction in this account
     * @param amount the amount transacted
     * @param memo   the transaction memo
     */
    public void addTransaction(double amount, String memo) {
        // create new transaction object and add it to our list
        Transaction newTrans = new Transaction(amount, memo, this);
        this.transactions.add(newTrans);

    }
}
