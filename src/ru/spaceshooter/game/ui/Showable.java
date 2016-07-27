package ru.spaceshooter.game.ui;

import java.awt.Graphics;

public interface Showable
{	
	void update();
	void paint(Graphics g);
	void setActive(boolean active);
	boolean isActive();
}
