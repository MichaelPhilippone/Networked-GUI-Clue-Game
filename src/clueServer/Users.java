package clueServer;

import java.net.Socket;

import clueClient.Client;

import utils.Utilities;

/**
 * <b>Class semantics and roles</b>: <br> 
 * The Users class is responsible for maintaining references to 
 * all of the {@link Player} objects. <br>
 * It is through the Users class that {@link Player}s and their
 * corresponding sockets and information are accessed.  <br>
 * (An important task for the Users object is to log the order 
 * in which players connect to the server-side program. 
 * This is accomplished by the use of the array: the index is 
 * the connection order.) <br><br>
 * 
 * <b>Information maintenance</b>: <br>
 * <b>Creation</b>: The Users object is created, and there is only one, 
 * when the {@link Server} is started. <br>
 * <b>Deletion</b>: The Users object is destroyed when the system is shut down
 *  
 *  @author Michael Philippone (Class Skeletons)
 *  @author Erich Schudt (Code)
 */
public class Users {
	
	/** back-reference to {@link Server} object */
	private Server server = null;
	
	/** reference to {@link ServerListener} object for initial player connections */
	private ServerListener listener = null;
	
	/** representation of structure of all players.
	 * Connection order is equal to the array index */
	private Player[] playerArr = new Player[Utilities.numRequiredPlayers];
	
	/**
	 * Default Constructor:	<br>
	 * Takes 0 arguments.	<br>
	 * <b>Purpose</b>: create a working instance of a {@link Users} object <br>
	 * <b>Pseudo-Code</b>: <br> 
	 * BEGIN <br>
	 * -> point internal {@link Server} reference to parameter reference
	 * -> null out the whole array of {@link Player} objects <br>
	 * -> instantiate a {@link ServerListener} object <br>
	 * -> start the ServerListener listening <br>
	 * END <br>
	 * 
	 * @param serv a reference to the {@link Server} object for the Game
	 */
	public Users(Server serv){

		//point internal Server reference to paramter:
		this.server = serv;
		
		//clear out the whole player array...
		for(int i=0; i<this.playerArr.length; i++) {
			this.playerArr[i] = null;
		}
		
		// create a ServerListener instance 
		//   and pass the ServerListener a 
		//   reference to this Users class instance...
		this.listener = new ServerListener(this);
		System.out.println("USERS: listening...");
		// start the ServerListener listening...
		//   (If there is an error at this point, shut the system down) 
		if( !(this.listener.listenForUsers()) ) {
			System.out.println("USERS: Error listening for Players to connect.  EXITING.");
			if(this.server != null) {
				this.server.systemShutdown(Utilities.e_ExitCode.listenerError);
			}
		}
		System.out.println("USERS: made it to the end of the user's constructor");
	}
	
	/**
	 * <b>Purpose</b>: Create an additional player instance in the array <br><br>
	 * <b>Precondition</b>: There is an array of some number of players between 0 and 5 <br>
	 * <b>Postcondition</b>: There is one more player added and the player has its thread started <br>
	 * <b>Pseudo-Code</b>: <br> 
	 * BEGIN <br>
	 * -> put the number of connected users into "INDEX" <br>
	 * -> create a {@link Player} object in element INDEX + 1 of {@link Player} object array <br>
	 * -> start the newly created {@link Player} thread <br>
	 * END <br>
	 * 
	 * @param username the username String of the connecting player
	 * @param sock the socket that the {@link Player} object is to communicate over
	 */
	public void createPlayer(String username, Socket sock) { 
		// since the array is indexed by zero, and this method counts by one, 
		// we will always have an index 1 past the last full element
		int index = this.usersCount(); 
		
		// now, construct the player based on params, constants and various references
		this.playerArr[index] = 
			new Player( 
				username, 						// known from the initial connection 
				sock, 							// passed from initial connection
				Utilities.pieceNames[index],	// implied by array index
				this);							// a reference to this Users object
		this.playerArr[index].run(); 	// start the player thread
	}
	
	/**
	 * <b>Purpose</b>: Update {@link Client} game state via client socket 
	 * in appropriate player(s).  The message is in an unparsed form 
	 * and should be parsed to determine nature of communication 
	 * (ie: guess, accuse, board update)  <br>
	 * <b>Precondition</b>: Game is playing <br>
	 * <b>Postcondition</b>: The appropriate player(s) have had 
	 * their socket(s) used to send a message to the corresponding {@link Client} 
	 * <b>Pseudo-Code</b>: <br> 
	 * BEGIN <br>
	 * -> FOR each {@link Player} object in the array: <br>
	 * -> -> write the message (passed in as a parameter) to the current {@link Player} object's socket <br>
	 * END <br>
	 * 
	 * @param affectedPlayerNums an array of integers specifying the playerArr indices of those players with which to communicate
	 * @param message the unparsed message String to send to the client
	 */
	public void update(int[] affectedPlayerNums , String message) { 
		for (int i=0; i < affectedPlayerNums.length; i++) {
			this.playerArr[ affectedPlayerNums[i] ].writeOnSocket(message);
		}
	}
	
	/**
	 * <b>Purpose</b>: Update {@link Client} game state via client socket 
	 * in all player(s).  <br>
	 * <b>Precondition</b>: Game is playing <br>
	 * <b>Postcondition</b>: All player(s) have had their socket(s) used to send a message to the corresponding {@link Client} 
	 * <b>Pseudo-Code</b>: <br> 
	 * BEGIN <br>
	 * - FOR each {@link Player} object in the array: <br>
	 * - - write the message (passed in as a parameter) to the current {@link Player} object's socket <br>
	 * - sleep for 2 seconds to allow other communications to catch up. (a weird fix to an untraceable error) <br>
	 * END <br><br>
	 * 
	 * @param message the unparsed message String to send to all clients
	 */
	public void updateAll( String message ) { 
		for (int i=0; i < playerArr.length; i++) {
			this.playerArr[i].writeOnSocket(message);
		}
		// sleep...
		try{Thread.sleep(1000*2);}catch(Exception e){}
	}
	
	/**
	 * <b>Purpose</b>: Get the number of {@link Player} objects currently maintained by the {@link Users} class <br><br>
	 * <b>Preconditions</b>: There are objects in array <br>
	 * <b>Postconditions</b>: the number of non-null elements is known <br>
	 * <b>Pseudo-Code</b>: <br>
	 * FOR all elements in player array: <br>
	 * --> IF the current element is not null: <br>
	 * --> --> increment the number of players by 1 <br>
	 * return the number of players <br>
	 * END <br>
	 * <em>(Added 19 NOV 2008, Michael Philippone)</em>
	 *  
	 * @return the number of {@link Player} objects currently 
	 * maintained
	 */
	public int usersCount() {
		//to keep track of players as we see them in the array
		int numPlayers = 0;	

		//loop over array and keep track of non-null elements:
		for(int i=0; i<this.playerArr.length; i++){
			if(this.playerArr[i] != null)
				numPlayers++;
		}
		
		//now that we've counted them all, return that count:
		return numPlayers;
	}
	
	/**
	 * <b>Purpose</b>: Get the index of a player in the user array <br><br>
	 * <b>Preconditions</b>: There are objects in the player array <br>
	 * <b>Postconditions</b>: There is a number known for a particular player <br>
	 * <b>Pseudo-Code</b>: <br> 
	 * FOR each element in the array of players: <br>
	 * --> IF the element is not null and it has a playername that matches the search paramete: <br>
	 * --> --> return the number of the element <br>
	 * --> ELSE : <br>
	 * --> --> return -1 <br>
	 * END
	 * <br>
	 * <em>(Added 2 DEC 2008, Erich Schudt)</em>
	 * 
	 * @param username the user name of the client
	 * @return index the number index of the array which is the 
	 * order of the current player or -1 if an error exists
	 */
	public int getOrder(String username){ 
		
		for(int i =0; i<this.playerArr.length; i++) {
			if( this.playerArr[i] != null && 
				this.playerArr[i].getPlayerName().toLowerCase().equalsIgnoreCase( username.toLowerCase() ) ) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * <b>Purpose</b>: Assign a piece name to a player <br><br>
	 *  <b>Precondition</b>: There are some number of 
	 *  {@link Player} objects in the playerArr array 
	 *  and they already have usernames 
	 *  (from the initial connection) <br>
	 *  <b>Postcondition</b>: the players have piece names assigned to them <br>
	 *  <b>Pseudo-Code</b>: <br>
	 *  FOR all elements in the playerArr array: <br>
	 *  --> IF the array element is NOT null: <br>
	 *  --> --> Assign the {@link Player} object's pieceName with the corresponding piece name from the {@link Utilities} pieceNames array <br>
	 *  END <br>
	 * <em>(Added 2 DEC 2008, Erich Schudt)</em> <br>
	 * <em>(MOD 08 DEC 2008, Michael Philippone)</em>
	 * 
	 * @deprecated this assignment of piece names occurs when 
	 * the players connect, see the createPlayer method 
	 * for this class ^^ above ^^
	 */
	public void assignPieces() { 
		
		for(int i=0; i<this.playerArr.length; i++) {
			if ( this.playerArr[i] != null ) {
				this.playerArr[i].setPieceName(Utilities.pieceNames[i]);
			}
		}
	}
	
	/**
	 * <b>Purpose</b>: Get an instance of a {@link Player} in the user array based on the playerName or pieceName field <br><br>
	 * <b>Preconditions</b>: There are objects in the player array <br>
	 * <b>Postconditions</b>: There is a number known for a particular player <br>
	 * <b>Pseudo-Code</b>: <br> 
	 * BEGIN <br>
	 * - IF searching by piece name: <br>
	 * - - FOR each element in the array of players: <br>
	 * - - - IF the element is not null and it has a {@code pieceName} that matches the search parameter: <br>
	 * - - - - return the player at that element <br> 
	 * - ELSE: <br>
	 * - - FOR each element in the array of players: <br>
	 * - - - IF the element is not null and it has a {@code playerName} that matches the search parameter: <br>
	 * - - - - return the player at that element <br> 
	 * - return {@code NULL} (if we get this point, none of the {@link Player}s in the array match) <br>
	 * END <br>
	 * <em>(Added 12 DEC 2008, Michael Philippone)</em> <br>
	 * 
	 * @param name the username of the Player to return
	 * @param searchByPiece whether or not hte given string parameter is a piece name to search on or a player name
	 * @return the player with the given username
	 */
	public Player getPlayer(String name , boolean searchByPiece) {
		if(searchByPiece) { // searching by pieceName
			for(int i =0; i<this.playerArr.length; i++) {
				if( this.playerArr[i] != null && 
					this.playerArr[i].getPieceName().toLowerCase().equalsIgnoreCase( name.toLowerCase() ) ) {
					return this.getPlayer(i);
				}
			}
		}
		else { // searching by playerName
			for(int i =0; i<this.playerArr.length; i++) {
				if( this.playerArr[i] != null && 
					this.playerArr[i].getPlayerName().toLowerCase().equalsIgnoreCase( name.toLowerCase() ) ) {
					return this.getPlayer(i);
				}
			}
		}
		// else:
		return null;
	}
	
	/**
	 * <b>Purpose</b>: Get the index of a player in the user array <br><br>
	 * <b>Preconditions</b>: There are objects in the player array <br>
	 * <b>Postconditions</b>: There is a number known for a particular player <br>
	 * <b>Pseudo-Code</b>: <br> 
	 * BEGIN <br>
	 * FOR each element in the array of players: <br>
	 * --> IF the element is not null and it has a playername that matches the search parameter: <br>
	 * --> --> return the element <br>
	 * --> ELSE : <br>
	 * --> --> return {@code NULL} <br>
	 * END <br>
	 * <em>(Added 12 DEC 2008, Michael Philippone)</em> <br>
	 * 
	 * @param p_num the number of the array element of the sought-after {@link Player}
	 * @return the player with the given username
	 */
	public Player getPlayer(int p_num) {
		if( this.playerArr[p_num] != null )
			return playerArr[p_num];
		else
			return null;
	}
	
//////////////////////////////////////////////////////////////////////////////////////	
/* ACCESSOR METHODS */
//////////////////////////////////////////////////////////////////////////////////////
	
	/**
	 * <b>Purpose</b>: Get the internal {@link Server} <br><br>
	 * <b>Preconditions</b>: There is an instantiated {@link Users} class <br>
	 * <b>Postconditions</b>: A reference to the internal {@link Server} reference has been returned <br>
	 * <em>(Added 12 DEC 2008, Michael Philippone)</em> <br>
	 * @return a reference to the {@link Users}' {@link Server} object
	 */
	public Server getServerReference() { return this.server; }
	
	/**
	 * <b>Purpose</b>: Get the internal {@link ServerListener} <br><br>
	 * <b>Preconditions</b>: There is an instantiated {@link Users} class <br>
	 * <b>Postconditions</b>: A reference to the internal {@link ServerListener} reference has been returned <br>
	 * <em>(Added 12 DEC 2008, Michael Philippone)</em> <br>
	 * @return a reference to the {@link Users}' {@link ServerListener} object
	 */
	public ServerListener getServerListenerReference() { return this.listener; }
}
