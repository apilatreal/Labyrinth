package game.object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;

import game.main.Game;
import game.main.Handler;

public class Player extends GameObject{
	
	public static boolean GOES_THROUGH_WALLS;
	
	public static int SIZE;
	public static int SPEED;
	public static int VIEW_DISTANCE;
	
	private Handler handler;
	private Game game;

	public Player(double x, double y, Handler handler, Game game){
		super(ID.PLAYER);
		this.x = x;
		this.y = y;
		this.handler = handler;
		this.game = game;
		dx = 0;
		dy = 0;
	}
	
	@Override
	public void tick() {
		x += dx;
		y += dy;
		
		
		checkCollision();
	}
	
	private void checkCollision(){
		List<GameObject> obj = handler.getObjects();
		for(int i = 0; i < obj.size(); i++){
			GameObject temp = obj.get(i);
			
			if(temp.getBounds().intersects(getBounds())){
				if(temp.getID() == ID.WALL){
					if(!GOES_THROUGH_WALLS){
						x -= dx;
						y -= dy;
					}
				}
				if(temp.getID() == ID.END_POINT)
					game.nextLevel();
			}
		}
	}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.GRAY);
		g.fillOval((int)x - VIEW_DISTANCE / 2 + SIZE / 2, (int)y - VIEW_DISTANCE / 2 + SIZE / 2, VIEW_DISTANCE, VIEW_DISTANCE);
		g.setColor(Color.RED);
		g.fillRect((int)x, (int)y, SIZE, SIZE);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, SIZE, SIZE);
	}

}
