import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankClient extends Frame{
	
	int x = 30, y=40;
	Image offScreenImage = null;
	
	@Override
	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(x, y, 30, 30);
		g.setColor(c);
		y= y+5;
	}
	
	
	@Override
	public void update(Graphics g) {
		if (offScreenImage == null) {
			offScreenImage = this.createImage(800, 600);
		}
		
		Graphics gOffScr = offScreenImage.getGraphics();		
		//reset the background color
		Color c = gOffScr.getColor();
		gOffScr.setColor(Color.GREEN);
		gOffScr.fillRect(0, 0, 800, 600);
		gOffScr.setColor(c);
		//draw everything on the image first
		paint(gOffScr);
		
		//show them together
		g.drawImage(offScreenImage, 0, 0, null);
		
	}



	public void launchFrame () {
		this.setLocation(400, 300);
		this.setSize(800, 600);		
		this.addWindowListener(new WindowAdapter() {			
			@Override //匿名内部类
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}			
		});
		
		this.setResizable(false);
		this.setBackground(Color.GREEN);
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

}
