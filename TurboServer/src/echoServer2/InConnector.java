package echoServer2;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class InConnector implements Runnable {
	
	private ServerSocket serverSocket;

	@Override
	public void run() {
		try {
			serverSocket = new ServerSocket(5151);
			Socket incomingSocket = serverSocket.accept();
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		
		
	}

}
