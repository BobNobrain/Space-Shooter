package ru.spaceshooter.game.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import ru.spaceshooter.game.EventBroker;
import ru.spaceshooter.main.Profile;
import ru.spaceshooter.main.ResourceManager;

public class LoadingScreen extends Screen
{
	int progress;
	
	protected LoadingScreen()
	{
		progress=0;
	}
	
	@Override
	public void paint(Graphics g)
	{
		Image splash=ResourceManager.getSplash();
		if(splash!=null)
		{
			g.drawImage(splash, 0, 0, null);
			g.setColor(Color.white);
		}
		g.drawString("Loading... "+progress/10F, 30, 30);
	}
	
	@Override
	public void update()
	{
		// TODO: non-fiction loading of resourses
		progress+=40;
		
		if(progress>=1000 && ResourceManager.isReady())
		{
			// TODO: Profile.loadAll();
			Profile.setCurrentProfile("default");
			EventBroker.invoke("screenChange", Screen.getMainMenuScreen());
		}
	}

	@Override
	public void dispose()
	{
		//nothing
	}
	
}
