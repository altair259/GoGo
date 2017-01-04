import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import javax.swing.JPanel;

import org.json.simple.JSONObject;

@SuppressWarnings("serial")
class GoPanel extends JPanel {

    Square[][] board;
    boolean whiteToMove;
	Socket socket = null;
    ObjectInputStream in = null;
    ObjectOutputStream out = null;
    JSONObject json = null;

    GoPanel(int dimension, Socket socket) {
    	this.socket = socket;
    	try {
	    	out = new ObjectOutputStream(this.socket.getOutputStream());
			in = new ObjectInputStream(this.socket.getInputStream());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	System.out.println("tutaj doszedłem GoPanel");
        board = new Square[dimension][dimension];
        whiteToMove = true;
        initBoard(dimension);
    }

    private void initBoard(int dimension) {
        super.setLayout(new GridLayout(dimension, dimension));
        for(int row = 0; row < dimension; row++) {
            for(int col = 0; col < dimension; col++) {
                board[row][col] = new Square(row, col);
                super.add(board[row][col]);
            }
        }
        repaint();
    }

    private class Square extends JPanel {

        Stone stone;
        final int row;
        final int col;

        Square(int r, int c) {
            stone = Stone.NONE;
            row = r;
            col = c;
            super.addMouseListener(new MouseAdapter(){
				@Override
                public void mouseClicked(MouseEvent me) {
              try {
	            	json = new JSONObject();
	            	JSONObject transmitData = new JSONObject();
	        		transmitData.put("moveX", row);
	        		transmitData.put("moveY", col);
	        		transmitData.put("Color", "mkyong.com");
					out.writeObject(json);
	        		JSONObject recived1Data = (JSONObject) in.readObject();
	    			boolean legal = (boolean) recived1Data.get("legal");

	        		
      		}catch(Exception e){}
					
                	/*try {
            			ObjectInputStream in = new ObjectInputStream(
            					socket.getInputStream());
            			
                    if(stone != Stone.NONE) return;
                    stone = whiteToMove ? Stone.WHITE : Stone.BLACK;
                    whiteToMove = !whiteToMove;
                    repaint();*/
                }
            });
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int w = super.getWidth();
            int h = super.getHeight();
            g.setColor(new Color(194,187,167));
            g.fillRect(0, 0, w, h);
            g.setColor(Color.BLACK);   
            if(row == 0 || row == board.length-1 || col == 0 || col == board.length-1) {
                if(col == 0) {
                    g.drawLine(w/2, h/2, w, h/2);
                    if(row == 0) g.drawLine(w/2, h/2, w/2, h);
                    else if(row == 18) g.drawLine(w/2, h/2, w/2, 0);
                    else g.drawLine(w/2, 0, w/2, h);
                }
                else if(col == 18) {
                    g.drawLine(0, h/2, w/2, h/2);
                    if(row == 0) g.drawLine(w/2, h/2, w/2, h);
                    else if(row == 18) g.drawLine(w/2, h/2, w/2, 0);
                    else g.drawLine(w/2, 0, w/2, h);
                }
                else if(row == 0) {
                    g.drawLine(0, h/2, w, h/2);
                    g.drawLine(w/2, h/2, w/2, h);
                }
                else {
                    g.drawLine(0, h/2, w, h/2);
                    g.drawLine(w/2, h/2, w/2, 0);
                }
            } else {
                g.drawLine(0, h/2, w, h/2);
                g.drawLine(w/2, 0, w/2, h);
            }
            stone.paint(g, w);
        }
    }
}
