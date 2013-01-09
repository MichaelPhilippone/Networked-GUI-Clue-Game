package clueServer;
import java.util.Random;

/**
 * <b>Class semantics and roles</b>: <br>
 * The Dice has only one purpose, to act as a traditional 6 sided die for game
 * play by creating a random number between 1 and 6 
 * <br><br>
 * <b>Information maintenance</b>: <br>
 * <b>Creation</b>: Dice is created during start up by GameLogic <br>
 * <b>Deletion</b>: Dice is deleted when the game ends or otherwise terminates
 * 
 * @author Matt SeGall (Class Skeletons)
 * @author Matt SeGall (Code)
 *
 */
public class Dice {
	
	/** a generator for producing the random numbers */
	private Random generator;
	
	/**
	 * Constructor: initializes the random number generator
	 * <br>
	 * <b>Parameters</b>: none
	 */
	public Dice() { generator = new Random( System.currentTimeMillis() ); }
	
	/**
	 * Public Method: Roll<br>
	 * <b>Purpose</b>: generates a number similar to rolling a die.
	 * produces a number between 1 and 6 inclusive
	 * <br><br>
	 * <b>Precondition</b>: a roll has been requested <br>
	 * <b>Postcondition</b>: a roll value is returned<br>
	 * <b>Parameters</b>: none
	 * @return a random number between 0 and 5 plus 1 (1, 2, 3, 4, 5, or 6)
	 */
	public int Roll(){ return generator.nextInt(6) + 1; }
}
