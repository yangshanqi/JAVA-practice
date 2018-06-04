import java.io.DataInputStream;
import java.net.DatagramSocket;

public interface TankMsg {
	
	public static final int NEW_TANK_TYPE = 1;
	public static final int TANK_MOVE_TYPE = 2;
	
	public abstract void send (DatagramSocket ds, String IP, int udpPort);
	
	public abstract void parse (DataInputStream dis);

}
