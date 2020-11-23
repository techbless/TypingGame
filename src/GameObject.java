import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public abstract class GameObject extends JLabel {	
	private String word;
	
	public GameObject(String word, int x, int y) {
		//super("\n" + word, new ImageIcon("ghost.png"), JLabel.CENTER);
		this.setIcon(new ImageIcon("ghost.png"));
		this.setText(word);
		this.setVerticalTextPosition(JLabel.BOTTOM);
		this.setHorizontalTextPosition(JLabel.CENTER);
		setForeground(Color.WHITE);
		setLocation(x, y);
		setSize(100, 150);
		setOpaque(false);
	}
	
	public String getWord() {
		return word;
	}
	
	public void setWord(String word) {
		this.word = word;
	}
}
