package ru.spaceshooter.game;

import java.awt.Graphics;

import ru.spaceshooter.main.GameCanvas;
import ru.spaceshooter.main.ResourceManager;
import ru.spaceshooter.utils.Pool;

public final class Bullet extends Sprite
{	
	// TODO: decide whether we need to use pool or create new objects
	
	private BulletType type;
	public BulletType getType() { return type; }
	
	private Pool<Bullet> user;
	public void setUser(Pool<Bullet> ptr) { user=ptr; }
	
	private int damage;
	public int getDamage() { return damage; }
	
	private boolean friendly=true;
	public boolean isFriendly() { return friendly; }
	public void setFriendly(boolean val) { friendly=val; }
	
	// TODO: change to protected ExplosionType explosion
	protected ExplosionType expType;
	public ExplosionType getExplosionType() { return expType; }
	
	
	public Bullet(BulletType type, boolean friendly)
	{
		super(null, 1, 1);
		this.friendly=friendly;
		this.type=BulletType.NONE;
		expType=ExplosionType.NONE;
		convert(type);
		pause();
		enablePhys(true);
	}
	
	
	public void convert(BulletType newType)
	{
		if(type==newType) return;
		if(newType==BulletType.NONE) { return; }
		
		type=newType;
		switch(type)
		{
		case LASER:
			src=ResourceManager.getBullet("b_laser");
			damage=10;
			expType=ExplosionType.LASER_BLAST;
			correctSize();
			break;
		case ROCKET:
			src=ResourceManager.getBullet("b_rocket");
			damage=15;
			expType=ExplosionType.REGULAR;
			correctSize();
			break;
		}
		
		//System.out.println("bullet");
	}
	
	void correctSize()
	{
		resize(src.getWidth(), src.getHeight());
	}
	
	@Override
	public void paint(Graphics g)
	{
		if(type!=BulletType.NONE)
		{
			super.paint(g);
		}
	}
	
	@Override
	public void update()
	{
		super.update();
		//when the bullet moves away from screen,
		if(getY()<-200 || getY()>GameCanvas.BH+200 || getX()<-200 || getX()>GameCanvas.BW+200)
		{
			//we should destroy it
			inactivate();
			user.dispose(this);
		}
	}
	
	public void inactivate()
	{
		type=BulletType.NONE;
		damage=0;
	}
	
	public boolean onHit()
	{
		switch(type)
		{
		case LASER:
			inactivate();
			user.dispose(this);
			return true;
		case ROCKET:
			inactivate();
			user.dispose(this);
			return true;
		
		default:
			return true;
		}
	}
}
