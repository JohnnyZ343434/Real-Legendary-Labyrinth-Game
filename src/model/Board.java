package model;

import java.util.Collections;
import java.util.Random;
import java.util.Stack;

import control.MazeController;

/*
 * Bruce Yin 85% Andy Feng 15%
 * 
 * The Board class manages the game board for a 7x7 tile-based game, 
 * including players, tiles, and a free tile for insertion. 
 * It initializes the board with shuffled tiles and assigns starting positions and treasure cards to players. 
 * The class handles inserting the free tile into the board, shifting tiles accordingly, and updating player positions. 
 * It also tracks the current player, their moves, and provides methods to get and set board and player details. 
 * This class is the core of managing the game state and interactions.
 */

public class Board {
    // Predefined starting positions for players on the board
    static final int[][] StartPosition = { { 0, 0 }, { 0, 6 }, { 6, 0 }, { 6, 6 } };

    // 7x7 grid representing the game board
    private Tile[][] tiles = new Tile[7][7];

    // Free tile available for insertion into the board
    private Tile freeTile;

    // Array of players in the game
    private Player[] PlayerList = new Player[4];

    // Tracks the current player's turn
    private int currentPlayer;

    // Determines if the player can move or insert a tile
    private boolean moveOrInsert;

    // Tracks the current player's number
    private int playerNumber;

    // Stores the last inserted tile's position
    private int lastInsert = -1;

    // Constructor to initialize the board for a new game
    public Board(Player[] PlayerList) {
        this.PlayerList = PlayerList;
        initializeGame(); // Sets up the game board with tiles and players
    }

    // Secondary constructor to load an existing game state
    public Board(Tile[][] tiles, Tile freeTile, Player[] playerList, int currentPlayer, boolean moveOrInsert,
            int playerNumber, int lastInsert) {
        super();
        this.tiles = tiles; // Load board tiles
        this.freeTile = freeTile; // Load free tile
        PlayerList = playerList; // Load players
        this.currentPlayer = currentPlayer; // Load current player
        this.moveOrInsert = moveOrInsert; // Load move/insert state
        this.playerNumber = playerNumber; // Load player number
        this.lastInsert = lastInsert; // Load last inserted tile's position
    }

    // Copy constructor to create a deep copy of an existing board
    public Board(Board board) {
        for (int i = 0; i < 7; ++i)
            for (int j = 0; j < 7; ++j)
                this.tiles[i][j] = new Tile(board.getTiles()[i][j]); // Deep copy each tile

        this.freeTile = new Tile(board.getFreeTile()); // Deep copy free tile
        for (int i = 0; i < 4; ++i) {
            if (board.getPlayerList()[i] == null)
                continue;
            this.PlayerList[i] = new Player(board.getPlayerList()[i]); // Deep copy each player
        }
        this.currentPlayer = board.getCurrentPlayer(); // Copy current player
        this.moveOrInsert = board.isMoveOrInsert(); // Copy move/insert state
        this.playerNumber = board.getPlayerNumber(); // Copy player number
        this.lastInsert = board.getLastInsert(); // Copy last inserted tile's position
    }

    // Initializes the game board with tiles, free tile, and players
    private void initializeGame() {
        Random rand = new Random();

        // Retrieve all tiles from the MazeController
        Tile[] TilesArray = MazeController.ScanTiles();
        Stack<Tile> TileStack = new Stack<Tile>();

        // Place fixed tiles and push the rest into a stack
        for (int i = 0; i < 50; i++) {
            if (!TilesArray[i].isFixed())
                tiles[TilesArray[i].getX()][TilesArray[i].getY()] = TilesArray[i];
            else
                TileStack.add(TilesArray[i]); // Add movable tiles to the stack
        }
        Collections.shuffle(TileStack); // Shuffle the stack for randomness

        // Fill the board with tiles from the stack
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 7; j++) {
                if (tiles[i][j] == null) { // Assign tiles to empty positions
                    tiles[i][j] = TileStack.pop();
                    tiles[i][j].setX(i); // Set tile's x-coordinate
                    tiles[i][j].setY(j); // Set tile's y-coordinate
                    tiles[i][j].setDirection(rand.nextInt(4)); // Randomize direction
                }
            }
        }

        // Assign the free tile
        freeTile = TileStack.pop();
        freeTile.setDirection(rand.nextInt(4)); // Randomize free tile's direction

        // Retrieve cards from the MazeController
        Stack<Card> CardStack = MazeController.ScanCards();

        // Assign starting positions and cards to players
        int temp = 0;
        for (Player x : PlayerList) {
            if (x == null)
                continue;
            if (temp == 0)
                this.setCurrentPlayer(x.getPlayerNumber()); // Set the first player as current
            x.setX(StartPosition[temp][0]); // Set player's starting x-coordinate
            x.setY(StartPosition[temp][1]); // Set player's starting y-coordinate
            temp++;

            tiles[x.getX()][x.getY()].setPlayer(true); // Mark tile as occupied by the player
            tiles[x.getX()][x.getY()].addPlayerNumber(x.getPlayerNumber()); // Add player's number to tile

            for (int i = 0; i < x.getNumOfCards(); i++) {
                x.addTreasureCard(CardStack.pop(), i); // Assign treasure cards to player
                x.getTreasureCards()[i].setOwner(x.getPlayerNumber()); // Set card ownership
            }
        }
    }

    // Inserts the free tile into the board at the specified row or column
    public void insertFreeTile(int rowOrCol, boolean isRow, boolean isTop) {
        Tile temp;
        if (!isRow) { // Insert into a row
            if (isTop) { // Insert at the top
                temp = tiles[rowOrCol][6];
                for (int i = 6; i > 0; i--) { // Shift tiles to the right
                    tiles[rowOrCol][i] = tiles[rowOrCol][i - 1];
                    tiles[rowOrCol][i].setX(rowOrCol);
                    tiles[rowOrCol][i].setY(i);
                }
                tiles[rowOrCol][0] = freeTile; // Place free tile at the start of the row
                tiles[rowOrCol][0].setX(rowOrCol);
                tiles[rowOrCol][0].setY(0);
                updatePlayerPositionAfterInsert(temp, rowOrCol, 0); // Update player positions
            } else { // Insert at the bottom
                temp = tiles[rowOrCol][0];
                for (int i = 0; i < 6; i++) { // Shift tiles to the left
                    tiles[rowOrCol][i] = tiles[rowOrCol][i + 1];
                    tiles[rowOrCol][i].setX(rowOrCol);
                    tiles[rowOrCol][i].setY(i);
                }
                tiles[rowOrCol][6] = freeTile; // Place free tile at the end of the row
                tiles[rowOrCol][6].setX(rowOrCol);
                tiles[rowOrCol][6].setY(6);
                updatePlayerPositionAfterInsert(temp, rowOrCol, 6); // Update player positions
            }
        } else { // Insert into a column
            if (isTop) { // Insert at the top
                temp = tiles[6][rowOrCol];
                for (int i = 6; i > 0; i--) { // Shift tiles downward
                    tiles[i][rowOrCol] = tiles[i - 1][rowOrCol];
                    tiles[i][rowOrCol].setX(i);
                    tiles[i][rowOrCol].setY(rowOrCol);
                }
                tiles[0][rowOrCol] = freeTile; // Place free tile at the top of the column
                tiles[0][rowOrCol].setX(0);
                tiles[0][rowOrCol].setY(rowOrCol);
                updatePlayerPositionAfterInsert(temp, 0, rowOrCol); // Update player positions
            } else { // Insert at the bottom
                temp = tiles[0][rowOrCol];
                for (int i = 0; i < 6; i++) { // Shift tiles upward
                    tiles[i][rowOrCol] = tiles[i + 1][rowOrCol];
                    tiles[i][rowOrCol].setX(i);
                    tiles[i][rowOrCol].setY(rowOrCol);
                }
                tiles[6][rowOrCol] = freeTile; // Place free tile at the bottom of the column
                tiles[6][rowOrCol].setX(6);
                tiles[6][rowOrCol].setY(rowOrCol);
                updatePlayerPositionAfterInsert(temp, 6, rowOrCol); // Update player positions
            }
        }
        temp.setPlayer(false); // Clear player information from the removed tile
        temp.getPlayerList().clear();
        freeTile = temp; // Update the free tile
    }

    // Updates the player positions after a tile is inserted
    private void updatePlayerPositionAfterInsert(Tile temp, int newX, int newY) {
        if (temp.isPlayer()) {
            freeTile.setPlayer(true); // Mark the free tile as occupied
            for (int i : temp.getPlayerList()) {
                freeTile.addPlayerNumber(i); // Add player numbers to the free tile
                this.getPlayerList()[i].setX(newX); // Update player's x-coordinate
                this.getPlayerList()[i].setY(newY); // Update player's y-coordinate
            }
        }
    }

    // Updates all players' locations based on their tile positions
    public void updatePlayerLocation() {
        for (int i = 0; i < 7; ++i) {
            for (int j = 0; j < 7; ++j) {
                if (this.tiles[i][j].isPlayer()) { // Check if tile is occupied
                    for (int k : this.tiles[i][j].getPlayerList()) {
                        this.getPlayerList()[k].setX(i); // Update player's x-coordinate
                        this.getPlayerList()[k].setY(j); // Update player's y-coordinate
                    }
                }
            }
        }
    }

    // Returns the name of the current player based on their ID
    public String getPlayerName() {
        switch (this.getCurrentPlayer()) {
            case 0:
                return "Aragorn";
            case 1:
                return "Ronaldo";
            case 2:
                return "Kobe";
            default:
                return "Messi";
        }
    }

    // Getter and Setter methods
    public int getLastInsert() {
        return lastInsert;
    }

    public void setLastInsert(int lastInsert) {
        this.lastInsert = lastInsert;
    }

    public boolean isMoveOrInsert() {
        return moveOrInsert;
    }

    public void setMoveOrInsert(boolean moveOrInsert) {
        this.moveOrInsert = moveOrInsert;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public Tile getFreeTile() {
        return freeTile;
    }

    public Player[] getPlayerList() {
        return PlayerList;
    }

    public void setPlayerList(Player[] playerList) {
        PlayerList = playerList;
    }

    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
    }

    public void setFreeTile(Tile freeTile) {
        this.freeTile = freeTile;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

}