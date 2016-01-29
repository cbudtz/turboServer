package turboRocket.communication;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import turboRocket.controllers.GameController;

public class TurboServer implements Runnable{
	
	private GameController gameController;
	public TurboServer(GameController gameController){
		this.gameController = gameController;
	}
	public void run(){
		int port = 5151;
		ServerSocket ss = null;
		
		try {

			ss = new ServerSocket(port);
			while(true){
				Socket s = ss.accept();
				gameController.addPlayer(s);
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
