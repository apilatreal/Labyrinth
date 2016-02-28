package game.main;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

import game.event.BasicControl;
import game.event.PlayerMove;
import game.object.EndPoint;
import game.object.Player;
import game.object.Wall;

public class Game extends Canvas implements Runnable {

	private static final long serialVersionUID = 1;

	public static int WIDTH;
	public static int HEIGHT;
	public static float TARGET_FPS;
	public static float TIME;

	public static final String[] LEVELS = {
			"12302133\n02223102\n23300002\n11230000\n32100321\n32122321\n32112300\n000012200",
			"02100000\n00122300\n31222000\n32121000\n03312100\n01222000\n00222300\n012310032",
			"12000120\n00122230\n00000331\n00123010\n01333201\n32200010\n00123321\n111100000",
			"11000230\n12003301\n00132013\n00000000\n12030002\n30001223\n11123200\n112220003",
			"00200130\n22013100\n21000320\n33001200\n20003120\n32100012\n22100001\n001223000" };
	public static final int MAX_LEVELS = LEVELS.length;

	static {
		Game.WIDTH = 800;
		Game.HEIGHT = 800;
		Game.TARGET_FPS = 60;
		Game.TIME = 15;
		Player.SIZE = 32;
		Player.SPEED = 5;
		Player.VIEW_DISTANCE = 300;
		EndPoint.SIZE = 32;

		Wall.SEEN_FROM_ANY_DISTANCE = false;
		Player.GOES_THROUGH_WALLS = false;

		try {
			for (String s : Files.readAllLines(Paths.get("config.ini"))) {
				if (s.matches("\\s*Height:\\s*\\d+")) {
					Game.HEIGHT = Integer.parseInt(s.replace("Height:", "").replaceAll("\\s", ""));
					System.out.print(Game.HEIGHT);
				}
				if (s.matches("\\s*Width:\\s*\\d+")) {
					Game.WIDTH = Integer.parseInt(s.replace("Width:", "").replaceAll("\\s", ""));
				}
				if (s.matches("\\s*FPS:\\s*[0-9\\.]+")) {
					Game.TARGET_FPS = Float.parseFloat(s.replace("FPS:", "").replaceAll("\\s", ""));
				}
				if (s.matches("\\s*Time-To-Solve:\\s*[0-9\\.]+")) {
					Game.TIME = Float.parseFloat(s.replace("Time-To-Solve:", "").replaceAll("\\s", ""));
				}
				if (s.matches("\\s*Player-Size:\\s*\\d+")) {
					Player.SIZE = Integer.parseInt(s.replace("Player-Size:", "").replaceAll("\\s", ""));
				}
				if (s.matches("\\s*Player-Speed:\\s*\\d+")) {
					Player.SPEED = Integer.parseInt(s.replace("Player-Speed:", "").replaceAll("\\s", ""));
				}
				if (s.matches("\\s*Player-View-Distacnce:\\s*\\d+")) {
					Player.VIEW_DISTANCE = Integer
							.parseInt(s.replace("Player-View-Distance:", "").replaceAll("\\s", ""));
				}
				if (s.matches("\\s*End-Size:\\s*\\d+")) {
					EndPoint.SIZE = Integer.parseInt(s.replace("End-Size:", "").replaceAll("\\s", ""));
				}

				if (s.matches("\\s*Hacks:\\s*(true)|(false)")) {
					Wall.SEEN_FROM_ANY_DISTANCE = Boolean.parseBoolean(s.replace("Hacks:", "").replaceAll("\\s", ""));
				}
				if (s.matches("\\s*More-Hacks:\\s*(true)|(false)")) {
					Player.GOES_THROUGH_WALLS = Boolean
							.parseBoolean(s.replace("More-Hacks:", "").replaceAll("\\s", ""));
				}
			}
		} catch (Exception e) {
		} finally {
			Player.SPEED *= 60 / TARGET_FPS;
			Player.SPEED *= WIDTH / 800;
			Player.SPEED *= HEIGHT / 800;

			Player.VIEW_DISTANCE *= WIDTH / 800;
			Player.VIEW_DISTANCE *= HEIGHT / 800;
		}
	}

	public enum GameState {
		MENU, INSTRUCTIONS, GAME, END, PAUSE;
	}

	private Thread thread;
	private Handler handler;
	private Manager manager;
	private GameState state;
	private Player player;
	private HUD hud;
	private Menu menu;
	private int level;
	private int passed;
	private boolean won = false;
	private boolean running = false;

	public Game() {
		handler = new Handler();
		hud = new HUD(0, this);
		menu = new Menu(this);
		manager = new Manager(this);
		new Window(WIDTH, HEIGHT, "Labyrinth", this);
		addKeyListener(new BasicControl(this));
		addKeyListener(new PlayerMove(this));
		addMouseListener(menu);
	}

	public void start() {
		thread = new Thread(this);
		running = true;

		state = GameState.MENU;
		hardReset();

		thread.start();
	}

	@Override
	public void run() {
		requestFocus();
		double ns = 1000000000 / TARGET_FPS;
		long lastTime = System.nanoTime();
		while (running) {
			if (System.nanoTime() - lastTime > ns) {
				lastTime = System.nanoTime();
				tick();
				render();
			}
		}
	}

	public void stop() {
		try {
			thread.join();
			running = false;
		} catch (Exception ignore) {
		}
	}

	private void tick() {
		manager.tick();
	}

	private void render() {
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(2);
			return;
		}

		Graphics g = bs.getDrawGraphics();

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, WIDTH, HEIGHT);

		manager.render(g);

		g.dispose();
		bs.show();
	}

	public void nextLevel() {
		passed++;
		if (passed >= MAX_LEVELS) {
			setState(GameState.END);
			won = true;
			return;
		}
		hud.addTime(TIME);
		softReset();
		loadLevel();
	}

	private void loadLevel() {
		int rand;
		do {
			rand = new Random().nextInt(LEVELS.length);
		} while (rand == level);
		level = rand;

		char[][] walls = new char[8][8];
		for (int i = 0; i < 8; i++) {
			walls[i] = LEVELS[level].split("\n")[i].toCharArray();
		}

		// walls[row][column]

		int widthDiff = 15;
		int heightDiff = 45;

		WIDTH -= widthDiff;
		HEIGHT -= heightDiff;

		handler.add(new Wall(0, 0, WIDTH + 10, 10, player));
		handler.add(new Wall(0, 0, 10, HEIGHT + 10, player));
		handler.add(new Wall(WIDTH, 0, 10, HEIGHT + 10, player));
		handler.add(new Wall(0, HEIGHT, WIDTH + 10, 10, player));

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				char temp = walls[j][i];

				if (temp == '1') {
					handler.add(new Wall((i + 1) * WIDTH / 8, j * HEIGHT / 8, 10, HEIGHT / 8 + 10, player));
				} else if (temp == '2') {
					handler.add(new Wall(i * WIDTH / 8, (j + 1) * HEIGHT / 8, WIDTH / 8 + 10, 10, player));
				} else if (temp == '3') {
					handler.add(new Wall((i + 1) * WIDTH / 8, j * HEIGHT / 8, 10, HEIGHT / 8 + 10, player));
					handler.add(new Wall(i * WIDTH / 8, (j + 1) * HEIGHT / 8, WIDTH / 8 + 10, 10, player));
				}
			}
		}

		WIDTH += widthDiff;
		HEIGHT += heightDiff;
	}

	public void softReset() {
		handler.reset();
		handler.add(player = new Player(WIDTH / 16 - Player.SIZE / 2, HEIGHT / 16 - Player.SIZE / 2, handler, this));
		handler.add(new EndPoint());
	}

	public void hardReset() {
		softReset();
		hud.setTime(0);
		passed = -1;
		won = false;
		nextLevel();
	}

	public void setState(GameState gs) {
		state = gs;
	}

	public GameState getState() {
		return state;
	}

	public Handler getHandler() {
		return handler;
	}

	public HUD getHUD() {
		return hud;
	}

	public Menu getMenu() {
		return menu;
	}

	public Player getPlayer() {
		return player;
	}

	public boolean isWon() {
		return won;
	}

	public static void main(String[] args) {
		new Game();
	}

}
