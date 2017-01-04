import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

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
		System.out.println("First accepted");
		socket2 = serverSocket.accept();
		System.out.println("Second accepted");
		Bridge bridge = new Bridge(socket, socket2);
		System.out.println("Created Runnable Object");
		(new Thread(bridge)).start();
		while(true){
			Thread.sleep(40);
//			serverSocket.accept();
		}
	}
}

