import java.awt.*;

public class Explode {
	
	private int x,y;
	private boolean live = true;
	private TankClient tankClient;
	
	private static Toolkit toolKit = Toolkit.getDefaultToolkit();
	private static boolean init = false;
	private static Image[] imgs = {
			toolKit.getImage(Explode.class.getClassLoader().getResource("images/0.gif")),
			toolKit.getImage(Explode.class.getClassLoader().getResource("images/1.gif")),
			toolKit.getImage(Explode.class.getClassLoader().getResource("images/2.gif")),
			toolKit.getImage(Explode.class.getClassLoader().getResource("images/3.gif")),
			toolKit.getImage(Explode.class.getClassLoader().getResource("images/4.gif")),
			toolKit.getImage(Explode.class.getClassLoader().getResource("images/5.gif")),
			toolKit.getImage(Explode.class.getClassLoader().getResource("images/6.gif")),
			toolKit.getImage(Explode.class.getClassLoader().getResource("images/7.gif")),
			toolKit.getImage(Explode.class.getClassLoader().getResource("images/8.gif")),
			toolKit.getImage(Explode.class.getClassLoader().getResource("images/9.gif")),
			toolKit.getImage(Explode.class.getClassLoader().getResource("images/10.gif"))
	};
	
	int step = 0;
	
	public Explode (int x, int y, TankClient tankClient) {
		this.x = x;
		this.y = y;
		this.tankClient = tankClient;
	}
	
	
	//第一次调用的时候，真正的图片并没有被画出，可能原因： 虚代理 或者 asynchronism IO
	public void draw (Graphics g) {
		if (!init) {
			for (int j = 0; j < imgs.length; j++) {
				g.drawImage(imgs[j], -100, -100, null);
			}
		}
		
		if (! live) return;
		if (step == imgs.length) {
			live  = false;
			step = 0;
			tankClient.explodes.remove(this);
			return;
		}
		
		g.drawImage(imgs[step],x,y,null);
		
		step ++;		
	}

}
