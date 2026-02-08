
/**
 * Write a description of class noughtsCrosses here.
 *
 * @author Kanya Farley
 * @version 9/2/2026
 */
import java.util.Scanner;
import java.util.Arrays;
public class noughtsCrosses
{
    String[][] board = new String[5][5];
    Scanner kb = new Scanner(System.in);
    int ocoord1;
    int ocoord2;
    int xcoord1;
    int xcoord2;
    /**
     * Constructor for objects of class noughtsCrosses
     */
    public noughtsCrosses()
    {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                board[i][j]= "[ ]";
            }
            System.out.println(" ");
        }
        System.out.println(Arrays.toString(board));
        System.out.println(board);
        player1Turn();
        getOcoord1();
        getOcoord2();
        System.out.println(board[ocoord1][ocoord2]);
    }
    
    public void player1Turn() {
        System.out.println("It is player 1s turn! (noughts)");
        System.out.println("Enter first coordinate");
        ocoord1 = kb.nextInt();
        System.out.println("Enter second coordinate");
        ocoord2 = kb.nextInt();
    }
    
    public int getOcoord1() {
        return ocoord1;
    }
    
    public int getOcoord2() {
        return ocoord2;
    }
    
    public void player2Turn() {
        System.out.println("It is player 2s turn! (noughts)");
        System.out.println("Enter first coordinate");
        xcoord1 = kb.nextInt();
        System.out.println("Enter second coordinate");
        xcoord2 = kb.nextInt();
    }
    
    public int getXcoord1() {
        return xcoord1;
    }
    
    public int getXcoord2() {
        return xcoord2;
    }
}
