import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ScorePanel extends JPanel {
	private JLabel scoreLabel;
	private JLabel lifeLabel;
	private Vector<JLabel> labels = new Vector<>(10);
	
	public ScorePanel() {
		setBackground(Color.DARK_GRAY);
		
		setLayout(new GridLayout(13, 1));
		
		scoreLabel = new JLabel("SCORE : 0");
		scoreLabel.setFont(new Font("Consolas", Font.BOLD, 18));
		scoreLabel.setForeground(Color.WHITE);
		scoreLabel.setHorizontalAlignment(JLabel.CENTER);
		add(scoreLabel);
		
		lifeLabel = new JLabel("♥ ♥ ♥");
		lifeLabel.setFont(new Font(Font.SERIF, Font.BOLD, 22));
		lifeLabel.setForeground(Color.RED);
		lifeLabel.setHorizontalAlignment(JLabel.CENTER);
		add(lifeLabel);
		
		
		JLabel topTenLabel = new JLabel("TOPTEN");
		topTenLabel.setFont(new Font("Consolas", Font.BOLD, 18));
		topTenLabel.setForeground(Color.WHITE);
		topTenLabel.setHorizontalAlignment(JLabel.CENTER);
		add(topTenLabel);
		
		createLabels();
		TopTen topten = TopTen.getInstance();
		updateTopTen(topten.getTopTenPlayers());
	}
	
	
	private void createLabels() {
		for(int i = 0; i < 10; i++) {
			JLabel label = new JLabel("--");
			label.setFont(new Font("Consolas", Font.BOLD, 13));
			label.setForeground(Color.WHITE);
			label.setHorizontalAlignment(JLabel.CENTER);
			labels.add(label);
			
			ScorePanel.this.add(label);
		}
	}
	
	
	public void updateTopTen(Vector<Player> players) {
		for(int i = 0; i < players.size(); i++) {
			labels.get(i).setText(players.get(i).name + ": " + players.get(i).score);
		}
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
