import java.awt.*;

public class Explode {
	
	private int x,y;
	private boolean live = true;
	private TankClient tankClient;
	
	int[] diameter = {10,15,22,31,42,55,40,25,10};
	int step = 0;
	
	public Explode (int x, int y, TankClient tankClient) {
		this.x = x;
		this.y = y;
		this.tankClient = tankClient;
	}
	
	public void draw (Graphics g) {
		if (! live) return;
		if (step == diameter.length) {
			live  = false;
			step = 0;
			tankClient.explodes.remove(this);
			return;
		}
		
		Color c = g.getColor();
		g.setColor(Color.YELLOW);
		g.fillOval(x, y, diameter[step], diameter[step]);
		g.setColor(c);
		
		step ++;
		
	}

}
