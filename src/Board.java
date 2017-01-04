import java.awt.*;
import java.awt.event.*;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Random;
import javax.swing.*;

public class Board {
//   public static void main(String[] args) {
	Socket socket = null;
	GoPanel panel = null;
    public Board(Socket socket){
    	panel = new GoPanel(19, socket);
    	this.socket = socket;
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);
    	System.out.println("tutaj doszedłem w Board!");
        frame.setSize(1200, 1250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
        panel.startGame();
        System.out.print("tutaj doszedłem w Board11!");
    }
}
