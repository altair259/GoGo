

import java.io.IOException;
import java.net.ServerSocket;

import org.junit.Test;
import org.mockito.Mockito;

public class ServerTest {

	
	@Test
	public void test() {
		Server server = new Server();
		ServerSocket fakeServerSocket = Mockito.mock(ServerSocket.class);
		ConnectionHandler fakeConnectionHandler = Mockito.mock(ConnectionHandler.class);
		Bridge fakeBridge = Mockito.mock(Bridge.class);
		try {
			Mockito.when(fakeConnectionHandler.getConnection(fakeServerSocket)).thenReturn(fakeBridge);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			server.init(fakeConnectionHandler, fakeServerSocket);	
			Mockito.verify(fakeConnectionHandler, Mockito.times(1)).getConnection(fakeServerSocket);
			Mockito.verify(fakeConnectionHandler, Mockito.times(1)).startConnection(fakeBridge);
		} catch (Exception e) {
			
		}
		
	}
}
