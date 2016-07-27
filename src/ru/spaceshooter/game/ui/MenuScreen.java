package ru.spaceshooter.game.ui;

public abstract class MenuScreen extends Screen
{
	protected Menu currentMenu;
	
	public void setMenu(Menu menu)
	{
		if(currentMenu!=null)
		{
			currentMenu.setActive(false);
			//currentMenu.finalize();
		}
		
		if(menu==null)
		{
			setActive(true);
			currentMenu=null;
			return;
		}
		
		if(!menu.hasOwner()) menu.setOwner(this);
		currentMenu=menu;
		currentMenu.setActive(true);
	}
	
	@Override
	public void update()
	{
		if(currentMenu!=null) currentMenu.update();
	}
	
	@Override
	public void dispose()
	{
		if(currentMenu!=null)
		{
			currentMenu.setActive(false);
			currentMenu.dispose();
		}
	}
}
