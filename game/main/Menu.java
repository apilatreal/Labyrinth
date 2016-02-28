package game.main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import game.main.Game.GameState;

public class Menu extends MouseAdapter {

	public static int BUTTON_WIDTH = Game.WIDTH / 4;
	public static int BUTTON_HEIGHT = Game.HEIGHT / 12;

	private Game game;
	private Font hugeFont, normalFont;

	public Menu(Game game) {
		this.game = game;
		hugeFont = new Font("Arial", Font.PLAIN, 72);
		normalFont = new Font("Arial", Font.PLAIN, 32);
	}

	public void renderMenu(Graphics g) {
		g.setColor(Color.WHITE);

		g.setFont(hugeFont);

		g.drawString("MENU", Game.WIDTH / 2 - 105, Game.HEIGHT / 6 - 15);

		g.setFont(normalFont);

		g.drawRect(Game.WIDTH / 2 - BUTTON_WIDTH / 2, Game.HEIGHT / 6 * 2 - BUTTON_HEIGHT / 2, BUTTON_WIDTH,
				BUTTON_HEIGHT);
		g.drawString("Play", Game.WIDTH / 2 - 30, Game.HEIGHT / 6 * 2 + 10);

		g.drawRect(Game.WIDTH / 2 - BUTTON_WIDTH / 2, Game.HEIGHT / 6 * 3 - BUTTON_HEIGHT / 2, BUTTON_WIDTH,
				BUTTON_HEIGHT);
		g.drawString("Instructions", Game.WIDTH / 2 - 85, Game.HEIGHT / 6 * 3 + 10);

		g.drawRect(Game.WIDTH / 2 - BUTTON_WIDTH / 2, Game.HEIGHT / 6 * 4 - BUTTON_HEIGHT / 2, BUTTON_WIDTH,
				BUTTON_HEIGHT);
		g.drawString("Quit", Game.WIDTH / 2 - 40, Game.HEIGHT / 6 * 4 + 10);
	}

	public void renderPause(Graphics g) {
		g.setColor(Color.WHITE);

		g.setFont(hugeFont);

		g.drawString("PAUSE", Game.WIDTH / 2 - 115, Game.HEIGHT / 5 - 15);

		g.setFont(normalFont);

		g.drawRect(Game.WIDTH / 2 - BUTTON_WIDTH / 2, Game.HEIGHT / 5 * 2 - BUTTON_HEIGHT / 2, BUTTON_WIDTH,
				BUTTON_HEIGHT);
		g.drawString("Resume", Game.WIDTH / 2 - 60, Game.HEIGHT / 5 * 2 + 10);

		g.drawRect(Game.WIDTH / 2 - BUTTON_WIDTH / 2, Game.HEIGHT / 5 * 3 - BUTTON_HEIGHT / 2, BUTTON_WIDTH,
				BUTTON_HEIGHT);
		g.drawString("Quit", Game.WIDTH / 2 - 35, Game.HEIGHT / 5 * 3 + 10);
	}

	public void renderInstructions(Graphics g) {
		g.setColor(Color.WHITE);

		g.setFont(hugeFont);

		g.drawString("INSTRUCTIONS", Game.WIDTH / 2 - 220, Game.HEIGHT / 6);

		g.setFont(normalFont);

		g.drawString("You have to get to the blue point in order to win a level.", 20, Game.WIDTH / 6 * 2);
		g.drawString("You need to finish " + Game.MAX_LEVELS + " levels to win the game.", 20, Game.WIDTH / 6 * 2 + 40);
		g.drawString("For each level you have " + Game.TIME + " seconds.", 20, Game.HEIGHT / 6 * 2 + 80);
		g.drawString("You cannot move through walls", 20, Game.HEIGHT / 6 * 2 + 120);
		g.drawString("Move you character using WASD or arrow keys.", 20, Game.HEIGHT / 6 * 2 + 200);

		g.drawRect(Game.WIDTH / 2 - BUTTON_WIDTH / 2, Game.HEIGHT / 6 * 5 - BUTTON_HEIGHT / 2, BUTTON_WIDTH,
				BUTTON_HEIGHT);
		g.drawString("Back", Game.WIDTH / 2 - 40, Game.HEIGHT / 6 * 5 + 10);
	}

	public void renderEnd(Graphics g) {
		g.setColor(Color.WHITE);

		g.setFont(hugeFont);

		if (game.isWon()) {
			g.drawString("YOU WIN!", Game.WIDTH / 2 - 170, Game.HEIGHT / 5);
		} else {
			g.drawString("YOU LOSE!", Game.WIDTH / 2 - 180, Game.HEIGHT / 5);
		}

		g.setFont(normalFont);

		g.drawRect(Game.WIDTH / 2 - BUTTON_WIDTH / 2, Game.HEIGHT / 5 * 2 - BUTTON_HEIGHT / 2, BUTTON_WIDTH,
				BUTTON_HEIGHT);
		g.drawString("Try again", Game.WIDTH / 2 - 65, Game.HEIGHT / 5 * 2 + 10);

		g.drawRect(Game.WIDTH / 2 - BUTTON_WIDTH / 2, Game.HEIGHT / 5 * 3 - BUTTON_HEIGHT / 2, BUTTON_WIDTH,
				BUTTON_HEIGHT);
		g.drawString("Quit", Game.WIDTH / 2 - 35, Game.HEIGHT / 5 * 3 + 10);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		switch (game.getState()) {
		case MENU:
			if (inside(e, Game.WIDTH / 2 - BUTTON_WIDTH / 2, Game.HEIGHT / 6 * 2 - BUTTON_HEIGHT / 2, BUTTON_WIDTH,
					BUTTON_HEIGHT)) {
				game.setState(GameState.GAME);
			} else if (inside(e, Game.WIDTH / 2 - BUTTON_WIDTH / 2, Game.HEIGHT / 6 * 3 - BUTTON_HEIGHT / 2,
					BUTTON_WIDTH, BUTTON_HEIGHT)) {
				game.setState(GameState.INSTRUCTIONS);
			} else if (inside(e, Game.WIDTH / 2 - BUTTON_WIDTH / 2, Game.HEIGHT / 6 * 4 - BUTTON_HEIGHT / 2,
					BUTTON_WIDTH, BUTTON_HEIGHT)) {
				System.exit(0);
			}
			break;
		case PAUSE:
			if (inside(e, Game.WIDTH / 2 - BUTTON_WIDTH / 2, Game.HEIGHT / 5 * 2, BUTTON_WIDTH, BUTTON_HEIGHT)) {
				game.setState(GameState.GAME);
			} else if (inside(e, Game.WIDTH / 2 - BUTTON_WIDTH / 2, Game.HEIGHT / 5 * 3, BUTTON_WIDTH, BUTTON_HEIGHT)) {
				System.exit(0);
			}
			break;
		case INSTRUCTIONS:
			if (inside(e, Game.WIDTH / 2 - BUTTON_WIDTH / 2, Game.HEIGHT / 6 * 5, BUTTON_WIDTH, BUTTON_HEIGHT)) {
				game.setState(GameState.MENU);
			}
			break;
		case END:
			if (inside(e, Game.WIDTH / 2 - BUTTON_WIDTH / 2, Game.HEIGHT / 5 * 2, BUTTON_WIDTH, BUTTON_HEIGHT)) {
				game.setState(GameState.GAME);
				game.hardReset();
			} else if (inside(e, Game.WIDTH / 2 - BUTTON_WIDTH / 2, Game.HEIGHT / 5 * 3 - BUTTON_HEIGHT / 2,
					BUTTON_WIDTH, BUTTON_HEIGHT)) {
				System.exit(0);
			}
			break;
		default:
			;
		}
	}

	public static boolean inside(MouseEvent e, int x, int y, int width, int height) {
		return e.getX() > x && e.getX() < x + width && e.getY() > y && e.getY() < y + height;
	}

}
