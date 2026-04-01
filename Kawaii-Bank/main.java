
/**
 * Creates and manages accounts
 * 
 * @author Kanya Farley
 * @version 1/04
 */
import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;
public class main
{
    Scanner kb = new Scanner(System.in);
    Bank accounts = new Bank();

    // account type boundarie
    final double EVERYDAY_SAVINGS_MIN = 0;
    final double CURRENT_MIN = -1000;
    final double MAX_WITHDRAWAL = 5000;

    // end of day stats
    double depositTotal = 0;
    double withdrawalTotal = 0;

    /**
     * Constructor for objects of class main
     */
    public main()
    {
        accounts.loadFromFile();
        System.out.println("Welcome to Kawaii-Bank account management!");
        start();
    }

    public void start() {
        boolean running = true;
        while (running) {
            System.out.println("What would you like to do? (Enter number for option)");

            System.out.println("1 : Create a new account");
            System.out.println("2 : Close an account");
            System.out.println("3 : Check account balance");
            System.out.println("4 : Deposit into account");
            System.out.println("5 : Withdraw from account");
            System.out.println("6 : End day");
            String userAction = kb.nextLine();
            if (userAction.equals("1")) {
                createAccount();
            } else if (userAction.equals("2")) {
                closeAccount();
            } else if (userAction.equals("3")) {
                checkAccounts();
            } else if (userAction.equals("4")) {
                depositToAccount();
            } else if (userAction.equals("5")) {
                withdrawFromAccount();
            } else if (userAction.equals("6")) {
                end();
            } else {
                System.out.println("Please enter '1', '2', '3', '4', or '5' in accordance with program options.");
            }
        }
    }

    /**
     * Asks for account information (customer name, address, account type) and provides an account number
     */

    public void createAccount() {
        System.out.print("Enter customer name for new account: ");
        String customerName = kb.nextLine();
        System.out.println();

        System.out.print("Enter customer address: ");
        String customerAddress = kb.nextLine();
        System.out.println();

        System.out.print("Enter account type (everyday, savings, or current): ");
        String accountType = kb.nextLine();
        System.out.println();

        // defines account number based on type
        String accountNumberType = " ";
        while (accountNumberType.equals(" ")) {
            accountType = accountType.toLowerCase();
            if (accountType.equals("everyday")) {
                accountNumberType = "-02";
            } else if (accountType.equals("savings") || accountType.equals("current")) {
                accountNumberType = "-00";
            } else {
                System.out.println("Input invalid. Try entering 'Everyday', 'Savings', or 'Current'");
                accountType = kb.nextLine();
            }
        }

        // generates random 6 digit number for account
        Random random = new Random();
        int min = 100000;
        int max = 999999;
        int randomAccountNumber = random.nextInt((max - min) + 1) + min;
        String accountNumber = "08-0101-0" + randomAccountNumber + accountNumberType;

        System.out.print("Enter amount to deposit: $");
        String input = kb.nextLine();
        double currentBalance = 0;
        boolean typeValidity = false;
        while (!typeValidity) {
            if (kb.hasNextDouble()) {
                currentBalance = kb.nextDouble();
                typeValidity = true;
            } else {
                System.out.println("Invalid. Please enter arabic numerals only.");
                input = kb.nextLine();
            }
        }

        boolean balanceValidity = false;
        while (!balanceValidity) {
            if (accountType.equals("everyday") && currentBalance < EVERYDAY_SAVINGS_MIN || accountType.equals("savings") && currentBalance < EVERYDAY_SAVINGS_MIN) {
                System.out.println("Sorry, minimum balance for " + accountType + " account is " + EVERYDAY_SAVINGS_MIN);
                System.out.println("Please try again.");
                currentBalance = kb.nextDouble();
            } else if (accountType.equals("current") && currentBalance < CURRENT_MIN) {
                System.out.println("Sorry, minimum balance for " + accountType + " account is " + CURRENT_MIN);
                System.out.println("Please try again.");
                currentBalance = kb.nextDouble();
            } else {
                balanceValidity = true;
            }
        }

        // creating object, verifying, and saving
        Account newAccount = new Account(customerName, accountNumber, customerAddress, accountType, currentBalance);
        System.out.println(customerName + "; " + accountNumber + "; " + customerAddress + "; " + accountType + "; " + "$" + currentBalance);
        newAccount.toString();
        accounts.addAccount(newAccount);
        System.out.println("Account added to list: ");
        accounts.displayAll();
        accounts.saveToFile(" ");
        kb.nextLine(); // clears buffer
        start();
    }

    public void closeAccount() {
        System.out.println("Current accounts: ");
        accounts.displayAll();
        System.out.println("Enter account number you wish to close (Note: Enter '-' characters with numbers):");
        String accountNumber = kb.nextLine();
        accounts.closeAccount(accountNumber);
        accounts.saveToFile(" ");
        kb.nextLine(); // clears buffer
        start();
    }

    public void checkAccounts() {
        System.out.println("Current accounts: ");
        accounts.displayAll();
        start();
    }

    public void depositToAccount() {
        System.out.println("Current accounts: ");
        accounts.displayAll();
        System.out.println("Enter account number to deposit money into: ");
        String accountNumber = kb.nextLine();
        System.out.println("Enter amount to deposit: $");
        String input = kb.nextLine();
        double amount = 0;
        boolean inputValidity = false;
        while (!inputValidity) {
            if (kb.hasNextDouble()) {
                amount = kb.nextDouble();
                inputValidity = true;
            } else {
                System.out.println("Invalid. Please enter an arabic numeral.");
                input = kb.nextLine();
            }
        }
        accounts.deposit(accountNumber, amount);
        depositTotal += amount;
        accounts.saveToFile(" ");
        kb.nextLine(); // clears buffer
        start();
    }

    public void withdrawFromAccount() {
        System.out.println("Current accounts: ");
        accounts.displayAll();
        System.out.println("Enter account number to withdraw money from: ");
        String accountNumber = kb.nextLine();
        System.out.println("Enter amount to withdraw: $");
        String input = kb.nextLine();
        double amount = 0;
        boolean inputValidity = false;
        while (!inputValidity) {
            if (kb.hasNextDouble()) {
                amount = kb.nextDouble();
                inputValidity = true;
            } else {
                System.out.println("Invalid. Please enter arabic numerals.");
                input = kb.nextLine();
            }
        }

        boolean amountValidity = false;
        while (!amountValidity) {
            if (amount > MAX_WITHDRAWAL) {
                amountValidity = false;
                System.out.println("Sorry, max withdrawal is " + MAX_WITHDRAWAL);
                System.out.println("Please try again.");
                amount = kb.nextDouble();
            } else { 
                amountValidity = true;
                accounts.withdraw(accountNumber, amount);
            }
        }
        withdrawalTotal += amount;
        accounts.saveToFile(" ");
        kb.nextLine(); // clears buffer
        start();
    }

    public void end() {
        double sum = accounts.total();
        System.out.println("Total amount in bank: $" + sum);
        System.out.println("Total amount deposited this day: $" + depositTotal);
        System.out.println("Total amount withdrawn this day: $" + withdrawalTotal);
        accounts.saveDayDataToFile(sum, depositTotal, withdrawalTotal);
    }

}
