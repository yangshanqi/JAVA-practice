import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;


public class NetClient {
	public static int UDP_PORT_START = 2338;
	private int udpPort;
	private TankClient tankClient;
	DatagramSocket ds =null;
	
	public NetClient(TankClient tankClient) {
		this.udpPort = UDP_PORT_START;
		UDP_PORT_START++;
		this.tankClient = tankClient;	
		
		try {
			ds = new DatagramSocket(udpPort);
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		new Thread (new UDPReceiveThread()).start();
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
		
		TankNewMsg tnm = new TankNewMsg(tankClient.myTank);
		send(tnm);
	}

	public void send(TankMsg tnm) {
		tnm.send(ds,"127.0.0.1",TankServer.UDP_PORT);
	}
	
	private class UDPReceiveThread implements Runnable {
		
		byte[] buf = new byte[1024];

		@Override
		public void run() {
			while (true) {
				DatagramPacket dp = new DatagramPacket(buf,buf.length);
				try {
					ds.receive(dp);
					System.out.println("client receives a packet from server");
					parse (dp);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		private void parse(DatagramPacket dp) {
			ByteArrayInputStream dais = new ByteArrayInputStream(buf);
			DataInputStream dis = new DataInputStream(dais);
			
			int msgType = -1;
			try {
				msgType = dis.readInt();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			TankMsg msg = null;
			switch (msgType) {
				case (TankMsg.NEW_TANK_TYPE):
					msg = new TankNewMsg(NetClient.this.tankClient);
					break;
				case (TankMsg.TANK_MOVE_TYPE):
					msg = new TankMoveMsg (NetClient.this.tankClient);
					break;
			}		
			msg.parse(dis);
			
		}
		
	}

}
