import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class TankMoveMsg implements TankMsg{
	private static final int MSG_TYPE = TankMsg.TANK_MOVE_TYPE;
	private int id;
	private Direction dir;
	private TankClient tankClient;

	public TankMoveMsg(TankClient tankClient) {
		this.tankClient = tankClient;
	}

	public TankMoveMsg(int id, Direction dir) {
		this.id = id;
		this.dir = dir;
	}

	@Override
	public void send(DatagramSocket ds, String IP, int udpPort) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);	
		
		try {
			dos.writeInt(MSG_TYPE);
			dos.writeInt(id);
			dos.writeInt(dir.ordinal());
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
			
			boolean exists = false;
			for (int i = 0; i < this.tankClient.tanks.size(); i++) {
				if (id == this.tankClient.tanks.get(i).getId()) {
					this.tankClient.tanks.get(i).dir = Direction.values()[dis.readInt()];
					exists = true;
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	

}
