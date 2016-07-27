package ru.spaceshooter.game.ui;

import java.awt.Color;
import java.awt.Graphics;

import ru.spaceshooter.game.EventBrokerListener;
import ru.spaceshooter.game.EventBroker;
import ru.spaceshooter.main.GameCanvas;
import ru.spaceshooter.main.Input;

public class PauseMenu extends Menu implements EventBrokerListener
{	
	int foneWidth=0;
	boolean closing=false;
	
	ConfirmationWindow exiting;
	
	
	public PauseMenu()
	{
		width=400;
		yoffset=200;
		height=GameCanvas.BH;
		x=GameCanvas.BW-width;
		
		addMenuItem("Продолжить");
		addMenuItem("Настройки");
		addMenuItem("Покинуть уровень");
		addMenuItem("Выйти в меню");
		addMenuItem("Выйти из игры");
		
		exiting=new ConfirmationWindow("Вы действительно хотите выйти в меню?", "Весь прогресс текущего уровня будет уничтожен!");
		exiting.setOwner(this);
		
		EventBroker.subscribe("msgWindowClose", this);
		
		setFontSize(36F);
	}
	
	@Override
	public void setOwner(MenuScreen scr)
	{
		super.setOwner(scr);
		scr.setActive(false);
	}
	
	@Override
	protected void onItemActivated(int pos)
	{
		switch(pos)
		{
		case 0:
			if(hasOwner())
			{
				/*setActive(false);
				closing=true;
				owner.setActive(true);*/
				//owner.setMenu(null);
				closing=true;
				setActive(false);
			}
			break;
		case 1:
			// TODO: new IngamePropertiesMenu()
			break;
		case 3:
			setActive(false);
			exiting.show();
			break;
		case 4:
			EventBroker.invoke("exiting", null);
		}
	}
	
	@Override
	public boolean onEvent(String eventId, Object params)
	{
		if(super.onEvent(eventId, params)) return true;
		if(eventId=="msgWindowClose")
		{
			ConfirmationWindow param=null;
			try
			{
				param=(ConfirmationWindow)params;
			} catch(ClassCastException ex) {}
			if(param==exiting)
			{
				if(exiting.getAsnwer())
				{
					EventBroker.invoke("screenChange", Screen.getMainMenuScreen());
					return true;
				}
				
			}
		}
		if(eventId=="keyTyped" && isActive())
		{
			int code=(int)params;
			if(code==Input.KEY_ESCAPE.getCode())
			{
				setActive(false);
				closing=true;
				return true;
			}
		}
		return false;
	}
	
	@Override
	public void paint(Graphics g)
	{
		if(width==foneWidth)
		{
			super.paint(g);
			exiting.paint(g);
		}
		else paintFone(g);
	}
	@Override
	protected void paintFone(Graphics g)
	{
		super.paintFone(g);
		g.setColor(new Color(0F, 0F, 0F, 0.65F));
		g.fillRect(x, 0, foneWidth, height);
	}
	
	@Override
	public void update()
	{
		super.update();
		if(closing)
		{
			if(foneWidth>0) foneWidth-=40;
			if(foneWidth<=0) { foneWidth=0; closing=false; owner.setMenu(null); dispose(); }
		}
		else if(isActive())
		{
			if(foneWidth<width) foneWidth+=20;
			if(foneWidth>width) foneWidth=width;
		}
		x=GameCanvas.BW-foneWidth;
		
		exiting.update();
	}
	
	@Override
	public void dispose()
	{
		super.dispose();
		exiting.dispose();
	}
}
