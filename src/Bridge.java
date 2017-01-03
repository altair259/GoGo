import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import org.json.simple.JSONObject;

public class Bridge implements Runnable {
	private Socket socket2;
	private Socket socket;
	public Bridge(Socket socket, Socket socket2) {
        this.socket = socket;
        this.socket2 = socket2;
    }
	@Override
	public void run() {
		
		try {
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
			ObjectOutputStream out2 = new ObjectOutputStream(socket2.getOutputStream());
		        
			JSONObject recivedData = (JSONObject) in.readObject();
			int moveX = (int) recivedData.get("moveX");
			int moveY = (int) recivedData.get("moveY");
			
			
			out2.writeObject();
		}catch(Exception e){}
		// TODO Auto-generated method stub
		
		
	}

}
