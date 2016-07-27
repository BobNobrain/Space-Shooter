package ru.spaceshooter.game.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import ru.spaceshooter.game.EventBroker;
import ru.spaceshooter.game.IShopItem;
import ru.spaceshooter.main.GameCanvas;
import ru.spaceshooter.main.ResourceManager;

public class HangarMenu extends Menu
{	
	ShopItemsMenu subMenu=null;
	
	
	public HangarMenu()
	{
		super();
		
		x=GameCanvas.BW-400; yoffset=20;
		width=400; height=750;
		
		addMenuItem("В бой!");
		
		addMenuItem("Купить корабль");
		moveLastItemDown();
		addMenuItem("Арсенал");
		addMenuItem("Оснастка");
		
		addMenuItem("В главное меню");
		moveLastItemDown();
		addMenuItem("Выход");
		
		setFontSize(36F);
		setActive(0);
	}
	
	@Override
	protected void onItemActivated(int pos)
	{
		switch(pos)
		{
		case 0: // next level
			EventBroker.invoke("screenChange", Screen.getHangarScreen());
			break;
		case 1:
			// creating and showing menu of ships shop
			break;
		case 2:
			// DEBUG: this code was used to test shopItemsMenu painting, remove it and write correct one
			subMenu=new ShopItemsMenu();
			subMenu.setItemsPerLine(5);
			subMenu.moveTo(0, 300);
			subMenu.setActive(true);
			for(int i=0; i<7; i++)
			{
				subMenu.addItem(new IShopItem()
				{
					@Override
					public String getName(){return "Blabla";}
					@Override
					public Image getIcon(){return ResourceManager.getShopItem("test");}
					@Override
					public String getDescription(){return null;}
				});
			}
			break;
		case 3:
			// ... tuning menu
			break;
		case 4:
			// show popup question and then act
			break;
		case 5:
			// query the GameCanvas instance to show "exiting"
			EventBroker.invoke("exiting", null);
			break;
		}
	}
	
	@Override
	protected void paintFone(Graphics g)
	{
		super.paintFone(g);
		g.setColor(new Color(0, 0, 0, 64));
		g.fillRect(x, 0, width, height);
	}
	
	@Override
	public void paint(Graphics g)
	{
		if(subMenu!=null)
		{
			if(subMenu.isActive())
			{
				subMenu.paint(g);
				return;
			}
		}
		super.paint(g);
	}
}
