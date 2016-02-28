
package game.event;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import game.main.Game;
import game.main.Game.GameState;

public class BasicControl extends KeyAdapter{

	private Game game;
	
	public BasicControl(Game game){
		this.game = game;
	}
	
	@Override
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		
		switch(key){
		case KeyEvent.VK_P:
			if(game.getState() == GameState.GAME){
				game.setState(GameState.PAUSE);
			} else if(game.getState() == GameState.PAUSE){
				game.setState(GameState.GAME);
			}
			break;
		case KeyEvent.VK_ESCAPE:
			System.exit(0);
		}
	}
	
}
