import java.awt.*;
import java.awt.event.*;

public class Tank {
	
	public static final int XSPEED = 5;
	public static final int YSPEED = 5;
	
	public static final int WIDTH = 30;
	public static final int HEIGHT = 30;
	
	//L stands  for left, U stand for up, R stand for right, D stands for down
	enum Direction {L,LU,U,RU,R,RD,D,LD,STOP};
	
	private int x,y;
	TankClient tankClient;
	private Direction dir = Direction.STOP;
	private Direction barrelDir = Direction.D;
	private boolean bL=false; boolean bU = false;boolean bR=false ;boolean bD=false;

	public Tank(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Tank(int x, int y, TankClient tankClient) {
		this(x,y);
		this.tankClient = tankClient;
	}

	public void draw (Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(Color.BLACK);
		switch (barrelDir) {
		case L:
			g.drawLine(x+WIDTH/2, y+HEIGHT/2, x, y+HEIGHT/2);
			break;
		case LU:
			g.drawLine(x+WIDTH/2, y+HEIGHT/2, x, y);
			break;
		case U:
			g.drawLine(x+WIDTH/2, y+HEIGHT/2, x+WIDTH/2, y);
			break;
		case RU:
			g.drawLine(x+WIDTH/2, y+HEIGHT/2, x+WIDTH, y);
			break;
		case R:
			g.drawLine(x+WIDTH/2, y+HEIGHT/2, x+WIDTH, y+HEIGHT/2);
			break;
		case RD:
			g.drawLine(x+WIDTH/2, y+HEIGHT/2, x+WIDTH, y+HEIGHT);
			break;
		case D:
			g.drawLine(x+WIDTH/2, y+HEIGHT/2, x+WIDTH/2, y+HEIGHT);
			break;
		case LD:
			g.drawLine(x+WIDTH/2, y+HEIGHT/2, x, y+HEIGHT);
			break;
		}
		move ();
		g.setColor(c);
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
		if (dir != Direction.STOP) {
			barrelDir = dir;
		}
	}
	
	public void keyPressed (KeyEvent e) {
		int code = e.getKeyCode();
		switch (code) {
			case (KeyEvent.VK_CONTROL):
				tankClient.myMissile = fire();
				break;
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

	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		switch (code) {
			case (KeyEvent.VK_UP):
			 	bU = false;
				break;
			case (KeyEvent.VK_DOWN):
				bD = false;
				break;
			case (KeyEvent.VK_LEFT):
				bL = false;
				break;
			case (KeyEvent.VK_RIGHT):
				bR = false;
			 	break;		 	
		}
		locateDirection ();
	}
	
	private Missile fire () {
		int x = this.x +Tank.WIDTH/2 -Missile.WIDTH/2;
		int y = this.y + Tank.HEIGHT/2 - Missile.HEIGHT/2;
		Missile m = new Missile (x,y,barrelDir);
		return m;
	}
	

}
