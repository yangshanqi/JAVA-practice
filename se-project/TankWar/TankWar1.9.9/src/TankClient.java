import java.awt.Button;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Label;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	List<Explode> explodes = new ArrayList<Explode> ();
	List<Tank> tanks = new ArrayList<Tank> ();
	
	//myTank: robotic:false
	Tank myTank = new Tank (30,40,false,this);
	NetClient nc = new NetClient(this);
	ConnDialog dialog = new ConnDialog ();
	
	Image offScreenImage = null;
	
	@Override
	public void paint(Graphics g) {
		g.drawString("MISSILES COUNT: "+ missiles.size(), 10, 20);
		g.drawString("EXPLODES COUNT: "+ explodes.size(), 10, 70);
		g.drawString("TANKS COUNT: "+ tanks.size(), 10, 40);
		for (int i=0 ; i<missiles.size(); i++) {
			Missile m = missiles.get(i);
			if (m.hitTank(myTank)) {
				TankDieMsg msg = new TankDieMsg(myTank.getId());
				this.nc.send(msg);
				MissileDieMsg msg1 = new MissileDieMsg (m.tankID,m.id);
				this.nc.send(msg1);
			}
			m.draw(g);
		}
		
		for (int i=0 ; i< explodes.size() ; i++) {
			explodes.get(i).draw(g);
		}
		
		for (int i=0 ; i < tanks.size(); i++ ) {
			tanks.get(i).draw(g);
		}
		myTank.draw(g);
		
		
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
		//nc.connect("127.0.0.1", TankServer.TCP_PORT);
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
			int code = e.getKeyCode();
			if (code == KeyEvent.VK_C) {
				dialog.show();
			}else {
				myTank.keyPressed(e);
			}
		}	
	}
	
	
	private class ConnDialog extends Dialog {
		private Button button = new Button ("ok");
		private TextField ipTf  = new TextField ("127.0.0.1",12);
		private TextField portTf = new TextField (""+TankServer.TCP_PORT,8);
		private TextField myUDPPortTf = new TextField (""+2333,4);

		public ConnDialog() {
			super(TankClient.this, true) ;
			this.add(button);
			this.setLayout(new FlowLayout());
			this.add (new Label ("IP"));
			this.add(ipTf);
			this.add(new Label("TCP PORT"));
			this.add(portTf);
			this.add(new Label ("my UDP Port"));
			this.add(myUDPPortTf);
			this.pack();
			this.setLocation(400, 400);
			this.addWindowListener(new WindowAdapter () {
				@Override
				public void windowClosing(WindowEvent arg0) {
					setVisible (false);
				}				
			});
			button.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					String IP = ipTf.getText();
					int port = Integer.parseInt(portTf.getText());
					int myUDPPort = Integer.parseInt(myUDPPortTf.getText());
					TankClient.this.nc.setUdpPort(myUDPPort);
					nc.IP = ipTf.getText();
					nc.serverUDPPort = Integer.parseInt(portTf.getText());
					
					TankClient.this.nc.connect(IP, port);					 
					setVisible(false);
				}
			});
		}
		
	}

}
