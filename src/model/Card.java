package model;

import javax.swing.ImageIcon;
//Johnny Zhong 100%
//This class represents a card in the game, which is associated with a specific image,
//name, owner (player), and whether the card has been found or not.
public class Card {
	
	// fields
	private ImageIcon image;
	private String name;
	private int owner;
	private boolean find;
	
	// Contructor
	public Card(ImageIcon image, String name, int owner, boolean find) {
		super();
		this.image = image;
		this.name = name;
		this.owner = owner;
		this.find = find;
	}
	
	// Secondary Constructor, inherits an older card
	public Card(Card old) {
		this.image = new ImageIcon(old.getImage().getImage());
		this.name = old.getName();
		this.owner = old.getOwner();
		this.find = old.isFind();
	}
	
	//toString Method
	@Override
	public String toString() {
		return "Card [image=" + image + ", name=" + name + ", owner=" + owner + ", find=" + find + "]";
	}

	// Getters and Setters
	public ImageIcon getImage() {
		return image;
	}
	public void setImage(ImageIcon image) {
		this.image = image;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getOwner() {
		return owner;
	}
	public void setOwner(int owner) {
		this.owner = owner;
	}
	public boolean isFind() {
		return find;
	}
	public void setFind(boolean find) {
		this.find = find;
	}
	
}