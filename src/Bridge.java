import org.json.simple.JSONObject;

public class Bridge implements Runnable {
	private Socket socket2;
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	private ObjectInputStream in2;
	private ObjectOutputStream out2;
	public Bridge(Socket socket, Socket socket2) {
        this.socket = socket;
        this.socket2 = socket2;
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
		        
			JSONObject recivedData = (JSONObject) in.readObject();
			int moveX = (int) recivedData.get("moveX");
			int moveY = (int) recivedData.get("moveY");
			Validator validator = new Validator();
			boolean check = validator.isLegal(moveX,moveY);
			JSONObject transmit1Data = new JSONObject();
			transmit1Data.put("legal" , check);
			
			out.writeObject(transmit1Data);
		}catch(Exception e){}
		// TODO Auto-generated method stub
		
		
	}

	}
