
/**
 * Saves and stores accounts
 *
 * @author Kanya Farley
 * @version 31/03
 */
import java.util.ArrayList;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
import java.util.Collections;
public class Bank
{
    private ArrayList<Account> accounts = new ArrayList<Account>();
    
    /**
     * Constructor for objects of class AccountCreator
     */
    public Bank()
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
        accounts.clear(); // avoids loading extra each time file is loaded?
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
        ArrayList<Account> tempAccounts = new ArrayList<>(accounts); // to avoid concurrent modification
        boolean found = false;
        for (Account thisAccount : tempAccounts) {
            if (accountNumber.equals(thisAccount.getAccountNumber())) {
                found = true;
                System.out.println(thisAccount.getCustomerName() + " " + thisAccount.getAccountType() + " is the account you're looking for.");
                System.out.println("It currently has a balance of $" + thisAccount.getCurrentBalance());
                System.out.println("Would you like to delete it? Enter 'yes' or 'no'");
                String userInput = kb.nextLine();
                userInput = userInput.toLowerCase();
                if (userInput.equals("yes")) {
                    this.removeAccount(thisAccount); // removes from archive
                    System.out.println("Account " + thisAccount.getAccountNumber() + " deleted!");
                    this.saveToFile(" "); // changes are saved
                } else if (userInput.equals("no")) {
                    System.out.println("Operation cancelled.");
                } else {
                    System.out.println("Sorry, I don't understand.");
                }
            }
        }
        if (!found) {
            System.out.println("Sorry, that account number does not exist within our records.");
        }
    }
    
    void deposit(String accountNumber, double deposit) {
        Scanner kb = new Scanner(System.in);
        ArrayList<Account> tempAccounts = new ArrayList<>(accounts); // to avoid concurrent modification
        boolean found = false;
        for (Account thisAccount: accounts) {
            if (accountNumber.equals(thisAccount.getAccountNumber())) {
                found = true;
                String accountType = thisAccount.getAccountType();
                System.out.println(thisAccount.getCustomerName() + " " + thisAccount.getAccountType() + " is the account you're looking for.");
                System.out.println("It currently has a balance of $" + thisAccount.getCurrentBalance());
                System.out.println("Would you like to deposit exactly $" + deposit + " into this account? Enter 'yes' or 'no'");
                String userInput = kb.nextLine();
                userInput = userInput.toLowerCase();
                if (userInput.equals("yes")) {
                    double newBalance = thisAccount.getCurrentBalance() + deposit;
                } else if (userInput.equals("no")) {
                    System.out.println("Operation cancelled.");
                }
            }
        }
        if (!found) {
            System.out.println("Sorry, that account number does not exist within our records.");
        }
    }
    
    void withdraw(String accountNumber, double withdraw) {
        
    }
    
    void displayAll() {
        System.out.println(accounts.size()); // debugging
        for(Account currentAccount: accounts) {
            System.out.println(currentAccount.toString());
        }
    }
}
