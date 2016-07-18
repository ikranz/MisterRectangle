import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Bullet extends Entity{

	public static final int BULLET_WIDTH = 10;
	public static final int BULLET_HEIGHT = 10;
	
	private int y_velocity = 8;
	
	public Bullet(int myX, int myY) {
		super(myX, myY);
	}
	
	public void move()
	{
		setY(getY()+y_velocity);
	}
	
	public void setVelocity(int v)
	{
		y_velocity = v;
	}
	
	public int getVelocity()
	{
		return y_velocity;
	}
}
