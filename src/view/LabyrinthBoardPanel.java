package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.*;

import model.Ai;
import model.Board;
import model.Tile;

/*
 * LabyrinthBoardPanel:
 * Andy Feng 85% Bruce Yin 15%
 * 
 * This class include Bruce's highlight all possible pathway codes
 * 
 * This class create the JPanel containing the game Board 
 * 
 */

public class LabyrinthBoardPanel extends JPanel {

	// fields for the board panel
	private JLayeredPane[][] GameBoard = new JLayeredPane[9][9];
	private JLabel[] PlayerIcon = new JLabel[4];
	private JButton[] InsertButton = new JButton[12];
	private ArrayList<Tile> dfsResult;

	// the images for insert buttons
	private ImageIcon buttonImage1 = new ImageIcon("image/buttonimage1.png");
	private ImageIcon buttonImage2 = new ImageIcon("image/buttonimage2.png");
	private ImageIcon buttonImage3 = new ImageIcon("image/buttonimage3.png");
	private ImageIcon buttonImage4 = new ImageIcon("image/buttonimage4.png");

	// a counter for the Insert Button
	static int buttoncounter = 0;

	// constructor for Panel
	public LabyrinthBoardPanel(Board board) {

		// get all the possible tile to move to
		this.dfsResult = Ai.dfs(board, board.getPlayerList()[board.getCurrentPlayer()].getX(),
				board.getPlayerList()[board.getCurrentPlayer()].getY(), board.getCurrentPlayer()); // 接收 DFS 路径

		// set the button counter to 0
		buttoncounter = 0;

		// set the detail for the panel
		this.setSize(540, 540);
		this.setOpaque(false);
		// this.setBackground(new Color(0, 0, 0, 0));
		this.setLayout(null);

		// paint the Panel
		setToporBottomRow(true);
		setMiddleBoard(board);
		setToporBottomRow(false);

		// repaint();

		// set visibility
		this.setVisible(true);
	}
	
	//create image icon for the highlighting of tiles 
	private ImageIcon createHighlightImageIcon() {
	    // highlight ImageIcon, Bruce Yin Highlight part
	    return new ImageIcon("image/highlight_diagonal.png"); 
	}

	private void setMiddleBoard(Board board) {
		//iterate through the board 
		for (int i = 0; i < 7; ++i) {
			//if it is the place for Insert Button 
			if (i == 1 || i == 3 || i == 5) {
				//but the insert button in 
				InsertButton[buttoncounter] = new JButton();
				InsertButton[buttoncounter].setContentAreaFilled(false);
				InsertButton[buttoncounter].setBorderPainted(false);
				InsertButton[buttoncounter].setFocusPainted(false);
				InsertButton[buttoncounter].setFocusable(false);
				InsertButton[buttoncounter].setIcon(
						new ImageIcon(buttonImage2.getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH)));
				InsertButton[buttoncounter].setBounds(0, (i + 1) * 60, 60, 60);
				//add the insert button and increment the counter 
				this.add(InsertButton[buttoncounter]);
				buttoncounter++;
			} else {
				//if not the place put a transparent label 
				JLabel temp = new JLabel();
				temp.setBackground(new Color(0, 0, 0, 0));
				temp.setBounds(0, (i + 1) * 60, 60, 60);
				this.add(temp);
			}
			
			//put the tiles on to the board 
			for (int j = 0; j < 7; ++j) {
				//set it as a Layered Panel
				GameBoard[j][i] = new JLayeredPane();
				GameBoard[j][i].setLayout(null);
				GameBoard[j][i].setBackground(new Color(0, 0, 0, 0));
				GameBoard[j][i].setBounds((j + 1) * 60, (i + 1) * 60, 60, 60);
				//if the tile is fixed 
				if (!board.getTiles()[j][i].isFixed()) {
					//put a transparent JLabel
					JLabel empty = new JLabel();
					empty.setBackground(new Color(0, 0, 0, 0));
					empty.setBounds(0, 0, 60, 60);
					GameBoard[j][i].add(empty, Integer.valueOf(0));
				} else {
					//if it not fixed put tile image as a JLabel
					JLabel tileImage = new JLabel(
							new ImageIcon(board.getTiles()[j][i].getImage()[board.getTiles()[j][i].getDirection()]
									.getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH)));
					tileImage.setBounds(0, 0, 60, 60);
					GameBoard[j][i].add(tileImage, Integer.valueOf(0));
				}
				//if it accessible, Bruce Yin Highlight part
				if (dfsResult != null && dfsResult.contains(board.getTiles()[j][i])) {
					//put a transparent Label on top of the tile 
					ImageIcon highlightIcon = createHighlightImageIcon();
	                JLabel highlightLabel = new JLabel(highlightIcon); 
	                highlightLabel.setBounds(0, 0, 60, 60); 
	                GameBoard[j][i].add(highlightLabel, Integer.valueOf(1)); 
				}
				if (board.getTiles()[j][i].isPlayer()) {
					//if it have a player on it put the player on top 
					JLabel playerImage = new JLabel(new ImageIcon(board.getPlayerList()[board.getTiles()[j][i]
							.getPlayerList().get(board.getTiles()[j][i].getPlayerList().size() - 1)].getAvatarImage()
							.getImage().getScaledInstance(40, 40, java.awt.Image.SCALE_SMOOTH)));
					playerImage.setBounds(10, 10, 40, 40);
					GameBoard[j][i].add(playerImage, Integer.valueOf(3));
				}
				//add it to the panel 
				this.add(GameBoard[j][i]);
			}
			//if it is the place for Insert Button 
			if (i == 1 || i == 3 || i == 5) {
				InsertButton[buttoncounter] = new JButton();
				InsertButton[buttoncounter].setContentAreaFilled(false);
				InsertButton[buttoncounter].setBorderPainted(false);
				InsertButton[buttoncounter].setFocusPainted(false);
				InsertButton[buttoncounter].setFocusable(false);
				InsertButton[buttoncounter].setIcon(
						new ImageIcon(buttonImage1.getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH)));
				InsertButton[buttoncounter].setBounds(480, 60 * (i + 1), 60, 60);
				//add the insert button and increment the counter 
				this.add(InsertButton[buttoncounter]);
				buttoncounter++;
			} else {
				//if not the place put a transparent label 
				JLabel temp = new JLabel();
				temp.setBackground(new Color(0, 0, 0, 0));
				temp.setBounds(480, 60 * (i + 1), 60, 60);
				this.add(temp);
			}
		}

	}

	//method that set up the top row and bottom row of the board
	private void setToporBottomRow(boolean top) {
		//iterate through the row
		for (int i = 0; i < 9; ++i) {
			//determine if it is the place for insert button 
			if (i == 2 || i == 4 || i == 6) {
				//if so add the button
				InsertButton[buttoncounter] = new JButton();
				InsertButton[buttoncounter].setContentAreaFilled(false);
				InsertButton[buttoncounter].setBorderPainted(false);
				InsertButton[buttoncounter].setFocusPainted(false);
				InsertButton[buttoncounter].setFocusable(false);
				//the image Icon is determined by it is top row or bottom row
				if (top) {
					InsertButton[buttoncounter].setIcon(new ImageIcon(
							buttonImage3.getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH)));
					InsertButton[buttoncounter].setBounds(i * 60, 0, 60, 60);
				} else {
					InsertButton[buttoncounter].setIcon(new ImageIcon(
							buttonImage4.getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH)));
					InsertButton[buttoncounter].setBounds(i * 60, 480, 60, 60);
				}
				//add it and increment the counter
				this.add(InsertButton[buttoncounter]);
				buttoncounter++;
			} else {
				//if not place a transparent label
				JLabel temp = new JLabel();
				temp.setBackground(new Color(0, 0, 0, 0));
				temp.setBounds(i * 60, 0, 60, 60);
				this.add(temp);
			}
		}

	}

	//update the board panel 
	//take board as an input
	public void repaintBoard(Board newBoard) {
		//set every tile's is visited to be false for the dfs, Bruce Yin Highlight part
		for (int i = 0; i < 7; ++i) {
			for (int j = 0; j < 7; ++j) {
				newBoard.getTiles()[i][j].setVisitied(false);
			}
		}

		//update the dfs, Bruce Yin Highlight part
		this.dfsResult = Ai.dfs(newBoard, newBoard.getPlayerList()[newBoard.getCurrentPlayer()].getX(),
				newBoard.getPlayerList()[newBoard.getCurrentPlayer()].getY(), newBoard.getCurrentPlayer());

		//clear the panel 
		this.removeAll();
		buttoncounter = 0;

		//repainth the panel 
		setToporBottomRow(true);
		setMiddleBoard(newBoard);
		setToporBottomRow(false);

		//update 
		this.repaint();
		this.revalidate();

	}

	//method use to paint the Game board 
	@Override
	protected void paintComponent(Graphics g) {
		Image image;
		try {
			image = ImageIO.read(new File("image/GameBoard.png"));
			super.paintComponent(g);
			g.drawImage(image, 60, 60, 420, 420, this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	//getters and setters
	public JLabel[] getPlayerIcon() {
		return PlayerIcon;
	}

	private void setPlayerIcon(JLabel[] playerIcon) {
		PlayerIcon = playerIcon;
	}

	public JLayeredPane[][] getGameBoard() {
		return GameBoard;
	}

	private void setGameBoard(JLayeredPane[][] gameBoard) {
		GameBoard = gameBoard;
	}

	public JButton[] getButtonArray() {
		return InsertButton;
	}

	public boolean getInsertButton(ActionEvent e) {
		for (int i = 0; i < buttoncounter; i++) {
			if (e.getSource() == InsertButton[i])
				return true;
		}
		return false;
	}

	public JButton[] getInsertButtonArray() {
		return InsertButton;
	}

	public int[] getInsertButtonIndex(ActionEvent e) {
		int[] ret = new int[4];
		for (int i = 0; i < buttoncounter; i++) {

			if (e.getSource() == InsertButton[i]) {
				ret[3] = i;

				if ((i >= 0 && i <= 2) || (i >= 9 && i <= 11)) {
					ret[1] = 0;
					if (i == 0 || i == 9) {
						ret[0] = 1;
						if (i == 0)
							ret[2] = 1;
						else
							ret[2] = 0;
					} else if (i == 1 || i == 10) {
						ret[0] = 3;
						if (i == 1)
							ret[2] = 1;
						else
							ret[2] = 0;
					} else {
						ret[0] = 5;
						if (i == 2)
							ret[2] = 1;
						else
							ret[2] = 0;
					}
				}

				else {
					ret[1] = 1;
					if (i == 3 || i == 4) {
						ret[0] = 1;
						if (i == 3)
							ret[2] = 1;
						else
							ret[2] = 0;
					} else if (i == 5 || i == 6) {
						ret[0] = 3;
						if (i == 5)
							ret[2] = 1;
						else
							ret[2] = 0;
					} else {
						ret[0] = 5;
						if (i == 7)
							ret[2] = 1;
						else
							ret[2] = 0;
					}
				}

			}

		}
		return ret;
	}

	private void setInsertButton(JButton[] insertButton) {
		InsertButton = insertButton;
	}

}