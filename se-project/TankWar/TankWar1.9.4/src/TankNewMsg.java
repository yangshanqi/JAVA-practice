import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class TankNewMsg {
	
	private Tank tank;
	
	public TankNewMsg () {
		
	}
	public TankNewMsg (Tank tank){
		this.tank = tank;
	}

	public void send(DatagramSocket ds, String IP, int udpPort) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);	
		
		try {
			dos.writeInt(tank.getId());
			dos.writeInt(tank.x);
			dos.writeInt(tank.y);
			dos.writeInt(tank.dir.ordinal());
			dos.writeBoolean(tank.isRobotic());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		byte[] buf = baos.toByteArray();
		DatagramPacket dp = new DatagramPacket (buf, buf.length,new InetSocketAddress(IP,udpPort) );
		try {
			ds.send(dp);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void parse(DataInputStream dis) {
		try {
			int id = dis.readInt();
			int x = dis.readInt();
			int y = dis.readInt();
			Direction dir = Direction.values()[dis.readInt()];
			boolean robotic = dis.readBoolean();
			System.out.println("id: "+ id +" x: "+ x + " y: "+ y +" direction: "+ dir +" robotic " +robotic);
		} catch (IOException e) {
			e.printStackTrace();
		}
	
	}

	

}
