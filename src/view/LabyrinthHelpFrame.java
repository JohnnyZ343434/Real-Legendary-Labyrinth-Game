package view;

import javax.swing.*;
import java.awt.*;

public class LabyrinthHelpFrame extends JFrame {

    String helpTextContent = "Contents:\n\n"
            + "1 game board with 16 fixed path tiles\n"
            + "14 square path tiles\n"
            + "24 treasure cards\n"
            + "4 playing pieces\n\n"
            + "The object of the Game\n"
            + "In this enchanted labyrinth, players set out to search for mysterious objects and creatures. "
            + "By cleverly sliding the paths players try to find their way to the coveted treasure. The first player\n to find "
            + "all their treasures and return to the starting square is the winner.\n\n"
            + "Set-Up\n"
            + "When playing for the first time, carefully punch out the path tiles and treasure cards. "
            + "Shuffle the path tiles, face down, and place them face up on the empty spaces of the game board to form a \nrandom maze of paths. "
            + "There should be one path tile remaining. Lay it face up next to the game board and use it later in the game to replace tiles that have been moved off the board. "
            + "Shuffle the 24 \ntreasure cards and divide them evenly among the players. Each player lays his treasure cards down in front of him on the table in a pile. "
            + "Each player chooses one of the 6 playing pieces and places it \non its colour in one of the four corners of the game board. Ready to go!\n\n"
            + "How to Play\n"
            + "Each player looks at the six cards of his stack of treasure cards. Other players also can see your treasure cards. "
            + "Now you try to get to the square showing the same treasure as on your card. The last \nplayer to go on a treasure hunt goes first with play continuing in a clockwise direction.\n\n"
            + "A turn is always made up of two steps:\n"
            + "1. Move the maze\n"
            + "2. Move your playing piece\n\n"
            + "On your turn, try to move your playing piece to the treasure in the labyrinth shown on your card. First, insert the path tile lying next to the game board and then move your piece on the board.\n\n"
            + "1. Moving the Maze\n"
            + "There are 12 arrows along the edge of the board. They are marking the rows where you can insert the path tile into the maze. "
            + "On your turn, insert the extra path tile into the game board where one of \nthe arrows is, until another path tile is pushed out of the maze on the opposite side. "
            + "The only exception: The path tile cannot be inserted back into the board at the same place where it was pushed out.\n\n"
            + "If the path tile you push out has a playing piece on it, put this piece on the opposite side of the board on the path tile that was just placed. "
            + "Moving this piece does not count as your turn!\n\n"
            + "Important: You must move the maze before you can move your playing piece. Even if you can get to the treasure you are looking for without moving the maze.\n\n"
            + "2. Moving Your Playing Piece\n"
            + "Once you have moved the maze, you can move your playing piece. You can occupy any square that you can move your piece to directly, without interruption. "
            + "You can move your playing piece as far \nas you like. Or, you can leave your playing piece where it is. Then click the end button to stay in the final location.\n\n"
            + "Once you find the treasure you are looking for, turn over your treasure card and lay it face up next to your card pile. Then it’s the next player’s turn. "
            + "This player inserts the extra path tile into the game \nboard before moving their playing piece, and so on.\n\n"
            + "Ending the Game\n"
            + "The game is over as soon as a player has turned over all their treasure cards and returned their playing piece to its starting position. "
            + "The first player to do this is the winner.\n";

    public LabyrinthHelpFrame() {
        setTitle("Help Frame");
        setSize(1200, 625);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon backgroundIcon = new ImageIcon("image/Helpbackground.jpeg");
        Image resizeImage = backgroundIcon.getImage().getScaledInstance(1200, 625, Image.SCALE_SMOOTH);
        JLabel background = new JLabel(new ImageIcon(resizeImage));
        background.setLayout(new BorderLayout());
        add(background);

        JLabel helpText = new JLabel("<html>" + helpTextContent.replaceAll("\n", "<br>") + "</html>");
        helpText.setHorizontalAlignment(JLabel.LEFT);
        helpText.setVerticalAlignment(JLabel.TOP);

        helpText.setSize(new Dimension(1150, 1000)); // Ensure content size is larger than the scroll pane

        JPanel textPanel = new JPanel();
        textPanel.setOpaque(false);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.add(helpText);

        JScrollPane scrollPane = new JScrollPane(textPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        background.add(scrollPane, BorderLayout.CENTER);

        setVisible(false);
    }

}