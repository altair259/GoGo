
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;

public class Validator {
	
	private HashSet<Point> visited = null;
	private Stone[][] tab = new Stone[19][19];
	private ArrayList<Point> forRemove = new ArrayList<Point>();
	private Stone[][] backupTab1 = new Stone[19][19];
	private Stone[][] backupTab2 = new Stone[19][19];

	
	 public boolean isLegal(int x, int y, Stone color) {
		 System.out.println(color);
		 if(tab[x][y] == null && isSuicide(x,y,color) /*&& !checkKo(x,y,color)*/){
			 tab[x][y] = color;
			 updateStones(x,y,color);
			 return true;
		 }else{
			 return false;
		 }
	}
	void updateStones(int x, int y, Stone color){
		Stone invertedColor = (color == Stone.WHITE ? Stone.BLACK : Stone.WHITE);
		if(DFS(x,y+1, invertedColor) == false){
			removeGroupOfStones(x,y+1,invertedColor);
		}
		if(DFS(x,y-1, invertedColor) == false){
			removeGroupOfStones(x,y-1,invertedColor);
		}
		if(DFS(x+1,y, invertedColor) == false){
			removeGroupOfStones(x+1,y,invertedColor);
		}
		if(DFS(x-1,y, invertedColor) == false){
			removeGroupOfStones(x-1,y,invertedColor);
		}
	}
	
	public ArrayList<Point> getPlacesForRemove(){
		ArrayList<Point> result = forRemove;
		forRemove = new ArrayList<Point>();
		return result;
	}
	
	private void removeGroupOfStones(int x, int y ,Stone color){
		System.out.print(x + " " + y);
		if( x == -1 || x == 19 || y == -1 || y == 19 || tab[x][y] == null || tab[x][y] != color){
			return;
		}
	
		
		forRemove.add(new Point(x,y));
		tab[x][y]= null;
		
		removeGroupOfStones(x,y+1,color);
		removeGroupOfStones(x,y-1,color);
		removeGroupOfStones(x+1,y,color);
		removeGroupOfStones(x-1,y,color);
	}
	
	
	public boolean isSuicide( int x, int y, Stone color){
		tab[x][y] = color;
		visited = new HashSet<Point>();
		boolean result = DFS(x,y,color);
		tab[x][y] = null;
		return result;
	}
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
	public boolean checkKo(int k, int l, Stone color){
		boolean isKo;
		Stone [][] backupTab3 = new Stone[19][19];
		for(int i=0; i<tab.length; i++){ 
            for(int j=0; j<tab[i].length; j++){ 
                backupTab3[i][j] = tab[i][j];
            }
		}
		tab[k][l] = color;
		updateStones(k,l,color);
		isKo = true;
		for(int i=0; i<tab.length; i++){ 
            for(int j=0; j<tab[i].length; j++){ 
                if(tab[k][l] != backupTab2[i][j]){
                	 isKo = false;
                } 	
            }
	
		}
		for(int i=0; i<backupTab3.length; i++){ 
            for(int j=0; j<backupTab3[i].length; j++){ 
                tab[i][j] = backupTab3[i][j];
            }
		}
		return isKo;
	}
	
	
	
	public boolean DFS(int x, int y, Stone color){
		if(visited.contains(new Point(x,y)) || x == -1 || x == 19 || y == -1 || y == 19 || (tab[x][y] != null && tab[x][y] != color)){
			System.out.println(x + " " + y + " " + color + " return false");
			return false;
		}
		
		visited.add(new Point(x,y));
		if(tab[x][y] == null){
			return true;
		}
		return (
				DFS(x,y+1, color) ||
				DFS(x,y-1, color) ||
				DFS(x+1,y, color) ||
				DFS(x-1,y,color)
		);
	}
	
}
