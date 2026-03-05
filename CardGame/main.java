
/**
 * This creates new objects in cahoots with the Card class
 *
 * @author Kanya Farley
 * @version 05/03/2026
 */
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
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
        System.out.println(card1.getFace() + " " + card1.getSuit());
        System.out.println(" (Value: " + card1.getValue() + ")");

        /* Card card2 = new Card();
        System.out.println(card2);

        Card card3 = new Card();
        System.out.println(card3);

        Card card4 = new Card();
        System.out.println(card4); */
        
        Deck hi = new Deck();
        hi.printDeck();
        hi.shuffle();
        hi.printDeck();
        hi.writeDeck();
        
        
    }
    
    // this lets user decide number of random cards printed
    public static void userPrintsCards () {
        Scanner kb = new Scanner(System.in);
        System.out.println("How many cards would you like me to print?");
        int cards = kb.nextInt();

        // creates cards according to users input
        ArrayList<Card>cardCreator = new ArrayList<Card>();
        for (int i = 0; i < cards; i++) {
            cardCreator.add(new Card());
        }
        //System.out.println(cardCreator); // just checks how many cards were made

        // prints suits, faces, and values of cards
        for (int i = 0; i < cardCreator.size(); i++) {
            Card currentCard = cardCreator.get(i);
            System.out.println(currentCard.getFace() + " " + currentCard.getSuit());
            System.out.println("Value : " + currentCard.getValue()); // it's always 0??
        }

        // adds card values up
        int totalValue = 0;
        for (int i = 0; i < cardCreator.size(); i++) {
            Card currentCard = cardCreator.get(i);
            totalValue += currentCard.getValue();
        }
        System.out.println(totalValue);
    }
    
    // this allows user to decide if they want to add another random card to an array or finish it
    public static void blackJack () {
        Scanner kb = new Scanner(System.in);
        boolean active = true;
        int totalValue = 0;
        Deck dealer = new Deck(); // initializes a new deck
        
        // introduction
        System.out.println("Welcome to Black Jack!");
        System.out.println("The rules of the game are that the total value of your cards must add up to");
        System.out.println("'21', or as close to 21 as possible.");
        dealer.shuffle();
        ArrayList<Card>cardCreator = new ArrayList<Card>();
        while (active) {
            System.out.println("Hit or stand? Type 'yes' to hit or 'no' to stand");
            String input = kb.nextLine();
            if (input.equals("yes") || input.equals("YES") || input.equals("Yes")) {
                cardCreator.add(new Card());
                totalValue = 0;
                // fetches + prints new card, gets and adds up total value
                for (int i = 0; i < cardCreator.size(); i++) {
                    Card currentCard = cardCreator.get(i);
                    dealer.popCard(); // how do i get it to get rid of specified card? currentCard and i doesnt work
                    totalValue += currentCard.getValue();
                }
                System.out.println("Total value: " + totalValue);
                
                // checks for loss
                if (totalValue > 21) {
                    System.out.println("You have lost! Total exceeds 21 by " + (totalValue - 21));
                    active = false;
                }
            } else if (input.equals("no") || input.equals("NO") || input.equals("No")) {
                System.out.println("Okay!");
                active = false; // stops loop
                System.out.println("Total value: " + totalValue);
                
                // checks for win
                if (totalValue == 21) {
                    System.out.println("Congrats! Your total is exactly 21! You have won.");
                } else if (totalValue < 21) {
                    System.out.println("Your total value is " + (totalValue - 21) + " from 21. Good game!");
                }
            } else {
                System.out.println("Sorry, I don't understand...");
            }
        }
    }
}
