package model;

import java.util.Arrays;

import javax.swing.ImageIcon;

/*
 * Bruce Yin 95% Andy Feng 5%
 * 
 * The Player class represents a game player, storing their number, 
 * avatar, position, and up to 7 treasure cards. It lets you add cards, 
 * move the player, and get or change their details. You can also copy a player or see their info as a string. 
 * It’s a simple way to manage players in a card or board game.
 */

public class Player {
    // Unique identifier for the player
    private int playerNumber;

    // Array to store the player's treasure cards
    private Card[] treasureCards = new Card[7];

    // Number of treasure cards the player currently has
    private int numOfCards;

    // Avatar image associated with the player
    private ImageIcon avatarImage; // Placeholder for avatar image path

    // Player's current x and y coordinates on the game board
    private int x, y;

    // Uncommented placeholder for cards left, potentially for future use
    //private int cardsLeft;

    // Constructor to initialize a player with their number, avatar, and number of cards
    public Player(int playerNumber, ImageIcon avatarImage, int numOfCards) {
        this.playerNumber = playerNumber;
        this.avatarImage = avatarImage;
        this.numOfCards = numOfCards;
        this.treasureCards = new Card[7]; // Initialize array to hold treasure cards
    }
    
    // Copy constructor to create a deep copy of another Player instance
    public Player(Player old) {
        this.playerNumber = old.getPlayerNumber();
        for (int i = 0; i < old.getNumOfCards(); ++i) {
            // Create a deep copy of each card from the old player's treasure cards
            this.treasureCards[i] = new Card(old.getTreasureCards()[i]);
        }
        this.numOfCards = old.getNumOfCards(); // Copy the number of cards
        this.avatarImage = new ImageIcon(old.getAvatarImage().getImage()); // Deep copy avatar image
        this.x = old.getX(); // Copy x coordinate
        this.y = old.getY(); // Copy y coordinate
        //this.cardsLeft = old.getCardsLeft(); // Uncomment if cardsLeft is utilized
    }
    
    // Override toString to provide a readable string representation of the Player object
    @Override
    public String toString() {
        return "Player [playerNumber=" + playerNumber + ", treasureCards=" + Arrays.toString(treasureCards)
                + ", numOfCards=" + numOfCards + ", avatarImage=" + avatarImage + ", x=" + x + ", y=" + y + "]";
    }

    // Adds a treasure card at a specific index in the player's treasure cards
    public void addTreasureCard(Card card, int index) {
        treasureCards[index] = card;
    }

    // Updates the player's coordinates to new positions
    public void move(int newX, int newY) {
        this.x = newX;
        this.y = newY;
    }

    // Getter for the number of cards
    public int getNumOfCards() {
        return numOfCards;
    }

    // Getter for the player's number
    public int getPlayerNumber() {
        return playerNumber;
    }

    // Setter for the player's number
    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    // Getter for the player's treasure cards array
    public Card[] getTreasureCards() {
        return treasureCards;
    }

    // Setter for the player's treasure cards array
    public void setTreasureCards(Card[] treasureCards) {
        this.treasureCards = treasureCards;
    }

    // Getter for the player's avatar image
    public ImageIcon getAvatarImage() {
        return avatarImage;
    }

    // Setter for the player's avatar image
    public void setAvatarImage(ImageIcon avatarImage) {
        this.avatarImage = avatarImage;
    }

    // Getter for the player's x-coordinate
    public int getX() {
        return x;
    }

    // Setter for the player's x-coordinate
    public void setX(int x) {
        this.x = x;
    }

    // Getter for the player's y-coordinate
    public int getY() {
        return y;
    }

    // Setter for the player's y-coordinate
    public void setY(int y) {
        this.y = y;
    }

    // Setter for the number of cards
    public void setNumOfCards(int numOfCards) {
        this.numOfCards = numOfCards;
    }

    // Placeholder for future implementation or use
//    public int getCardsLeft() {
//        return cardsLeft;
//    }
//
//    public void setCardsLeft(int cardsLeft) {
//        this.cardsLeft = cardsLeft;
//    }
}