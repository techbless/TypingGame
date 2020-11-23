import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class SpeedPanel extends JPanel {
	public SpeedPanel() {
		GridLayout grid = new GridLayout(2, 1);
		grid.setVgap(-30);
		setLayout(grid);
		
		
		JLabel wpmLabel = new JLabel("WPM : 80", JLabel.CENTER);
		JLabel accuracyLabel = new JLabel("ACCURACY : 99%", JLabel.CENTER);
		
		wpmLabel.setFont(new Font("Consolas", Font.PLAIN, 18));
		accuracyLabel.setFont(new Font("Consolas", Font.PLAIN, 18));
		
		add(wpmLabel);
		add(accuracyLabel);
	}
}
