import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TankServer {
	
	public static final int TCP_PORT=5555;
	public static int id = 7;
	
	List<Client> clients = new ArrayList<Client> ();
	
	public void strat () {
		ServerSocket ss =null;
		try {
			ss = new ServerSocket (TCP_PORT);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		while (true) {
			Socket s = null; 
			try {
				s=ss.accept();
				System.out.println("a client connected: "+s.getInetAddress()+"PORT:"+s.getPort());
				DataInputStream dis = new DataInputStream(s.getInputStream());
				String IP = s.getInetAddress().getHostAddress();
				int udpPort = dis.readInt();
				System.out.println("client udpport: "+udpPort);
				Client c = new Client (IP,udpPort);
				clients.add(c);
				DataOutputStream dos = new DataOutputStream(s.getOutputStream());
				dos.writeInt(id);
				id++;
			} catch (IOException e) {
				e.printStackTrace();
			}finally {
				if (s != null) {
					try {
						s.close();
						s = null;
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
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
