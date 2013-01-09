package testPages;

import utils.Utilities;
import clueClient.Client;

/**
 * <b>Class semantics and roles</b>: <br>
 * This class represents a simple dirver class that creates 
 * (and therefore runs) the requisite number of {@link Client} objects in 
 * order to test their functioning.
 * <br><br>
 * <b>Information maintenance</b>: <br>
 * <em>Creation</em>: ??? <br>
 * <em>Deletion</em>: ??? <br>
 *  
 *  @author Michael Philippone (Code and Design)
 */
public class ClientTest {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		long time = System.currentTimeMillis();
		long timePast;
		
		/* Open the console output to a FILE: (also redirect error output there too) */
		System.setOut(Utilities.setFileOutput("AllClientsNum_Test.txt"));
		System.setErr(System.out);
		System.out.println("PROGRAM STARTED AT: " + Utilities.getTodaysDate() + "\n\n\n");
		
		
		System.out.println("This test is to show that the required number of clients ");
		System.out.println("   can connect to a running server instance and properly set themselves ");
		System.out.println("   for a game to begin.");
		System.out.println("This includes:");
		System.out.println("  - Connecting and registering with the Server process");
		System.out.println("      (Sending username)");
		System.out.println("      (receiveing information about the player's assigned game piece)");
		System.out.println("  - Obtaining a current model of the board to display on the GUI.");
		System.out.println("  - Obtaining and displaying the hand of 3 cards the player has been dealt");
		System.out.println("  - The first player should have a roll occurr");
		System.out.println("  - All other player should be alerted to the roll's value");
		System.out.println("  - The first player's board should light up with the appropriate ending spaces");
		System.out.println("");
		System.out.println("That is the extent of the preplay.");
		System.out.println("");
		System.out.println("The console out put from this test run, in conjunciton with the GUI ");
		System.out.println("  Panel updates will prove that the above conditions occurred (or did not occurr)");
		System.out.println("");
		System.out.println("Please also refer to the Server process' \"Clue_LOG.txt\" file for more information on ");
		System.out.println("  connection and communication conditions");
		System.out.println("");
		System.out.println("The Rest of this file is the output from the requisite number of Client threads and ");
		System.out.println("  and their subsequent Game updates and client-server communications");
		System.out.println("");
		System.out.println("NOTE: this test turns off after 2 minutes, since no one is playing the game, there will be no progress.");
		//System.out.println("NOTE ALSO: there is no shutoff timer for the SERVER process, that is the responsibility of the person testing.");
		System.out.println("");
		System.out.println("********************************************************************************************");
		System.out.println("");
		System.out.println("");
		System.out.println("BEGIN");
		System.out.println("********************************************************************************************");
		System.out.println("");
		System.out.println("");
		
		
		/* Make an array of Client objects: */
		Client[] cls = new Client[Utilities.numRequiredPlayers];
		String name = "mphilip1_";
		/* instantiate a Client instance in each array element: */
		for(int i=0; i<cls.length; i++) {
			cls[i] = new Client("127.0.0.1",name+i);
			cls[i].start();
			cls[i].setName(name+i);			
		}

		
		while(true) {
			timePast = System.currentTimeMillis();
			if( timePast - time >= (1000 * 60 * 2) ) {
				System.out.println("CLIENTTEST: started at "+ time +", ended at "+ timePast +" ran for "+(((timePast-time)/1000)/60)+" minutes.");
				cls[0].getClientSocketReference().send("testingshutdown");
				System.exit(0);
			}
		}
				
		
	}
}
