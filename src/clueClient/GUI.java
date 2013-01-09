package clueClient;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;

import utils.Utilities;
import utils.Utilities.e_ExitCode;

/**
 * <b>Class semantics and roles</b>: <br>
 * The GUI maintains and manages graphical display for the system.  This is where the client will
 * be interacting with the game. <br>
 * This will also contain a vast collection of support classes including buttons, event handlers, labels, containers.
 *(Not all support classes yet implemented)
 *  
 * <br><br>
 * <b>Information maintenance</b>: <br>
 * <em>Creation</em>: the GUI is created when the {@link Client} creates it 
 * at the start of a client-side process <br>
 * <em>Deletion</em>: the GUI is deleted when the {@link Client} has stopped executing
 *  
 *  @author Todd Wright (Class Skeletons)
 *  @author The Green Team's Collaborative Efforts (Code)
 */
public class GUI extends JFrame implements WindowListener {
	
	/** Reference to {@link Client} object */
	private Client client = null;
	
	/** a {@link JPanel} reference for filling this frame */
	private JPanel contentPane = null;
	
	/** The graphical representation of the game board */
	private JPanel boardPanel = null;
	
	/** The panel to hold buttons */
	private JPanel buttonPanel = null;
	
	/** panel to hold widgets for message displays */
	private JPanel messagePanel = null;
	
	/** panel to hold widgets for the help display */
	private JPanel helpPanel = null;
	
	/** panel to hold card viewing graphics */
	private JPanel cardsPanel = null;
	
	/** The button for guess.  */
	private JButton guess = null;
	
	/** The button for accuse.  */
	private JButton accuse = null;
	
	/** The button for ending your current turn.  */
	private JButton endTurn = null;
	
	/** The button for displaying the 'help' panel.  */
	private JButton helpBtn = null;
	
	/** label for communicating messages to user through {@link GUI}'s display */
	private JLabel msgLbl = null;
	
	/** size constant for a card graphic */
	private final Dimension d_cardSize = new Dimension(20,20);
	
	/** size constant for a board cell graphic */
	private final Dimension d_cellSize = new Dimension(25,25);
	
	/** size constant for a JButton component */
	private final Dimension d_btnSize = new Dimension(100,20);
	 
	
	/**
	 * Default Constructor <br>
	 * <b>Purpose</b>: Initialize the board components and initially draw the game board <br><br>
	 * <b>Precondition</b>: all board components are null and the screen is empty of Clue game components <br>
	 * <b>Postcondition</b>: the board components are initialized and the screen has something drawn on it. <br><br>
	 * <br>
	 * <em>CREATED: 13 DEC 2008 Michael Philippone</em> <br>
	 * <em>LAST MODIFIED: 16 DEC 2008 Michael Philippone</em> <br>
	 * 
	 * @param cl Reference to a {@link Client} object
	 */
	public GUI(Client cl) {
		this.client = cl;
		
		/* get a hold of the contentPane for this frame:  */
		this.contentPane = (JPanel) this.getContentPane();
		
		/* null layout manager... */
		this.contentPane.setLayout(null);
		
		/* now, set up the Window: */
		this.setTitle(Utilities.ClientFrameTitle);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		/* set window size: */
		this.setSize( new Dimension( Utilities.WindowWidth , Utilities.WindowHeight ) );
		
		/* CENTER THE window */
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frame = this.getSize();
		
		if (frame.height > screen.height)
		      frame.height = screen.height;
	    if (frame.width > screen.width)
	      frame.width = screen.width;
	    
	    this.setLocation((screen.width-frame.width)/2, (screen.height-frame.height)/2);
	    this.setVisible(true);
	    
	    /* Make the look and feel consistent with the user's OS: */
	    try {
	    	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    }
	    catch(InstantiationException ie) { System.out.println("Error setting look and feel. (InstantiatonException)"); }
	    catch(ClassNotFoundException cnfe) { System.out.println("Error setting look and feel. (ClassNotFoundException)"); }
	    catch(IllegalAccessException iae) { System.out.println("Error setting look and feel. (IllegalAccessException)"); }
	    catch(UnsupportedLookAndFeelException ule) { System.out.println("Error setting look and feel. (UnsupportedLookAndFeelException)"); }
	    
		
		/* set up all the other components' initial values... */
	    prepButtonPanel();
		prepBoardPanel();
		prepMessagePanel();
		prepCardPanel();
		prepHelpPanel();
		
		/* draw this frame */
		draw();
		
		/* Room Labeling MUST go AFTER board cell drawing, 
		 * b/c it relies on cell locations: */
		prepRoomLabels(); 
	}	
	
	/**
	 * <b>Purpose</b>: Convenience function for preparing the help panel objects <br>
	 * <b>Preconditions</b>: there is an instance of a GUI, but nothing regarding the help panel is set up yet <br>
	 * <b>Postconditions</b>: the help-related graphical display objects are ready <br>
	 * <br>
	 * <em>CREATED: 16 DEC 2008 Michael Philippone</em> <br>
	 * <em>LAST MODIFIED: 16 DEC 2008 Michael Philippone</em> <br>
	 */
	private void prepHelpPanel() {
		
		helpPanel = new JPanel();
		helpPanel.setLayout( null );
		helpPanel.setSize( new Dimension(700 , 670) );
		
		/* Must be initially visible */
		helpPanel.setVisible(true);
		
		/* help panel components 
		 * (never change, so we can just add 
		 * them here and put them in the panel) */
		// "" + "\n" +
		String story  = 
			"Welcome to the exciting game of Clue, a classic 'whodunnit' with a new networked twist!" + "\n\n" +
			"You and several other guests, have been invited to Mr. Body's mansion for\n" + 
			"a weekend of extravagant feasting, late night karaoke and expensive caviars." + "\n" + 
			"But woe befalls the event: for Mr. Body has been MURDERED!" + "\n" +
			"And what's more, it was one of the guests at his very own party." + "\n" +
			"It is up to you, party-goer turned detective, to determine his murderer" + "\n" + 
			"as well as the gruesome details of his fate." + "\n\n" + 
			"Have fun and good Luck!" + "\n" + 
			"Mr Body depends on your sleuthing!";
		
		String rules =
			"In order to be an effective detective there are a few rules you must follow:" + "\n" +
			"  1) You may move anywhere on the board on your turn" + "\n" +
			"  2) You may make guesses as to 'whodunnit' only in a room, during your turn, after a move" + "\n" +
			"  3) You may make an accusation as to 'whodunnit' during your turn, after a move" + "\n" +
			"    !! Beware! A wrong accusation will cause you to lose the game!  Accuse wisely!" + "\n" +
			"  4) The game ends when all of the players have accused incorrectly or one player\n"+ 
			"    has determined the murderer, weapon and location of Mr Body's ultimate demise" + "\n" +
			"  5) Your turn begins when you have clickable board spaces to which to move \n" + 
			"    (see color code below)" + "\n" +
			"\n\nHere are the colors you will see around the house:";
			
		/* Use one color for all help panel components' backgrounds */
		Color bgColor = 
			new Color(
					Utilities.RGBColors[1][0],
					Utilities.RGBColors[1][1],
					Utilities.RGBColors[1][2] );
		
		/* INIT the component for the story info: */
		JTextArea storyArea = new JTextArea(story);
		storyArea.validate();
		storyArea.setVisible(true);
		storyArea.setEnabled(true);
		storyArea.setBackground(bgColor);
		storyArea.setSize(new Dimension(600,180));
		
		/* INIT the component for the rules info: */
		JTextArea rulesArea = new JTextArea(rules);
		rulesArea.validate();
		rulesArea.setVisible(true);
		rulesArea.setEnabled(true);
		rulesArea.setBackground(bgColor);
		rulesArea.setSize(new Dimension(600,195));
		
		/* INIT and CONFIGURE the button for hiding the help panel 
		 * This button should render the helpPanel invisible when clicked: */		
		JButton hideHelpBtnMini = new JButton("X");
		hideHelpBtnMini.setEnabled(true);
		hideHelpBtnMini.setSize(new Dimension(38,38));
		hideHelpBtnMini.setBackground(bgColor);
		hideHelpBtnMini.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						helpPanel.setVisible(false);
						helpBtn.setVisible(true);

						/* show all the other panels */
						cardsPanel.setVisible(true);
						messagePanel.setVisible(true);
						boardPanel.setVisible(true);
						buttonPanel.setVisible(true);
						
						draw();
					}
				});
	
		/* Create a panel for a color-code legend: */
		JPanel colorsPanel = makeColorSwatchPanel();
		
		
		/* ADD the components to the help panel */
		hideHelpBtnMini.setLocation(656,5);
		storyArea.setLocation(50,30);
		rulesArea.setLocation(
				50,
				(storyArea.getLocation().y + storyArea.getSize().height + 20) );
		colorsPanel.setLocation(
				(helpPanel.getSize().width-colorsPanel.getSize().width)/2,
				(rulesArea.getLocation().y + rulesArea.getSize().height + 20) );
		
		helpPanel.add(hideHelpBtnMini);
		helpPanel.add(storyArea);
		helpPanel.add(rulesArea);
		helpPanel.add(colorsPanel);
		
		/* set the background of the message 
		 * panel to a value from the 
		 * Utilities RGBColors array: */
		helpPanel.setBackground(bgColor);
	}
	
	/**
	 * <b>Purpose</b>: Convenience method to create a container and populate it with color swatches and their corresponding labels <br>
	 * <b>Preconditions</b>: there is an instance of a GUI, and the help panel is being populated <br>
	 * <b>Postconditions</b>: a {@link JPanel} exists with color swatches and labels inside <br>
	 * <br>
	 * <em>CREATED: 16 DEC 2008 Michael Philippone</em> <br>
	 * <em>LAST MODIFIED: 16 DEC 2008 Michael Philippone</em> <br>
	 * 
	 * @return a {@link JPanel} instance of the color swatches in a container
	 */
	public JPanel makeColorSwatchPanel() {
		JPanel colorsPanel = new JPanel();
		colorsPanel.setLayout( null );
		colorsPanel.setSize( new Dimension(640 , 170) );
		colorsPanel.setBackground(Color.GRAY);
		
		/* For every color, make a little swatch to represent it
		 * also include a corresponding label: */
		JPanel tmp = null;
		JLabel tmplbl = null;
		int distFromLeft = colorsPanel.getLocation().x + 45;
		int distFromTop = colorsPanel.getLocation().y + 15;
		int lblWidth, swatchWidth=30, lblHeight=30, swatchHeight=30;
		
		for(int i=0; i<Utilities.BoardColors.length; i++) {
			/* set up the heights and widths for this iteration: */
			lblWidth = (Utilities.ColorsMeanings[i].length() * 8);

			/* make a panel for the swatch: */
			tmp = new JPanel();
			tmp.setBackground(new Color(
					Utilities.RGBColors[i][0],
					Utilities.RGBColors[i][1],
					Utilities.RGBColors[i][2]));
			/* the swatch should be as long as there are characters in the name: */
			tmp.setSize(new Dimension(30,30));
			tmp.validate();
			tmp.setEnabled(true);
			tmp.setVisible(true);
			
			/* make a label for the swatch panel: */
			tmplbl = new JLabel(Utilities.ColorsMeanings[i]);
			tmplbl.validate();
			tmplbl.setEnabled(true);
			tmplbl.setVisible(true);
			/* the label should be as long as the swatch: */
			tmplbl.setSize(new Dimension( lblWidth ,30));
			
			/* add the color swatch & its label */
			colorsPanel.add(tmplbl);
			tmplbl.setLocation(distFromLeft, (distFromTop+swatchHeight+2) );
			colorsPanel.add(tmp);
			/* center the swatch above the label: */
			tmp.setLocation( 
					(distFromLeft + ((lblWidth-swatchWidth) / 2)), 
					distFromTop ); 
			
			/* now, advance the left position marker based on width of the label */
			distFromLeft += lblWidth + 10;
			
			if( i == (int)Utilities.BoardColors.length / 2) {
				distFromLeft = colorsPanel.getLocation().x + 45;
				distFromTop += distFromTop+swatchHeight+lblHeight + 4;
			}
		}
		colorsPanel.validate();
		colorsPanel.setEnabled(true);
		colorsPanel.setVisible(true);
		
		return colorsPanel;
	}
	
	/**
	 * <b>Purpose</b>: Convenience function for preparing the message display objects <br>
	 * <b>Preconditions</b>: there is an instance of a GUI, but nothing regarding the messages display is set up yet <br>
	 * <b>Postconditions</b>: the message-related graphical display objects are ready <br>
	 * 
	 * <br>
	 * <em>CREATED: 13 DEC 2008 Michael Philippone</em> <br>
	 * <em>LAST MODIFIED: 13 DEC 2008 Michael Philippone</em> <br>
	 */
	private void prepMessagePanel() {
		this.msgLbl = new JLabel("     Waiting for remaining players to join.");
		this.messagePanel = new JPanel( new GridLayout(1,1) );
		this.messagePanel.add(this.msgLbl);
		this.messagePanel.setSize(new Dimension(675,75));
		this.messagePanel.setVisible(false);

		//set the background of the message panel to a value from the Utilities RGBColors array:
		this.messagePanel.setBackground(
				new Color(
					Utilities.RGBColors[1][0],
					Utilities.RGBColors[1][1],
					Utilities.RGBColors[1][2] ));
	}
	
	/**
	 * <b>Purpose</b>: Convenience function for preparing the board objects <br>
	 * <b>Preconditions</b>: there is an instance of a GUI, but nothing regarding the board is set up yet <br>
	 * <b>Postconditions</b>: the board-related graphical display objects are ready <br>
	 * <br>
	 * <em>CREATED: 13 DEC 2008 Michael Philippone</em> <br>
	 * <em>LAST MODIFIED: 13 DEC 2008 Michael Philippone</em> <br>
	 */
	private void prepBoardPanel() { 
		
		int x = this.client.getBoardModelReference().X_max;
		int y = this.client.getBoardModelReference().Y_max;
		
		this.boardPanel = new JPanel( new GridLayout( x , y , 2 , 2 ));
		this.boardPanel.setSize(new Dimension(675,625));
		this.boardPanel.setBackground(new Color(222,222,222));
		this.boardPanel.setVisible(false);
	}
	
	/**
	 * <b>Purpose</b>: Convenience function for preparing the card objects <br>
	 * <b>Preconditions</b>: there is an instance of a GUI, but nothing regarding the cards is set up yet <br>
	 * <b>Postconditions</b>: the card-related graphical display objects are ready <br>
	 * <br>
	 * <em>CREATED: 13 DEC 2008 Michael Philippone</em> <br>
	 * <em>LAST MODIFIED: 13 DEC 2008 Michael Philippone</em> <br>
	 */
	private void prepCardPanel() { 
		
		// the total number of game cards is equal to trhe 
		// the sum of rooms, weapons, and game pieces:
		int numCards = 
			Utilities.pieceNames.length + 
			Utilities.roomNames.length +
			Utilities.weaponNames.length;
		
		// Grid layout, (number of cards in the gamer's hand) x (1)
		// (with 4 pixels padding around each)
		this.cardsPanel = 
			new JPanel( 
				new GridLayout( numCards+2 , 1 , 1 , 1));
		
		//set the height of the panel to the 
		// (number of cards * the height of one card + 40)
		this.cardsPanel.setSize( 
				new Dimension( 150, ((numCards+2) * d_cardSize.height) ));
		this.cardsPanel.setBackground(Color.GRAY);
		
		this.cardsPanel.add(new JLabel("CARDS"));
		this.cardsPanel.add(new JLabel("(held cards in red)"));
		
		this.cardsPanel.setVisible(false);
	}	
	
	/**
	 * <b>Purpose</b>: Convenience function for preparing the button objects <br>
	 * <b>Preconditions</b>: there is an instance of a GUI, but nothing regarding the buttons is set up yet <br>
	 * <b>Postconditions</b>: the button-related graphical display objects are ready <br>
	 * <br>
	 * <em>CREATED: 13 DEC 2008 Michael Philippone</em> <br>
	 * <em>LAST MODIFIED: 16 DEC 2008 Michael Philippone</em> <br>
	 */
	private void prepButtonPanel() { 
		
		//////////////////////////////////////////////////
		/* MAKE THE PANEL: */
		//////////////////////////////////////////////////
		
		/* make an instance of the buttonPanel
		 * 3 rows tall, 1 col wide: */
		this.buttonPanel = new JPanel( new GridLayout(4,1,2,2) );
		
		
		//////////////////////////////////////////////////
		/* MAKE THE BUTTONS: */
		//////////////////////////////////////////////////
		
		/* instantiate the accuse button and then
		 * create an anonymous (read: nameless) instance of an actionListener
		 * then add the actionPerformed method event to it. */
		this.accuse = new JButton("Accuse");
		this.accuse.setEnabled(true);
		this.accuse.setSize(d_btnSize);
		this.accuse.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						accuseBtnClick();
					}
				});
		
		/* instantiate the guess button and then
		 * create an anonymous (read: nameless) instance of an actionListener
		 * then add the actionPerformed method event to it. */
		this.guess = new JButton("Guess");
		this.guess.setEnabled(true);
		this.guess.setSize(d_btnSize);
		this.guess.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						guessBtnClick();
					}
				});
		
		/* instantiate the endTurn button and then
		 * create an anonymous (read: nameless) instance of an actionListener
		 * then add the actionPerformed method event to it. */
		this.endTurn = new JButton("End Turn");
		this.endTurn.setEnabled(true);
		this.endTurn.setSize(d_btnSize);
		this.endTurn.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						endTurnBtnClick();
					}
				});
		
		/* instantiate the help button and then
		 * create an anonymous (read: nameless) instance of an actionListener
		 * then add the actionPerformed method event to it. */
		this.helpBtn = new JButton("Help");
		this.helpBtn.setEnabled(true);
		this.helpBtn.setSize(d_btnSize);
		this.helpBtn.addActionListener(
				new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						helpBtnClick();
					}
				});
		
		
		//////////////////////////////////////////////////
		/* ADD THE BUTTONS TO THE PANEL: */
		//////////////////////////////////////////////////
		
		// Now, add the buttons to the buttonPanel:
		this.buttonPanel.add(this.guess);
		this.buttonPanel.add(this.endTurn);
		this.buttonPanel.add(this.accuse);
		this.buttonPanel.add(this.helpBtn);
		
		//////////////////////////////////////////////////
		/* CONFIGURE THE PANEL: */
		//////////////////////////////////////////////////
		
		/* Make the button panel as tall as the 
		 * combined heights of the buttons we are adding in: */
		this.buttonPanel.setSize( new Dimension(150, 150) );
		this.buttonPanel.setBackground(Color.BLUE);		
		this.buttonPanel.setVisible(false);
	}
	
	/**
	 * <b>Purpose</b>: Convenience method applying labels above rooms on the graphical display <br>
	 * <b>Preconditions</b>: there is an instance of a GUI, and it is populated with colored cells <br>
	 * <b>Postconditions</b>: the spaces that correspond to rooms have a label 'floating' above them <br>
	 * <br>
	 * <em>CREATED: 16 DEC 2008 Michael Philippone</em> <br>
	 * <em>LAST MODIFIED: 16 DEC 2008 Michael Philippone</em> <br>
	 */
	private void prepRoomLabels() {
		// kitchen,  diningRoom, lounge
		// ballroom,  basement,  hall
		// conservatory,  billiardRoom,  library,  study
		
		JLabel kitchen = new JLabel("Kitchen");
		kitchen.setBackground(Color.WHITE);
		kitchen.validate();
		kitchen.setEnabled(true);
		kitchen.setVisible(true);
		kitchen.setSize(new Dimension(100, 30));
		kitchen.setLocation(
			client.getBoardModelReference().getBoardArray()[3][2].getLocation()	
			);
		
		JLabel diningRoom = new JLabel("Dining Room");
		diningRoom.setBackground(Color.WHITE);
		diningRoom.validate();
		diningRoom.setEnabled(true);
		diningRoom.setVisible(true);
		diningRoom.setSize(new Dimension(100, 30));
		diningRoom.setLocation(
				client.getBoardModelReference().getBoardArray()[2][11].getLocation()	
				);
		
		JLabel lounge = new JLabel("Lounge");
		lounge.setBackground(Color.WHITE);
		lounge.validate();
		lounge.setEnabled(true);
		lounge.setVisible(true);
		lounge.setSize(new Dimension(100, 30));
		lounge.setLocation(
				client.getBoardModelReference().getBoardArray()[3][20].getLocation()	
		);
		
		JLabel ballroom = new JLabel("Ballroom");
		ballroom.setBackground(Color.WHITE);
		ballroom.validate();
		ballroom.setEnabled(true);
		ballroom.setVisible(true);
		ballroom.setSize(new Dimension(100, 30));
		ballroom.setLocation(
				client.getBoardModelReference().getBoardArray()[11][2].getLocation()	
		);
		
		JLabel basement = new JLabel("Basement");
		basement.setBackground(Color.WHITE);
		basement.validate();
		basement.setEnabled(true);
		basement.setVisible(true);
		basement.setSize(new Dimension(100, 30));
		basement.setLocation(
				client.getBoardModelReference().getBoardArray()[11][12].getLocation()	
		);
		
		JLabel hall = new JLabel("Hall");
		hall.setBackground(Color.WHITE);
		hall.validate();
		hall.setEnabled(true);
		hall.setVisible(true);
		hall.setSize(new Dimension(100, 30));
		hall.setLocation(
				client.getBoardModelReference().getBoardArray()[10][20].getLocation()	
		);
		
		JLabel conservatory = new JLabel("Conservatory");
		conservatory.setBackground(Color.WHITE);
		conservatory.validate();
		conservatory.setEnabled(true);
		conservatory.setVisible(true);
		conservatory.setSize(new Dimension(100, 30));
		conservatory.setLocation(
				client.getBoardModelReference().getBoardArray()[20][1].getLocation()	
		);
		
		JLabel billiardRoom = new JLabel("Billiard Room");
		billiardRoom.setBackground(Color.WHITE);
		billiardRoom.validate();
		billiardRoom.setEnabled(true);
		billiardRoom.setVisible(true);
		billiardRoom.setSize(new Dimension(100, 30));
		billiardRoom.setLocation(
				client.getBoardModelReference().getBoardArray()[19][8].getLocation()	
		);
		
		JLabel library = new JLabel("Library");
		library.setBackground(Color.WHITE);
		library.validate();
		library.setEnabled(true);
		library.setVisible(true);
		library.setSize(new Dimension(100, 30));
		library.setLocation(
				client.getBoardModelReference().getBoardArray()[19][15].getLocation()	
		);
		
		JLabel study = new JLabel("Study");
		study.setBackground(Color.WHITE);
		study.validate();
		study.setEnabled(true);
		study.setVisible(true);
		study.setSize(new Dimension(100, 30));
		study.setLocation(
				client.getBoardModelReference().getBoardArray()[19][22].getLocation()	
		);
		
		contentPane.add( kitchen );
		contentPane.add( diningRoom );
		contentPane.add( lounge );
		contentPane.add( ballroom );
		contentPane.add( basement );
		contentPane.add( hall );
		contentPane.add( conservatory );
		contentPane.add( billiardRoom );
		contentPane.add( library );
		contentPane.add( study );
	}
	
	/**
	 * <b>Purpose</b>: Draw game board components on the screen <br><br>
	 * <b>Precondition</b>: There is a 'damaged' version of the game display on the user's screen <br>
	 * <b>Postcondition</b>: There is an updated / current version of the game display on the user's screen <br>
	 * 
	 * <b>Pseudo-Code</b>: <br>
	 * BEGIN <br>
	 * - add the {@link BoardModel} cell objects into the frame
	 * - validate all frame's sub components <br>
	 * - pack the {@link GUI} object's {@link JFrame} instance <br>
	 * - set the {@link GUI} object's {@link JFrame} instance's visibility to {@code true} <br> 
	 * END <br><br> 
	 * 
	 * <br>
	 * <em>CREATED: 13 DEC 2008 Michael Philippone</em> <br>
	 * <em>LAST MODIFIED: 16 DEC 2008 Michael Philippone</em> <br>
	 */
	public void draw() {
		
		int x = this.client.getBoardModelReference().X_max;
		int y = this.client.getBoardModelReference().Y_max;

		/* now add each gameboard cell into the boardPanel */
		for(int i=0; i < x ; i++) {
			for (int j=0; j < y ; j++) {
				this.client.getBoardModelReference().getBoardArray()[i][j].setSize(d_cellSize);
				this.client.getBoardModelReference().getBoardArray()[i][j].setEnabled(true);
				this.client.getBoardModelReference().getBoardArray()[i][j].setVisible(true);
				this.client.getBoardModelReference().getBoardArray()[i][j].validate();
				this.boardPanel.add(
					this.client.getBoardModelReference().getBoardArray()[i][j]	
					);
			}
		}
		
		Dimension d_cardSize = new Dimension(75,75);
		
		/* the total number of game cards is equal to the 
		 * the sum of rooms, weapons, and game pieces: */
		int numCards = 
			Utilities.pieceNames.length + 
			Utilities.roomNames.length +
			Utilities.weaponNames.length;
		
		/* now add each card into the card panel */
		for(int i=0; i < numCards ; i++) {
			this.client.getBoardModelReference().getCardArray()[i].setSize(d_cardSize);
			this.client.getBoardModelReference().getCardArray()[i].setEnabled(true);
			this.client.getBoardModelReference().getCardArray()[i].setVisible(true);
			this.client.getBoardModelReference().getCardArray()[i].validate();
			this.cardsPanel.add(
				this.client.getBoardModelReference().getCardArray()[i]	
				);
		}
		
		/* prep Components */
		buttonPanel.validate();
		buttonPanel.setEnabled(true);
		
		boardPanel.validate();
		boardPanel.setEnabled(true);
		
		messagePanel.validate();
		messagePanel.setEnabled(true);
		
		cardsPanel.validate();
		cardsPanel.setEnabled(true);
		
		helpPanel.validate();
		helpPanel.setEnabled(true);
		
		/* NOW add all the components: 
		 * And position them */
		
		contentPane.add(helpPanel);
		helpPanel.setLocation( 70 , 25 );
		
		contentPane.add(buttonPanel);
		buttonPanel.setLocation( 685 , 3 );
		
		contentPane.add(boardPanel);
		boardPanel.setLocation( 3 , 3 );
		
		contentPane.add(messagePanel);
		messagePanel.setLocation( 3 , 635 );
		
		contentPane.add(cardsPanel);
		cardsPanel.setLocation( 685 , 200 );
		
		/* prep contentPane: */
		this.contentPane.setEnabled(true);
		this.contentPane.validate();
		this.contentPane.setVisible(true);
		
		/* prep Frame itself */
		this.setEnabled(true);
		this.validate();
		this.setVisible(true);
	}
	
	/**
	 * <b>Purpose</b>: Alert the user with some interactive message <br>
	 * <b>Preconditions</b>: there is an instance of a {@link GUI} object <br>
	 * <b>Postconditions</b>: the message parameter has been displayed to the user <br>
	 * 
	 * <br>
	 * <em>CREATED: 13 DEC 2008 Michael Philippone</em> <br>
	 * <em>LAST MODIFIED: 14 DEC 2008 Michael Philippone</em> <br>
	 * 
	 * @param message the message to display to the user 
	 */
	public void inform(String message) { 
		msgLbl.setText("     " + message);
	}
	
	/**
	 * <b>Purpose</b>: execute necessary steps for an accusation <br>
	 * <b>Preconditions</b>: The accuse button has been clicked <br>
	 * <b>Postconditions</b>: There has been an accusation sent to the server <br>
	 * 
	 * <br>
	 * <em>CREATED: 13 DEC 2008 Michael Philippone</em> <br>
	 * <em>LAST MODIFIED: 15 DEC 2008 Michael Philippone</em> <br>
	 */
	private void accuseBtnClick() {  
		inform("Accuse Clicked!");
		String character = JOptionPane.showInputDialog("Please enter a Character to accuse");
		String weapon = JOptionPane.showInputDialog("Please enter a weapon to imply in the accusation");
		String room = JOptionPane.showInputDialog("Please enter a room to imply in the accusation");
		client.getClientSocketReference().send("accuse " + character + " " + weapon + " " + room);
	}
	
	/**
	 * <b>Purpose</b>: execute necessary steps for a guess <br>
	 * <b>Preconditions</b>: The guess button has been clicked <br>
	 * <b>Postconditions</b>: There has been a guess sent to the server <br>
	 * 
	 * <br>
	 * <em>CREATED: 13 DEC 2008 Michael Philippone</em> <br>
	 * <em>LAST MODIFIED: 15 DEC 2008 Michael Philippone</em> <br>
	 */
	private void guessBtnClick() { 
		inform("Guess Clicked!");
		String character = JOptionPane.showInputDialog("Please enter a Character to guess");
		String weapon = JOptionPane.showInputDialog("Please enter a weapon to imply in the guess");
		client.getClientSocketReference().send("guess " + character + " " + weapon);
	}
	
	/**
	 * <b>Purpose</b>: execute necessary steps for ending a player's turn <br>
	 * <b>Preconditions</b>: The endTurn button has been clicked <br>
	 * <b>Postconditions</b>: server has been notified of the endturn request <br>
	 * 
	 * <br>
	 * <em>CREATED: 13 DEC 2008 Michael Philippone</em> <br>
	 * <em>LAST MODIFIED: 15 DEC 2008 Michael Philippone</em> <br>
	 */
	private void endTurnBtnClick() { 
		inform("Your turn is over for now.");
		client.getClientSocketReference().send("endturn");
	}
	
	/**
	 * <b>Purpose</b>: execute necessary steps for ending a player's turn <br>
	 * <b>Preconditions</b>: The endTurn button has been clicked <br>
	 * <b>Postconditions</b>: server has been notified of the endturn request <br>
	 * 
	 * <br>
	 * <em>CREATED: 16 DEC 2008 Michael Philippone</em> <br>
	 * <em>LAST MODIFIED: 16 DEC 2008 Michael Philippone</em> <br>
	 */
	private void helpBtnClick() { 
		/* Only listen to the button if the panel is not already shown: */
		if( !helpPanel.isVisible() ) {
			helpBtn.setVisible(false);
			helpPanel.setVisible(true);
			
			/* hide all the other panels */
			cardsPanel.setVisible(false);
			messagePanel.setVisible(false);
			boardPanel.setVisible(false);
			buttonPanel.setVisible(false);
			
			draw();
		}
	}
	
	/**
	 * <b>Purpose</b>: Handle window close event <br>
	 * <b>Preconditions</b>: the user has effected a windowClose event <br>
	 * <b>Postconditions</b>: the system goes into shutdown <br>
	 * 
	 * <b>Pseudo-Code</b>: <br>
	 * BEGIN <br>
	 * - call {@link Client}'s {@code systemShutdown()} method with {@link Utilities}.{@link e_ExitCode}.{@code clientGameOver} status flag<br>
	 * END <br><br>
	 * 
	 * <br>
	 * <em>CREATED: 13 DEC 2008 Michael Philippone</em> <br>
	 * <em>LAST MODIFIED: 13 DEC 2008 Michael Philippone</em> <br>
	 */
	public void windowClosed(WindowEvent e) { this.client.systemShutdown(Utilities.e_ExitCode.clientGameOver); }
	
	
	/** This method is not used, however it must be "implemented" to satisfy WindowListener usage */
	public void windowDeiconified(WindowEvent e) { /* NOT USED */ }
	/** This method is not used, however it must be "implemented" to satisfy WindowListener usage */
	public void windowActivated(WindowEvent e) { /* NOT USED */ }
	/** This method is not used, however it must be "implemented" to satisfy WindowListener usage */
	public void windowClosing(WindowEvent e) { /* NOT USED */ }
	/** This method is not used, however it must be "implemented" to satisfy WindowListener usage */
	public void windowDeactivated(WindowEvent e) { /* NOT USED */ }
	/** This method is not used, however it must be "implemented" to satisfy WindowListener usage */
	public void windowIconified(WindowEvent e) { /* NOT USED */ }
	/** This method is not used, however it must be "implemented" to satisfy WindowListener usage */
	public void windowOpened(WindowEvent e) { /* NOT USED */ }
	/** This method is not used, however it must be "implemented" to satisfy WindowListener usage */
	
	
	/* TEMPORARY: */
	public static void main(String[] args) { Client.main(args); }
}
