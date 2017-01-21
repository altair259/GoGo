package program;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;

import com.sun.prism.paint.Color;

public class Validator {
	
	private HashSet<Point> visited = null;
	private Stone[][] tab = new Stone[19][19];
	private ArrayList<Point> forRemove = new ArrayList<Point>();
	private Stone[][] backupTab1 = new Stone[19][19];
	private Stone[][] backupTab2 = new Stone[19][19];

	
	 /**
 	 * Checks if is legal.
 	 *
 	 * @param x the x
 	 * @param y the y
 	 * @param color the color
 	 * @return true, if is legal
 	 */
 	public boolean isLegal(int x, int y, Stone color) {
		 System.out.println(color);
		 if(tab[x][y] == null && !isSuicide(x,y,color) && !checkKo(x,y,color)){
			 System.out.println(x + ", " + y + " is OK!");
			 tab[x][y] = color;
			 updateStones(x,y,color);
			 this.backupBoard();
			 return true;
		 }else{
			 return false;
		 }
	}
	
	/**
	 * Update stones.
	 *
	 * @param x the x
	 * @param y the y
	 * @param color the color
	 */
	void updateStones(int x, int y, Stone color){
		Stone invertedColor = (color == Stone.WHITE ? Stone.BLACK : Stone.WHITE);
		
		if(!DFS(x,y+1, invertedColor)){
			removeGroupOfStones(x,y+1,invertedColor);
		}
		if(!DFS(x,y-1, invertedColor)){
			removeGroupOfStones(x,y-1,invertedColor);
		}
		if(!DFS(x+1,y, invertedColor)){
			removeGroupOfStones(x+1,y,invertedColor);
		}
		if(!DFS(x-1,y, invertedColor)){
			removeGroupOfStones(x-1,y,invertedColor);
		}
	}
	
	/**
	 * Gets the places for remove.
	 *
	 * @return the places for remove
	 */
	public ArrayList<Point> getPlacesForRemove(){
		ArrayList<Point> result = forRemove;
		forRemove = new ArrayList<Point>();
		return result;
	}
	
	/**
	 * Removes the group of stones.
	 *
	 * @param x the x
	 * @param y the y
	 * @param color the color
	 */
	private void removeGroupOfStones(int x, int y ,Stone color){
		
		if( x == -1 || x == 19 || y == -1 || y == 19 || tab[x][y] == null || tab[x][y] != color){
			return;
		}
		System.out.println("Remove " + x + " " + y);
	
		
		forRemove.add(new Point(x,y));
		tab[x][y] = null;
		
		removeGroupOfStones(x,y+1,color);
		removeGroupOfStones(x,y-1,color);
		removeGroupOfStones(x+1,y,color);
		removeGroupOfStones(x-1,y,color);
		
	}
	
	
	/**
	 * Checks if is suicide.
	 *
	 * @param x the x
	 * @param y the y
	 * @param color the color
	 * @return true, if is suicide
	 */
	public boolean isSuicide( int x, int y, Stone color){
		tab[x][y] = color;
		visited = new HashSet<Point>();
		Stone invertedColor = (color == Stone.WHITE ? Stone.BLACK : Stone.WHITE);
		boolean result = true;
		result &= !DFS(x,y,color);
		result &= DFS(x+1, y, invertedColor);
		result &= DFS(x-1, y, invertedColor);
		result &= DFS(x, y+1, invertedColor);
		result &= DFS(x, y-1, invertedColor);
		tab[x][y] = null;
		return result;
	}
	
	/**
	 * Backup board.
	 */
	public void backupBoard(){
		for(int i=0; i<backupTab1.length; i++){ 
            for(int j=0; j<backupTab1[i].length; j++) 
                backupTab2[i][j] = backupTab1[i][j];
		}
		for(int i=0; i<tab.length; i++){ 
            for(int j=0; j<tab[i].length; j++) 
                backupTab1[i][j] = tab[i][j]; 
		
		}
	}
	
	/**
	 * Check ko.
	 *
	 * @param x the x
	 * @param y the y
	 * @param color the color
	 * @return true, if successful
	 */
	public boolean checkKo(int x, int y, Stone color){
		boolean isKo = false;
		Stone [][] backupTab3 = new Stone[19][19];
		for(int i = 0; i < tab.length; i++){ 
            for(int j = 0; j < tab[i].length; j++){
            	backupTab3[i][j] = tab[i][j];
            }
		}

		System.out.println("AAAAAAAAAAAAA " + x + ", " + y + " checking KO");
		tab[x][y] = color;
		forRemove = new ArrayList<Point>();
		updateStones(x, y, color);
		forRemove = new ArrayList<Point>();
		isKo = true;
		for(int i=0; i<tab.length; i++){ 
            for(int j=0; j<tab[i].length; j++){ 
                if(tab[i][j] != backupTab2[i][j]){
                	System.out.println("tablice różnią się w " + i + " " + j + " :) ");
                	 isKo = false;
                } 	
            }
	
		}
		for(int i = 0; i < tab.length; i++){ 
            for(int j = 0; j < tab[i].length; j++){
            	tab[i][j] = backupTab3[i][j];
            }
		}
		return isKo;
	}
	
	
	/**
	 * Check if Point(x,y) has path to null.
	 *
	 * @param x the x
	 * @param y the y
	 * @param color the color
	 * @return True if Point(x,y) has path to null.
	 */
	
	private boolean DFS(int x, int y, Stone color){
		visited = new HashSet<Point>();
		boolean result = DFSRecursive(x, y, color);
		System.out.println("DFS from " + x + ", " + y + " result: " + result);
		return result;
	}
	
	/**
	 * DFS recursive.
	 *
	 * @param x the x
	 * @param y the y
	 * @param color the color
	 * @return true, if successful
	 */
	private boolean DFSRecursive(int x, int y, Stone color){
		if(visited.contains(new Point(x,y)) || x == -1 || x == 19 || y == -1 || y == 19 || (tab[x][y] != null && tab[x][y] != color)){
			System.out.println(x + " " + y + " " + (color==null?"null":color) + " return false");
			return false;
		}
		
		visited.add(new Point(x,y));
		if(tab[x][y] == null){
			return true;
		}
		return (
				DFSRecursive(x,y+1, color) ||
				DFSRecursive(x,y-1, color) ||
				DFSRecursive(x+1,y, color) ||
				DFSRecursive(x-1,y,color)
		);
	}
	
}
