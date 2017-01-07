import java.awt.Point;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;

import org.json.simple.JSONObject;

public class Bridge implements Runnable {
	private Socket socket2;
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private ObjectInputStream in2;
	private ObjectOutputStream out2;
	private Validator validator = null;
	private Boolean playerColor = null;
	private JSONObject recivedData = null;
	private JSONObject transmitData = null;
	private ArrayList<Point> forRemove;
	
	public Bridge(Socket socket, Socket socket2) {
        this.socket = socket;
        this.socket2 = socket2;
		validator = new Validator();
        try{
        	in = new ObjectInputStream(socket.getInputStream());
        	out = new ObjectOutputStream(socket.getOutputStream());
        	in2 = new ObjectInputStream(socket2.getInputStream());
        	out2 = new ObjectOutputStream(socket2.getOutputStream());
        }catch(Exception e){
        	e.printStackTrace();
        }
		
    }
	@Override
	public void run() {
		System.out.println("Ruszył wątek panowie!");
		try {
			int moveX, moveY;

			transmitData = new JSONObject();
			transmitData.put("color", Stone.WHITE );
			out.writeObject(transmitData);
			transmitData.put("color", Stone.BLACK );
			out2.writeObject(transmitData);
			
			while(true){

				
				transmitData = new JSONObject();
				recivedData = new JSONObject();
				do{
					recivedData = (JSONObject) in.readObject();
				}while(! validator.isLegal(
							(int)recivedData.get("moveX"),
							(int)recivedData.get("moveY"),
							Stone.BLACK
						));

				System.out.println(recivedData);
				forRemove = validator.getPlacesForRemove();
				transmitData.put("moveX", recivedData.get("moveX"));
				transmitData.put("moveY", recivedData.get("moveY"));
				transmitData.put("color", Stone.BLACK);
				transmitData.put("operation", "set");
				transmitData.put("listForRemove", forRemove);
				out.writeObject(transmitData);
				out.flush();
				out2.writeObject(transmitData);
				out2.flush();
				
				
				System.out.println(forRemove);

				
				
				
				transmitData = new JSONObject();
				recivedData = new JSONObject();
				do{
					recivedData = (JSONObject) in2.readObject();
					System.out.println(recivedData);
				}while(! validator.isLegal(
							(int)recivedData.get("moveX"),
							(int)recivedData.get("moveY"),
							Stone.WHITE
						));

				System.out.println(recivedData);
				forRemove = validator.getPlacesForRemove();
				transmitData.put("moveX", recivedData.get("moveX"));
				transmitData.put("moveY", recivedData.get("moveY"));
				transmitData.put("color", Stone.WHITE);
				transmitData.put("listForRemove", forRemove);
				transmitData.put("operation", "set");
				out.writeObject(transmitData);
				out.flush();
				out2.writeObject(transmitData);
				out2.flush();
				
			}
		}catch(Exception e){}
	}

}
