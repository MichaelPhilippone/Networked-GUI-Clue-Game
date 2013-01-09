package clueServer;

/**
 * <b>Class semantics and roles</b>: <br>
 * Pieces keeps track of player locations throughout the game.  It also sets
 * the initial starting points of all the players.
 * <br><br>
 * <b>Information maintenance</b>: <br>
 * <b>Creation</b>: Pieces is created at start up by GameBoard. <br>
 * <b>Deletion</b>: Pieces is deleted when the game has finished 
 * or otherwise been terminated.
 *
 * @author Matt SeGall (Class Skeletons & Code)
 */
public class Pieces {
	/**
	 * Private Class: position <br>
	 * <b>Purpose</b>: creates an object to act as a struct for holding two properties
	 * that will need to be used together throughout Pieces.
	 * <br>
	 * <b>Methods</b>: none
	 */
	private class position{
		//local variables
		/** an X coordinate */
		public int X;
		
		/** a Y coordinate */
		public int Y;
		
		/**
		 * Constructor: takes an X coordinate, Y coordinate and makes an X, Y pair
		 * @param x the X coordinate to assign to the corresponding internal variable
		 * @param y the Y coordinate to assign to the corresponding internal variable
		 */
		public position(int x, int y){
			X = x;
			Y = y;
		}
	}
	
	//Private variable
	private position Positions[] = new position[6];//holds the position for each player
	
	/**
	 * Constructor: initializes the player positions
	 * <br>
	 * <b>Parameters</b>: none
	 */
	public Pieces(){
		//initialize start positions
		Positions[0] = new position(7,24);//7,24, Miss.Scarlet
		Positions[1] = new position(23,19);//23,19, Professor plum
		Positions[2] = new position(23,5);//23,5, Mrs.Peacock
		Positions[3] = new position(14,0);//14,0, Mr.Green
		Positions[4] = new position(9,0);//9,0, Miss.White
		Positions[5] = new position(0,17);//0,17, Colonel Mustard
	}
	
	/*
	 * METHODS:
	 * 
	 * isOccupied: determines where a spot is occupied by a player
	 * relocate: updates a player's position
	 * getX: gets a player's x coordinate
	 * getY: gets a player's y coordinate
	 */
	
	/**
	 * Public Method: isOccupied
	 * <b>Purpose</b>: determines where a given space contains a player or not, 
	 * and which is so.
	 * <br><br>
	 * <b>Precondition</b>: there is a need to know whether a space is occupied
	 * <br>
	 * <b>Postcondition</b>: occupancy of a cell is known
	 * @param x
	 * @param y
	 * @return int (player number or 0 if no one)
	 */
	public int isOccupied(int x, int y){
		for(int i = 0; i < 6; i++)//traverse players
			if(Positions[i].X == x && Positions[i].Y == y)//if player space is same as given space
				return (i + 1);//return player number (number is 1 more than i)
		return 0;//return 0, no one is occupying the room
	}
	
	/**
	 * Public Method: relocate<br>
	 * <b>Purpose</b>: updates a players position after a move has been selected
	 * <br><br>
	 * <b>Precondition</b>: a move was made <br>
	 * <b>Postcondition</b>: player has been repositioned
	 * @param player 
	 * @param x
	 * @param y
	 * Return: none
	 */
	public void relocate(int player, int x, int y){
		Positions[player].X = x;//set player's x coordinate to new value (player number is 1 more than player's index)
		Positions[player].Y = y;//set player's y coordinate to new value (player number is 1 more than player's index)
	}
	
	/**
	 * Public Method: getX <br>
	 * <b>Purpose</b>: returns a player's x coordinate
	 * <br><br>
	 * <b>Precondition</b>: move has begun and the need for a player's 
	 * position has come up <br>
	 * <b>Postcondition</b>: player's x coordinate is now known
	 * @param player
	 * @return an integer: player's X coordinate (player number is 1 more than player's index)
	 */
	public int getX(int player){
		return Positions[player].X;
	}
	
	/**
	 * Public Method: getY <br>
	 * <b>Purpose</b>: returns a player's y coordinate 
	 * <br><br>
	 * <b>Precondition</b>: move has begun and the need for a player's 
	 * position has come up <br>
	 * <b>Postcondition</b>: player's y coordinate is now known
	 * @param player
	 * @return player's Y coordinate (player number is 1 more than player's index)
	 */
	public int getY(int player){
		return Positions[player].Y;
	}
}
