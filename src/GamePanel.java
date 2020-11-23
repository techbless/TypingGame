import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GamePanel extends JPanel {
	
	GameGroundPanel gameGroundPanel = new GameGroundPanel();
	WordSource wordSource;
	Vector<GameObject> gameObjects;
	
	
	public GamePanel() {
		gameObjects = new Vector<GameObject>(30);
		wordSource = WordSource.getInstance();

		setLayout(new BorderLayout());
		add(gameGroundPanel, BorderLayout.CENTER);
		add(new InputPanel(), BorderLayout.SOUTH);
		
		new Thread(new GameObjectGenerator()).start();
		new Thread(new GameObjectMover()).start();
	}
	
	
	class GameGroundPanel extends JPanel {
		public GameGroundPanel() {
			setLayout(null);
			setBackground(Color.DARK_GRAY);
		}
		
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			ImageIcon icon = new ImageIcon("background.jpg");
			Image img = icon.getImage();
			g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
		}
	}
	
	
	class InputPanel extends JPanel {
		public InputPanel() {
			setBackground(Color.GRAY);
			JTextField inputField = new JTextField(40);
			add(inputField);
			
			inputField.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					JTextField tField = (JTextField)(e.getSource());
					String inWord = tField.getText();
					
					for(int i = 0; i < gameObjects.size(); i++) {
						GameObject targetObj = gameObjects.get(i);
						if(targetObj.getWord().equals(inWord)) {
							gameGroundPanel.remove(targetObj);
							gameGroundPanel.revalidate();
							gameGroundPanel.repaint();
							gameObjects.remove(i);
							System.out.println("Answer : " + inWord);
						}
					}
					
					inputField.setText("");
				}
			});
		}
	}
	
	
	class GameObjectMover implements Runnable {

		@Override 
		public void run() {
			int count = 0;
			while(true) {
				count++;
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
				for(int i = 0; i < gameObjects.size(); i++) {
					int deltaY = 0;
					if(count % 3 == 0) {
						deltaY = (int)(Math.random() * 2) * ((Math.random() > 0.5 ? 1 : -1));						
					}
					
					GameObject targetObj = gameObjects.get(i);
					targetObj.setLocation(targetObj.getX() + 1, targetObj.getY() + deltaY);
				}
			}
		}
		
	}
	
	
	class GameObjectGenerator implements Runnable {
		@Override
		public void run() {
			int i = 0;
			while(true) {
				Ghost newObject = new Ghost(wordSource.getWord(), 0, (int)(Math.random()*600));
				gameObjects.add(newObject);
				gameGroundPanel.add(newObject);
				gameGroundPanel.repaint();
								
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
