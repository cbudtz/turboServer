package turboRocket;

import java.awt.event.KeyEvent;
import java.io.Serializable;

public interface KeyPress extends Serializable {

	/**
	 * Used to identify which ship keypress belongs to
	 * @return a unique identifier for player
	 */
	String getShipId();
	/**
	 * This should be compared to {@link KeyEvent}.VK_[KEY] 
	 * @return an identifier for which key is pressed
	 */
	int getKey();
	/**
	 * used to identify if key should be added or removed from command list 
	 * @return
	 */
	boolean isKeyDown();
	boolean isKeyUp();
}
