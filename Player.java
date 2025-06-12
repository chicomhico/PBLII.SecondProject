package pbb_project2;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Player {
	public NumberSnake game;
    private KeyListener klis;
    private int energy=10000;
    private int life=50000;
    private int trap=0;
    private int score = 0;
    private static  Trap[] traps = new Trap[50];
    
    public boolean isdead = false;
    
    char symbol = 'P';

    int keypr = 0;
    int rkey;

    Coordinate position;
    
    long timer = 0;

    public Player(NumberSnake game) throws Exception {
    	position = new Coordinate(5, 5);
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
    }
    public boolean TimeElapse(long elapsedtime) {
    	neighborDamage(elapsedtime);
    	timer += elapsedtime;
		if (energy > 0) {
			timer += elapsedtime;
			//energy -= elapsedtime;
			if (energy < 0)
				energy = 0;
		}
		Interface();
		if (timer > 200) {
			timer -= 200;
			return update();
		}
		return false;
    }

    public boolean update() {
    	boolean result = false;
        if (keypr == 1) {
        	if(energy>0) {
            	energy-=200;
            	if(energy<0)energy=0;
            }
            int newX = position.x, newY = position.y;
            if (rkey == KeyEvent.VK_LEFT && canMove(position.x - 1, position.y)) {
            	newX--;
            	result = true;
            }
            if (rkey == KeyEvent.VK_RIGHT && canMove(position.x + 1, position.y)) {
            	newX++;
            	result = true;
            }
            if (rkey == KeyEvent.VK_UP && canMove(position.x, position.y - 1)) {
            	newY--;
            	result = true;
            }
            if (rkey == KeyEvent.VK_DOWN && canMove(position.x, position.y + 1)) {
            	newY++;
            	result = true;
            }
            position.x = newX;
            position.y = newY;
            keypr = 0;
            updatePlayer();
        }
        return result;
    }

    public boolean canMove(int newX, int newY) {
        /*if (newX < 0 || newX >= game.board.map[0].length || newY < 0 || newY >= game.board.map.length) {
            return false;
        }
        if (game.board.GetCoor(new Coordinate(newX, newY)) == '#') {
            return false;
        }
        return true;*/
    	return game.isAvailableToMove(new Coordinate(newX, newY));
    }
    public void Interface() {
    	game.cn.getTextWindow().setCursorPosition(61, 10);
    	System.out.print("---Player---");
    	game.cn.getTextWindow().setCursorPosition(62, 11);
    	System.out.print("Energy : " + energy/10 + "   " );
    	game.cn.getTextWindow().setCursorPosition(62, 12);
    	System.out.print("Life   : " + life/100 + "   " );
    	game.cn.getTextWindow().setCursorPosition(62, 13);
    	System.out.print("Trap   : " + trap);
    	game.cn.getTextWindow().setCursorPosition(62, 14);
    	System.out.print("Score  : "+ score);
    	
    	game.cn.getTextWindow().setCursorPosition(61, 17);
    	System.out.print("---Computer---");
    	game.cn.getTextWindow().setCursorPosition(62, 18);
    	System.out.print("S Robot : " + game.board.srob );
    	game.cn.getTextWindow().setCursorPosition(62, 19);
    	System.out.print("Score   : " + game.computer.computer_score );
    	game.cn.getTextWindow().output(0, 0, '#');//önemli engigma hatasını çözüyor
    }
    private void updatePlayer() {
    	if (game.board.GetCoor(position) =='1') {
			energy+=500;
			score+=1;
			game.board.SetCoor(position, ' ');
		}
    	if (game.board.GetCoor(position) =='2') {
			energy+=1500;
			score+=4;
			game.board.SetCoor(position, ' ');
		}
    	if (game.board.GetCoor(position) =='3') {
			energy+=2500;
			score+=16;
			game.board.SetCoor(position, ' ');
		}
    	if (game.board.GetCoor(position) =='S') {
			energy+=5000;
			game.board.SetCoor(position, ' ');
		}
    	if (game.board.GetCoor(position) =='@') {
    		traps[trap] = new Trap();
			trap++;
			score+=50;
			game.board.SetCoor(position, ' ');
		}
    }

    boolean placed = false;
    boolean pressedSpace = false;
    long[] trapTimes = new long[50];
    Coordinate[] trapPositions = new Coordinate[1000]; 

    public void PlaceTrap() {
        if (rkey == KeyEvent.VK_SPACE) {
            if (!pressedSpace && !placed && trap != 0) {
                placed = true;
                int index = trap - 1;
                traps[index].Place(position.y, position.x);
                game.board.SetCoor(position, '=');

                trapPositions[index] = position.Copy();
                trapTimes[index] = System.currentTimeMillis();

                trap--;
            }
            pressedSpace = true;
        } else {
            pressedSpace = false;
            placed = false;
        }
    }
    public void updateTraps() {
        long now = System.currentTimeMillis();

        for (int i = 0; i < traps.length; i++) {
            if (trapTimes[i] != 0 && now - trapTimes[i] >= 10000) {
                Coordinate trapCoor = trapPositions[i];
                if (trapCoor != null) {
                    int cx = trapCoor.x;
                    int cy = trapCoor.y;
                    
                    for (int dy = -1; dy <= 1; dy++) {
                        for (int dx = -1; dx <= 1; dx++) {
                            int nx = cx + dx;
                            int ny = cy + dy;
                            Coordinate tocheck = new Coordinate(nx, ny);
                            
                            
                            if (ny >= 0 && ny < game.board.maplengtfirst &&
                                nx >= 0 && nx < game.board.maplengtsecond && 
                                game.board.GetCoor(tocheck) != 'C' && game.board.GetCoor(tocheck) != 'P' &&
			       game.board.GetCoor(tocheck) != '#') {
                                game.board.SetCoor(tocheck, ' '); 
                            }
                        }
                    }

                    trapTimes[i] = 0;
                    trapPositions[i] = null;
                }
            }
        }
    }
    public int getLife() {
        return life;
    }
    public void setLife(int life) {
        this.life = life;    }

    public void takeDamage(int amount) {
        life -= amount;
        if (life < 0) life = 0;
    }
    private void neighborDamage(long elapsedtime) {
		Player player = this;
		Snake[] snakes = game.snakecontroller.snakes;
		Computer computer = game.computer;
	    int x = player.position.x;
	    int y = player.position.y;
	    int damage = 0;

	    int[][] directions = { {0, 1}, {1, 0}, {0, -1}, {-1, 0} };

	    for (int i = 0; i < directions.length; i++) {
	        int nx = x + directions[i][0];
	        int ny = y + directions[i][1];
	        Coordinate neighborCoord = new Coordinate(nx, ny);
	        if (computer.current.IsSame(neighborCoord)) {
	        	damage += 30 * elapsedtime;
	        }
	        else {
	        	for (Snake snake : snakes) {
	        		if (snake != null) {
	        	
	        			if (neighborCoord.IsSame(snake.current)) {
	            			damage += 1;
	            			break;
	            		}

	            		SLL body = snake.body;
	            		SLLNode node = body.headnode;
	            		while (node != null) {
	                		Coordinate bodyPart = node.location;
	                		if (neighborCoord.IsSame(bodyPart)) {
	                    		damage += 1 * elapsedtime;
	                    		break;
	                		}
	                		node = node.GetNext();
	            		}
	        		}
	        	}
	        }
	    }
	    life -= damage;

	    isdead = life <= 0;
	}
}

