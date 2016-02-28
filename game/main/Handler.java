package game.main;

import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

import game.object.GameObject;

public class Handler {
	
	private List<GameObject> objects;
	
	public Handler(){
		objects = new LinkedList<GameObject>();
	}
	
	public void tick(){
		for(int i = 0; i < objects.size(); i++){
			objects.get(i).tick();
		}
	}
	
	public void render(Graphics g){
		for(int i = 0; i < objects.size(); i++){
			objects.get(i).render(g);
		}
	}
	
	public void add(GameObject g){
		objects.add(g);
	}
	
	public void remove(GameObject g){
		objects.remove(g);
	}
	
	public List<GameObject> getObjects(){
		return objects;
	}
	
	public void reset(){
		objects.clear();
	}
	
}
