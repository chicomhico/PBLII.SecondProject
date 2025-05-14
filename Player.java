package pbb_project2;
import enigma.core.Enigma;
import enigma.console.TextAttributes;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Player {
	public enigma.console.Console cn = Enigma.getConsole();
    public KeyListener klis;
    public static int energy=1000;
    public static int life=500;
    public static int trap=0;
    public static int score = 0;
    
    char symbol = 'P';
    public static char[][] mapData;

    int keypr = 0;
    int rkey;

    int x = 10, y = 10;

    public Player(Board board) throws Exception {
        mapData = board.loadMap("map.txt");
        board.printMap();
        klis = new KeyListener() {
            public void keyTyped(KeyEvent e) {}
            public void keyPressed(KeyEvent e) {
                if (keypr == 0) {
                    keypr = 1;
                    rkey = e.getKeyCode();
                }
            }
            public void keyReleased(KeyEvent e) {}
        };
        cn.getTextWindow().addKeyListener(klis);
        cn.getTextWindow().output(x, y, symbol);
    }

    public void update() {
        if (keypr == 1) {
            int newX = x, newY = y;
            if (rkey == KeyEvent.VK_LEFT && canMove(x - 1, y)) newX--;
            if (rkey == KeyEvent.VK_RIGHT && canMove(x + 1, y)) newX++;
            if (rkey == KeyEvent.VK_UP && canMove(x, y - 1)) newY--;
            if (rkey == KeyEvent.VK_DOWN && canMove(x, y + 1)) newY++;

            if ((newX != x || newY != y) && energy > 0) {
                energy -= 20;
                if (energy < 0) energy = 0;
                char c = mapData[x][y];
                Color fg = Color.WHITE;
                Color bg = Color.BLACK;
                if (c == 'P' || c == '@') fg = Color.BLUE;
                if (c == 'C' || c == 'S') { fg = Color.WHITE; bg = Color.RED; }
                cn.getTextWindow().output(x, y, c, new TextAttributes(fg, bg));
                x = newX;
                y = newY;
                cn.getTextWindow().output(x, y, symbol, new TextAttributes(Color.BLUE, Color.BLACK));
            }
            keypr = 0;
        }
    }

    public boolean canMove(int newX, int newY) {
        if (newX < 0 || newX >= mapData.length || newY < 0 || newY >= mapData[0].length) {
            return false;
        }
        if (mapData[newX][newY] == '#') {
            return false;
        }
        return true;
    }

    public void drawAll() {
        for (int yy = 0; yy < mapData[0].length; yy++) {
            for (int xx = 0; xx < mapData.length; xx++) {
                char c = mapData[xx][yy];
                Color fg = Color.WHITE;
                Color bg = Color.BLACK;
                if (c == '@') fg = Color.BLUE;
                if (c == 'C' || c == 'S') { fg = Color.WHITE; bg = Color.RED; }
                if (c == '#') {fg=Color.BLACK;bg=Color.WHITE;}
                cn.getTextWindow().output(xx, yy, c, new TextAttributes(fg, bg));
            }
        }
        cn.getTextWindow().output(x, y, symbol, new TextAttributes(Color.BLUE, Color.BLACK));
    }

    public void Interface() {
    	updatePlayer();
    	cn.getTextWindow().setCursorPosition(61, 10);
    	System.out.print("---Player---");
    	cn.getTextWindow().setCursorPosition(62, 11);
    	System.out.print("Energy : " + energy );
    	cn.getTextWindow().setCursorPosition(62, 12);
    	System.out.print("Life   : " + life );
    	cn.getTextWindow().setCursorPosition(62, 13);
    	System.out.print("Trap   : " + trap);
    	cn.getTextWindow().setCursorPosition(62, 14);
    	System.out.print("Score  : "+ score);
    	
    	cn.getTextWindow().setCursorPosition(61, 17);
    	System.out.print("---Computer---");
    	cn.getTextWindow().setCursorPosition(62, 18);
    	System.out.print("S Robot : " + Board.srob );
    	cn.getTextWindow().setCursorPosition(62, 19);
    	System.out.print("Score   : " + life );
    }

    public void updatePlayer() {
    	if (mapData[x][y]=='1') {
			energy+=50;
			score+=1;
			mapData[x][y]= ' ';
		}
    	if (mapData[x][y]=='2') {
			energy+=150;
			score+=4;
			mapData[x][y]= ' ';
		}
    	if (mapData[x][y]=='3') {
			energy+=250;
			score+=16;
			mapData[x][y]= ' ';
		}
    	if (mapData[x][y]=='S') {
			energy+=500;
			mapData[x][y]= ' ';
		}
    	if (mapData[x][y]=='@') {
			trap++;
			score+=50;
			mapData[x][y]= ' ';
		}
    }
}

