
/**
 * This creates new objects in cahoots with the Card class
 * 
 * Issues:
 * Not sure how to call value of a suit in here...
 *
 * @author Kanya Farley
 * @version 20/02/2026
 */
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
public class main
{
    /**
     * Constructor for objects of class main
     */
    public static void main(String[] args)
    {
        Random random = new Random();
        String[] suits = {"Hearts", "Clubs", "Diamonds", "Spades"};
        String[] faces = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
        
        // prints random face
        String randomFace = faces[random.nextInt(faces.length)];
        //System.out.println(randomFace);
        
        // prints random suit
        String randomSuit = suits[random.nextInt(suits.length)];
        //System.out.println(randomSuit);
        
        
        
        // creates new random card
        Card card1 = new Card(randomFace, randomSuit);
        System.out.println(card1.getSuit());
        System.out.println(card1.getFace() + " (Value: " + card1.getValue() + ")");
        //for (int i = 0; i < Card.size; i++) {
            
        //}
    }
    
    
}
