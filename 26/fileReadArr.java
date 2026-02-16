
/**
 * This program uses an array to store 5 words a user imports
 *
 * @author Kanya Farley
 * @version 16/9/2026
 */
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
public class fileReadArr
{
    String[] stuff = new String [5];
    Scanner kb = new Scanner(System.in);
    /**
     * Constructor for objects of class fileReadArr
     */
    public fileReadArr()
    {
        System.out.println("Enter 5 words and I will put them into a file!)");
        for (int i = 0; i < stuff.length; i++) {
            System.out.println("Enter word #" + i);
            stuff[i] = kb.nextLine();
        }
        
    }
}
