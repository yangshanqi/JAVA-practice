import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class MissileNewMsg implements Msg{	
	private static final int MSG_TYPE = Msg.NEW_MISSILE_TYPE;
	
	private Missile missile;
	private TankClient tankClient;
	
	public MissileNewMsg (Missile missile) {
		this.missile = missile;
	}
	
	public MissileNewMsg (TankClient tankClient) {
		this.tankClient = tankClient;
	}

	@Override
	public void send(DatagramSocket ds, String IP, int udpPort) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);	
		
		try {
			dos.writeInt(MSG_TYPE);
			dos.writeInt(missile.tankID);
			dos.writeInt(missile.x);
			dos.writeInt(missile.y);
			dos.writeInt(missile.dir.ordinal());
			dos.writeBoolean(missile.robotic);
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

	@Override
	public void parse(DataInputStream dis) {
		try {
			int id = dis.readInt();
			if (id == this.tankClient.myTank.getId()) {
				return;
			}
			
			int x = dis.readInt();
			int y = dis.readInt();
			Direction dir = Direction.values()[dis.readInt()];
			boolean robotic = dis.readBoolean();
			
			Missile missile = new Missile (id,x,y,dir,robotic,tankClient);
			this.tankClient.missiles.add(missile);
		}catch (IOException e) {
			e.printStackTrace();
		}		
	}

}
