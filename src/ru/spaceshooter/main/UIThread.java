package ru.spaceshooter.main;

public class UIThread extends UpdatingThread
{	
	GameCanvas item;
	
	public UIThread(GameCanvas gcv)
	{
		super("UIThread");
		item=gcv;
	}
	
	@Override
	protected void update()
	{
		item.repaint();
	}
}
