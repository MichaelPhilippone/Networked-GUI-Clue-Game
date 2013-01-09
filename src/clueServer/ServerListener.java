package clueServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;

import utils.Utilities;

/**
 * <b>Class semantics and roles</b>: <br>
 * Class that describes a server listener. 
 * handle initial connection requests from the clients
 * <br><br>
 * <b>Information maintenance</b>: <br>
 * <b>Creation</b>: The single ServerListener object created at start up <br>
 * <b>Deletion</b>: the ServerListener object is destroyed when the 
 * server-side software is terminated.
 * 
 * @author Erich Schudt (Class Skeletons)
 * @author Michael Philippone (Code)
 */

public class ServerListener {

	////////////////fields//////////////
	/** the port number */
	private int port;
	
	/**stores an instance of the listening socket */
	private ServerSocket serverSock = null;
	
	/** 
	 * A Back reference to the {@link Users} class
	 * for {@link Player} creation and addition <br>
	 */
	private Users users = null;
	
	//////////////constructor////////////
	
	/**
	 * Constructor that creates a serverListener <br>
	 * <b>Purpose</b>: default constructor <br>
	 * Takes 1 arguments, applies the default port 
	 * static property from the {@link Utilities} class
	 * @param u a reference to an instance of a {@link Users} object
	 */
	public ServerListener(Users u) { this(u, Utilities.defaultPort); }

	/**
	 * <b>Purpose</b>: Constructor that creates a serverListener
	 * <br>
	 * <b>What it does</b>:
	 * <br><br>
	 * 
	 * First: assign the given port to the internal port representation
	 * <br>
	 * Second: create a new Server listening socket on the specified port
	 * <br>
	 * Third: start listening for player connections
	 * @param u a reference to an instance of a {@link Users} object
	 * @param portIn the port number
	 */
	public ServerListener(Users u, int portIn) {
		try {
			
			//assign the given Server instance to the internal Server reference
			this.users = u;
			
			//assign the given port to the internal port representation
			this.port = portIn;
			
			//create a new Server listening socket on the specified port
			this.serverSock = new ServerSocket(this.port);
			
		}catch(IOException ioe) {
			ioe.printStackTrace();
			System.out.println("SERVERLISTENER: Error creating Server Listener Socket");
		}
	}
	  
	  
	  /////////////////////////methods//////////////////////////
	  
	  /**
	   * <b>Purpose</b>: receive and handle a connection <br><br>
	   * <b>Precondition</b>: port has a value <br>
	   * <b>Postcondition</b>: connection is established from client <br>
	   * <b>Pseudo-Code</b>: <br> 
	   * BEGIN <br>
	   * -> WHILE the number of connected {@link Player}'s is less than the number of required players:
	   * -> -> listen for a connection attempt and create a socket when one is detected <br>
	   * -> -> read the first line of communication from the subsequent socket (This is the username) <br>
	   * -> -> create a new {@link Player} with the socket and the new information
	   * END <br>
	   * 
	   * <b>NOTE</b>: this method assumes that the first line read from a 
	   * socket is the username for that player
	   * 
	   * @return boolean true if it is successful, false if it fails at some point
	   */
	  public boolean listenForUsers() {
		  BufferedReader br = null;
		  String username = "";
		  
		  try {
			  //Loop until we have all of our clients
			 do {					  
				  // set the timeout value for the ServerSocket's accept() method
				  this.serverSock.setSoTimeout(Utilities.serverListenerTimeoutMillis);
				  
				  // listen for incoming requests:
				  Socket sk = this.serverSock.accept();
				  
				  //get a reader ready to get the username
				  br = new BufferedReader( 
						  new InputStreamReader( 
								  sk.getInputStream()));
				  
				  while(!br.ready()); //block until the socket can be read (for username)
				  
				  //get the connecting user's name:
				  username = br.readLine();
				  
				  System.out.println("SERVERLISTENER: Connection detected from:" );
				  System.out.println("SERVERLISTENER:    ip: " + sk.getInetAddress().getHostAddress() );
				  System.out.println("SERVERLISTENER:    remote port: " + sk.getPort() );
				  System.out.println("SERVERLISTENER:    local port: " + sk.getLocalPort() );
				  System.out.println("SERVERLISTENER:    username: " + username );
				  
				  //make a new player:
				  this.users.createPlayer( username , sk );
				  
				  //alert the console with the newly connected player's name:
				  System.out.println("SERVERLISTENER: " + username);
				  
				  if( this.users != null && 
						  this.users.getServerReference() != null && 
						  this.users.getServerReference().debugMode )
					  System.out.println("SERVERLISTENER: There are now " + this.users.usersCount() + " users connected." );  
				  
				  //break out of this loop once we have all of our required players:
				  if(this.users.usersCount() >= Utilities.numRequiredPlayers)
					  break;
				  
			  } while( true );
			  
			  //Success! we have all of our players! 
			  //Time to leave...
			  System.out.println("SERVERLISTENER: Required number of players connected.");
			  return true;
			  
		  } catch(SocketTimeoutException ste) { // if a timeout occurs:
			  System.out.println("SERVERLISTENER: TIMEOUT waiting for players to join.");
			  System.out.println("SERVERLISTENER: 5 Minutes passed since last/any connection.");
			  return false;
		  } catch (IOException ioe) { //if there is some other failure:
			  System.out.println("SERVERLISTENER: failure.  IOException caught.");
			  return false;
		  } 
	  }
}

