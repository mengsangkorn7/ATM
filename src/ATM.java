import java.util.Scanner;

public class ATM {

    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        Bank theBank = new Bank("Bank of America");

        // adding a user to the bank which also create a saving account
        System.out.println("Please Enter your first name.");
        String firstName = sc.nextLine();
        System.out.println("Please Enter your last name.");
        String lastName = sc.nextLine();

        // User aUser = theBank.addUser("Mengsang", "Korn", "12345");
        User aUser = theBank.addUser(firstName, lastName , "12345");

        // adding a checking account
        Account newAccount = new Account("Checking", aUser, theBank);
        aUser.addAccount(newAccount);
        theBank.addAccount(newAccount);

        User currentUser;
        while (true) {
            //stay in teh login prompt until successfully login
            currentUser = ATM.mainMenuPrompt(theBank, sc);

            //stay in the menu until quit
            ATM.printUserMenu(currentUser, sc);
        }
    }

    public static User mainMenuPrompt(Bank theBank, Scanner sc) {

        String userID;
        String pin;
        User authUser;
        do {
            System.out.printf("\nWelcome to %s\n\n", theBank.getName());
            System.out.print("Enter user ID: ");
            userID = sc.nextLine();
            System.out.print("Enter pin: ");
            pin = sc.nextLine();

            // try to get the user object corresponding to the ID and pin combo
            authUser = theBank.userLogin(userID, pin);
            if(authUser == null) {
                System.out.println("Incorrect user ID or pin number combination!!" +
                                    " Please try again.");
            }
        } while (authUser == null);

        return authUser;
    }

    public static void printUserMenu(User theUser, Scanner sc) {

        // print a summary of the user account
        theUser.printAccountsSummary();

        int choice;
        // user menu
        do {
            System.out.printf("Welcome %s, what would you like to do?\n", theUser.getFirstName());
            System.out.println(" 1. for account transaction history." );
            System.out.println(" 2. for withdrawal.");
            System.out.println(" 3. for deposit.");
            System.out.println(" 4. for transfer.");
            System.out.println(" 5. to Quit!");
            System.out.print("Please select one of the options above: ");
            choice = sc.nextInt();
            if (choice < 1 || choice > 5) {
                System.out.println("Invalid choice! Please select from 1-5.");
            }
        } while(choice < 1 || choice > 5);

        // processing the selected choice
        switch (choice) {
            case 1 -> ATM.showTransHistory(theUser, sc);
            case 2 -> ATM.withdrawalFunds(theUser, sc);
            case 3 -> ATM.depositFunds(theUser, sc);
            case 4 -> ATM.transferFunds(theUser, sc);
            case 5 -> {
                System.out.println("Quited!");
                sc.nextLine(); // quit menu
            }
        }

        // redisplay the menu unless 5/quit is selected
        if (choice != 5) {
            ATM.printUserMenu(theUser, sc);
        }
    }

    /**
     * Show the transaction history for an account
     * @param theUser the logg-in User object
     * @param sc      the scanner object used for user input
     */
    public static void showTransHistory(User theUser, Scanner sc) {

        int theAcct;

        // get account whose transaction history to look at
        do {
            System.out.printf("Enter the number (1-%d) of the account whose " +
                    "transaction you want to see: ", theUser.numAccounts());
            theAcct = sc.nextInt()-1;
            if (theAcct < 0 || theAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account! Please try again.");
            }
        } while (theAcct < 0 || theAcct >= theUser.numAccounts());

        // print the transaction history
        theUser.printAccountsHistory(theAcct);
    }

    /**
     * Process transferring funds from one account to another
     * @param theUser the logged-in User object
     * @param sc      the scanner Object used for user input
     */
    public static void transferFunds(User theUser, Scanner sc) {
        int fromAcct;
        int toAcct;
        double amount;
        double acctBal;

        do {
            System.out.printf("Enter the number (1-%d) of the account " +
                            "to transfer from: ", theUser.numAccounts());
            fromAcct = sc.nextInt()-1;
            if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account! Please try again.");
            }
        } while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(fromAcct);

        // get the account to transfer to
        do {
            System.out.printf("Enter the number (1-%d) of the account to transfer to: ", theUser.numAccounts());
            toAcct = sc.nextInt()-1;
            if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account! Please try again.");
            }
        } while (toAcct < 0 || toAcct >= theUser.numAccounts());

        // get the amount to transfer
        do {
            System.out.printf("Enter the amount to transfer (max $%.02f) : $", acctBal);
            amount = sc.nextDouble();
            if (amount < 0 ) {
                System.out.println("Amount must be greater than zero!");
            } else if (amount > acctBal) {
                System.out.printf("Amount must be greater than\n balance of $%.02f. \n", acctBal);
            }
        }while (amount < 0 || amount > acctBal);

        theUser.addAcctTransaction(fromAcct, -1*amount, String.format("Transferred to account %s.",
                theUser.getAcctUUID(toAcct)));
        theUser.addAcctTransaction(toAcct, amount, String.format("Received from account %s.",
                theUser.getAcctUUID(fromAcct)));
    }

    /**
     * Process a fund withdraw from an account
     * @param theUser the logged-in User object
     * @param sc      the Scanner object user for user input
     */
    public static void withdrawalFunds(User theUser, Scanner sc) {
        int fromAcct;
        //int toAcct;
        double amount;
        double acctBal;
        String memo;

        // get the account to transfer from
        do {
            System.out.printf("Enter the number (1-%d) of the account to transfer from: ", theUser.numAccounts());
            fromAcct = sc.nextInt()-1;
            if (fromAcct < 0 || fromAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account! Please try again.");
            }
        } while (fromAcct < 0 || fromAcct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(fromAcct);

        // get the amount to transfer
        do {
            System.out.printf("Enter the amount to transfer (max $%.02f) : $", acctBal);
            amount = sc.nextDouble();
            if (amount < 0 ) {
                System.out.println("Amount must be greater than zero!");
            } else if (amount > acctBal) {
                System.out.printf("Amount must be greater than\n balance of $%.02f. \n", acctBal);
            }
        }while (amount < 0 || amount > acctBal);

        // gobble up the rest of previous input
        sc.nextLine();

        // get a memo
        System.out.print("Enter a memo: ");
        memo = sc.nextLine();

        // do the withdrawal
        theUser.addAcctTransaction(fromAcct, -1*amount, memo);
    }


    /**
     * Process a fund deposit to an account
     * @param theUser the logged-in user object
     * @param sc      the Scanner object used for user input
     */
    public static void depositFunds(User theUser, Scanner sc) {
        int toAcct;
        double amount;
        double acctBal;
        String memo;

        // get the account to transfer from
        do {
            System.out.printf("Enter the number (1-%d) of the account to deposit to: ", theUser.numAccounts());
            toAcct = sc.nextInt()-1;
            if (toAcct < 0 || toAcct >= theUser.numAccounts()) {
                System.out.println("Invalid account! Please try again.");
            }
        } while (toAcct < 0 || toAcct >= theUser.numAccounts());
        acctBal = theUser.getAcctBalance(toAcct);

        // get the amount to transfer
        do {
            System.out.print("Enter the amount to deposit to: $");
            amount = sc.nextDouble();
            if (amount < 0 ) {
                System.out.println("Amount must be greater than zero!");
            }
        }while (amount < 0);

        // gobble up the rest of previous input
        sc.nextLine();

        // get a memo
        System.out.print("Enter a memo: ");
        memo = sc.nextLine();

        // do the withdrawal
        theUser.addAcctTransaction(toAcct, amount, memo);
    }

}
