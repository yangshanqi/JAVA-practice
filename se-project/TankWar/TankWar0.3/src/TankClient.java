import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TankClient extends Frame{
	
	@Override
	public void paint(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillOval(30, 40, 30, 30);
		g.setColor(c);
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
	}
	


	public static void main(String[] args) {
		TankClient tankClient = new TankClient();
		tankClient.launchFrame();
	}

}
