package clueNetwork;

/**
 * 
 * @deprecated
 * 
 * <b>Class semantics and roles</b>: <br>
 * This class represents the payload of data on a socket. <br>
 * It is used to shuttle information back and forth 
 * between two socket connection endpoints. <br>
 * The main usage is no defined yet.
 * 
 * <br><br>
 * 
 * <b>Information maintenance</b>: <br>
 * <em>Creation</em>: The object is created when passing some data on the socket <br>
 * <em>Deletion</em>: the object is destroyed after receipt by the 
 * destination socket endpoint
 *  
 *  @author Michael Philippone (Class Skeletons & Code)
 */
public class UpdateState {
	
	/** the payload as an object to be passed on the socket */
	private Object objPayload = null;
	
	/** the payload as a double to be passed on the socket */
	private double numPayload = -1.0;
	
	/** the payload as a boolean to be passed on the socket */
	private boolean boolPayload;
}
