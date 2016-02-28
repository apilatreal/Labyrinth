package game.event;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import game.main.Game;
import game.object.Player;

public class PlayerMove extends KeyAdapter{
	
	private Game game;
	private Player player;
	
	public PlayerMove(Game game){
		this.game = game;
		this.player = game.getPlayer();
	}
	
	@Override
	public void keyPressed(KeyEvent e){
		int key = e.getKeyCode();
		
		this.player = game.getPlayer();
		
		switch(key){
		case KeyEvent.VK_W:
		case KeyEvent.VK_UP:
			player.setDY(-Player.SPEED);
			break;
		case KeyEvent.VK_A:
		case KeyEvent.VK_LEFT:
			player.setDX(-Player.SPEED);
			break;
		case KeyEvent.VK_S:
		case KeyEvent.VK_DOWN:
			player.setDY(Player.SPEED);
			break;
		case KeyEvent.VK_D:
		case KeyEvent.VK_RIGHT:
			player.setDX(Player.SPEED);
			break;
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e){
		int key = e.getKeyCode();
		
		switch(key){
		case KeyEvent.VK_W:
		case KeyEvent.VK_UP:
			player.setDY(0);
			break;
		case KeyEvent.VK_A:
		case KeyEvent.VK_LEFT:
			player.setDX(0);
			break;
		case KeyEvent.VK_S:
		case KeyEvent.VK_DOWN:
			player.setDY(0);
			break;
		case KeyEvent.VK_D:
		case KeyEvent.VK_RIGHT:
			player.setDX(0);
			break;
		}
	}
	
}
