import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class TankDieMsg implements Msg{
	private static final int MSG_TYPE = Msg.TANK_DIE_TYPE;
	private int tankId;
	private TankClient tankClient;

	public TankDieMsg(int tankId) {
		super();
		this.tankId = tankId;
	}
	
	public TankDieMsg(TankClient tankClient) {
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
			
			for (int i=0 ; i <this.tankClient.tanks.size(); i++) {
				if (this.tankClient.tanks.get(i).getId() == id) {
					this.tankClient.tanks.get(i).setLive(false);
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
