package model;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;

/*
 * AI Class By Andy Feng 
 * 
 * Individual Additional Feature
 * 
 * A subclass of player containing different method that helps to make decisions 
 * for the AI in the game. 
 * the class have an extra field of type to specify which type of AI this is, and 
 * return movement of the AI to the game. 
 */

public class Ai extends Player {

	// fields for the object
	private String Type;

	// variable to iterate through each direction
	private static int[][] direction = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
	private static String[] directionW = { "D", "R", "U", "L" };

	// constructor
	public Ai(int playerNumber, ImageIcon avatarImage, int numOfCards, String type) {
		super(playerNumber, avatarImage, numOfCards);
		this.Type = type;
	}

	// method to get the action of the AI in each round
	// take input of copyBoard and type string
	public static int[] getAIAction(Board copyBoard, String type) {
		// the integer array containing the move information
		// ret[0] is the where to insert
		// ret[1] is the direction of free tile
		// ret[2, 3] is the coordinate of AI's destination
		int[] ret = new int[4];

		// have temporary variable for the AI
		Player tempAi = copyBoard.getPlayerList()[copyBoard.getCurrentPlayer()];
		// if the AI is random
		if (type.equals("Random Bot")) {
			// create a random object
			Random rando = new Random();
			// use the object to get Random action
			ret[0] = rando.nextInt(12);
			ret[1] = rando.nextInt(4);

			// change the board based on the random action
			copyBoard.getFreeTile().setDirection(ret[1]);
			int[] insertInfo = getInsertButtonIndex(ret[0]);
			copyBoard.insertFreeTile(insertInfo[0], insertInfo[1] == 1, insertInfo[2] == 1);
			copyBoard.setLastInsert(insertInfo[3]);
			copyBoard.updatePlayerLocation();

			// get all the possible Tile for AI to move to
			ArrayList<Tile> potentialMove = dfs(copyBoard, tempAi.getX(), tempAi.getY(), tempAi.getPlayerNumber());
			// get a random Tile from the ArrayList
			Tile target = potentialMove.get(rando.nextInt(potentialMove.size()));
			// set it in the return array
			ret[2] = target.getX();
			ret[3] = target.getY();

		}
		// if the AI is smart - consider both
		// the treasure of itself and preventing
		// Opponent from getting theirs
		else if (type.equals("Smart Bot")) {
			// get the movement from the method
			ret = getSmartAction(copyBoard);
		}
		// if the AI is average - only consider
		// the treasure of the AI
		else if (type.equals("Average Bot")) {
			// get the movement from the method
			ret = getAverageAction(copyBoard);
		}
		// if the AI is sabotage - it will prioritize
		// preventing opponent from getting their
		// treasure
		else if (type.equals("Sabotage Bot")) {
			// get the movement from the method
			ret = getSabotageAction(copyBoard);
		}
		// return the movement info
		return ret;
	}

	// get the sabotage AI Action take board as input
	// and return the movement info
	public static int[] getSabotageAction(Board board) {
		// the movement info to return
		int[] ret = new int[4];

		// keep track of the smallest value
		int minScore = Integer.MAX_VALUE;

		// iterate through all possible insert
		for (int i = 0; i < 12; ++i) {

			// check it insert is possible
			if (i == opposite(board.getLastInsert()))
				continue;
			int[] insertInfo = getInsertButtonIndex(i);

			// iterate through the possible direction of the free tile
			for (int j = 0; j < 4; ++j) {
				// create a new board
				Board copyBoard = new Board(board);
				// insert the free tile
				copyBoard.getFreeTile().setDirection(j);
				copyBoard.insertFreeTile(insertInfo[0], insertInfo[1] == 1, insertInfo[2] == 1);
				copyBoard.setLastInsert(insertInfo[3]);
				copyBoard.updatePlayerLocation();

				// construct random
				Random rando = new Random();

				// move randomly on the board
				ArrayList<Tile> potentialMove = dfs(copyBoard,
						copyBoard.getPlayerList()[copyBoard.getCurrentPlayer()].getX(),
						copyBoard.getPlayerList()[copyBoard.getCurrentPlayer()].getY(), copyBoard.getCurrentPlayer());
				Tile target = potentialMove.get(rando.nextInt(potentialMove.size()));

				// keep track the maximum score a the next opponent can get
				int maxV = Integer.MIN_VALUE;

				// iterate through all possible insert of opponent
				for (int k = 0; k < 12; ++k) {
					if (k == opposite(copyBoard.getLastInsert()))
						continue;
					int[] humaninsertInfo = getInsertButtonIndex(k);
					for (int n = 0; n < 4; ++n) {

						// create another copy of the board
						Board tempBoard = new Board(copyBoard);
						tempBoard.setCurrentPlayer(getNextPlayer(tempBoard));

						// insert the tile into the board
						tempBoard.getFreeTile().setDirection(n);
						tempBoard.insertFreeTile(humaninsertInfo[0], humaninsertInfo[1] == 1, humaninsertInfo[2] == 1);
						tempBoard.updatePlayerLocation();

						// get the score of the move
						int[] humanMove = calculateMaxScore(tempBoard, false);
						// Maximize the score
						maxV = Math.max(maxV, humanMove[0]);
					}
				}

				// find the smallest score
				if (maxV < minScore) {
					// minimize the score
					minScore = maxV;

					// set the movement info
					ret[0] = i;
					ret[1] = j;
					ret[2] = target.getX();
					ret[3] = target.getY();
				}
			}

		}

		// return the movement info
		return ret;
	}

	// get the average AI Action take board as input
	// and return the movement info
	public static int[] getAverageAction(Board board) {
		// the movement info to return
		int[] ret = new int[4];

		// ensure the maximum score
		int maxScore = Integer.MIN_VALUE;

		// iterate through all possible insert
		for (int i = 0; i < 12; ++i) {

			// check it insert is possible
			if (i == opposite(board.getLastInsert()))
				continue;
			int[] insertInfo = getInsertButtonIndex(i);

			// iterate through the possible direction of the free tile
			for (int j = 0; j < 4; ++j) {
				// create a new board
				Board copyBoard = new Board(board);
				// insert the free tile
				copyBoard.getFreeTile().setDirection(j);
				copyBoard.insertFreeTile(insertInfo[0], insertInfo[1] == 1, insertInfo[2] == 1);
				copyBoard.setLastInsert(insertInfo[3]);
				copyBoard.updatePlayerLocation();

				// calculate the maximum score of the player
				int[] AiMove = calculateMaxScore(copyBoard, true);

				// Maximize score
				if (AiMove[0] > maxScore) {
					maxScore = AiMove[0];

					// set the return info
					ret[0] = i;
					ret[1] = j;
					ret[2] = AiMove[1];
					ret[3] = AiMove[2];
				}
			}

		}

		// return the movement info
		return ret;
	}

	// get the Smart AI Action take board as input
	// and return the movement info
	public static int[] getSmartAction(Board board) {
		// the movement info to return
		int[] ret = new int[4];

		// ensure the maximum score
		int maxScore = Integer.MIN_VALUE;

		// iterate through all possible insert
		for (int i = 0; i < 12; ++i) {

			// check it insert is possible
			if (i == opposite(board.getLastInsert()))
				continue;
			int[] insertInfo = getInsertButtonIndex(i);

			// iterate through the possible direction of the free tile
			for (int j = 0; j < 4; ++j) {
				// create a new board
				Board copyBoard = new Board(board);
				// insert the free tile
				copyBoard.getFreeTile().setDirection(j);
				copyBoard.insertFreeTile(insertInfo[0], insertInfo[1] == 1, insertInfo[2] == 1);
				copyBoard.setLastInsert(insertInfo[3]);
				copyBoard.updatePlayerLocation();

				// get the max score for the AI
				int[] AiMove = calculateMaxScore(copyBoard, true);

				// maximize the score for the next opponent
				int maxV = Integer.MIN_VALUE;

				// iterate through all possible insert of opponent
				for (int k = 0; k < 12; ++k) {
					if (k == opposite(copyBoard.getLastInsert()))
						continue;
					int[] humaninsertInfo = getInsertButtonIndex(k);
					for (int n = 0; n < 4; ++n) {

						// create another copy of the board
						Board tempBoard = new Board(copyBoard);
						tempBoard.setCurrentPlayer(getNextPlayer(tempBoard));

						// insert the tile into the board
						tempBoard.getFreeTile().setDirection(n);
						tempBoard.insertFreeTile(humaninsertInfo[0], humaninsertInfo[1] == 1, humaninsertInfo[2] == 1);
						tempBoard.updatePlayerLocation();

						// get the score of the move
						int[] humanMove = calculateMaxScore(tempBoard, false);
						// Maximize the score
						maxV = Math.max(maxV, humanMove[0]);
					}
				}

				// ensure the differences between the AI and the next player
				// is maximize
				if ((AiMove[0] - maxV) > maxScore) {
					maxScore = AiMove[0] - maxV;

					// set the move info
					ret[0] = i;
					ret[1] = j;
					ret[2] = AiMove[1];
					ret[3] = AiMove[2];
				}
			}

		}

		// return the movement info
		return ret;
	}

	// get the max score for a player, take in board and boolean as input
	// return the score and target coordinate
	public static int[] calculateMaxScore(Board copyBoard, boolean AiOrHuman) {
		// array containing the movement info and score
		int[] move = new int[3];
		// score for each of the tile
		int[][] tileValue = new int[7][7];
		for (int i = 0; i < 7; ++i)
			for (int j = 0; j < 7; ++j)
				tileValue[i][j] = 0;

		// iterate through all the tiles
		for (int i = 0; i < 7; ++i) {
			for (int j = 0; j < 7; ++j) {
				// set it to be not visited for the dfs later
				copyBoard.getTiles()[i][j].setVisitied(false);

				// if it is fixed is worth more
				if (copyBoard.getTiles()[i][j].isFixed())
					tileValue[i][j] += 1;

				// if it is a T shape is worth more than other
				if (copyBoard.getTiles()[i][j].getShape().equals("T"))
					tileValue[i][j] += 2;

				// check if it contain a needed treasure
				if (checkNeededTreasure(copyBoard, i, j)) {
					// prioritize non fixed tile because can be move
					// out of the way
					if (!copyBoard.getTiles()[i][j].isFixed()) {
						// is calculating the Current or Opponent
						if (AiOrHuman) {
							if (getCardsLeft(copyBoard) == 1)// last treasure worth more
								tileValue[i][j] += 20000;
							else
								tileValue[i][j] += 1000;
						} else {
							if (getCardsLeft(copyBoard) == 1)// last treasure worth more
								tileValue[i][j] += 10000;
							else {
								// check whether the game have more than two player or not
								if (!(copyBoard.getPlayerNumber() == 2))
									tileValue[i][j] += 10;
								// the opponent piece is worth more if only 2 player
								else
									tileValue[i][j] += 10000;
							}
						}
					} else {
						// is calculating the Current or Opponent
						if (AiOrHuman) {
							if (getCardsLeft(copyBoard) == 1)// last treasure worth more
								tileValue[i][j] += 20000;
							else
								tileValue[i][j] += 1100;
						} else {
							if (getCardsLeft(copyBoard) == 1)// last treasure worth more
								tileValue[i][j] += 10000;
							else {
								// check whether the game have more than two player or not
								if (!(copyBoard.getPlayerNumber() == 2))
									tileValue[i][j] += 10;
								// the opponent piece is worth more if only 2 player
								else
									tileValue[i][j] += 10000;
							}
						}
					}
				} else {
					// get all the needed tile
					ArrayList<Tile> needed = getNeededTreasure(copyBoard);

					// if it is closer to the needed tile is score is higher
					for (Tile temp : needed) {
						tileValue[i][j] -= calDistance(copyBoard, temp);
					}

				}
			}
		}

		// get all accessible tile
		ArrayList<Tile> possibleMove = dfs(copyBoard, copyBoard.getPlayerList()[copyBoard.getCurrentPlayer()].getX(),
				copyBoard.getPlayerList()[copyBoard.getCurrentPlayer()].getY(), copyBoard.getCurrentPlayer());

		// if(AiOrHuman)System.out.println(possibleMove);
		// ensure the largest value of the move
		move[0] = Integer.MIN_VALUE;
		move[1] = -1;
		move[2] = -1;
		// iterate through all the possible move to find the move
		// that give us the highest score tile
		for (Tile temp : possibleMove) {
			// if(AiOrHuman)System.out.println("*" + temp.getX() + " " + temp.getY());
			// if the tile value is higher
			if (tileValue[temp.getX()][temp.getY()] > move[0]) {
				// change the score and the target coordinate
				move[0] = tileValue[temp.getX()][temp.getY()];
				move[1] = temp.getX();
				move[2] = temp.getY();
			}
		}

		// return the movement and the score
		return move;
	}

	// use dfs to search for all accessible tiles
	// take board current coordinate and player number
	// return all accessible tiles as a arrayList
	public static ArrayList<Tile> dfs(Board board, int curX, int curY, int playerNumber) {
		// Array list to return
		ArrayList<Tile> ret = new ArrayList<Tile>();
		// add the current tile into it
		ret.add(board.getTiles()[curX][curY]);
		// set this tiles to be visited already
		board.getTiles()[curX][curY].setVisitied(true);

		// iterate through all 4 direction
		for (int i = 0; i < 4; ++i) {
			// check whether the movement is possible
			if (checkOut(directionW[i], board.getTiles()[curX][curY]) && curX + direction[i][0] <= 6
					&& curX + direction[i][0] >= 0 && curY + direction[i][1] <= 6 && curY + direction[i][1] >= 0) {
				if (checkIn(directionW[i], board.getTiles()[curX + direction[i][0]][curY + direction[i][1]])
						&& !board.getTiles()[curX + direction[i][0]][curY + direction[i][1]].isVisitied()) {
					// System.out.println("#" + curX + " " + curY);
					// if so continue the search by recursion
					ret.addAll(dfs(board, curX + direction[i][0], curY + direction[i][1], playerNumber));

				}
			}
		}

		// return all the possible tile
		return ret;
	}

	// return true of false if the tile is needed for the player
	// take in the coordinate and the board
	private static boolean checkNeededTreasure(Board board, int a, int b) {
		// iterate through the treasure list of the current player
		for (int j = 0; j < board.getPlayerList()[board.getCurrentPlayer()].getNumOfCards(); ++j)
			if (board.getTiles()[a][b].getName()
					.equals(board.getPlayerList()[board.getCurrentPlayer()].getTreasureCards()[j].getName())
					&& !board.getPlayerList()[board.getCurrentPlayer()].getTreasureCards()[j].isFind())
				// if it is in the list and not found yet return true
				return true;

		// other wise return false
		return false;
	}

	// get all tile needed for the current player
	// take board as input and return the needed treasure
	// in a array list of tile
	private static ArrayList<Tile> getNeededTreasure(Board board) {
		// Array List to return
		ArrayList<Tile> ret = new ArrayList<Tile>();
		// iterate through all the tiles
		for (int a = 0; a < 7; ++a) {
			for (int b = 0; b < 7; ++b) {
				// iterate though the treasure list
				for (int j = 0; j < board.getPlayerList()[board.getCurrentPlayer()].getNumOfCards(); ++j)
					if (board.getTiles()[a][b].getName()
							.equals(board.getPlayerList()[board.getCurrentPlayer()].getTreasureCards()[j].getName())
							&& !board.getPlayerList()[board.getCurrentPlayer()].getTreasureCards()[j].isFind())
						// if it is in the list and not found yet add it to the array list
						ret.add(board.getTiles()[a][b]);
			}
		}
		// return all the needed tile
		return ret;
	}

	// get the distance between the current player and a tile
	// take board and the target tile as input
	private static int calDistance(Board board, Tile t) {
		// a temporary variable for the current player
		Player tempP = board.getPlayerList()[board.getCurrentPlayer()];
		// return the distance as an integer
		return (int) Math.round(Math.sqrt(Math.pow(tempP.getX() - t.getX(), 2) + Math.pow(tempP.getY() - t.getY(), 2)));
	}

	// return the next player in the game
	// take the board as input 
	private static int getNextPlayer(Board board) {
		// k is the starting point for the iteration
		int k = board.getCurrentPlayer() + 1;
		if (board.getCurrentPlayer() == 3)
			k = 0;

		// iterate through the player list starting at k
		for (int i = k; i < 4; ++i) {
			// System.out.println(i);
			// if this player is null move on to next
			if (board.getPlayerList()[i] == null) {
				// if it at the end circle back to the start
				if (i == 3)
					i = -1;
				continue;
			} else {
				// if not null return the iterator
				return i;
			}
		}
		// if not find return -1
		return -1;
	}

	// get the number of cards left to find for the current player
	private static int getCardsLeft(Board board) {
		// set up a variable for the current player
		Player curP = board.getPlayerList()[board.getCurrentPlayer()];
		int sum = 0;

		// iterate through the card list
		for (Card temp : curP.getTreasureCards()) {
			// if it is empty move on
			if (temp == null)
				continue;
			// if it is not find increment the sum
			if (!temp.isFind())
				sum++;
		}
		// return the sum
		return sum;
	}

	// get the opposite index of the insert button
	private static int opposite(int a) {
		// each insert button have an opposite as shown below
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
			// if out of bound return -1
			return -1;
		}
	}

	// check if the player can enter the tile
	private static boolean checkIn(String direction, Tile tile) {

		// System.out.println("HERE");

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
	private static boolean checkOut(String direction, Tile tile) {
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

	// get the specify information for insert with the index of the button
	public static int[] getInsertButtonIndex(int index) {
		// integer array to return it
		int[] ret = new int[4];

		// third is the index of the button
		ret[3] = index;

		// if it is on a column
		if ((index >= 0 && index <= 2) || (index >= 9 && index <= 11)) {
			ret[1] = 0;
			// Determine which column it is on
			if (index == 0 || index == 9) {
				ret[0] = 1;
				// determine which side it is on
				if (index == 0)
					ret[2] = 1;
				else
					ret[2] = 0;
			} else if (index == 1 || index == 10) {
				ret[0] = 3;
				// determine which side it is on
				if (index == 1)
					ret[2] = 1;
				else
					ret[2] = 0;
			} else {
				ret[0] = 5;
				// determine which side it is on
				if (index == 2)
					ret[2] = 1;
				else
					ret[2] = 0;
			}
		}
		// if is on a row
		else {
			ret[1] = 1;
			// determine which row it is on
			if (index == 3 || index == 4) {
				ret[0] = 1;
				// determine which side it is on
				if (index == 3)
					ret[2] = 1;
				else
					ret[2] = 0;
			} else if (index == 5 || index == 6) {
				ret[0] = 3;
				// determine which side it is on
				if (index == 5)
					ret[2] = 1;
				else
					ret[2] = 0;
			} else {
				ret[0] = 5;
				// determine which side it is on
				if (index == 7)
					ret[2] = 1;
				else
					ret[2] = 0;
			}
		}

		return ret;
	}

	// getters and setters
	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

}