package ru.spaceshooter.game.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.Vector;

import ru.spaceshooter.game.IShopItem;
import ru.spaceshooter.main.ResourceManager;

public class ShopItemsMenu extends Layer
{
	public final int ITEM_SIZE=128;
	
	private int x, y;
	public int getX() { return x; }
	public int getY() { return y; }
	public void moveTo(int nx, int ny) { x=nx; y=ny; }
	public void moveBy(int dx, int dy) { x+=dx; y+=dy; }
	
	private int margin;
	public int getMargin()
	{
		return margin;
	}
	public void setMargin(int margin)
	{
		this.margin=margin;
	}
	
	private int horSize=10;
	public int getItemsPerLine() { return horSize; }
	public void setItemsPerLine(int value) { horSize=value; }
	
	protected int calculateWidth()
	{
		return horSize*(ITEM_SIZE+margin)+margin;
	}
	protected int calculateHeight()
	{
		return (items.size()/horSize+items.size()%horSize==0?0:1)*(ITEM_SIZE+margin)+margin;
	}
	
	private Vector<IShopItem> items;
	
	private int selected=-1;
	public int getSelectedIndex() { return selected; }
	public void selectItem(int index)
	{
		if(index<0) index=0;
		if(index>=items.size()) index=items.size()-1;
		selected=index;
	}
	
	
	public ShopItemsMenu()
	{
		x=y=0;
		margin=10;
		
		items=new Vector<IShopItem>();
	}
	
	
	public void addItem(IShopItem item)
	{
		items.add(item);
		if(selected==-1) selected=0;
	}
	
	@Override
	public void update()
	{
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void paint(Graphics g)
	{
		//g.fillRect(x-margin, y-margin, calculateWidth()+margin, calculateHeight()+margin);
		
		int penx=x+margin, peny=y+margin;
		int m=margin/2;
		for(int i=0; i<items.size(); i++)
		{
			if(i%horSize==0)
			{
				peny+=margin+ITEM_SIZE;
				penx=x;
			}
			if(selected==i)
			{
				g.setColor(new Color(255, 255, 255, 128));
			}
			else
			{
				g.setColor(new Color(0, 0, 0, 64));
			}
			g.fillRect(penx-m, peny-m, ITEM_SIZE+m, ITEM_SIZE+m);
			g.drawImage(items.get(i).getIcon(), penx, peny, null);
			penx+=margin+ITEM_SIZE;
		}
	}
}
