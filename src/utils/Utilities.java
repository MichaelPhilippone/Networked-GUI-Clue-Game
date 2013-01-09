package utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.SocketTimeoutException;
import java.util.Date;

import clueClient.Client;
import clueClient.GUI;
import clueServer.ServerListener;

/**
 * This is a collection of static methods and properties 
 * (read: global constants) that I was finding useful to 
 * include several times, so I unified the access point(s) 
 * for them.
 * <br>
 * 
 * @author Michael Philippone
 */
public class Utilities {
	
	/** 
	 * a String constant that represents a {@code CRLF} 
	 * character sequence (for flushing byte streams)<br>
	 * Signifies an end to a socket communication 
	 */
	public static final String CRLF = "\r\n";
	
	/**
	 * Enum of possible system exit status codes
	 */
	public enum e_ExitCode {
		/** represents a normal exit */
		normal,
		/** represents an unrecoverable {@link ServerListener} error */
		listenerError,
		/** represents an error on the {@link Client}'s part while trying to connect */
		clientConnectError,
		/** represents a normal, 'gameover' signal received by {@link Client} */
		clientGameOver,
		/** represents a shutdown signal received from the client during a testing */
		testingShutdown
	}
	
	/** 
	 * This is the unified access point for the 
	 * commonly used default port number, if it 
	 * has to be changed, this means we only need 
	 * to change it in one place. 
	 */
	public static final int defaultPort = 64000;
	
	/**
	 * This is the unified access point for the 
	 * commonly used number corresponding to 
	 * the number of allowable/required players
	 * for the game 
	 */
	public static final int numRequiredPlayers = 6;
	
	/**
	 * This is the value--in milliseconds--that the 
	 * {@link ServerListener} will listen with it's 
	 * {@code accept} method before raising a 
	 * {@link SocketTimeoutException} event <br>
	 * <b>60 seconds in a minute, 1000 milliseconds 
	 * in a second, 5 minutes</b>
	 */
	public static final int serverListenerTimeoutMillis = (60 * 1000) * 5;
	
	/**
	 * Array representing the names of the rooms on the board
	 * This will allow easy changing of rooms' names
	 * for customizing the experience. <br>
	 * <em>Intentionally not {@code final} so as to allow re-defining (config file...?)</em>
	 */
	public static String[] roomNames = 
		{
			"Kitchen",			// 0
			"Ballroom",			// 1
			"Observatory",		// 2
			"Dining_Room",		// 3
			"Billiard_Room",	// 4
			"Library",			// 5 
			"Lounge",			// 6
			"Hall",				// 7
			"Study"				// 8
		};
	
	/**
	 * Array representing the names of the player pieces
	 * This will allow easy changing of players' names
	 * for customizing the experience. <br>
	 * <em>Intentionally not {@code final} so as to allow re-defining (config file...?)</em>
	 * For example to get to the piece name corresponding to Miss Scarlett you woulkd type: <br>
	 * {@code Utilities.pieceNames[0]} <br>
	 */
	public static String[] pieceNames = 
		{
			"Miss_Scarlet",		// 0
			"Professor_Plum",	// 1
			"Mrs._Peacock",		// 2
			"Mr._Green",		// 3
			"Miss_White",		// 4
			"Colonel_Mustard"	// 5
			//"TOP SECRET"		// 6
		};
	
	/**
	 * Array representing the names of the weapons
	 * This will allow easy changing of weapons' names
	 * for customizing the experience. <br>
	 * <em>Intentionally not {@code final} so as to allow re-defining (config file...?)</em>
	 */
	public static String[] weaponNames = 
		{
			"Wrench",			// 0
			"Pipe",				// 1
			"Revolver",			// 2
			"Rope",				// 3
			"Knife",			// 4
			"Candlestick"		// 5
		};
	
	/**
	 * <b>PART 01 of the coloring scheme</b> (see {@code RGBColors[]} for more information) <br>
	 * Array representing all of the choices for colors on the board <br>
	 * This is used both for both client and server side components, and allows an easy changing of the color choices <br>
	 */
	public static final String[] BoardColors = 
		{
			"Grey",			// 0
			"LightBlue",	// 1
			"Brown",		// 2
			"Black",		// 3
			"DarkYellow",	// 4
			"Red",			// 5
			"Purple",		// 6
			"Turquoise",	// 7
			"Green",		// 8
			"Yellow",		// 9
			"White",		// 10
			"Pink",			// 11
			"Orange"		// 12 (this is only ever used to signal a color error)
		};
	/**
	 * <b>PART 02 of the coloring scheme</b> (see {@code RGBColors[]} and {@code BoardColors[]} for more information) <br>
	 * Array representing all of meanings for the colors choices <br>
	 */
	public static final String[] ColorsMeanings = 
		{
			"Hallway",					// 0
			"Room Space",		// 1
			"No space",			// 2
			"Tunnel",			// 3
			"Doorway",			// 4
			"Miss Scarlet",		// 5
			"Prof Plum",		// 6
			"Mrs Peacock",		// 7
			"Mr Green",			// 8
			"Col Mustard",		// 9
			"Mrs White",		// 10
			"Clickable",		// 11
			"Error"				// 12 (this is only ever used to signal a color error)
		};

	/**
	 * <b>PART 03 of the coloring scheme</b> (see {@code BoardColors[]} for more information) <br>
	 * Array representing all of the RGB color values corresponding to color choices named in {@code BoardColors[]} <br>
	 * This is used both for both client and server side components, and allows an easy changing of the color choices <br>
	 */
	public static final int[][] RGBColors = 
		{ 
			{100,100,100},	// Grey 0 
			//{152,245,255},	// Light Blue 1
			{102,195,205},	// Light Blue 1
			{200,200,200},	// Brown 2
			{0,0,0},		// Black 3
			{205,173,0},	// Dark Yellow 4
			{255,0,0},		// Red 5
			{127,91,216},	// Purple 6
			{64,224,158},	// Turquoise 7
			{0,128,0},		// Green 8
			{255,255,0},	// Yellow 9
			{255,255,255},	// White 10
			{255,105,180},	// Pink 11
			{255,69,0}		// Orange 12 (this is only ever used to signal a color error)
		};
	
	/** String that is printed into the Title bar on the {@link Client}'s {@link GUI} Frame */
	public static String ClientFrameTitle = "Clue | The Green Team";
	
	/** and integer constant representing the height of the window */
	public static final int WindowHeight = 740;
	
	/** and integer constant representing the width of the window */
	public static final int WindowWidth = 850;
	
	/**
	 * <b>Purpose</b>: 
	 * Utility function for opening a {@link FileOutputStream} on a {@link File} <br>
	 * <b>Preconditions</b>: None <br>
	 * <b>Postconditions</b>: There is a {@link File} object ready to be written <br>
	 * 
	 * <b>Pseudo-Code</b>: <br>
	 * BEGIN <br>
	 * - TRY to open a {@link FileOutputStream} on the {@link File} with the name parameter <br>
	 * - - package the {@link FileOutputStream} into a {@link PrintStream} <br>
	 * - CATCH {@link FileNotFoundException} and just return the console {@link PrintStream} <br>
	 * END <br><br>
	 * 
	 * @param fileName the name of the file to open the {@link PrintStream} on
	 * @return the {@link PrintStream} opened on a file (success), or the console {@link PrintStream} (failure)
	 */
	public static PrintStream setFileOutput(String fileName) {
		try { 
			return 
				new PrintStream( 
						new FileOutputStream(
								new File(fileName)));
		}
		catch(FileNotFoundException fnfe){
			System.out.println("\nFile not found for debugging log.");
			System.out.println("Printing to console instead.");
			System.out.println("");
			return System.out;
		}
	}
	
	/**
	 * A convenience method for constructing a date and time string.
	 * @return A string representing the date and time of this method call
	 */
	public static String getTodaysDate() {
		Date d = new Date();
		String date = (d.getMonth()+1) + "/ " + (d.getDate()) + "/" + (d.getYear()+1900) + " ";
		date += (d.getHours()) + ":" + (d.getMinutes()) + ":" + (d.getSeconds());
		date += "  (mm/dd/yyy hh:mm:ss)";
		return date;
	}
	
}
