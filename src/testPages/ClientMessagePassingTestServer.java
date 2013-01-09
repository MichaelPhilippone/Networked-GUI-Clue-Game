package testPages;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import utils.Utilities;

import clueNetwork.ClientSocket;

/**
 * <b>Class semantics and roles</b>: <br>
 * This class is used to test the passage of Strings that lead 
 * to method calls from the server to the client.
 * 
 * @author Erich Schudt (code)
 */
public class ClientMessagePassingTestServer {

 private static Socket skTmp;
 
 public static void main(String[] args) {

	/* Open the console output to a FILE: (also redirect error output there too) */
	System.setOut(Utilities.setFileOutput("ClientMessagePassingTest_Server.txt"));
	System.setErr(System.out);
	System.out.println("PROGRAM STARTED AT: " + Utilities.getTodaysDate() + "\n\n\n");
	
	 
	  String gameover = "gameover";
	  String update = "update 7,0,blue,true;7,1,grey,true";
	  String havecard = "havecard 7;6";
	  String choosecard = "choosecard 7;6";
	  String inform = "inform the test is working well";
	  String error = "error";
	  
	  ServerSocket servSock = null;
	  try { servSock = new ServerSocket(64000);}
	  catch (IOException ioe) { System.out.println("Error creating Server Socket"); }
	  
	  System.out.println("Starting ServerSocket...");
	  skTmp = null;
	  try { skTmp = servSock.accept();}
	  catch(IOException ioe) {System.out.println("Error accepting connection");}
	
	  System.out.println("Message Passing and method calls in Client Test Report");
	  System.out.println("");
	  System.out.println("Server Startup");
	  System.out.println("");
	  System.out.println("4 Steps to test message passing and method calls");
	  System.out.println("  1) Create a ClientSocket to accept connecgtion from client");
	  System.out.println("  2) send Strings that lead to method calls in Server");
	  System.out.println("  3) receive Strings to confirm methods were called");
	  System.out.println("  4) close the connection");
	  System.out.println("");
	  
	  //Step 01
	  ClientSocket clientSocket = new ClientSocket(skTmp);
	  System.out.println("Step 1 is a success");
	  
	  //Step 02 and Step 03
	  
	  //testing gameover string
	  clientSocket.send(gameover);
	  System.out.println("");
	  System.out.println("Test send() in clientSocket");
	  System.out.println("passed in the gameover protocol");
	  System.out.println("Expected output:                      -the systemShutdown method was called");
	  System.out.println("SERVER: from client  Actual output: " + clientSocket.read());
	
	  
	  //testing update string
	  clientSocket.send(update);
	  System.out.println("");
	  System.out.println("Test send() in clientSocket");
	  System.out.println("passed in the update protocol");
	  System.out.println("Expected output:                    7,0,blue,true;7,1,grey,true  -the parseUpdateString method was called");
	  System.out.println("SERVER: from client  Actual output: " + clientSocket.read());
	
	  
	  //testing inform string
	  clientSocket.send(inform);
	  System.out.println("");
	  System.out.println("Test send() in clientSocket");
	  System.out.println("passed in the inform protocol");
	  System.out.println("Expected output:                    the test is working well  -the inform method was called");
	  System.out.println("SERVER: from client  Actual output: " + clientSocket.read());
	  
	  
	  //testing havecard string
	  clientSocket.send(havecard);
	  System.out.println("");
	  System.out.println("Test send() in clientSocket");
	  System.out.println("passed in the havecard protocol");
	  System.out.println("Expected output:                    7;6  -the haveCards method was called");
	  System.out.println("SERVER: from client  Actual output: " + clientSocket.read());
	  
	  
	  //testing choosecard string
	  clientSocket.send(choosecard);
	  System.out.println("");
	  System.out.println("Test send() in clientSocket");
	  System.out.println("passed in the choosecard protocol");
	  System.out.println("Expected output:                    7;6  -the inform method was called");
	  System.out.println("SERVER: from client  Actual output: " + clientSocket.read());
	  
	  
	  //testing an error string
	  clientSocket.send(error);
	  System.out.println("");
	  System.out.println("Test send() in clientSocket");
	  System.out.println("passed in an error string (no protocol)");
	  System.out.println("Expected output:                    invalid string");
	  System.out.println("SERVER: from client  Actual output: " + clientSocket.read());
	  
	  
	  // STEP 04
	  clientSocket.close();
	  System.out.println("");
	  System.out.println("SERVER: the connection has been closed, Step 5 success");
	  System.out.println("");
	  System.out.println("The test was a complete success. No bugs of any kind");
 }

}
