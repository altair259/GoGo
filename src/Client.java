package program;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import org.json.simple.JSONObject;

// TODO: Auto-generated Javadoc
/**
 * The Class Client.
 */
public class Client {
	
	/**
	 * Instantiates a new client.
	 */
	public Client(){}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main(String[] args)throws Exception{
		Socket socket = new Socket("127.0.0.1", 7777);
		JSONObject obj = new JSONObject();
		Board board = null;
		
//		try {
//			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			
//			obj = (JSONObject) in.readObject();
//		}catch(Exception e){
//			System.out.println(e.getMessage());
//		}
		
		board = new Board(socket);
	}
}
