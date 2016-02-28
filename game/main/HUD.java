package game.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import game.main.Game.GameState;

public class HUD {
	
	private Game game;
	private float time;
	
	public HUD(float time, Game game){
		this.time = time;
		this.game = game;
	}
	
	public void addTime(float time){
		this.time += time;
	}
	
	public void setTime(float time){
		this.time = time;
	}
	
	public void tick(){
		if(time > 0)
			time -= 1 / Game.TARGET_FPS;
		else
			game.setState(GameState.END);
	}

	public void render(Graphics g){
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.PLAIN, 48));
		g.drawString("Time: " + time, 30, 70);
	}
	
}