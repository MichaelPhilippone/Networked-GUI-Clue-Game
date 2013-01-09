package clueServer;

import utils.Utilities;

/**
 * <b>Class semantics and roles</b>: <br>
 * The GameBoard keeps the current state of the game play's board, 
 * which includes player locations and the board.  It will have several
 * roles, which include updating the state, finding end positions for 
 * moves, making a presentable board for display, and relocating players.
 * <br><br>
 * <b>Information maintenance</b>: <br>
 * <b>Creation</b>: The GameBoard is created at startup with the players in pre-
 * determined positions and the board is drawn.<br>
 * <b>Delection</b>: The GameBoard is deleted when the game has finished or other-
 * wise been terminated.
 *
 * @author Matt SeGall (Class Skeletons & Code)
 */
public class GameBoard {
	/**
	 * Private Class: position <br>
	 * <b>Purpose</b>: creates an object to act as a struct for holding two properties
	 * that will need to be used together throughout Pieces.<br>
	 * <b>Methods</b>: none
	 */
	private class position{
		//local variables
		/** an x coordinate */
		public int X;
		
		/** a Y coordinate */
		public int Y;
		
		/**
		 * Constructor: takes an X coordinate, Y coordinate and makes an X, Y pair
		 * @param x
		 * @param y
		 */
		public position(int x, int y){
			X = x;
			Y = y;
		}
	}
	
	//Global Constants
	/** width of the board */
	final int WIDTH = 24;

	/** height of the board */
	final int HEIGHT = 25;
	
	//Global Variable
	/** a String model of the board */
	String Display[][] = new String[WIDTH][HEIGHT];
	
	//Private Variables
	/** array of positions for door ways */
	private position Doorways[] = new position[17];
	
	/** array of positions for tunnel entrances */
	private position Tunnels[] = new position[4];
	
	//Global Objects

	/** contains all the information on player locations */
	public Pieces Piece = new Pieces();
	
	/** contains all the information of the room and null space locations */
	Rooms Room = new Rooms();
	
	/**
	 * Constructor: initializes the door ways and tunnel 
	 * locations and creates the first viewable board
	 * <br>
	 * <b>Parameters</b>: none
	 */
	public GameBoard(){
		//populate Tunnels with tunnel locations
		Tunnels[0] = new position(1,1);//kitchen
		Tunnels[1] = new position(22,1);//observatory
		Tunnels[2] = new position(1,23);//lounge
		Tunnels[3] = new position(22,23);//study
		
		//populate Doorways with door way locations
		Doorways[0] = new position(4,7);//kitchen
		Doorways[1] = new position(7,5);
		Doorways[2] = new position(9,8);
		Doorways[3] = new position(14,8);
		Doorways[4] = new position(16,5);//ball room
		Doorways[5] = new position(18,4);//observatory
		Doorways[6] = new position(8,12);
		Doorways[7] = new position(6,16);//dining room
		Doorways[8] = new position(17,8);
		Doorways[9] = new position(22,13);//billiard room
		Doorways[10] = new position(20,13);
		Doorways[11] = new position(16,16);//library
		Doorways[12] = new position(6,18);//lounge
		Doorways[13] = new position(11,18);
		Doorways[14] = new position(12,18);
		Doorways[15] = new position(15,20);//hall
		Doorways[16] = new position(17,20);//study
		
		//create the first board model
		serialize();
	}
	
	/*
	 * METHODS:
	 * 
	 * isATunnel: finds out if a location is a tunnel entrance
	 * isAdoorway: finds out if a locations is a door way
	 * serialize: makes a board model of Stringed colors
	 * getDoorways: returns a list of doors for a room
	 */
	
	/**
	 * Public Method: isAtunnel <br>
	 * <b>Purpose</b>: determines if a cell is a tunnel location or not 
	 * <br><br>
	 * <b>Precondition</b>: must have a need to know if a location 
	 * is a tunnel entrance
	 * <br>
	 * <b>Postcondition</b>: cell is known to be a tunnel location or not
	 * @param x
	 * @param y
	 * @return boolean
	 */
	public boolean isAtunnel(int x, int y){
		for(int i = 0; i < Tunnels.length; i++)//traverse tunnels array
			if(Tunnels[i].X == x && Tunnels[i].Y == y)//if tunnels is equal to the cell
				return true;//return that it is
		return false;//return that it is not
	}
	
	/**
	 * Public Method: isAdoorway <br>
	 * <b>Purpose</b>: determines if a cell is a door way or not <br><br>
	 * <b>Precondition</b>: must have a need to know if a location is a door way
	 * <br>
	 * <b>Postcondition</b>: cell is known to be a door way or not
	 * @param x
	 * @param y
	 * @return boolean
	 */
	public boolean isAdoorway(int x, int y){
		for(int i = 0; i < Doorways.length; i++)//traverse doorways
			if(Doorways[i].X == x && Doorways[i].Y == y)//if doorways is equal to the cell
				return true;//return that is is 
		return false;//return that it is not
	}
	
	/**
	 * Public Method: serialize <br>
	 * <b>Purpose</b>: takes the information from GameBoard, 
	 * Piece, and Room to make an array that describes the board
	 * via a series of colors to represent each bit 
	 * of information about a cell
	 * <br><br>
	 * <b>Precondition</b>: board must have been changed <br>
	 * <b>Postcondition</b>: model is created and returned for display
	 * <br>
	 * <b>Parameters</b>: none
	 * @return String[][] : a 2D array of colors that will be seen by the GUI and displayed, based on the colors
	 */
	public String[][] serialize(){
		for(int j = 0; j < HEIGHT; j++)//traverse the height of the board
			for(int i = 0; i < WIDTH; i++){//traverse the width of the board
				Display[i][j] = null;//clean up old model
				if(Room.isAroom(i,j))//if the cell is a room
					Display[i][j] = Utilities.BoardColors[1];//color it light blue (carpet color)
				if(Room.isAnull(i,j))//if the cell is a null space
					Display[i][j] = Utilities.BoardColors[2];//color it brown (wood color)
				if(isAtunnel(i,j))//if the cell is a tunnel (after room, so that it can be overwritten since tunnel info is more important)
					Display[i][j] = Utilities.BoardColors[3];//color it black (darkness)
				if(isAdoorway(i,j))//if the cell is a doorway
					Display[i][j] = Utilities.BoardColors[4];//color it dark yellow (to show it is different than the corridor)
				if(Piece.isOccupied(i,j) != 0)//if the cell is occupied (after all others since it has the highest priority)
					switch(Piece.isOccupied(i,j)){//color it based on player
						case 1:
							Display[i][j] = Utilities.BoardColors[5];//Miss.Scarlet
							break;
						case 2:
							Display[i][j] = Utilities.BoardColors[6];//Professor Plum
							break;
						case 3:
							Display[i][j] = Utilities.BoardColors[7];//Mrs.Peacock
							break;
						case 4:
							Display[i][j] = Utilities.BoardColors[8];//Mr.Green
							break;
						case 5:
							Display[i][j] = Utilities.BoardColors[10];//Miss.White
							break;
						case 6:
							Display[i][j] = Utilities.BoardColors[9];//Colonel Mustard
							break;
					}
				if(Display[i][j] == null)//if it didn't get colored
					Display[i][j] = Utilities.BoardColors[0];//it is a corridor space, color it grey (tile)
			}//end for loop
		return Display;//return the model to be displayed
	}//end method
	
	/**
	 * Public Method: getDoorways <br>
	 * <b>Purpose</b>: returns the door ways out of room for leaving purposes
	 * <br><br>
	 * <b>Precondition</b>: player must be leaving a room on a move 
	 * and needs to know where the doors are <br>
	 * <b>Postcondition</b>: door ways are known
	 * @param roomNumber the room the player is in, ordered from top 
	 * left to bottom right
	 * @return int[]: a list of the coordinates for the door ways, x 
	 * followed by y makes the pair
	 */
	public int[] getDoorways(int roomNumber){
		//local variables
		int low = 0,high = 0,index = 0;//low is the low side of the doorways array that corresponds to a room,
		//high is the high side of the doorways array that corresponds to a room, index is for indexing the output array
		int doors[] = new int[8];//the array to be outputted (the ball room has the most doors with 4, and 2 coordinates each, size 8)

		switch(roomNumber){//switch the room number into bounds on the doorways area
			case 1://kitchen
				low = 0;
				high = 1;
				break;
			case 2://ballroom
				low = 1;
				high = 5;
				break;
			case 3://observatory
				low = 5;
				high = 6;
				break;
			case 4://dining room
				low = 6;
				high = 8;
				break;
			case 5://billiard room
				low = 8;
				high = 10;
				break;
			case 6://library
				low = 10;
				high = 12;
				break;
			case 7://lounge
				low = 12;
				high = 13;
				break;
			case 8://hall
				low = 13;
				high = 16;
				break;
			case 9://study
				low = 16;
				high = 17;
				break;
		}
		
		//make the output
		for(int i = low; i < high; i++){//traverse the doorways area from the low to high - 1, the area of the room
			doors[index] = Doorways[i].X;//set the x coordinate
			index++;//increase the output index
			doors[index] = Doorways[i].Y;//set the y coordinate
			index++;//increase the output index
		}
		
		//return the result
		return doors;
	}
	
	/**
	 * <b>Purpose</b>: Create a single String value that encompasses the entire board
	 * <br>
	 * <b>Precondition(s)</b>: There is a board object instance <br>
	 * <b>Postconditions</b>: A single String representing the board is returned
	 * <br>
	 * <b>Pseudo-Code</b>: <br>
	 * BEGIN <br>
	 * -> <br>
	 * END <br><br>
	 * 
	 *  
	 */
	public String StringBoard(){
		String StringBoard = "";
		for(int i = 0; i < HEIGHT; i++)
			for(int j = 0; j < WIDTH; j++)
				if(Display[j][i].compareTo(Utilities.BoardColors[11]) == 0)
					StringBoard += j + "," + i + "," + Display[j][i] + ",true;";
				else
					StringBoard += j + "," + i + "," + Display[j][i] + ",false;";
		return StringBoard;
	}
	
	/**
	 * <b>Purpose</b>: Print out a textual representation on the board
	 * <br>
	 * <b>Precondition(s)</b>: There is a board object instance <br>
	 * <b>Postconditions</b>: the board has a textual representation 
	 * printed to the console <br>
	 * <b>Pseudo-Code</b>: <br>
	 * BEGIN <br>
	 * -> <br>
	 * END <br><br>
	 * 
	 * <em>NOTE: This is only used for Unit testing.</em> 
	 */
	public void printBoard(){
		//serialize();
		for(int i = 0; i < HEIGHT; i++){
			System.out.println();
			for(int j = 0; j < WIDTH; j++){
				String out = Display[j][i];
				if(out.compareTo(Utilities.BoardColors[0]) == 0)
					out = "g";
				else if(out.compareTo(Utilities.BoardColors[1]) == 0)
					out = "l";
				else if(out.compareTo(Utilities.BoardColors[2]) == 0)
					out = "b";
				else if(out.compareTo(Utilities.BoardColors[3]) == 0)
					out = "t";
				else if(out.compareTo(Utilities.BoardColors[4]) == 0)
					out = "d";
				else if(out.compareTo(Utilities.BoardColors[5]) == 0)
					out = "R";
				else if(out.compareTo(Utilities.BoardColors[6]) == 0)
					out = "P";
				else if(out.compareTo(Utilities.BoardColors[7]) == 0)
					out = "T";
				else if(out.compareTo(Utilities.BoardColors[8]) == 0)
					out = "G";
				else if(out.compareTo(Utilities.BoardColors[9]) == 0)
					out = "Y";
				else if(out.compareTo(Utilities.BoardColors[10]) == 0)
					out = "W";
				else if(out.compareTo(Utilities.BoardColors[11]) == 0)
					out = "!";
				System.out.print(out);
			}
		}
	}
}
