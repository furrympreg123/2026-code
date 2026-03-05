
/**
 * This provides a full deck of cards and methods to edit the deck object
 *
 * @author Kanya Farley
 * @version 05/03/2026
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.io.IOException;
import java.io.File;
import java.util.Scanner;
import java.io.FileWriter;

public class Deck
{
    ArrayList<Card> myDeck = new ArrayList<Card>();
    /**
     * Constructor for objects of class Deck
     */
    public Deck()
    {

        String[] suit = {"Hearts", "Clubs", "Diamonds", "Spades"};
        String[] face = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};

        // selects each variation of suit + face
        for (int i = 0; i < suit.length; i++) {
            for (int j = 0; j < face.length; j++) {
                Card currentCard = new Card (face[j], suit[i]);
                myDeck.add(currentCard);
                //System.out.println(currentCard.getSuit() + currentCard.getFace());
                
            }
        }

    }
    
    public void writeDeck() {
        File myFile = new File("deckData.csv");
        try {
            FileWriter writer = new FileWriter(myFile);
            for (Card thisCard : this.myDeck) {
                writer.write(thisCard.getFace() + ", " + thisCard.getSuit() + "\n");
            }
            writer.flush();
            writer.close();
        } catch (IOException e) {
            System.out.println("Error: could not write file.");
        }
    }
    
    public Deck (boolean reading) {
        try{
            File myFile = new File ("deckData.csv");
            Scanner read = new Scanner(myFile);
            
            while (read.hasNextLine()) {
                String line = read.nextLine();
                String[] tempCard = line.split(" , ");
                myDeck.add(new Card(tempCard[0], tempCard[1]));
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void shuffle() {
        Collections.shuffle(this.myDeck);
    }
    
    public Card popCard() {
        return (myDeck.remove(0)); // use when user takes a card
    }
    
    public void printDeck() {
        for (int i = 0; i < myDeck.size(); i++) {
            System.out.println(myDeck.get(i).getCard());
        }
    }
}