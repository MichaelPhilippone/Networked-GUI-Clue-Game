package clueServer;

import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.StringTokenizer;

import clueNetwork.ClientSocket;

/**
 * <b>Class semantics and roles</b>: <br>
 * Class that describes a player. 
 * maintains and manages server-side instance(s) of a connected player
 * <br><br>
 * <b>Information maintenance</b>: <br>
 * <b>Creation</b>: ??? <br>
 * <b>Deletion</b>: ???
 *
 * @author Erich Schudt (Class Skeletons)
 * @author Erich Schudt (Code)
 */

public class Player extends Thread {

	//// Class Properties ////
	
	/** the name the player (Client-side) has provided
	 * for interacting with the other players */
	private String playerName;
	
	/** the name of the piece to which 
	 * this player is assigned */
	private String pieceName;
	
	/** The {@link ClientSocket} that the player class uses
	 * to communicate over the network */
	private ClientSocket socket;
	
	/** The {@link Users} class that contains a reference to 
	 * this {@link Player} object in its {@link Player}s array */
	private Users users = null;
	
	/** boolean switch for starting or stopping the thread */
	private boolean shouldIRun = true;
	
	/** flag for determining whether or not the computer should play for this {@link Player} */
	private boolean isComputerPlayer = false;
	
	///////////////////constructors///////////////
	/**
	 * Default Constructor <br>
	 * Takes 0 arguments
	 * Constructor that creates a player
	 */
	public Player(){ /*CODE*/ }
	
	/**
	 * Constructor that creates a player <br>
	 * Takes 4 arguments
	 * @param username the user name of the player
	 * @param sock the {@link Socket} with which this Player
	 * @param piece the name string of the assigned play-piece
	 * @param u a reference to the creator {@link Users} class
	 * will create a {@link ClientSocket}
	 */
	public Player(	String username, 
					Socket sock, 
					String piece, 
					Users u) {

		this.playerName = username; // + "-" + u.usersCount();
		this.pieceName = piece;
		this.socket = new ClientSocket( sock );
		this.users = u;
		this.setName("T_"+this.playerName);	// this is a Thread method

		if(users != null &&
				this.users.getServerReference() != null && 
				this.users.getServerReference().debugMode) {
			System.out.println(
					"PLAYER: Player created.  \n    " + 
					"IP: " + this.socket.getEndPointAddress() + "\n    " +
					"Username: " + this.playerName + "\n    " + 
					"Assigned piece: " + this.pieceName + ".");
		}
	}
	
	///////////////////methods///////////////////
	/**
	 * <b>Purpose</b>: Run the {@link Player}'s execution <br><br>
	 * <b>Precondition</b>: player exists and initial connection is established <br>
	 * <b>Postcondition</b>: <br>
	 * @deprecated we are not using this.
	 */
	public void run() { /* NOT USED */ }
	
	/**
	   * <b>Purpose</b>: communicating with Client <br><br>
	   * <b>Precondition</b>: player exists and initial connection is established <br>
	   * <b>Postcondition</b>: data written to socket <br>
	   * @param message the String to send on the {@link Player}'s {@link ClientSocket}
	   */
	public boolean writeOnSocket(String message) { 
		try { 
			return this.socket.send(message); 
		}
		catch (Exception ste) { 
			System.out.println("PLAYER("+playerName+"):  Exception occurred on write operation. (stack trace to follow)");
			ste.printStackTrace();
			return false;
		}
	}
	
	/**
	   * <b>Purpose</b>: communicating with Client <br><br>
	   * <b>Precondition</b>: player exists and initial connection is established <br>
	   * <b>Postcondition</b>: data data read from socket <br>
	   * @return String read from socket
	   */
	public String readFromSocket() { return this.socket.read(); }
	
	////////// ACCESSORS //////////
	
	/**
	   * <b>Purpose</b>: get the user name of the client <br><br>
	   * <b>Precondition</b>: player exists <br>
	   * <b>Postcondition</b>: none
	   * @return userName the user name string of the client
	   */
	public String getPlayerName() { return playerName; }
	
	/**
	 * <b>Purpose</b>: get player's game piece name<br><br>
	 * <b>Precondition</b>: player exists <br>
	 * <b>Postcondition</b>: none <br>
	 * @return pieceName the name string of the player's game piece
	 */
	public String getPieceName(){ return this.pieceName; }
	
	/**
   * <b>Purpose</b>: Get the player's {@link ClientSocket} instance <br><br>
   * <b>Precondition</b>: player exists <br>
   * <b>Postcondition</b>: none <br>
   * @return a reference to the {@link ClientSocket} instance
   */
	public ClientSocket getSocket() { return this.socket; }
	
	/**
	 * <b>Purpose</b>: get computerPlayer designation for this {@link Player} <br><br>
	 * <b>Precondition</b>: player exists <br>
	 * <b>Postcondition</b>: none <br>
	 * @return boolean true if the player is no longer playing, false if they are still connected
	 */
	public boolean isComputerPlayer() {return this.isComputerPlayer;}
	
	/**
	   * <b>Purpose</b>: set player's game piece name <br><br>
	   * <b>Precondition</b>: player exists <br>
	   * <b>Postcondition</b>: none <br> 
	   * @param name the name to assign the internal name reference
	   */
	public void setPieceName(String name){ this.pieceName = name; }
	
	/**
	   * <b>Purpose</b>: set player's username <br><br>
	   * <b>Precondition</b>: player exists <br>
	   * <b>Postcondition</b>: none 
	   * @param name the name to assign the internal name reference
	   */
	public void setPlayerName(String name){ this.playerName = name; }
	
	/**
	 * <b>Purpose</b>: designate this {@link Player} as no longer having a connected {@link ClientSocket} <br><br>
	 * <b>Precondition</b>: player exists <br>
	 * <b>Postcondition</b>: none <br>
	 */
	public void setComputerPlayer() { this.isComputerPlayer = true; }
}

