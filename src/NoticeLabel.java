import java.awt.Color;
import java.awt.Font;

import javax.swing.JLabel;

@SuppressWarnings("serial")
public class NoticeLabel extends JLabel {
	public NoticeLabel() {
		setText("Notice!");
		setFont(new Font("Consolas", Font.BOLD, 30));
		
		setForeground(Color.RED);
		setLocation(50, 30);
		setSize(1000, 50);
		setOpaque(false);
	}
	
	
	public void showNotice(String message, int durationInMillis) {
		showNotice(message, durationInMillis, Color.RED);
	}
	
	
	public void showNotice(String message, int durationInMillis, Color color) {
		setVisible(true);
		setForeground(color);
		setText(message);
		new Thread(new AutoHider(durationInMillis)).start();
	}
	
	
	private void hideNotice() {
		setVisible(false);
	}
	
	
	class AutoHider implements Runnable {
		private int durationInMillis;
		
		public AutoHider(int durationInMillis) {
			this.durationInMillis = durationInMillis;
		}

		@Override
		public void run() {
			try {
				Thread.sleep(durationInMillis);
			} catch (InterruptedException e) {
				return;
			}
			
			hideNotice();
		}
		
	}
}
