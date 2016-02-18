package echoServer2;

public class EchoServer2 implements Runnable{
	Runnable receiver;
	Transmitter2 transmitter;
	private Thread receiverThread;
	
	@Override
	public void run() {
		receiver = new InConnector();
		receiverThread = new Thread(receiver);
		receiverThread.start();

		
	}

}
