package ru.spaceshooter.game;

import java.awt.Graphics;

public abstract class GameObject
{	
	int hp, maxhp;
	public int getHp() { return hp; }
	public int getMaxHp() { return maxhp; }
	protected void setHp(int nhp)
	{
		if(nhp<=0)
		{
			nhp=0;
			onDestroyed();
		}
		if(nhp>maxhp) nhp=maxhp;
		hp=nhp;
	}
	protected void setMaxHp(int val)
	{
		maxhp=val; if(hp>val) hp=val;
	}
	protected void decreaseHp(int dhp)
	{
		hp-=dhp;
		if(hp<=0)
		{
			hp=0;
			onDestroyed();
		}
		if(hp>maxhp)
		{
			hp=maxhp;
		}
	}
	
	public boolean isDestroyed() { return hp==0; }
	
	
	protected void onDestroyed()
	{
		// ...to be overrided
	}
	
	public void hit(Bullet b)
	{
		decreaseHp(b.getDamage());
	}
	
	public abstract Sprite getSprite();
	public void paint(Graphics g)
	{
		Sprite s=getSprite();
		if(s!=null) s.paint(g);
	}
	
	public void update()
	{
		getSprite().update();
	}
	
	public void heal()
	{
		hp=maxhp;
	}
}
