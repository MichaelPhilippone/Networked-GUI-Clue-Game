package clueClient;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import clueServer.Server;

import utils.Utilities;

/**
 * <b>Class semantics and roles</b>: <br>
 * The BoardModel is the Model (as per Java's Model-View-Controller 
 * approach to GUI design) for the Clue system.  The BoardModel represents
 * the logical model of the Clue game.  <br>
 * The class contains a reference to the {@link Client} object also on the 
 * client-side so as to be able to update visual displays as it updates 
 * logical game-state data.
 *  
 * <br><br>
 * 
 * <b>Information maintenance</b>: <br>
 * <em>Creation</em>: the BoardModel is created when the {@link Client} creates it 
 * at the start of a client-side process <br>
 * <em>Deletion</em>: the BoardModel is deleted when the {@link Client} has stopped executing
 *  
 *  @author Michael Philippone (Class Skeletons and Code)
 */
public class BoardModel {
	
	/** the width of the game board (in {@link Cell}'s */
	public final int X_max = 24;
	
	/** the height of the game board (in {@link Cell}'s */
	public final int Y_max = 25;
	
	/** the array of cell structures representing the board */
	private Cell[][] board = new Cell[X_max][Y_max];
	
	/** an array of Cards for display on the screen <br> 
	 * The array is initialized to hold as many values as there 
	 * are rooms, pieces and weapons */
	private Card[] cards = null; // init this in the constuctor
	
	/** reference to the {@link Client} object */
	private Client client = null;
	
	/**
	 * <b>Purpose</b>: {@link BoardModel} Constructor <br>
	 * <b>Preconditions</b>: there is no instance of a {@link BoardModel} <br>
	 * <b>Postconditions</b>: there is some instance of a {@link BoardModel} <br>
	 * @param cl a reference to a {@link Client} object.  
	 */
	public BoardModel(Client cl) {
		
		// assign reference to Client instance:
		this.client = cl;
		
		//init the cards array:
		int numCards = 
			Utilities.roomNames.length +
			Utilities.pieceNames.length +
			Utilities.weaponNames.length;
		cards = new Card[ numCards ];
		
		// init the board: visit each element, cell by cell, 
		// going left to right, by rows, top to bottom
		for (int i=0; i<this.X_max; i++) {
			for (int j=0; j<this.Y_max; j++) {
				this.board[i][j] = 
					new Cell( 
							false,		//clickable
							"none",		//playerOn
							new Color(	//BG color, element 11 is pink
									Utilities.RGBColors[12][0],
									Utilities.RGBColors[12][1],
									Utilities.RGBColors[12][2]), 
							i, j); 		//position
				this.board[i][j].validate();
				this.board[i][j].setEnabled(true);
				this.board[i][j].setVisible(true);
			}
		}
		
		// init the hand of cards:
		// To do this, I am using three FOR loops to 
		// fill all 21 of the cards
		// I use the following 'int' variable, "numCards"
		// to step through all of the cards
		
		numCards = 0;
		
		for(int i=0; i < Utilities.roomNames.length; i++){
			// Create a new Card object (initialized too!)
			// at the current slot in the card array
			cards[numCards] = 
				new Card(
						Utilities.roomNames[i], // name (from rooms)
						false,	// clickable
						new Color( //BG color, element 3 is black
								Utilities.RGBColors[3][0],
								Utilities.RGBColors[3][1],
								Utilities.RGBColors[3][2]),
						false ); // is this card currently held by this gamer?

			// keep a running tally of how many cards 
			// deep we are in the cards array:
			numCards++;
		}
		for(int i=0; i < Utilities.pieceNames.length; i++){
			cards[numCards] = 
				new Card(
						Utilities.pieceNames[i], // name (from rooms)
						false,	// clickable
						new Color( //BG color, element 3 is black
								Utilities.RGBColors[3][0],
								Utilities.RGBColors[3][1],
								Utilities.RGBColors[3][2]),
						false ); // is this card currently held by this gamer?
			numCards++; 
		}
		for(int i=0; i < Utilities.weaponNames.length; i++){
			cards[numCards] = 
				new Card(
						Utilities.weaponNames[i], // name (from rooms)
						false,	// clickable
						new Color( //BG color, element 3 is black
								Utilities.RGBColors[3][0],
								Utilities.RGBColors[3][1],
								Utilities.RGBColors[3][2]),
						false ); // is this card currently held by this gamer?
			numCards++;
		}
	}

	/**
	 * <b>Purpose</b>: loads the passed in card names into {@link Card} objects in the card array <br>
	 * <b>Preconditions</b>: there has been a "havecard" message received from the server<br>
	 * <b>Postconditions</b>: the gamer's hand of cards has been 'dealt' <br>
	 * @param cardsIn the names of the cards to update, separated by ";" (semicolon) characters 
	 */
	public void haveCards(String cardsIn) {
		String msg = "BOARDMODEL("+client.getUsername()+"): ";
		
		// given the names of the cards in the gamer's hand--
		// delimited by ";" (semicolon) characters--
		// re-init those cards in card array with the same name 
		//  to be designated "held"
		String[] tk_cards = cardsIn.split(";");
		String cardName1 = tk_cards[0];
		String cardName2 = tk_cards[1];
		String cardName3 = tk_cards[2];
		
		//System.out.println("BOARDMODEL("+ client.getUsername() +"): haveCards called with: " + cardName1);
		//System.out.println("BOARDMODEL("+ client.getUsername() +"): haveCards called with: " + cardName2);
		//System.out.println("BOARDMODEL("+ client.getUsername() +"): haveCards called with: " + cardName3);
			
		// loop over all the cards, looking for the one with same name as "cardName"
		for(int i=0; i<this.cards.length; i++) {
			
			// IF this is the card we are looking for THEN re-init the card:
			if( cards[i].getText().compareToIgnoreCase(cardName1) == 0 ) {
				//System.out.println(msg + "holding Card: " + cards[i]);
				this.cards[i].held = true; 
				this.cards[i].setBackground(Color.RED);
				this.cards[i].clickable = false;	
			}
			else if( cards[i].getText().compareToIgnoreCase(cardName2) == 0 ) {
				//System.out.println(msg + "holding Card: " + cards[i]);
				this.cards[i].held = true; 
				this.cards[i].setBackground(Color.RED);
				this.cards[i].clickable = false;	
			}
			else if( cards[i].getText().compareToIgnoreCase(cardName3) == 0 ) {
				//System.out.println(msg + "holding Card: " + cards[i]);
				this.cards[i].held = true; 
				this.cards[i].setBackground(Color.RED);
				this.cards[i].clickable = false;	
			}
		
		}// end for
	}// end haveCards() method
	
	/**
	 * <b>Purpose</b>: Makes the selected card ready for choosing (in order to disprove another player's guess on the {@link Server} side) <br>
	 * <b>Preconditions</b>: there has been a "choosecard" message received from the server <br>
	 * <b>Postconditions</b>: there are clickable cards in the gamer's hand <br>
	 * @param names the names of the cards to update 
	 */
	public void chooseCards(String names) {
		String[] tk_cards = names.trim().split( ";" );
		String fromUser = "";
		
		String msg = 
			"You must choose a card to disprove a guess.  \n" + 
			"Your choices are: " + tk_cards[0] + ", " + tk_cards[1];
		
		if(tk_cards[2]!= null && !tk_cards[2].equalsIgnoreCase(""))
			msg += " or " + tk_cards[2];
		
		do {
			fromUser = JOptionPane.showInputDialog(msg);
			
			if(!fromUser.equalsIgnoreCase(tk_cards[0])) 
				break;
			
			if(!fromUser.equalsIgnoreCase(tk_cards[1]))
				break;
			
			if(tk_cards[2] != null)
				if( !tk_cards[2].equalsIgnoreCase("") )
					if(!fromUser.equalsIgnoreCase(tk_cards[2]))
						break;
			
		}
		while ( true );		
		
		client.getClientSocketReference().send("showcard " + fromUser);
	}
	
	/**
	 * <b>Purpose</b>: Method takes in a coordinate pair and an unparsed method 
	 * and performs the specified update directed in updateMessage parameter. <br><br>
	 * <b>Precondition</b>: The board is in some state <br>
	 * <b>Postcondition</b>: the board's state is altered based on the directive in updateMessage and at {@link Cell} (X,Y) <br>
	 * 
	 * <b>Pseudo-Code</b>: <br>
	 * BEGIN <br>
	 * - assign cell at (param0,param1) to have the clickable value of param3 <br>
	 * - assign cell at (param0,param1) to have the background color of the corresponding RGB value of the color (string) in param2 <br>
	 * END <br><br>
	 * 
	 * @param x the x coordinate of the update to occur
	 * @param y the y coordinate of the update to occur
	 * @param color Which color string should we match against from {@link Utilities}
	 * @param clickable is the cell to be updated allowed to be clicked?
	 */
	public void update(int x, int y, String color, boolean clickable) { 
		
		/* set the affected cell's clickability */
		board[x][y].clickable = clickable;
		
		Color c = null;
		/* since we have the string value of the color we need in "colorStr"
		 * we can loop over our array of color names in 
		 * Utilities and test for the right one: (one line, versus 12 if statements!) */
		for(int i=0; i<Utilities.BoardColors.length; i++){
			if(color.compareToIgnoreCase(Utilities.BoardColors[i]) == 0) {
				c = new Color(
						Utilities.RGBColors[i][0],
						Utilities.RGBColors[i][1],
						Utilities.RGBColors[i][2]);
			}
		}
		if(c == null) {
			/* there was no match in the color array, so c was never initialized */
			System.out.println("ERROR: Unrecognizable color choice message: " + color);
				
			/* so use the warning / error color values from the 12th element: */				
			c = new Color(
					Utilities.RGBColors[12][0],
					Utilities.RGBColors[12][1],
					Utilities.RGBColors[12][2]);
		}

		/* now assign the updating cell this new Color background */   
		this.board[x][y].setBackground(c);
	}
	
	
	/**
	 * <b>Purpose</b>: parse through the string to create the board update array <br>
	 * <b>Preconditions</b>: there has been an update message received from the server with cells specified for updating on the {@code board} object <br>
	 * <b>Postconditions</b>: there has been an array created for {@code board} cell updating <br>
	 * 
	 * <b>Pseudo-Code</b>: <br>
	 * BEGIN <br>
	 * - split the string on the ";" (semicolon) characters into SPLIT_1 <br>
	 * - FOR EACH string in SPLIT_1: <br>
	 * - - split the string on the "," (comma) characters into 3 new strings, STR_A, STR_B, STR_C, STR_D <br>
	 * - - call {@code update(STR_A, STR_B, STR_C, STR_D)} <br>
	 * END <br><br>
	 * 
	 * <b>NOTE</b>: The string passed to this method should be formatted as follows:<br>
	 * "X1,Y1,STATE1,CLICKABLE;X2,Y2,STATE2,CLICKABLE;X3,Y4,STATE3,CLICKABLE;...;Xn,Yn,STATE4,CLICKABLE" <br>
	 * Tuples of the form (X,Y,STATE) are separated by a semicolon, ";" <br>
	 * X: the x coordinate of the cell to update <br>
	 * Y: the y coordinate of the cell to update <br>
	 * STATE: how should the cell be updated (<em>see "REPO://src/SocektStringProtocol.txt" for a full rundown of socket string syntax</em>) <br>
	 * 
	 * @param updateStr the string from the server's socket communication
	 */
	public void parseUpdateString(String updateStr) {
		
		/* Catch a blank update string (and ignore it) */
		if(updateStr.equalsIgnoreCase("")) {
			System.out.println("BOARDMODEL.parseUpdateString(): received empty string.");
			return;
		}
		
		String[] tk_semiColonSplitter = updateStr.split( ";" );
		String[] tk_commaSplitter = null;
		
		/* X Y coordinates for each update tuple */
		int x,y;
		
		/* boolean value for whether or not the cell is clickable: */
		boolean clickable;
		
		/* the color value for each cell update tuple */
		String color;
		
		/* loop over the string for all update tuples in it: */
		for(int i=0; i<tk_semiColonSplitter.length; i++) {

			/* split this update tuple on commas
			 * this (SHOULD) give us the X, Y and STATE
			 * each in the 'nextToken()' call of 'tk_commaSplitter' */
			tk_commaSplitter = tk_semiColonSplitter[i].split(",");
			
			x = Integer.parseInt( tk_commaSplitter[0] );
			y = Integer.parseInt( tk_commaSplitter[1] );
			color = tk_commaSplitter[2];
			clickable = (tk_commaSplitter[3].compareToIgnoreCase("true") == 0);
			
			/* update the cell on the grid specified by this update tuple: */
			this.update( x, y, color, clickable );				
		}
	}
	
////////////////////////////////////////////////////////////////////////
	/* ***********
	 * ACCESSORS *
	 *********** */
////////////////////////////////////////////////////////////////////////	
	/**
	 * <b>Purpose</b>: get a reference to the array of Cell objects <br>
	 * <b>Preconditions</b>: there is an array of initialized {@link Cell}s <br>
	 * <b>Postconditions</b>: none <br>
	 * 
	 * @return an instance of the {@link Cell} array <br>
	 */
	public Cell[][] getBoardArray() { return this.board; }
	
	/**
	 * <b>Purpose</b>: get a reference to the array of {@link Card} objects <br>
	 * <b>Preconditions</b>: there is an array of initialized {@link Card}s <br>
	 * <b>Postconditions</b>: none <br>
	 * 
	 * @return an instance of the {@link Cell} array <br>
	 */
	public Card[] getCardArray() { return this.cards; }
	
	/**
	 * <b>Purpose</b>: get the number of cards held in this gamer's hand <br>
	 * <b>Preconditions</b>: there is an array of initialized {@link Card}s <br>
	 * <b>Postconditions</b>: none <br>
	 * 
	 * @return an int corresponding to the number of cards <br>
	 * @deprecated use the sum of rooms, weapons, and game pieces, ie:
	 * {@code Utilities.pieceNames.length + Utilities.roomNames.length + Utilities.weaponNames.length }
	 */
	public int getnumCards() { 
		int num = 0;
		for(int i=0; i < cards.length; i++) {
			if(!cards[i].getText().equalsIgnoreCase(""))
				num++;
		}
		return num;
	}
	
////////////////////////////////////////////////////////////////////////
	/* ***********
	 * INNER CLASSES *
	 *********** */
////////////////////////////////////////////////////////////////////////	
	/**
	 * <b>Class semantics and roles</b>: <br>
	 * The Cell class is an inner class that 
	 * represents a single grid square on the board.
	 * The class implements {@link MouseListener} so as to allow mouse event handling. <br><br>
	 * <b>Information maintenance</b>: <br>
	 * <em>Creation</em>: A Cell class is created when the board is created.  
	 * They are thereafter updated any time a logical update to the board occurs.
	 * ie: a player moves into / out of a room, etc. <br>
	 * <em>Deletion</em>: a cell is destroyed when the BoardModel is destroyed.
	 * 
	 * @author Michael Philippone
	 */
	public class Cell extends JPanel implements MouseListener { 
		/** a representation as to whether the square should have attention paid if clicked */
		private boolean clickable = false; 
		
		/** if non-empty, the string is the value ofthe player occupying the cell */
		private String playerOn = "";
		
		/** the X coord of this cell */
		private int cellX = -1;
		
		/** the Y coord of this cell */
		private int cellY = -1;
		
		/**
		 * <b>Purpose</b>: create and init a {@link Cell} object <br>
		 * <b>Preconditions</b>: there is no instance of a {@link Cell} <br>
		 * <b>Postconditions</b>: There is an instance of a {@link Cell} <br>
		 * 
		 * @param canClick initial value for the "clickable" field
		 * @param player initial value for the "playerOn" field
		 * @param c initial value for the "color" field, also sets the background color
		 * @param x initial value for the "cellX" field
		 * @param y initial value for the "cellY" field
		 */
		public Cell(boolean canClick, String player, Color c, int x, int y) {
			setSize(8, 8);
			setBackground(c);
			this.playerOn = player;
			this.clickable = canClick;
			this.cellX = x;
			this.cellY = y;
			
			//set up this Cell to be its own event handler:
			addMouseListener(this);
		}
		
		/**
		 * <b>Purpose</b>:
		 * When the cell is clicked this is the event handler called. <br>
		 * The logic of the method should only be used if the cell's clickable value is 'true' <br><br>
		 * <b>Precondition</b>: The game is being played and the board is displayed on the client machine's screen.  
		 * Also, the cell that is about to be clicked has been deemed 'clickable' in the {@link BoardModel}  <br>
		 * <b>Postcondition</b>: the necessary logic for a cell-click is executing <br>
		 * @param e an {@link MouseEvent} object corresponding to what triggered the event
		 */
		public void mouseClicked(MouseEvent e) {
			/* what color (the actual string representation 
			 * of the color's name) is this cell? */
			int colorNum = 0;
			for(int i=0; i<Utilities.RGBColors.length; i++) {
				if( getBackground().getRed() == Utilities.RGBColors[i][0] && 
						getBackground().getGreen() == Utilities.RGBColors[i][1] &&
						getBackground().getBlue() == Utilities.RGBColors[i][2] ) {
					colorNum = i;
					break;
				}
			}
			
			String clicked = 			
				"CELL CLICKED: \n" +
				"     X: " + cellX + "\n" +
				"     Y: " + cellY + "\n" +
				"     playerOn: " + playerOn + "\n" +
				"     Color: " + Utilities.BoardColors[colorNum] + "\n" +
				"     clickable: " + clickable + ".";
			
			/* If this cell is allowed to be listened to when it is clicked,
			 * shoot the move command to the server */
			if(this.clickable)
				client.getClientSocketReference().send("move " + cellX + " " + cellY);
			
			System.out.println(clicked);
		}
		/* These must stay here to satisfy use of the MouseListener interface */
		public void mouseEntered(MouseEvent e) { /* NOT USED */ }
		public void mouseExited(MouseEvent e) { /* NOT USED */ }
		public void mousePressed(MouseEvent e) { /* NOT USED */ }
		public void mouseReleased(MouseEvent e) { /* NOT USED */ }
		
	}
	
	/**
	 * <b>Class semantics and roles</b>: <br>
	 * The Card class is an inner class that represents a single card in the gamer's hand.
	 * The class implements {@link MouseListener} so as to allow mouse event handling. <br><br>
	 * <b>Information maintenance</b>: <br>
	 * <em>Creation</em>: A {@link Card} object is created when the {@link BoardModel} is created. <br>
	 * <em>Deletion</em>: a {@link Card} is destroyed when the {@link BoardModel} is destroyed. <br>
	 * 
	 * @author Michael Philippone
	 */
	public class Card extends JCheckBox implements MouseListener { 
			
		/** can this card display be clicked? */
		private boolean clickable = false;
		
		/** Is this card currently in the gamer's hand? */
		private boolean held = false;

		
		/**
		 * <b>Purpose</b>: create an instance of a {@link Card} <br>
		 * <b>Preconditions</b>: there is no {@link Card} instance <br>
		 * <b>Postconditions</b>: there is an initialized card instance <br>
		 */
		public Card(String valIn, boolean canClick, Color c, boolean amIHeld) {	
			// initialize class properties:
			this.held = amIHeld;
			this.clickable = canClick;
			setText(valIn);
			
			// if the card is held, adjust its display properties
			// only listen for mouse events if the card is not held
			if(held) {
				setSelected(true);
				setBackground(Color.RED);
			}
			else {
				setBackground(Color.LIGHT_GRAY);
				addMouseListener(this);
			}
			
			//prep this thing for display:
			validate();
			setVisible(true);
		}
		
		/**
		 * <b>Purpose</b>: When the card is clicked this is the event handler that gets called. 
		 * The logic of the method should only be used if the card's clickable value is 'true'  <br><br>
		 * <b>Precondition</b>: The game is being played and the cards are displayed on the client machine's screen.  
		 * Also, the card that is about to be clicked has been deemed 'clickable' in the {@link BoardModel}  <br>
		 * <b>Postcondition</b>: the necessary logic for a card-click is executing <br><br>
		 * @param e an {@link MouseEvent} object corresponding to what triggered the event
		 */
		public void mouseClicked(MouseEvent e) {
			
			// what color (the actual string representation of the color's name) is this cell?
			int colorNum = 0;
			for(int i=0; i<Utilities.RGBColors.length; i++) {
				if( getBackground().getRed() == Utilities.RGBColors[i][0] && 
						getBackground().getGreen() == Utilities.RGBColors[i][1] &&
						getBackground().getBlue() == Utilities.RGBColors[i][2] ) {
					colorNum = i;
					break;
				}
			}
			
			String clicked = 
				"CARD CLICKED: \n" +
				"     Card name: " + getText() + "\n" +
				"     This card is held: " + held + "\n" +
				"     Color: " + Utilities.BoardColors[colorNum] + "\n" +
				"     This card is clickable: " + clickable + ".";
			
			if(this.clickable) {  }
			System.out.println(clicked);
		}
		/* These must stay here to satisfy use of the MouseListener interface */
		public void mouseEntered(MouseEvent e) { /* NOT USED */ }
		public void mouseExited(MouseEvent e) { /* NOT USED */ }
		public void mousePressed(MouseEvent e) { /* NOT USED */ }
		public void mouseReleased(MouseEvent e) { /* NOT USED */ }
		
	}
	

}
