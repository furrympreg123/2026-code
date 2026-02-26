
/**
 * Write a description of class Deck here.
 *
 * @author Kanya Farley
 * @version 27/2/26
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
public class Deck
{
    ArrayList<Card> myDeck = new ArrayList<Card>();
    /**
     * Constructor for objects of class Deck
     */
    public Deck()
    {
        /**
         * add two arrays (suits and faces),  

        this time rather than selecting one of each, use nested loops to
        select each variation of a card's face and suit, 

        Use each combination of these to create a new 'Card' object and, 

        Add the 'Card' object to the ArrayList you created
         */

        String[] suit = {"Hearts", "Clubs", "Diamonds", "Spades"};
        String[] face = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};

        // selects each variation of suit + face
        for (int i = 0; i < suit.length; i++) {
            for (int j = 0; j < face.length; j++) {
                Card currentCard = new Card (suit[i], face[j]);
                myDeck.add(currentCard);
                System.out.println(currentCard.getSuit() + currentCard.getFace());
            }
        }

    }
    
    public void shuffle() {
        Collections.shuffle(this.myDeck);
    }
    
    public Card popCard() {
        return (myDeck.remove(0));
    }
}