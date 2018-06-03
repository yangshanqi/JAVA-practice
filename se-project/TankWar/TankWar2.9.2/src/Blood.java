import java.awt.*;

public class Blood {
	
	private int x,y,w,h;
	private TankClient tankClient;
	
	private int step = 0;
	private boolean live = true;
	
	private int[][] pos = {
			{200,300},{205,305},{210,310},{215,315},{220,320},{225,325},
			{230,330},{235,335}
	};
	
	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public Blood (TankClient tankClient) {
		w = 20;
		h = 20;
		x = pos[step][0];
		y = pos[step][1];
		this.tankClient = tankClient;
	}
	
	public void draw (Graphics g) {
		if (!live) return;
		Color c = g.getColor();
		g.setColor(Color.CYAN);
		g.fillRect(x, y, w, h);
		g.setColor(c);
		
		move();
	}

	private void move() {
		step ++;
		if (step == pos.length) {
			step = 0;
		}
		
		x = pos[step][0];
		y = pos[step][1];
	}
	
	public Rectangle getRect () {
		return new Rectangle (x,y,w,h);
	}

}
