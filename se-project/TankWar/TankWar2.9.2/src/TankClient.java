import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TankClient extends Frame{
	
	public static final int GAME_HEIGHT = 600;
	public static final int GAME_WIDTH = 800;
	
	List<Missile> missiles = new ArrayList<Missile> ();
	List<Explode> explodes = new ArrayList<Explode> ();
	List<Tank> tanks = new ArrayList<Tank> ();
	
	Wall w1 = new Wall (300,300,300,30,this);
	Wall w2 = new Wall (100,200,30,200,this);
	//myTank: robotic:false
	Tank myTank = new Tank (30,40,false,this);
	Blood blood = new Blood(this);

	Image offScreenImage = null;

	@Override
	public void paint(Graphics g) {
		g.drawString("MISSILES COUNT: "+ missiles.size(), 10, 20);
		g.drawString("EXPLODES COUNT: "+ explodes.size(), 10, 60);
		g.drawString("TANKS COUNT: "+ tanks.size(), 10, 40);
		g.drawString("myTank LIFE: "+ myTank.getLife(), 10, 80);
		
		if (tanks.size() <= 0 ) {
			for (int i=0 ; i<Integer.parseInt(PropertyMgr.getValue("reProduceTankAmount")) ;  i++) {
				tanks.add(new Tank(200+46*i,200,true,Direction.D,this));
			}
		}
		
		w1.draw(g);
		w2.draw(g);
		
		for (int i=0 ; i<missiles.size(); i++) {
			Missile m = missiles.get(i);
			m.hitTanks(tanks);
			m.hitTank(myTank);
			m.hitWall(w1);
			m.hitWall(w2);
			m.draw(g);
		}
		
		for (int i=0 ; i< explodes.size() ; i++) {
			explodes.get(i).draw(g);
		}
		
		for (int i=0 ; i < tanks.size(); i++ ) {
			Tank t = tanks.get(i);
			t.collidesWithWall(w1);
			t.collidesWithWall(w2);
			t.collidesWithTanks(tanks);
			t.draw(g);
		}
		
		myTank.collidesWithTanks(tanks);
		myTank.collidesWithWall(w1);
		myTank.collidesWithWall(w2);
		myTank.collidesWithBlood(blood);
		myTank.draw(g);
		blood.draw(g);	
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
		int initTankAmount;
		
		initTankAmount = Integer.parseInt(PropertyMgr.getValue("initTankAmount"));
		
		for (int i=0 ; i<initTankAmount ;  i++) {
			tanks.add(new Tank(100+60*i,200,true,Direction.D,this));
		}
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
