package Game_lat_hinh;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

public class Task extends  TimerTask {
	  @Override
	  public void run() {
		JOptionPane.showMessageDialog(null, "Còn 5p nữa là hết giờ!");
		TaskSmall end = new TaskSmall();
		Timer timer = new Timer();
	    timer.schedule(end, 5*60*1000);
	  }
}
      class TaskSmall extends  TimerTask {
	  @Override
	  public void run() {
		JOptionPane.showMessageDialog(null, "Hết thời gian chơi");
	    System.exit(0);
	  }
}
