package testPages;

import utils.Utilities;
import clueServer.Rooms;

/**
 * <b>Class semantics and roles</b>: <br>
 * This class is used to test the functionality of the {@link Rooms} class and
 * its methods. <br>
 * @author Todd Wright
 */
public class RoomsTest {

	/**
	 * <b>Purpose</b>: Test if the isAroom method can recognize if an X and Y 
	 * pair are part of a room. Test if the isAnull method can recognize if an 
	 * X and Y pair is a null cell.
	 * <br><br>
	 * @param args
	 */
	public static void main(String[] args) {
		
		/* Open the console output to a FILE: (also redirect error output there too) */
		System.setOut(Utilities.setFileOutput("RoomsTest.txt"));
		System.setErr(System.out);
		System.out.println("PROGRAM STARTED AT: " + Utilities.getTodaysDate() + "\n\n\n");
		
		// declare Rooms object
		Rooms rooms = new Rooms();


		System.out.println("!THIS IS THE UNIT TEST FOR THE ROOMS CLASS!");
		System.out.println("-------------------------------------------");
		
	
		/*
		 * Run the chambers array through a for loop, checking if every X and Y
		 * pair in the array are in fact room cell coordinates.
		 */
		System.out.println("**This is a test of the isAroom() method.**" +
				"\nHere we run the Chambers array through the isAroom() method" +
				"\nto make sure that all rooms, are in fact rooms, and that the" +
				"\nmethod is actually working. Expected output is no ouput.");
		for (int i = 0; i < rooms.getChambers().length; i++) {
			// GOT RID OF THIS BECAUSE THERE WAS SO MUCH OUTPUT!
			/*
			// Output the X and Y pair at current index.
			System.out.println("X: " + rooms.getChambers()[i].X + " Y: "
					+ rooms.getChambers()[i].Y);
			// Output the return from running the isAroom method with the
			// above X and Y
			// pair (will return true).
			System.out.println(rooms.isAroom(rooms.getChambers()[i].X, rooms
					.getChambers()[i].Y));
			// Just in case, check if the return of the isAroom method is
			// false.
			// If it is false, output that the X and Y pair are not part of
			// a room.
			// Then break out of the for loop.
			 */
			if (rooms.isAroom(rooms.getChambers()[i].X,
					rooms.getChambers()[i].Y) == false) {
				System.out.println("COORDINATES ARE NOT PART OF A ROOM!");
				System.out.println("Output: " + rooms.isAroom(rooms.getChambers()[i].X, rooms.getChambers()[i].Y));
			}
		}
		System.out.println();
		System.out.println();
		System.out.println();
		
		// Output coordinates that are not part of a room, and were purposely
		// chosen. Output the return of the isAroom method with the X and Y 
		// pair that are not part of a room (will return false).
		System.out.println("Now we input coordinates that were purposesly chosen " +
				"\nto not be part of a room.  Expected output is false.");
		System.out.println("Coordinates to be input:");
		System.out.println("X: 7" + " Y: 0");
		System.out.println("Output: " + rooms.isAroom(7, 0));

		System.out.println("--------------------");

		/*
		 * Run the nulls array through a for loop, checking if every X and Y
		 * pair in the array are in fact null cell coordinates.
		 */
		System.out.println("**This is a test of the isAnull() method.**" +
				"\nHere we run the Nulls array through the isAnull() method" +
				"\nto make sure that all nulls, are in fact nulls, and that the" +
				"\nmethod is actually working. Expected output is no ouput.");
		for (int j = 0; j < rooms.getNulls().length; j++) {
			/*
			// Output the X and Y pair at current index.
			System.out.println("X: " + rooms.getNulls()[j].X + " Y: "
					+ rooms.getNulls()[j].Y);
			// Output the return from running the isAnull method with the above X and Y 
			// pair (will return true).
			System.out.println(rooms.isAnull(rooms.getNulls()[j].X, rooms
					.getNulls()[j].Y));
			// Just in case, check if the return of the isAnull method is false.
			// If it is false, output that the X and Y pair is not a null cell. 
			// Then break out of the for loop.
			 */
			if (rooms.isAnull(rooms.getNulls()[j].X, rooms.getNulls()[j].Y) == false) {
				System.out.println("COORDINATES ARE NOT A NULL CELL!");
				System.out.println("Output: " + rooms.isAnull(rooms.getNulls()[j].X, rooms.getNulls()[j].Y));
			}
		}
		System.out.println();
		System.out.println();
		System.out.println();
		
		
		// Output coordinates that are not null cell coordinates, and were purposely
		// chosen. Output the return of the isAnull method with the X and Y 
		// pair that are not a null cell (will return false).
		System.out.println("Now we input coordinates that were purposesly chosen " +
				"\nto not be a null cell.  Expected output is false.");
		System.out.println("Coordinates to be input:");
		System.out.println("X: 7" + " Y: 0");
		System.out.println("Output: " + rooms.isAroom(7, 0));

	}
}
