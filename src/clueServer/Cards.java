package clueServer;

import java.util.Random;

import utils.Utilities;

/**
 * <b>Class semantics and roles</b>: <br>
 * Cards is to model a deck of cards that is to be distributed out to players.
 * An equal number of cards will be held as the 'hidden cards'.  Cards will also
 * know who owns which cards after the cards have been dealt.
 * <br><br>
 * <b>Information maintenance</b>: <br>
 * <b>Creation</b>: GameLogic is created at start up <br>
 * <b>Deletion</b>: GameLogic is deleted when the game has finished 
 * or otherwise been terminated.
 *
 * @author Matt SeGall (Class Skeletons & Code)
 */
public class Cards {
	
	/**
	 * contains the cards held by which player 
	 * (the first index number, 7 is held cards)
	 */
	private String Deck[][] = new String[7][3];
	
	/**
	 * a random number generator used 
	 * to randomize the cards for dealing
	 */
	private Random generator = new Random();
	
	/**
	 * Constructor: deals the cards and remembers who gets what
	 * <br>
	 * <b>Parameters</b>: none
	 */
	public Cards(){
		//local variables
		/**  */
		int i = 0, j = 0, index = 0;//i is the player index, j is the card index (only 3 per player), index is for indexing the haves array
		int haves[] = new int[21];//keeps track of which cards have been dealt already
		boolean done = false;//loop control variable
		int card = generator.nextInt(21) + 1;//the current card to be dealt
		int secret[] = new int[3];
		
		//int secret1 = , secret2 = generator.nextInt(9) + 1, secret3 = generator.nextInt(6) + 1;
		secret[0] = generator.nextInt(6) + 1;
		secret[1] = generator.nextInt(9) + 1 + 6;
		secret[2] = generator.nextInt(6) + 1 + 6 + 9;
	
		//while all the cards have yet to be dealt
		while(!done){
			for(int k = 0; k < haves.length-3; k++)//traverse already dealt cards
				if(haves[k] == card || secret[k%3] == card)//if we have seen it
					break;//move on, stop looking
				else if(k == haves.length-4){//we haven't seen it (traversed the array)
					haves[index] = card;//add it to what we have seen
					index++;//increment index
				}
				if(index == haves.length-3)//if we have seen all the cards
					done = true;//finish dealing cards
				card = generator.nextInt(21) + 1;//get the next card to deal
			}
		haves[18] = secret[0];
		haves[19] = secret[1];
		haves[20] = secret[2];
		//translate integer card values to String card values and give to players
		for(int k = 0; k < haves.length; k++){//traverse the haves array
			switch(haves[k]){//switch from integer value to String value
				case 1:
					Deck[i][j] = Utilities.weaponNames[0]; // Wrench
					break;
				case 2:
					Deck[i][j] = Utilities.weaponNames[1]; // Pipe
					break;
				case 3:
					Deck[i][j] = Utilities.weaponNames[2]; // Revolver
					break;
				case 4:
					Deck[i][j] = Utilities.weaponNames[3]; // Rope
					break;
				case 5:
					Deck[i][j] = Utilities.weaponNames[4]; // Knife
					break;
				case 6:
					Deck[i][j] = Utilities.weaponNames[5]; // Candlestick
					break;
				case 7:
					Deck[i][j] = Utilities.roomNames[0]; // Kitchen
					break;
				case 8:
					Deck[i][j] = Utilities.roomNames[1]; // Ballroom
					break;
				case 9:
					Deck[i][j] = Utilities.roomNames[2]; // Observatory
					break;
				case 10:
					Deck[i][j] = Utilities.roomNames[3]; // Dining Room
					break;
				case 11:
					Deck[i][j] = Utilities.roomNames[4]; // Billiard Room
					break;
				case 12:
					Deck[i][j] = Utilities.roomNames[5]; // Library
					break;
				case 13:
					Deck[i][j] = Utilities.roomNames[6]; // Lounge
					break;
				case 14:
					Deck[i][j] = Utilities.roomNames[7]; // Hall
					break;
				case 15:
					Deck[i][j] = Utilities.roomNames[8]; // Study
					break;
				case 16:
					Deck[i][j] = Utilities.pieceNames[0]; // Miss Scarlet
					break;
				case 17:
					Deck[i][j] = Utilities.pieceNames[1]; // Professor Plum
					break;
				case 18:
					Deck[i][j] = Utilities.pieceNames[2]; // Mrs. Peacock
					break;
				case 19:
					Deck[i][j] = Utilities.pieceNames[3]; // Mr. Green
					break;
				case 20:
					Deck[i][j] = Utilities.pieceNames[4]; // Miss White
					break;
				case 21:
					Deck[i][j] = Utilities.pieceNames[5]; // Colonel Mustard
					break;
			}
			j++;//increase the card #
			if(j == 3){//if a player has 3 cards
				i++;//next player gets cards now
				j = 0;//that player has no cards
			}
		}//end for loop
	}//end constructor
	
	
	/*
	 * METHODS:
	 * 
	 * getOwner: find the holder of a card
	 */
	
	/**
	 * Public Method: getOwner <br>
	 * <b>Purpose</b>: determines who holds the card a player is asking about
	 * <br><br>
	 * <b>Precondition</b>: a guess or accusation has been made
	 * <br>
	 * <b>Postcondition</b>: the holder of the card is now known
	 * @param cardName
	 * @return int : player #, 6 if 'hidden', 8 if invalid
	 */
	public int getOwner(String cardName){
		for(int i = 0; i < 7; i++)//traverse through the players
			//if player has the card, as first card, second, or third
			if(Deck[i][0].compareTo(cardName) == 0 || Deck[i][1].compareTo(cardName) == 0 ||Deck[i][2].compareTo(cardName) == 0)
				return (i);//return that player's number (i is 1 less than the player number)
		return 8;//invalid, return a known code for invalid
	}
	
	/**
	 * Public Method: getDeck <br>
	 * <b>Purpose</b>: returns Deck 
	 * <br><br>
	 * @return Deck
	 */
	public String[][] getDeck() {
		return Deck;
	}
}
