package ru.spaceshooter.main;

import ru.spaceshooter.game.ui.IUpdatable;

public class LogicThread extends UpdatingThread
{
	IUpdatable item;
	
	public LogicThread(IUpdatable undergo)
	{
		super("LogicsThread");
		item=undergo;
	}	
	
	@Override
	protected void update()
	{
		item.update();
	}
}
