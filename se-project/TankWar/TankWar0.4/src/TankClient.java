import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankClient extends Frame{
	
	int x = 30, y=40;
	
	@Override
	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(x, y, 30, 30);
		g.setColor(c);
		y= y+5;
	}

	public void launchFrame () {
		this.setLocation(400, 300);
		this.setSize(800, 600);		
		this.addWindowListener(new WindowAdapter() {			
			@Override
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
