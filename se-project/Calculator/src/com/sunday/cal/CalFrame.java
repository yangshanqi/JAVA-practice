package com.sunday.cal;

import java.awt.*;
import java.awt.event.*;

public class CalFrame extends Frame{
	

	private final int TEXT_WIDTH = 360;
	private final int TEXT_HEIGHT = 720;
	
	private TextField textField = null;
	private CalServer calServer;
	
	private String[] Operations = {"7","8","9","/","BackSpace","delete",
			"4","5","6","*","(",")",
			"1","2","3","-","square","sqrt",
			"0",".","+","="};
	
	private ActionListener actionListener =null;
	
	public CalFrame(){
		super();
		this.calServer = new CalServer();
		init();
	}
	
	private void init(){
		this.setTitle("calculator");
		this.setResizable(false);
		this.setSize(500, 300);
		
		
		Panel panel = new Panel();
		panel.setLayout(new BorderLayout(200,20));
	    this.textField = new TextField();
		panel.add(textField, BorderLayout.NORTH);
		panel.setPreferredSize(new Dimension(TEXT_WIDTH, TEXT_HEIGHT));
		
		Button[] opButtons = this.getButtons();
		Panel panel1 = new Panel ();
		panel1.setLayout(new GridLayout(4,6));		
		for (Button button: opButtons){
			panel1.add(button);
		}
		panel.add(panel1, BorderLayout.CENTER);
		
		this.add(panel);
		myEvent();
		this.setVisible(true);
		
		
	}
	
	//define the event of the text field and the frame window
	private void myEvent () {
		this.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				System.exit(0);	
			}
		});
		
		textField.addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent e)
			{
				int code = e.getKeyCode();
				if((code>=KeyEvent.VK_0 && code<=KeyEvent.VK_9))
				{
					String returnExp = calServer.proceeKey(code);
					System.out.println("hi"+textField.getCaretPosition());
				}


			}
		});
	}
	
	private ActionListener getActionLis () {
		if (this.actionListener==null) {
			actionListener = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent event) {
					String cmd = event.getActionCommand();
					String returnExp=calServer.processMouse(cmd);
					textField.setText(returnExp);
				}			
			};	
		}
		return this.actionListener;
	}
	
	private Button[] getButtons (){
		Button[] buttons= new Button [Operations.length];
		for (int i=0 ; i<Operations.length ; i++ ){
			buttons[i] = new Button (Operations[i]);
			buttons[i].setForeground(Color.red);
			buttons[i].addActionListener(getActionLis());
		}
		return buttons;
	}

}
