package testPages;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import utils.Utilities;

import clueNetwork.ClientSocket;
import clueServer.GameLogic;
import clueServer.GameLogic.GuessReturn;
import clueServer.Player;

/**
 * <b>Class semantics and roles</b>: <br>
 * This class is used to test the passage of Strings that lead 
 * to method calls from the client to the server.
 * 
 * @author Erich Schudt (code)
 */
public class MessagePassingServer {

	private static Player currPlayer;
	private static Socket skTmp;
	private static GameLogic logic;
	
	/*
	 * Method that contains pieces of codes taken from the Server Class
	 *  that parses incoming messages from the client's clientScoket
	 *  and calls the appropriate methods
	 */
	public void parseMessage(){
		
		String message = "";
		String[] token;
		int strNum = 1;
		String x= "";
		String y = "";
		
		//while till a value is read from socket
		while(message.equalsIgnoreCase("")){
			message = currPlayer.readFromSocket();
		}

			token = message.split(" ");
			
			//if the message passed is a move protocol
			//call the move method and send back the message received
			if(token[0].compareToIgnoreCase("move") == 0){
				x = token[strNum++];
				y = token[strNum++];
				int xPos = Integer.parseInt(x);
				int yPos = Integer.parseInt(y);
				logic.Board.Piece.relocate(logic.currentPlayer, xPos, yPos);
				logic.Board.serialize();
				String send = "move " + x + " " + y + "  -the move method was called";
				currPlayer.writeOnSocket(send);
			}
			
			//if the message passed is an exit protocol
			//call the setComputerPlayer method and send back the message received
			else if(token[0].compareToIgnoreCase("exit") == 0){
				currPlayer.writeOnSocket("exit  -the setComputerPlayer method was called");
			}
			
			//if the message passed is an endturn protocol
			//call the nextPlayer method and send back the message received
			else if(token[0].compareToIgnoreCase("endturn") == 0){
				currPlayer.writeOnSocket("endturn  -the nextPlayer method was called");
			}
			
			//if the message passed is an accuse protocol
			//call the accuse method and send back the message received
			else if(token[0].compareToIgnoreCase("accuse") == 0){
				String player = token[strNum++];
				String weapon  = token[strNum++];
				String room = token[strNum++];
				boolean accuse = logic.accuse(player, weapon, room);
				String send = "accuse " + player + " " + weapon + " " + room + "  -the accuse method was called";
				currPlayer.writeOnSocket(send);
			}
			
			//if the message passed is a guess protocol
			//call the guess method and send back the message received
			else if(token[0].compareToIgnoreCase("guess") == 0){
				String player = token[strNum++];
				String weapon  = token[strNum++];
				GuessReturn guessReturn = logic.guess(player, weapon);
				String send = "guess " + player + " " + weapon + "  -the guess method was called";
				currPlayer.writeOnSocket(send);
			}
	}
	
	
	public Player makePlayer() {
		ServerSocket servSock = null;
		try { servSock = new ServerSocket(1234);
		System.out.println("SERVER: Step 01 Success");}
		catch (IOException ioe) { System.out.println("Error creating Server Socket"); }
		
		System.out.println("Starting ServerSocket...");
		skTmp = null;
		try { skTmp = servSock.accept(); 
		System.out.println("SERVER: step 2 is also good");}
		catch(IOException ioe) {System.out.println("Error accepting connection");}

		return new Player(
				"happyTesterGuy",
				skTmp,
				"miss_scarlett",
				null);
	}
	
	
	
	public static void main(String[] args) {
		
		/* Open the console output to a FILE: */
		System.setOut(Utilities.setFileOutput("MessagePassingTest_Server.txt"));
		System.setErr( System.out );
		System.out.println("PROGRAM STARTED AT: " + Utilities.getTodaysDate() + "\n\n\n");
		
		
		MessagePassingServer server = new MessagePassingServer();
		logic = new GameLogic();
		
		System.out.println("Server Startup");
		  System.out.println("4 Steps to test message passing and method calls");
		  System.out.println("  1) Create a ClientSocket to connect with the testing client process");
		  System.out.println("  2) receive strings that lead to method calls");
		  System.out.println("  3) send confirmation to client that right strings were received");
		  System.out.println("  4) close the connection");
		 
		  currPlayer = server.makePlayer();
			int index = 0;
			
			//repeat the parseMessage method 5 times to test all netwprotocols
		  while(index<5){
			// STEP 02
		   System.out.println("got message " + (index+1));
		   
		   //Step 03
		   server.parseMessage();
		   index ++;
		  }
			
		
			// STEP 04
			ClientSocket clientSocket = new ClientSocket(skTmp);
			clientSocket.close();
			System.out.println("SERVER: the connection has been closed, Step 4 success");
			System.out.println("Refer to client side's console for test report");

	}

}
