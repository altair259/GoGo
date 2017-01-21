package program;
import java.awt.*;
import java.awt.event.*;
import java.net.Socket;
import javax.swing.*;

/**
 * The Class Board.
 */
public class Board {

/** The socket. */
//   public static void main(String[] args) {
	Socket socket = null;
	
	/** The panel. */
	GoPanel panel = null;
    
    /** The points. */
    JLabel points = null;
    
    /**
     * Instantiates a new board.
     *
     * @param socket the socket
     */
    public Board(Socket socket){
    	panel = new GoPanel(19, socket ,this);
    	this.socket = socket;
        JFrame frame = new JFrame();
        Container pane = frame.getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        pane.add(panel, BorderLayout.CENTER);
        points = new JLabel("test");
        
        JButton button = new JButton("Pasuj xD");
        button.setMinimumSize(new Dimension(100, 50));
        button.setSize(new Dimension(100, 50));
        pane.add(button, pane.CENTER_ALIGNMENT);
        pane.add(points, pane.CENTER_ALIGNMENT);
        pane.setMinimumSize(new Dimension(1200, 1330));
        frame.setMinimumSize(new Dimension(1200, 1330));
        frame.setSize(new Dimension(1200, 1330));
        
        
    	System.out.println("tutaj doszedłem w Board!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
        panel.startGame();
        System.out.print("tutaj doszedłem w Board11!");
    }
    
    	/**
	     * Sets the points.
	     *
	     * @param myPoints the my points
	     * @param opponentPoints the opponent points
	     */
	    public void setPoints(float myPoints , float opponentPoints){
    		this.points.setText("Your points: "+ Float.toString(myPoints)+ " Opponent points: "+ Float.toString(opponentPoints) );
    	}
}
