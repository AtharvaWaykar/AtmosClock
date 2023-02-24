
import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.TimeZone;
import java.util.Timer;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;


public class Window extends Canvas{


	private static final long serialVersionUID = 1882842017213253971L;
	Timer timer;
	TimeZone timeZone;
	boolean clicked = true;
	String city;
	static JFrame frame;

	public Window(ClockGui clock) {
		frame = new JFrame("AtmosClock");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
		frame.setBounds(700, 100, 400, 450);
		frame.getContentPane().setBackground(new Color(212, 234, 255));
		
		frame.setLayout(new BorderLayout());
		frame.setVisible(true);
		/*------------Creates first screen-----------------*/
		JLabel question = new JLabel("Enter a city or a zip code");
		question.setFont(new Font("Lucida Grande", Font.PLAIN, 20));
		question.setBounds(75, 0, 300, 40);
		JTextField field = new JTextField(10);
		field.setBounds(75,100,250,20);  	
		JButton enter = new JButton("Enter");
		enter.setBounds(150,100,50,20); 
		JButton change = new JButton("Change City");
		change.setBounds(150,100,50,20);  
		
		frame.add(question);
		frame.add(field);
		frame.add(enter, BorderLayout.SOUTH);
		frame.setVisible(true);
		/*-----------------------------*/
		
		//To enter data and open clock
		enter.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				city = field.getText();
				ClockGui.str = city;
				try {
				AtmosClockPre.atmos(city);
				} catch (IOException ioe) {
					System.out.print("hello");
					System.exit(0);
				}
				field.setText("");
				frame.add(change, BorderLayout.SOUTH);
				frame.remove(enter);
				frame.remove(field);
				frame.remove(question);			
				clock.addMouseListener(clock);				
				frame.add(clock);					
				frame.setVisible(true);
				clock.start();
			}

		});
			
		/*-----------------------------*/
		
		//To go back to change city
		change.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				frame.add(enter, BorderLayout.SOUTH);
				frame.remove(change);
				clock.stop();
				frame.remove(clock);
				clock.removeMouseListener(clock);
				frame.add(question);	
				frame.add(field);	
				field.requestFocus();
				frame.setVisible(true);
						
			}

		});
		
	}

}