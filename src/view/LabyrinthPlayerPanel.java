package view;

import java.awt.Color;

import javax.swing.*;

import model.Board;
import model.Player;

/*
 * LabyrinthPlayerPanel:
 * Andy Feng 100%
 * 
 * create the player panel that display the cards of every player 
 * on the game frame
 * when a cards is find a check mark will appear on it 
 * 
 */

public class LabyrinthPlayerPanel extends JPanel{
	
	//the fields for the panel
	private JLayeredPane[] PlayerPanel = new JLayeredPane[4]; 
	private JLabel[][] PlayerCards = new JLabel[4][7]; 
	private JLabel[] currentPlayer = new JLabel[4];
	
	//the constructor for the JPanel
	public LabyrinthPlayerPanel(Board board) {
		
		//method that put the components on the panel 
		updatePlayer(board);
		
		//set the detail for the panel
		this.setOpaque(false);;
		this.setSize(400, 625);
		this.setLayout(null);
		this.setVisible(true); 
	
	}
	
	//a method that return a layeredPane for each player 
	//take player and index as input 
	private JLayeredPane createPlayerPanel(Player player, int index) {
		//initialize the layeredPane
		JLayeredPane ret = new JLayeredPane(); 
		//set the detail for the LayeredPane
		ret.setLayout(null);
		ret.setOpaque(false);
		ret.setSize(200, 300);
		
		//create a JLabel with this player's icon 
		JLabel playerimage = new JLabel(new ImageIcon(player.getAvatarImage().
				getImage().getScaledInstance(100, 90, java.awt.Image.SCALE_SMOOTH)));
		playerimage.setBounds(50, 0, 100, 90);
		//add the JLabel
		ret.add(playerimage);
		
		//iterate through the 3x2 display of cards 
		for(int i = 0; i < 2; ++i) {
			for(int j = 0; j < 3; ++j) {
				//get the index for the card array 
				int temp = i*3 + j;
				//if the cards is empty move on 
				if(player.getTreasureCards()[temp] == null)continue;
				//generate a new JLabel with the image of the cards 
				PlayerCards[index][temp] = new JLabel(new ImageIcon(
						player.getTreasureCards()[temp].getImage().
						getImage().getScaledInstance(40, 65, java.awt.Image.SCALE_SMOOTH)));
				//set the JLabel with proper bounds 
				PlayerCards[index][temp].setHorizontalAlignment(JLabel.CENTER);
				PlayerCards[index][temp].setBounds(i * 100, j * 70 + 90, 100, 70);
				//add the JLabel
				ret.add(PlayerCards[index][temp], 1);
				//if the treasure is already find add a check mark on top of it 
				if(player.getTreasureCards()[temp].isFind()) {
					//create a JLabel with a image icon of a check mark s
					ImageIcon checkmark = new ImageIcon("image/checkmark.png");
					JLabel check = new JLabel(new ImageIcon(checkmark.getImage()
							.getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH))); 
					//set the bounds 
					check.setBounds(i * 100 + 25, j * 70 + 100, 50, 50);
					//add the check mark on a higher layer
					ret.add(check, 0);
				}
			}
		}
		
		//return the layered pane
		return ret; 
	}
	
	//update the player panel
	//take a board object as input
	public void updatePlayer(Board board) { 
		
		//remove all element in the panel
		this.removeAll();
		
		//set each layered pane in the player panel array
		for(int i = 0; i < board.getPlayerList().length; ++i) {
			if(board.getPlayerList()[i] == null)continue; 
			PlayerPanel[i] = createPlayerPanel(board.getPlayerList()[i], i);
		}

		//set the bounds for each of the layered pane
		for(int i = 0; i < 2; ++i) {
			for(int j = 0; j < 2; ++j) {
				//get the index for the array
				int temp = i * 2 + j;
				//if it is empty continue 
				if(PlayerPanel[temp] == null)continue;
				PlayerPanel[temp].setBounds(i * 200, j * 300, 200, 300);
				//add the layered pane to the panel 
				this.add(PlayerPanel[temp]);
			}
		}
		
		//repaint and revalidated 
		this.repaint();
		this.revalidate();
	}

	//getters and setters
	public JLayeredPane[] getPlayerPanel() {
		return PlayerPanel;
	}
	public void setPlayerPanel(JLayeredPane[] playerPanel) {
		PlayerPanel = playerPanel;
	}
	public JLabel[][] getPlayerCards() {
		return PlayerCards;
	}
	public void setPlayerCards(JLabel[][] playerCards) {
		PlayerCards = playerCards;
	}
	public JLabel[] getCurrentPlayer() {
		return currentPlayer;
	}
	public void setCurrentPlayer(JLabel[] currentPlayer) {
		this.currentPlayer = currentPlayer;
	} 
}