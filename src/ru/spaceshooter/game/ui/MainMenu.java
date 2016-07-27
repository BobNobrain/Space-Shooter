package ru.spaceshooter.game.ui;

import java.awt.Color;
import java.awt.Graphics;

import ru.spaceshooter.game.EventBroker;
import ru.spaceshooter.game.EventBrokerListener;

public class MainMenu extends MainScreenMenu implements EventBrokerListener
{	
	int frame;
	
	//StubWaitingWindow wnd;
	
	Color back=new Color(0F, 0F, 0F, 0.65F);
	
	public MainMenu(boolean continueEnabled)
	{
		super();
		frame=0;
		
		addMenuItem("Продолжить");
		if(!continueEnabled) items.get(0).disable();
		
		addMenuItem("Новая игра");
		addMenuItem("Настройки");
		addAndPlace(new RedMenuItem("Выход", 0, 0, 0, 0, 0));
		moveLastItemDown();
		
		setFontSize(36F);
		
		setActive(continueEnabled?0:1);
		
		//wnd=new StubWaitingWindow();
		
		EventBroker.subscribe("msgWindowClose", this);
	}
	
	@Override
	protected void onItemActivated(int pos)
	{
		switch(pos)
		{
		case 1:
			EventBroker.invoke("screenChange", Screen.getGameScreen());
			break;
		case 2:
			OptionsMenu om=new OptionsMenu();
			om.setParentMenu(this);
			om.setOwner(this.owner);
			owner.setMenu(om);
			break;
		case 3:
			EventBroker.invoke("exiting", null);
			break;
		}
	}
	
	@Override
	public void update()
	{
		super.update();
		if(frame<30) frame++;
		//wnd.update();
	}
	
	@Override
	protected void paintFone(Graphics g)
	{
		if(frame==30)
		{
			super.paintFone(g);
			return;
		}
		
		float c=1F*frame/30;
		g.setColor(back);
		g.fillRect(x, 0, width, (int)(height*c));
	}
	
	@Override
	public void paint(Graphics g)
	{
		if(frame==30)
		{
			super.paint(g);
		}
		else paintFone(g);
	}

	@Override
	public boolean onEvent(String eventId, Object params)
	{
		if(super.onEvent(eventId, params)) return true;
		return false;
	}
	
	@Override
	public void dispose()
	{
		super.dispose();
		EventBroker.unsubscribe(this);
	}
}
