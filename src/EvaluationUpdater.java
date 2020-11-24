
public class EvaluationUpdater {
	private SpeedPanel speedPanel;
	private ScorePanel scorePanel;
	private String inputStringDummy = "";
	private int totalWordCount = 0;
	private int rightWordCount = 0;
	private Thread wpmUpdater;
	private long startTimeInMillis;
	
	
	public EvaluationUpdater(SpeedPanel speedPanel, ScorePanel scorePanel) {
		this.speedPanel = speedPanel;
		this.scorePanel = scorePanel;
	}
	
	
	public void start() {
		wpmUpdater = new Thread(new WPMUpdater());
		wpmUpdater.start();
		startTimeInMillis = System.currentTimeMillis();
	}
	
	
	public void end() {
		wpmUpdater.interrupt();
	}
	
	
	public void increaseAccuracy() {
		totalWordCount++;
		rightWordCount++;
		updateAccuracy();
	}
	
	
	public void decreaseAccuracy() {
		totalWordCount++;
		updateAccuracy();
	}
	
	
	private void updateAccuracy() {
		int accuracy = (int)((float)rightWordCount / totalWordCount * 100);
		speedPanel.setAccuracy(accuracy);
	}
	
	
	public void appendDummy(char input) {
		inputStringDummy += input;
	}
	
	
	public void updateWPM() {
		long currentTimeInMillis = System.currentTimeMillis();
		long elapsedTimeInMillis = currentTimeInMillis - startTimeInMillis;
		
		int wpm = (int)((float)(inputStringDummy.length() / 5) / (float)(elapsedTimeInMillis / 1000) * 60);
		speedPanel.setWPM(wpm);
	}
	
	
	public void increaseScore(int score) {
		scorePanel.setScore(score);
	}
	
	
	class WPMUpdater implements Runnable {

		@Override
		public void run() {
			while(true) {
				updateWPM();
				
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					return;
				}
			}
		}
		
	}
}