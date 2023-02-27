
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.TimeZone;
import java.util.Timer;
import java.io.File;
import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.io.PrintWriter;

public class Window extends Canvas{


	private static final long serialVersionUID = 1882842017213253971L;
	Timer timer;
	TimeZone timeZone;
	boolean clicked = true;
	String city;
	static JFrame frame;

	public Window(ClockGui clock) {
		/*------------Creates window and sets its variables-----------------*/
		frame = new JFrame("AtmosClock");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		frame.setBounds(700, 100, 400, 450);
		frame.getContentPane().setBackground(new Color(212, 234, 255));
		frame.setPreferredSize(new Dimension(700, 100));
		frame.setLayout(new BorderLayout());
		frame.setVisible(true);
		/*-----------------------------*/
		/*------------Creates objects for screen-----------------*/
		JLabel question = new JLabel("Enter a city or a zip code");
		question.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		question.setBounds(75, 0, 300, 40);
		JTextField field = new JTextField(10);
		field.setBounds(75,100,250,20);  	
		JButton enter = new JButton("Enter");
		enter.setBounds(150,100,50,20); 
		JButton future = new JButton("Show next 12H");
		future.setBounds(150,100,50,20); 
		Border original  = future.getBorder();
		JButton change = new JButton("Change City");
		change.setBounds(150,100,50,20);  
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout (FlowLayout.CENTER, 10, 2));
		/*-----------------------------*/
		/*------------Creates first screen-----------------*/
		panel.add(enter);
		frame.add(question);
		frame.add(field);
		frame.add(panel, BorderLayout.SOUTH);
		frame.setVisible(true);
		/*-----------------------------*/

		/*------------Adds actions to Enter button-----------------*/
		ActionListener enterAction = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				city = field.getText();
				ClockGui.str = city;

				try {
					AtmosClockPre.atmos(city);
					field.setText("");
					panel.add(change);	
					panel.add(future);	
					panel.remove(enter);		
					frame.setVisible(true);
					frame.remove(field);
					frame.remove(question);	

					clock.addMouseListener(clock);				
					frame.add(clock);
					clock.start();
					frame.setVisible(true);
				} catch (IOException ioe) {
					field.setText("Invalid City or Zip Code Try Again");
					field.selectAll();
					field.requestFocus();

				}
			}

		};
		//Keyboard Shortcut
		field.addKeyListener(new KeyAdapter()
		{
			public void keyPressed(KeyEvent e)
			{
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					enterAction.actionPerformed(null);;
				}
			}
		});
		enter.addActionListener(enterAction);
		/*-----------------------------*/

		/*-------------Add actions to change city button----------------*/

		change.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				panel.add(enter);
				frame.add(question);	
				frame.add(field);	
				frame.setVisible(true);
				panel.remove(future);
				panel.remove(change);

				clock.stop();
				frame.remove(clock);
				clock.removeMouseListener(clock);

				field.requestFocus();
				future.setBorder(original);
				ClockGui.showfuture = false;
				frame.setVisible(true);

			}

		});
		/*-----------------------------*/

		/*--------------Adds action to show next 12H button---------------*/
		future.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if(future.getBorder() == original) {
					future.setBorder(BorderFactory.createLineBorder(Color.RED));
					ClockGui.showfuture = true;
				} else {
					future.setBorder(original);
					ClockGui.showfuture = false;
				}


			}

		});
		/*-----------------------------*/
	
	}

	private void savePref(int ln, String text){
		String fileName = "AtmosPref";
		File file = new File(fileName);
		
		try {
			file.createNewFile();
			PrintWriter writer = new PrintWriter(file);
			writer.write(text);
		} catch (Exception e1) {
			
			e1.printStackTrace();
		}
		
				
	}
}