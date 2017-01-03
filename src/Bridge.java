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
        JSONObject obj = new JSONObject();
        obj.put("name", "test");
        obj.put("age", new Integer(100));
        try {
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectOutputStream out2 = new ObjectOutputStream(socket2.getOutputStream());

            out.writeObject(obj);
            out2.writeObject(obj);
        }catch(Exception e){}
        // TODO Auto-generated method stub


    }

}



