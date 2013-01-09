HI!

This file is VERY important.  I think this is A 
LOT easier than trying to keep checking the 
comments in the REPO updates and emails and everything else.  

So, the way it works is add a comment below, just like the first one
and the rest will always check here first to keep up-to-date.

Got it?  Great!  Communication has never been so easy!

--------------------------------------------------------
14 NOV 2008, 02:17AM, 
Michael:
Hey all, this is the first (of many!) comment in our CHANGELOG file.
It is crucial that we leave little sweet-nothings for one another here
to document the changes that we have made and what we are working on
so we don't conflict with one another's work and also so we can explain
what exactly we were thinking in coding a particular component.

This is MUCH easier than trying to meet to discuss every little change
(also, that is impossoble since we all work at such different times of 
the day!)

So, the moral of the story is always stop here after an update and 
UPDATE OFTEN!
Thanks!

~Michael
--------------------------------------------------------
16 NOV 11:15PM
Hi all, 
I tweaked my javadocs and added a bit to some of the stuff 
I did earlier in the week.

I also helped matt out with his a bit, this should all 
serve as examples so I don't have to do it again, jujst copy and paste 
the (fairly obvious, I hope) skeleton of the comments and fill them in.

I also built the javadoc site after my updates.

~Michael
--------------------------------------------------------
************************************
***************19 NOV***************
************************************
What I have done:
-- Coded Client Socket
-- javadoc'd as I went along
	-- should be a pretty solid doc base
		to follow my work
-- Added a utils package and into that I added
	a Utilities class.
	-- its all static properties/methods that I 
		wanted a access point to make modifications.
		Sure beats a search-and-replace!
-- Coded the ServerListener Class
	-- I did my best with what we have so far to 
		code up the player creation and addition
		to the Users class, but we'll see how it 
		takes shape. 

Todd-- 
	I added you to code the Server.java file, 
	you weren't listed anywhere in the implementation 
	for any server-side development and this should 
	be something that doesn't take too long.  Also, I 
	somewhat remember talking about you doing it at 
	the last group meeting.  
	I also added you to code the Client class.
	I am pretty sure these are just a few method calls, 
	nothing too serious.
	Right?  
	Speak up if I am way out of line.

Erich--
	I coded up the ClientSocket class, all it needs 
	are your tests.  It shouldn't be too bad, just 
	write a brief client server app (no user input, 
	just volleying strings across the socket) to test
	its 2-way communication.
 
TODO:
	-- We all need to study up on our Swing programming.  
		I know it's messy, but we all need to know it 
		forwards and backwards more or less.  
		Sorry! ;-)
	--   


Good luck and happy hacking!
~Michael
--------------------------------------------------------
*************************
****** 01 DEC 2008 ******
******  11:45 AM   ******
*************************
What I have done:
--> Added a bunch of skeleton to the Client Class
--> added a bunch of skeleton to the GUI Class

~Michael
--------------------------------------------------------
*************************
****** 01 DEC 2008 ******
*************************
What I have done:
--> Added the Server object reference to the ServerListener constructor
--> finished the pre-break tweaks from our "John Barr Hour"
	-> updated the Server class' method signatures and javadoc
	-> removed some code from the GUI class file(sorry Todd!)
		(The code was buggy and not really a clean implementation for a GUI.)
	-> added "connect()" method to Client, for alerting sub-objects
		that a connection should be made
--> updated sequence diagrams for startup sequences(fail and success)
--> updated LaTeX docs with new images
--> compiled javadoc
~Michael
--------------------------------------------------------
***********************
***** 03 DEC 2008 *****
***********************
	-> Added a Testing package to fill with test driver classes.

~Michael
--------------------------------------------------------
***********************
***** 06 DEC 2008 *****
***********************
Here are my notes from our 05 DEC meeting with John Barr:

	- Fix socket names in ALL sequence diagrams
		- specifically: move, accuse, guess (both).  
		- Though there could be problems with this in 
			any of the other diagrams as well
	
	- Integrate network stuff with user management stuff
	
	- develop socket message protocol 
		- (ie: how will our messages on the socket be formatted?)
	
	- ensure that ALL sequence diagrams are represented in ALL class skeletons
	
	- more specifically outline what each method does in its javadoc
		- optimally: use pseudo-code to describe the method actions
	
	- change "makeUser" arrow in startup sequence diagram to "createPlayer"
	
	- change "play" call in startup sequence diagram to "nextPlayer"
	
	- add "move" call between Server & gamelogic in move sequence 
		diagram and startup sequence diagram (after "nextPlayer")
	
	- fix move sequence diagram to match code (and vice versa)
	
	- determine necessary "fake-AI" for player removal during game-play
	
	- determine how to listen on socket AND take GUI input
	
	ADDED 07 DEC 2008:
	
	- make sure our documentation does not allow jumping over a player 
		on the ending space
		
	- add proper doorways to ÒREPO://src/clueServer/clueBoard.jpgÓ 
		(actually edit the image file)
	
	- in socket, test for "good" connection each time
		- handle if connection is lost
		
	X make ServerListener keep track of timings (ie: not enough users timeout)

	- make "quit" / shutdown methods

	X remove "assign" method call from "startup_success" sequence diagram
	
	X remove "sixPlayers" method call from "startup_success" sequence diagram
	
	X remove cards manipulation method call from "startup_success" sequence diagram
	
	X remove "populate" method callS from "startup_success" sequence diagram
	
	
	
Whew!
~Michael
--------------------------------------------------------
***********************
***** 07 DEC 2008 *****
***********************
UPDATES:
	- removed "assign" method call from "startup_success" sequence diagram
	- removed "sixPlayers" method call from "startup_success" sequence diagram
	- removed card manipulation method callS from "startup_success" sequence diagram
	- removed "populate" method callS from "startup_success" sequence diagram
	
	- manufactured latest documentation release
		- LaTeX'd our work from today (the test plan)
		- provided two version of documentation 
			(one is FULL book, other is abrreviated, currently-due work)
		- These two documents are in "REPO://DesignDocumentation/DesignBooklet/"
	
	- Updated the group site accessible via "http://rohan.ithaca.edu/~mphilip1/"

~Michael
--------------------------------------------------------
***********************
***** 09 DEC 2008 *****
***********************
UPDATES:
	- Added timeout functionality to ServerListener class
		- not tested yet, but it times out the ServerSocket's 
		accept method after a specified num of milliseconds.
		I specified that number in the Utilities class so we 
		can change it easily if need be
	
	- Spruced up the Users and Player functions.
		- some of the code was a little hairy.  
		- I included pseudo code of what I did in the javadoc, 
		check the "REPO://doc/index.html" site for info
	
	- removed creatUser method from ServerListener
		- we are using the one in Users anyways
	
	- Added access to rooms and pieces names via the Utilities class
		- you'll notice 2 arrays of strings, those are where we should 
		read the names of pieces and rooms from
		- I already changed the piece names' access in Users to the 
		Utilities array
		
	-  added functionality for ClientSocket / Player creation from ServerListener
	
	- added socket update code to Users

~Michael
--------------------------------------------------------
***********************
***** 10 DEC 2008 *****
***********************
	- added some beginnings of testing software for the socket.
		- I know I wasn't supposed to, but it needs to get 
			done and I needed to test my code.
			
	- I added the lookups for the piece and room strings to point 
		to Utilities class property values
	
	- tested ClientSocket with another ClientSocket (works!)
		- See NetTestClient and NetTestServer for more details
	
	- I started writing a little driver to test socket communication through Player creation
		- I'll finish it up later on
	
~Michael
--------------------------------------------------------

*****************************
*****10 Dec 2008 ************

I've updated the move sequence diagram and the move formal scenario but I havn't committed them. 
I'm not sure how to do that.

Thanks Mike for doing the ClientSocket connection. I didn't realize you had done it so i still added 
my work to svn site. I had problems with the send() method. I had to add a print writer to it before
it worked. But you manage to get it to work without doing that so i guess i missed something. 

I also started writing a driver to get an initial connection and create the players but i keep getting
a null pointer exception. i'll keep working on that.

Erich
--------------------------------------------------------
***********************
***** 10 DEC 2008 *****
***********************
UPDATES:
	- I had already written a test suite for the ClientSocket, if you want to look at that
		- see "REPO://src/testPages/NetTestServer.java" & "REPO://src/testPages/NetTestClient.java"
	
	-  when we write a test suite, I think it would be best to use a class
	that has the class(es) that we are trying to test as a property, not to just
	copy the class code into a new file.  This way we can keep the class code separate
	from the test code. (let me know if this is unclear)
	
	- 

~Michael
--------------------------------------------------------
***********************
***** 12 DEC 2008 *****
***********************
UPDATES:

	- Cleaned up "REPO://src/testPages/"
		- removed GameBoard class from testPages package
		- Removed Dice from testPages and replaced it with DiceTest
			- the new one just uses an object instance of Dice, instead of its code.
		- moved Erich's socket testing logic to NetTestServer.java and NetTestClient.java
		- moved GameBoard testing logic into GameBoardTest class
			- replaced with a test driver wrapper class
			- moved printBoard() method into GameBoard.java in clueServer package

	- !!! I made a HUGE change !!!
		- I was noticing that it was VERY difficult to use the ServerListener as we had designed it,
		so to fix this, I added it to the Users class instead of the Server class
		- updated sequence diagram for Startup to reflect the change
	
		- Finished the UserManagementComponentsTest.java & UserManagementComponentsTestClient.java classes
		- THEY WORK!!
		- made client threads that connect to the UserManagementComponentsTest.java 
		- the 'server' then reads their info in, prints out the Player metadata and sends a message to them
	
	- started a protocol spec for the Socket communication strings
		- see the file: "REPO://src/SocketStringProtocol.txt"
	
	- Todd:
		- javadoc'd the hell out of the SERVER class for you
			- I am not guaranteeing that the pseudoCode I've offered is 100%, but it IS a start.
				- check with Matt about how to call the GameLogic stuff
		- added the computerPlayer() method stub, feel free to add to it.
	
	- Started some work on debug output during Server system running
		- should make printing output to a file easier for logging, error detection, etc.
		- we'll see if it is worth the time though
		- if you want to use the functionality:
			-	just test if Server.debugMode is true and print out your message if it is

TODO:	
	- javadoc for printBoard method in GameBoard.java
		- Matt ?
	- nap.


WHEW.

~Michael
--------------------------------------------------------
***********************
***** 13 DEC 2008 *****
***********************
UPDATES:
	- turns out it is REALLY easy to add file output for debugging
		- created logging functionality for Server software
		- there is a boolean flag that tells the whole Server hierarchy whether or not 
		to print things into a file / console (depending on the message importance)
		- for more info see javadoc site-->Server-->prepOutput() method listing

	- started in on the Client program
		- hashed out the socket reading technique
		- still need implementations after message parsing
		- started in on GUI building
			- added window listener support for closing socket connections 
			when the player closes the GUI window
		- almost done with BoardModel? 
			- need Cell MouseListener implementation
			- need Card MouseListener implementation
		- 
		
~Michael
---------------------------------------------------------------------
***********************
***** 13 DEC 2008 *****
***********************
UPDATES:
-I uploaded all the changes we made to the design book.
-We still need the sequence diagrams updated.  The info for changes is in the REPO.  We discussed these in meetings, please take care of these.
-I started some server integration testing.
-GameLogic has a bug in it that prevents nicely coding the move correctly
-I fixed this so that it does a brute force method
-more resource intensive, but works properly
-With the change made, the code now has lots of useless lines in it
-should these be taken out or left commented out???
-All the parts from GameLogic down seem to be working as planned
-Todd, I added two lines in GameBoard so that it prints out spaces a player can move to in an '!', exclamation point
-I looked at the progress of Server.  I added comments where needed for further info.

~Matt
---------------------------------------------------------------------
***********************
***** 14 DEC 2008 *****
***********************
UPDATES:
	- WOO! I did some really cool stuff with the colors (client and server side)
		- pulled the color names into an array of strings (like rooms, pieces, etc)
		- also created a corresponding array of integer triples for Red Green Blue (RGB values)
		- this eases the act of searching for color updates and assigning cell colors on the GUI
	- The GUI is all done (I think)
		- there are cards now
			- as free checkboxes that don't affect anything
			- when a users' cards are flagged as "held", the background color for the checkbox turns red
		- see the code comments and javadoc for explanantions on what is happening and when
		- I tried to be as intuitive as possible, but Swng is tough.  

TODO:
	- Sync client and server on network 
	- fill in Server methods
	- add and implement a notifyCardChoice() method 
		- determine if specified player is computer player or not
		- act accordingly
	- move parseIncomingMessage(String message) method to Server (from Player)
		- add neccessary code to Server's Play() method to handle the strings from the clients		

~Michael
--------------------------------------------------------------------
December 14, 2008

Updates:
	-Gave GameBoard a method to make the board one large string
		-cell values separated by a ' ' (space)
	-The listenForUsers in the serverListener was being called twice and messing up connection attempts
		-fixed
	-got client/server connections verified.
	-got message passing working between client/server
To do:
	-do something if a seventh player attempts to connect
		
	-Mike, could you look at the string tokenizing in client?  It finds the update command (only one
	 I have tested so far), but does not see any other tokens and all the parsing stuff then becomes
	 blank.  I have the full message being displayed in the client's inform field, so I know it is 
	 passing fine.
	
	*******
	- I am working on getting the tokenizing just right, there was a problem with how I was building the "remaining" string.
		- I should have it sorted out by tomorrow afternoon so development can continue on.
	- I added titles to the outputs you were using to the Server console so we can see where they originate.  
	- seventh player connection should be handled in the sense that there is no longer a listener running after sixth player is connected.
		~Michael
	*******
	
~Matt

***************************************
December 15th

     - I moved the parseIncomingMethod from the player class to the server
     - It calls methods that has different return values so i created new properties
       in the Server Class
     - I wanted to start on a createMessageToSend method to send strings to the
       client but decided to stop after looking at my watch.
     - Eclipse did not show me errors so i tried as much as possible to triple check
       what i did for errors.
     - And if an easier or better way exists to parse messages and call the appropriate
       methods, please go ahead and get rid of what I did.
~Erich
***************************************
DECEMBER 15TH
TODD WRIGHT

	- ALL TESTS FOR GAME LOGIC AND ITS COMPONENTS ARE COMPLETE. CURRENTLY IT ONLY OUTPUTS
	VALUES THAT YOU WOULD NOT UNDERSTAND WITHOUT LOOKING AT THE CODE DIRECTLY. ALL THAT
	IS LEFT IS TEST REPORTS AND MAKING THE CODE MORE PRETTY SO THAT YOU CAN UNDERSTAND WHAT
	TESTS ARE HAPPENING WITHOUT HAVING TO LOOK AT THE TESTING CODE. I WILL WORK ON THIS 
	TOMORROW MORNING.
	
********************************************
December 17th
Todd Wright
	- Fixed the testing output so the latex wouldn't cut off lines.
	Everything looks good to go.