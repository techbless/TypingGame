import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class SpeedPanel extends JPanel {
	private int wpm;
	private int accuracy;
	private JLabel wpmLabel;
	private JLabel accuracyLabel;
	
	public SpeedPanel() {
		wpm = 0;
		accuracy = 0;
		
		setBackground(Color.DARK_GRAY);
		GridLayout grid = new GridLayout(2, 1);
		grid.setVgap(-30);
		setLayout(grid);
		
		
		wpmLabel = new JLabel("WPM : 0", JLabel.CENTER);
		accuracyLabel = new JLabel("ACCURACY : 100%", JLabel.CENTER);
		
		wpmLabel.setFont(new Font("Consolas", Font.PLAIN, 18));
		accuracyLabel.setFont(new Font("Consolas", Font.PLAIN, 18));
		
		wpmLabel.setForeground(Color.WHITE);
		accuracyLabel.setForeground(Color.WHITE);
		
		add(wpmLabel);
		add(accuracyLabel);
	}
	
	public void setWPM(int wpm) {
		wpmLabel.setText("WPM : " + Integer.toString(wpm));
	}
	
	public void setAccuracy(int accuracy) {
		accuracyLabel.setText("ACCURACY : " + Integer.toString(accuracy) + "%");		
	}
	
}
