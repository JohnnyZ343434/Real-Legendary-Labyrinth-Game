package model;

import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.ImageIcon;

/*
 * Bruce Yin 95% Andy Feng 5%
 * 
 * The Tile class represents a game tile with properties like its image, name, shape, direction, 
 * and whether it’s fixed or occupied by players. It tracks its position on the board, the players on it, 
 * and whether it’s been visited. The class includes methods to rotate the tile, manage player occupancy, 
 * and access or update its properties. It’s used to define and manage the behavior of individual tiles in the game.
 */

public class Tile {
    // Array of images representing the tile in different orientations
    private ImageIcon[] image;

    // Name of the tile
    private String name;

    // Shape of the tile (e.g., L-shape, T-shape)
    private String shape;

    // Direction the tile is currently facing (0 to 3 for 4 possible orientations)
    private int direction;

    // Indicates whether the tile is fixed in place or movable
    private boolean isFixed;

    // Indicates whether the tile is occupied by a player
    private boolean isPlayer;

    // Indicates whether the tile has been visited during a game
    private boolean isVisitied;

    // List of player numbers currently occupying the tile
    private ArrayList<Integer> playerList = new ArrayList<Integer>();

    // x and y coordinates of the tile on the board
    private int x, y;

    // Constructor to initialize a tile with its properties
    public Tile(ImageIcon[] image, String name, String shape, int direction, boolean isFixed, int x, int y) {
        super();
        this.image = image;
        this.name = name;
        this.shape = shape;
        this.direction = direction;
        this.isFixed = isFixed;
        this.x = x;
        this.y = y;
        this.isPlayer = false; // Initially, the tile is not occupied by a player
    }

    // Copy constructor to create a deep copy of an existing tile
    public Tile(Tile old) {
        this.image = old.getImage(); // Copy image references
        this.name = old.getName(); // Copy name
        this.shape = old.getShape(); // Copy shape
        this.direction = old.getDirection(); // Copy direction
        this.isFixed = old.isFixed(); // Copy fixed status
        this.x = old.getX(); // Copy x-coordinate
        this.y = old.getY(); // Copy y-coordinate
        this.playerList.addAll(old.getPlayerList()); // Copy player list
        this.isPlayer = old.isPlayer(); // Copy player occupancy status
        this.isVisitied = old.isVisitied(); // Copy visited status
    }

    // Rotates the tile either clockwise or counterclockwise
    public void rotateTile(boolean direction) {
        if (direction) { // Clockwise rotation
            if (this.direction == 3) {
                this.setDirection(0); // Wrap around to 0
            } else {
                this.setDirection(this.direction + 1); // Increment direction
            }
        } else { // Counterclockwise rotation
            if (this.direction == 0) {
                this.setDirection(3); // Wrap around to 3
            } else {
                this.setDirection(this.direction - 1); // Decrement direction
            }
        }
    }

    // Returns a string representation of the tile's state
    @Override
    public String toString() {
        return "Tile [image=" + Arrays.toString(image) + ", name=" + name + ", shape=" + shape + ", direction="
                + direction + ", isFixed=" + isFixed + ", isPlayer=" + isPlayer + ", PlayerNumber=" + playerList
                + ", x=" + x + ", y=" + y + "]";
    }

    // Getters and Setters
    public String getShape() {
        return shape;
    }

    public boolean isFixed() {
        return isFixed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public ImageIcon[] getImage() {
        return image;
    }

    public void setImage(ImageIcon[] image) {
        this.image = image;
    }

    public boolean isPlayer() {
        return isPlayer;
    }

    public void setPlayer(boolean isPlayer) {
        this.isPlayer = isPlayer;
    }

    public ArrayList<Integer> getPlayerList() {
        return playerList;
    }

    // Adds a player's number to the list of players occupying the tile
    public void addPlayerNumber(int playerNumber) {
        playerList.add(playerNumber);
    }

    // Sets the list of players occupying the tile
    public void setPlayerList(ArrayList<Integer> playerNumber) {
        this.playerList = playerNumber;
    }

    // Removes a player's number from the list of players occupying the tile
    public void erasPlayer(int playerNumber) {
        playerList.remove(Integer.valueOf(playerNumber));
    }

    public boolean isVisitied() {
        return isVisitied;
    }

    public void setVisitied(boolean isVisitied) {
        this.isVisitied = isVisitied;
    }
}