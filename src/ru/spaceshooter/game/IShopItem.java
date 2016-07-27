package ru.spaceshooter.game;

import java.awt.Image;

public interface IShopItem
{	
	String getDescription();
	String getName();
	
	Image getIcon();
	// TODO: decide is it needed? Image getImage(); // large image to display
}
