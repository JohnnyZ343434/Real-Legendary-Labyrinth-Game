package view;

import javax.swing.*;

/*
 * LabyrinthStartFrame:
 * Andy Feng 100%
 * 
 * a Frame that allow the starting of the game with buttons to help frame 
 * and setting frame
 */

public class LabyrinthStartFrame extends JFrame {

	// set the fields for the frame
	private JButton HelpButton;
	private JButton StartButton;

	// constructor of the field
	public LabyrinthStartFrame() {

		// set the details for the help button
		HelpButton = new JButton();
		HelpButton.setVisible(true);
		HelpButton.setOpaque(false);
		HelpButton.setBorderPainted(false);
		HelpButton.setContentAreaFilled(false);
		HelpButton.setBounds(750, 390, 290, 75);

		// set the details for the start button
		StartButton = new JButton();
		StartButton.setVisible(true);
		StartButton.setOpaque(false);
		StartButton.setBorderPainted(false);
		StartButton.setContentAreaFilled(false);
		StartButton.setBounds(750, 505, 290, 75);

		// create the background picture
		ImageIcon back = new ImageIcon("image/StartFrameBackGround.jpg");
		ImageIcon scaledBack = new ImageIcon(back.getImage().getScaledInstance(1200, 625, java.awt.Image.SCALE_SMOOTH));
		this.setContentPane(new JLabel(scaledBack));

		// set the details for the frame
		this.setTitle("Amazing Labyrinth Game");
		this.setResizable(false);
		this.setLayout(null);
		this.setSize(1200, 625);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		// add the buttons
		this.add(HelpButton);
		this.add(StartButton);

		// set the visibility
		this.setVisible(false);
	}

	// getters and setters
	public JButton getHelpButton() {
		return HelpButton;
	}

	private void setHelpButton(JButton helpButton) {
		HelpButton = helpButton;
	}

	public JButton getStartButton() {
		return StartButton;
	}

	private void setStartButton(JButton startButton) {
		StartButton = startButton;
	}

}