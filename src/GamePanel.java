import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GamePanel extends JPanel {
	public GamePanel() {
		setLayout(new BorderLayout());
		add(new GameGroundPanel(), BorderLayout.CENTER);
		add(new InputPanel(), BorderLayout.SOUTH);
	}
	
	class GameGroundPanel extends JPanel {
		public GameGroundPanel() {
			setBackground(Color.DARK_GRAY);
			add(new JLabel("게임 화면"));			
		}
	}
	
	class InputPanel extends JPanel {
		public InputPanel() {
			setBackground(Color.GRAY);
			add(new JTextField(40));			
		}
	}
}
