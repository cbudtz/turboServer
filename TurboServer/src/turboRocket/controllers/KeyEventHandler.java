package turboRocket.controllers;

import static java.awt.event.KeyEvent.VK_1;
import static java.awt.event.KeyEvent.VK_2;
import static java.awt.event.KeyEvent.VK_3;
import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;
import static java.awt.event.KeyEvent.VK_UP;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.Timer;

import org.jbox2d.common.Mat22;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.World;

import turboRocket.KeyPress;
import static java.lang.Math.*;

public class KeyEventHandler {

	private Body ship;
	private Queue<KeyPress> cmdQueue = new LinkedList<KeyPress>();
	private int COMMAND_UPDATE_RATE = 50;
	private float rotationStep = 0.00001f; // radians
	private Vec2 forceVec = new Vec2(0, 10);


	public KeyEventHandler(Body ship){
		this.ship = ship;
	}

	public void start(){
		executeCommands();
	}
	
	private void executeCommands(){
		new Timer(COMMAND_UPDATE_RATE, new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				float angle = ship.m_sweep.a;
				Mat22 r = new Mat22(new Vec2((float) cos(angle),(float) sin(angle)), new Vec2((float)-sin(angle),(float) cos(angle)));
				for(KeyPress k : cmdQueue){
					switch(k.getKey()){
					case VK_UP:
						ship.applyForceToCenter(r.mul(forceVec));
						break;
					case VK_DOWN:
						System.err.println("not implemented");
						break;
					case VK_LEFT:
						ship.m_sweep.a += rotationStep;
						break;
					case VK_RIGHT:
						ship.m_sweep.a -= rotationStep;
						break;
					case VK_1:
						System.err.println("not implemented");
						break;
					case VK_2:
						System.err.println("not implemented");
						break;
					case VK_3:
						System.out.println("not implemented");
						break;
					default:
						System.err.println("invalid command");
						break;
					}
				}
			}
		}).start();
	}
	public void addKey(KeyPress key){
		// only add allowed keys to queue
		switch(key.getKey()){
		case VK_UP:
		case VK_DOWN:
		case VK_LEFT:
		case VK_RIGHT:
		case VK_1:
		case VK_2:
		case VK_3:
			if(key.isKeyDown())
				cmdQueue.add(key);
			break;
		default:
			System.out.println("illegal keycommand");
			break;
		}
	}

}
