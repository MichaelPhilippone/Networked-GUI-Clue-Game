package testPages;

import utils.Utilities;
import clueServer.Dice;

/**
 * <b>Purpose</b>: Test the functionality of the {@link Dice} object 
 * @author Todd Wright
 */
public class DiceTest {
	
	/**
	 * <b>Purpose</b>: Driver method to test the functionality of {@link Dice} rolls <br>
	 * <b>Pseudo-Code</b>: <br>
	 * BEGIN <br>
	 * --> declare an array of {@link Dice} objects <br>
	 * --> for each {@link Dice} object in the array:
	 * --> --> call the {@code roll} method of the selected {@link Dice} object <br>
	 * --> --> if the result of the {@code roll} method was less than 1 or greater than 6: <br>
	 * --> --> --> print message and break out of loop <br>
	 * --> otherwise, no errors <br>
	 * END <br>
	 * @param args
	 */
	public static void main(String[] args) {

		/* Open the console output to a FILE: (also redirect error output there too) */
		System.setOut(Utilities.setFileOutput("DiceTest.txt"));
		System.setErr(System.out);
		System.out.println("PROGRAM STARTED AT: " + Utilities.getTodaysDate() + "\n\n\n");
		
		//declare variable size
		int size = 1000;
		
		//declare Dice object
		Dice die = new Dice();
		
		//create an array called diceArray
		int[] diceArray = new int[size];
		
		System.out.println("!THIS IS A UNIT TEST OF THE DICE CLASS!");
		System.out.println("---------------------------------------");
		
		//populate diceArray with values from the roll method
		System.out.println("**This is a test of the Roll() method.**");
		System.out.println("Populate a Dice array with 1000 rolls.  Check" +
				"\nif each roll is not less than 1, and not greater than 6." +
				"\nExpected output is that no errors occured.");
		int i;
		for( i = 0; i < size; i++ ) {
			diceArray[i] = die.Roll();
			//if the value from Roll() are less than 1 or greater than 6,
			//output that an error occurred and break the loop
			if(diceArray[i] < 1 && diceArray[i] > 6) { 
				System.out.println("Output: Error occurred!");	
				return;
			}
		}
		//output that no errors occurred
		System.out.println("Output: No errors!");
	}
	
}
