import java.awt.*;
import java.util.HashMap;
//import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class Missile {
	
	public static final int XSPEED = 10;
	public static final int YSPEED = 10;	
	
	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;	
	
	private int x,y;
	private Direction dir;
	
	private boolean live = true;
	private boolean robotic;
	
	TankClient tankClient;
	
	private static Toolkit toolKit = Toolkit.getDefaultToolkit();
	
	private static Image[] missileImages = null;
	private static Map<String,Image> imgs = new HashMap<String,Image>();
	
	static{
		missileImages = new Image[] {
				toolKit.getImage(Missile.class.getClassLoader().getResource("images/missileL.gif")),
				toolKit.getImage(Missile.class.getClassLoader().getResource("images/missileLU.gif")),
				toolKit.getImage(Missile.class.getClassLoader().getResource("images/missileU.gif")),
				toolKit.getImage(Missile.class.getClassLoader().getResource("images/missileRU.gif")),
				toolKit.getImage(Missile.class.getClassLoader().getResource("images/missileR.gif")),
				toolKit.getImage(Missile.class.getClassLoader().getResource("images/missileRD.gif")),
				toolKit.getImage(Missile.class.getClassLoader().getResource("images/missileD.gif")),
				toolKit.getImage(Missile.class.getClassLoader().getResource("images/missileLD.gif")),
		};
		imgs.put("L",missileImages[0]);
		imgs.put("LU", missileImages[1]);
		imgs.put("U", missileImages[2]);
		imgs.put("RU", missileImages[3]);
		imgs.put("R", missileImages[4]);
		imgs.put("RD", missileImages[5]);
		imgs.put("D", missileImages[6]);
		imgs.put("LD", missileImages[7]);
	}
	
	public Missile(int x, int y, Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	
	public Missile(int x, int y, Direction dir, boolean robotic, TankClient tankClient) {
		this (x,y,dir);
		this.robotic = robotic;
		this.tankClient = tankClient;
	}

	public void draw (Graphics  g) {
		if (!live) {
			this.tankClient.missiles.remove(this);
			return;
		}
		
		switch (dir) {
		case L:
			g.drawImage(imgs.get("L"), x, y, null);
			break;
		case LU:
			g.drawImage(imgs.get("LU"), x, y, null);
			break;
		case U:
			g.drawImage(imgs.get("U"), x, y, null);
			break;
		case RU:
			g.drawImage(imgs.get("RU"), x, y, null);
			break;
		case R:
			g.drawImage(imgs.get("R"), x, y, null);
			break;
		case RD:
			g.drawImage(imgs.get("RD"), x, y, null);
			break;
		case D:
			g.drawImage(imgs.get("D"), x, y, null);
			break;
		case LD:
			g.drawImage(imgs.get("LD"), x, y, null);
			break;
		}
		move ();
	}

	private void move() {
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
		}
		if(x<0 || y<0 || x>TankClient.GAME_WIDTH || y>TankClient.GAME_HEIGHT) {
			live = false;
		}
	}
	
	Rectangle getRect () {
		return new Rectangle(x,y,WIDTH,HEIGHT);
	}
	
	public boolean hitTank (Tank tank) {
		if (this.getRect().intersects(tank.getRect()) && tank.isLive() && this.robotic != tank.isRobotic()) {
			if (!tank.isRobotic()) {
				
				//可以用观察者模式改进？
				tank.setLife(tank.getLife()-20);
				if (tank.getLife() <= 0) tank.setLive(false);
			}else {
				tank.setLive(false);}
			this.live = false;
			this.tankClient.explodes.add(new Explode(x,y,tankClient));
			return true;
		}
		return false;	
	}
	
	public boolean hitTanks (List<Tank> tanks) {
		for (Tank tank: tanks) {
			if (hitTank(tank)) return true;
		}
		return false;
	}
	
	public boolean hitWall (Wall w) {
		if (live && this.getRect().intersects(w.getRect())) {
			live = false;
			return true;
		}
		return false;		
	}	
}
