package pbb_project2;
import enigma.core.Enigma;
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

    int x = 5, y = 5;

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
        	if(energy>0) {
            	energy-=20;
            	if(energy<0)energy=0;
            }
            cn.getTextWindow().output(x, y, mapData[y][x]);
            int newX = x, newY = y;
            if (rkey == KeyEvent.VK_LEFT && canMove(x - 1, y)) newX--;
            if (rkey == KeyEvent.VK_RIGHT && canMove(x + 1, y)) newX++;
            if (rkey == KeyEvent.VK_UP && canMove(x, y - 1)) newY--;
            if (rkey == KeyEvent.VK_DOWN && canMove(x, y + 1)) newY++;
            x = newX;
            y = newY;
            cn.getTextWindow().output(x, y, symbol);
            
            
            keypr = 0;
        }
    }

    public boolean canMove(int newX, int newY) {
        if (newX < 0 || newX >= mapData[0].length || newY < 0 || newY >= mapData.length) {
            return false;
        }
        if (mapData[newY][newX] == '#') {
            return false;
        }
        return true;
    }

    public void drawAll() {
        for (int yy = 0; yy < mapData.length; yy++) {
            for (int xx = 0; xx < mapData[yy].length; xx++) {
                cn.getTextWindow().output(xx, yy, mapData[yy][xx]);
            }
        }
        cn.getTextWindow().output(x, y, symbol);
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
    	if (mapData[y][x]=='1') {
			energy+=50;
			score+=1;
			mapData[y][x]= ' ';
		}
    	if (mapData[y][x]=='2') {
			energy+=150;
			score+=4;
			mapData[y][x]= ' ';
		}
    	if (mapData[y][x]=='3') {
			energy+=250;
			score+=16;
			mapData[y][x]= ' ';
		}
    	if (mapData[y][x]=='S') {
			energy+=500;
			mapData[y][x]= ' ';
		}
    	if (mapData[y][x]=='@') {
			trap++;
			score+=50;
			mapData[y][x]= ' ';
		}
    }

}

