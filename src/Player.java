
@SuppressWarnings("rawtypes")
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