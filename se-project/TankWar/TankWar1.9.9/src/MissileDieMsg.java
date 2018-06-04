import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class MissileDieMsg implements Msg{
	private static final int MSG_TYPE = Msg.MISSILE_DIE_TYPE;
	private int tankId;
	private int id;
	private TankClient tankClient;
		
	public MissileDieMsg(int tankId, int id) {
		super();
		this.tankId = tankId;
		this.id = id;
	}
	
	public MissileDieMsg(TankClient tankClient) {
		super();
		this.tankClient = tankClient;
	}

	@Override
	public void send(DatagramSocket ds, String IP, int udpPort) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);	
		
		try {
			dos.writeInt(MSG_TYPE);
			dos.writeInt(tankId);
			dos.writeInt(id);
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
			int tankId = dis.readInt();
			int id = dis.readInt();
			
			for (int i=0 ; i <this.tankClient.missiles.size(); i++) {
				Missile m = this.tankClient.missiles.get(i);
				if (m.tankID == tankId && m.id ==id) {
					m.live = false;
					this.tankClient.explodes.add(new Explode (m.x, m.y, this.tankClient));
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
