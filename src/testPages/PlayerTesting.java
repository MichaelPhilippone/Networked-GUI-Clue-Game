package testPages;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import utils.Utilities;
import clueServer.Player;

/**
 * <b>Class semantics and roles</b>: <br>
 * This class is used to test the functionality of the {@link Player}
 * object and its methods.
 * @author Michael Philippone (code and class skeletons)
 */
public class PlayerTesting {
	
	/**
	 * <b>Purpose</b>: Set up all of the necessary components for 
	 * testing the {@link Player} object.  Then run a few tests 
	 * <br><br>
	 * <b>Preconditions</b>: None.  Nothing is running.
	 * <br>
	 * <b>Postconditions</b>: The {@link Player} has been tested
	 * <br>
	 * <b>Pseudo-Code</b>:  <br>
	 * END
	 */
	public void run() {
		Player player = makePlayer();
		
		System.out.println("Player's username: " + player.getPlayerName());
		System.out.println("Player piece name: " + player.getPieceName());
		
		System.out.println( "READING (from client):  " + player.readFromSocket() );
		player.writeOnSocket("Hello World! (from server)");
		
		//player.start();
	}
	
	/** 
	 * <b>Purpose</b>: Facilitate the creation of a {@link Player} object 
	 * <br><br>
	 * <b>Preconditions</b>: A {@link PlayerTesting} has been established <br>
	 * <b>Postconditions</b>: A {@link Player} object has been tested <br>
	 * <b>Pseudo-Code</b>: <br>
	 * Create an instance of the {@link Player} object 
	 * <br>
	 * END
	 * 
	 * @return Player object instance reference
	 */
	public Player makePlayer() {
		ServerSocket servSock = null;
		try { servSock = new ServerSocket( 5678 ); }
		catch (IOException ioe) { System.out.println("Error creating Server Socket"); }
		
		System.out.println("Starting ServerSocket...");
		Socket skTmp = null;
		try { skTmp = servSock.accept(); }
		catch(IOException ioe) {System.out.println("Error accepting connection");}

		return new Player(
				"happyTesterGuy",			// username
				skTmp,						// socket reference
				Utilities.pieceNames[1],	// piece name ("Miss Scarlett")
				null						// no need to reference a Users class instance here
				);
	}
	
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		/* Open the console output to a FILE: (also redirect error output there too) */
		System.setOut(Utilities.setFileOutput("PlayerTesting.txt"));
		System.setErr(System.out);
		System.out.println("PROGRAM STARTED AT: " + Utilities.getTodaysDate() + "\n\n\n");
		
		
		PlayerTesting pt = new PlayerTesting();
		pt.run();
	}

}
