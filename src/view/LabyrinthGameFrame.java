package view;

import javax.swing.*;

import model.Board;

/*
 * LabyrinthGameFrame: 
 * Andy Feng 100%
 * 
 * create a JFrame that load up the game frame of the Labyrinth game
 * 
 */

public class LabyrinthGameFrame extends JFrame{
	
	//fields of the game frame's different panel
	private LabyrinthBoardPanel BoardPanel; 
	private LabyrinthControlPanel ControlPanel; 
	private LabyrinthPlayerPanel PlayerPanel; 
	private Board board; 
	
	public LabyrinthGameFrame(Board board) {
		this.board = board;
		
		//set up the panels 
		setControlPanel(); 
		setBoardPanel(); 
		setPlayerPanel(); 
		
		
		//create the background picture
		ImageIcon back = new ImageIcon("image/GameFrameBackGround.jpg");
		ImageIcon scaledBack = new ImageIcon(back.getImage().getScaledInstance(1200, 625, java.awt.Image.SCALE_SMOOTH)); 
		this.setContentPane(new JLabel(scaledBack));
		
		//set the bounds of each panel 
		ControlPanel.setBounds(0, 0, 250, 625);
		this.add(ControlPanel);
		
		BoardPanel.setBounds(250, 20, 540, 540);
		this.add(BoardPanel);
		
		PlayerPanel.setBounds(800, 0, 400, 625);
		this.add(PlayerPanel);
			
		//set the details of the JFrame
		this.setTitle("Amazing Labyrinth Game");
		this.setResizable(false);
		this.setLayout(null); 
		this.setSize(1200, 625);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
				
		//set the visibility
		this.setVisible(true); 
	}
	
	//getters and setters for the panels 
	public LabyrinthBoardPanel getBoardPanel() {
		return BoardPanel;
	}

	public void setBoardPanel() {
		BoardPanel = new LabyrinthBoardPanel(board);
	}

	public LabyrinthControlPanel getControlPanel() {
		return ControlPanel;
	}

	public void setControlPanel() {
		ControlPanel = new LabyrinthControlPanel(board);
	}

	public LabyrinthPlayerPanel getPlayerPanel() {
		return PlayerPanel;
	}

	public void setPlayerPanel() {
		PlayerPanel = new LabyrinthPlayerPanel(board);
	}

}