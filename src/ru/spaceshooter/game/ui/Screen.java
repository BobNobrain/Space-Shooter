package ru.spaceshooter.game.ui;

import java.awt.Graphics;

public abstract class Screen extends Layer
{	
	public void initialize()
	{
		setActive(true);
	}
	public abstract void paint(Graphics g);
	public abstract void update();
	public abstract void dispose();
	
	protected Screen()
	{
		;
	}
	
	public static Screen getMainMenuScreen()
	{
		return new MainMenuScreen();
	}
	
	public static Screen getLoadingScreen()
	{
		return new LoadingScreen();
	}
	
	public static Screen getGameScreen()
	{
		return new GameScreen();
	}
	
	public static Screen getHangarScreen()
	{
		return new HangarScreen();
	}
}
