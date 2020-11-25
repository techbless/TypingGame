import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
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
	
	
	@SuppressWarnings("unchecked")
	public void updateTopTen(String name, int score) {
		Player p = new Player(name, score);
		topten.add(p);
		Collections.sort(topten, Collections.reverseOrder());
		saveFile(10);
		
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
	
	
	class Player implements Comparable {
		public String name;
		public int score;
		
		public Player(String name, int score) {
			this.name = name;
			this.score = score;
		}


		@Override
		public int compareTo(Object o) {
			Player player = (Player)o;
			if (this.score < player.score) {
             return -1;
			} else if (this.score == player.score) {
				return 0;
			} else {
				return 1;
			}
		}
	}
}
