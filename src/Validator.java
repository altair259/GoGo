
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;

public class Validator {
	
	private HashSet<Point> visited = null;
	private Stone[][] tab = new Stone[19][19];
	private ArrayList<Point> forRemove = new ArrayList<Point>();
	
	
	 public boolean isLegal(int x, int y, Stone color) {
		 System.out.println(color);
		 if(tab[x][y] == null && isSuicide(x,y,color)){
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
//	
		
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
		
	public boolean DFS(int x, int y, Stone color){
		if(visited.contains(new Point(x,y)) || x == -1 || x == 19 || y == -1 || y == 19 || (tab[x][y] != null && tab[x][y] != color)){
			System.out.println(x + " " + y + " " + color + " return false");
			return false;
		}
		System.out.println(x + " " + y + " " + color);
		visited.add(new Point(x,y));
		if(tab[x][y] == null){
			System.out.println("return true");
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
