package testPages;

import utils.Utilities;
import clueServer.GameLogic;

public class GameLogicTest {

	public static void main(String[] args) {
		
		/* Open the console output to a FILE: (also redirect error output there too) */
		System.setOut(Utilities.setFileOutput("GameLogicTest.txt"));
		System.setErr(System.out);
		System.out.println("PROGRAM STARTED AT: " + Utilities.getTodaysDate() + "\n\n\n");
		
		
		// declare GameLogic object
		GameLogic gameLogic = new GameLogic();
		
		
		// GUESS TESTING
		
		System.out.println("**Below are the tests for the guess() method.  " +
				"\nTesting all combinations of true and false**");
		// Correct input
		// T-T-T
		//RELOCATE MISS SCARLET TO A ROOM CELL
		gameLogic.Board.Piece.relocate(0, 0, 6);
		System.out.println(gameLogic.guess(gameLogic.Card.getDeck()[6][2], gameLogic.Card.getDeck()[6][0]).cardName1);
		System.out.println(gameLogic.guess(gameLogic.Card.getDeck()[6][2], gameLogic.Card.getDeck()[6][0]).cardName2);
		System.out.println(gameLogic.guess(gameLogic.Card.getDeck()[6][2], gameLogic.Card.getDeck()[6][0]).cardName3);
		System.out.println(gameLogic.guess(gameLogic.Card.getDeck()[6][2], gameLogic.Card.getDeck()[6][0]).Disprover);
		System.out.println("------");
		// Incorrect input
		// T-T-F
		//RELOCATE MISS SCARLET TO A HALLWAY CELL
		gameLogic.Board.Piece.relocate(0, 6, 5);
		System.out.println(gameLogic.guess(gameLogic.Card.getDeck()[6][2], gameLogic.Card.getDeck()[6][0]).cardName1);
		System.out.println(gameLogic.guess(gameLogic.Card.getDeck()[6][2], gameLogic.Card.getDeck()[6][0]).cardName2);
		System.out.println(gameLogic.guess(gameLogic.Card.getDeck()[6][2], gameLogic.Card.getDeck()[6][0]).cardName3);
		System.out.println(gameLogic.guess(gameLogic.Card.getDeck()[6][2], gameLogic.Card.getDeck()[6][0]).Disprover);
		System.out.println("------");
		// T-F-T
		System.out.println(gameLogic.guess(gameLogic.Card.getDeck()[6][2], gameLogic.Card.getDeck()[3][2]).cardName1);
		System.out.println(gameLogic.guess(gameLogic.Card.getDeck()[6][2], gameLogic.Card.getDeck()[3][2]).cardName2);
		System.out.println(gameLogic.guess(gameLogic.Card.getDeck()[6][2], gameLogic.Card.getDeck()[3][2]).cardName3);
		System.out.println(gameLogic.guess(gameLogic.Card.getDeck()[6][2], gameLogic.Card.getDeck()[3][2]).Disprover);
		System.out.println("------");
		// T-F-F
		System.out.println(gameLogic.guess(gameLogic.Card.getDeck()[6][2], gameLogic.Card.getDeck()[5][1]).cardName1);
		System.out.println(gameLogic.guess(gameLogic.Card.getDeck()[6][2], gameLogic.Card.getDeck()[5][1]).cardName2);
		System.out.println(gameLogic.guess(gameLogic.Card.getDeck()[6][2], gameLogic.Card.getDeck()[5][1]).cardName3);
		System.out.println(gameLogic.guess(gameLogic.Card.getDeck()[6][2], gameLogic.Card.getDeck()[5][1]).Disprover);
		System.out.println("------");
		// F-T-T
		System.out.println(gameLogic.guess(gameLogic.Card.getDeck()[2][1], gameLogic.Card.getDeck()[6][0]).cardName1);
		System.out.println(gameLogic.guess(gameLogic.Card.getDeck()[2][1], gameLogic.Card.getDeck()[6][0]).cardName2);
		System.out.println(gameLogic.guess(gameLogic.Card.getDeck()[2][1], gameLogic.Card.getDeck()[6][0]).cardName3);
		System.out.println(gameLogic.guess(gameLogic.Card.getDeck()[2][1], gameLogic.Card.getDeck()[6][0]).Disprover);
		System.out.println("------");
		// F-T-F
		System.out.println(gameLogic.guess(gameLogic.Card.getDeck()[5][0], gameLogic.Card.getDeck()[6][0]).cardName1);
		System.out.println(gameLogic.guess(gameLogic.Card.getDeck()[5][0], gameLogic.Card.getDeck()[6][0]).cardName2);
		System.out.println(gameLogic.guess(gameLogic.Card.getDeck()[5][0], gameLogic.Card.getDeck()[6][0]).cardName3);
		System.out.println(gameLogic.guess(gameLogic.Card.getDeck()[5][0], gameLogic.Card.getDeck()[6][0]).Disprover);
		System.out.println("------");
		// F-F-T
		System.out.println(gameLogic.guess(gameLogic.Card.getDeck()[1][2], gameLogic.Card.getDeck()[5][2]).cardName1);
		System.out.println(gameLogic.guess(gameLogic.Card.getDeck()[1][2], gameLogic.Card.getDeck()[5][2]).cardName2);
		System.out.println(gameLogic.guess(gameLogic.Card.getDeck()[1][2], gameLogic.Card.getDeck()[5][2]).cardName3);
		System.out.println(gameLogic.guess(gameLogic.Card.getDeck()[1][2], gameLogic.Card.getDeck()[5][2]).Disprover);
		System.out.println("------");
		// F-F-F
		System.out.println(gameLogic.guess(gameLogic.Card.getDeck()[6][1], gameLogic.Card.getDeck()[2][1]).cardName1);
		System.out.println(gameLogic.guess(gameLogic.Card.getDeck()[6][1], gameLogic.Card.getDeck()[2][1]).cardName2);
		System.out.println(gameLogic.guess(gameLogic.Card.getDeck()[6][1], gameLogic.Card.getDeck()[2][1]).cardName3);
		System.out.println(gameLogic.guess(gameLogic.Card.getDeck()[6][1], gameLogic.Card.getDeck()[2][1]).Disprover);
		System.out.println("------");
		// Non-existant cards for all inputs.
		System.out.println(gameLogic.guess("apple", "banana").cardName1);
		System.out.println(gameLogic.guess("apple", "banana").cardName2);
		System.out.println(gameLogic.guess("apple", "banana").cardName3);
		System.out.println(gameLogic.guess("apple", "banana").Disprover);
		
		System.out.println("-------------------");
		
		//ACCUSE TESTING
		System.out.println("**Below are the tests for the accuse() method.  " +
				"\nTesting all combinations of true and false**");
		// Correct input
		//T-T-T
		System.out.println("Calling accuse() and passing in T-T-T. Expected output is true.");
		System.out.println("Output:" + gameLogic.accuse(gameLogic.Card.getDeck()[6][2], gameLogic.Card.getDeck()[6][0], gameLogic.Card.getDeck()[6][1]));
		System.out.println();
		// Incorrect input
		//T-T-F
		System.out.println("Calling accuse() and passing in T-T-F. Expected output is false.");
		System.out.println("Output:" + gameLogic.accuse(gameLogic.Card.getDeck()[6][2], gameLogic.Card.getDeck()[6][0], gameLogic.Card.getDeck()[1][2]));
		System.out.println();
		//T-F-T
		System.out.println("Calling accuse() and passing in T-F-T. Expected output is false.");
		System.out.println("Output:" + gameLogic.accuse(gameLogic.Card.getDeck()[6][2], gameLogic.Card.getDeck()[3][2], gameLogic.Card.getDeck()[6][1]));
		System.out.println();
		//T-F-F
		System.out.println("Calling accuse() and passing in T-F-F. Expected output is false.");
		System.out.println("Output:" + gameLogic.accuse(gameLogic.Card.getDeck()[6][2], gameLogic.Card.getDeck()[5][1], gameLogic.Card.getDeck()[2][1]));
		System.out.println();
		//F-T-T
		System.out.println("Calling accuse() and passing in F-T-T. Expected output is false.");
		System.out.println("Output:" + gameLogic.accuse(gameLogic.Card.getDeck()[2][1], gameLogic.Card.getDeck()[6][0], gameLogic.Card.getDeck()[6][1]));
		System.out.println();
		//F-T-F
		System.out.println("Calling accuse() and passing in F-T-F. Expected output is false.");
		System.out.println("Output:" + gameLogic.accuse(gameLogic.Card.getDeck()[5][0], gameLogic.Card.getDeck()[6][0], gameLogic.Card.getDeck()[6][0]));
		System.out.println();
		//F-F-T
		System.out.println("Calling accuse() and passing in F-F-T. Expected output is false.");
		System.out.println("Output:" + gameLogic.accuse(gameLogic.Card.getDeck()[1][2], gameLogic.Card.getDeck()[5][2], gameLogic.Card.getDeck()[6][1]));
		System.out.println();
		//F-F-F
		System.out.println("Calling accuse() and passing in F-F-F. Expected output is false.");
		System.out.println("Output:" + gameLogic.accuse(gameLogic.Card.getDeck()[6][1], gameLogic.Card.getDeck()[2][1], gameLogic.Card.getDeck()[4][2]));
		System.out.println();
		//Non-existent cards for all inputs.
		System.out.println("Calling accuse() and passing in apple-banana-orange (These cards " +
				"\ndo not exist!).  Expected output is false.");
		System.out.println("Output:" + gameLogic.accuse("apple", "banana", "orange"));
		
		System.out.println("-------------------");
		
		//NEXTPLAYER TESTING
		System.out.println("**Below are the tests for the nextPlayer() method.**");
		System.out.println("Player: " + gameLogic.currentPlayer + " <--- This is the first player's turn.");
		System.out.println("Then we run the nextPlayer() method.");
		gameLogic.nextPlayer();
		
		System.out.println("Player: " + gameLogic.currentPlayer + " <--- This is the second player's turn.");
		System.out.println("Then we run the nextPlayer() method.");
		gameLogic.nextPlayer();
		
		System.out.println("Player: " + gameLogic.currentPlayer + " <--- This is the third player's turn.");
		System.out.println("Then we run the nextPlayer() method.");
		gameLogic.nextPlayer();
		
		System.out.println("Player: " + gameLogic.currentPlayer + " <--- This is the fourth player's turn.");
		System.out.println("Then we run the nextPlayer() method.");
		gameLogic.nextPlayer();
	
		System.out.println("Player: " + gameLogic.currentPlayer + " <--- This is the fifth player's turn.");
		System.out.println("Then we run the nextPlayer() method.");
		gameLogic.nextPlayer();
		
		System.out.println("Player: " + gameLogic.currentPlayer + " <--- This is the sixth player's turn.");
		System.out.println("Then we run the nextPlayer() method.");
		gameLogic.nextPlayer();
		
		System.out.println("Player: " + gameLogic.currentPlayer + " <--- Return back to the first player's turn.");
		
		System.out.println("-------------------");
		
		//MOVE TESTING
		System.out.println("**Below are the tests for the move() method.**");
		//LOCATION OF A HALLWAY CELL.
		System.out.println("This board reflects the position of " +
				"\nMiss Scarlet (R) at (5,18).");
		gameLogic.Board.Piece.relocate(0, 5, 18); // hallway cell
		gameLogic.Board.serialize();
		gameLogic.Board.printBoard();
		System.out.println("");
		System.out.println("");
		gameLogic.move();
		System.out.println("This board reflects Miss Scarlet's (R) " +
				"\navailable spaces to move with her roll.");
		gameLogic.Board.printBoard();
		System.out.println("");
		System.out.println("");
		//MOVE FROM HALLWAY TO ROOM.
		System.out.println("This board reflects the relocation " +
				"\nof Miss Scarlet (R) into a room.");
		gameLogic.Board.Piece.relocate(0, 0, 21);
		gameLogic.Board.serialize();
		gameLogic.Board.printBoard();
		System.out.println("");
		System.out.println("");
		gameLogic.move();
		System.out.println("This board reflects Miss Scarlet's (R) " +
				"\navailable spaces to move with her roll.");
		gameLogic.Board.printBoard();
		System.out.println("");
		System.out.println("");
	}
}
