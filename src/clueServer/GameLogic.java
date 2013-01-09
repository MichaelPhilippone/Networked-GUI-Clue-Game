package clueServer;
import java.util.*;

import utils.Utilities;

/**
 * <b>Class semantics and roles</b>: <br>
 * The GameLogic does the high end computations of the more complex problems.
 * It is responsible for moving pieces, making guess, and making accusations
 * while being the link between the GameBoard, the Cards, and the Dice objects.
 * <br><br>
 * <b>Information maintenance</b>: <br>
 * <b>Creation</b>: GameLogic is created at start up <br>
 * <b>Deletion</b>: GameLogic is deleted when the game has finished 
 * or otherwise been terminated.
 *
 * @author Matt SeGall (Class Skeletons & Code)
 */
public class GameLogic {
	/**
	 * Private Class: position <br>
	 * <b>Purpose</b>: creates an object to act as a struct for holding three properties
	 * that will need to be used together throughout GameLogic. <br>
	 * <b>Methods</b>: none
	 */
	private class position{
		//Public Variables
		/** an X coordinate */
		public int X;
		
		/** a Y coordinate */
		public int Y;
		
		/** keep track of steps taken for rolling/moving */
		public int Step;

		/**
		 * Constructor: takes an X coordinate, Y coordinate and a step count
		 * and initializes the variables.
		 * @param x
		 * @param y
		 * @param step
		 */
		public position(int x, int y, int step){
			X = x;
			Y = y;
			Step = step;
		}//end constructor
	}//end class
	
	/**
	 * Private Class: position <br>
	 * <b>Purpose</b>: creates an object to act as a struct for holding three properties
	 * that will need to be used together throughout GameLogic. <br>
	 * <b>Methods</b>: none
	 */
	public class GuessReturn{
		//Public Variables
		/** an integer representation of the person who disproved the player */
		public int Disprover = -1;
		
		/** a String that represents a card that can be shown to disprove */
		public String cardName1 = null;
		
		/** a String that represents a card that can be shown to disprove */
		public String cardName2 = null;

		/** a String that represents a card that can be shown to disprove */
		public String cardName3 = null;
		
		/** a boolean for whether an error occured or not */
		public boolean error = false;

		/**
		 * Constructor: takes an X coordinate, Y coordinate and a step count
		 * and initializes the variables.
		 * @param disprover
		 * @param card1
		 * @param card2
		 * @param card3
		 * @param err
		 */
		public GuessReturn(int disprover, String card1, String card2, String card3, boolean err){
			Disprover = disprover;
			cardName1 = card1;
			cardName2 = card2;
			cardName3 = card3;			
			error = err;
		}//end constructor
		
		public GuessReturn(){/*default constructor*/}
	}//end class
	
	//Global Variables
	/** the current player */
	public int currentPlayer = 0;//keeps track of whose turn it is
	
	/** boolean as to whether a player has made a guess this turn */
	public boolean hasGuessed = false;//keeps track of whose turn it is
	
	/** a game board */
	public GameBoard Board;

	/** a die */
	private Dice Die;

	/** collection of cards */
	public Cards Card;
	
	/**
	 * Constructor: initializes the objects that will be used
	 * Parameters: none
	 */
	public GameLogic(){
		Board = new GameBoard();//makes GameBoard
		Die = new Dice();//makes Dice
		Card = new Cards();//makes Cards
	}//end constructor
	
	/*
	 * METHODS:
	 * 
	 * guess: determines the holders of guessed cards
	 * accuse: determines where an accusation was correct
	 * move: highlights the board with possible moves for a player
	 * nextPlayer: updates which player is active
	 */
	
	/**
	 * Public Method: guess <br>
	 * <b>Purpose</b>: determines who holds the card(s) a player is guessing about
	 * and moves the guessed player to the guessed location
	 * <br><br>
	 * 
	 * <b>Precondition</b>: it is a players turn and they 
	 * have chosen to make a guess <br>
	 * <b>Postcondition</b>: guessed player is relocated 
	 * to guessed location, card holders are determined
	 * 
	 * @param player the guessed person 
	 * @param weapon the guessed weapon
	 * @return GuessReturn  this is a player that can disprove the guess and what card(s) they did it with 
	 */
	public GuessReturn guess(String player, String weapon){
		//local variables
		int targetPlayer = 0;//integer version of the String player
		int x = 0;//an x coordinate
		int y = 0;//a y coordinate
		int owner1, owner2, owner3;//card owners
		
		// the coordinates for the currentPlayer's piece:
		int gottenX,gottenY;
		String location = ""; // where is the currently guessing player?
		
		//convert the String player to the integer player
		for(int i=0;i<Utilities.pieceNames.length;i++){
			if(player.compareTo(Utilities.pieceNames[i]) == 0)
				targetPlayer = i;
		}
		
		///////////////
		// get reference on the room the current, guessing, player is in
		///////////////
		
		// assign the current player's coordinates:
		gottenX = Board.Piece.getX(currentPlayer);
		gottenY = Board.Piece.getY(currentPlayer);
		
		if(gottenY == 0) {  
			if(gottenX < 6)
				location = Utilities.roomNames[0]; // in kitchen
			else if (gottenX >= 18)
				location = Utilities.roomNames[2]; // in observatory
		}
		else if( gottenY == 3 ) 
			location = Utilities.roomNames[1]; // in ball room
		else if(gottenY == 12)
			location = Utilities.roomNames[3]; // in dining room
		else if(gottenY == 7)
			location = Utilities.roomNames[4]; // in dining room		
		else if(gottenY == 15)
			location = Utilities.roomNames[5]; // in library
		else if(gottenY == 21)
			location = Utilities.roomNames[6]; // in lounge
		else if(gottenY == 20)
			location = Utilities.roomNames[7]; // in hall
		else if(gottenY == 22)
			location = Utilities.roomNames[8]; // in study
		
		//determine where to move the guessed player
		if(location.compareTo(Utilities.roomNames[0]) == 0){//somewhere in kitchen
			x = 0;//player position in the room, x coordinate
			y = 0;//player position in the room, y coordinate
			while(Board.Piece.isOccupied(x,y) != 0){//if someone is already there
				x++;//move over
			}
		}
		else if(location.compareTo(Utilities.roomNames[1]) == 0){//somewhere in the ball room
			x = 9;//player position in the room, x coordinate
			y = 3;//player position in the room, y coordinate
			while(Board.Piece.isOccupied(x,y) != 0){//if someone is already there
				x++;//move over
			}
		}
		else if(location.compareTo(Utilities.roomNames[2]) == 0){//somewhere in the observatory
			x = 18;//player position in the room, x coordinate
			y = 0;//player position in the room, y coordinate
			while(Board.Piece.isOccupied(x,y) != 0){//if someone is already there
				x++;//move over
			}
		}
		else if(location.compareTo(Utilities.roomNames[3]) == 0){//somewhere in the dining room
			x = 0;//player position in the room, x coordinate
			y = 12;//player position in the room, y coordinate
			while(Board.Piece.isOccupied(x,y) != 0){//if someone is already there
				x++;//move over
			}
		}
		else if(location.compareTo(Utilities.roomNames[4]) == 0){//somewhere in the billiard room
			x = 18;//player position in the room, x coordinate
			y = 7;//player position in the room, y coordinate
			while(Board.Piece.isOccupied(x,y) != 0){//if someone is already there
				x++;//move over
			}
		}
		else if(location.compareTo(Utilities.roomNames[5]) == 0){//somewhere in the library
			x = 18;//player position in the room, x coordinate
			y = 15;//player position in the room, y coordinate
			while(Board.Piece.isOccupied(x,y) != 0){//if someone is already there
				x++;//move over
			}
		}
		else if(location.compareTo(Utilities.roomNames[6]) == 0){//somewhere in the lounge
			x = 0;//player position in the room, x coordinate
			y = 21;//player position in the room, y coordinate
			while(Board.Piece.isOccupied(x,y) != 0){//if someone is already there
				x++;//move over
			}
		}
		else if(location.compareTo(Utilities.roomNames[7]) == 0){//somewhere in the hall
			x = 9;//player position in the room, x coordinate
			y = 20;//player position in the room, y coordinate
			while(Board.Piece.isOccupied(x,y) != 0){//if someone is already there
				x++;//move over
			}
		}
		else if(location.compareTo(Utilities.roomNames[8]) == 0){//somewhere in the study
			x = 17;//player position in the room, x coordinate
			y = 22;//player position in the room, x coordinate
			while(Board.Piece.isOccupied(x,y) != 0){//if someone is already there
				x++;//move over
			}
		}
		//else
		//	return new GuessReturn(-1,-1,-1,false,true);//error on room name
		
		//relocate the player to the new location
		Board.Piece.relocate(targetPlayer, x, y);
		
		//update the board with new player location
		Board.serialize();
		
		//get the owner of the cards
		owner1 = Card.getOwner(player);
		owner2 = Card.getOwner(weapon);
		owner3 = Card.getOwner(location);
		
		//prepare the results of the guess and owners
	/*	if(owner1 == 7 || owner2 == 7 || owner3 == 7)//if name is invalid
			return new GuessReturn(-1,-1,-1,false,true);//location and player would have already returned errors
		else if(owner1 != 6 || owner2 != 6 || owner3 != 6)//if they are not the correct cards
			return new GuessReturn(owner1,owner2,owner3,true,false);
		else//they are the correct cards, no owners
			return new GuessReturn(-1,-1,-1,true,false);*/
		System.out.println("GAMELOGIC: piece relocated, card owners found, beginning determine which to show");
		int own1, own2, own3;//who holds the card
		int match2 = -1, match3 = -1, matchodd = -1;
		if(owner1 != 6 || owner2 != 6 || owner3 != 6){//someone can disprove the guess
			System.out.println("GAMELOGIC: some one can disprove guess, either " + owner1 + ", " + owner2 + ", or " + owner3);
			own1 = owner1;
			own2 = owner2;
			own3 = owner3;
			//correct for cards that cannot be disproved
			if(owner1 == 6)
				owner1 = 12;
			if(owner2 == 6)
				owner2 = 12;
			if(owner3 == 6)
				owner3 = 12;
			
			//order the player numbers
			if(owner1 > owner2){
				int temp = owner2;
				owner2 = owner1;
				owner1 = temp;
			}
			if(owner2 > owner3){
				int temp = owner3;
				owner3 = owner2;
				owner2 = temp;
			}
			if(owner1 > owner2){
				int temp = owner2;
				owner2 = owner1;
				owner1 = temp;
			}
			
			System.out.println("GAMELOGIC: owner order is: " + owner1 + " " + owner2 + " " + owner3);
			//see if any match
			if(owner1 == owner2 && owner2 == owner3){
				match3 = owner1;
			}
			else if(owner1 == owner2){
				match2 = owner1;
				matchodd = owner3;
			}
			else if(owner2 == owner3){
				match2 = owner2;
				matchodd = owner1;
			}
			//set play order
			if(match3 != -1){
				System.out.println("GAMELOGIC: all belong to same person");
				return new GuessReturn(match3, player, weapon, location, false);
			}
			else if(match2 != -1){
				System.out.println("GAMELOGIC: Two belong to one player");
				boolean changed1 = false, changed2 = false;
				if(match2 <= currentPlayer){
					match2 += 5;
					changed1 = true;
				}
				if(matchodd <= currentPlayer){
					matchodd += 5;
					changed2 = true;
				}
				if(match2 < matchodd){
					if(changed1)
						match2 -= 5;
					if(match2 == own1 && match2 == own2)
						return new GuessReturn(match2, player, weapon, null, false);
					else if(match2 == own1 && match2 == own3)
						return new GuessReturn(match2, player, location, null, false);
					else
						return new GuessReturn(match2, weapon, location, null, false);
				}
				else{
					if(changed2)
						matchodd -= 5;
					if(matchodd == own1)
						return new GuessReturn(matchodd, player, null, null, false);
					else if(matchodd == own2)
						return new GuessReturn(matchodd, weapon, null, null, false);
					else
						return new GuessReturn(matchodd, location, null, null, false);
				}
			}
			else{
				System.out.println("GAMELOGIC: first person ordered will disprove them");
				if(owner1 == own1)
					return new GuessReturn(owner1, player, null, null, false);
				else if(owner1 == own2)
					return new GuessReturn(owner1, weapon, null, null, false);
				else
					return new GuessReturn(owner1, location, null, null, false);
			}
		}
		else
			return new GuessReturn(-1, null, null, null, false);
	}
	
	/**
	 * Public Method: accuse <br>
	 * <b>Purpose</b>: determines whether an accusation is correct
	 * <br><br>
	 * <b>Precondition</b>: it is a players turn and they have 
	 * chosen to make an accusation <br>
	 * <b>Postcondition</b>: the accusation has been determined
	 * 
	 * @param player the accused person
	 * @param weapon the accused weapon
	 * @param location the accused location
	 * @return boolean
	 */
	public boolean accuse(String player, String weapon, String location){
		//local variables
		int owner1, owner2, owner3;//card owners
		
		//determine card holders
		owner1 = Card.getOwner(player);
		owner2 = Card.getOwner(weapon);
		owner3 = Card.getOwner(location);
		
		//return results
		if(owner1 == 6 && owner2 == 6 && owner3 ==6)//if all are correct cards
			return true;
		return false;//invalid name, or incorrect accusation
	}
	
	/**
	 * Public Method: move <br>
	 * <b>Purpose</b>: determines all possible end points for the user and highlights them
	 * so that they can easily pick a new location
	 * <br><br>
	 * <b>Precondition</b>: it is a players turn and they are allowed to move <br>
	 * <b>Postcondition</b>: board is highlighted with end points for the user <br>
	 * Parameters: none <br>
	 * Return: integer roll value
	 */
	public int move(){
		//local variables
		int roll = Die.Roll();//how far the player may move
//		int index = 0;//array index for marked cells (can't traverse already traversed cells)
		
		position current = new position(Board.Piece.getX(currentPlayer), Board.Piece.getY(currentPlayer), 0);//where the player currently is
		position push;//initialize a position for use in the algorithms
		Queue<position> Queue = new LinkedList<position>();//a queue that will keep cells to be traversed and determine other cells from
		//position marked[] = new position[5000];//array that keeps the positions of marked cells (larger than need be for safety)
		int roomNumber = 0;//for finding tunnels and moving to other rooms with tunnels
		boolean skip = false, tunnel = false;//skip is for marked cells that were already calculated, tunnel for if a room contains a tunnel
		int doors[] = new int[8];//will contain the coordinates of doorways, for leaving rooms
		
		System.out.println("GAMELOGIC: dice roll value = " + roll);
		
		//if the current location is a room
		if(Board.Room.isAroom(current.X, current.Y)){
			for(int i = 0; i < 6; i++)//determine whether they are in the kitchen
				if(current.X == i && current.Y == 0){
					tunnel = true; //there is a tunnel
					roomNumber = 1;//kitchen
					break;
				}
			for(int i = 9; i < 14; i++)//determine whether they are in the ball room
				if(current.X == i && current.Y == 3){
					roomNumber = 2;//ball room
					break;
				}
			for(int i = 18; i < 24; i++)//determine whether they are in the observatory
				if(current.X == i && current.Y == 0){
					tunnel = true;//there is a tunnel
					roomNumber = 3;//observatory
					break;
				}
			for(int i = 0; i < 6; i++)//determine whether they are in the dining room
				if(current.X == i && current.Y == 12){
					roomNumber = 4;//dining room
					break;
				}
			for(int i = 18; i < 24; i++)//determine whether they are in the billiard room
				if(current.X == i && current.Y == 7){
					roomNumber = 5;//billiard room
					break;
				}
			for(int i = 18; i < 24; i++)//determine whether they are in the library
				if(current.X == i && current.Y == 15){
					roomNumber = 6;//library
					break;
				}
			for(int i = 0; i < 6; i++)//determine whether they are in the lounge
				if(current.X == i && current.Y == 21){
					tunnel = true;//there is a tunnel
					roomNumber = 7;//lounge
					break;
				}
			for(int i = 9; i < 15; i++)//determine whether they are in the hall
				if(current.X == i && current.Y == 20){
					roomNumber = 8;//hall
					break;
				}
			for(int i = 17; i < 23; i++)//determine whether they are in the study
				if(current.X == i && current.Y == 22){
					tunnel = true;//there is a tunnel
					roomNumber = 9;//study
					break;
				}

			//if they are in a room with a tunnel, highlight the connecting room
			if(tunnel && roomNumber == 1){//in the kitchen
				//go to the study
				int x = 17;//player position in the room, x coordinate
				int y = 22;//player position in the room, y coordinate
				while(Board.Piece.isOccupied(x,y) != 0){//if someone is already there
					x++;//move over
				}
				Board.Display[x][y] = Utilities.BoardColors[11];//highlight it
			}
			else if(tunnel && roomNumber == 3){//in the observatory
				//go to the lounge
				int x = 0;//player position in the room, x coordinate
				int y = 21;//player position in the room, y coordinate
				while(Board.Piece.isOccupied(x,y) != 0){//if someone is already there
					x++;//move over
				}
				Board.Display[x][y] = Utilities.BoardColors[11];//highlight it
			}
			else if(tunnel && roomNumber == 7){//in the lounge
				//got to the observatory
				int x = 18;//player position in the room, x coordinate
				int y = 0;//player position in the room, y coordinate
				while(Board.Piece.isOccupied(x,y) != 0){//if someone is already there
					x++;//move over
				}
				Board.Display[x][y] = Utilities.BoardColors[11];//highlight it
			}
			else if(tunnel && roomNumber == 9){//in the study
				//go to the kitchen
				int x = 0;//player position in the room, x coordinate
				int y = 0;//player position in the room, y coordinate
				while(Board.Piece.isOccupied(x,y) != 0){//if someone is already there
					x++;//move over
				}
				Board.Display[x][y] = Utilities.BoardColors[11];//highlight it
			}
			
			//doorways out of room, rather than a tunnel if one existed
			//get the position of the doors for the current room
			doors = Board.getDoorways(roomNumber);
			
			//add the doors to the queue
			for(int i = 0; i < doors.length; i = i + 2){//traverse the door ways
				if(Board.Piece.isOccupied(doors[i],doors[i+1]) == 0){//if it is not occupied
					push = new position(doors[i],doors[i+1],1);//create a position with the first step out of the room
//					marked[index] = push;//mark the door way
//					index++;//marked cells index increased
					Board.Display[doors[i]][doors[i+1]] = Utilities.BoardColors[11];
					Queue.add(push);//add door way to queue
				}//ignore the cell if it is occupied
			}
			
		}//end if it is a room they started in
		else{//they weren't in a room, so add the current cell
			Queue.add(current);//add cell to queue
//			marked[index] = current;//mark it
		}
		
		//while there are cells to calculate and moves to use and cells to highlight
		while(!Queue.isEmpty()){
			//local variable
			position workOn = Queue.remove();//get a cell to work on
			
			skip = false;//reset the marked status to false
//			for(int i = 0; i < index; i++)//traverse the marked cells
//				if(marked[i].X == workOn.X + 1 && marked[i].Y == workOn.Y)//if the right cell is marked
//					skip = true;//skip it
			
			//check right cell to see if it is available
			//if it is marked, within the board, not occupied, not a null space, and haven't exceed the roll
			if(!skip && workOn.X + 1 < Board.WIDTH && Board.Piece.isOccupied(workOn.X + 1, workOn.Y) == 0 && !Board.Room.isAnull(workOn.X + 1, workOn.Y) && workOn.Step < roll)
				if(Board.isAdoorway(workOn.X, workOn.Y) && Board.Room.isAroom(workOn.X + 1, workOn.Y) && !Board.Room.isAroom(current.X, current.Y)){//if in a door way and the right is a room
					push = new position(workOn.X + 1, workOn.Y, workOn.Step + 1);//make mark
//					marked[index] = push;//mark it
//					index++;//marked cell index increase
					
					//local variables
					int x, y;//coordinates for spot in room
					
					if(workOn.X + 1 == 8 && workOn.Y == 5){//if entering ball room
						x = 9;//the start x coordinate for players locations in the room
						y = 3;//the y coordinate for players in the room
						
						//find spot in ball room
						while(Board.Piece.isOccupied(x,y) != 0){//if someone is already there
							x++;//move over
						}
						Board.Display[x][y] = Utilities.BoardColors[11];//highlight the spot
					}
					else if(workOn.X + 1 == 18 && workOn.Y == 8){
						x = 18;//the start x coordinate for players locations in the room
						y = 7;//the y coordinate for players in the room
						
						//find spot in billiard room
						while(Board.Piece.isOccupied(x,y) != 0){//if someone is already there
							x++;//move over
						}
						Board.Display[x][y] = Utilities.BoardColors[11];//highlight the spot
					}
					else if(workOn.X + 1 == 17 && workOn.Y == 16){
						x = 18;//the start x coordinate for players locations in the room
						y = 15;//the y coordinate for players in the room
						
						//find spot in library
						while(Board.Piece.isOccupied(x,y) != 0){//if someone is already there
							x++;//move over
						}
						Board.Display[x][y] = Utilities.BoardColors[11];//highlight the spot
					}
				}
				else if(!Board.Room.isAroom(workOn.X + 1, workOn.Y)){//can't go in rooms while not at a door 
					push = new position(workOn.X + 1, workOn.Y, workOn.Step + 1);//create cell to move into
//					marked[index] = push;//mark it
//					index++;//marked cells index increase
					Board.Display[workOn.X + 1][workOn.Y] = Utilities.BoardColors[11];//highlight it
					Queue.add(push);//add to the queue				
				}//end if on right cell available	
			
//			skip = false;//reset marked token
//			for(int i = 0; i < index; i++)//traverse marked cells
//				if(marked[i].X == workOn.X - 1 && marked[i].Y == workOn.Y)//if cell is marked
//					skip = true;//skip it
			
			//check left cell to see if it is available
			if(!skip && workOn.X - 1 >= 0 && Board.Piece.isOccupied(workOn.X - 1, workOn.Y) == 0 && !Board.Room.isAnull(workOn.X - 1, workOn.Y) && workOn.Step < roll)
				if(Board.isAdoorway(workOn.X, workOn.Y) && Board.Room.isAroom(workOn.X - 1, workOn.Y) && !Board.Room.isAroom(current.X, current.Y)){//if in a door way and the left is a room
					push = new position(workOn.X - 1, workOn.Y, workOn.Step + 1);//make mark
//					marked[index] = push;//mark it
//					index++;//marked cell index increase
					
					//local variables
					int x, y;//coordinates for spot in room
					
					if(workOn.X - 1 == 15 && workOn.Y == 5){//if entering ball room
						x = 9;//the start x coordinate for players locations in the room
						y = 3;//the y coordinate for players in the room
						
						//find spot in ball room
						while(Board.Piece.isOccupied(x,y) != 0){//if someone is already there
							x++;//move over
						}
						Board.Display[x][y] = Utilities.BoardColors[11];//highlight the spot
					}
					else if(workOn.X - 1 == 7 && workOn.Y == 12){
						x = 0;//the start x coordinate for players locations in the room
						y = 12;//the y coordinate for players in the room
						
						//find spot in dining room
						while(Board.Piece.isOccupied(x,y) != 0){//if someone is already there
							x++;//move over
						}
						Board.Display[x][y] = Utilities.BoardColors[11];//highlight the spot
					}
					else if(workOn.X - 1 == 14 && workOn.Y == 20){
						x = 9;//the start x coordinate for players locations in the room
						y = 20;//the y coordinate for players in the room
						
						//find spot in hall
						while(Board.Piece.isOccupied(x,y) != 0){//if someone is already there
							x++;//move over
						}
						Board.Display[x][y] = Utilities.BoardColors[11];//highlight the spot
					}
				}
				else if(!Board.Room.isAroom(workOn.X - 1, workOn.Y)){//can't go in rooms while not at a door
					push = new position(workOn.X - 1, workOn.Y, workOn.Step + 1);//create cell to move into
//					marked[index] = push;//mark it
//					index++;//marked cells index increase
					Board.Display[workOn.X - 1][workOn.Y] = Utilities.BoardColors[11];//highlight it
					Queue.add(push);//add to the queue				
				}//end if on left cell available
			
//			skip = false;//reset marked token
//			for(int i = 0; i < index; i++)//traverse marked cells
//				if(marked[i].X == workOn.X && marked[i].Y - 1 == workOn.Y)//if cell is marked
//					skip = true;
			
			//check top cell to see if it is available
			if(!skip && workOn.Y - 1 >= 0 && Board.Piece.isOccupied(workOn.X, workOn.Y - 1) == 0 && !Board.Room.isAnull(workOn.X, workOn.Y - 1) && workOn.Step < roll)
				if(Board.isAdoorway(workOn.X, workOn.Y) && Board.Room.isAroom(workOn.X, workOn.Y - 1) && !Board.Room.isAroom(current.X, current.Y)){//if in a door way and the top is a room
					push = new position(workOn.X, workOn.Y - 1, workOn.Step + 1);//make mark
//					marked[index] = push;//mark it
//					index++;//marked cell index increase
					
					//local variables
					int x, y;//coordinates for spot in room
					
					if(workOn.X == 9 && workOn.Y - 1 == 7 || workOn.X == 14 && workOn.Y - 1 == 7){//if entering ball room
						x = 9;//the start x coordinate for players locations in the room
						y = 3;//the y coordinate for players in the room
						
						//find spot in ball room
						while(Board.Piece.isOccupied(x,y) != 0){//if someone is already there
							x++;//move over
						}
						Board.Display[x][y] = Utilities.BoardColors[11];//highlight the spot
					}
					else if(workOn.X == 6 && workOn.Y - 1 == 15){
						x = 0;//the start x coordinate for players locations in the room
						y = 12;//the y coordinate for players in the room
						
						//find spot in dining room
						while(Board.Piece.isOccupied(x,y) != 0){//if someone is already there
							x++;//move over
						}
						Board.Display[x][y] = Utilities.BoardColors[11];//highlight the spot
					}
					else if(workOn.X == 4 && workOn.Y - 1 == 6){
						x = 0;//the start x coordinate for players locations in the room
						y = 0;//the y coordinate for players in the room
						
						//find spot in kitchen
						while(Board.Piece.isOccupied(x,y) != 0){//if someone is already there
							x++;//move over
						}
						Board.Display[x][y] = Utilities.BoardColors[11];//highlight the spot
					}
					else if(workOn.X == 18 && workOn.Y - 1 == 3){
						x = 18;//the start x coordinate for players locations in the room
						y = 0;//the y coordinate for players in the room
						
						//find spot in observatory
						while(Board.Piece.isOccupied(x,y) != 0){//if someone is already there
							x++;//move over
						}
						Board.Display[x][y] = Utilities.BoardColors[11];//highlight the spot
					}
					else if(workOn.X == 22 && workOn.Y - 1 == 12){
						x = 18;//the start x coordinate for players locations in the room
						y = 7;//the y coordinate for players in the room
						
						//find spot in billiard room
						while(Board.Piece.isOccupied(x,y) != 0){//if someone is already there
							x++;//move over
						}
						Board.Display[x][y] = Utilities.BoardColors[11];//highlight the spot
					}
				}
				else if(!Board.Room.isAroom(workOn.X, workOn.Y - 1)){//can't go in rooms while not at a door
					push = new position(workOn.X, workOn.Y - 1, workOn.Step + 1);//create cell to move into
//					marked[index] = push;//mark it
//					index++;//marked cells index increase
					Board.Display[workOn.X][workOn.Y - 1] = Utilities.BoardColors[11];//highlight it
					Queue.add(push);//add to the queue		
				}//end if on top cell available
			
//			skip = false;//reset marked token
//			for(int i = 0; i < index; i++)//traverse marked cells
//				if(marked[i].X == workOn.X && marked[i].Y + 1 == workOn.Y)
//					skip = true;//if cell is marked
			
			//check bottom cell to see if it is available
			if(!skip && workOn.Y + 1 < Board.HEIGHT && Board.Piece.isOccupied(workOn.X, workOn.Y + 1) == 0 && !Board.Room.isAnull(workOn.X, workOn.Y + 1) && workOn.Step < roll)
				if(Board.isAdoorway(workOn.X, workOn.Y) && Board.Room.isAroom(workOn.X, workOn.Y + 1) && !Board.Room.isAroom(current.X, current.Y)){//if in a door way and the bottom is a room
					push = new position(workOn.X, workOn.Y + 1, workOn.Step + 1);//make mark
//					marked[index] = push;//mark it
//					index++;//marked cell index increase
					
					//local variables
					int x, y;//coordinates for spot in room
					
					if(workOn.X == 11 && workOn.Y + 1 == 19 || workOn.X == 12 && workOn.Y + 1 == 19){//if entering hall
						x = 9;//the start x coordinate for players locations in the room
						y = 20;//the y coordinate for players in the room
						
						//find spot in ball room
						while(Board.Piece.isOccupied(x,y) != 0){//if someone is already there
							x++;//move over
						}
						Board.Display[x][y] = Utilities.BoardColors[11];//highlight the spot
					}
					else if(workOn.X == 12 && workOn.Y + 1 == 12){
						x = 18;//the start x coordinate for players locations in the room
						y = 15;//the y coordinate for players in the room
						
						//find spot in library
						while(Board.Piece.isOccupied(x,y) != 0){//if someone is already there
							x++;//move over
						}
						Board.Display[x][y] = Utilities.BoardColors[11];//highlight the spot
					}
					else if(workOn.X == 6 && workOn.Y + 1 == 19){
						x = 0;//the start x coordinate for players locations in the room
						y = 21;//the y coordinate for players in the room
						
						//find spot in lounge
						while(Board.Piece.isOccupied(x,y) != 0){//if someone is already there
							x++;//move over
						}
						Board.Display[x][y] = Utilities.BoardColors[11];//highlight the spot
					}
					else if(workOn.X == 17 && workOn.Y + 1 == 21){
						x = 17;//the start x coordinate for players locations in the room
						y = 22;//the y coordinate for players in the room
						
						//find spot in study
						while(Board.Piece.isOccupied(x,y) != 0){//if someone is already there
							x++;//move over
						}
						Board.Display[x][y] = Utilities.BoardColors[11];//highlight the spot
					}
				}
				else if(!Board.Room.isAroom(workOn.X, workOn.Y + 1)){//can't go in rooms while not at a door
					push = new position(workOn.X, workOn.Y + 1, workOn.Step + 1);//create cell to move into
//					marked[index] = push;//mark it
//					index++;//marked cells index increase
					Board.Display[workOn.X][workOn.Y + 1] = Utilities.BoardColors[11];//highlight it
					Queue.add(push);//add to the queue					
				}//end if on bottom cell available
		}//end while loop
		boolean err = false;
		if((Board.Display[4][7].compareTo(Utilities.BoardColors[11]) != 0 || (Board.Piece.getY(currentPlayer) == 7 && Board.Piece.getX(currentPlayer) == 4)) && Board.Piece.getY(currentPlayer) != 22)
				err = true;
		if(err && Board.Piece.isOccupied(0, 0) == 0)
			Board.Display[0][0] = Utilities.BoardColors[1];
		return roll;
	}//end method
	
	/**
	 * Public Method: nextPlayer <br>
	 * <b>Purpose</b>: changes the player to the next one in order 
	 * <br> <br>
	 * <b>Precondition</b>: players turn has ended and next player 
	 * is about to begin <br>
	 * <b>Postcondition</b>: player changed, may begin turn <br>
	 * <b>Parameters</b>: none<br>
	 * <b>Return</b>: none
	 */
	public void nextPlayer(){
		currentPlayer ++;//make the current player the next one
		if(currentPlayer == 6)//correct for for being over the number of players
			currentPlayer = 0;//if too large, it is now the first player's turn again
	}
}
