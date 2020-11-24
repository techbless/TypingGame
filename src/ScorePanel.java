import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class ScorePanel extends JPanel {
	private JLabel scoreLabel;
	private JLabel lifeLabel;
	
	public ScorePanel() {
		setBackground(Color.DARK_GRAY);
		
		setLayout(new GridLayout(2, 1));
		
		scoreLabel = new JLabel("SCORE : 0");
		scoreLabel.setFont(new Font("Consolas", Font.BOLD, 18));
		scoreLabel.setForeground(Color.WHITE);
		scoreLabel.setHorizontalAlignment(JLabel.CENTER);
		add(scoreLabel);
		
		lifeLabel = new JLabel("♥♥♥");
		lifeLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 22));
		lifeLabel.setForeground(Color.RED);
		lifeLabel.setHorizontalAlignment(JLabel.CENTER);
		add(lifeLabel);
		
	}
	
	
	public void setScore(int score) {
		scoreLabel.setText("SCORE : " + score);
	}
	
	
	public void setLife(int nLife) {
		String lifeInString = "";
		
		if (nLife == 0) {
			lifeInString = "NO LIFE";
		}
		else {
			for(int i = 0; i < nLife; i++) {
				lifeInString += "♥";
			}			
		}
		
		lifeLabel.setText(lifeInString);
	}
}
