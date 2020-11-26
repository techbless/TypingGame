import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Toy extends GameObject {

	public Toy(String word, int x, int y) {
		super(word, x, y);
		System.out.println(word + " Toy Created! (" + x + ", "+ y + ")");
	}

	@Override
	void draw(String word, int x, int y) {
		ImageIcon imageIcon = new ImageIcon(this.getClass().getResource("img/toy.png"));
		Image image = imageIcon.getImage();
		Image newImage = image.getScaledInstance(62, 45, Image.SCALE_SMOOTH);
		imageIcon = new ImageIcon(newImage);
		
		this.setIcon(imageIcon);
		this.setText(word);
		this.setFont(new Font("Consolas", Font.BOLD, 15));
		
		this.setVerticalTextPosition(JLabel.BOTTOM);
		this.setHorizontalTextPosition(JLabel.CENTER);
		
		setForeground(Color.WHITE);
		setLocation(x, y);
		setSize(100, 90);
		setOpaque(false);
	}

}
