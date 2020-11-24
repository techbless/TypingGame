import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScorePanel extends JPanel {
	private JLabel scoreLabel;
	private JLabel lifeLabel;
	private int score;
	
	public ScorePanel() {
		setBackground(Color.DARK_GRAY);
		
		scoreLabel = new JLabel("SCORE : 0");
		scoreLabel.setFont(new Font("Consolas", Font.BOLD, 18));
		scoreLabel.setForeground(Color.WHITE);
		add(scoreLabel);
		
		lifeLabel = new JLabel("LIFE : 3");
		lifeLabel.setFont(new Font("Consolas", Font.BOLD, 18));
		lifeLabel.setForeground(Color.WHITE);
		add(lifeLabel);
		
	}
	
	
	public void setScore(int score) {
		this.score = score;
		scoreLabel.setText("SCORE : " + score);
	}
	
	
	public void setLife(int nLife) {
		lifeLabel.setText("LIFE : " + nLife);
	}
}
