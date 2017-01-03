import java.awt.*;
import java.awt.event.*;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Random;
import javax.swing.*;

public class Board {
//    public static void main(String[] args) {
	Socket socket = null;
    public Board(Socket socket){
    	this.socket = socket;
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        frame.add(new GoPanel(19, socket), BorderLayout.CENTER);
        frame.setSize(1200, 1250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}




enum Stone { 

    BLACK(Color.BLACK), WHITE(Color.WHITE), NONE(null);

    final Color color;
    private final static Random rand = new Random();

    private Stone(Color c) {
        color = c;
    }

    public void paint(Graphics g, int dimension) {
        if(this == NONE) return;
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(color);
        int x = 5;
        g2d.fillOval(rand.nextInt(x), rand.nextInt(x), dimension-x, dimension-x);
    }
}
