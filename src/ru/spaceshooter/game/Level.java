package ru.spaceshooter.game;

import java.awt.Graphics;
import java.awt.Image;

import ru.spaceshooter.game.levels.*;
import ru.spaceshooter.main.GameCanvas;
import ru.spaceshooter.utils.Pool;

public abstract class Level
{
	protected Image fone;
	
	public abstract Level nextLevel();
	public abstract void checkFinished();
	
	private boolean finished, lost;
	protected void finish()
	{
		finished=true;
	}
	protected void onLost() { lost=true; }
	public boolean isFinished() { return finished; }
	public boolean isLost() { return lost; }
	
	protected Enemy[] enemies;
	// TODO: also friends
	protected GameObject[] decorations;
	
	protected Pool<Explosion> explosions;
	
	
	public static Level getLevel(int number)
	{
		if(number==0) return new Level0();
		return null;
	}
	
	protected Pool<Bullet> bullets;
	// TODO: protected Pool? array? Vector? enemies, friends;
	protected Player player;
	public Player getPlayer() { return player; }
	public void setPlayer(Player pl)
	{
		player=pl;
		pl.setWrapper(this);
	}
	public int getPlayerX() { return player.getX(); }
	public int getPlayerY() { return player.getY(); }
	
	
	
	protected void explode(Bullet b)
	{
		explode(b.getX(), b.getY(), b.getExplosionType());
	}
	
	protected void checkCollisions()
	{
		Object[] bs=bullets.getArray();
		
		OUTERLOOP:
		for(int i=0; i<bs.length; i++)
		{
			if(bs[i]==null) continue;
			Bullet b=(Bullet)bs[i];
			if(b.getType()==BulletType.NONE) continue;
			
			if(b.isFriendly())
			{
				for(int j=0; j<enemies.length; j++)
				{
					if(enemies[j]==null) continue;
					if(!enemies[j].isActive()) continue;
					if(enemies[j].getSprite().collidesWith(b))
					{
						enemies[j].hit(b);
						explode(b);
						if(b.onHit())
						{
							//bullets.dispose(b);
							continue OUTERLOOP;
						}
					}
				}
			}
			else
			{
				//friends...
				
				if(player.ship.getSprite().collidesWith(b))
				{
					player.ship.hit(b);
					explode(b);
					b.onHit();
				}
			}
		}
	}
	
	public Bullet getBulletInstance()
	{
		Bullet b=bullets.getNewObject();
		b.setUser(bullets);
		return b;
	}
	
	// for future use within other GameObject sons
	public void explode(int x, int y, ExplosionType type)
	{
		if(type!=ExplosionType.NONE)
		{
			Explosion expl=explosions.getNewObject();
			expl.setType(type);
			expl.explodeAt(x, y);
		}
	}
	
	public abstract void dispose();
	
	protected void paintFone(Graphics g)
	{
		if(fone!=null)
			g.drawImage(fone, 0, 0, null);
	}
	public void paint(Graphics g)
	{
		paintFone(g);
		Object[] bs=bullets.getArray();
		for(int i=0; i<bs.length; i++)
		{
			if(bs[i]!=null)
			{
				((Bullet)bs[i]).paint(g);
			}
		}
		
		for(int i=0; i<enemies.length; i++)
		{
			if(enemies[i].isActive())
			{
				enemies[i].paint(g);
			}
		}
		
		for(int i=0; i<decorations.length; i++)
		{
			decorations[i].paint(g);
		}
		
		player.paint(g);
		/*g.setColor(Color.red);
		g.fillRect(50, 50, 50, 50);*/
		//...
		
		Object[] expls=explosions.getArray();
		for(int i=0; i<expls.length; i++)
		{
			if(expls[i]!=null)
			{
				((Explosion)expls[i]).paint(g);
			}
		}
	}
	
	public void update()
	{
		Object[] bs=bullets.getArray();
		for(int i=0; i<bs.length; i++)
		{
			if(bs[i]!=null)
			{
				((Bullet)bs[i]).update();
			}
		}
		
		Object[] expls=explosions.getArray();
		for(int i=0; i<expls.length; i++)
		{
			if(expls[i]!=null)
			{
				((Explosion)expls[i]).update();
			}
		}
		
		for(int i=0; i<enemies.length; i++)
		{
			if(enemies[i].isActive())
			{
				enemies[i].update();
			}
		}
		
		for(int i=0; i<decorations.length; i++)
		{
			decorations[i].update();
		}
		
		player.update();
		Sprite s=player.ship.getSprite();
		if(s.getX()<0)
		{
			s.velocity().x*=-1;
			s.location().x=0;
		}
		if(s.getX()+s.getWidth()>GameCanvas.BW)
		{
			s.velocity().x*=-1;
			s.location().x=GameCanvas.BW-s.getWidth();
		}
		if(s.getY()<0)
		{
			s.velocity().y*=-1;
			s.location().y=0;
		}
		if(s.getY()+s.getHeight()>GameCanvas.BH)
		{
			s.velocity().y*=-1;
			s.location().y=GameCanvas.BH-s.getHeight();
		}
		
		checkCollisions();
		checkFinished();
	}
}
