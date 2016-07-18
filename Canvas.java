/*The JPanel on which everything is painted
 * pretty much the place where the entire display is confugures
 * */
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
public class Canvas extends JPanel{
	
	public static final int CANVAS_WIDTH = 1500;
	public static final int CANVAS_HEIGHT = 950;
	
	private int level = 1;
	
	private JLabel livesLabel;
	private JLabel scoreLabel;
	private JLabel levelLabel;
	
	private Player player;
	private ArrayList<Enemy> enemies;
	private ArrayList<Bullet> bullets;
	
	public Canvas()
	{
		super();
		player = new Player(CANVAS_WIDTH/2 - player.PLAYER_WIDTH/2, CANVAS_HEIGHT - player.PLAYER_HEIGHT);
		enemies = new ArrayList<Enemy>();
		bullets = new ArrayList<Bullet>();
		addEnemies();
		
		setSize(CANVAS_WIDTH,CANVAS_HEIGHT);
		
		livesLabel = new JLabel("LIVES: " + player.getNumLives());
		livesLabel.setForeground(Color.WHITE);
		scoreLabel = new JLabel("SCORE: " + 0);
		scoreLabel.setForeground(Color.WHITE);
		levelLabel = new JLabel("LEVEL: " + level);
		levelLabel.setForeground(Color.WHITE);
		
		JPanel scorePanel = new JPanel();
		scorePanel.setOpaque(false);
		scorePanel.setSize(CANVAS_WIDTH,CANVAS_HEIGHT/3);
		scorePanel.setLayout(new BorderLayout(CANVAS_WIDTH/4, 0));
		scorePanel.add(livesLabel, BorderLayout.LINE_START);
		scorePanel.add(scoreLabel, BorderLayout.CENTER);
		scorePanel.add(levelLabel, BorderLayout.LINE_END);
		add(scorePanel);
	}
	
	public void addEnemies()
	{
		for(int i = 0; i <level; i++)
		{
			enemies.add(new Enemy((int)(Math.random()*Canvas.CANVAS_WIDTH),20+(i)*Enemy.ENEMY_HEIGHT));
		}
	}
	
	public void clearBullets()
	{
		for(int i = 0; i<bullets.size(); i++)
		{
			bullets.remove(i);
		}
	}
	
	public Player getPlayer()
	{
		return player;
	}
	
	public ArrayList<Enemy> getEnemies()
	{
		return enemies;
	}
	
	public ArrayList<Bullet> getBullets()
	{
		return bullets;
	}
	
	public int getLevel()
	{
		return level;
	}
	
	public void setLevel(int l)
	{
		level = l;
	}
	
	public void setLivesLabel(int n)
	{
		livesLabel.setText("LIVES: " + n);
	}
	public void setScoreLabel(int n)
	{
		scoreLabel.setText("SCORE: " + n);
	}
	public void setLevelLabel(int n)
	{
		levelLabel.setText("LEVEL: " + n);
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		setBackground(Color.DARK_GRAY);
		g.setColor(Color.ORANGE);
		g.fillRect(player.getX(), player.getY(), player.PLAYER_WIDTH, player.PLAYER_HEIGHT);
		g.setColor(Color.MAGENTA);
		for(int i = 0; i <enemies.size(); i++)
			{g.fillRect(enemies.get(i).getX(), enemies.get(i).getY(), Enemy.ENEMY_WIDTH, Enemy.ENEMY_HEIGHT);}
		g.setColor(Color.RED);
		for(int i = 0; i <bullets.size(); i++)
			{g.fillRect(bullets.get(i).getX(), bullets.get(i).getY(), Bullet.BULLET_WIDTH, Bullet.BULLET_HEIGHT);}
	}
}