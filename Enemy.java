/*The magenta enemy
 * just rectangles that go side to side in different rows
 * */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Enemy extends Entity{
	
	public static final int ENEMY_WIDTH = 34;
	public static final int ENEMY_HEIGHT = 42;
	
	private int x_velocity = 0;
	private int bulletCounter = (int)(Math.random()*10);
	
	public Enemy(int myX, int myY) {
		super(myX, myY);
		if((int)(Math.random()*2) == 1)x_velocity = 15;
		else x_velocity = -15;
	}
	
	public void move()
	{
		this.setX(this.getX() + x_velocity);
		setBulletCounter(getBulletCounter() + 1);
	}
	
	public int getVelocity()
	{
		return x_velocity;
	}
	public void setVelocity(int v)
	{
		x_velocity = v;
	}
	
	public boolean hitWall()
	{
		if(getX()<=0 || getX()>=Canvas.CANVAS_WIDTH)return true;
		else return false;
	}
	
	public void resetPosition()
	{
		this.setX((int)(Math.random()*Canvas.CANVAS_WIDTH));
	}

	public int getBulletCounter() {
		return bulletCounter;
	}

	public void setBulletCounter(int bulletCounter) {
		this.bulletCounter = bulletCounter;
	}
}