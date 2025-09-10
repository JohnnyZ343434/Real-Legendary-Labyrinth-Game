package application;

import control.MazeController;

/*
 * Basic Task percentage(all completed): Bruce Yin 25%, Johnny Zhong 30%, Andy Feng 45%
 * Additional Tasks(all completed): Bruce Yin 100%, Johnny Zhong 100%, Andy Feng 100%
 * 
 * Header:
 * Name: Bruce Yin, Andy Feng, Johnny Zhong
 * Date: November 24, 2024
 * Course: ICS4U1-02 P2 S1 
 * Teacher: Mr.Fernandes
 * Title: Real Legendary Labyrinth Game
 * Description: 
 * 				
 * Major Skill: JFrame elements, if statement, constructor, Arrays, 
 * 				Method Object and Classes, Stacks, GUI components(eg. JFrame, JButton, JSlider), 
 * 				IPO programming, selection statements, For loops and While loops, 
 * 				Methods, Class inheritance. etc.
 * 
 * Added Features: 
 *	Basic: 	1. Basic board loads up and treasure tiles show up on the board (random placement)
			2. Cards are shuffled and dealt to each player (1 to 6 cards each)
			3. ‘Free’ tile rotates both ways and inserts
			4. Tiles shift properly and the ‘end’ tile becomes the new ‘free’ tile
			5. Token shifts to other side of game board if on an ‘end’ tile
			6. Token moves to valid tiles only (must have a direct pathway)
			7. Overall layout and design of GUI is both functional and attractive
			8. GUI is user-friendly - instructions, labels to keeps track of game status, messages given for user feedback
			9. Game play fully works as described in problem
 *	
 *	Advance:
 *		1. Computer player (AI) to replace one or more of the human players(Andy)
 *		2. Potential pathways are ‘highlighted’ to show possible moves(Bruce)
 *		3. Game can be saved and reloaded(Johnny)
 *
 *  				
 */


public class LabyrinthApplication {
	//main method
	public static void main(String args[]) {
		
		// call the controller to start the game
		MazeController controller = new MazeController();
	}
}