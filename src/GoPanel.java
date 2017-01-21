package program;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JPanel;

import org.json.simple.JSONObject;

// TODO: Auto-generated Javadoc
/**
 * The Class GoPanel.
 */
@SuppressWarnings("serial")
class GoPanel extends JPanel {
	
	/** The my points. */
	float myPoints = 0;
	
	/** The opponent points. */
	float opponentPoints = 0;
    
    /** The board. */
    Square[][] board;
    
    /** The color. */
    Stone color = null;
    
    /** The color for move. */
    Stone colorForMove = Stone.WHITE;
	
	/** The socket. */
	Socket socket = null;
    
    /** The in. */
    ObjectInputStream in = null;
    
    /** The out. */
    ObjectOutputStream out = null;
    
    /** The json. */
    JSONObject json = null;
    
    /** The recived data. */
    JSONObject recivedData = null;
	
	/** The parent board. */
	private Board parentBoard = null;

    /**
     * Instantiates a new go panel.
     *
     * @param dimension the dimension
     * @param socket the socket
     * @param parentBoard the parent board
     */
    GoPanel(int dimension, Socket socket, Board parentBoard) {
    	this.parentBoard  = parentBoard;
    	this.socket = socket;
    	try {
	    	out = new ObjectOutputStream(this.socket.getOutputStream());
			in = new ObjectInputStream(this.socket.getInputStream());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        board = new Square[dimension][dimension];
        initBoard(dimension);
        
    }
    
    /**
     * Start game.
     */
    public void startGame(){
    	try{
    		recivedData = (JSONObject)in.readObject();
    		System.out.println("takietam kolory " + recivedData);
    		color = (Stone) recivedData.get("color");
    		(new Thread(new Game())).start();
    	} catch (Exception e){}
    }
    
    /**
     * The Class Game.
     */
    private class Game implements Runnable{
    	
    	/* (non-Javadoc)
	     * @see java.lang.Runnable#run()
	     */
	    public void run(){
	        try{
	        	while(true){
	        		recivedData = (JSONObject)in.readObject();
	        		System.out.println(recivedData);
	        		if(recivedData.get("operation").equals("set")){
	        			int x = (int)recivedData.get("moveX");
	        			int y = (int)recivedData.get("moveY");
	        			Stone stone = (Stone)recivedData.get("color");
	        			board[x][y].setStone(stone);
	        			colorForMove = (Stone)(recivedData.get("color"));
	        			for(Point p : (ArrayList<Point>)recivedData.get("listForRemove")){
	        				board[p.x][p.y].setStone(Stone.NONE);
	        				if(color == colorForMove){
	        					opponentPoints++;	
	        				}else{
	        					myPoints++;
	        				}
	        				parentBoard.setPoints(myPoints, opponentPoints);
	        			}
	        		}
	        		repaint();
	        	}
	        }catch(Exception e){
	        	e.printStackTrace();
	        }
    	}
    }

    /**
     * Inits the board.
     *
     * @param dimension the dimension
     */
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
    
    

    /**
     * The Class Square.
     */
    private class Square extends JPanel {

        /** The stone. */
        Stone stone;
        
        /** The row. */
        final int row;
        
        /** The col. */
        final int col;
        
        /**
         * Sets the stone.
         *
         * @param s the new stone
         */
        void setStone(Stone s){ stone = s;}

        /**
         * Instantiates a new square.
         *
         * @param r the r
         * @param c the c
         */
        Square(int r, int c) {
            stone = Stone.NONE;
            row = r;
            col = c;
            super.addMouseListener(new MouseAdapter(){
				@Override
                public void mouseClicked(MouseEvent me) {
					try {
						JSONObject transmitData = new JSONObject();
						transmitData.put("moveX", row);
						transmitData.put("moveY", col);
						if(color.equals(colorForMove)){
							out.writeObject(transmitData);
						}
	        		
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

        /* (non-Javadoc)
         * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
         */
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int w = super.getWidth();
            int h = super.getHeight();
           // System.out.println(w+ ""+h);
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
