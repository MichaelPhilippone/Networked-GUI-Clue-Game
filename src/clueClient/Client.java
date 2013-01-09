package clueClient;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JOptionPane;

import utils.Utilities;
import utils.Utilities.e_ExitCode;

import clueNetwork.ClientSocket;

/**
 * <b>Class semantics and roles</b>: <br>
 * The Client represents the entire client-side driver code  <br>
 * The class creates the {@link Client} class and the {@link GUI} class.
 *  
 * <br><br>
 * 
 * <b>Information maintenance</b>: <br>
 * <b>Creation</b>: the Client is created when the program is started. <br>
 * <b>Deletion</b>: the Client is deleted when the program is closed.
 *  
 *  @author Todd Wright (Class Skeletons & Code)
 */
public class Client extends Thread {
	
	/** reference to the {@link GUI} object */
	private GUI gui = null;
	/** reference to the {@link BoardModel} object */
	private BoardModel boardModel = null; 
	/** reference to the {@link ClientSocket} object */
	private ClientSocket socket = null;
	/** the username of the gamer for this {@link Client} */
	private String username = "";
	/** the hostname / ip string of the server process for this Clue game */
	private String serverAddr = "";
	
	/**
	 * <b>Purpose</b>: <br>
	 * <b>Preconditions</b>: <br>
	 * <b>Postconditions</b>: <br>
	 * 
	 * <b>Pseudo-Code</b>: <br>
	 * BEGIN <br>
	 * - instantiate the {@link GUI} object <br>
	 * - instantiate the {@link BoardModel} object <br>
	 * - connect to the server via {@link Client}'s {@code connect()} method <br>
	 * END <br>
	 * 
	 */
	public Client() {
		this("","");
	}
	public Client(String hostAddr, String user) {
		this.boardModel = new BoardModel(this);
		gui = new GUI(this);
		
		username = user;
		serverAddr = hostAddr;
	}
	
	public void run() {
		/* connect to Server process
		 * if an error occurs, shutdown the client: */
		if( !(this.connect()) ) {
			this.systemShutdown(Utilities.e_ExitCode.clientConnectError);
		}
		
		while(true) {
			readFromServer();
		}
	}
	
	/**
	 * <b>Purpose</b>: Flag the appropriate object(s) for creating an active  {@link ClientSocket } connection <br><br>
	 * <b>Precondition</b>: The {@link ClientSocket } connection is not established<br>
	 * <b>Postcondition</b>: The {@link ClientSocket } connection is being established <br><br>
	 * 
	 * <em>LAST MODIFIED: Michael Philippone, 13 DEC 2008 </em><br>
	 * @return boolean true if successful in connecting to server, false if an error occurred
	 */
	public boolean connect() {
		
		// get server IP and port from user (if not specified) 
		int port = Utilities.defaultPort;
		
		if(serverAddr.equalsIgnoreCase(""))
			serverAddr = 
				JOptionPane.showInputDialog("Input the ip address of the Clue host");
		
		if(username.equalsIgnoreCase(""))
			username = 
				JOptionPane.showInputDialog("Please enter a Username");
		
		System.out.println("CLIENT("+username+"): username: " + username);
		
		gui.setTitle( gui.getTitle() + " | " + username );
		
		/* connect to the server instance if possible, 
		 * if not, shut the Client process down: */
		try {
			Socket skTmp = new Socket(serverAddr, port);
			this.socket = new ClientSocket(skTmp);
			this.socket.send(username);
			return true;
		} catch(UnknownHostException uhe) { 
			System.out.println("CLIENT("+username+"): Error connecting to the Server process.(UnknownHostException)");
			gui.inform("CLIENT("+username+"): Error connecting to the Server process.(UnknownHostException)");
			return false;
		} catch(IOException ioe) {
			System.out.println("CLIENT("+username+"): Error connecting to the Server process. (IOException)");
			gui.inform("CLIENT("+username+"): Error connecting to the Server process. (IOException)");
			return false;
		}
	}
	
	/**
	 * <b>Purpose</b>: <br>
	 * <b>Preconditions</b>: <br>
	 * <b>Postconditions</b>: <br>
	 * 
	 * <b>Pseudo-Code</b>: <br>
	 * BEGIN <br>
	 * - read a non-empty string from the server (from the {@link ClientSocket} connection) <br>
	 * - tokenize the string (by spaces) and parse its first word <br>
	 * - IF the first word is "update" <br>
	 * - -  <br>
	 * - ELSE IF the first word is "yourturn" <br>
	 * - -  <br>
	 * - ELSE IF the first word is "havecard" <br>
	 * - - <br>
	 * - ELSE IF the first word is "choosecard" <br>
	 * - -  <br>
	 * - ELSE IF the first word is "inform" <br>
	 * - -  <br>
	 * - ELSE IF the first word is "gameover" <br>
	 * - -  <br>
	 * END <br><br>
	 * 
	 * <em>LAST MODIFIED: Michael Philippone, 13 DEC 2008 </em><br> 
	 */
	public void readFromServer() {
		
		/* fromServer: what we are getting from the server: 
		 * remaining: the rest of the server communication */
		String fromServer = "" , 
				remaining = "";
		
		/* disallow blank strings
		 * (keep reading until we DON'T have a blank string): */
		while(fromServer.equalsIgnoreCase(""))
			fromServer = this.socket.read().trim();
		///////////////
		/* parse the server message: */
		///////////////
		
		String[] stk = fromServer.split(" ");
		
		if(stk[0].equalsIgnoreCase("update")) {
			
			/* build the remaining tokens into a string:  */
			remaining = "";
			for(int i=1; i<stk.length; i++)
				remaining += stk[i];
			
			/* parse this string for the update tuples:  */
			boardModel.parseUpdateString(remaining);
			
			/* print the message */
			System.out.println("CLIENT("+ username +"): update ");
			//gui.inform("Board update received.");
			
			gui.draw();
			
		}
		else if(stk[0].equalsIgnoreCase("havecard")) {
			
			/* build the remaining tokens into a string:  */
			remaining = "";
			for(int i=1; i<stk.length; i++)
				remaining += " " + stk[i];
			remaining = remaining.trim();
			
			/* print the message */
			System.out.println("CLIENT("+ username +"): havecard: " + remaining);
			
			/* pass this string in to be parsed */
			boardModel.haveCards(remaining);
			
			gui.draw();
		}
		else if(stk[0].equalsIgnoreCase("choosecard")) {

			/* build the remaining tokens into a string:  */
			remaining = "";
			for(int i=1; i<stk.length; i++)
				remaining += " " + stk[i];
			remaining = remaining.trim();
			
			/* print the message */
			System.out.println("CLIENT("+ username +"): choosecard: " + remaining);
			//gui.inform(fromServer);
			
			boardModel.chooseCards(remaining);
			
			gui.draw();
		}
		else if(stk[0].equalsIgnoreCase("inform")) {

			/* build the remaining tokens into a string:  */
			remaining = "";
			for(int i=1; i<stk.length; i++)
				remaining += " " + stk[i];
			remaining = remaining.trim();
			
			/* print the message */
			System.out.println("CLIENT("+username+"): inform: " + remaining);
			gui.inform(remaining);
			
			gui.draw();
		}
		else if(stk[0].equalsIgnoreCase("gameover")) {
			/* exit the system, ignore any other socket strings */
			gui.inform("Game over.  Shutting Down.");
			systemShutdown(Utilities.e_ExitCode.clientGameOver);
		}
		else {
			System.out.println("CLIENT("+username+"): error reading string from server.  Unrecognizable socket message.");
			System.out.println("CLIENT("+username+"): server sent: \"" + fromServer + "\".");
		}
	}
	
	/**
	 * <b>Purpose</b>: Shut down the {@link Client} program. <br><br> 
	 * <b>Pseudo-Code</b>: <br>
	 * BEGIN <br>
	 * - use the {@link System} method {@code exit(int)} to close this program <br>
	 * - flag the Operating System with this method's parameter value <br>
	 * END<br><br>
	 * 
	 * <em>LAST MODIFIED: Michael Philippone, 13 DEC 2008 </em><br>
	 * 
	 * @param status the {@link e_ExitCode} status flag to return to the operating system as an integer 
	 */
	public void systemShutdown(e_ExitCode status) { 
		
		try {
			System.out.println("CLIENT("+username+"): Closing socket connection with server.");
			socket.send("exit");
			socket.close();
		} catch(NullPointerException npe) { 
			System.out.println("CLIENT("+username+"): No socket connection."); 
		}
		
		System.out.println("CLIENT("+username+"): System shutting down with exit code \"" + status.ordinal() + "\".");
		
		/* turn the enum into an int and use 
		 * it as an exit status code: */
		//System.exit(status.ordinal()); 
	}
//////////////////////////////////////////////////////////////////////////////////////////////////////
	/* ACCESSOR METHODS */
//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * <b>Purpose</b>: Return a reference to the {@link GUI} object in this class <br>
	 * <b>Preconditions</b>: there is an instance of a {@link Client} <br>
	 * <b>Postconditions</b>: a reference has been returned <br>
	 */
	public GUI getGuiReference() { return gui; }
	
	/**
	 * <b>Purpose</b>: Return a reference to the {@link BoardModel} object in this class <br>
	 * <b>Preconditions</b>: there is an instance of a {@link Client} <br>
	 * <b>Postconditions</b>: a reference has been returned <br>
	 */
	public BoardModel getBoardModelReference() { return boardModel; }
	
	/**
	 * <b>Purpose</b>: Return a reference to the {@link ClientSocket} object in this class <br>
	 * <b>Preconditions</b>: there is an instance of a {@link Client} <br>
	 * <b>Postconditions</b>: a reference has been returned <br>
	 */
	public ClientSocket getClientSocketReference() { return socket; }
	
	/**
	 * <b>Purpose</b>: Return a reference to the {@link ClientSocket} object in this class <br>
	 * <b>Preconditions</b>: there is an instance of a {@link Client} <br>
	 * <b>Postconditions</b>: a reference has been returned <br>
	 */
	public String getUsername() { return this.username; }
	
	
//////////////////////////////////////////////////////////////////////////////////////////////////////
	/*/ MAIN METHOD /*/
//////////////////////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * <b>Purpose</b>: Run the Client-side instance of the software <br><br>
	 * <b>Precondition</b>: There is no client-side software executing <br>
	 * <b>Postcondition</b>: The client-side software is running <br>
	 * 
	 * <em>LAST MODIFIED: Michael Philippone, 13 DEC 2008 </em><br>
	 */
	public static void main(String[] args) { 		
		Client cl = new Client();
		cl.start();
	}
	
//////////////////////////////////////////////////////////////////////////////////////////////////////	
}
