import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Baby extends GameObject {

	public Baby(int x, int y) {
		super("BABY", x, y);
	}

	@Override
	void draw(String word, int x, int y) {
		ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("img/baby.png"));
		Image image = imageIcon.getImage();
		Image newImage = image.getScaledInstance(120, 120, Image.SCALE_SMOOTH);
		imageIcon = new ImageIcon(newImage);
		
		this.setIcon(imageIcon);
		this.setText(word);
		this.setFont(new Font("Consolas", Font.BOLD, 15));
		
		this.setVerticalTextPosition(JLabel.BOTTOM);
		this.setHorizontalTextPosition(JLabel.CENTER);
		
		setForeground(Color.CYAN);
		setLocation(x, y);
		setSize(150, 140);
		setOpaque(false);
	}
	
	
	public boolean isTouched(GameObject gameObject) {		
		return (gameObject.getX() >= this.getX());
	}

}
