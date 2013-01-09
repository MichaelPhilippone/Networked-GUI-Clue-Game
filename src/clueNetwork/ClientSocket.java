package clueNetwork;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import utils.Utilities;

import clueClient.Client;
import clueServer.Player;
import clueServer.ServerListener;

/**
 * <b>Class semantics and roles</b>: <br>
 * The ClientSocket is the main utility for communicating over a socket connection. 
 * this includes board updates, guesses and accusations. <br> 
 * ANY usage of the socket is over a ClientSocket object. <br><br>
 * 
 * <b>Information maintenance</b>: <br>
 * <b>Creation</b>: The ClientSocket is created on the server-side when 
 * a connection is established with a client process. On the client-side, a 
 * ClientSocket is created when the user chooses to try to connect to a server instance. <br>
 * <b>Deletion</b>: The ClientSocket is deleted when the connection is lost.
 *  
 *  @author Michael Philippone (Class Skeletons & Code)
 *  @author Erich Schudt (Testing and Drivers)
 */
public class ClientSocket {

	/** Internal representation of the Java {@link Socket} object */
	private Socket sk = null;
	
	/** string representation of the other end of the socket connection */
	private String endPointAddress = "";
	
	/** integer representation of the other end of the socket's port */
	private int endPointPort = -1;
	
	/**
	 * Default Constructor <br>
	 * Takes 0 arguments, calls {@code ClientSocket("127.0.0.1");}
	 * <br>
	 * (Same as calling {@code connect("127.0.0.1" , Utilities.defaultPort);})
	 */
	public ClientSocket() { this("127.0.0.1"); }
	
	/**
	 * Constructor 01<br>
	 * Takes 1 argument, calls {@code ClientSocket(addr , Utilities.defaultPort);}
	 * <br>
	 * (Same as calling {@code connect(addr , Utilities.defaultPort);})
	 * 
	 * @param addr the address of the other socket endpoint
	 */
	public ClientSocket(String addr) { this(addr, Utilities.defaultPort); }
	
	/**
	 * Constructor 02<br>
	 * Takes 2 arguments, calls {@code ClientSocket(addr, port);}
	 * <br>
	 * (Same as calling {@code connect(addr,port);})
	 * 
	 * @param addr the address of the other socket endpoint 
	 * @param port the port for the socket to communicate on
	 */
	public ClientSocket(String addr, int port) { this.connect(addr, port); }
	
	/**
	 * Constructor 03<br>
	 * Takes 1 arguments, calls {@code connect(Socket sktIn);}
	 * <br>
	 * (Same as calling {@code connect(Socket sktIn);})
	 * 
	 * @param sktIn the active socket from 
	 * {@link ServerListener}'s intially-detected connection 
	 */
	public ClientSocket(Socket sktIn) { this.connect(sktIn); }
	
	//////////// METHODS ////////////
	
	/**
	 * <b>Purpose</b>: The connect method connects to 
	 * a TCP port at another computer.
	 * <br>
	 * <b>Precondition</b>: There is not connection, there is an endpoint 
	 * ready to hold a TCP session <br>
	 * <b>Postcondition</b>: There is a stateful, persistent 
	 * TCP connection established and the properties {@code endPointAddress} 
	 * and {@code endPointPort} are set to the given values 
	 * <br>
	 * 
	 * @param address the address of the other endpoint in the TCP communication
	 * @param port the port of the other endpoint in the TCP communication
	 * @return boolean true if the send was successful, false if not
	 * 
	 * @deprecated this should only ever accept an already-connected socket, 
	 * like the one from {@link ServerListener}.  So use the one 
	 * that takes in a socket as its argument  
	 */
	public boolean connect(String address, int port) { 
		String oldAddress = "";
		int oldPort = -1;
		try { 
			// keep track of the old properties' values, 
			// then set them to the new/given values
			oldAddress = this.endPointAddress;
			oldPort = this.endPointPort;
			this.endPointAddress = address;
			this.endPointPort = port;
			
			//instantiate the socket with the address and port given
			this.sk = new Socket(this.endPointAddress, this.endPointPort);
			
			//signal success
			return true;
			
		} catch(UnknownHostException uhe) {
			uhe.printStackTrace();	//be verbose
			//if it fails, ensure that our property values aren't affected
			this.endPointAddress = oldAddress;
			this.endPointPort = oldPort;
			//now, signal the error
			return false;
		}
		catch(IOException ioe) {
			ioe.printStackTrace();	// be verbose
			//if it fails, ensure that our property values aren't affected
			this.endPointAddress = oldAddress;
			this.endPointPort = oldPort;
			//now, signal the error
			return false;
		}
	}
	
	/**
	 * <b>Purpose</b>: This connect method assigns this class'
	 *  socket a reference to an active one from {@link ServerListener}'s 
	 *  initial detection of a connection.
	 * <br>
	 * <b>Precondition</b>: There may or may not be an 
	 * active socket referenced by {@code this.sk} 
	 * <br>
	 * <b>Postcondition</b>: There is a socket 
	 * referenced by {@code this.sk} 
	 * <br>
	 * 
	 * @param sktIn the socket to reference by {@code this.sk}
	 * @return boolean true if the send was successful, false if not  
	 */
	public boolean connect(Socket sktIn) {
	
		try {
			this.sk = sktIn;
			this.endPointAddress = this.sk.getInetAddress().getHostAddress();
			this.endPointPort = this.sk.getPort();
		} catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	/**
	 * Method returns data sent over the socket. <br>
	 *  <b>Precondition</b>: There is a connection, the other 
	 *  endpoint loads data onto the socket  <br>
	 *  <b>Postcondition</b>: The socket is in its ready state <br>
	 * 
	 * @return the value of the data received from the socket as a string, 
	 * or null if it fails
	 */
	public String read() { 
		BufferedReader br = null;
		
		try {

			// Create a new buffered reader to read from the socket
			br = new BufferedReader( 
					new InputStreamReader ( 
							this.sk.getInputStream() ) );
			
			// Now, block until something is availabe to read:
			while(!br.ready());
			
			// Now, since the while loop has eneded
			// we can assume that the reader is ready.
			// so return the string
			return br.readLine();
			
		} catch (IOException ioe) {
			ioe.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Method sends the bytes corresponding to the given message on the socket. <br>
	 * <b>Precondition</b>: The socket is in the ready state, 
	 * there is a message to be sent<br>
	 * <b>Postcondition</b>: The message has been sent, 
	 * the socket is in the ready state <br>
	 * 
	 * @param message the string formatted message to send as bytes on the socket
	 * @return boolean true if the send was successful, false if not
	 */
	public boolean send(String message) { //throws SocketTimeoutException { 
		DataOutputStream dos = null;
		 try {
			 
			 //open up an output stream with the output stream 
			 // from the underlying socket
			 dos = new DataOutputStream( sk.getOutputStream() );
			 
			 // clear out the stream:
			 //dos.flush();
			 
		 } catch (IOException ioe) {
			System.out.println("Error acquiring socket output stream.");
			ioe.printStackTrace();
			return false;
		 }
		 
		 try {
			 
			// Write the string onto the socket and then 
			// terminate it with a CRLF sequence
			// just to push out the last of the byte-stream...
			 dos.writeBytes(message + Utilities.CRLF);
			 return true;	//Done! 
			 
		 } catch (IOException ioe) {
			 System.out.println("Error while writing on socket output stream.");
			 ioe.printStackTrace();
			 return false;
		 }		
	}
	
	/**
	 * Method closes the socket connection on call. Returns true if successful. <br>
	 * <b>Precondition</b>: Socket exists in the ready state <br>
	 * <b>Postcondition</b>: socket connection to endpoint is terminated / closed <br>
	 * @return boolean true if the disconnect was successful, false if it was not
	 */
	public boolean close() { 
		 try {
			 
			 //call the underlying socket's close method
			 this.sk.close();
			 return true;		// Done! that's it!
			 
		 } catch (IOException ioe) {
			 System.out.println("Error closing socket.");
			 ioe.printStackTrace();
			 return false;		// Oops! error!
		 }
	}
	
	/**
	 * <b>Purpose</b>: Get the address this socket is communicating to.
	 * @return the IP address string of the <em>other</em> computer
	 * on the socket
	 */
	public String getEndPointAddress() { return this.sk.getInetAddress().getHostAddress(); }
	
	/**
	 * <b>Purpose</b>: Get the port this socket is communicating on.
	 * @return the integer port number of the <em>other</em> computer
	 * on the socket
	 */
	public int getEndPointPort() { return this.sk.getPort(); }
	
	/**
	 * <b>Purpose</b>: Get the whole socket instance from this class.
	 * @return the socket instance maintained in this class
	 */
	public Socket getSocket() { return this.sk; }
	
}
