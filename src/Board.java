import java.awt.*;
import java.awt.event.*;
import java.net.Socket;
import javax.swing.*;

public class Board {
//   public static void main(String[] args) {
	Socket socket = null;
	GoPanel panel = null;
    public Board(Socket socket){
    	panel = new GoPanel(19, socket);
    	this.socket = socket;
        JFrame frame = new JFrame();
        Container pane = frame.getContentPane();
        pane.setLayout(new BoxLayout(pane, BoxLayout.Y_AXIS));
        pane.add(panel, BorderLayout.CENTER);
        JButton button = new JButton("Pasuj xD");
        button.setMinimumSize(new Dimension(100, 50));
        button.setSize(new Dimension(100, 50));
        pane.add(button, pane.CENTER_ALIGNMENT);
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
}
