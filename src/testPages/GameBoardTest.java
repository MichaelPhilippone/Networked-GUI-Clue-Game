package testPages;

import utils.Utilities;
import clueServer.GameBoard;


/**
 * <b>Class semantics and roles</b>: <br>
 * This class is used to test the functionality 
 * of the {@link GameBoard} class and its methods.
 * @author Matt SeGall
 */
public class GameBoardTest {
	
	/**
	 * <b>Purpose</b>: Drive the testing of a {@link GameBoard} object<br>
	 * Call a {@link GameBoard}'s {@code printBoard() } method to verify that
	 * the board is constructed properly
	 * @param args
	 */
	public static void main(String[] args) { 
		
		/* Open the console output to a FILE: (also redirect error output there too) */
		System.setOut(Utilities.setFileOutput("GameBoardTest.txt"));
		System.setErr(System.out);
		System.out.println("PROGRAM STARTED AT: " + Utilities.getTodaysDate() + "\n\n\n");
		
		
		GameBoard board = new GameBoard();
		board.printBoard();
	}
}
