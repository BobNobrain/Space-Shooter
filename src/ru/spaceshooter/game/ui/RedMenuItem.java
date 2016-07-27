package ru.spaceshooter.game.ui;

import java.awt.Color;

public class RedMenuItem extends MenuItem
{
	public RedMenuItem(String txt, int position, int x, int y, int w, int h)
	{
		super(txt, position, x, y, w, h);
		a_f=new Color(1F, 0F, 0F, 0.25F);
	}	
	
}
