package game.object;

import java.awt.Graphics;
import java.awt.Rectangle;

public abstract class GameObject {
	
	protected double x, y;
	protected double dx, dy;
	protected final ID id;
	
	public GameObject(ID id){
		this.id = id;
	}
	
	public abstract void tick();
	public abstract void render(Graphics g);
	public abstract Rectangle getBounds();
	
	public double getX(){return x;}
	public double getY(){return y;}
	public double getDX(){return dx;}
	public double getDY(){return dy;}
	public ID getID(){return id;}
	
	public void setX(double x){this.x = x;}
	public void setY(double y){this.y = y;}
	public void setDX(double dx){this.dx = dx;}
	public void setDY(double dy){this.dy = dy;}
	
}
