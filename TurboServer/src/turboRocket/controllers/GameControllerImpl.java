package turboRocket.controllers;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.Timer;

import org.jbox2d.collision.shapes.PolygonShape;
import org.jbox2d.common.Vec2;
import org.jbox2d.dynamics.Body;
import org.jbox2d.dynamics.BodyDef;
import org.jbox2d.dynamics.BodyType;
import org.jbox2d.dynamics.FixtureDef;
import org.jbox2d.dynamics.World;

import static java.awt.event.KeyEvent.*;
import turboRocket.B2Separator;
import turboRocket.KeyPress;
import turboRocket.communication.Transmitter;
import turboRocket.communication.TurboServer;

public class GameControllerImpl implements GameController{


	private int timeStep = 1000/60;
	int velocityIterations = 6;
    int positionIterations = 2;
    
	private World world;
	private Vec2 gravity = new Vec2(0, -9.82f);
	String keyPrefix = "player";
	private Vec2 position = new Vec2(0,0);
	private static Vec2[] shapeVectors = new Vec2[]{new Vec2(-4f,0f), new Vec2(0f,3f), new Vec2(4f,0f), new Vec2(0f,10f)};
	HashMap<String, Body> players = new HashMap<String, Body>();
	ArrayList<Transmitter> sockets = new ArrayList<Transmitter>();
	
	public GameControllerImpl(){
	    world = new World(gravity);	    
	    BodyDef groundBodyDef = new BodyDef();
	    Body groundBody = world.createBody(groundBodyDef);
	    
	    PolygonShape groundBox = new PolygonShape();
	    groundBox.setAsBox(100, 50);

	    groundBody.createFixture(groundBox, 0);
	}
	
	public void acceptPlayers(){
		new Thread(new TurboServer(this)).start();
		startSimulation();
		
	}
	
	public void startSimulation(){
		new Timer(timeStep, new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				world.step(timeStep, velocityIterations, positionIterations);
				System.out.println("update: "+ sockets.size());
				for(Transmitter t : sockets){
					System.out.println("sending: " + t.getSocket().getPort() + ";" + t.getSocket().getLocalPort());
					if(!t.sendData(world)){
						System.err.println("failed to send data");
					}
				}
			}
		}).start();
	}
	
	public void addPlayer(Socket socket){
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = BodyType.DYNAMIC;
		bodyDef.setPosition(position);
		Body body = world.createBody(bodyDef);

		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.density = 2;
		fixtureDef.friction = 0.3f;
		
		
	    B2Separator.seperate(body, fixtureDef, shapeVectors, 1);
		players.put(keyPrefix + socket.getPort(), body);
		sockets.add(new Transmitter(socket));
	}
	
	public void removePlayer(Socket socket){
		Body body = players.remove(keyPrefix + socket.getPort());
		world.destroyBody(body);
	}
	
}
