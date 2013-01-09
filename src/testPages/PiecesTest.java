package testPages;

import utils.Utilities;
import clueServer.GameBoard;
import clueServer.Pieces;
import clueServer.GameLogic;
import clueServer.Rooms;

/**
 * <b>Class semantics and roles</b>: <br>
 * This class is used to test the functionality of the {@link Pieces} class and
 * its methods. <br>
 * 
 * @author Todd Wright
 */
public class PiecesTest {

	/**
	 * <b>Purpose</b>: Test the functionalities and expected method return values of the {@link Pieces} class <br>
	 * @param args
	 */
	public static void main(String[] args) {
		
		/* Open the console output to a FILE: (also redirect error output there too) */
		System.setOut(Utilities.setFileOutput("PiecesTest.txt"));
		System.setErr(System.out);
		System.out.println("PROGRAM STARTED AT: " + Utilities.getTodaysDate() + "\n\n\n");
		
		
		// declare Pieces object
		Pieces pieces = new Pieces();

		// declare Gameboard object
		GameBoard board = new GameBoard();

		// declare GameLogic object
		GameLogic gameLogic = new GameLogic();

		// declare Rooms object
		Rooms rooms = new Rooms();
		
		// PIECE UNIT TESTING
		System.out.println("!THIS IS THE UNIT TEST FOR THE PIECES CLASS!");
		System.out.println("--------------------------------------------");
		System.out.println("");
		
		int i;
		System.out
				.println("**These are the starting positions for each player's piece.**");
		// Verify that everyone has a position (these will be starting positions)
		for (i = 0; i < 6; i++) {
			// Print the pieces name at i
			System.out.println(Utilities.pieceNames[i] + "'s start location is:");
			// Print the X and Y pair of that piece
			System.out.println("X: " + pieces.getX(i) + " Y: " + pieces.getY(i));
			System.out.println("");
		}

		System.out.println("------------------------");

		/*
		 * Print the game board to verify with the above loop that all pieces
		 * are being places in their starting positions.
		 */
		System.out.println("**This is a copy of the game board to verify that the locations "
						+ "\nof each piece is correct.**");
		board.printBoard();
		System.out.println(" ");

		System.out.println("------------------------");

		/*
		 * Choose an X and Y coordinate that is occupied and see if isOccupied
		 * will return that someone is occupying the space, except for the last
		 * one, which will return that no one is occupying the space.
		 */
		System.out.println("**This is a test of the isOccupied() method.**" + 
				"\n**Here we pass in different coordinates to see if that space" +
				" is occupied or not.**");
		System.out.println();
		
		// Pass in Miss Scarlet's position. Expected output is 1.
		System.out.println("Pass in Miss Scarlet's position of (7, 24) into" +
				" isOccupied().\nExpected output is 1, because Miss Scarlet is player 1.");
		System.out.println("Output: " + pieces.isOccupied(7, 24)); 
		System.out.println();
		
		// Pass in Professor Plum's position. Expected output is 2.
		System.out.println("Pass in Professor Plum's position of (23, 19) into" +
		" isOccupied().\nExpected output is 2, because Professor Plum is player 2.");
		System.out.println("Output: " + pieces.isOccupied(23, 19));
		System.out.println();
		
		// Pass in Mrs. Peacock's position. Expected output is 3.
		System.out.println("Pass in Mrs. Peacock's position of (23, 5) into" +
		" isOccupied().\nExpected output is 3, because Mrs. Peacock is player 3.");
		System.out.println("Output: " + pieces.isOccupied(23, 5));
		System.out.println();
		
		// Pass in Mr. Green's position. Expected output is 4.
		System.out.println("Pass in Mr. Green's position of (14, 0) into" +
		" isOccupied().\nExpected output is 4, because Mr. Green is player 4.");
		System.out.println("Output: " + pieces.isOccupied(14, 0));
		System.out.println();
		
		// Pass in Mrs. White's position. Expected output is 5.
		System.out.println("Pass in Mrs. White's position of (9, 0) into" +
		" isOccupied().\nExpected output is 5, because Mrs. White's is player 5.");
		System.out.println("Output: " + pieces.isOccupied(9, 0));
		System.out.println();
		
		// Pass in Colonel Mustard's position. Expected output is 6.
		System.out.println("Pass in Colonel Mustard's position of (0, 17) into" +
		" isOccupied().\nExpected output is 6, because Colonel Mustard is player 6.");
		System.out.println("Output: " + pieces.isOccupied(0, 17));
		System.out.println();
		
		// Pass in Kitchen doorway position, which no one is currently occupying. 
		// Expected output is 0 (no player occupies).
		System.out.println("Pass in coordinates (4, 7) which are located in the kitchen." +
				"\nExpected out is 0 because no one is currently occupying that cell.");
		System.out.println("Output: " + pieces.isOccupied(4, 7)); 

		System.out.println("------------------------");

		/*
		 * Relocate Professor Plum and print out his X and Y coordinates to
		 * verify the relocation.
		 */
		System.out.println("**This is a test of the relocate() method.**");
		// Professor Plums original position.
		System.out.println("Professor Plum's position:");
		System.out.println("X: " + pieces.getX(1) + "\nY: " + pieces.getY(1)); 
		System.out.println();
		
		// Relocating Professor Plum to Kitchen doorway Coordinates.
		System.out.println("*Calling relocate() and passing in (4, 7)" +
				"\nas Professor Plum's new coordinates.....*");
		pieces.relocate(1, 4, 7);
		System.out.println();
		
		// Professor Plums new coordinates. Should be Kitchen doorway coordinates.
		System.out.println("Professor Plum's new/current position:" + "\nX: " + pieces.getX(1) + " Y: " + pieces.getY(1)); 

		System.out.println("------------------------");

		/*
		 * Call the guess method in GameLogic and pass in Colonel Mustard,
		 * Wrench, and Kitchen as parameters. Then check if the position of
		 * Colonel Mustard has changed to a position in Kitchen.
		 */
		System.out.println("**This is a test to see if making a guess will" +
				"\nrelocate a player to the correct room.**");
		// Relocate Miss Scarlet to a square in kitchen
		System.out.println("Relocating Miss Scarlet to cell (0, 6) in Kitchen...");
		pieces.relocate(0, 0, 6);
		System.out.println("X: " + pieces.getX(0) + " Y: " + pieces.getY(0));
		System.out.println();
		
		// Print out Colonel Mustard's current X Y coordinate
		System.out.println("Colonel Mustard's current position:");
		System.out.println("X: " + pieces.getX(5) + " Y: " + pieces.getY(5));
		System.out.println();
		
		// Implicate Colonel Mustard in a guess, weapon is not important(Wrench).
		System.out.println("Miss Scarlet implicates Colonel Mustard in a guess...");
		gameLogic.guess(Utilities.pieceNames[5], Utilities.weaponNames[0]);
		System.out.println();
		
		// Colonel Mustards new coordinates (an X and Y pair located in kitchen).
		System.out.println("Colonel Mustard's expected position is (0,0), the first cell in Kitchen:");
		System.out.println("X: " + gameLogic.Board.Piece.getX(5) + " Y: "
				+ gameLogic.Board.Piece.getY(5));
		System.out.println();
		
		// Check with the isAroom method if Colonel Mustards new location is in
		// fact a room. Expected output is true.
		System.out.println("Check if Colonel Mustard's new position is a room using" +
				"\nthe Rooms class' isAroom() method. Expected output is true.");
		System.out.println("Output: " + rooms.isAroom(gameLogic.Board.Piece.getX(5),
				gameLogic.Board.Piece.getY(5)));
	}

}
