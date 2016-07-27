package ru.spaceshooter.game.ui;

import java.awt.Graphics;

import ru.spaceshooter.main.ResourceManager;

public class MainMenuScreen extends MenuScreen
{
	//MainMenu menu;
	
	protected MainMenuScreen()
	{
		setMenu(new MainMenu(false));
	}
	
	@Override
	public void initialize()
	{
		super.initialize();
	}
	
	@Override
	public void paint(Graphics g)
	{
		g.drawImage(ResourceManager.getSplash(), 0, 0, null);
		currentMenu.paint(g);
	}
	
	@Override
	public void update()
	{
		super.update();
	}

	@Override
	public void dispose()
	{
		super.dispose();
	}
}
