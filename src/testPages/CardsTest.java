package testPages;

import utils.Utilities;

import clueServer.Cards;

/**
 * <b>Class semantics and roles</b>: <br>
 * This class is used to test the functionality of the {@link Cards} class and
 * its methods. <br>
 * @author Todd Wright
 */
public class CardsTest {

	/**
	 * <b>Purpose</b>:   
	 * <br><br>
	 * @param args
	 */
	public static void main(String[] args) {
		
		/* Open the console output to a FILE: (also redirect error output there too) */
		System.setOut(Utilities.setFileOutput("CardsTest.txt"));
		System.setErr(System.out);
		System.out.println("PROGRAM STARTED AT: " + Utilities.getTodaysDate() + "\n\n\n");
		
		
		// declare Card object
		Cards c = new Cards();
		
		// declare second Card object
		Cards c2 = new Cards();
		
		// declare third Card object
		Cards c3 = new Cards();

		// declare array object and populate with first Card objects deck
		String[][] d = c.getDeck();
		
		// declare array object and populate with second Card objects deck
		String[][] d2 = c2.getDeck();
		
		// declare array object and populate with third Card objects deck
		String[][] d3 = c3.getDeck();
		
		System.out.println("!THIS IS THE UNIT TEST FOR THE CARDS CLASS!");
		System.out.println("-------------------------------------------");
		System.out.println("This is a test to see if the cards are being shuffled, " +
				"\nbeing assigned an owner, and if that owner is able to " +
				"\nbe gotten." +
				"\nExpected output is 3 different decks, and each owner owning " +
				"\na different set of 3 cards each deck.");
		System.out.println();
		int i;
		int j;
		// Print out all the cards of the first deck, as well as all the owners of each card.
		System.out.println("Shuffle #1");
		for (i = 0; i < 7; i++)
			for (j = 0; j < 3; j++){
				System.out.println("Card: " + d[i][j] + 
						"   Owner: " + c.getOwner(d[i][j]));
			}
		
		System.out.println("---------------------");
		
		// Print out all the cards of the second deck, as well as all the owners of each card.
		System.out.println("Shuffle #2");
		for (i = 0; i < 7; i++)
			for (j = 0; j < 3; j++){
				System.out.println("Card: " + d2[i][j] + 
						"   Owner: " + c2.getOwner(d[i][j]));
			}
		
		System.out.println("---------------------");
		
		//Print out all the cards of the third deck, as well as all the owners of each card.
		System.out.println("Shuffle #3");
		for (i = 0; i < 7; i++)
			for (j = 0; j < 3; j++){
				System.out.println("Card: " + d3[i][j] + 
						"   Owner: " + c3.getOwner(d[i][j]));
			}
	}

}
