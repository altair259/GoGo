import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;

import org.json.simple.JSONObject;

public class Bridge implements Runnable {
	private Socket socket2;
	private Socket socket;
	public void Socket(Socket socket, Socket socket2) {
        this.socket = socket;
        this.socket2 = socket2;}
	@Override
	public void run() {
		JSONObject obj = new JSONObject();
		obj.put("key1", "test");
		obj.put("key2", new Integer(100));
		try {
			ObjectOutputStream out = new ObjectOutputStream(
		        socket.getOutputStream());
		        
		    out.writeObject(obj);
		}catch(Exception e){}
		// TODO Auto-generated method stub
		
		
	}

}

