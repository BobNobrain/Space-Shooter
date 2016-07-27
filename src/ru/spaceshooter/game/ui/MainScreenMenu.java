package ru.spaceshooter.game.ui;

import java.awt.Color;
import java.awt.Graphics;
/*
 * Parent class for every menu on the MainMenuScreen
 * (e.g. OptionsMenu, etc.)
 */
public class MainScreenMenu extends Menu
{	
	public MainScreenMenu()
	{
		super();
		x=300; yoffset=200;
		width=600; height=900;
	}
	
	protected void paintFone(Graphics g)
	{
		super.paintFone(g);
		
		Color back=new Color(0F, 0F, 0F, 0.65F);
		g.setColor(back);
		g.fillRect(x, 0, width, height);
	}
}
