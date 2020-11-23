import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GamePanel extends JPanel {
	
	private GameGroundPanel gameGroundPanel = new GameGroundPanel();
	private Thread generator;
	private Thread mover;
	private WordSource wordSource;
	private Vector<GameObject> gameObjects;
	Baby baby;
	
	
	public GamePanel() {
		gameObjects = new Vector<GameObject>(30);
		wordSource = WordSource.getInstance();

		setLayout(new BorderLayout());
		
		add(gameGroundPanel, BorderLayout.CENTER);
		add(new InputPanel(), BorderLayout.SOUTH);
	}
	
	
	public void startGame() {

		// Remove gameObjects from panel.
		for(int i = 0; i < gameObjects.size(); i++) {			
			gameGroundPanel.remove(gameObjects.get(i));
		}
		
		gameGroundPanel.revalidate();
		gameGroundPanel.repaint();
		
		// Clear container.
		gameObjects.clear();

		generator = new Thread(new GameObjectGenerator());
		mover = new Thread(new GameObjectMover());
		
		generator.start();
		mover.start();
	}
	
	
	public void endGame() {
		generator.interrupt();
		mover.interrupt();
	}
	
	
	class GameGroundPanel extends JPanel {
		public GameGroundPanel() {
			setLayout(null);
			setBackground(Color.DARK_GRAY);
			
			baby = new Baby(950, 430);
			add(baby);
		}
		
		
		// Set the background of game ground.
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			ImageIcon icon = new ImageIcon("resources/img/background.jpg");
			Image img = icon.getImage();
			g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
		}
	}
	
	
	class InputPanel extends JPanel {
		public InputPanel() {
			setBackground(Color.DARK_GRAY);
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
				
				for(int i = 0; i < gameObjects.size(); i++) {
					int deltaY = 0;
					if(count % 3 == 0) {
						deltaY = (int)(Math.random() * 5) * ((Math.random() > 0.5 ? 1 : -1));
					}
					
					GameObject targetObj = gameObjects.get(i);
					targetObj.setLocation(targetObj.getX() + 1, targetObj.getY() + deltaY);
					
					if(baby.isTouched(targetObj)) {
						endGame();
					}
				}
				
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					return;
				}
			}
		}
		
	}
	
	
	class GameObjectGenerator implements Runnable {
		@Override
		public void run() {
			int i = 1;
			while(true) {
				GameObject newObject;
				if(i % 5 == 0) {
					newObject = new Toy(wordSource.getWord(), 0, (int)(Math.random()*600));
				}
				else {
					newObject = new Ghost(wordSource.getWord(), 0, (int)(Math.random()*600));
				}
				gameObjects.add(newObject);
				gameGroundPanel.add(newObject);
				gameGroundPanel.repaint();
								
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					return;
				}
				
				i++;
			}
		}
	}
}
