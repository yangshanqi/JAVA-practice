import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class NetClient {
	public static int UDP_PORT_START = 2333;
	private int udpPort;
	
	public NetClient() {
		this.udpPort = UDP_PORT_START;
		UDP_PORT_START++;
	}

	public void connect (String IP, int port) {
		Socket s = null;
		try {
			s = new Socket (IP,port);
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			dos.writeInt(udpPort);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}finally {
			if (s != null) {
				try {
					s.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

}
