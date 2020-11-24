import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GamePanel extends JPanel {
	
	private GameGroundPanel gameGroundPanel = new GameGroundPanel();
	private Thread generator;
	private Thread mover;
	private WordSource wordSource;
	private Vector<GameObject> gameObjects;
	private EvaluationUpdater evaluationUpdater;
	private Baby baby;
	
	private int score = 0;
	private int nLife = 3;
	private boolean isPlaying = false;
	
	
	public GamePanel(EvaluationUpdater evaluationUpdater) {
		this.evaluationUpdater = evaluationUpdater;
		
		gameObjects = new Vector<GameObject>(30);
		wordSource = WordSource.getInstance();

		setLayout(new BorderLayout());
		
		add(gameGroundPanel, BorderLayout.CENTER);
		add(new InputPanel(), BorderLayout.SOUTH);
	}
	
	
	public void startGame() {
		isPlaying = true;

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
		evaluationUpdater.start();
		
		nLife = 3;
		evaluationUpdater.setLife(nLife);
	}
	
	
	public void endGame() {
		isPlaying = false;
		
		generator.interrupt();
		mover.interrupt();
		evaluationUpdater.end();
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
			ImageIcon icon = new ImageIcon(this.getClass().getResource("img/background.jpg"));
			Image img = icon.getImage();
			g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
		}
	}
	
	
	class InputPanel extends JPanel {
		public InputPanel() {
			setBackground(Color.DARK_GRAY);
			JTextField inputField = new JTextField(40);
			add(inputField);
			
			inputField.addKeyListener(new KeyAdapter() {

				@Override
				public void keyPressed(KeyEvent e) {
					char keyChar = e.getKeyChar();
					evaluationUpdater.appendDummy(keyChar);
				}
				
			});
			
			inputField.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if(!isPlaying) { 
						inputField.setText("");
						return; 
					}
					
					JTextField tField = (JTextField)(e.getSource());
					String inWord = tField.getText();
					
					boolean isFoundAnswer = false;
					for(int i = 0; i < gameObjects.size(); i++) {
						GameObject targetObj = gameObjects.get(i);
						if(targetObj.getWord().equals(inWord)) {
							isFoundAnswer = true;
							
							if(targetObj instanceof Toy) {
								if(nLife < 3) {
									nLife++;
									evaluationUpdater.setLife(nLife);									
								}
							}
							else if(targetObj instanceof Ghost) {
								evaluationUpdater.increaseAccuracy();
								evaluationUpdater.setScore(++score);								
							}
							
							gameGroundPanel.remove(targetObj);
							gameGroundPanel.revalidate();
							gameGroundPanel.repaint();
							gameObjects.remove(i);
							
							
							System.out.println("Answer : " + inWord);
						}
					}
					
					if(!isFoundAnswer) {
						evaluationUpdater.decreaseAccuracy();
					}
					
					inputField.setText("");
				}
			});
		}
	}
	
	
	class GameObjectMover implements Runnable {
		
		private boolean isInSafeArea(GameObject obj, int y) {
			return (gameGroundPanel.getHeight() >= y + obj.getHeight() && y >= 0);
		}

		@Override 
		public void run() {
			int count = 0;
			while(true) {
				count++;
				
				for(int i = 0; i < gameObjects.size(); i++) {
					int deltaY = 0;
					if(count % 5 == 0) {
						deltaY = (int)(Math.random() * 3 + 2) * ((Math.random() > 0.5 ? 1 : -1));
					}
					
					GameObject targetObj = gameObjects.get(i);
					int beforeY = targetObj.getY();
					
					// Check if new location is in safe area or not.
					if(!isInSafeArea(targetObj, beforeY + deltaY)) {
						deltaY = 0; // if not, don't move to Y direction.
					}
					targetObj.setLocation(targetObj.getX() + 1, targetObj.getY() + deltaY);						
					
					if(baby.isTouched(targetObj)) {
						if(targetObj instanceof Ghost) {
							nLife--;
							evaluationUpdater.setLife(nLife);
							
							if(nLife <= 0) {
								endGame();															
							}
						}
						
						gameGroundPanel.remove(targetObj);
						gameObjects.remove(i);
					}
				}
				
				int delay = 50;
				if(score >= 65) {delay = 30; }
				else if(score >= 45) { delay = 35; }
				else if(score >= 30) { delay = 40; }
				else if(score >= 15) { delay = 45; }
				
				try {
					Thread.sleep(delay);
					
				} catch (InterruptedException e) {
					return;
				}
			}
		}
		
	}
	
	
	class GameObjectGenerator implements Runnable {
		private int i = 2;
		
		private int getRandomY() {
			int y = 0;
			
			if(i == 0) { // Upper Side 0 ~ 130
				y = (int)(Math.random() * 130);
				i++;
			}
			else if(i == 1) { // Lower Side 380 ~ 580
				y = (int)(Math.random() * 200 + 380);
				i++;
			}
			else if(i == 2) { // Middle Side 180 ~ 330
				y = (int)(Math.random() * 150 + 180);
				i = 0;
			}
			
			return y;
		}
		
		@Override
		public void run() {
			int i = 1;
			while(true) {
				GameObject newObject;
				int y = getRandomY();
				
				if(i % 10 == 0) {
					newObject = new Toy(wordSource.getWord(), 0, y);
				}
				else {
					newObject = new Ghost(wordSource.getWord(), 0, y);
				}
				
				gameObjects.add(newObject);
				gameGroundPanel.add(newObject);
				gameGroundPanel.repaint();
					
				int delay = 2100;
				if(score >= 65) { delay = 1350; }
				else if(score >= 45) { delay = 1500; }
				else if(score >= 30) { delay = 1750; }
				else if(score >= 15) { delay = 1950; }
				
				try {
					System.out.println(delay);
					Thread.sleep(delay);
				} catch (InterruptedException e) {
					return;
				}
				
				i++;
			}
		}
	}
}
