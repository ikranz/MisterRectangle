/*Just a shape
 * represents every object in the game
 * */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Entity {
	
	private int x, y;
	
	public Entity(int myX, int myY)
	{
		x = myX;
		y = myY;
	}
	
	public int getX()
	{
		return x;
	}
	public void setX(int myX)
	{
		x = myX;
	}
	public int getY()
	{
		return y;
	}
	public void setY(int myY)
	{
		y = myY;
	}
}