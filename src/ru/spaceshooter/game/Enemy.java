package ru.spaceshooter.game;

import ru.spaceshooter.phys.VectorF;

public abstract class Enemy extends Spaceship
{
	@Override
	public final boolean isFriendly()
	{
		return false;
	}
	
	@Override
	protected float getBulletDirection()
	{
		return VectorF.DOWN;
	}
	
	public void reanimate()
	{
		setHp(getMaxHp());
	}
	
	boolean active;
	public boolean isActive() { return active; }
	public void setActive(boolean val) { active=val; }
	
	@Override
	protected void onDestroyed()
	{
		super.onDestroyed();
		setActive(false);
	}
	
	@Override
	public void update()
	{
		super.update();
		behave();
	}
	
	protected abstract void behave();
}
