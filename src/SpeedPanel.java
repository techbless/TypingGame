import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class SpeedPanel extends JPanel {
	public SpeedPanel() {
		GridLayout grid = new GridLayout();
		setLayout(grid);
		
		JLabel wpmLabel = new JLabel("WPM : ");
		JLabel accuracyLabel = new JLabel("WPM : ");
		
		JLabel wpm = new JLabel("100", JLabel.CENTER);
		JLabel accuracy = new JLabel("100%", JLabel.CENTER);
		
		wpmLabel.setHorizontalAlignment(JLabel.RIGHT);
		accuracyLabel.setHorizontalAlignment(JLabel.RIGHT);
		
		add(wpmLabel);
		add(wpm);
		add(accuracyLabel);
		add(accuracy);
	}
}
