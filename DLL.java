package pbb_project2;


public class DLL {
	private DLL next;
	private String playername;
	private int score;
	public DLL() {
		score = Integer.MIN_VALUE;
		playername = null;
		next = null;
	}
	public DLL(String playername, int score) {
		this.playername = playername;
		this.score = score;
	}
	public void AddElement(String playername, int score) {
		if (this.playername == null) {
			this.playername = playername;
			this.score = score;
			return;
		}
		if (this.score >= score) {
			if (next == null) {
				next = new DLL(playername, score);
			}
			else
				next.AddElement(playername, score);
		}
		else {
			DLL toadd = new DLL(playername, score);
			toadd.next = this.next;
			next = toadd;
		}
	}
	public String GetStringForm() {
		String result = "";
		if (playername == null)
			return result;
		result = playername + " " + score;
		if (next == null)
			return result;
		return result + "\n" + next.GetStringForm();
	}
}
