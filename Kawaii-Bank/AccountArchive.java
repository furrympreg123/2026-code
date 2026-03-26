
/**
 * Saves and stores accounts
 *
 * @author Kanya Farley
 * @version 27/03
 */
import java.util.ArrayList;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.Collections;
public class AccountArchive
{
    public ArrayList<Account> accounts = new ArrayList<Account>(); // made public for accessability??
    /**
     * Constructor for objects of class AccountCreator
     */
    public AccountArchive()
    {
        this.loadFromFile();
    }
    
    void saveToFile (String Accounts) {
        File file = new File("Accounts.txt");
        try {
            FileWriter writer = new FileWriter(file);
            for (Account thisAccount : accounts) {
                writer.write(thisAccount.getCustomerName() + "; " +
                thisAccount.getAccountNumber() + "; " +
                thisAccount.getCustomerAddress() + "; " +
                thisAccount.getAccountType() + "; " +
                thisAccount.getCurrentBalance() + "\n");
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println("Sorry! Couldn't write that file.");
        }
    }
    
    void loadFromFile () {
        try {
            File file = new File("Accounts.txt");
            Scanner read = new Scanner(file);
            while (read.hasNextLine()) {
                String line = read.nextLine();
                String[] tempAccount = line.split("; ");
                accounts.add(new Account(tempAccount[0], tempAccount[1], tempAccount[2], tempAccount[3], Double.valueOf(tempAccount[4])));
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    
    void addAccount(Account currentAccount) {
        accounts.add(currentAccount);
    }
    
    void removeAccount(Account currentAccount) {
        accounts.remove(currentAccount);
    }
    
    void closeAccount(String accountNumber) {
        Scanner kb = new Scanner(System.in);
        for (Account thisAccount : accounts) {
            System.out.println("inside for loop"); // debugging
            if (accountNumber.equals(thisAccount.getAccountNumber())) {
                System.out.println(thisAccount.getCustomerName() + " " + thisAccount.getAccountType() + " is the account you're looking for.");
                System.out.println("It currently has a balance of $" + thisAccount.getCurrentBalance());
                System.out.println("Would you like to delete it? Enter 'yes' or 'no'");
                String userInput = kb.nextLine();
                userInput.toLowerCase();
                if (userInput.equals("yes")) {
                    System.out.println("Deleting account...");
                    accounts.remove(thisAccount); // removes from this classes arraylist
                    this.removeAccount(thisAccount); // removes from archive
                } else if (userInput.equals("no")) {
                    System.out.println("Operation cancelled.");
                } else {
                    System.out.println("Sorry, I don't understand.");
                }
            } else {
                System.out.println("Sorry, that does not match any accounts in our records.");
            } 
        }
    }
    
    void displayAll() {
        System.out.println(accounts.size()); // debugging
        for(Account currentAccount: accounts) {
            System.out.println(currentAccount.toString());
        }
    }
}
