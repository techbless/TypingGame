import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Scanner;
import java.util.Vector;

public class TopTen {
	private static TopTen instance = new TopTen();
	private Vector<Player> topten;
	
	private TopTen() {
		topten = new Vector<Player>(11);
		readFile();
	}
	
	
	public static TopTen getInstance() {
		return instance;
	}
	
	
	public void updateTopTen(String name, int score) {
		boolean isFound = false;
		for(int i = 0; i < topten.size(); i++) {
			Player player = topten.get(i);
			String nameInFile = player.name;
			
			// If the player exists arleady
			if(nameInFile.equals(name)) {
				isFound = true;
				if(player.score < score) {
					player.score = score;	
				}
			}
		}
		
		// If a new player
		if(!isFound) {
			Player p = new Player(name, score);
			topten.add(p);			
		}
		
		// Order by score
		Collections.sort(topten, Collections.reverseOrder());
		
		// Remove all after index number 9
		for(int i = 10; i < topten.size(); i++) {
			topten.remove(i);
		}
		
		// Save players' info in file
		saveFile(10);
	}
	
	
	public Vector<Player> getTopTenPlayers() {
		return topten;
	}
	
	
	private void readFile() {
		try {
			Scanner fsc = new Scanner(new FileReader("topten.txt"));
			
			while(fsc.hasNext()) {
				String name = fsc.next();
				int score = fsc.nextInt();
				topten.add(new Player(name, score));
			}
			
			fsc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return;
		}
	}
	
	
	private void saveFile(int n) {
		if(n > topten.size()) {
			n = topten.size();
		}
		
		StringBuffer sb = new StringBuffer("");
		for(int i = 0; i < n; i++) {
			Player p = topten.get(i);
			sb.append(p.name);
			sb.append(" ");
			sb.append(p.score);
			sb.append("\n");
		}
		
		try {
			FileWriter fw = new FileWriter(new File("topten.txt"));
			fw.write(sb.toString());
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
