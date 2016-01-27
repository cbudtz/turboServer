import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TurboServer {
	public static void main(String[] args) {
		int port = 5151;
		try {
			ServerSocket ss = new ServerSocket(port);
			Socket s = ss.accept();
			InputStream is = s.getInputStream();
			ObjectInputStream ois = new ObjectInputStream(is);
			KeyPressData kpd = (KeyPressData)ois.readObject();
			System.out.println(kpd);
			System.out.println(kpd.keys.get(0) + "");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			
			
		}
	}

}
