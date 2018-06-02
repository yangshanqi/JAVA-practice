import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

public class TankClient extends Frame{
	
	public static final int GAME_HEIGHT = 600;
	public static final int GAME_WIDTH = 800;
	
	List<Missile> missiles = new ArrayList<Missile> ();
	Tank myTank = new Tank (30,40,false,this);
	Tank enemyTank = new Tank (100,100,true,this);
	Image offScreenImage = null;
	
	@Override
	public void paint(Graphics g) {
		g.drawString("MISSILES COUNT: "+ missiles.size(), 10, 20);
		
		for (int i=0 ; i<missiles.size(); i++) {
			Missile m = missiles.get(i);
			m.draw(g);
		}
		myTank.draw(g);
		enemyTank.draw(g);
	}
	
	
	@Override
	public void update(Graphics g) {
		if (offScreenImage == null) {
			offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
		}
		
		Graphics gOffScr = offScreenImage.getGraphics();		
		//reset the background color
		Color c = gOffScr.getColor();
		gOffScr.setColor(Color.GREEN);
		gOffScr.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		gOffScr.setColor(c);
		//draw everything on the off-line image first
		paint(gOffScr);
		
		//show them together
		g.drawImage(offScreenImage, 0, 0, null);
		
	}



	public void launchFrame () {
		this.setLocation(400, 300);
		this.setSize(GAME_WIDTH, GAME_HEIGHT);		
		this.addWindowListener(new WindowAdapter() {			
			@Override //匿名内部类
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}			
		});
		
		this.setResizable(false);
		this.setBackground(Color.GREEN);
		this.addKeyListener(new KeyMonitor());
		this.setVisible(true);
		
		new Thread (new RepaintThread()).start();
	}
	


	public static void main(String[] args) {
		TankClient tankClient = new TankClient();
		tankClient.launchFrame();
	}
	
	//内部类
	private class RepaintThread implements Runnable  {			
		@Override
		public void run() {
			while (true) {
				repaint();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}			
		}
	}
	
	private class KeyMonitor extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent e) {
			myTank.keyReleased (e);
		}

		@Override
		public void keyPressed(KeyEvent e) {
			myTank.keyPressed(e);
		}
		
	}

}
