package view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.*;

import model.*;

/*
 * LbyrinthControlPanel:
 * Andy Feng 90% Johnny Zhong 10%
 * 
 * Create the Control Panel for the Game Frame
 * 
 */

public class LabyrinthControlPanel extends JPanel{
	
	//fields of the panel
	private JLabel FreeTile; 
	private JButton rightButton; 
	private JButton leftButton;
	private JLabel InstructionLabel; 
	private JButton EndButton;
	private JLabel CurrentPlayer; 
	private JButton SaveButton;
	
	//font variable for the panel
	static Font title = new Font("Serif", Font.ITALIC, 20);
	static Font bigtitle = new Font("Serif", Font.ITALIC, 40);

	//constructor of the panel
	public LabyrinthControlPanel(Board board) {
		
		//Create A JLabel that show the current player's name
		CurrentPlayer = new JLabel("<html><div WIDTH=200>Current Player is " + 
				board.getPlayerName() + "</div></html>"); 
		CurrentPlayer.setBackground(new Color(0, 0, 0, 0));
		CurrentPlayer.setFont(title);
		CurrentPlayer.setBounds(25, 120, 200, 50);
		//add this label to the panel 
		this.add(CurrentPlayer);
		
		//create the Label showing the free tile of the board
		FreeTile = new JLabel(board.getFreeTile().getImage()[board.getFreeTile().getDirection()]);
		FreeTile.setBackground(new Color(0, 0, 0, 0));
		FreeTile.setBounds(75, 185, 100, 100);
		this.add(FreeTile);
		
		//set the icon for the rotation buttons  
		ImageIcon rarrow = new ImageIcon("image/LeftArrow.png");
		ImageIcon larrow = new ImageIcon("image/RightArrow.png");
		//create the right buttons and sets it details 
		rightButton = new JButton(new ImageIcon(rarrow.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH)));
		rightButton.setContentAreaFilled(false);
		rightButton.setBorderPainted(false);
		rightButton.setFocusPainted(false);
		rightButton.setFocusable(false);
		rightButton.setBackground(new Color(0, 0, 0, 0));
		rightButton.setBounds(25, 235, 50, 50); 
		//add the buttons 
		this.add(rightButton);
		
		//create the left buttons and set its details 
		leftButton = new JButton(new ImageIcon(larrow.getImage().getScaledInstance(50, 50, java.awt.Image.SCALE_SMOOTH)));
		leftButton.setContentAreaFilled(false);
		leftButton.setBorderPainted(false);
		leftButton.setFocusPainted(false); 
		leftButton.setFocusable(false);
		leftButton.setBackground(new Color(0, 0, 0, 0));
		leftButton.setBounds(175, 235, 50, 50); 
		//add the buttons 
		this.add(leftButton);
		
		//create a JLabel that return remind the user to use arrow keys 
		InstructionLabel = new JLabel("<html><div WIDTH=200>Use the arrow key to move "
									+ "When finished press the button "
									+ "below</div></html>");
		InstructionLabel.setBackground(new Color(0, 0, 0, 0));
		InstructionLabel.setFont(title);
		InstructionLabel.setBounds(25, 285, 200, 100);
		InstructionLabel.setVisible(false);
		//add the label
		this.add(InstructionLabel);
		
		//create a JButton for ending this round
		EndButton = new JButton("End Round");
		EndButton.setForeground(Color.black);
		EndButton.setOpaque(true);
		EndButton.setBackground(Color.green);
		EndButton.setFont(title);
		EndButton.setVisible(false);
		EndButton.setFocusable(false);
		EndButton.setBounds(25, 385, 200, 50);
		//add the button 
		this.add(EndButton);
		
		//create a JButton for saving this game
		SaveButton = new JButton("Save Game");
		SaveButton.setForeground(Color.black);
		SaveButton.setOpaque(true);
		SaveButton.setBackground(Color.green);
		SaveButton.setFont(title);
		SaveButton.setVisible(true);
		SaveButton.setFocusable(false);
		SaveButton.setBounds(25, 450, 200, 50);
		//add the button
		this.add(SaveButton);
		
		//set the details of the panel
		this.setLayout(null);
		this.setOpaque(false);
		this.setSize(250, 625);
		this.setVisible(true); 
	}
	
	//a void method taking in board as an input 
	//and rotate the free tile on the panel
	public void updateTile(Board board) {
		//change the direction 
		this.FreeTile.setIcon(
				board.getFreeTile().getImage()[board.getFreeTile().getDirection()]);
	}
	
	//a void method taking in board as an input 
	//update the JLabel of the current player 
	public void updatePlayer(Board board) {
		//change the text of the label
		this.CurrentPlayer.setText("<html><div WIDTH=200>Current Player is " + 
				board.getPlayerName() + "</div></html>");
	}
	
	//a void method that change the visibility of end button and 
	//instruction label depending on its input 
	public void changeVisibility(boolean vis) {
		//change the visibility
		EndButton.setVisible(vis);
		InstructionLabel.setVisible(vis);
	}
	
	//getters and setters 
	public JLabel getCurrentPlayer() {
		return CurrentPlayer;
	}

	public void setCurrentPlayer(JLabel currentPlayer) {
		CurrentPlayer = currentPlayer;
	}

	public JButton getRightButton() {
		return rightButton;
	}

	public void setRightButton(JButton rightButton) {
		this.rightButton = rightButton;
	}

	public JButton getLeftButton() {
		return leftButton;
	}

	public void setLeftButton(JButton leftButton) {
		this.leftButton = leftButton;
	}

	public JButton getEndButton() {
		return EndButton;
	}

	private void setEndButton(JButton endButton) {
		EndButton = endButton;
	}

	public JLabel getFreeTile() {
		return FreeTile;
	}

	private void setFreeTile(JLabel freeTile) {
		FreeTile = freeTile;
	}

	public JLabel getInstructionLabel() {
		return InstructionLabel;
	}

	public JButton getSaveButton() {
		return SaveButton;
	}

	public void setSaveButton(JButton saveButton) {
		SaveButton = saveButton;
	}

	private void setInstructionLabel(JLabel instructionLabel) {
		InstructionLabel = instructionLabel;
	}

}