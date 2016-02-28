package game.object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import game.main.Game;

public class Wall extends GameObject{
	
	public static boolean SEEN_FROM_ANY_DISTANCE;
	
	private int width, height;
	private Player player;
	
	private String text;

	public Wall(int x, int y, int width, int height, Player player){
		super(ID.WALL);
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.player = player;
		this.text = "";
	}
	
	public Wall(int x, int y, int width, int height, Player player, String text){
		this(x, y, width, height, player);
		this.text = text;
	}

	@Override
	public void tick() {}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.RED);
		if(!SEEN_FROM_ANY_DISTANCE){
			for(int i = (int)x; i < x+width; i++){
				for(int j = (int)y; j < y+height; j++){
					if(Math.sqrt(Math.pow(Math.abs(i-player.getX()-Player.SIZE/2), 2)+Math.pow(Math.abs(j-player.getY()-Player.SIZE/2), 2)) < Player.VIEW_DISTANCE/2){
						g.fillRect(i, j, 1, 1);
					}
				}
			}
		} else {
			g.fillRect((int)x, (int)y, width, height);
			g.drawString(text, (int)x+Game.WIDTH/16, (int)y+Game.HEIGHT/16);
		}
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, width, height);
	}
	
}
