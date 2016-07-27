package ru.spaceshooter.game.ui;

import java.awt.Graphics;
import java.awt.Image;

import ru.spaceshooter.game.Sprite;
import ru.spaceshooter.main.Profile;
import ru.spaceshooter.main.ResourceManager;

public class HangarScreen extends MenuScreen
{
	private Image fone;
	private Sprite ship;
	
	
	public HangarScreen()
	{
		fone=ResourceManager.getFone("hangar");
		ship=Profile.current().getShip().getSprite();
		ship.moveTo(150-ship.getWidth()/2, 150-ship.getHeight()/2);
		
		setMenu(new HangarMenu());
	}
	
	
	@Override
	public void paint(Graphics g)
	{
		if(fone!=null)
		{
			g.drawImage(fone, 0, 0, null);
		}
		if(ship!=null)
		{
			ship.paint(g);
		}
		
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
