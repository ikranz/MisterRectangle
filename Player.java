/*Our orange rectangle
 * able to move anywhere and in any direction of the space
 * */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Player extends Entity{
	
	public static final int PLAYER_WIDTH = 42;
	public static final int PLAYER_HEIGHT = 51;
	
	private int x_velocity = 0;
	private int y_velocity = 0;
	
	private int lives = 3;
	
	public Player(int myX, int myY)
	{
		super(myX, myY);
	}
	
	public void resetPosition()
	{
		this.setX(Canvas.CANVAS_WIDTH/2 - PLAYER_WIDTH/2);
		this.setY(Canvas.CANVAS_HEIGHT - PLAYER_HEIGHT);
	}
	
	public void move()
	{
		this.setX(this.getX() + x_velocity);
		this.setY(this.getY() + y_velocity);
	}
	
	public boolean hitXWall()
	{
		if(getX()<=0 || getX()>=Canvas.CANVAS_WIDTH)return true;
		else return false;
	}
	public boolean hitYWall()
	{
		if(getY()<=0 || getY()>=Canvas.CANVAS_HEIGHT)return true;
		else return false;
	}
	
	public void loseLife()
	{
		lives--;
		resetPosition();
	}
	
	public int getNumLives()
	{
		return lives;
	}
	
	public int getVerticalVelocity()
	{
		return y_velocity;
	}
	public void setVerticalVelocity(int myYV)
	{
		y_velocity = myYV;
	}
	public int getHorizontalVelocity()
	{
		return x_velocity;
	}
	public void setHorizontalVelocity(int myXV)
	{
		x_velocity = myXV;
	}
}
