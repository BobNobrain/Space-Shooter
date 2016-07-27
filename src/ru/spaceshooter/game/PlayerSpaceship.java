package ru.spaceshooter.game;

import ru.spaceshooter.phys.VectorF;

public abstract class PlayerSpaceship extends Spaceship
{
	// MovingXXX
	protected boolean mleft, mright, mup, mdown;
	
	public final boolean isFriendly()
	{
		return true;
	}
	
	@Override
	protected float getBulletDirection()
	{
		return VectorF.UP;
	}
	
	public void moveUp(boolean flag)
	{
		mup=flag;
	}
	public void moveDown(boolean flag)
	{
		mdown=flag;
	}
	public void moveLeft(boolean flag)
	{
		mleft=flag;
	}
	public void moveRight(boolean flag)
	{
		mright=flag;
	}
	
	@Override
	public void update()
	{
		// TODO: Engine engine defines constant of acceleration
		getSprite().accel().x=mleft?(mright?0:-0.7F):(mright?0.7F:0);
		getSprite().accel().y=mup?(mdown?0:-0.7F):(mdown?0.7F:0);
		
		// if ship is moving, don't use friction
		/*if((mleft!=mright) || (mup!=mdown)) getSprite().friction=0;
		else getSprite().friction=0.25F;*/
		
		super.update();
	}
	
	public void recreateShield()
	{
		// TODO: after developing shields, make them full here
	}
}
