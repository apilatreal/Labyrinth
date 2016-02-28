package game.main;

import javax.swing.JFrame;

public class Window {

	private JFrame frame;
	
	public Window(int width, int height, String title, Game game){
		frame = new JFrame(title);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.add(game);
		frame.setVisible(true);
		frame.setSize(width, height);
		game.start();
	}
	
}
