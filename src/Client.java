import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import org.json.simple.JSONObject;

public class Client {
	public Client(){}
	
	public static void main(String[] args)throws Exception{
		Socket socket = new Socket("127.0.0.1", 7777);
		JSONObject obj = new JSONObject();
		Board board = null;
		
		try {
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			
//			obj = (JSONObject) in.readObject();
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		
		board = new Board(socket);
	}
}
