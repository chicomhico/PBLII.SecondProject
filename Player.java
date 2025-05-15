package pbb_project2;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Player {
	public NumberSnake game;
    private KeyListener klis;
    private int energy=1000;
    private int life=500;
    private int trap=0;
    private int human_score = 0;
    
    char symbol = 'P';

    int keypr = 0;
    int rkey;

    int x = 5, y = 5;
    
    long timer = 0;

    public Player(NumberSnake game) throws Exception {
    	this.game = game;
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
        game.cn.getTextWindow().addKeyListener(klis);
        game.cn.getTextWindow().output(x, y, symbol);
    }
    public boolean TimeElapse(long elapsedtime) {
    	timer += elapsedtime;
		if (energy > 0) {
			timer += elapsedtime;
			energy -= elapsedtime;
			if (energy < 0)
				energy = 0;
		}
		Interface();
		if (timer > 400) {
			timer -= 100;
			return update();
		}
		return false;
    }

    public boolean update() {
    	boolean result = false;
        if (keypr == 1) {
        	if(energy>0) {
            	energy-=20;
            	if(energy<0)energy=0;
            }
        	game.cn.getTextWindow().output(x, y, game.board.map[y][x]);
            int newX = x, newY = y;
            if (rkey == KeyEvent.VK_LEFT && canMove(x - 1, y)) {
            	newX--;
            	result = true;
            }
            if (rkey == KeyEvent.VK_RIGHT && canMove(x + 1, y)) {
            	newX++;
            	result = true;
            }
            if (rkey == KeyEvent.VK_UP && canMove(x, y - 1)) {
            	newY--;
            	result = true;
            }
            if (rkey == KeyEvent.VK_DOWN && canMove(x, y + 1)) {
            	newY++;
            	result = true;
            }
            game.cn.getTextWindow().output(x, y, symbol);
            x = newX;
            y = newY;
            keypr = 0;
            updatePlayer();
        }
        return result;
    }

    public boolean canMove(int newX, int newY) {
        if (newX < 0 || newX >= game.board.map[0].length || newY < 0 || newY >= game.board.map.length) {
            return false;
        }
        if (game.board.map[newY][newX] == '#') {
            return false;
        }
        return true;
    }
    public void Interface() {
    	game.cn.getTextWindow().setCursorPosition(61, 10);
    	System.out.print("---Player---");
    	game.cn.getTextWindow().setCursorPosition(62, 11);
    	System.out.print("Energy : " + energy );
    	game.cn.getTextWindow().setCursorPosition(62, 12);
    	System.out.print("Life   : " + life );
    	game.cn.getTextWindow().setCursorPosition(62, 13);
    	System.out.print("Trap   : " + trap);
    	game.cn.getTextWindow().setCursorPosition(62, 14);
    	System.out.print("Score  : "+ human_score);
    	
    	game.cn.getTextWindow().setCursorPosition(61, 17);
    	System.out.print("---Computer---");
    	game.cn.getTextWindow().setCursorPosition(62, 18);
    	System.out.print("S Robot : " + game.board.srob );
    	game.cn.getTextWindow().setCursorPosition(62, 19);
    	System.out.print("Score   : " + game.computer.computer_score);
    	game.cn.getTextWindow().output(0, 0, '#');//önemli engigma hatasını çözüyor
    }
    private void updatePlayer() {
    	if (game.board.map[y][x]=='1') {
			energy+=50;
			human_score+=1;
			game.board.map[y][x]= ' ';
		}
    	if (game.board.map[y][x]=='2') {
			energy+=150;
			human_score+=4;
			game.board.map[y][x]= ' ';
		}
    	if (game.board.map[y][x]=='3') {
			energy+=250;
			human_score+=16;
			game.board.map[y][x]= ' ';
		}
    	if (game.board.map[y][x]=='S') {
			energy+=500;
			game.board.map[y][x]= ' ';
		}
    	if (game.board.map[y][x]=='@') {
			trap++;
			human_score+=50;
			game.board.map[y][x]= ' ';
		}
    }

}
