import java.util.ArrayList;

public class User<global> {

    private String firstName;
    private String lastName;
    private String uuid;                    // User ID
    //private byte pinHash[];
    private String pinHash;
    private ArrayList<Account> accounts;    // list of accounts for this user

    SHA512Hasher sha512 = new SHA512Hasher();
    //MD5 md5 = new MD5();

    public User(String firstName, String lastName, String pin , Bank theBank) {
        this.firstName = firstName;
        this.lastName = lastName;

        //this.pinHash = md5.hash(pin);
        this.pinHash = sha512.hash(pin);

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


    public boolean validatePin(String aPin) {
        //return md5.checkPassword(this.pinHash, aPin);
        return sha512.checkPassword(this.pinHash, aPin);

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