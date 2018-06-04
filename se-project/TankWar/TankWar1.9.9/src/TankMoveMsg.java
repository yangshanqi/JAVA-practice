import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

public class TankMoveMsg implements Msg{
	private static final int MSG_TYPE = Msg.TANK_MOVE_TYPE;
	private int id;
	private int x,y;
	private Direction dir;
	private Direction barrelDir;
	private TankClient tankClient;

	public TankMoveMsg(TankClient tankClient) {
		this.tankClient = tankClient;
	}

	public TankMoveMsg(int id,int x,int y, Direction dir,Direction barrelDir) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.barrelDir = barrelDir;
	}

	@Override
	public void send(DatagramSocket ds, String IP, int udpPort) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dos = new DataOutputStream(baos);	
		
		try {
			dos.writeInt(MSG_TYPE);
			dos.writeInt(id);
			dos.writeInt(x);
			dos.writeInt(y);
			dos.writeInt(dir.ordinal());
			dos.writeInt(barrelDir.ordinal());
			
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
					Tank t  = this.tankClient.tanks.get(i);
					t.x = dis.readInt();
					t.y = dis.readInt();
					t.dir = Direction.values()[dis.readInt()];
					t.barrelDir = Direction.values()[dis.readInt()];
					exists = true;
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
