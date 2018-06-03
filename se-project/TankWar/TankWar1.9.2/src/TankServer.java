import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TankServer {
	
	public static final int TCP_PORT=5555;
	
	List<Client> clients = new ArrayList<Client> ();
	
	public void strat () {
		try {
			ServerSocket ss = new ServerSocket (TCP_PORT);
			while (true) {
				Socket s = ss.accept();
				System.out.println("a client connected: "+s.getInetAddress()+"PORT:"+s.getPort());
				DataInputStream dis = new DataInputStream(s.getInputStream());
				String IP = s.getInetAddress().getHostAddress();
				int udpPort = dis.readInt();
				System.out.println("client udpport: "+udpPort);
				Client c = new Client (IP,udpPort);
				clients.add(c);
				s.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		new TankServer().strat();
	}
	
	//保留客户端UDP消息
	private class Client {
		int udpPort ;
		String IP;
		
		public Client (String IP, int udpPort) {
			this.IP = IP;
			this.udpPort = udpPort;
		}
	}

}
