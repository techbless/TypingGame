import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Baby extends GameObject {

	public Baby(int x, int y) {
		super("Baby", x, y);
	}

	@Override
	void draw(String word, int x, int y) {
		ImageIcon imageIcon = new ImageIcon("resources/img/baby.png");
		Image image = imageIcon.getImage();
		Image newImage = image.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
		imageIcon = new ImageIcon(newImage);
		
		this.setIcon(imageIcon);
		this.setText(word);
		this.setVerticalTextPosition(JLabel.BOTTOM);
		this.setHorizontalTextPosition(JLabel.CENTER);
		
		setForeground(Color.WHITE);
		setLocation(x, y);
		setSize(150, 170);
		setOpaque(false);
	}

}
