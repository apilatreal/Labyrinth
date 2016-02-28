package game.object;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import game.main.Game;

public class EndPoint extends GameObject{
	
	public static int SIZE;
	
	public EndPoint(){
		super(ID.END_POINT);
		x = Game.WIDTH-40-SIZE;
		y = Game.HEIGHT-70-SIZE;
	}

	@Override
	public void tick() {}

	@Override
	public void render(Graphics g) {
		g.setColor(Color.CYAN);
		g.fillRect((int)x, (int)y, SIZE, SIZE);
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle((int)x, (int)y, SIZE, SIZE);
	}
	
}
