package clueServer;

import java.util.StringTokenizer;
import javax.swing.JOptionPane;

import clueClient.Client;
import clueServer.GameLogic.GuessReturn;
import utils.Utilities;
import utils.Utilities.e_ExitCode;

/**
 * <b>Class semantics and roles</b>: <br>
 * The Server class represents the top of the server-side 
 * class hierarchy.  This is essentially the driver for the server-side
 * software.  The roles include directing the actions of all other 
 * server-side classes and starting / ending the server-side program 
 * session(s).
 * <br><br>
 * <b>Information Maintenance</b>: <br>
 * <b>Creation</b>: The Server object, and there is only one, is 
 * created when the server-side program is launched <br>
 * <b>Deletion</b>: the Server object is not destroyed until the server-side 
 * program is shut down
 *  
 * @author Michael Philippone (Class Skeletons and Code)
 * @author Todd Wright (Code)
 * @author Matthew SeGall (Code)
 */
public class Server {
	
	/** reference to the {@link GameLogic} class */
	private GameLogic logic = null;
	
	/** 
	 * reference to {@link ServerListener} object
	 * @deprecated MOVED To {@link Users} class (Michael Philippone, 12 DEC 2008) 
	 */
	private ServerListener listener = null;
	
	/** reference to the {@link Users} object: a collection of players */
	private Users users = null;
	
	/** flag variable for turning on debugging output */
	public boolean debugMode = false;
	
	/** 
	 * Default constructor override. <br>
	 * <b>Purpose</b>: Call the output stream preparation method.
	 */
	public Server() { this.prepOutput(); }
	
	/**
	 * <b>Purpose</b>: Once the server-side program has started executing, this method begins instantiating the necessary logic and user-management components components <br><br>
	 * <b>Precondition</b>: Server-side program has been launched <br>
	 * <b>Postcondition</b>: Server-side program has objects necessary to continue pre-game setup <br>
	 * <b>Pseudo-Code</b> : <br>
	 * BEGIN <br>
	 * - create an instance of a {@link GameLogic} object and point internal {@link GameLogic} reference to it <br>
	 * - create an instance of a {@link Users} object and reference internally <br>
	 * - - This will start the {@link ServerListener} listening as well, the {@code listen()} method is called from the {@link ServerListener}'s constructor <br>
	 * END <br><br>
	 * 
	 * <em>LAST MODIFIED: Michael Philippone, 12 DEC 2008 </em><br>
	 */
	public void createServerComponents() {
		
		//make and reference new GameLogic object
		this.logic = new GameLogic();

		//make and reference new Users object (pass instance of this Server class to it)
		this.users = new Users(this);
		
		if(debugMode) System.out.println("SERVER: made it to the end of server's createservercomponents method");
	}
	
	/**
	 * <b>Purpose</b>: Begin executing actions that are necessary to begin game-play. <br><br>
	 * <b>Precondition</b>: The All players are created and connected <br>
	 * <b>Postcondition</b>: All server-side program components have been initialized and game play can begin <br>
	 * 
	 * <b>Pseudo-Code</b>: <br>
	 * BEGIN <br>
	 * - get a serialized, String version of the board with ALL spaces marked for update <br>
	 * - call Player's update method with String board version <br>
	 * - give players their cards <br>
	 * - ANYTHING ELSE???
	 * END <br><br>
	 * 
	 * <em>LAST MODIFIED: Michael Philippone, 12 DEC 2008 </em><br>
	 */
	public void prePlay() {
		
		if(debugMode) System.out.println("SERVER: made it to the preplay method!");
		
		for(int i = 0; i < Utilities.numRequiredPlayers; i++) {
			System.out.println("SERVER: " +
					users.getPlayer(i).writeOnSocket("inform You have been assigned the piece\"" + users.getPlayer(i).getPieceName() + "\"")
					+ " (result of inform piece name command sent to user: " + 
					users.getPlayer(i).getPlayerName()
					+ ")");
		}
		
		// wait for the socket communications to send before sending more:
		try { Thread.sleep(1000); } catch (Exception e) {}
		
		for(int i = 0; i < Utilities.numRequiredPlayers; i++) {
			System.out.println("SERVER: " +
					users.getPlayer(i).writeOnSocket(
							"havecard " + 
							logic.Card.getDeck()[i][0] + ";" + 
							logic.Card.getDeck()[i][1] + ";" + 
							logic.Card.getDeck()[i][2]) 
					+ " (result of havecard command sent to user: " + 
					users.getPlayer(i).getPlayerName()
					+ ")");
		}
		
		// wait for the socket communications to send before sending more:
		try { Thread.sleep(1000); } catch (Exception e) {}
		
		for(int i = 0; i < Utilities.numRequiredPlayers; i++) {
			System.out.println("SERVER: " + 
					users.getPlayer(i).writeOnSocket("update " + logic.Board.StringBoard()) 
					+ " (result of update command sent to user: " + 
					users.getPlayer(i).getPlayerName()
					+ ")");
		}
		
		if(debugMode) System.out.println(
							"SERVER: TOP SECRET cards are: " + 
							logic.Card.getDeck()[6][2] + 
							" did it in the " + 
							logic.Card.getDeck()[6][1] + 
							" with the " + 
							logic.Card.getDeck()[6][0]);
		
	}
	
	/**
	 * <b>Purpose</b>: Start game play on and alert all classes lower on the class-hierarchy of game play <br><br>
	 * <b>Precondition</b>: All classes necessary for game-play have been loaded and initialized <br>
	 * <b>Postcondition</b>: The game has begun <br>
	 * 
	 * <b>Pseudo-Code</b>:  <br>
	 * BEGIN <br>
	 * - WHILE the game is not over (a player has not won, all have not lost OR all players are not removed): <br>
	 * - - IF current player designated to take a turn is removed from play:
	 * - - - call {@code Server.computerPlayer()} <br>
	 * - - - continue loop <br>
	 * - - listen on the socket for the current player <br>
	 * - - call corresponding method based on the result of the socket communication <br>
	 * - - - move <br>
	 * - - - - call {@code GameLogic.move()} method <br>
	 * - - - - get {@code logic.board.display} property (this is the String[][] representation of the board) <br>
	 * - - - - communicate that to {@link Client} on {@link Player} object who has active designation (a not true {@code Player.isComputerPlayer()} return value) <br>
	 * - - - - read the message from {@link Client} regarding selected position (X,Y coord) <br>
	 * - - - - call {@code logic.board.piece.relocate(X,Y)} to move player <br>
	 * - - - - call {@code logic.board.serialize()} <br>
	 * - - - - get {@code logic.board.display} property (this is the String[][] representation of the board) <br>
	 * - - - - communicate board model from above step to ALL {@link Player}s <br>
	 * - - - - read from client for next step (accuse, guess, endturn) <br>
	 * - - - guess <br>
	 * - - - - call {@code logic.guess()} method. pass in CHARACTER, WEAPON, ROOM <br>
	 * - - - - test returned string from {@code logic.guess()} (previous step) for error <br> 
	 * - - - - IF no error: <br>
	 * - - - - - IF returns true: <br>
	 * - - - - - - send message to all players that guess was made, what it was and that it was NOT disproved <br>
	 * - - - - - ELSE (guess returns false meaning that it can be disproved): <br>
	 * - - - - - - IF one of the returned player numbers is a 6 or an 8: <br>
	 * - - - - - - - that card is one of the mystery cards or there was an invalid card guess (respectively) and it should be ignored <br>
	 * - - - - - - ensure that each specified player returned from guess method is distinct <br>
	 * - - - - - - notify the players specified (in the order they connected to the game after 
	 * 					the current player--looping back to Miss Scarlet if need be) 
	 * 					that they need to choose one (or more) of the applicable cards to 
	 * 					show so long as they are NOT the player whose turn it is 
	 * 					(TO BE REPLACED by the {@code Server.notifyCardChoice()} method<br>
	 * - - - - - - display the chosen card to all players with message that guess was disproved <br>
	 * - - - - - - update all {@link Player}'s board 
	 * - - - - ELSE (error):
	 * - - - - - treat an error as a wrong guess but with no cards shown <br>
	 * - - - accuse <br>
	 * - - - - call {@code GameLogic.accuse()} method <br>
	 * - - - - 
	 * - - - exit <br>
	 * - - - - remove player from game play <br>
	 * - - - - add player to computer player <br>
	 * END <br><br>
	 * 
	 * <em>LAST MODIFIED: Michael Philippone, 12 DEC 2008</em> <br>
	 * 
	 */
	public void play() {
		boolean win = false;//true/false tells us if someone has won
		boolean lost = true;//true/false tells us if everyone has lost
		int strNum = 0, x,y,roll;
		String message = "";
		String[] token;
		Player currPlayer=null;
		
		while(!win && lost){// if no one has won and everyone has not yet lost, keep playing
				
			currPlayer = users.getPlayer(logic.currentPlayer);
			
			/* keep track of how many guesses the current is allowed to make */
			logic.hasGuessed = false;
			
			//prepare the move for the player
			roll = logic.move();
			
			// Tell everyone the the user's roll
			try {Thread.sleep(1000); } catch (Exception e) {}
			for(int i = 0; i < Utilities.numRequiredPlayers; i++)
				users.getPlayer(i).writeOnSocket("inform " + currPlayer.getPlayerName() + " has rolled a " + roll);
			try{ Thread.sleep(1000*5); } catch (Exception e) {}
			
			currPlayer.writeOnSocket("update " + logic.Board.StringBoard()); // show the player the moves
			
			message = currPlayer.readFromSocket();//find where the player moved to
			
			if(debugMode) System.out.println("SERVER: received \"" + message + "\" from player: " + users.getPlayer(logic.currentPlayer).getPlayerName()  + " (while loop bottom)");
			
			token = message.split(" ");
			
			/* If this Server process receives a message from a client
			 * to shutdown, it should match the following token */
			if(token[0].compareToIgnoreCase("testingshutdown") == 0)
				systemShutdown(Utilities.e_ExitCode.testingShutdown);
			
			while(!(token[0].compareToIgnoreCase("endturn") == 0)){
				
				strNum = 1;
				if(token[0].compareToIgnoreCase("move") == 0){		
					
					x = Integer.parseInt(token[strNum++]);
					y = Integer.parseInt(token[strNum++]);
					
					logic.Board.Piece.relocate(logic.currentPlayer, x, y);
					logic.Board.serialize();
					
					for(int i = 0; i < Utilities.numRequiredPlayers; i++)
						users.getPlayer(i).writeOnSocket("update " + logic.Board.StringBoard());
					
					// wait for it to catch up
					try {Thread.sleep(1000*2); } catch (Exception e) {}
					
					currPlayer.writeOnSocket("inform Your move has been executed.");
				}
				else if(token[0].compareToIgnoreCase("exit") == 0){
					currPlayer.setComputerPlayer();
					break;
				}
				else if(token[0].compareToIgnoreCase("accuse") == 0){
					
					String player = token[strNum++];
					String weapon  = token[strNum++];
					String room = token[strNum++];
					
					if(logic.accuse(player, weapon, room)){
						win = true;
						for(int i = 0; i < Utilities.numRequiredPlayers; i++)
							users.getPlayer(i).writeOnSocket("inform " + users.getPlayer(logic.currentPlayer).getPieceName() + " has correctly accused " + player + " with the " + weapon + " in the " + room);
						
						// wait for it to catch up
						try {Thread.sleep(1000*2); } catch (Exception e) {}
						
						break;
					}
					else{ // LOSS:
						currPlayer.setComputerPlayer();
						currPlayer.writeOnSocket("inform Your accusation is incorrect.  Fail.");
						try {Thread.sleep(1000); } catch (Exception e) {}
						for(int i = 0; i < Utilities.numRequiredPlayers; i++)
							users.getPlayer(i).writeOnSocket("inform " + users.getPlayer(logic.currentPlayer).getPieceName() + " has incorrectly accused " + player + " with the " + weapon + " in the " + room);

						// wait for it to catch up
						try {Thread.sleep(1000*2); } catch (Exception e) {}
						
						// they lost, don't wait for them to end their turn
						break;
					}
				}
				else if(token[0].compareToIgnoreCase("guess") == 0 && !logic.hasGuessed){
					// catch a non-room guess:
					if(!logic.Board.Room.isAroom( logic.Board.Piece.getX(logic.currentPlayer), logic.Board.Piece.getY(logic.currentPlayer)))
						currPlayer.writeOnSocket("inform You are only allowed to make guesses when you are in a room.");
					else {
						
						String player = token[strNum++];
						String weapon = token[strNum++];
						
						/* Check to make sure that guess entries are valid choices in the game configurations */
						boolean isValidGuessParams = true;						
						for(int i=0;i<Utilities.pieceNames.length;i++)
							if(!Utilities.pieceNames[i].equalsIgnoreCase(player))
									isValidGuessParams = false;
						for(int i=0;i<Utilities.weaponNames.length;i++)
							if(!Utilities.weaponNames[i].equalsIgnoreCase(weapon))
									isValidGuessParams = false;
						
						if(isValidGuessParams)
							currPlayer.writeOnSocket("inform You have provided invalid guess input.  You may try again.");
						else {
							/* alert user to the fact that the guess was received: */
							currPlayer.writeOnSocket("inform Your guess has been executed.  You are not allowed any more guesses in this turn.");
							
							/* Sleep after socket write to allow catch-up */
							try {Thread.sleep(1000); } catch (Exception e) {}
							
							/* Actually make the guess: */
							GuessReturn guessResult = logic.guess(player, weapon);
							
							if(guessResult.Disprover != -1){
								System.out.println("SERVER: Disprover available");
								if(users.getPlayer(guessResult.Disprover).isComputerPlayer()){
									System.out.println("SERVER: Disprover is a computer player");
									String cardToShow = guessResult.cardName1;
									currPlayer.writeOnSocket("inform Your guess was disproved by " + users.getPlayer(guessResult.Disprover).getPieceName() + " with " + cardToShow +".  Fail.");
								}
								else if(guessResult.cardName2 != null){
									System.out.println("SERVER: contacting player for a card to show");
									users.getPlayer(guessResult.Disprover).writeOnSocket("choosecard " + guessResult.cardName1 + ";" + guessResult.cardName2 + ";" + guessResult.cardName3);
									
									/* Read the disproving player's card choice: */
									StringTokenizer card = new StringTokenizer( 
											users.getPlayer(guessResult.Disprover).readFromSocket() );

									/* Assume that the first token is "showcard"  and bypass it: */
									card.nextToken();
									
									String cardToShow = card.nextToken();
									
									/* inform user of their disproved guess: */
									currPlayer.writeOnSocket("inform Your guess was disproved by " + users.getPlayer(guessResult.Disprover).getPieceName() + " with " + cardToShow +".  Fail.");
								}
								/* The player holding the disproving card only has one card to show, so do it for them: */
								else{
									System.out.println("SERVER: default guess results returned");
									currPlayer.writeOnSocket("inform Your guess was disproved by " + users.getPlayer(guessResult.Disprover).getPieceName() + " with " + guessResult.cardName1 + ".  Fail.");
									try {Thread.sleep(1000*4); } catch (Exception e) {}
								}
								for(int i = 0; i < Utilities.numRequiredPlayers; i++)
									users.getPlayer(i).writeOnSocket("inform " + users.getPlayer(logic.currentPlayer).getPieceName() + " has incorrectly guessed " + player + " with the " + weapon);
								// wait for it to catch up
								try {Thread.sleep(1000*2); } catch (Exception e) {}
							}
							else{
								currPlayer.writeOnSocket("inform Your guess was NOT disproved.");
								for(int i = 0; i < Utilities.numRequiredPlayers; i++)
									users.getPlayer(i).writeOnSocket("inform " + users.getPlayer(logic.currentPlayer).getPieceName() + " has guessed " + player + " with the " + weapon + " and no one could disprove it.");
								// wait for it to catch up
								try {Thread.sleep(1000*2); } catch (Exception e) {}
							}
							logic.Board.serialize();
							for(int i = 0; i < Utilities.numRequiredPlayers; i++)
								users.getPlayer(i).writeOnSocket("update " + logic.Board.StringBoard());
							// wait for it to catch up
							try {Thread.sleep(1000*2); } catch (Exception e) {}
							
							/* this player is not allowed any more guesses */
							logic.hasGuessed = true;	
						}
					}
				}
			
				if(users.getPlayer(0).isComputerPlayer() && 
						users.getPlayer(1).isComputerPlayer() && 
						users.getPlayer(2).isComputerPlayer() && 
						users.getPlayer(3).isComputerPlayer() && 
						users.getPlayer(4).isComputerPlayer() && 
						users.getPlayer(5).isComputerPlayer()) {
					lost = false;
					for(int i = 0; i < Utilities.numRequiredPlayers; i++)
						users.getPlayer(i).writeOnSocket("inform No on was able to correctly accuse the murderer.  You all FAIL!!");
					// wait for it to catch up
					try {Thread.sleep(1000*2); } catch (Exception e) {}
				}
				
				message = users.getPlayer(logic.currentPlayer).readFromSocket();//find where the player moved to
				
				if(debugMode) System.out.println("SERVER: received \"" + message + "\" from player: " + users.getPlayer(logic.currentPlayer).getPlayerName() + " (while loop bottom)");
				
				token = message.split(" ");
				
			} // end the loop for a player's turn
			
			/* increment currentPlayer, 
			 * but make sure they are still active */
			do{ logic.nextPlayer(); }
			while(users.getPlayer(logic.currentPlayer).isComputerPlayer());
			
		}// end overall game loop
			
		for(int i = 0; i < Utilities.numRequiredPlayers; i++)
			users.getPlayer(i).writeOnSocket("gameover");
		// wait for it to catch up
		try {Thread.sleep(1000*2); } catch (Exception e) {}
		
	}
	
	
	/**
	 * <b>Purpose</b>: Shut down the server program. This is a convenience method for de-allocating the Server-Side components <br><br>
	 * <b>Precondition</b>: The game has been won by someone or lost by all <br>
	 * <b>Postcondition</b>: There are no longer instantiated server components. <br> 
	 * 
	 * <b>Pseudo-Code</b>: <br>
	 * BEGIN <br>
	 * - use the {@link System} method {@code exit(int)} to close this program <br>
	 * - flag the Operating System with this method's parameter value <br>
	 * END<br><br>
	 * 
	 * <em>LAST MODIFIED: Michael Philippone, 12 DEC 2008 </em><br>
	 * 
	 * @param status the {@link e_ExitCode} status flag to return to the operating system as an integer 
	 */
	public void systemShutdown(e_ExitCode status) { 
		if(this.debugMode){ 
			System.out.println(
					"SERVER: System shutting down with exit code \"" + 
					status.ordinal() + 
					"\".");
		}
		System.exit(status.ordinal()); //turn the enum into an int and return its value
	}
	
	//////////////////ACCESSORS//////////////////
	/**
	 * <b>Purpose</b>: An accessor method for obtaining a reference to the {@link Server}'s {@link Users} object <br><br>
	 * <b>Precondition</b>: The caller is expecting a reference to a {@link Users} object <br>
	 * <b>Postcondition</b>: The caller has a reference to a {@link Users} object
	 * @return a reference of the {@link Server}'s {@link Users} object
	 */
	public Users getUsers() { return this.users; }
	
	/**
	 * <b>Purpose</b>: An accessor method for obtaining a reference to the {@link Server}'s {@link GameLogic} object <br><br>
	 * <b>Precondition</b>: The caller is expecting a reference to a {@link Users} object <br>
	 * <b>Postcondition</b>: The caller has a reference to a {@link GameLogic} object
	 * @return a reference of the {@link Server}'s {@link GameLogic} object
	 */
	public GameLogic getGameLogic() { return this.logic; }

	
//////////////////////////////////////////////////////////////////////////////////////
/* :::MAIN METHOD:::*/
//////////////////////////////////////////////////////////////////////////////////////
		/**
		 * <b>Purpose</b>: This is the main method that is run for the server-side software.  It all starts here!  <br><br>
		 * <b>Precondition</b>: The system is NOT running.   <br>
		 * <b>Postcondition</b>: The system has started running and the server-side objects are instantiated <br>
		 * <b>Pseudo-Code</b> : <br>
		 * BEGIN <br>
		 * - create and instance of a {@link Server} object <br>
		 * - run {@code createServerComponents()} method <br>
		 * - run {@code prePlay()} method <br>
		 * - run {@code play()} method <br>
		 * - run {@code systemShutdown()} method <br>
		 * END <br>
		 * 
		 * @param args - any command line options passed in
		 */
		public static void main(String[] args) { 
			
			Server server = new Server();
			
			server.createServerComponents();
			server.prePlay();
			server.play();
			server.systemShutdown(Utilities.e_ExitCode.normal);
			
		}
//////////////////////////////////////////////////////////////////////////////////////
		
		/**
		 * <b>Purpose</b>: Utility method for prepping output streams and determining debug modes<br>
		 * <b>Explanation</b>: <br>
		 * Here seems as good a place as any to explain a bit about the File 
		 * output that appears throughout the server software. <br><br>  
		 * 
		 * 1) There is a flag in the {@link Server} class ({@code Server.debugMode}) that,
		 * when {@code true} allows special, added output to occurr.  The output consists of  
		 * messages regarding the server program's state, network connections, method alerts, non-fatal 
		 * error conditions and debugging information.  (And anything else that we MIGHT want to 
		 * communicate to an admin user. <br><br>
		 * 
		 * 2) Anywhere in the Server program that non-necessary output could occurr, the 
		 * {@code debugMode} flag is tested for {@code true} and the output is printed 
		 * accordingly.  (<em>For all necessary outputs, a duplicate message is printed to 
		 * the chosen debugging output stream: {@code Server.out}.</em> see #3 for more 
		 * info on the output stream(s)) <br><br>
		 * 
		 * 3) Throughout the software, there are calls to {@link System}{@code .out} methods.
		 * (ie: {@code System.out.print();} and {@code System.out.println();} ) those have 
		 * been modified to use the {@link Server} class' output stream "out" instead of the 
		 * System's output stream (with the same name, for conceptual ease of use). <br><br><br>
		 * 
		 * <b>Pseudo-Code</b>: <br>
		 * BEGIN <br>
		 * - IF debugging mode is on: <br>
		 * - - Assign the {@link Server}'s console output stream to a file (or leave it set to console if there is a failure) <br>
		 * - - return <br>
		 * END <br>
		 */
		private void prepOutput() {
			
			// ask user for debug mode:
			debugMode = (JOptionPane.showConfirmDialog(
					null, 
					"Would you like to run in debug mode?", 
					"Debug Mode", 
					JOptionPane.YES_OPTION) == 0);			
			
			if(this.debugMode) {
				//assign the output stream to "Clue_LOG.txt"
				System.setOut(Utilities.setFileOutput("Clue_LOG.txt"));

				System.out.println("\nSERVER: Debugging mode set to 'ON'.");
				System.out.println("SERVER: All messages will be prefaced with the originating component in caps.");
				System.out.println("SERVER: program started at " + Utilities.getTodaysDate() + ".");
				System.out.println("");
			}
		}
}
