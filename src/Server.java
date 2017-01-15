import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

class ConnectionHandler {
	
	public Bridge getConnection(ServerSocket serverSocket) throws IOException {
		System.out.println("Server started");
		Socket socket = serverSocket.accept();
		System.out.println("First accepted");
		Socket socket2 = serverSocket.accept();
		System.out.println("Second accepted");
		Bridge bridge = new Bridge(socket, socket2);
		System.out.println("Created Runnable Object");
		return bridge;
	}
	
	public void startConnection(Bridge bridge) throws InterruptedException {
		(new Thread(bridge)).start();
		while(true){
			Thread.sleep(40);
//			serverSocket.accept();
		}
	}
}

public class Server {

	static ServerSocket serverSocket;
	static Socket socket;
	static DataOutputStream out;
	static Socket socket2;
	
	public void init(ConnectionHandler cf, ServerSocket ss) throws Exception{
		cf.startConnection(cf.getConnection(ss));
	}

	public static void main(String[] args) throws Exception{
		Server server = new Server();
		System.out.println("Starting server..");
		server.init(new ConnectionHandler(), new ServerSocket(7777));
	}
}

