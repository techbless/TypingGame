import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

public class WordSource {
	private static WordSource instance = new WordSource();
	private Vector<String> words;
	
	// SingleTon
	private WordSource() {
		words = new Vector<String>(30000);
		readFile();
	}
	
	public static WordSource getInstance() {
		return instance;
	}
	
	public String getWord() {
		int index = (int)(Math.random()*words.size());
		return words.get(index);
	}
	
	public Vector<String> getWords() {
		return words;
	}
	
	public void update(String word) {
		if(words.contains(word)) {
			removeWord(word);
			words.remove(word);
		}
		else {
			addWord(word);
			words.add(word);
		}
	}
	
	private void removeWord(String word) {
		StringBuffer sb = new StringBuffer("");
		
		for(int i = 0; i < words.size(); i++) {
			String w = words.get(i);
			
			if(w.equals(word)) {
				continue;
			}
			
			sb.append(w);
			sb.append("\n");
		}
		
		try {
			FileWriter fw = new FileWriter(new File("words.txt"));
			fw.write(sb.toString());
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void addWord(String word) {
		try {
			FileWriter fw = new FileWriter(new File("words.txt"), true);
			fw.write(word + "\n");
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void readFile() {
		try {
			Scanner fsc = new Scanner(new FileReader("words.txt"));
			
			while(fsc.hasNext()) {
				String word = fsc.nextLine();
				words.add(word);
			}
			
			fsc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
		
	}
}
