/* Mr. Rectangle
 * the story of a lone orange rectangle
 * Created by Ian Kranz
 * */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
public class Game extends JFrame{
	
	private Canvas canvas;
	private Player player;
	private ArrayList<Enemy> enemies;
	private ArrayList<Bullet> bullets;
	private int score = 0;
	private boolean playing = true;
	
	public Game()
	{
		init();
		play();
	}
	
	public void init()
	{
		//The initiation method where everything is set up on the JFrame
		//Also where the player is given the ability to move
		setSize(Canvas.CANVAS_WIDTH,Canvas.CANVAS_HEIGHT+50);
		setTitle("Mr. Rectangle!");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		canvas = new Canvas();
		player = canvas.getPlayer();
		enemies = canvas.getEnemies();
		bullets = canvas.getBullets();
		
		add(canvas);
		
		addKeyListener(new KeyListener(){
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_W||e.getKeyCode() == KeyEvent.VK_UP)player.setVerticalVelocity(-15);
				if(e.getKeyCode() == KeyEvent.VK_S||e.getKeyCode() == KeyEvent.VK_DOWN)player.setVerticalVelocity(15);
				if(e.getKeyCode() == KeyEvent.VK_A||e.getKeyCode() == KeyEvent.VK_LEFT)player.setHorizontalVelocity(-15);
				if(e.getKeyCode() == KeyEvent.VK_D||e.getKeyCode() == KeyEvent.VK_RIGHT)player.setHorizontalVelocity(15);
				requestFocus();}
			

			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_W)player.setVerticalVelocity(player.getVerticalVelocity()+15);
				if(e.getKeyCode() == KeyEvent.VK_S)player.setVerticalVelocity(player.getVerticalVelocity()-15);
				if(e.getKeyCode() == KeyEvent.VK_A)player.setHorizontalVelocity(player.getHorizontalVelocity()+15);
				if(e.getKeyCode() == KeyEvent.VK_D)player.setHorizontalVelocity(player.getHorizontalVelocity()-15);
				requestFocus();
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}
		});
		setVisible(true);
	}
	
	public void play()
	{
		//Creating a new thread in which the rules of the game will be placed
		Thread t = new Thread(){
			@Override
			public void run(){
				while(playing)
				{
					//Check for hitting the wall
					for(int i = 0; i <enemies.size(); i++)
						if(enemies.get(i).hitWall())enemies.get(i).setVelocity(-enemies.get(i).getVelocity());
					if(player.getY()<=0)player.setY(0);
					if(player.getY()>=Canvas.CANVAS_HEIGHT-Player.PLAYER_HEIGHT)player.setY(Canvas.CANVAS_HEIGHT-player.PLAYER_HEIGHT);
					if(player.getX()<=0)player.setX(0);
					if(player.getX()>=Canvas.CANVAS_WIDTH-Player.PLAYER_WIDTH)player.setX(Canvas.CANVAS_WIDTH-player.PLAYER_WIDTH);
					
					//Beating up and removing the enemies
					checkPlayerEnemyCollision(player, enemies);
					
					//Looking to see if you get hit
					checkPlayerBulletCollision(player, bullets);
					
					//Checks to see if level is complete and advances to next level
					checkForNextLevel();
					
					//Add and remove bullets when necessary
					shoot();
					removeBullets();
					
					//Move everything
					player.move();
					for(int i = 0; i <enemies.size(); i++)
						enemies.get(i).move();
					for(int i = 0; i <bullets.size(); i++)
						bullets.get(i).move();
					
					//Repaint and give the time delay
					canvas.repaint();
					try {
						sleep(35);
					} catch (InterruptedException e){e.printStackTrace();}
					if(player.getNumLives() == 0)playing = false;
				}
				new EndingGui(score);
			}
		};
		t.start();
	}
	
	public void checkPlayerEnemyCollision(Player p, ArrayList<Enemy> e)
	{
		for(int i = 0; i <enemies.size(); i++)
		{
			if(p.getX()>e.get(i).getX()&&p.getX()<e.get(i).getX()+Enemy.ENEMY_WIDTH
			&& p.getY()>e.get(i).getY() && p.getY()<e.get(i).getY()+Enemy.ENEMY_HEIGHT)
				{e.remove(e.get(i));score+=1000;canvas.setScoreLabel(score);}
			else if(p.getX()+Player.PLAYER_WIDTH>e.get(i).getX()&&p.getX()+Player.PLAYER_WIDTH<e.get(i).getX()+Enemy.ENEMY_WIDTH
			&& p.getY()>e.get(i).getY() && p.getY()<e.get(i).getY()+Enemy.ENEMY_HEIGHT)
				{e.remove(e.get(i));score+=1000;canvas.setScoreLabel(score);}
			else if(p.getX()>e.get(i).getX()&&p.getX()<e.get(i).getX()+Enemy.ENEMY_WIDTH
			&& p.getY()+Player.PLAYER_HEIGHT>e.get(i).getY() && p.getY()+Player.PLAYER_HEIGHT<e.get(i).getY()+Enemy.ENEMY_HEIGHT)
				{e.remove(e.get(i));score+=1000;canvas.setScoreLabel(score);}
			else if(p.getX()+Player.PLAYER_WIDTH>e.get(i).getX()&&p.getX()+Player.PLAYER_WIDTH<e.get(i).getX()+Enemy.ENEMY_WIDTH
			&& p.getY()+Player.PLAYER_HEIGHT>e.get(i).getY() && p.getY()+Player.PLAYER_HEIGHT<e.get(i).getY()+Enemy.ENEMY_HEIGHT)
				{e.remove(e.get(i));score+=1000;canvas.setScoreLabel(score);}
			else if(p.getX()+Player.PLAYER_WIDTH/2>e.get(i).getX()&&p.getX()+Player.PLAYER_WIDTH/2<e.get(i).getX()+Enemy.ENEMY_WIDTH
			&& p.getY()+Player.PLAYER_HEIGHT/2>e.get(i).getY() && p.getY()+Player.PLAYER_HEIGHT/2<e.get(i).getY()+Enemy.ENEMY_HEIGHT)
				{e.remove(e.get(i));score+=1000;canvas.setScoreLabel(score);}
		}
	}
	
	public void checkPlayerBulletCollision(Player p, ArrayList<Bullet> b)
	{
		for(int i = 0; i <bullets.size(); i++)
		{
			if(bullets.get(i).getX()>player.getX()&&bullets.get(i).getX()<player.getX()+Player.PLAYER_WIDTH
			&&bullets.get(i).getY()>player.getY()&&bullets.get(i).getY()<player.getY()+Player.PLAYER_HEIGHT)
				{bullets.remove(i); player.loseLife();canvas.setLivesLabel(player.getNumLives());canvas.clearBullets();}
			else if(bullets.get(i).getX()+Bullet.BULLET_WIDTH>player.getX()&&bullets.get(i).getX()+Bullet.BULLET_WIDTH<player.getX()+Player.PLAYER_WIDTH
			&&bullets.get(i).getY()>player.getY()&&bullets.get(i).getY()<player.getY()+Player.PLAYER_HEIGHT)
				{bullets.remove(i); player.loseLife();canvas.setLivesLabel(player.getNumLives());canvas.clearBullets();}
			else if(bullets.get(i).getX()>player.getX()&&bullets.get(i).getX()<player.getX()+Player.PLAYER_WIDTH
			&&bullets.get(i).getY()+Bullet.BULLET_HEIGHT>player.getY()&&bullets.get(i).getY()+Bullet.BULLET_HEIGHT<player.getY()+Player.PLAYER_HEIGHT)
				{bullets.remove(i); player.loseLife();canvas.setLivesLabel(player.getNumLives());canvas.clearBullets();}
			else if(bullets.get(i).getX()+Bullet.BULLET_WIDTH>player.getX()&&bullets.get(i).getX()+Bullet.BULLET_WIDTH<player.getX()+Player.PLAYER_WIDTH
			&&bullets.get(i).getY()+Bullet.BULLET_HEIGHT>player.getY()&&bullets.get(i).getY()+Bullet.BULLET_HEIGHT<player.getY()+Player.PLAYER_HEIGHT)
				{bullets.remove(i); player.loseLife();canvas.setLivesLabel(player.getNumLives());canvas.clearBullets();}
		}
	}
	
	public void shoot()
	{
		for(int i = 0; i <enemies.size(); i++)
			if(enemies.get(i).getBulletCounter() == 10)
				{bullets.add(new Bullet(enemies.get(i).getX()+Enemy.ENEMY_WIDTH/2,enemies.get(i).getY()+Enemy.ENEMY_HEIGHT));
				enemies.get(i).setBulletCounter(0);}
	}
	public void removeBullets()
	{
		for(int i = 0; i <bullets.size(); i++)
		{
			if(bullets.get(i).getY()>=Canvas.CANVAS_HEIGHT)bullets.remove(bullets.get(i));
		}
	}
	
	public void checkForNextLevel()
	{
		if(player.getY()<=0 && enemies.size()==0)
		{
			canvas.setLevel(canvas.getLevel() + 1);
			player.resetPosition();
			canvas.addEnemies();
			canvas.setLevelLabel(canvas.getLevel());
			canvas.clearBullets();
		}
	}
	
	public boolean getPlaying()
	{
		return playing;
	}
	
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run()
			{
				new Game();
			}
		});
