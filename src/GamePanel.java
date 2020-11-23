import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

public class GamePanel extends JPanel {
	
	GameGroundPanel gameGroundPanel = new GameGroundPanel();
	Vector<GameObject> gameObjects;
	
	public GamePanel() {
		gameObjects = new Vector<GameObject>(30);

		setLayout(new BorderLayout());
		new Thread(new GameObjectGenerator()).start();
		add(gameGroundPanel, BorderLayout.CENTER);
		add(new InputPanel(), BorderLayout.SOUTH);
		
	}
	
	class GameGroundPanel extends JPanel {
		public GameGroundPanel() {
			setLayout(null);
			setBackground(Color.DARK_GRAY);
		}
	}
	
	class InputPanel extends JPanel {
		public InputPanel() {
			setBackground(Color.GRAY);
			add(new JTextField(40));			
		}
	}
	
	
	
	
	class GameObjectGenerator implements Runnable {

		@Override
		public void run() {
			int i = 0;
			while(true) {
				
				Ghost newObject = new Ghost("dsfdsfsd", (int)(Math.random()*600), (int)(Math.random()*600));
				gameObjects.add(newObject);
				gameGroundPanel.add(newObject);
				gameGroundPanel.repaint();
				
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			
		}
		
	}
}
