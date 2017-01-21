package program;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * The Class ConnectionHandler.
 */
public class ConnectionHandler {
	
	/**
	 * Gets the connection.
	 *
	 * @param serverSocket the server socket
	 * @return the connection
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
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
	
	/**
	 * Start connection.
	 *
	 * @param bridge the bridge
	 * @throws InterruptedException the interrupted exception
	 */
	public void startConnection(Bridge bridge) throws InterruptedException {
		(new Thread(bridge)).start();
		while(true){
			Thread.sleep(40);
//			serverSocket.accept();
		}
	}
}
