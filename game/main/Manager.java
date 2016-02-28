package game.main;

import java.awt.Graphics;

public class Manager {

	private Game game;
	
	public Manager(Game game){
		this.game = game;
	}
	
	public void tick(){
		switch(game.getState()){
		case GAME:
			game.getHandler().tick();
			game.getHUD().tick();
			break;
		case MENU:
		case PAUSE:
		case END:
		case INSTRUCTIONS:
			;
		}
	}
	
	public void render(Graphics g){
		switch(game.getState()){
		case GAME:
			game.getHandler().render(g);
			game.getHUD().render(g);
			break;
		case MENU:
			game.getMenu().renderMenu(g);
			break;
		case PAUSE:
			game.getMenu().renderPause(g);
			break;
		case END:
			game.getMenu().renderEnd(g);
			break;
		case INSTRUCTIONS:
			game.getMenu().renderInstructions(g);
			break;
		}
	}
}
