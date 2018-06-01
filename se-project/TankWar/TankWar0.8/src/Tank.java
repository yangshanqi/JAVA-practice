import java.awt.*;
import java.awt.event.*;

public class Tank {
	
	public static final int XSPEED = 5;
	public static final int YSPEED = 5;
	
	//L stands  for left, U stand for up, R stand for right, D stands for down
	enum Direction {L,LU,U,RU,R,RD,D,LD,STOP};
	private int x,y;
	private Direction dir = Direction.STOP;
	private boolean bL=false; boolean bU = false;boolean bR=false ;boolean bD=false;

	public Tank(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void draw (Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(x, y, 30, 30);
		g.setColor(c);
		move ();
	}
	
	void move () {
		switch (dir) {
			case L:
				x -= XSPEED;
				break;
			case LU:
				x -= XSPEED;
				y -= YSPEED;
				break;
			case U:
				y -= YSPEED;
				break;
			case RU:
				x += XSPEED;
				y -= YSPEED;
				break;
			case R:
				x += XSPEED;
				break;
			case RD:
				x += XSPEED;
				y += YSPEED;
				break;
			case D:
				y += YSPEED;
				break;
			case LD:
				x -= XSPEED;
				y += YSPEED;
				break;
			case STOP:
				break;
		}
	}
	
	public void keyPressed (KeyEvent e) {
		int code = e.getKeyCode();
		switch (code) {
			case (KeyEvent.VK_UP):
			 	bU = true;
				break;
			case (KeyEvent.VK_DOWN):
				bD = true;
				break;
			case (KeyEvent.VK_LEFT):
				bL = true;
				break;
			case (KeyEvent.VK_RIGHT):
				bR = true;
			 	break;		 	
		}
		locateDirection ();
	}
	
	void locateDirection () {
		if (bL & !bU & !bR & !bD) dir = Direction.L;
		else if (bL & bU & !bR & !bD) dir = Direction.LU;
		else if (!bL & bU & !bR & !bD) dir = Direction.U;
		else if (!bL & bU & bR & !bD) dir = Direction.RU;
		else if (!bL & !bU & bR & !bD) dir = Direction.R;
		else if (!bL & !bU & bR & bD) dir = Direction.RD;
		else if (!bL & !bU & !bR & bD) dir = Direction.D;
		else if (bL & !bU & !bR & bD) dir = Direction.LD;
		else if (!bL & !bU & !bR & !bD) dir = Direction.STOP;
	}
	
	

}
