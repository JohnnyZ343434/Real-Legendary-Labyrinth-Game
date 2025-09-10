package view;

import java.awt.Color;
import java.awt.Font;

import javax.swing.*;

/*
 * LabyrinthSettingFrame: 
 * Andy Feng 100%
 * 
 * load up the setting frame that pass details to the controller and create the game frame
 */

public class LabyrinthSettingFrame extends JFrame{
	
	//set the fields for the setting frame
	private JComboBox<String> player1Menu; 
	private JComboBox<String> player2Menu; 
	private JComboBox<String> player3Menu; 
	private JComboBox<String> player4Menu; 
	private JSlider CardSlider; 
	private JButton LoadButton; 
	private JButton StartButton; 
	
	//set a variable for the common font
	static Font title = new Font("Serif", Font.ITALIC, 20); 
	
	//constructor for the frame
	public LabyrinthSettingFrame() {
		
		//choices for each player
		String[] playerchoice =
			{"None", "Player", "Average Bot", "Smart Bot", "Sabotage Bot", "Random Bot"}; 
		
		//set the details for each combo box 
		player1Menu = new JComboBox<String>(playerchoice); 
		player1Menu.setBackground(new Color(0xfaf0c5));
		player1Menu.setBounds(215, 440, 100, 62);
		
		player2Menu = new JComboBox<String>(playerchoice); 
		player2Menu.setBackground(new Color(0xfaf0c5));
		player2Menu.setBounds(438, 443, 100, 62);
		
		player3Menu = new JComboBox<String>(playerchoice); 
		player3Menu.setBackground(new Color(0xfaf0c5));
		player3Menu.setBounds(693, 443, 100, 62);
		
		player4Menu = new JComboBox<String>(playerchoice); 
		player4Menu.setBackground(new Color(0xfaf0c5));
		player4Menu.setBounds(945, 445, 100, 62);
		
		//set the details card slider for the setting frame 
		CardSlider = new JSlider(1, 6, 1); 
		CardSlider.setPaintTrack(true); 
		CardSlider.setPaintTicks(true); 
		CardSlider.setPaintLabels(true);
		CardSlider.setMajorTickSpacing(1);
		CardSlider.setSnapToTicks(true);
		CardSlider.setFont(title);
		CardSlider.setBounds(600, 505, 470, 100);
		
		//set the start button for the frame 
		StartButton = new JButton();
		//make the button invisible to user 
		StartButton.setVisible(true);
		StartButton.setOpaque(false);
		StartButton.setBorderPainted(false);
		StartButton.setContentAreaFilled(false);
		StartButton.setBounds(317, 515, 220, 65);
		
		//set the load button for the frame 
		LoadButton = new JButton(); 
		//make the button invisible to the user
		LoadButton.setVisible(true);
		LoadButton.setOpaque(false);
		LoadButton.setBorderPainted(false);
		LoadButton.setContentAreaFilled(false);
		LoadButton.setBounds(82, 40, 90, 90);
		
		//create the background picture
		ImageIcon back = new ImageIcon("image/SettingFrameBackGround.jpg");
		ImageIcon scaledBack = new ImageIcon(back.getImage().getScaledInstance(1200, 625, java.awt.Image.SCALE_SMOOTH)); 
		this.setContentPane(new JLabel(scaledBack));
				
		//set the details the of the frame
		this.setTitle("Amazing Labyrinth Game");
		this.setResizable(false);
		this.setLayout(null); 
		this.setSize(1200, 625);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		//add the component to the frame
		this.add(player1Menu);
		this.add(player2Menu);
		this.add(player3Menu);
		this.add(player4Menu);
		this.add(CardSlider);
		this.add(StartButton);
		this.add(LoadButton);
				
		//set the visibility of the frame
		this.setVisible(false); 
		
	}
	 
	//a method that returns the player choice of each player 
	//by index in a for loop
	public JComboBox<String> getPlayerMenu(int index){
		if(index == 0) 
			return getPlayer1Menu(); 
		else if(index == 1)
			return getPlayer2Menu(); 
		else if(index == 2)
			return getPlayer3Menu(); 
		else 
			return getPlayer4Menu();
	}
	
	//getters and setters 
	public JButton getLoadButton() {
		return LoadButton;
	}

	public void setLoadButton(JButton loadButton) {
		LoadButton = loadButton;
	}

	public JComboBox<String> getPlayer1Menu() {
		return player1Menu;
	}

	public void setPlayer1Menu(JComboBox<String> player1Menu) {
		this.player1Menu = player1Menu;
	}

	public JComboBox<String> getPlayer2Menu() {
		return player2Menu;
	}

	public void setPlayer2Menu(JComboBox<String> player2Menu) {
		this.player2Menu = player2Menu;
	}

	public JComboBox<String> getPlayer3Menu() {
		return player3Menu;
	}

	public void setPlayer3Menu(JComboBox<String> player3Menu) {
		this.player3Menu = player3Menu;
	}

	public JComboBox<String> getPlayer4Menu() {
		return player4Menu;
	}

	public void setPlayer4Menu(JComboBox<String> player4Menu) {
		this.player4Menu = player4Menu;
	}

	public JSlider getCardSlider() {
		return CardSlider;
	}

	public void setCardSlider(JSlider cardSlider) {
		CardSlider = cardSlider;
	}

	public JButton getStartButton() {
		return StartButton;
	}

	public void setStartButton(JButton startButton) {
		StartButton = startButton;
	}

}