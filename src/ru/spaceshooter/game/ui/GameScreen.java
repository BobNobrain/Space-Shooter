package ru.spaceshooter.game.ui;

import java.awt.Color;
import java.awt.Graphics;

import ru.spaceshooter.game.EventBroker;
import ru.spaceshooter.game.EventBrokerListener;
import ru.spaceshooter.game.Level;
import ru.spaceshooter.game.Player;
import ru.spaceshooter.game.Weapon;
import ru.spaceshooter.main.GameCanvas;
import ru.spaceshooter.main.Input;
import ru.spaceshooter.main.Profile;

public class GameScreen extends MenuScreen implements EventBrokerListener
{
	Level current;
	Player player;
	ConfirmationWindow ask;
	
	
	public GameScreen()
	{	
		current=Level.getLevel(0);
		
		//player=current.getPlayer();
		player=new Player(Profile.current().getShip());
		player.moveTo(600, 700);
		current.setPlayer(player);
		
		ask=new ConfirmationWindow("Вы проиграли, ХА-ХА", "Попробовать ещё раз или как?");
		ask.setOwner(this);
		ask.setActive(false);
		setActive(true);
	}
	
	@Override
	public void initialize()
	{
		super.initialize();
		EventBroker.subscribe("keyTyped", this);
	}
	
	@Override
	public void paint(Graphics g)
	{
		g.setColor(Color.white);
		g.fillRect(0, 0, GameCanvas.BW, GameCanvas.BH);
		
		current.paint(g);
		
		paintPanels(g);
		
		if(currentMenu!=null) currentMenu.paint(g);
		
		ask.paint(g);
	}
	
	private void paintPanels(Graphics g)
	{
		if(player==null) return;
		
		int y=GameCanvas.BH;
		
		g.setColor(new Color(64, 160, 255));
		g.fillRect(0, y-12, (int)(GameCanvas.BW*player.getShieldRate()), 12);
		
		float hpr=player.getHpRate();
		if(hpr<0.2F) g.setColor(new Color(160, 16, 8));
		else g.setColor(new Color(8, 128, 16));
		g.fillRect(0, y-5, (int)(GameCanvas.BW*player.getHpRate()), 5);
		
		y-=65;
		
		Weapon[] wpn=player.getWeapons();
		int penx=5;
		g.setColor(new Color(192, 32, 16));
		for(int i=0; i<wpn.length; i++)
		{
			int w=wpn[i].getWeaponIcon().getWidth(null);
			int h=(int)(wpn[i].getLoadedPercent()*w);
			
			g.fillRect(penx, y-h+wpn[i].getWeaponIcon().getHeight(null), w, h);
			
			g.drawImage(wpn[i].getWeaponIcon(), penx, y, null);
			
			penx+=5+w;
		}
		
	}
	
	@Override
	public void update()
	{
		super.update();
		if(currentMenu==null && !ask.isActive()) current.update();
		
		ask.update();	
		if(ask.isFinished())
		{
			if(ask.getAsnwer())
			{
				EventBroker.invoke("screenChange", Screen.getGameScreen());
			}
			else
			{
				EventBroker.invoke("screenChange", new HangarScreen());
			}
		}
		
		if(current.isFinished())
		{
			Profile.current().levelUp();
			EventBroker.invoke("screenChange", new HangarScreen());
			return;
		}
		if(current.isLost() && !ask.isActive())
		{
			ask.show();
		}
	}
	
	@Override
	public void dispose()
	{
		EventBroker.unsubscribe(this);
		current.dispose();
		//sm.dispose();
		if(currentMenu!=null) currentMenu.dispose();
		ask.dispose();
	}

	@Override
	public boolean onEvent(String eventId, Object params)
	{
		if(eventId=="keyTyped" && isActive())
		{
			int code=(int)params;
			if(code==Input.KEY_ESCAPE.getCode())
			{
				setMenu(new PauseMenu());
				return true;
			}
		}
		return false;
	}
	
}
