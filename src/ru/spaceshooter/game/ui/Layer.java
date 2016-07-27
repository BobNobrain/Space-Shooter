package ru.spaceshooter.game.ui;

public abstract class Layer implements Showable
{	
	boolean active;
	@Override
	public void setActive(boolean active)
	{
		this.active=active;
	}
	
	@Override
	public boolean isActive()
	{
		return active;
	}
}
