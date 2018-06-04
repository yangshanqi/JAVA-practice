import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

public class TankServer {
	
	public static final int TCP_PORT=5555;
	public static final int UDP_PORT=7777;
	
	public static int id = 7;
	
	List<Client> clients = new ArrayList<Client> ();
	
	public void strat () {
		ServerSocket ss =null;
		try {
			ss = new ServerSocket (TCP_PORT);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		new Thread(new UDPThread()).start();
		
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
	
	private class UDPThread implements Runnable{
		byte[] buf = new byte[1024];

		@Override
		public void run() {
			DatagramSocket ds = null;
			try {
				ds = new DatagramSocket(UDP_PORT);
			} catch (SocketException e) {
				e.printStackTrace();
			}
			
			while (true) {
				DatagramPacket dp = new DatagramPacket(buf,buf.length);
				try {
					ds.receive(dp);
					System.out.println("server receives a packet from client");
					
					for (int i=0 ; i<clients.size() ; i ++) {
						Client client = clients.get(i);
						InetSocketAddress addr = new InetSocketAddress (client.IP,client.udpPort);
						dp.setSocketAddress(addr);
						ds.send(dp);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

}
