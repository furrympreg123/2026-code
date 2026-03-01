
/**
 * This provides the constructor with attributes for generating cards
 * 
 * Current problems:
 * .getValue() returns 0 whereas .valueOfFace() returns the right value!
 *
 * @author Kanya Farley
 * @version 26/02/2026
 */
import java.util.Random;
import java.util.ArrayList;
public class Card {
    private String faces;
    private String suits;
    
    static String[] suit = {"Hearts", "Clubs", "Diamonds", "Spades"};
    static String[] face = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
    
    private int value;
    Random r = new Random();
    /**
     * Constructor for objects of class Card
     */
    public Card(String faces, String suits)
    {
        this.faces = faces;
        this.suits = suits;
        this.value = valueOfFace();
    }
    
    public Card() { // takes no arguments
        System.out.println("Card with no attributes has been created.");
        
        // get random suit
        int randSuits = r.nextInt(suit.length);
        this.suits = suit[randSuits];
        
        // get random face
        int randFaces = r.nextInt(face.length);
        this.faces = face[randFaces];
        
        System.out.println(faces + " " + suits + " Value : " + valueOfFace());
    }
    
    public String getFace() {
        return(this.faces);
    }
    
    public String getSuit() {
        return(this.suits);
    }
    
    // defines values based on face
    public int valueOfFace() {
        int value = 0;
        switch (this.faces) {
            case "Ace" : value = 1;
                break;
            case "2" : value = 2;
                break;
            case "3" : value = 3;
                break;
            case "4" : value = 4;
                break;
            case "5" : value = 5;
                break;
            case "6" : value = 6;
                break;
            case "7" : value = 7;
                break;
            case "8" : value = 8;
                break;
            case "9" : value = 9;
                break;
            case "10" : value = 10;
                break;
            case "Jack" : value = 10;
                break;
            case "Queen" : value = 10;
                break;
            case "King" : value = 10;
                break;
        }
        return value;
    }
    
    public int getValue() { // returns 0 every time... somethings wrong
        return(this.value);
    }
}
