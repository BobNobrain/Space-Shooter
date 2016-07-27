package ru.spaceshooter.game.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

import ru.spaceshooter.game.EventBroker;
import ru.spaceshooter.game.EventBrokerListener;
import ru.spaceshooter.main.Input;

public class TrackbarMenuItem extends MenuItem implements EventBrokerListener
{
	protected int value, min, max, step;
	public int getValue() { return value; }
	public int getMaximum() { return max; }
	public int getMinimum() { return min; }
	public int getStep() { return step; }
	public void setValue(int val)
	{
		value=Math.max(min, Math.min(val, max));
		text=originalText+value;
		// to be overriden is children classes
	}
	public void setMaximum(int nmax) { if(nmax>min) { max=nmax; if(value>max) value=max; } }
	public void setMinimum(int nmin) { if(max>nmin) { min=nmin; if(value<min) value=min; } }
	public void setStep(int nstep) { step=nstep; }
	
	String originalText;
	
	
	public TrackbarMenuItem(String txt, int position, int x, int y, int w, int h)
	{
		super(txt+0, position, x, y, w, h+20);
		originalText=txt;
		min=value=0; max=100; step=5;
		
		EventBroker.subscribe("keyTyped", this);
	}
	
	
	@Override
	public void paint(Graphics g)
	{
		super.paint(g);
		
		g.setColor(new Color(0F, 0F, 0F, 0.65F));
		g.fillRect(x+15, y+height-16, width-30-getStripeWidth(), 12);
		g.setColor(a_s);
		float per=1F*value/(max-min);
		if(per>0) g.fillRect(x+20, y+height-14, (int)((width-40-getStripeWidth())*per), 8);
		
		// arrows
		Polygon arrow=new Polygon();
		arrow.addPoint(5, 0);
		arrow.addPoint(5, 11);
		arrow.addPoint(0, 6);
		arrow.addPoint(0, 5);
		arrow.translate(x+7, y+height-16);
		g.fillPolygon(arrow);
		arrow.reset();
		arrow.addPoint(0, 0);
		arrow.addPoint(0, 11);
		arrow.addPoint(5, 6);
		arrow.addPoint(5, 5);
		arrow.translate(x+width-getStripeWidth()-13, y+height-16);
		g.fillPolygon(arrow);
	}
	@Override
	public boolean onEvent(String eventId, Object params)
	{
		if(eventId=="keyTyped" && active)
		{
			int code=(int)params;
			if(code==Input.KEY_LEFT.getCode())
			{
				setValue(value-step);
				return true;
			}
			if(code==Input.KEY_RIGHT.getCode())
			{
				setValue(value+step);
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void finalize()
	{
		super.finalize();
		EventBroker.unsubscribe(this);
	}
}
