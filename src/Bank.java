import java.util.ArrayList;
import java.util.Random;

public class Bank {

    private String name;    // name of the bank
    private ArrayList<User> users; // list of all user for this bank
    private ArrayList<Account> accounts; // list of accounts in this bank

    public Bank(String name){
        this.name = name;
        this.users = new ArrayList<User>();
        this.accounts = new ArrayList<Account>();
    }

    public String getNewUserUUID() {

        // init
        StringBuilder uuid;
        Random rng = new Random();
        int len = 6;
        boolean nonUnique;

        // continue to loop until we get a unique ID
        do {

            // generating the number
            uuid = new StringBuilder();
            for (int c = 0; c < len; c++){
                uuid.append(((Integer) rng.nextInt(10)).toString());
            }

            // check if it's unique
            nonUnique = false;
            for (User u : this.users) {
               if (uuid.toString().compareTo(u.getUUID()) == 0) {
                   nonUnique = true;
                   break;
               }
            }

        } while (nonUnique);

        return uuid.toString();
    }

    public String getNewAccountUUID(){

        // inits
        StringBuilder uuid;
        Random rng = new Random();
        int len = 10;
        boolean nonUnique;

        // continue to loop until we get a unique ID
        do {
            // generating the number
            uuid = new StringBuilder();
            for (int c = 0; c < len; c++){
                uuid.append(((Integer) rng.nextInt(10)).toString());
            }
            // check if it's unique
            nonUnique = false;
            for (User u : this.users) {
                if (uuid.toString().compareTo(u.getUUID()) == 0) {
                    nonUnique = true;
                    break;
                }
            }

        } while (nonUnique);

        return uuid.toString();
    }

    public void addAccount(Account anAccount) {
        this.accounts.add(anAccount);
    }

    public User addUser(String firsName, String lastName, String pin) {

        // create a new user object and add it to our list
        User newUser = new User(firsName, lastName, pin, this);
        this.users.add(newUser);

        // create a saving account
        Account newAccount = new Account("Saving", newUser, this);
        newUser.addAccount(newAccount);
        this.addAccount(newAccount);

        return newUser;

    }

    public User userLogin(String userID, String pin) {

        // search through the list of users
        for (User u : this.users) {

            // check user ID is correct
            if(u.getUUID().compareTo(userID) == 0 && u.validatePin(pin)) {
                return u;
            }
        }

        return null;
    }

    public String getName(){
        return this.name;
    }

}