
/**
 * This program saves a users info into files
 *
 * @author Kanya Farley
 * @version 16/2/2026
 */
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;
public class fileReadingPrac
{
    Scanner kb = new Scanner(System.in);
    /**
     * Constructor for objects of class fileReadingPrac
     */
    public fileReadingPrac()
    {
        File userInfo = new File("userInfo.txt");
        System.out.println("What is your name?");
        String userName = kb.nextLine();
        System.out.println("What is your email address?");
        String email = kb.nextLine();
        try {
            FileWriter writer = new FileWriter(userInfo);
            writer.write("Name: " + userName + "\n");
            writer.write("Email: " + email);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println("Error: could not write file");
        }
    }

}
