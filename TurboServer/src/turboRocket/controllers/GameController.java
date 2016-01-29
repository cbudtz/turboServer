package turboRocket.controllers;

import java.net.Socket;

public interface GameController {

	void addPlayer(Socket socket);
	void removePlayer(Socket socket);

}
