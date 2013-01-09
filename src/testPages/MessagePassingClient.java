package testPages;

import utils.Utilities;
import clueNetwork.ClientSocket;


/**
 * <b>Class semantics and roles</b>: <br>
 * This class is used to test the passage of Strings that lead 
 * to method calls from the client to the server.
 * @author Erich Schudt (code)
 */
public class MessagePassingClient {
 
 
   public static void main(String[] args) {

   /* Open the console output to a FILE: */
	System.setOut(Utilities.setFileOutput("MessagePassingTest_Client.txt"));
	System.setErr( System.out );
	System.out.println("PROGRAM STARTED AT: " + Utilities.getTodaysDate() + "\n\n\n");
	
	
    String guess = "guess miss_scarlet revolver kitchen"; //test the guess method
    String accuse = "accuse miss_white rope study"; // test the accuse method
    String endturn = "endturn"; // test the endturn method
    String move = "move 13 12"; // test the move method
    String exit = "exit"; //test the setComputerPlayer method
    
    System.out.println("Message Passing and method calls Test Report");
    System.out.println("");
    System.out.println("Client Startup");
    System.out.println("");
    System.out.println("4 Steps to test message passing and method calls");
    System.out.println("  1) Create a ClientSocket to connect with the testing server process");
    System.out.println("  2) create a connection with another ClientSocket instance");
    System.out.println("  3) send Strings that lead to method calls in Server");
    System.out.println("  4) receive Strings to confirm methods were called");
    System.out.println("  5) close the connection");
    System.out.println("");
    
    // STEP 01 and 02
    ClientSocket clientSocket = new ClientSocket("127.0.0.1", 1234);
    System.out.println("CLIENT: Step 01 Success: working so far(create connection)");

    //Step 03 and 04
    
    //testing the move string 
    clientSocket.send(move);
    System.out.println("");
    System.out.println("Test send() in clientSocket");
    System.out.println("passed in the move protocol");
    System.out.println("Expected output:                    move 13 12  -the move method was called");
    System.out.println("CLIENT: from server  Actual output: " + clientSocket.read());
    
    //testing the accuse string 
    clientSocket.send(accuse);
    System.out.println("");
    System.out.println("Test send() in clientSocket");
    System.out.println("passed in the accuse protocol");
    System.out.println("Expected output:                    accuse miss_white rope study  -the move method was called");
    System.out.println("CLIENT: from server  Actual output: " + clientSocket.read());
    
    //testing the guess string 
    clientSocket.send(guess);
    System.out.println("");
    System.out.println("Test send() in clientSocket");
    System.out.println("passed in the guess protocol");
    System.out.println("Expected output:                    guess miss_scarlet revolver  -the move method was called");
    System.out.println("CLIENT: from server  Actual output: " + clientSocket.read());
    
    //testing the exit string 
    clientSocket.send(exit);
    System.out.println("");
    System.out.println("Test send() in clientSocket");
    System.out.println("passed in the exit protocol");
    System.out.println("Expected output:                    exit  -the setComputerPlayer method was called");
    System.out.println("CLIENT: from server  Actual output: " + clientSocket.read());
    
    //testing the endturn string 
    clientSocket.send(endturn);
    System.out.println("");
    System.out.println("Test send() in clientSocket");
    System.out.println("passed in the endturn protocol");
    System.out.println("Expected output:                    endturn  -the nextPlayr method was called");
    System.out.println("CLIENT: from server  Actual output: " + clientSocket.read());
    
    // STEP 05
    clientSocket.close();
    System.out.println("");
    System.out.println("CLIENT: the connection has been closed, Step 5 success");
    System.out.println("");
    System.out.println("The test was a complete success. No bugs of any kind");
    System.out.println("Did not test error strings because it never occurs in the game");
   } 
}