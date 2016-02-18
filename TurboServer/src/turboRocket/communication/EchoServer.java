package turboRocket.communication;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import dto.GameState;
import dto.GameState.PlayerData;
import dto.KeyPressData;


public class EchoServer implements Runnable{

	int port = 5151;
	ServerSocket ss = null;
	private volatile GameState gameState;

	public EchoServer() {
		gameState = GameState.getDefaultGameState();
	}

	@Override
	public void run() {

		while (true){
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
						System.out.println("Data received:" + kpd);
						for (PlayerData p : gameState.getPlayers().values()){
							//Testing....
							System.out.println(p.name + " " + p.health);
							gameState.getPlayers().get(p.sessionID).health--;
							System.out.println(gameState.getPlayers().get(p.sessionID).health);
							
						}
					} catch (ClassNotFoundException e) {
						System.err.println("Error reading KeyPressData:" + kpd);
					}
					System.out.println(kpd);
					objectOutputStream.writeObject(gameState);
					objectOutputStream.reset();
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

	}

	public static void main(String[] args) {
		new EchoServer().run();
	}
}


