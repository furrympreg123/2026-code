
/**
 * Write a description of class shopping here.
 *
 * @author Kanya Farley
 * @version 19/02/26
 */
import java.util.ArrayList;
import java.util.Scanner;
public class shopping
{
    // instance variables
    ArrayList<String> shoppingList = new ArrayList<String>();
    Scanner kb = new Scanner(System.in);
    /**
     * Constructor for objects of class shopping
     */
    public shopping()
    {
        System.out.println("Let's create a shopping list!");
        System.out.println("Add the first item below:");
        String firstItem = kb.nextLine();
        shoppingList.add(firstItem);
        System.out.println("Keep adding items, then type 'stop' to stop!");
        // loop continues allowing use to add to list
        for (int i = 0; i < shoppingList.size(); i++) { // tried to use a for-each loop but it didn't work
            /*System.out.println("Would you like to add another item? Enter 'yes' or 'no'");
            String response = kb.nextLine();
            if (response.equals("yes")) {
                System.out.println("Okay! Enter next item below:");
                String item = kb.nextLine();
                shoppingList.add(item);
            } else if (response.equals("no")) {
                System.out.println("Okay! Shopping list complete!");
                System.out.println(shoppingList);
            } else {
                System.out.println("Sorry, I don't understand. Try again! \n");
                System.out.println("Would you like to add another item? Enter 'yes' or 'no'");
                response = kb.nextLine();
            }*/
            System.out.println("Enter next item below:");
            String item = kb.nextLine();
            if (item.equals("stop")) { // not sure how to get this to stop user input
                System.out.println("Okay! Shopping list complete!");
                System.out.println(shoppingList);
            }
            shoppingList.add(item);
        }
    }
}
