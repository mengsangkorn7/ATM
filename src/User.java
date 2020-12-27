import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class User {

    private String firstName;
    private String lastName;
    private String uuid;                    // User ID
    private byte pinHash[];                 // MD5 of user's PIN number
    private ArrayList<Account> accounts;    // list of accounts for this user

    /**
     * Create a new user
     * @param firstName the user's first name
     * @param lastName  the user's last name
     * @param pin       the user's account pin number
     * @param theBank   Bank object that the user is a customer of
     */

    public User(String firstName, String lastName, String pin , Bank theBank) {
        this.firstName = firstName;
        this.lastName = lastName;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());
        } catch (NoSuchAlgorithmException e) {
            System.err.println("error, caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }

        // get user's Pin number/uuid
        this.uuid = theBank.getNewUserUUID();

        //create empty list of accounts
        this.accounts = new ArrayList<Account>();

        // print log message
        System.out.printf("New user: %s %s ,ID: %s has been created.\n",
                firstName, lastName, this.uuid);
    }

    public void addAccount(Account account){
        this.accounts.add(account);
    }

    public String getUUID() {
        return this.uuid;
    }

    /*
     * Check whether a given pin match the true user pin
     * @param aPin  the pin to check
     * @return      whether the pin is a valid one
     */

    public boolean validatePin(String aPin) {

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return MessageDigest.isEqual(md.digest(aPin.getBytes()),
                this.pinHash);
        } catch (NoSuchAlgorithmException e) {
            System.err.println("error, caught NoSuchAlgorithmException");
            e.printStackTrace();
            System.exit(1);
        }

        return false;
    }

    public String getFirstName(){
        return this.firstName;
    }

    public void printAccountsSummary() {

        System.out.printf("\n\n%s's accounts summary:\n", this.firstName);
        for (int a = 0; a < this.accounts.size(); a++) {
            System.out.printf("%d) %s\n", a+1, this.accounts.get(a).getSummaryLine());
        }
        System.out.println();
    }

    public int numAccounts() {
        return this.accounts.size();
    }

    public void printAccountsHistory(int acctIdx) {
        this.accounts.get(acctIdx).printTransHistory();
    }

    public double getAcctBalance(int acctIdx) {
        return this.accounts.get(acctIdx).getBalance();
    }

    /**
     * Get the UUID of a particular account
     * @param accIdx the index of the account to use
     * @return       the UUID of the account
     */
    public String getAcctUUID(int accIdx){
        return this.accounts.get(accIdx).getUUID();
    }

    public void addAcctTransaction(int accIdx, double amount, String memo) {
       this.accounts.get(accIdx).addTransaction(amount, memo);
    }
}
