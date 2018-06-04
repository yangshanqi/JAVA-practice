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

	private int udpPort;
	private TankClient tankClient;
    String IP;
    int serverUDPPort;
	DatagramSocket ds =null;
	
	public NetClient(TankClient tankClient) {
		
		this.tankClient = tankClient;	
		
	}
	
	
	public int getUdpPort() {
		return udpPort;
	}


	public void setUdpPort(int udpPort) {
		this.udpPort = udpPort;
	}


	public void connect (String IP, int port) {
		

		try {
			ds = new DatagramSocket(udpPort);
		} catch (SocketException e) {
			e.printStackTrace();
		}
		
		new Thread (new UDPReceiveThread()).start();
		
		Socket s = null;
		try {
			s = new Socket (IP,port);
			DataOutputStream dos = new DataOutputStream(s.getOutputStream());
			dos.writeInt(udpPort);
			DataInputStream dis = new DataInputStream(s.getInputStream());
			int id = dis.readInt();
			tankClient.myTank.setId(id);
			if (id % 2 ==1) tankClient.myTank.setRobotic(false);
			else {
				tankClient.myTank.setRobotic(true);
			}
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

	public void send(Msg tnm) {
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
			
			Msg msg = null;
			switch (msgType) {
				case (Msg.NEW_TANK_TYPE):
					msg = new TankNewMsg(NetClient.this.tankClient);
					break;
				case (Msg.TANK_MOVE_TYPE):
					msg = new TankMoveMsg (NetClient.this.tankClient);
					break;
				case(Msg.NEW_MISSILE_TYPE):
					msg = new MissileNewMsg(NetClient.this.tankClient);
					break;
				case (Msg.TANK_DIE_TYPE):
					msg = new TankDieMsg(NetClient.this.tankClient);
					break;
				case (Msg.MISSILE_DIE_TYPE):
					msg = new MissileDieMsg(NetClient.this.tankClient);
					break;
			}		
			msg.parse(dis);
			
		}
		
	}

}
