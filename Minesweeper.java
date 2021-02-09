
/**
 * Lab 13 - Minesweeper game
 *
 * @author Austin Ellsworth
 * @version December 9, 2020
 */
import java.util.*;

public class Minesweeper
{
    // instance variables
    public static final int SIZE = 10;
    public static final int NUM_MINES = 5;
    public static final char EMPTY = '.';
    public static final char MINE = '*';
    public static final char CLEARED = ' ';
    public static final char FLAG = 'F';

    private char [][] board;
    private char [][] flags;
    private int numCorrectFlags;

    public Minesweeper() {
        board = new char[SIZE][SIZE];
        flags = new char[SIZE][SIZE];
        numCorrectFlags = 0;

        for ( int r=0; r < SIZE; r++) {
            for (int c=0; c < SIZE; c++) {
                board[r][c] = EMPTY;
                flags[r][c] = EMPTY;
            }
        }

        Random rand = new Random();
        for (int m = 0; m < NUM_MINES; m++) {
            boolean isPlaced = false;
            while (! isPlaced) {
                int row = rand.nextInt(SIZE);
                int col = rand.nextInt(SIZE);
                if (board[row][col] == EMPTY) {
                    board[row][col] = MINE;
                    isPlaced = true;
                }

            }
        }
    }

    
    //hides mines and overlays flags from flag array if they exist
    public void printBoard(){
        //print columns
        System.out.print(" ");
        for (int c=0; c < SIZE; c++) {
            System.out.print(" " + c);
        }
        System.out.println();
        // print rows
        for (int r=0; r < SIZE; r++) {
            System.out.print((char)('A' + r) + " ");
            for (int c = 0; c < SIZE; c++) {
                if (flags[r][c] == FLAG) {
                    System.out.print(flags[r][c] + " ");
                }
                else if (board[r][c] == MINE) {
                    System.out.print(EMPTY + " ");
                }
                else {
                    System.out.print(board[r][c] + " ");
                }
            }
            System.out.println();
        }

    }

    
    //play method with main loop
    public void play() {
        Scanner keyboard = new Scanner(System.in);
        boolean gameOver = false;
        //while game played
        while (! gameOver) {
            //print board
            printBoard();

            char action = keyboard.next().charAt(0);
            if (action == 'S') {
                int row = keyboard.next().charAt(0) - 'A';
                int col = keyboard.nextInt();
                select(row, col);
            }
            else if (action == 'F') {
                int row = keyboard.next().charAt(0) - 'A';
                int col = keyboard.nextInt();
                gameOver = flag(row, col);
            }
            else if (action == 'Q') {
                return;
            }
            else {
                System.out.println("Incorrect input: " + action);
            }
        }

        
    }

    
    //checks for mine at row, col
    public int checkForMine(int row, int col) {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
            return 0;
        }

        if (board[row][col] == MINE) {
            return 1;
        }

        else {
            return 0;
        }
    }

    //checks for checks for mines in all adjacent positions and reveals number of mines
    public void reveal(int row, int col) {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
            return;
        }

        if (board[row][col] == MINE) {
            return;
        }

        int mineCount = 
            checkForMine(row-1, col-1) +
            checkForMine(row-1, col) +
            checkForMine(row-1, col+1) +
            checkForMine(row, col-1) +
            checkForMine(row, col+1) +
            checkForMine(row+1, col-1) +
            checkForMine(row+1, col) +
            checkForMine(row+1, col+1);
        board[row][col] = (char) ('0' + mineCount);

    }
    
    //return true if game is now over
    public boolean select(int row, int col) {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
            System.out.println("That is off the board");
            return false;
        }

        if (board[row][col] == MINE) {
            System.out.println("You hit a mine, game over.");
            return true;
        }

        //clear the given row, col and reveal adjacent positions
        board[row][col] = CLEARED;
        reveal(row-1, col-1);
        reveal(row-1, col);
        reveal(row-1, col+1);
        reveal(row, col-1);
        reveal(row, col+1);
        reveal(row+1, col-1);
        reveal(row+1, col);
        reveal(row+1, col+1);

        return false;

    }

    
    //returns true if all flags are correctly set
    public boolean flag(int row, int col) {
        if (row < 0 || row >= SIZE || col < 0 || col >= SIZE) {
            return false;
        }

        if (flags[row][col] == FLAG) {
            flags[row][col] = EMPTY;
            if (board[row][col] == MINE) {
                numCorrectFlags--;
            }
        }

        else {
            flags[row][col] = FLAG;
            if (board[row][col] == MINE) {
                numCorrectFlags++;
            }
        }
        
        
        if (numCorrectFlags == NUM_MINES) {
            System.out.println("You won!");
            return true;
        }
        else {
            return false;
        }
    }

    public static void main(String[] a){

        Minesweeper game = new Minesweeper();
        game.play();
    }

}
