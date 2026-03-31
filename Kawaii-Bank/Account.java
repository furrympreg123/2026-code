
/**
 * Blueprint for account objects.
 *
 * @author Kanya Farley
 * @version 2 20/03
 */
public class Account
{
    String customerName;
    String accountNumber;
    String customerAddress;
    String accountType;
    double currentBalance;
    /**
     * Constructor for objects of class Account
     */
    public Account(String customerName, String accountNumber, String customerAddress, String accountType, double currentBalance)
    {
        this.customerName = customerName;
        this.accountNumber = accountNumber;
        this.customerAddress = customerAddress;
        this.accountType = accountType;
        this.currentBalance = currentBalance;
    }
    
    public String getCustomerName() {
        return(this.customerName);
    }
    
    public String getAccountNumber() {
        return(this.accountNumber);
    }
    
    public String getCustomerAddress() {
        return(this.customerAddress);
    }
    
    public String getAccountType() {
        return(this.accountType);
    }
    
    public double getCurrentBalance() {
        return(this.currentBalance);
    }
    
    public String toString() {
        String accountInfo = ("Customer Name: " + this.customerName +"; Account Number: " + this.accountNumber + "; Customer Address: " +
        this.customerAddress + "; Account Type: " + this.accountType + "; Current Balance: " + currentBalance);
        return(accountInfo);
    }
}
