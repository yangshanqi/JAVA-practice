import java.awt.*;
import java.util.Random;
import java.awt.event.*;

public class Tank {
	
	public static final int XSPEED = 5;
	public static final int YSPEED = 5;
	
	public static final int WIDTH = 30;
	public static final int HEIGHT = 30;
	
	
	public int x,y;
	private boolean robotic;
	private boolean live = true;
	TankClient tankClient;
	public Direction dir = Direction.STOP;
	private Direction barrelDir = Direction.D;
	private int id;
	// static: all the obejects share this 
	private static Random random = new Random();
	
	private int step = random.nextInt(12)+3;
	
	private boolean bL=false; boolean bU = false;boolean bR=false ;boolean bD=false;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public Tank(int x, int y, boolean robotic) {
		this.x = x;
		this.y = y;
		this.robotic = robotic;
	}
	
	public Tank(int x, int y,boolean robotic, TankClient tankClient) {
		this(x,y,robotic);
		this.tankClient = tankClient;
	}
	
	public Tank(int x, int y,boolean robotic,Direction dir, TankClient tankClient) {
		this(x,y,robotic,tankClient);
		this.dir = dir;
	}


	public void draw (Graphics g) {
		if (!live) {
			if (robotic) tankClient.tanks.remove(this);
			return;}
		Color c = g.getColor();
		
		if (!robotic) g.setColor(Color.RED);
		else g.setColor(Color.gray);
		g.drawString("id: "+id, x, y-10);
		
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
		if (x<0) x=0;
		if (y<0) y=0;
		if (x+Tank.WIDTH > TankClient.GAME_WIDTH) x = TankClient.GAME_WIDTH - Tank.WIDTH;
		if ( y+Tank.HEIGHT > TankClient.GAME_HEIGHT ) y =TankClient.GAME_HEIGHT - Tank.HEIGHT;
	
		if (robotic) {
			Direction[] dirs = Direction.values();
			step --;
			if (step == 0 ) {
				dir = dirs[random.nextInt(dirs.length)];
				step = random.nextInt(12)+3;
			}
			
			if (random.nextInt(30)>20) this.fire();
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

	public void keyReleased(KeyEvent e) {
		int code = e.getKeyCode();
		switch (code) {
			case (KeyEvent.VK_CONTROL):
				fire();
				break;
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
	
	private void fire () {
		if (!live) return ;
		int x = this.x +Tank.WIDTH/2 -Missile.WIDTH/2;
		int y = this.y + Tank.HEIGHT/2 - Missile.HEIGHT/2;
		Missile m = new Missile (x,y,barrelDir,robotic,tankClient);
		tankClient.missiles.add(m);
	}
	
	Rectangle getRect () {
		return new Rectangle(x,y,WIDTH,HEIGHT);
	}

	public boolean isRobotic() {
		return robotic;
	}

	
	
}
