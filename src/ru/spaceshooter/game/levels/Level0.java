package ru.spaceshooter.game.levels;

import java.awt.Graphics;

import ru.spaceshooter.game.Bullet;
import ru.spaceshooter.game.BulletFactory;
import ru.spaceshooter.game.Enemy;
import ru.spaceshooter.game.Explosion;
import ru.spaceshooter.game.ExplosionFactory;
import ru.spaceshooter.game.GameObject;
import ru.spaceshooter.game.Level;
import ru.spaceshooter.game.Player;
import ru.spaceshooter.game.Sprite;
import ru.spaceshooter.game.TestEnemy1;
import ru.spaceshooter.main.ResourceManager;
import ru.spaceshooter.utils.Pool;
import ru.spaceshooter.utils.TestEnemy1Factory;

public class Level0 extends Level
{			
	public Level0()
	{
		bullets=new Pool<Bullet>(new BulletFactory(), 300);
		explosions=new Pool<Explosion>(new ExplosionFactory(), 80);
		
		enemies=new Enemy[1];
		for(int i=0; i<1; i++)
		{
			enemies[i]=new TestEnemy1();
			enemies[i].getSprite().moveTo(75+150*i, 50);
			//System.out.println(enemies[i].getSprite().getCollisionBounds());
			enemies[i].setActive(true);
			enemies[i].setWrapper(this);
			
			// DEBUG:
			break;
		}
		
		decorations=new GameObject[0];
		
		fone=ResourceManager.getFone("lvl0");
	}
	
	
	@Override
	public Level nextLevel()
	{
		return null;
	}
	
	@Override
	public void checkFinished()
	{
		if(enemies[0].isDestroyed())
			finish();
		else if(player.getHpRate()<=0F)
		{
			onLost();
		}
	}


	@Override
	public void dispose()
	{
		bullets.dispose();
	}
	
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
	}
	
	@Override
	public void update()
	{
		super.update();
	}
}
