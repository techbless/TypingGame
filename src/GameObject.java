import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public abstract class GameObject extends JLabel {	
	private String word;
	
	public GameObject(String word, int x, int y) {
		this.word = word;
		
		ImageIcon imageIcon = new ImageIcon("ghost.png");
		Image image = imageIcon.getImage();
		Image newImage = image.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
		imageIcon = new ImageIcon(newImage);
		
		this.setIcon(imageIcon);
		this.setText(word);
		this.setVerticalTextPosition(JLabel.BOTTOM);
		this.setHorizontalTextPosition(JLabel.CENTER);
		
		setForeground(Color.WHITE);
		setLocation(x, y);
		setBackground(Color.YELLOW);
		setSize(100, 90);
		setOpaque(false);
	}
	
	public String getWord() {
		return word;
	}
	
	public void setWord(String word) {
		this.word = word;
	}
}
