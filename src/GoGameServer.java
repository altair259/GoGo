import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class GoGameServer {

	static ServerSocket serverSocket;
	static Socket socket;
	static DataOutputStream out;
	static Socket socket2;
	

	public static void main(String[] args)throws Exception{
		 Socket socket = null;
		 Socket socket2 = null;
		System.out.println("Starting server..");
		serverSocket = new ServerSocket(7777);
		System.out.println("Server started");
		socket = serverSocket.accept();
		socket2 = serverSocket.accept();
		Bridge bridge = new Bridge(socket, socket2);
		Thread thread = new Thread(bridge);
		thread.run();
		while(1==1){
			serverSocket.accept();
		}
	}
}



