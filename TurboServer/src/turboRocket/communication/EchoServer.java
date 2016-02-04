package turboRocket.communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import connectionHandler.GameState;
import connectionHandler.KeyPressData;

public class EchoServer implements Runnable{

	int port = 5151;
	ServerSocket ss = null;



	@Override
	public void run() {


		try {

			ss = new ServerSocket(port);
			System.out.println("Server socket opened on port " + ss.getLocalPort());
			Socket s = ss.accept();
			System.out.println("Connection established on port " + s.getLocalPort());
			ObjectInputStream objectInStream = new ObjectInputStream(s.getInputStream());
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(s.getOutputStream());
			while(true){	
				KeyPressData kpd = null;
				try {
					System.out.println("Server waiting for data");
					kpd = (KeyPressData) objectInStream.readObject();
					System.out.println("Data received");
				} catch (ClassNotFoundException e) {
					System.err.println("Errpr reading KeyPressData");
				}
				System.out.println(kpd);
				//objectOutputStream.writeObject(new GameState());
				System.out.println("Wrote Gamestate");
			}

		} catch (IOException e) {
			System.err.println("failed on open socket");
		} finally {

			if(ss != null)
				try {
					ss.close();
				} catch (IOException e) {
					System.err.println("failed on closing server socket");
				}
		}

	}
	
	public static void main(String[] args) {
		new EchoServer().run();
	}
}


