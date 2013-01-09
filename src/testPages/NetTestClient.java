package testPages;

import utils.Utilities;
import clueNetwork.ClientSocket;


/**
 * <b>Class semantics and roles</b>: <br>
 * This class is used to test the functionality of the {@link ClientSocket}
 * object and its methods.
 * @author Michael Philippone (code and class skeletons)
 * @author Erich Schudt (code)
 */
public class NetTestClient {
	
	
	/**
	 * <b>Purpose</b>: Test a ClientSocket object's ability to send and receive information 
	 * <br><br>
	 * <b>Pseudo-Code</b>: <br>
	 * BEGIN <br>
	 * -> Connect a {@link ClientSocket} object to another instance in a listening server process <br>
	 * -> Communicate with the other endpoint connection (send and receive packets) <br>
	 * -> close the connection <br> 
	 * END <br>
	 * @param args
	 */
	  public static void main(String[] args) {
		  
		/* Open the console output to a FILE: (also redirect error output there too) */
		System.setOut(Utilities.setFileOutput("NetTestClient.txt"));
		System.setErr(System.out);
		System.out.println("PROGRAM STARTED AT: " + Utilities.getTodaysDate() + "\n\n\n");
		
		
		  //local var for socket send data:
		  String info = "Ha ha, i got it to work";
		  
		  System.out.println("Client Startup");
		  System.out.println("4 Steps to test ClientSocket (test client):");
		  System.out.println("  1) Create a ClientSocket to connect with the testing server process");
		  System.out.println("  2) create a connection with another ClientSocket instance");
		  System.out.println("  3) communicate (send and receive packets)");
		  System.out.println("  4) close the connection");
		  
		  // STEP 01 
		  ClientSocket clientSocket = new ClientSocket("127.0.0.1", 1234);
		  System.out.println("CLIENT: Step 01 Success: working so far");

		  // STEP 02
		  clientSocket.send(info);
		  System.out.println("CLIENT: sent info to server, if printed from server: Step 02 success.");
		  
		  //Step 03
		  System.out.println( "CLIENT: From sever: " + clientSocket.read());
		  
		  // STEP 04
		  clientSocket.close();
		  System.out.println("CLIENT: the connection has been closed, Step 4 success");
		 }
	
	
	
	
	/*
	 * REMOVED 12 DEC 2008
	 * by Michael Philippone
	 * Replaced by Erich's Code
	 */
	
	/*
	/**
	 * <b>Purpose</b>: Run a simple send and receive test on the {@link ClientSocket} 
	 * <br><br>
	 * <b>Preconditions</b>: None.  Nothing is running.
	 * <br>
	 * <b>Postconditions</b>: The {@link ClientSocket} has been tested
	 * <br>
	 * <b>Pseudo-Code</b>:  <br>
	 * Create an instance of a {@link ClientSocket}  <br>
	 * Connect to another instance of a {@link ClientSocket} (via {@link ServerSocket} on {@link NetTestServer} <br>
	 * write a line to the socket <br>
	 * read a line from the socket <br>
	 * END
	public void run() {
		ClientSocket sk = new ClientSocket( "127.0.0.1", 5678 );	
		sk.send("Hello World (from client)!");
		System.out.println( "READING (from server):  " + sk.read() );
	}
	
	/**
	 * <b>Purpose</b>: "Drive" the network components testing software, client-side
	 * <br><br>
	 * <b>Preconditions</b>: this client is not running
	 * <br>
	 * <b>Postconditions</b>: this client has run
	public static void main(String[] args){
		System.out.println("Starting Client: ");
		NetTestClient ntc = new NetTestClient();
		ntc.run();
	}
	*/
	
}
