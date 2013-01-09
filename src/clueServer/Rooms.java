package clueServer;


/**
 * <b>Class semantics and roles</b>: <br>
 * Rooms maintains a group of positions for cells that are rooms on the board
 * or are null spaces on the board (around player positions and board middle)
 * <br><br>
 * <b>Information maintenance</b>: <br>
 * <b>Creation</b>: Rooms is created at start up by GameBoard <br>
 * <b>Deletion</b>: Rooms is deleted when the game has finished or 
 * otherwise been terminated.
 *
 * @author Matt SeGall (Class Skeletons)
 * @author Matt SeGall (Code)
 */
public class Rooms {
	/**
	 * Private Class: position <br>
	 * <b>Purpose</b>: creates an object to act as a struct for holding two properties
	 * that will need to be used together throughout Pieces. <br>
	 * <b>Methods</b>: none
	 */
	public class position{
		//local variables
		/** an X coordinate */
		public int X;
		
		/** an Y coordinate */
		public int Y;
		/**
		 * Constructor: takes an X coordinate, Y coordinate and makes an X, Y pair
		 * @param x X coordinate to assign to internal variable
		 * @param y Y coordinate to assign to internal variable
		 */
		position(int x, int y){
			X = x;
			Y = y;
		}
		
	}
	
	//Private Variables
	/** the positions of the room cells */
	private position Chambers[] = new position[355];

	/** the positions of the null spaces */
	private position Nulls[] = new position[51];

	/**
	 * Constructor: creates the rooms and null spaces<br>
	 * <b>Parameters</b>: none
	 */
	public Rooms(){
		//local variable
		int index = 0;//Chambers index value for array location
		
		//populate Chambers with kitchen
		for(int i = 0; i < 6; i++)
			for(int j = 0; j < 7; j++){
				Chambers[index]= new position(i,j);
				index++;
			}
		
		//populate Chambers with ball room
		for(int i = 8; i < 16; i++)
			for(int j = 2; j < 8; j++){
				Chambers[index] = new position(i,j);
				index++;
			}
		for(int i = 10; i < 14; i++)//not a perfect box
			for(int j = 0; j < 2; j++){
				Chambers[index] = new position(i,j);
				index++;
			}
		
		//populate Chambers with observatory
		for(int i = 18; i < 24; i++)
			for(int j = 0; j < 4; j++){
				Chambers[index] = new position(i,j);
				index++;
			}
		for(int i = 19; i < 24; i++){//not a perfect box
			Chambers[index] = new position(i,4);
			index++;
		}
		
		//populate Chambers with dining room
		for(int i = 0; i < 5; i++){
			Chambers[index]= new position(i,9);
			index++;
		}
		for(int i = 0; i < 8; i++)//not a perfect box
			for(int j = 10; j < 16; j++){
				Chambers[index] = new position(i,j);
				index++;
			}
		
		//populate Chambers with billiard room
		for(int i = 18; i < 24; i++)
			for(int j = 7; j < 13; j++){
				Chambers[index] = new position(i,j);
				index++;
			}
		
		//populate Chambers with library
		for(int i = 18; i < 24; i++)
			for(int j = 14; j < 19; j++){
				Chambers[index] = new position(i,j);
				index++;
			}
		for(int i = 15; i < 18; i++){//not a perfect box
			Chambers[index] = new position(17,i);
			index++;
		}
		
		//populate Chambers with lounge
		for(int i = 0; i < 7; i++)
			for(int j = 19; j < 25; j++){
				Chambers[index] = new position(i,j);
				index++;
			}
		
		//populate Chambers with hall
		for(int i = 9; i < 15; i++)
			for(int j = 19; j < 25; j++){
				Chambers[index] = new position(i,j);
				index++;
			}
		
		//populate Chambers with study
		for(int i = 17; i < 24; i++)
			for(int j = 21; j < 25; j++){
				Chambers[index] = new position(i,j);
				index++;
			}
		
		//populate Nulls
		Nulls[0] = new position(6,0);
		Nulls[1] = new position(7,0);
		Nulls[2] = new position(8,0);
		Nulls[3] = new position(6,1);//space around Miss.White starting point
		Nulls[4] = new position(15,0);
		Nulls[5] = new position(16,0);
		Nulls[6] = new position(17,0);
		Nulls[7] = new position(17,1);//space around Mr.Green starting point
		Nulls[8] = new position(0,8);//space between kitchen and dining room
		Nulls[9] = new position(23,6);//space around Mrs.Peacock starting point
		Nulls[10] = new position(23,13);//space between billiard room and library
		Nulls[11] = new position(0,16);
		Nulls[12] = new position(0,18);//space around Colonel Mustard starting point
		Nulls[13] = new position(23,20);//space around Professor Plum starting point
		Nulls[14] = new position(8,24);//space around Miss.Scarlet starting point
		Nulls[15] = new position(15,24);//space between hall and study
		
		index = 16;//switch index to use for Nulls array rather than Chambers
		for(int i = 10; i < 15; i++)//populate Nulls with middle of the board
			for(int j = 11; j < 18; j++){
				Nulls[index] = new position(i,j);
				index++;
			}//space in middle of board
	}//end constructor
	
	/*
	 * METHODS:
	 * 
	 * isAroom: determines if a cell is part of a room
	 * isAnull: determines if a cell is part of the null areas
	 */
	
	/**
	 * Public Method: isAroom <br>
	 * <b>Purpose</b>: determines whether a cell is part of any room or not <br>
	 * <b>Preconditions</b>: there needs to be a desire to check to see if a cell is part of a room
	 * <br>
	 * <b>Postconditions</b>: cell is known to be part of the room or not
	 * @param x
	 * @param y 
	 * @return boolean true or false depending on if it is part of a room
	 */
	public boolean isAroom(int x, int y){
		for(int j = 0; j < Chambers.length; j++)//traverse rooms
			if(Chambers[j].X == x && Chambers[j].Y == y)//if room cell is the same as the query cell
				return true;//return that it is part of a room
		return false;//return it is not part of a room
	}
	
	/**
	 * Public Method: isAnull <br>
	 * <b>Purpose</b>: determines whether a cell is part of any null area <br><br>
	 * <b>Precondition</b>: there needs to be a desire to check to see if a cell is part of a null area
	 * <br>
	 * <b>Postcondition</b>: cell is known to be part of the null area or not
	 * @param x
	 * @param y
	 * @return boolean true or false if it is (or is not) part of a null area
	 */
	public boolean isAnull(int x, int y){
		for(int i = 0; i < 51; i++)//traverse the null area
			if(Nulls[i].X == x && Nulls[i].Y == y)//if null cell is the same as the query cell
				return true;//return that it is part of the null area
		return false;//return that it is not part of the null area
	}
	
	/**
	 * Public Method: getChambers <br>
	 * <b>Purpose</b>: gets Chambers[] <br><br>
	 * @return Chambers
	 */
	public position[] getChambers() {
		return Chambers;
	}
	
	/**
	 * Public Method: getNulls <br>
	 * <b>Purpose</b>: gets Nulls[] <br><br>
	 * @return Nulls
	 */
	public position[] getNulls() {
		return Nulls;
	}
	
}
