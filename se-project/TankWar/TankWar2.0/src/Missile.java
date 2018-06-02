import java.awt.*;
//import java.util.ArrayList;
import java.util.List;


public class Missile {
	
	public static final int XSPEED = 10;
	public static final int YSPEED = 10;	
	
	public static final int WIDTH = 10;
	public static final int HEIGHT = 10;	
	
	private int x,y;
	private Tank.Direction dir;
	private boolean live = true;
	private boolean robotic;
	TankClient tankClient;
	
	public Missile(int x, int y, Tank.Direction dir) {
		this.x = x;
		this.y = y;
		this.dir = dir;
	}
	
	public Missile(int x, int y, Tank.Direction dir, boolean robotic, TankClient tankClient) {
		this (x,y,dir);
		this.robotic = robotic;
		this.tankClient = tankClient;
	}

	public void draw (Graphics  g) {
		if (!live) {
			this.tankClient.missiles.remove(this);
			return;
		}
		Color c = g.getColor();
		g.setColor(Color.BLACK);
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);
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
			tank.setLive(false);
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
