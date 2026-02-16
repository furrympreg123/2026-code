
/**
 * This is the game noughts and crosses!
 *
 * @author Kanya Farley
 * @version 16/2/2026
 */
import java.util.Scanner;
import java.util.Arrays;
import java.io.IOException;
import java.io.File;
import java.io.FileWriter;
public class noughtsCrosses
{
    char[][] board = new char[3][3];
    Scanner kb = new Scanner(System.in);
    int oRow;
    int oCol;
    int xRow;
    int xCol;
    char p1 = 'O';
    char p2 = 'X';
    boolean winner = false;
    /**
     * Constructor for objects of class noughtsCrosses
     */
    public noughtsCrosses()
    {
        for (int i = 0; i < board.length; i++) { // initializes grid
            for (int j = 0; j < board[0].length; j++) {
                board[i][j]= ' ';
            }
        }
        printBoard();
        System.out.println(Arrays.toString(board));
        System.out.println(board);
        while (true) {
            player1Turn();
            player1Print();
            player2Turn();
            player2Print();
            player1wins();
            player2wins();
            saveGame();
        }
        /*if (Winner(board, p1)) { // unsure how to solve this but will check later
                printBoard();
                System.out.println("Player 1 (noughts) wins!"); 
            } else if (Winner(board, p2)) {
                printBoard();
                System.out.println("Player 2 (crosses) wins!");
            } else if (moves == 9) {
                printBoard();
                System.out.println("It's a draw!");
            }*/
    }
    
    public void printBoard() {
        System.out.println(" 0   1   2 ");
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(" " + board[i][j]);
                
                // prints "|" between each collumn
                if (j < 2) System.out.print(" |");
            }
            System.out.println();
            // prints a line between each row
            if (i < 2) System.out.println("---+---+---");
        }
    }
    
    public void player1Turn() {
        System.out.println("It is player 1s turn! (noughts)");
        System.out.println("Enter first coordinate");
        oRow = kb.nextInt();
        System.out.println("Enter second coordinate");
        oCol = kb.nextInt();
    }
    
    public int getOcoord1() {
        return oRow;
    }
    
    public int getOcoord2() {
        return oCol;
    }
    
    public void player1Print() {
        getOcoord1();
        getOcoord2();
        if (board[oRow][oCol] == ' ') {
            board[oRow][oCol] = p1;
        } else if (board[oRow][oCol] != ' ') {
            System.out.println("This spot on the grid is already taken! Try again.");
            player2Turn();
        }
        printBoard();
    }
    
    public void player2Turn() {
        System.out.println("It is player 2s turn! (crosses)");
        System.out.println("Enter first coordinate");
        xRow = kb.nextInt();
        if (xRow > 4) {
            System.out.println("Coordinate out of bounds, please retry!");
        }
        System.out.println("Enter second coordinate");
        xCol = kb.nextInt();
    }
    
    public int getXcoord1() {
        return xRow;
    }
    
    public int getXcoord2() {
        return xCol;
    }
    
    public void player2Print() {
        getXcoord1();
        getXcoord2();
        if (board[xRow][xCol] == ' ') {
            board[xRow][xCol] = p2;
        } else if (board[xRow][xCol] != ' ') {
            System.out.println("This spot on the grid is already taken! Try again.");
            player2Turn();
        }
        board[xRow][xCol] = p2;
        printBoard();
    }
    
    // following 2 methods intended to check for wins, but don't work yet...
    
    public boolean player1wins() {
        for (int i = 0; i < 3; i++) {
            // Checks rows and columns
            if ((board[i][0] == p1 && board[i][1] == p1 && board[i][2] == p1) ||
                (board[0][i] == p1 && board[1][i] == p1 && board[2][i] == p1)) {
                return true;
            }
        }
        // Check diagonals
        if ((board[0][0] == p1 && board[1][1] == p1 && board[2][2] == p1) ||
        (board[0][2] == p1 && board[1][1] == p1 && board[2][0] == p1)) {
            return true;
        }
        return false;
    }
    
    public boolean player2wins() {
        for (int i = 0; i < 3; i++) {
            // Checks rows and columns
            if ((board[i][0] == p2 && board[i][1] == p2 && board[i][2] == p2) ||
                (board[0][i] == p2 && board[1][i] == p2 && board[2][i] == p2)) {
                return true;
            }
        }
        // Check diagonals
        if ((board[0][0] == p2 && board[1][1] == p2 && board[2][2] == p2) ||
        (board[0][2] == p2 && board[1][1] == p2 && board[2][0] == p2)) {
            return true;
        }
        return false;
    }
    
    public void saveGame() {
        System.out.println("Checkpoint! To save game enter 'yes', otherwise game will continue");
        String hrm = kb.nextLine();
        if (hrm == "yes") {
            System.out.println("Now saving game!");
            File save = new File("last_save.txt");
            try {
                FileWriter fw = new FileWriter(save);
                for (int i = 0; i < board.length; i++) { // initializes grid
                    for (int j = 0; j < board[0].length; j++) {
                        fw.write(board[i][j] + "\n");
                    }
                }
                
                fw.flush();
                fw.close();
            } catch (IOException e) {
                System.out.println("Error: game could not be saved");
            }
        }
    }
}
