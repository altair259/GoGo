package program;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Server {

	static ServerSocket serverSocket;
	static Socket socket;
	static DataOutputStream out;
	static Socket socket2;
	
	public void init(ConnectionHandler cf, ServerSocket ss) throws Exception{
		cf.startConnection(cf.getConnection(ss));
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main(String[] args) throws Exception{
		Server server = new Server();
		System.out.println("Starting server..");
		server.init(new ConnectionHandler(), new ServerSocket(7777));
	}
}


