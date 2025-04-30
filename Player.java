package pbb_project2;
import enigma.core.Enigma;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Player { // <- Sınıf adı dosya adıyla aynı

	public enigma.console.Console cn = Enigma.getConsole("Simge Hareketi");
    public KeyListener klis;

    int x = 10, y = 10;
    char symbol = 'P';
    char[][] mapData;

    int keypr = 0;
    int rkey;

    Player() throws Exception {
        Board board = new Board();
        mapData = board.loadMap("map.txt");

        printMap();

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

        while (true) {
            if (keypr == 1) {
                cn.getTextWindow().output(x, y, mapData[y][x]);

                if (rkey == KeyEvent.VK_LEFT && canMove(x - 1, y)) x--;
                if (rkey == KeyEvent.VK_RIGHT && canMove(x + 1, y)) x++;
                if (rkey == KeyEvent.VK_UP && canMove(x, y - 1)) y--;
                if (rkey == KeyEvent.VK_DOWN && canMove(x, y + 1)) y++;

                cn.getTextWindow().output(x, y, symbol);

                keypr = 0;
            }
            Thread.sleep(20);
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

    public void printMap() {
        for (int y = 0; y < mapData.length; y++) {
            for (int x = 0; x < mapData[y].length; x++) {
                cn.getTextWindow().output(x, y, mapData[y][x]);
            }
        }
    }

}

