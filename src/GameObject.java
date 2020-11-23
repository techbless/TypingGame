import javax.swing.JLabel;

public abstract class GameObject extends JLabel {	
	private String word;
	
	public GameObject(String word, int x, int y) {
		this.word = word;
		draw(word, x, y);
		
	}
	
	abstract void draw(String word, int x, int y);
	
	public String getWord() {
		return word;
	}
	
	public void setWord(String word) {
		this.word = word;
	}
}
