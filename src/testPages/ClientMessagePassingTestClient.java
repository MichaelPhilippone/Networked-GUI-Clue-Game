package testPages;

import utils.Utilities;
import clueNetwork.ClientSocket;

/**
 * <b>Class semantics and roles</b>: <br>
 * This class is used to test the passage of Strings that lead 
 * to method calls from the client to the server.
 * 
 * @author Erich Schudt (code)
 */

public class ClientMessagePassingTestClient {

	private static ClientSocket clientSocket;
	String username = "testerGuy";
	
	/**
	 * 
	 * This method reads for strings in from the server and parses it.
	 * It then calls the appropriate method.
	 * Code taken from Client Class.
	 * 
	 * @author Erich Schudt (code)
	 */
    public void readFromServer() {
		
		/* fromServer: what we are getting from the server: 
		 * remaining: the rest of the server communication */
		String fromServer = "" , 
				remaining = "";
		
		/* disallow blank strings
		 * (keep reading until we DON'T have a blank string): */
		while(fromServer.equalsIgnoreCase(""))
			fromServer = clientSocket.read();
		///////////////
		/* parse the server message: */
		///////////////
		
		String[] stk = fromServer.split(" ");
		
		if(stk[0].equalsIgnoreCase("update")) {
			
			/* build the remaining tokens into a string:  */
			remaining = "";
			for(int i=1; i<stk.length; i++)
				remaining += stk[i];
			
			/* parse this string for the update tuples:  */
			//boardModel.parseUpdateString(remaining);
			
			/* print the message */
			System.out.println("CLIENT("+ username +"): update ");
			clientSocket.send(remaining + "  -the parseUpdateString method was called");
			
		}
		else if(stk[0].equalsIgnoreCase("havecard")) {
			
			/* build the remaining tokens into a string:  */
			remaining = "";
			for(int i=1; i<stk.length; i++)
				remaining += " " + stk[i];
			remaining = remaining.trim();
			
			/* print the message */
			System.out.println("CLIENT("+ username +"): havecard: " + remaining);
			clientSocket.send(remaining + "  -the haveCards method was called");
			
			/* pass this string in to be parsed */
			//boardModel.haveCards(remaining);
			
			//gui.draw();
		}
		else if(stk[0].equalsIgnoreCase("choosecard")) {

			/* build the remaining tokens into a string:  */
			remaining = "";
			for(int i=1; i<stk.length; i++)
				remaining += " " + stk[i];
			remaining = remaining.trim();
			
			/* print the message */
			System.out.println("CLIENT("+ username +"): choosecard: " + remaining);
			clientSocket.send(remaining + "  -the inform method was called");
			
			//gui.inform(fromServer);
			
			//boardModel.chooseCards(remaining);
			
			//gui.draw();
		}
		else if(stk[0].equalsIgnoreCase("inform")) {

			/* build the remaining tokens into a string:  */
			remaining = "";
			for(int i=1; i<stk.length; i++)
				remaining += " " + stk[i];
			remaining = remaining.trim();
			
			/* print the message */
			System.out.println("CLIENT("+username+"): inform: " + remaining);
			clientSocket.send(remaining + "  -the inform method was called");
			//gui.inform(remaining);
			
			//gui.draw();
		}
		else if(stk[0].equalsIgnoreCase("gameover")) {
			/* exit the system, ignore any other socket strings */
			//gui.inform("Game over.  Shutting Down.");
			//systemShutdown(Utilities.e_ExitCode.clientGameOver);
			clientSocket.send(remaining + "  -the systemShutdown method was called");
		}
		else {
			System.out.println("CLIENT("+username+"): error reading string from server.  Unrecognizable socket message.");
			System.out.println("CLIENT("+username+"): server sent: \"" + fromServer + "\".");
			clientSocket.send("invalid string");
		}
	}
	
	public static void main(String[] args) {

		/* Open the console output to a FILE: (also redirect error output there too) */
		System.setOut(Utilities.setFileOutput("ClientMessagePassingTest_Client.txt"));
		System.setErr(System.out);
		System.out.println("PROGRAM STARTED AT: " + Utilities.getTodaysDate() + "\n\n\n");
		
		
	    ClientMessagePassingTestClient client = new ClientMessagePassingTestClient();
	    clientSocket = new ClientSocket("127.0.0.1", 64000);
	    System.out.println("CLIENT: Step 01 Success: working so far(create connection)");
	    
	    int index = 0;
	    while (index < 6){
	    	System.out.println("got into while loop");
	    	client.readFromServer();
	    	System.out.println("read message " + (index + 1));
	    	index ++;
	    }
	    clientSocket.close();
	}

}
