package testPages;

import utils.Utilities;
import clueNetwork.ClientSocket;
import clueServer.Player;
import clueServer.Server;
import clueServer.ServerListener;
import clueServer.Users;

/**
 * <b>Class semantics and roles</b>: <br>
 * This class is used to test the unification of the following objects: <br>
 * - {@link Player} <br>
 * - {@link ServerListener} <br>
 * - {@link Users} <br>
 * - {@link ClientSocket} (via {@link Player}) <br>
 * 
 * @author Michael Philippone (code and class skeletons)
 */
public class UserManagementComponentsTest {
	
	/**
	 * <b>Purpose</b>: Test the {@link Users} class and all subclasses on the class list hierarchy <br><br>
	 * <b>Precondition</b>: there is a null internal {@link Users} object reference  <br>
	 * <b>Postcondition</b>: there is an initialized internal {@link Users} object reference <br>
	 * <b>Pseudo-Code</b>: <br>
	 * BEGIN <br>
	 * -> Instantiates the internal {@link Users} object. (Uses a null value for the {@link Server} object reference)
	 * -> -> this also starts the {@link ServerListener} listening <br>
	 * -> -> waits (via {@link ServerListener}) for 6 clients to connect <br>
	 * -> sends and receives some messages with the clients <br>
	 * END <br>
	 * @param args command line options (if any)
	 */
	public static void main(String[] args) {
		
		/* Open the console output to a FILE: (also redirect error output there too) */
		System.setOut(Utilities.setFileOutput("UserManagementsComponentsTest.txt"));
		System.setErr(System.out);
		System.out.println("PROGRAM STARTED AT: " + Utilities.getTodaysDate() + "\n\n");
		
		
		System.out.println("Testing the User Management Components.");
		System.out.println("This is done in several steps:");
		System.out.println("  1) First, test the ability of the system to create a Users object");
		System.out.println("  2) Once the Users object is created, listen for incoming connection requests ");
		System.out.println("      -> creating players until there are the required numbers (specifically, "+Utilities.numRequiredPlayers+")");
		System.out.println("  3) Once the players are all connected, print out their metaData");
		System.out.println("      -> To show that they are in fact connected and to prove that all of the data was sent on the Socket.");
		System.out.println("  4) Then test writing a string to all of the players");
		System.out.println("	  -> To simulate an update in the actual game play");
		System.out.println("");
		System.out.println("");
		
		// create an instance of a Users class:
		//   (pass null value for Server reference, 
		//    we don't use the Server class in this test)
		System.out.println("Creating the Users object then listening for requests...");
		Users users = new Users(null);
		
		System.out.println("Players connected...");
		System.out.println("Players' metadata: ");
		for(int i=0; i < users.usersCount(); i++) {
			System.out.print( "T_"+users.getPlayer(i).getName() + " ");
			System.out.println("Player's name: " + users.getPlayer(i).getName());
			
			System.out.print( "T_"+users.getPlayer(i).getName() + " ");
			System.out.println( "Player's piece: " + users.getPlayer(i).getPieceName() );	
			
			System.out.print( "T_"+users.getPlayer(i).getName() + " ");
			System.out.println( "Player's connection order: " + users.getOrder( users.getPlayer(i).getName() ) );
		}
		
		// send some string to all the users
		System.out.println("Writing to all players...");
		int[] playersAffected = {0,1,2,3,4,5};
		users.update( playersAffected , "This is an update for all clients!");
		System.out.println("");
		System.out.println("");
		System.out.println("Done. Exiting.");
	}
}
