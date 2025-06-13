package pbb_project2;

import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;


public class HighScoreTable {
	private NumberSnake game;
	public HighScoreTable(int score, NumberSnake game) throws Exception{
		this.game = game;
		HighScoreTablee(score);
	}
	private void HighScoreTablee(int score) throws Exception{
		game.cn.getTextWindow().setCursorPosition(0, 0);
		System.out.print("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
				+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n");
		game.cn.getTextWindow().setCursorPosition(0, 0);
		System.out.println("Write Your Name");
		Scanner scanner = new Scanner(System.in);
		String playername = scanner.nextLine();
		scanner.close();
		File file = new File("src/pbb_project2/HighScoreTable.txt");
		scanner = new Scanner(file);
		DLL players = new DLL();
		String line = scanner.nextLine();
		System.out.print(line);
		while (scanner.hasNextLine()) {
            line = scanner.nextLine();
            String name = "";
            int namescore = 0;
            String toadd = "";
            boolean first = true;
            for(int i = 0; i < line.length();i++) {
            	char tocheck = line.charAt(i);
            	if (tocheck == ' ') {
            		if (first)
            			name += toadd;
            		else
            			name += " " + toadd;
            		toadd = "";
            	}
            	else {
            		toadd += tocheck;
            	}
            }
            namescore = Integer.parseInt(toadd);
            players.AddElement(name, namescore);
        }
		players.AddElement(playername, score);
		scanner.close();
		FileWriter writer = new FileWriter("src/pbb_project2/HighScoreTable.txt", false);
		writer.write("High Score Table\n");
		writer.write(players.GetStringForm());
		writer.close();
		game.cn.getTextWindow().setCursorPosition(0, 0);
		System.out.print("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
				+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n"
		+"\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\n");
		game.cn.getTextWindow().setCursorPosition(0, 0);
		System.out.println("New High Score Table");
		System.out.print(players.GetStringForm() + "     ");
	}
}
