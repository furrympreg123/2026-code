
/**
 * This program asks the user to enter the names and ages of five people as strings and integers respectively. 
 *
 * @author Kanya Farley
 * @version 19/2/26
 */
import java.util.Scanner;
import java.util.Arrays;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
public class namesAndAges
{
    Scanner kb = new Scanner(System.in);
    String[] names = new String[5];
    int[] ages = new int [5];
    File namesAndAges = new File("names_and_ages.txt");
    /**
     * Constructor for objects of class namesAndAges
     */
    public namesAndAges() {
        // get names + ages from user and put in respective arrays)
        for (int i = 0; i < names.length; i++) {
            System.out.println("Give me name " + (i + 1));
            names[i] = kb.nextLine();
        }
        for (int i = 0; i < ages.length; i++) {
            System.out.println("Give me the age of " + names[i]);
            ages[i] = kb.nextInt();
        }
        
        // prints names and ages using for-each loops
        for (String currentName : names) {
            System.out.println(currentName);
        }
        for (int currentAge : ages) {
            System.out.println(currentAge);
        }
        
        // prints names and ages using for loops
        for (int i = 0; i < names.length; i++) {
            System.out.println(names[i]);
        }
        for (int i = 0; i < ages.length; i++) {
            System.out.println(ages[i]);
        }
        
        // prints name + age together
        for (int i = 0; i < names.length; i++) {
            System.out.println(names[i] + "'s age is " + ages[i] + "!");
        }
    }
}
