
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferStrategy;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;


/* 
 * Clock analog
 */

public class ClockGui extends Canvas implements MouseListener, Runnable {


	private static final long serialVersionUID = 1030296368320806826L;
	private int status=0;
	private static final int spacing = 35;
	private static final float radPerSecMin = (float)(Math.PI / 30.0);
	private static final float radPerNum = (float)(Math.PI/ -6);
	private int size;
	private int centerX;
	private int centerY;
	int curr = -1;
	TickTimerTask tick;
	private Thread thread; 

	SimpleDateFormat sf;
	Calendar cal;
	int hour;
	int minute;
	int second;

	double rsecond ;
	double rminute ;
	double rhours ;
	Timer timer;
	static TimeZone timeZone;
	static String str;
	static ZoneId zone;
	public static void main(String args[]){

		new ClockGui();


	}

	public ClockGui() {

		new Window(this);


	}

	public synchronized void start() {
		thread = new Thread(this);
		thread.start();


	}
	public synchronized void stop() {
		try {	
			tick.cancel();
			
			System.gc();
			thread.join();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void run() {

		timer = new Timer();
		

		
		tick = new TickTimerTask();
		timer.schedule(tick, 0, 1000); //after 1s once repaint



	}

	class TickTimerTask extends TimerTask{

		@Override
		public void run() {	

				BufferStrategy bs = getBufferStrategy();
				if (bs == null){
					createBufferStrategy(2);
					return;
				} 			
				Graphics g = bs.getDrawGraphics();
				apaint(g);
				g.dispose();
				bs.show();

		}

	}

	public void apaint(Graphics g) {

		
		if(status==0){
			g.setColor(new Color(212, 234, 255));
			g.fillRect(0, 0, 400, 430);		
		}else if(status ==1){

			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(0, 0, 400, 430);	
		}else{
			g.setColor(Color.BLACK);
			g.fillRect(0, 0, 400, 430);	

		}
		//border clock	
		if(status == 0) {
			g.setColor(Color.LIGHT_GRAY);
			g.fillRect(0, 0, 400, 430);

			g.setColor(new Color(124, 136, 162));
			g.fillOval(25, spacing, 350, 350);	
			g.setColor(Color.WHITE);
			g.fillOval(35, spacing+10, 330, 330);
		}else if(status == 1){
			g.setColor(Color.BLACK);
			g.fillOval(25, spacing, 350, 350);	
			g.setColor(Color.WHITE);
			g.fillOval(35, spacing+10, 330, 330);
		}

		size = 400 -spacing;		
		centerX = 400/2;
		centerY = 400/2+10;


		//clock face
		drawClockFace(g);

		//number clock face
		drawNumberClock(g);

		//get system time
		cal = Calendar.getInstance(timeZone);
		sf = new SimpleDateFormat("EE MMM dd a");
		sf.setTimeZone(timeZone);
		
		hour = cal.get(Calendar.HOUR);
		minute = cal.get(Calendar.MINUTE);
		second = cal.get(Calendar.SECOND);	
		
		//	draw date and its box
		if(status ==0){

			g.setColor(Color.BLACK);
			g.drawRect(centerX -50 , centerY-200, 100, 20);
			g.drawString(sf.format(cal.getTime()), centerX + 7 - 50, centerY-185);
		}

		if(status == 1){
			g.setColor(Color.BLACK);
			g.drawRect(centerX -50 , centerY-200, 100, 20);		
			g.drawString(sf.format(cal.getTime()), centerX + 7 - 50, centerY-185);
		}

		if(status ==2){
			g.setColor(Color.YELLOW);
			g.drawRect(centerX -50 , centerY-200, 100, 20);	
			g.drawString(sf.format(cal.getTime()), centerX + 7 - 50, centerY-185);

		}

		//draws hands of clock
		if(status==2){
			drawHands(g,hour,minute,second,Color.RED,Color.YELLOW);
		}else{
			drawHands(g,hour,minute,second,Color.RED,Color.BLACK);
		}

		//draw center point of clock
		g.setColor(Color.BLACK);
		g.fillOval(centerX-5, centerY-5, 10, 10);

		g.setColor(Color.RED);
		g.fillOval(centerX-3, centerY-3, 6, 6);

	}

	/*-------------Clock Face----------------*/
	private void drawClockFace(Graphics g) {


		// tick marks
		for (int sec = 0; sec<60; sec++) {
			int ticStart;
			if (sec%5 == 0) {
				ticStart = size/2-10;
			}else{

				ticStart = size/2-5;
			}

			if(status ==2){
				drawRadius(g, centerX, centerY, radPerSecMin*sec, ticStart-20, size/2-20,Color.YELLOW);
			}else{
				drawRadius(g, centerX, centerY, radPerSecMin*sec, ticStart-20, size/2-20,Color.BLACK);
			}

		}
	}

	private void drawRadius(Graphics g, int x, int y, double angle,
			int minRadius, int maxRadius, Color colorNumber) {
		float sine = (float)Math.sin(angle);
		float cosine = (float)Math.cos(angle);
		int dxmin = (int)(minRadius * sine);
		int dymin = (int)(minRadius * cosine);
		int dxmax = (int)(maxRadius * sine);
		int dymax = (int)(maxRadius * cosine);
		g.setColor(colorNumber);
		g.drawLine(x+dxmin, y+dymin, x+dxmax, y+dymax);
	}
	/*-----------------------------*/

	/*--------------Clock Data and Numbers---------------*/
	//Calls method to draw each section of the clock
	private void drawNumberClock(Graphics g) {

		for(int num = 12 ;num > 0 ;num-- ){			
			drawnum(g,radPerNum*num,num);			
		}


	}
	//Draws numbers and weather data to clock
	private void drawnum(Graphics g, float angle,int n) {

		float sine = (float)Math.sin(angle);
		float cosine = (float)Math.cos(angle);
		int dx = (int)((size/2-20-25) * -sine);
		int dy = (int)((size/2-20-25) * -cosine);		

		g.drawString(""+n,dx+centerX-5,dy+centerY+5);


		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH");
		DateTimeFormatter dtfspec = DateTimeFormatter.ofPattern("HHmmss");
		LocalTime now = LocalTime.now(zone);
		boolean check = true;
		//midnight data call 
		if(Integer.parseInt(dtfspec.format(now)) == 000002 && check){
			try {
			AtmosClockPre.atmos(str);
			} catch (IOException ioe) {
				
			}
			check = false;

		}
		//stops midnight call from happening more than once
		if(Integer.parseInt(dtfspec.format(now)) == 000003){
			check = true;
		}

		int n1 = 0;
		//Midnight Correction
		if (Integer.parseInt(dtf.format(now)) == 0 && n == 12) {
			n1 -= 12;

		}

		//for pm shift
		if (Integer.parseInt(dtf.format(now)) > 12) {
			n1 += 12;

		}

		//for updating passed hours 
		if (Integer.parseInt(dtf.format(now)) > n + n1) {
			n1 += 12;

		}
		//for main temp colors on clock
		g.setColor(AtmosClockPre.getColor(n + n1));
		g.fillOval(centerX-10 +(int)((size/2-20-50) * -sine), centerY-10 + (int)((size/2-20-50) * -cosine), 20, 20);

		//for setting raining colors on clock
		g.setColor(AtmosClockPre.getRaining(n + n1));
		g.fillOval(centerX-5 +(int)((size/2-20-75) * -sine), centerY-5 + (int)((size/2-20-75) * -cosine), 10, 10);

		//for number color
		g.setColor(Color.BLACK);
	}
	/*-----------------------------*/

	/*---------------- Draws Clock Hands--------------------*/
	private void drawHands(Graphics g, double hour, double minute, double second, Color colorSecond, Color colorMHour) {

		rsecond = (second*6)*(Math.PI)/180;
		rminute = ((minute + (second / 60)) * 6) * (Math.PI) / 180;
		rhours = ((hour + (minute / 60)) * 30) * (Math.PI) / 180;

		g.setColor(colorSecond);
		g.drawLine(centerX, centerY, centerX + (int) (150 * Math.cos(rsecond - (Math.PI / 2))), centerY + (int) (150 * Math.sin(rsecond - (Math.PI / 2))));
		g.setColor(colorMHour);
		g.drawLine(centerX, centerY, centerX + (int) (120 * Math.cos(rminute - (Math.PI / 2))), centerY + (int) (120 * Math.sin(rminute - (Math.PI / 2))));
		g.drawLine(centerX, centerY, centerX + (int) (90 * Math.cos(rhours - (Math.PI / 2))), centerY + (int) (90 * Math.sin(rhours - (Math.PI / 2))));
	}

	/*-----------------------------*/





	// Changes colors of a clocked when clicked
	public void mouseClicked(MouseEvent arg0) {

		if(status==0){
			status =1;		

		}else if(status ==1){
			status=2;		

		}else{
			status =0;

		}

	}






	@Override
	public void mouseEntered(MouseEvent arg0) {}

	@Override
	public void mouseExited(MouseEvent arg0) {}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}
}
