package ru.spaceshooter.game.ui;

import java.awt.Color;
import java.awt.Graphics;

import ru.spaceshooter.main.GameCanvas;

public abstract class WaitingMessageWindow extends MessageWindow
{	
	float progress;
	public float getProgress() { return progress; }
	public int getPercentage() { return Math.round(progress*100); }
	public void setProgress(float value) { progress=Math.max(0F, Math.min(value,  1F)); }
	public void setPercentage(int p) { setProgress(p/100F); }
	
	
	public WaitingMessageWindow(String title, String msg)
	{
		super();
		setTitle(title);
		setText(msg);
		progress=0F;
	}
	
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		
		if(foneHeight!=height) return;
		
		g.setColor(new Color(0F, 0F, 0F, 0.7F));
		g.fillRect(10, (GameCanvas.BH+foneHeight)/2-20, GameCanvas.BW-20, 10);
		g.setColor(borderColor);
		g.fillRect(12, (GameCanvas.BH+foneHeight)/2-18, (int)((GameCanvas.BW-24)*progress), 6);
	}
}
