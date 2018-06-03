import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

public class NetClient {
	public static int UDP_PORT_START = 2333;
	private int udpPort;
	private TankClient tankClient;
	
	public NetClient(TankClient tankClient) {
		this.udpPort = UDP_PORT_START;
		UDP_PORT_START++;
		this.tankClient = tankClient;
	}

	public void connect (String IP, int port) {
		Socket s = null;
		try {
			s = new Socket (IP,port);
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			dos.writeInt(udpPort);
			DataInputStream dis = new DataInputStream(s.getInputStream());
			int id = dis.readInt();
			tankClient.myTank.setId(id);
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
