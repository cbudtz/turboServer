package turboRocket.communication;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import org.jbox2d.dynamics.World;
import org.jbox2d.serialization.JbSerializer;
import org.jbox2d.serialization.SerializationResult;
import org.jbox2d.serialization.pb.PbSerializer;

public class Transmitter{

	private Socket socket;
	private ObjectOutputStream oos;
	private OutputStream os;
	public Transmitter(Socket socket){
		this.socket = socket;
		
		try {
			os = socket.getOutputStream();
			this.oos = new ObjectOutputStream(os);
		} catch (IOException e) {
			System.err.println("transmitter initialization failed");
		}
		
	}
	
	public Socket getSocket(){
		return this.socket;
	}
	public boolean sendData(World obj){
		JbSerializer serializer = new PbSerializer();
		SerializationResult res = serializer.serialize(obj);
		
		
		if(socket.isClosed()) return false;
		try {
			res.writeTo(os);
//			oos.writeObject(res);
		} catch (IOException e) {
			System.err.println("failed to write data");
			return false;
		}
		return true;
	}
}
