import java.awt.Color;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Ghost extends GameObject {

	public Ghost(String word, int x, int y) {
		super(word, x, y);
		System.out.println(word + " Ghost Created! (" + x + ", "+ y + ")");
	}

	@Override
	void draw(String word, int x, int y) {
		ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("img/ghost.png"));
		Image image = imageIcon.getImage();
		Image newImage = image.getScaledInstance(70, 70, Image.SCALE_SMOOTH);
		imageIcon = new ImageIcon(newImage);
		
		this.setIcon(imageIcon);
		this.setText(word);
		this.setVerticalTextPosition(JLabel.BOTTOM);
		this.setHorizontalTextPosition(JLabel.CENTER);
		
		setForeground(Color.WHITE);
		setLocation(x, y);
		setSize(100, 90);
		setOpaque(false);
	}
	

}
