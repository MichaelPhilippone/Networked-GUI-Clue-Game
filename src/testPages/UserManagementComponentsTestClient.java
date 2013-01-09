package testPages;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import utils.Utilities;
import clueNetwork.ClientSocket;

public class UserManagementComponentsTestClient {

	/**
	 * 
	 * @author Michael Philippone
	 */
	private static class ClientThread implements Runnable {
		
		/** username for this thread to write to server */
		private String username = "";
		
		/**
		 * <b>Purpose</b>: construct the thread and assign username <br>
		 * @param name the name string passed into this object
		 */
		public ClientThread(String name) { 
			this.username = name; 
			System.out.println( "FROM T_"+ this.username + ": Starting test-client Thread " + name + " ...");
		}
		
		/**
		 * <b>Purpose</b>: When this class is used to create a {@link Thread}, this method is called by the {@link Thread}'s {@code start()} method <br>
		 * <em>Michael Philippone, 12 DEC 2008</em> <br>  
		 */
		public void run() {
			ClientSocket cs = null;
			try {
				
				/*  Create a socket (to use in implementing a ClientSocket and 
				 * Set its timeout value to 50 seconds: */
				Socket sk = new Socket( "127.0.0.1", Utilities.defaultPort);
				
				sk.setSoTimeout(1000*50);
				
				cs = new ClientSocket( sk );

				while(!cs.send(this.username));
				
				if(sk.isConnected() && !sk.isClosed())
					System.out.println( "FROM T_"+ this.username + ":  Reading from server: " + cs.read() );
				else
					System.out.println( "FROM T_"+ this.username + ":  Error reading from the server, socket has closed" );
				
			}catch (SocketTimeoutException ste) {
				System.out.println( "FROM T_"+ this.username + ":  Error utilizing socket connection (Socket Timeout Exception, after 50 seconds)" );
			} catch(UnknownHostException uhe) { 
				System.out.println( "FROM T_"+ this.username + ":  Error opening socket connection (UnknownHostException)" );
			} catch(IOException ioe) {
				System.out.println( "FROM T_"+ this.username + ":  Error opening socket connection (IOException)" );
			}
		}
	}
	
	
	/**
	 * <b>Purpose</b>: Drive the testing of the User management components <br><br>
	 * <b>Pseudo-Code</b>: <br>
	 * BEGIN <br>
	 * -> FOR the number of required players for the game: <br> 
	 * -> -> create a new {@link Thread} using the {@link ClientThread} inner class <br>
	 * -> -> start the {@link Thread} <br>
	 * END <br>
	 * @param args
	 */
	public static void main(String[] args) {
		long time = System.currentTimeMillis();
		long timePast;
		
		/* Open the console output to a FILE: (also redirect error output there too) */
		System.setOut(Utilities.setFileOutput("UserManagementComponentsTestClient.txt"));
		System.setErr(System.out);
		System.out.println("PROGRAM STARTED AT: " + Utilities.getTodaysDate() + "\n\n\n");
		
		System.out.println("Testing the User Management Components. ( a threaded testing driver ) ");
		System.out.println("This is done in several steps:");
		System.out.println("  1) First, test the connection-handling abilities of the Users class");
		System.out.println("	  -> spawn threads (specifically, "+Utilities.numRequiredPlayers+") " +
				"to connect to the listening Users class");
		System.out.println("  2) Once connected, mimic a Client");
		System.out.println("	  -> Do so by sending one string (the username) for Player " +
				"object creation upon connection");
		System.out.println("  3) Next, test the error handling");
		System.out.println("	  -> Try to connect too many clients to the Users listener");
		System.out.println("	  -> This should result in those threads labeled with AnnotherPlayer# "); 
		System.out.println("          never hearing back from the server process and hanging.");
		System.out.println("          (This testing driver will automatically shutdown after 2 minutes.)");
		
		System.out.println("");
		System.out.println("");
		
		
		for(int i=0; i <Utilities.numRequiredPlayers; i++) {
			new Thread( new ClientThread("Player_"+i) ).start();
		}
		
		for(int i=0; i <Utilities.numRequiredPlayers+1; i++) {
				new Thread( new ClientThread("AnotherPlayer_"+i) ).start();
		}
		
		
		/* Keep this thread testing for 2 minutes having passed
		 * When it has, kill the program, This will disallow hanging waiting for a closed server */
		while(true) {
			timePast = System.currentTimeMillis();
			if( timePast - time >= (1000 * 60 * 2) ) {
				System.out.println("USER-MANGEMENT-COMPONENTS-TEST_CLIENT: \n"+
						"            started at "+ (((time)/1000)/60) +
						", ended at "+ (((timePast)/1000)/60) +
						" ran for "+(((timePast-time)/1000)/60)+" minutes.");
				System.exit(0);
			}
		}
		
	}
}
