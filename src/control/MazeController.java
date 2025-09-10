package control;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Stack;
import java.util.concurrent.TimeUnit;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import model.Ai;
import model.BackgroundMusic;
import model.Board;
import model.Card;
import model.Player;
import model.Tile;
import view.LabyrinthBoardPanel;
import view.LabyrinthGameFrame;
import view.LabyrinthHelpFrame;
import view.LabyrinthSettingFrame;
import view.LabyrinthStartFrame;

//Controller: Johnny Zhong 90%, Andy Feng 10%.
/**
 * This class serves as the controller for the Labyrinth game, handling logic,
 * player interactions, and game state transitions between views and models.
 */


public class MazeController implements ActionListener, KeyListener {

	// StartFrame start = new StartFrame;
	public static Stack<Card> cardStack = new Stack<>();
	public Stack<Tile> tileStack = new Stack<>();
	public ImageIcon messiIcon = new ImageIcon("image/Messi.png");
	public ImageIcon aragornIcon = new ImageIcon("image/Aragorn.png");
	public ImageIcon kobeIcon = new ImageIcon("image/Kobe.png");
	
	//music
	private BackgroundMusic backgroundMusic;
	
	
	// Create the frames as fields to access their buttons
	private LabyrinthSettingFrame settingFrame = new LabyrinthSettingFrame();
	private LabyrinthHelpFrame helpFrame = new LabyrinthHelpFrame();
	private LabyrinthStartFrame startFrame = new LabyrinthStartFrame();
	private LabyrinthGameFrame gameFrame;
	private Board board;

	// default start position
	static final int[][] StartPosition = { { 0, 0 }, { 0, 6 }, { 6, 0 }, { 6, 6 } };

	private boolean gameStart = false;

	// Constructor
	public MazeController() {

		// show the start frame and add actionListeners
		startFrame.setVisible(true);
		startFrame.getStartButton().addActionListener(this);
		startFrame.getHelpButton().addActionListener(this);
		settingFrame.getStartButton().addActionListener(this);
		settingFrame.getLoadButton().addActionListener(this);

	}
	

	// getters and setters
	public void setSettingFrame(LabyrinthSettingFrame settingFrame) {
		this.settingFrame = settingFrame;
	}

	public LabyrinthSettingFrame getSettingFrame() {
		return settingFrame;
	}

	public Board getBoard() {
		return board;
	}

	public void setBoard(Board board) {
		this.board = board;
	}

	public LabyrinthStartFrame getStartFrame() {
		return startFrame;
	}

	public void setStartFrame(LabyrinthStartFrame startFrame) {
		this.startFrame = startFrame;
	}

	public LabyrinthGameFrame getGameFrame() {
		return gameFrame;
	}

	public void setGameFrame(LabyrinthGameFrame gameFrame) {
		this.gameFrame = gameFrame;
	}

	public ImageIcon ronaldoIcon = new ImageIcon("image/Ronaldo.png");

	// a method to add the image as a field of the tile.
	// the image is added based on other attributes of this tile.
	private static Tile combineFields(String name, String shape, int direction, boolean fixed, int col, int row) {

		// find the image using the name
		ImageIcon[] tempArray = new ImageIcon[4];
		// insert all 4 images
		for (int x = 0; x < tempArray.length; x++) {
			tempArray[x] = new ImageIcon("image//" + name + x + ".png");
		}
		// create a temporary Tile
		Tile tempTile = new Tile(tempArray, name, shape, direction, fixed, col, row);
		return tempTile;
	}

	// a method to scan and return a stack of all cards.
	public static Stack<Card> ScanCards() {

		// Start by putting the cards into a stack and shuffle it
		// read input the cards
		try {

			// Scanner for input cards
			Scanner inputCard = new Scanner(new File("files/Cards.csv"));
			inputCard.useDelimiter(",|\r\n");
			// inputCard.nextLine();

			for (int x = 0; x < 24; x++) {

				// Name of treasure(String variable, if have any)
				String name = inputCard.next();
				// Shape of the tile
				int owner = inputCard.nextInt();
				// Fixed or not(boolean variable)
				int undefined = inputCard.nextInt();
				// The coordinate of the tile
				boolean find = inputCard.nextBoolean();
				if (inputCard.hasNext())
					inputCard.nextLine();

				// Use Scanner to input the image;
				Card tempCard = combineCards(name, owner, find);

				// adding the data into the tile stack
				cardStack.push(tempCard);
			}
			// Closing the scanner.
			inputCard.close();

		} catch (FileNotFoundException error) {
			System.out.println("File Error.ppp");
		}

		// randomize the stack
		Collections.shuffle(cardStack);

		// return statement
		return cardStack;

	}

	// load the default game instead of saved game.
	// Setup Game Algorithm (distribute tiles and treasures)

	// This method is to scan the default tiles
	// and return an array filled with tiles
	public static Tile[] ScanTiles() {

		// read the tiles
		// Create an array to store the tiles
		Tile[] tileArray = new Tile[50];
		try {

			// Scanner for input tiles
			Scanner inputTreasure = new Scanner(new File("files//Treasures.csv"));
			inputTreasure.useDelimiter(",|\r\n");
			// inputTreasure.nextLine();
			

			int x = 0;
			// Scan each line
			while (inputTreasure.hasNext()) {

				// Name of treasure(String variable, if have any)
				String name = inputTreasure.next();
				// Shape of the tile
				String shape = inputTreasure.next();
				// The direction the tile is facing
				int direction = inputTreasure.nextInt();
				// Fixed or not(boolean variable)
				boolean fixed = inputTreasure.nextBoolean();
				// The coordinate of the tile
				int col = inputTreasure.nextInt();
				int row = inputTreasure.nextInt();
				// Go to next line if any exists
				if (inputTreasure.hasNext())
					inputTreasure.nextLine();

				// Use Scanner to input the image;
				Tile tempTile = combineFields(name, shape, direction, fixed, row, col);
				// adding the data into the tile stack
				tileArray[x] = tempTile;
				x++;

			} // Closing the scanner.
			inputTreasure.close();

		} catch (FileNotFoundException error) {
			System.out.println("File Error.ppp");// report any errors;
		}

		// add the non-treasure tiles from 25 to 35
		// These have a specific amount so add them to the array manually
		for (int x = 24; x < 36; x++) {
			ImageIcon[] tempArray = new ImageIcon[4];
			String iShape = "I";
			for (int direction = 0; direction < 4; direction++)
				tempArray[direction] = new ImageIcon("image//" + iShape + direction + ".png");
			tileArray[x] = new Tile(tempArray, "I", iShape, 0, true, -1, -1);
		}

		// "L"s
		for (int x = 36; x < 46; x++) {
			ImageIcon[] tempArray = new ImageIcon[4];
			String iShape = "L";
			for (int direction = 0; direction < 4; direction++)
				tempArray[direction] = new ImageIcon("image//" + iShape + direction + ".png");
			tileArray[x] = new Tile(tempArray, "L", iShape, 0, true, -1, -1);
		}

		// 4 fixed "L"s
		for (int x = 46; x < 50; x++) {
			ImageIcon[] tempArray = new ImageIcon[4];
			String iShape = "L";
			for (int direction = 0; direction < 4; direction++)
				tempArray[direction] = new ImageIcon("image//" + iShape + direction + ".png");
			tileArray[x] = new Tile(tempArray, "L", iShape, 0, false, StartPosition[x - 46][0],
					StartPosition[x - 46][1]);
		}

		// add the "L" images for the "L" tiles.
		ImageIcon[] tempArray = new ImageIcon[4];
		String iShape = "L";
		for (int direction = 0; direction < 4; direction++)
			tempArray[direction] = new ImageIcon("image//" + iShape + direction + ".png");
		tileArray[46] = new Tile(tempArray, "L", iShape, 1, false, StartPosition[0][0], StartPosition[0][1]);
		tileArray[47] = new Tile(tempArray, "L", iShape, 0, false, StartPosition[1][0], StartPosition[1][1]);
		tileArray[48] = new Tile(tempArray, "L", iShape, 2, false, StartPosition[2][0], StartPosition[2][1]);
		tileArray[49] = new Tile(tempArray, "L", iShape, 3, false, StartPosition[3][0], StartPosition[3][1]);

		return tileArray;
	}

	// method to add the image for each card as a field
	private static Card combineCards(String name, int owner, boolean find) {

		// Create a temporary Image Icon to store the image
		ImageIcon image = new ImageIcon("image/Card" + name + ".png");
		// Create a temporary card to return the fields
		return new Card(image, name, owner, find);
	}

	@Override
	// ActionListeners for any actions performed by the frames.
	public void actionPerformed(ActionEvent event) {

		//System.out.println("Listened");
		
		// if start button is clicked
		if (event.getSource() == this.getStartFrame().getStartButton()) {
			// play music
			backgroundMusic = new BackgroundMusic();
			backgroundMusic.playMusic("music/bgm.wav");
			this.settingFrame.setVisible(true);
			this.startFrame.setVisible(false);
		}

		// if help button is clicked
		if (event.getSource() == this.getStartFrame().getHelpButton()) {
			this.helpFrame.setVisible(true);
		}

		// if load button is clicked
		if (event.getSource() == this.getSettingFrame().getLoadButton()) {
			loadGame("files/save.txt");
			//System.out.println("loading");
		}

		// if the start button on the second frame is clicked, initialize the players
		if (event.getSource() == this.getSettingFrame().getStartButton()) {

			// read in the images
			ImageIcon avatarArray[] = new ImageIcon[4];
			// Get the image icon for each player
			for (int x = 0; x < avatarArray.length; x++) {
				avatarArray[x] = new ImageIcon("image/avatar" + x + ".jpg");
			}

			// Initialize the players
			Player[] playerArray = new Player[4];
			boolean havePlayer = false;
			int Pnum = 0;

			// Create each player based on user's choice on the setting frame
			for (int x = 0; x < playerArray.length; x++) {
				// Get the number of cards from the JSlider
				int numOfCards = this.getSettingFrame().getCardSlider().getValue();
				String playerType = (String) this.getSettingFrame().getPlayerMenu(x).getSelectedItem();

				// if this player isn't selected then nothing happens
				if (playerType.equals("None"))
					continue;
				Pnum++;
				if (playerType.equals("Player")) {
					havePlayer = true;
					playerArray[x] = new Player(x, avatarArray[x], numOfCards);
				} else {
					playerArray[x] = new Ai(x, avatarArray[x], numOfCards, playerType);
				} // Get the player from the JComboBox from the setting Frame for each player
			}

			// Only if there is a player, the game can be started.
			if (havePlayer) {
				// start
				this.board = new Board(playerArray);
				board.setMoveOrInsert(true);
				board.setPlayerNumber(Pnum);
				gameFrame = new LabyrinthGameFrame(board);
				for (int i = 0; i < 12; i++) {
					gameFrame.getBoardPanel().getButtonArray()[i].addActionListener(this);
				}
				// Add action listeners for buttons on control panel.
				gameFrame.getControlPanel().getLeftButton().addActionListener(this);
				gameFrame.getControlPanel().getRightButton().addActionListener(this);
				gameFrame.getControlPanel().getEndButton().addActionListener(this);
				gameFrame.getControlPanel().getSaveButton().addActionListener(this);
				gameFrame.setFocusable(true);
				gameFrame.addKeyListener(this);
				gameStart = true;
				settingFrame.setVisible(false);

				// if a character is AI, activate AI movement.
				if (board.getPlayerList()[board.getCurrentPlayer()].getClass().getSimpleName().equals("Ai")) {
					aiMovement();
				}
			} else {
				JOptionPane.showMessageDialog(settingFrame, "No Human Player Selected");
			}
		}

		if (gameStart) {
			// If one of the button is clicked
			
			if (this.getGameFrame().getBoardPanel().getInsertButton(event) && board.isMoveOrInsert()) {
				// a int array to refer to the insert buttons.
				int[] tempArray = this.getGameFrame().getBoardPanel().getInsertButtonIndex(event);
				// insert is allowed if it is not at the last insert row/column
				if (tempArray[3] != opposite(board.getLastInsert())) {
					// call board's insertFreeTile
					board.insertFreeTile(tempArray[0], tempArray[1] == 1, tempArray[2] == 1);
					board.setLastInsert(tempArray[3]);
					board.setMoveOrInsert(false);
					board.updatePlayerLocation();
					// System.out.println(board.getCurrentPlayer());
					// Then call LabyrinthBoardPanel's repaintBoard
					gameFrame.getBoardPanel().repaintBoard(board);
					gameFrame.getControlPanel().updateTile(board);
					for (int i = 0; i < 12; i++) {
						gameFrame.getBoardPanel().getButtonArray()[i].addActionListener(this);
					}
					if (!board.getPlayerList()[board.getCurrentPlayer()].getClass().getSimpleName().equals("Ai"))
						gameFrame.getControlPanel().changeVisibility(true);
				} else {
					JOptionPane.showMessageDialog(gameFrame, "Cannot Reverse Insert");
				}
			}
			// rotate the free tile
			if (event.getSource() == this.getGameFrame().getControlPanel().getLeftButton()) {
				// if this button is clicked
				// change the direction of the freeTile.
				board.getFreeTile().rotateTile(false);
				gameFrame.getControlPanel().updateTile(board);
			}

			// rotate the free tile
			if (event.getSource() == this.getGameFrame().getControlPanel().getRightButton()) {
				// if this button is clicked
				// change the direction of the freeTile.
				board.getFreeTile().rotateTile(true);
				gameFrame.getControlPanel().updateTile(board);
			}

			// button for saving the ongoing game
			if (event.getSource() == this.getGameFrame().getControlPanel().getSaveButton()) {
				if (board.isMoveOrInsert() == false)
					saveGame("files/save.txt");
				else
					// the user is only allowed to save the game during their movement stage since
					// it is impossible to determine who's the currentPlayer between the stages of
					// two different players.
					JOptionPane.showMessageDialog(gameFrame,
							"You are only allowed to save during your movement stage.");
				//System.out.println("You are only allowed to save during your movement stage.");
			}

			// JButton EndButton for ending the movement stage.
			if (event.getSource() == this.getGameFrame().getControlPanel().getEndButton()) {
				gameFrame.getControlPanel().changeVisibility(false);
				// get the current player
				Player tempPlayer = this.getBoard().getPlayerList()[this.getBoard().getCurrentPlayer()];
				int x = tempPlayer.getX();
				int y = tempPlayer.getY();
				// And check if any treasure can be collected at this tile.
				for(Player tPlayer : board.getPlayerList()) {
					int num = 0;
					if(tPlayer == null) continue;
					for (Card tempCard : tPlayer.getTreasureCards()) {
						if (tempCard == null)
							break;
						// If there's a treasure
						// Check if this player needs this treasure.
						if (tempCard.getName().equals(board.getTiles()[tPlayer.getX()][tPlayer.getY()].getName())) {
							//Mark this treasure as collected.
							tempCard.setFind(true);
							tPlayer.getTreasureCards()[num] = tempCard;
							break;// break to increase efficiency
						}
						num++;
					}
				}
				this.gameFrame.getPlayerPanel().updatePlayer(board);
				// default value of win.
				boolean win = true;
				// For each player, any uncollected card will stop them from winning.
				for (Card tempCard : tempPlayer.getTreasureCards()) {
					if (tempCard == null)
						continue;
					if (tempCard.isFind() == false) {
						win = false;
						break;
					}
				}

				// 在检查玩家赢没赢，如果赢了游戏结束，如果没有则继续
				if (win) {
					// prompt winner
					JOptionPane.showMessageDialog(gameFrame, board.getPlayerName() + " Wins!");
					gameFrame.dispose();
					settingFrame.dispose();
					startFrame.dispose();
					helpFrame.dispose();
				}

				// gameFrame.getControlPanel().changeVisibility(false);
				board.setMoveOrInsert(true);

				int k = board.getCurrentPlayer() + 1;
				if (board.getCurrentPlayer() == 3)
					k = 0;

				// System.out.println("a");

				for (int i = k; i < 4; ++i) {
					// System.out.println(i);
					if (board.getPlayerList()[i] == null) {
						if (i == 3)
							i = -1;
						continue;
					} else {
						board.setCurrentPlayer(i);
						break;
					}
				}
				// System.out.println("b");
				board.updatePlayerLocation();
				gameFrame.getControlPanel().updatePlayer(board);
				//System.out.println("new Player " + board.getCurrentPlayer());

				if (board.getPlayerList()[board.getCurrentPlayer()].getClass().getSimpleName().equals("Ai")) {

//					try {
//						TimeUnit.SECONDS.sleep(3);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}

					aiMovement();
				}
				
				gameFrame.getBoardPanel().repaintBoard(board);
				for (int i = 0; i < 12; i++) {
					gameFrame.getBoardPanel().getButtonArray()[i].addActionListener(this);
				}

			}
			// 把board里的currentPlayer给改成下一个player(currentPlayer是我在board里新加的fields)

		}

	}

	private void aiMovement() {
		// Create a deep copy of the board to allow the AI to evaluate moves without modifying the current state
		Board copyBoard = new Board(board);
		// Get the current AI player and determine its move based on its type
		Ai tempAi = (Ai) board.getPlayerList()[board.getCurrentPlayer()];
		int[] aiMove = Ai.getAIAction(copyBoard, tempAi.getType());
		// Debugging output to log the AI's move details
		//System.out.println(aiMove[0] + " " + aiMove[1] + " " + aiMove[2] + " " + aiMove[3]);

		// Timer for AI's tile insertion action
		Timer t1 = new Timer(2000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Rotate the free tile to the direction specified by the AI move
				board.getFreeTile().setDirection(aiMove[1]);
				// Simulate the AI clicking the insert button at the specified index
				gameFrame.getBoardPanel().getInsertButtonArray()[aiMove[0]].doClick();
			}
		});
		t1.setRepeats(false); // Ensure the timer runs only once
		t1.start(); // Start the timer for the insertion action

		// Timer for AI's player movement action
		Timer t2 = new Timer(3000, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// Remove the current player from its current tile
				board.getTiles()[board.getPlayerList()[board.getCurrentPlayer()]
						.getX()][board.getPlayerList()[board.getCurrentPlayer()].getY()]
						.erasPlayer(board.getCurrentPlayer());
				// If the tile has no remaining players, set it as unoccupied
				if (board.getTiles()[board.getPlayerList()[board.getCurrentPlayer()]
						.getX()][board.getPlayerList()[board.getCurrentPlayer()].getY()].getPlayerList().size() == 0) {
					board.getTiles()[board.getPlayerList()[board.getCurrentPlayer()]
							.getX()][board.getPlayerList()[board.getCurrentPlayer()].getY()].setPlayer(false);
				}

				// Update the AI player's position to the new coordinates
				board.getPlayerList()[board.getCurrentPlayer()].setX(aiMove[2]);
				board.getPlayerList()[board.getCurrentPlayer()].setY(aiMove[3]);

				// Mark the new tile as occupied if it has no other players
				if (board.getTiles()[board.getPlayerList()[board.getCurrentPlayer()]
						.getX()][board.getPlayerList()[board.getCurrentPlayer()].getY()].getPlayerList().size() == 0) {
					board.getTiles()[board.getPlayerList()[board.getCurrentPlayer()]
							.getX()][board.getPlayerList()[board.getCurrentPlayer()].getY()].setPlayer(true);
				}
				// Add the current player to the new tile's player list
				board.getTiles()[board.getPlayerList()[board.getCurrentPlayer()]
						.getX()][board.getPlayerList()[board.getCurrentPlayer()].getY()]
						.addPlayerNumber(board.getCurrentPlayer());

				// Repaint the board to reflect the updated game state
				gameFrame.getBoardPanel().repaintBoard(board);
				for (int i = 0; i < 12; i++) {
					gameFrame.getBoardPanel().getButtonArray()[i].addActionListener(this);
				}
				// Simulate clicking the "End Turn" button to finalize the AI's move
				gameFrame.getControlPanel().getEndButton().doClick();
			}
		});
		t2.setRepeats(false); // Ensure the timer runs only once
		t2.start(); // Start the timer for the movement action

	}

private int opposite(int a) {
		// Determine the opposite direction of the given index
		if (a == 0) {
			return 9;
		} else if (a == 1) {
			return 10;
		} else if (a == 2) {
			return 11;
		} else if (a == 3) {
			return 4;
		} else if (a == 4) {
			return 3;
		} else if (a == 5) {
			return 6;
		} else if (a == 6) {
			return 5;
		} else if (a == 7) {
			return 8;
		} else if (a == 8) {
			return 7;
		} else if (a == 9) {
			return 0;
		} else if (a == 10) {
			return 1;
		} else if (a == 11) {
			return 2;
		} else {
			// Return -1 for invalid input
			return -1;
		}
	}

	// NOT USED - must be kept for the KeyListener
	public void keyTyped(KeyEvent e) {
		// nothing

	}

	@Override
	public void keyPressed(KeyEvent key) {
		
		// if movement keyboard is clicked, and if the player is allowed to move in this direction
		if (key.getKeyCode() == KeyEvent.VK_UP && !board.isMoveOrInsert()) {
			// create a temp player to access the fields easily.
			Player tempPlayer = board.getPlayerList()[board.getCurrentPlayer()];
			
			if (checkOut("U", board.getTiles()[tempPlayer.getX()][tempPlayer.getY()]) && tempPlayer.getY() != 0) {

				if (checkIn("U", board.getTiles()[tempPlayer.getX()][tempPlayer.getY() - 1])) {

					// check move in and move out
					board.getTiles()[board.getPlayerList()[board.getCurrentPlayer()]
							.getX()][board.getPlayerList()[board.getCurrentPlayer()].getY()]
							.erasPlayer(board.getCurrentPlayer());
					if (board.getTiles()[board.getPlayerList()[board.getCurrentPlayer()]
							.getX()][board.getPlayerList()[board.getCurrentPlayer()].getY()].getPlayerList()
							.isEmpty()) {
						board.getTiles()[board.getPlayerList()[board.getCurrentPlayer()]
								.getX()][board.getPlayerList()[board.getCurrentPlayer()].getY()].setPlayer(false);
					}

					int newY = (tempPlayer.getY() - 1);
					board.getPlayerList()[board.getCurrentPlayer()].setY(newY);
					board.getTiles()[board.getPlayerList()[board.getCurrentPlayer()]
							.getX()][board.getPlayerList()[board.getCurrentPlayer()].getY()].setPlayer(true);
					board.getTiles()[board.getPlayerList()[board.getCurrentPlayer()]
							.getX()][board.getPlayerList()[board.getCurrentPlayer()].getY()]
							.addPlayerNumber(board.getCurrentPlayer());


					// refresh the current position of the player
					gameFrame.getBoardPanel().repaintBoard(board);
					for (int i = 0; i < 12; i++) {
						gameFrame.getBoardPanel().getButtonArray()[i].addActionListener(this);
					}
				}
			}
		}
		
		// if movement keyboard is clicked, and if the player is allowed to move in this direction
		else if (key.getKeyCode() == KeyEvent.VK_LEFT && !board.isMoveOrInsert()) {
			Player tempPlayer = board.getPlayerList()[board.getCurrentPlayer()];
	
			if (checkOut("L", board.getTiles()[tempPlayer.getX()][tempPlayer.getY()]) && tempPlayer.getX() != 0) {
				if (checkIn("L", board.getTiles()[tempPlayer.getX() - 1][tempPlayer.getY()])) {

					// check move in and move out
					board.getTiles()[board.getPlayerList()[board.getCurrentPlayer()]
							.getX()][board.getPlayerList()[board.getCurrentPlayer()].getY()]
							.erasPlayer(board.getCurrentPlayer());
					if (board.getTiles()[board.getPlayerList()[board.getCurrentPlayer()]
							.getX()][board.getPlayerList()[board.getCurrentPlayer()].getY()].getPlayerList()
							.isEmpty()) {
						board.getTiles()[board.getPlayerList()[board.getCurrentPlayer()]
								.getX()][board.getPlayerList()[board.getCurrentPlayer()].getY()].setPlayer(false);
					}

					int newX = (tempPlayer.getX() - 1);
					board.getPlayerList()[board.getCurrentPlayer()].setX(newX);
					board.getTiles()[board.getPlayerList()[board.getCurrentPlayer()]
							.getX()][board.getPlayerList()[board.getCurrentPlayer()].getY()].setPlayer(true);
					board.getTiles()[board.getPlayerList()[board.getCurrentPlayer()]
							.getX()][board.getPlayerList()[board.getCurrentPlayer()].getY()]
							.addPlayerNumber(board.getCurrentPlayer());

					// refresh the current position of the player
					gameFrame.getBoardPanel().repaintBoard(board);
					for (int i = 0; i < 12; i++) {
						gameFrame.getBoardPanel().getButtonArray()[i].addActionListener(this);
					}
				}
			}
		}
		
		// if movement keyboard is clicked, and if the player is allowed to move in this direction
		else if (key.getKeyCode() == KeyEvent.VK_DOWN && !board.isMoveOrInsert()) {
			Player tempPlayer = board.getPlayerList()[board.getCurrentPlayer()];
			
			if (checkOut("D", board.getTiles()[tempPlayer.getX()][tempPlayer.getY()]) && tempPlayer.getY() != 6) {


				if (checkIn("D", board.getTiles()[tempPlayer.getX()][tempPlayer.getY() + 1])) {
					// check move in and move out
					board.getTiles()[board.getPlayerList()[board.getCurrentPlayer()]
							.getX()][board.getPlayerList()[board.getCurrentPlayer()].getY()]
							.erasPlayer(board.getCurrentPlayer());
					if (board.getTiles()[board.getPlayerList()[board.getCurrentPlayer()]
							.getX()][board.getPlayerList()[board.getCurrentPlayer()].getY()].getPlayerList()
							.isEmpty()) {
						board.getTiles()[board.getPlayerList()[board.getCurrentPlayer()]
								.getX()][board.getPlayerList()[board.getCurrentPlayer()].getY()].setPlayer(false);
					}

					int newY = (tempPlayer.getY() + 1);
					board.getPlayerList()[board.getCurrentPlayer()].setY(newY);
					board.getTiles()[board.getPlayerList()[board.getCurrentPlayer()]
							.getX()][board.getPlayerList()[board.getCurrentPlayer()].getY()].setPlayer(true);
					board.getTiles()[board.getPlayerList()[board.getCurrentPlayer()]
							.getX()][board.getPlayerList()[board.getCurrentPlayer()].getY()]
							.addPlayerNumber(board.getCurrentPlayer());

					// refresh the current position of the player
					gameFrame.getBoardPanel().repaintBoard(board);
					for (int i = 0; i < 12; i++) {
						gameFrame.getBoardPanel().getButtonArray()[i].addActionListener(this);
					}
				}
			}
		}
		
		// if movement keyboard is clicked, and if the player is allowed to move in this direction
		else if (key.getKeyCode() == KeyEvent.VK_RIGHT && !board.isMoveOrInsert()) {
			
			Player tempPlayer = board.getPlayerList()[board.getCurrentPlayer()];
			// check move in and move out
			if (checkOut("R", board.getTiles()[tempPlayer.getX()][tempPlayer.getY()]) && tempPlayer.getX() != 6) {

				if (checkIn("R", board.getTiles()[tempPlayer.getX() + 1][tempPlayer.getY()])) {

					board.getTiles()[board.getPlayerList()[board.getCurrentPlayer()]
							.getX()][board.getPlayerList()[board.getCurrentPlayer()].getY()]
							.erasPlayer(board.getCurrentPlayer());
					if (board.getTiles()[board.getPlayerList()[board.getCurrentPlayer()]
							.getX()][board.getPlayerList()[board.getCurrentPlayer()].getY()].getPlayerList()
							.isEmpty()) {
						board.getTiles()[board.getPlayerList()[board.getCurrentPlayer()]
								.getX()][board.getPlayerList()[board.getCurrentPlayer()].getY()].setPlayer(false);
					}

					int newX = (tempPlayer.getX() + 1);
					board.getPlayerList()[board.getCurrentPlayer()].setX(newX);
					board.getTiles()[board.getPlayerList()[board.getCurrentPlayer()]
							.getX()][board.getPlayerList()[board.getCurrentPlayer()].getY()].setPlayer(true);
					board.getTiles()[board.getPlayerList()[board.getCurrentPlayer()]
							.getX()][board.getPlayerList()[board.getCurrentPlayer()].getY()]
							.addPlayerNumber(board.getCurrentPlayer());
					// refresh the current position of the player
					gameFrame.getBoardPanel().repaintBoard(board);
					for (int i = 0; i < 12; i++) {
						gameFrame.getBoardPanel().getButtonArray()[i].addActionListener(this);
					}
				}
			}
		}

	}

	private boolean checkIn(String direction, Tile tile) {

		// there are multiple valid tiles
		switch (direction) {

		// Reverse// if the player wants to move up from this tile
		case ("D"):
			// I0 and I2 valid
			if (tile.getShape().equals("I") && tile.getDirection() % 2 == 0)
				return true;
			// L0 and L3 valid
			if (tile.getShape().equals("L") && tile.getDirection() % 3 == 0)
				return true;
			// T123 valid
			if (tile.getShape().equals("T") && tile.getDirection() != 0)
				return true;
			break;
		// Reverse// if the player wants to move left from this tile
		case ("R"):
			// I1 and I3 valid
			if (tile.getShape().equals("I") && tile.getDirection() % 2 == 1)
				return true;
			// L2 and L3 valid
			if (tile.getShape().equals("L") && tile.getDirection() >= 2)
				return true;
			// T012 valid
			if (tile.getShape().equals("T") && tile.getDirection() < 3)
				return true;
			break;
		// Reverse// if the player wants to move down from this tile
		case ("U"):
			// I0 and I2 valid
			if (tile.getShape().equals("I") && tile.getDirection() % 2 == 0)
				return true;
			// L1 and L2 valid
			if (tile.getShape().equals("L") && tile.getDirection() <= 2 && tile.getDirection() >= 1)
				return true;
			// T013 valid
			if (tile.getShape().equals("T") && tile.getDirection() != 2)
				return true;
			break;
		// Reverse// if the player wants to move right from this tile
		case ("L"):
			// I1 and I3 valid
			if (tile.getShape().equals("I") && tile.getDirection() % 2 == 1)
				return true;
			// L0 and L1 valid
			if (tile.getShape().equals("L") && tile.getDirection() <= 1)
				return true;
			// T013 valid
			if (tile.getShape().equals("T") && tile.getDirection() != 1)
				return true;
			break;
		}
		return false;
	}

	// check if the player is allowed to move out of their current tile
	private boolean checkOut(String direction, Tile tile) {
		// there are multiple valid tiles
		switch (direction) {

		// if the player wants to move up from this tile
		case ("U"):
			// I0 and I2 valid
			if (tile.getShape().equals("I") && tile.getDirection() % 2 == 0)
				return true;
			// L0 and L3 valid
			if (tile.getShape().equals("L") && tile.getDirection() % 3 == 0)
				return true;
			// T123 valid
			if (tile.getShape().equals("T") && tile.getDirection() != 0)
				return true;
			break;

		// if the player wants to move left from this tile
		case ("L"):
			// I1 and I3 valid
			if (tile.getShape().equals("I") && tile.getDirection() % 2 == 1)
				return true;
			// L2 and L3 valid
			if (tile.getShape().equals("L") && tile.getDirection() >= 2)
				return true;
			// T012 valid
			if (tile.getShape().equals("T") && tile.getDirection() < 3)
				return true;
			break;

		// if the player wants to move down from this tile
		case ("D"):
			// I0 and I2 valid
			if (tile.getShape().equals("I") && tile.getDirection() % 2 == 0)
				return true;
			// L1 and L2 valid
			if (tile.getShape().equals("L") && tile.getDirection() <= 2 && tile.getDirection() >= 1)
				return true;
			// T013 valid
			if (tile.getShape().equals("T") && tile.getDirection() != 2)
				return true;
			break;
		// if the player wants to move right from this tile
		case ("R"):
			// I1 and I3 valid
			if (tile.getShape().equals("I") && tile.getDirection() % 2 == 1)
				return true;
			// L0 and L1 valid
			if (tile.getShape().equals("L") && tile.getDirection() <= 1)
				return true;
			// T013 valid
			if (tile.getShape().equals("T") && tile.getDirection() != 1)
				return true;
			break;
		}
		return false;
	}
	
	// method for loading the game
	public void loadGame(String fileName) {
		try (Scanner scanner = new Scanner(new File(fileName))) {
			
			// default values for each fields.
			Tile[][] tiles = new Tile[7][7];
			Player[] players = new Player[4];
			Tile freeTile = null;
			int currentPlayer = 0;
			boolean moveOrInsert = true;
			int lastInsert = 0;
			int[] theCardOfPlayers = { 0, 0, 0, 0 };
			
			//input from the existing text file.
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				String[] parts = line.split(",");
				switch (parts[0]) {
				
				// read  players
				case "Player":
					String playerType = parts[1];
					int playerNumber = Integer.parseInt(parts[2]);
					int x = Integer.parseInt(parts[3]);
					int y = Integer.parseInt(parts[4]);
					int numOfCards = Integer.parseInt(parts[5]);

					ImageIcon avatar = new ImageIcon("image/avatar" + playerNumber + ".jpg");
					if (playerType.equals("AI")) {
						String aiType = parts[6];
						players[playerNumber] = new Ai(playerNumber, avatar, numOfCards, aiType);
					} else {
						players[playerNumber] = new Player(playerNumber, avatar, numOfCards);
					}
					players[playerNumber].setX(x);
					players[playerNumber].setY(y);
					break;
				
					//// read cards
				case "Card":
					String cardName = parts[1];
					boolean isFound = Boolean.parseBoolean(parts[2]);
					int owner = Integer.parseInt(parts[3]);
					ImageIcon cardImage = new ImageIcon("image/Card" + cardName + ".png");
					Card card = new Card(cardImage, cardName, owner, isFound);
					players[owner].getTreasureCards()[theCardOfPlayers[owner]] = card;
					theCardOfPlayers[owner]++;
					break;
					// read tiles
				case "Tile":
					String name = parts[1];
					String shape = parts[2];
					int direction = Integer.parseInt(parts[3]);
					boolean fixed = Boolean.parseBoolean(parts[4]);
					int tileX = Integer.parseInt(parts[5]);
					int tileY = Integer.parseInt(parts[6]);
					tiles[tileX][tileY] = MazeController.combineFields(name, shape, direction, fixed, tileX, tileY);
					break;
					
					// read  free tile
				case "FreeTile":
					name = parts[1];
					shape = parts[2];
					direction = Integer.parseInt(parts[3]);
					fixed = Boolean.parseBoolean(parts[4]);
					int freeTileX = Integer.parseInt(parts[5]);
					int freeTileY = Integer.parseInt(parts[6]);
					freeTile = MazeController.combineFields(name, shape, direction, fixed, freeTileX, freeTileY);
					break;
					
					// read  game state
				case "GameState":
					currentPlayer = Integer.parseInt(parts[1]);
					moveOrInsert = Boolean.parseBoolean(parts[2]);
					lastInsert = Integer.parseInt(parts[3]);
					//System.out.printf("According to the saved file, the current player is %d", currentPlayer);
					break;
				}
			}
			// check for playerNumber
			int numOfPlayers = 0;
			
			// count the chose players, ignoring null players
			for (Player x : players) {
				if (x != null)
					numOfPlayers++;
			}
			
			for (Player x : players) {
				if (x == null)
					continue;
				//System.out.println(x);
				tiles[x.getX()][x.getY()].setPlayer(true);
				tiles[x.getX()][x.getY()].addPlayerNumber(x.getPlayerNumber());
			}
			// initiate the board with info loaded from the text file.
			board = new Board(tiles, freeTile, players, currentPlayer, moveOrInsert, numOfPlayers, lastInsert);
			//System.out.println(board.getCurrentPlayer());
			// Update the board with the loaded state.
			// Update the game frame
			gameFrame = new LabyrinthGameFrame(board);
			for (int i = 0; i < 12; i++) {
				gameFrame.getBoardPanel().getButtonArray()[i].addActionListener(this);
			}
			gameFrame.getControlPanel().getLeftButton().addActionListener(this);
			gameFrame.getControlPanel().getRightButton().addActionListener(this);
			gameFrame.getControlPanel().getEndButton().addActionListener(this);
			gameFrame.getControlPanel().getSaveButton().addActionListener(this);
			gameFrame.setFocusable(true);
			gameFrame.addKeyListener(this);
			gameStart = true;
			settingFrame.setVisible(false);// add actionListeners and switch the type of the gameFrame.
			
			// allow the user to use the controlPanel.
			gameFrame.getControlPanel().changeVisibility(true);
			
			
			// AI Movement method
			if (board.getPlayerList()[board.getCurrentPlayer()].getClass().getSimpleName().equals("Ai")) {
				aiMovement();
			}

			JOptionPane.showMessageDialog(gameFrame, "Game Loaded Successfully! Enjoy your game!");
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(gameFrame, "There isn't any saved game.");
			System.err.println("Save file not found: " + e.getMessage());
		} catch (Exception e) {
			JOptionPane.showMessageDialog(gameFrame, "Error loading game.");
			System.err.println("Error loading game: " + e.getMessage());
		}
	}
	// method for saving the current game
	public void saveGame(String fileName) {
		
		File saveFile = new File(fileName);
		try {
			if (!saveFile.exists() && !saveFile.createNewFile()) {
				System.out.println("Failed to create save file.");
				return;
			}

			try (PrintWriter writer = new PrintWriter(saveFile)) {
				// Save players
				Player[] players = board.getPlayerList();
				for (int i = 0; i < players.length; i++) {
					Player player = players[i];
					if (player == null) {
						continue;
					} else if (player instanceof Ai) {
						Ai aiPlayer = (Ai) player;
						writer.printf("Player,AI,%d,%d,%d,%d,%s\n", aiPlayer.getPlayerNumber(), aiPlayer.getX(),
								aiPlayer.getY(), aiPlayer.getNumOfCards(), aiPlayer.getType());
					} else {
						writer.printf("Player,Human,%d,%d,%d,%d\n", player.getPlayerNumber(), player.getX(),
								player.getY(), player.getNumOfCards());
					}

					for (Card card : player.getTreasureCards()) {
						if (card != null) {
							writer.printf("Card,%s,%b,%d\n", card.getName(), card.isFind(), player.getPlayerNumber());
						}
					}
				}

				// Save board tiles
				Tile[][] tiles = board.getTiles();
				for (Tile[] row : tiles) {
					for (Tile tile : row) {
						writer.printf("Tile,%s,%s,%d,%b,%d,%d\n", tile.getName(), tile.getShape(), tile.getDirection(),
								tile.isFixed(), tile.getX(), tile.getY());
					}
				}

				// Save free tile
				Tile freeTile = board.getFreeTile();
				writer.printf("FreeTile,%s,%s,%d,%b,%d,%d\n", freeTile.getName(), freeTile.getShape(),
						freeTile.getDirection(), freeTile.isFixed(), freeTile.getX(), freeTile.getY());

				// Save game state
				writer.printf("GameState,%d,%b,%d\n", board.getCurrentPlayer(), board.isMoveOrInsert(),
						board.getLastInsert());

				JOptionPane.showMessageDialog(gameFrame, "Game Saved Successfully! Enjoy your game!");
			}
		} catch (IOException e) {
			System.err.println("Error saving game: " + e.getMessage());
			JOptionPane.showMessageDialog(gameFrame, "Error saving game.");
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}


}