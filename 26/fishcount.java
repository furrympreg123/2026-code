
/**
 * This program calculates the total, mean, heaviest, and lightest weights of an array of 10 fish. Thank you Amy
 * Hina you saved my life I owe you my third child.
 * 
 * Current Issues:
 * 
 *
 * @author Kanya Farley
 * @version 5/2/26
*/
import java.util.Scanner;
import java.util.Arrays;
public class fishcount
{
    Scanner kb = new Scanner(System.in);
    final int FISHCOUNT = 10; // length of array
    int[] fish = new int[10];
    int totalWeight;
    int meanWeight;
    int[] fish2 = new int[11];
    /**
     * Constructor for objects of class fishcount
     */
    public fishcount()
    {
        System.out.println("Tell me the weight in grams of the last 10 fish you got and I'll give you some sick stats!");
        System.out.println("Please strictly use arabic numerals or program will crash.");
        
        // user enters fish weights
        for (int i = 0; i < FISHCOUNT; i++) {
            System.out.println("Enter the weight of fish " + i + ": ");
            /*String fishh = kb.nextLine();
            if (kb.hasNextInt()) {
                fish[i] = Integer.parseInt(fishh);
                System.out.println("here");
            } else if (!kb.hasNextInt()) {
                System.out.println("Sorry! That's an invalid character. Please restart program.");
            }*/
            fish[i] = kb.nextInt();
        }
        
        // adds all fish weights together
        for (int i = 0; i < fish.length; i++) {
            totalWeight += fish[i];
        }
        System.out.println("The total overall weight is " + totalWeight + "gs.");
        meanWeight = totalWeight / 10;
        System.out.println("The mean weight is " + meanWeight + "g.");
        
        int heaviestFish = fish[0];
        int lightestFish = fish[0];
        for (int i = 1; i < fish.length; i++) {
            if (fish[i] > heaviestFish) { // fish must be the largest of all values
                heaviestFish = fish[i];
            }
        }
        System.out.println("The heaviest fish is " + heaviestFish + "g.");
        for (int i = 1; i < fish.length; i++) {
            if (fish[i] < lightestFish) { // fish must be the smallest of all values
                lightestFish = fish[i];
            }
        }
        System.out.println("The lightest fish is " + lightestFish + "g.");
        System.out.println("Now you may enter a value to (linear) search for a fish by weight.");
        //String weightSearchh = kb.nextLine();
        //int weightSearch = Integer.parseInt(weightSearchh);
        int weightSearch = kb.nextInt();
        for (int i = 1; i < FISHCOUNT; i++) {
            if (fish[i] == weightSearch) {
                System.out.println("This is fish " + fish[i] + " of the 10 fish you caught!");
            } else {
                //System.out.println("Sorry, but there are no documented fish with that exact weight."); // repeats...?
            }
        }
        //Arrays.sort(fish);
        //System.out.println("Let's do the same thing using a binary search instead. Enter a weight to search for!");
        /*System.out.println("Would you like to add a fish to the array? enter 'y' for yes or 'n' for no");
        String choice = kb.nextLine();
        String yes = "y";
        String no = "n";
        if (choice.contains(yes)) {
            System.out.println("Cool!");
            boolean addFish;
            System.arraycopy(fish, 0, fish2, 0, fish.length);
            System.out.println("Now enter the 11th fish's weight: ");
            fish2[11] = kb.nextInt();
            System.out.println("test: fish 7 = " + fish2[7] + ", and fish 11 = " + fish[11]);
            
        } else if (choice.contains(no)) {
            System.out.println("Okay! My work here is done.");
        } */
        System.out.println("Let's add another fish!");
        boolean addFish;
        System.arraycopy(fish, 0, fish2, 0, fish.length);
        System.out.println("Now enter the 11th fish's weight: ");
        fish2[10] = kb.nextInt();
        System.out.println("Now let's remove a fish from the array! Which fish weight would you like to erase?");
        fish[] = 
    }
}
