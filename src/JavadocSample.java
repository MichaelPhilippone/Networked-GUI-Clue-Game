
/*
 * This Class is for explaining a little bit about JAVADOC.
 * It has no bearing WHATSOEVER on the project itself.
 * For those that have never seen the syntax,
 * some basic commands that we'll be using are:
 * 
 * "<br>": this corresponds to a line-break in the 
 * 		  HTML output from the javadoc
 * 
 * "<em> ... some text ... </em>": this will make the 
 * 									the text between the <em> and </em>
 * 									appear italic
 * 
 * "<b> ... some text ... </b>": this will make the 
 * 									the text between the <em> and </em>
 * 									appear BOLD
 * 
 * "{@link ClassName}": this will provide a link to the ClassName
 * 						given in the corresponding HTML. It is really helpful to 
 * 						let Eclipse do this for you, just type ctl-space for a list of
 * 						auto-completions
 * 
 * "@param varName": this will label the parameter to a 
 * 					 method in the corresponding HTML
 * 
 * 
 */

 /**
  *  
  * 
  * @author Michael Philippone
  */
public class JavadocSample {
	
	/** 
	 * An example of a private constant <br>
	 * used to check if a player is "X"
	 */
	private final int X = 1;
	
	/** 
	 * An example of a private constant <br>
	 * used to check if a player is "O"
	 */
	private final int O = 2;

	/** The {@link String} object to represent my name */
	private String myName = "";
	
	/**
	 * Parameterized Constructor.  <br>
	 * Given a string, assign its value to the internal myName variable
	 * <br>
	 * <b>Precondition</b>: ... ...
	 * <br>
	 * <b>Postcondition</b>: ... ...
	 * 
	 * @param nameIn the string value to assign
	 */
	public JavadocSample(String nameIn) {
		this.myName = nameIn;
	}
	
	/**
	 * Just a simple main 'hello world' method.   <br>
	 * <b>Precondition</b>: The system is started, nothing has happened
	 * <br>
	 * <b>Postcondition</b>: Some text is printed and then the program exits
	 * 
	 * @param args - any command line arguments given to the program
	 */
	public static void main(String[] args) {
		System.out.println("HELLO WORLD!");
	}

}
