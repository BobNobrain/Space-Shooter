package ru.spaceshooter.game.ui;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

import ru.spaceshooter.main.ResourceManager;

public class MenuItem
{	
	public String text;
	public int pos;
	int x, y, width, w, height;
	boolean enabled, active;
	public float fontSize;
	
	// Active fone color
	protected Color a_f=new Color(1F, 1F, 1F, 0.25F);
	// Inactive stripe color
	protected Color i_s=new Color(1F, 1F, 1F, 0.15F);
	// Active stripe color
	protected Color a_s=Color.white;
	// Font color
	protected Color f=Color.white;
	// Disabled font color
	protected Color d_f=new Color(1F, 1F, 1F, 0.25F);
	
	protected int getStripeWidth() { return 15; }
	
	
	public MenuItem(String txt, int position, int x, int y, int w, int h)
	{
		text=txt;
		pos=position;
		this.x=x; this.y=y; this.width=w; this.height=h;
		enabled=true; active=false;
	}
	
	public boolean isEnabled() { return enabled; }
	public void enable() { enabled=true; }
	public void disable() { enabled=false; active=false; }
	
	public boolean isActive() { return active; }
	public void setActive(boolean act) { if(enabled) active=act; }
	
	public boolean contains(int x, int y)
	{
		if(x>this.x+width) return false;
		if(x<this.x) return false;
		if(y>this.y+height) return false;
		if(y<this.y) return false;
		return true;
	}
	
	public void paint(Graphics g)
	{
		paintFone(g);
		int labelx=x+15, labely=y;
		
		FontMetrics m=g.getFontMetrics(ResourceManager.defFont().deriveFont(fontSize));
		labely+=(height+m.getAscent()-m.getDescent())/2;
		
		if(!enabled)
		{	
			g.setColor(d_f);
			g.setFont(ResourceManager.defFont().deriveFont(fontSize));
			g.drawString(text, labelx, labely);
			
			
			return;
		}
		
		g.setColor(Color.white);
		g.setFont(ResourceManager.defFont().deriveFont(fontSize));
		g.drawString(text, labelx, labely);
	}
	protected void paintFone(Graphics g)
	{
		if(!enabled)
		{
			g.setColor(i_s);
			g.fillRect(x+width-getStripeWidth(), y, getStripeWidth(), height);
			
			return;
		}
		if(active)
		{
			g.setColor(a_f);
			g.fillRect(x+width-w, y, w, height);
			g.setColor(a_s);
			g.fillRect(x+width-getStripeWidth(), y, getStripeWidth(), height);
		}
		else
		{
			g.setColor(i_s);
			g.fillRect(x+width-getStripeWidth(), y, getStripeWidth(), height);
		}
	}
	
	public void update()
	{
		if(active)
		{
			if(w<width) w+=50;
			if(w>width) w=width;
		}
		else
		{
			if(w>0) w-=100;
			if(w<0) w=0;
		}
	}
	
	public void finalize() { /* to be overrided... */ }
}
