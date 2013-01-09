package testPages;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import utils.Utilities;

import clueNetwork.ClientSocket;

/**
 * <b>Class semantics and roles</b>: <br>
 * This class is used to test the functionality of the {@link ClientSocket}
 * object and its methods. <br>
 * This class is one (of two) used to detect and communicate with the 
 * connections from the second class, {@link NetTestClient}.
 * @author Michael Philippone (code and class skeletons)
 * @author Erich Schudt (code)
 */
public class NetTestServer {
	
	/**
	 * <b>Purpose</b>: Test a ClientSocket object's ability to send and receive information 
	 * <br><br>
	 * <b>Pseudo-Code</b>: <br>
	 * BEGIN <br>
	 * -> Create a {@link ServerSocket} to listen for incoming connection requests <br>
	 * -> link connection to an active {@link Socket} object <br>
	 * -> with the new {@link Socket} object, create an active {@link ClientSocket} object <br>
	 * -> send and receive packets with endpoint {@link ClientSocket} connection <br>
	 * -> close the connection <br> 
	 * END <br>
	 * @param args
	 */
	public static void main(String[] args) {
		
		/* Open the console output to a FILE: (also redirect error output there too) */
		System.setOut(Utilities.setFileOutput("NetTestServer.txt"));
		System.setErr(System.out);
		System.out.println("PROGRAM STARTED AT: " + Utilities.getTodaysDate() + "\n\n\n");
		
		
		int port = 1234;
		
		System.out.println("Server Startup");
		System.out.println("5 Steps to test ClientSocket (test server):");
		System.out.println("  1) Create a ServerSocket to accept connections");
		System.out.println("  2) accept a connection from another ClientSocket instance");
		System.out.println("  3) ServerSocket spawns ClientSocket based on incoming connection");
		System.out.println("  4) Send and receive packets");
		System.out.println("  5) Close the socket connection");
		
		// STEP 01
		ServerSocket serverSock = null;		
		try { 
			serverSock = new ServerSocket(port); 
			System.out.println("SERVER: Step 01 Success");
		} catch (IOException ioe) { System.out.println("SERVER: Error with Server Socket"); }
		
		// STEP 02
		Socket socket = null;
		try {
			socket = serverSock.accept();
			System.out.println("SERVER: step 2 is also good");
		} catch(IOException ioe) { System.out.println("SERVER: Error with connection"); }
		
		
		// STEP 03
		ClientSocket clientSocket = new ClientSocket(socket);
		System.out.println("SERVER: step 3 as good as good");
		
		// STEP 04
		System.out.println("SERVER: time for string passing");
		System.out.println( "SERVER: from client:  " + clientSocket.read());
		clientSocket.send("ha ha, i got it to work");
		System.out.println("SERVER: If both sockets displayed their receipts, Step 4 is Successful");
		
		// STEP 05
		clientSocket.close();
		System.out.println("SERVER: the connection has been closed, Step 5 success");

	}
	
	////////////////////////// OLD /////////////////////////
	///////////////REPLACED BY ERICH'S CODE/////////////////
	////////////////// (Pasted above) //////////////////////
	
	/*
	/**
	 * <b>Purpose</b>: Set up all of the necessary components for 
	 * testing the {@link ClientSocket} object.  Then run a 
	 * simple send and receive test on the {@link ClientSocket} 
	 * <br><br>
	 * <b>Preconditions</b>: None.  Nothing is running.
	 * <br>
	 * <b>Postconditions</b>: The {@link ClientSocket} has been tested
	 * <br>
	 * <b>Pseudo-Code</b>:  <br>
	 * Create an instance of a {@link ServerSocket}  <br>
	 * listen for a connection with the ServerSocket  <br>
	 * when a connection is detected, spawn a connected {@link Socket} object  <br>
	 * Pass that {@link Socket} object into a {@link ClientSocket}   <br>
	 * read a line from the {@link ClientSocket}  <br>
	 * write a line to {@link ClientSocket}  <br>
	 * END
	public void run() {
		ServerSocket servSock = null;
		try { servSock = new ServerSocket( 5678 ); }
		catch (IOException ioe) { System.out.println("Error creating Server Socket"); }
		
		Socket skTmp = null;
		try { skTmp = servSock.accept(); }
		catch(IOException ioe) {System.out.println("Error accepting connection");}
		
		ClientSocket sk = new ClientSocket(skTmp);
		System.out.println( "READING (from client):  " + sk.read() );
		sk.send("Hello World! (from server)");
	}
	
	/**
	 * <b>Purpose</b>: "Drive" the network components testing software, server-side
	 * <br><br>
	 * <b>Preconditions</b>: this server is not running
	 * <br>
	 * <b>Postconditions</b>: this server has run
	public static void main(String[] args) {
		System.out.println("Starting Server: ");
		NetTestServer nts = new NetTestServer();
		nts.run();
	}
	*/
	
}
