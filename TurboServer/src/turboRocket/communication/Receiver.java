package turboRocket.communication;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.Socket;

import turboRocket.KeyPress;
import turboRocket.controllers.GameController;
import turboRocket.controllers.KeyEventHandler;

public class Receiver implements Runnable{

	private Socket socket;
	private GameController gameController;
	private KeyEventHandler keyListener;
	public Receiver(Socket socket, GameController gameController, KeyEventHandler keyListener){
		this.socket = socket;
		this.keyListener = keyListener;
	}

	public void run() {
		KeyPress kpd = null;
		try {
			InputStream is = socket.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(is);
			
			while(true){
				kpd = (KeyPress) ois.readObject();
				keyListener.addKey(kpd);
			}
		} catch (ClassNotFoundException e) {
			System.err.println("class not found: " + kpd.getClass());
		}catch (EOFException e){
			System.err.println("client closed connection on socket: " + socket.getLocalAddress() + ";" + socket.getPort());
			gameController.removePlayer(socket);
		}catch (IOException e) {
			System.err.println("io exception");	
		} 
	}

}
