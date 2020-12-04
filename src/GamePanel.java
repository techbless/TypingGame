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

@SuppressWarnings("serial")
public class GamePanel extends JPanel {
	
	private GameGroundPanel gameGroundPanel = new GameGroundPanel();
	private Thread generator;
	private Thread mover;
	private WordSource wordSource;
	private Vector<GameObject> gameObjects;
	private EvaluationUpdater evaluationUpdater;
	private Baby baby;
	
	private JTextField inputField;
	private NoticeLabel noticeLabel;
	
	private String name = null;
	private int difficulty;
	private int score = 0;
	private int nLife = 3;
	private boolean isPlaying = false;
	
	private int movingDelay = 50;
	private int generatingDelay = 2200;
	
	private ImageIcon backgroundIcon;
	
	
	public GamePanel(EvaluationUpdater evaluationUpdater) {
		backgroundIcon = new ImageIcon(this.getClass().getResource("img/background_easy.jpg"));

		this.evaluationUpdater = evaluationUpdater;
		
		gameObjects = new Vector<GameObject>(30);
		wordSource = WordSource.getInstance();

		setLayout(new BorderLayout());
		
		add(gameGroundPanel, BorderLayout.CENTER);
		add(new InputPanel(), BorderLayout.SOUTH);
		
		noticeLabel.showNotice("Enter your name and press the start button. (Dont Enter)", 10000, Color.YELLOW);
	}
	
	
	public void startGame() {
		name = inputField.getText();
		if(name.equals("")) {
			noticeLabel.showNotice("Enter your name and try again.", 3000, Color.PINK);
			return;
		}
		
		nLife = 3;
		difficulty = 1;
		score = 0;
		isPlaying = true;
		
		inputField.setText("");

		// Remove gameObjects from panel.
		for(int i = 0; i < gameObjects.size(); i++) {			
			gameGroundPanel.remove(gameObjects.get(i));
		}
		
		gameGroundPanel.revalidate();
		gameGroundPanel.repaint();
		
		// Clear container.
		gameObjects.clear();

		resetDifficulty();
		
		generator = new Thread(new GameObjectGenerator());
		mover = new Thread(new GameObjectMover());
		
		generator.start();
		mover.start();
		evaluationUpdater.start();
		evaluationUpdater.setLife(nLife);
		evaluationUpdater.setScore(0);
		
		noticeLabel.showNotice("Game Start! Save the baby.", 2000, Color.GREEN);
	}
	
	
	public void endGame() {
		// Interrupt threads
		generator.interrupt();
		mover.interrupt();
		
		// End evaluation updater
		evaluationUpdater.end();
		noticeLabel.showNotice("Game End... Your score is " + score + ".", 5000);
		
		// Update Top10 board
		TopTen topten = TopTen.getInstance();
		topten.updateTopTen(name, score);
		evaluationUpdater.updateTopTen(topten.getTopTenPlayers());
		
		isPlaying = false;
		name = null;
	}
	
	
	class GameGroundPanel extends JPanel {
		public GameGroundPanel() {
			setLayout(null);
			setBackground(Color.DARK_GRAY);
			
			// Add baby on the panel
			baby = new Baby(920, 430);
			add(baby);
			
			// Notice Label
			noticeLabel = new NoticeLabel();
			add(noticeLabel);
			noticeLabel.setVisible(false);
		}
		
		
		// Set the background of game ground.
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			Image image = backgroundIcon.getImage();
			g.drawImage(image, 0, 0, getWidth(), getHeight(), null);
		}
	}
	
	
	class InputPanel extends JPanel {
		public InputPanel() {
			setBackground(Color.DARK_GRAY);
			inputField = new JTextField(40);
			inputField.setHorizontalAlignment(JTextField.CENTER);
			add(inputField);
			
			inputField.addKeyListener(new KeyAdapter() {

				@Override
				public void keyPressed(KeyEvent e) {
					char keyChar = e.getKeyChar();
					if(isPlaying) {
						evaluationUpdater.appendDummy(keyChar);						
					}
				}
				
			});
			
			inputField.addActionListener(new AnswerCheckListener());
		}
		
		
		class AnswerCheckListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(!isPlaying) { 
					inputField.setText("");
					return; 
				}
				
				JTextField tField = (JTextField)(e.getSource());
				String inWord = tField.getText();
				tField.setText("");
				
				boolean isFoundAnswer = false;
				
				for(int i = 0; i < gameObjects.size(); i++) {
					GameObject targetObj = gameObjects.get(i);
					
					// If not target game object
					if(!targetObj.getWord().equals(inWord)) {
						continue;
					}
					
					isFoundAnswer = true;
					
					if(targetObj instanceof Toy) {
						if(nLife < 5) {
							nLife++;
							evaluationUpdater.setLife(nLife);
							noticeLabel.showNotice("You got a life!", 1500, Color.CYAN);
						}
						else {
							noticeLabel.showNotice("You can't have more than 5 lives.", 1500, Color.ORANGE);
						}
					}
					else if(targetObj instanceof Ghost) {
						evaluationUpdater.increaseAccuracy();
						evaluationUpdater.setScore(++score);
						checkAndUpdateDifficulty();
					}
					
					gameGroundPanel.remove(targetObj);
					gameGroundPanel.revalidate();
					gameGroundPanel.repaint();
					gameObjects.remove(i);
					
					System.out.println("Answer : " + inWord);
				}
				
				if(!isFoundAnswer) {
					evaluationUpdater.decreaseAccuracy();
				}
				
			}
		}
	}
	
	
	public void resetDifficulty() {
		backgroundIcon = new ImageIcon(this.getClass().getResource("img/background_easy.jpg"));
		generatingDelay = 2200;
		movingDelay = 50;
	}
	
	
	public void checkAndUpdateDifficulty() {
		int beforeDifficulty = difficulty;
		if(score >= 65) { // Level 5
			difficulty = 5;
			generatingDelay = 1400; 
			movingDelay = 30;
		}
		else if(score >= 45) { // Level 4
			difficulty = 4;
			generatingDelay = 1600; 
			movingDelay = 35;
		}
		else if(score >= 30) { // Level 3
			difficulty = 3;
			generatingDelay = 1800;
			movingDelay = 40;
		}
		else if(score >= 15) { // Level 2
			difficulty = 2;
			generatingDelay = 2000;
			movingDelay = 45;
		}
		
		// When difficulty is changed
		if(beforeDifficulty != difficulty) {
			noticeLabel.showNotice("Level " + difficulty + "!", 1500);
			if(difficulty == 2) {
				backgroundIcon = new ImageIcon(this.getClass().getResource("img/background_mid.jpg"));				
			}
			else if(difficulty == 4) {
				backgroundIcon = new ImageIcon(this.getClass().getResource("img/background_hard.jpg"));
			}
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
					
					// If the ghost doesn't touch the baby, skip this loop
					if(!baby.isTouched(targetObj)) {
						continue;
					}
					
					if(targetObj instanceof Ghost) {
						noticeLabel.showNotice("The ghost startled the baby. [ ♥ - 1 ]", 1500);
						
						nLife--;
						evaluationUpdater.setLife(nLife);
						
						if(nLife <= 0) {
							endGame();
						}
					}
					
					// Remove it regardless of object type
					gameGroundPanel.remove(targetObj);
					gameObjects.remove(i);
				}
				
				try {
					Thread.sleep(movingDelay);
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
				
				if(i % 10 == 0) { // Generate Toy at every 10 times
					newObject = new Toy(wordSource.getWord(), 0, y);
				}
				else {
					newObject = new Ghost(wordSource.getWord(), 0, y);
				}
				
				gameObjects.add(newObject);
				gameGroundPanel.add(newObject);
				gameGroundPanel.repaint();
					
				try {
					Thread.sleep(generatingDelay);
				} catch (InterruptedException e) {
					return;
				}
				
				i++;
			}
		}
	}
}
