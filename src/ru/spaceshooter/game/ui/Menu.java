package ru.spaceshooter.game.ui;

import java.awt.Graphics;
import java.util.Vector;

import ru.spaceshooter.game.EventBroker;
import ru.spaceshooter.game.EventBrokerListener;
import ru.spaceshooter.main.Input;

public class Menu extends Layer implements EventBrokerListener
{
	protected Vector<MenuItem> items;
	
	protected int x, yoffset, width, height, itemHeight, margin;
	
	private float fontSize=1F;
	
	int pos;
	protected int getPos() { return pos; }
	
	protected Menu parent;
	public void setParentMenu(Menu p)
	{
		parent=p;
	}
	public boolean hasParent() { return parent!=null; }
	
	protected MenuScreen owner;
	public void setOwner(MenuScreen scr)
	{
		owner=scr;
	}
	public boolean hasOwner() { return owner!=null; }
	
	private boolean visible;
	public boolean isVisible() { return visible; }
	public void setVisibility(boolean value) { visible=value; }
	public void show() { visible=true; }
	public void hide() { visible=false; }
	
	
	public Menu()
	{
		items=new Vector<MenuItem>();
		width=1200; height=900;
		itemHeight=70; margin=2;
		visible=true;
		//setActive(true);
		parent=null;
		EventBroker.subscribe("keyTyped", this);
	}
	
	
	public int addMenuItem(String text)
	{
		MenuItem m=new MenuItem(text, items.size(), x, 0, width, itemHeight);
		if(items.size()==0)
		{
			m.y=yoffset;
			m.setActive(true);
		}
		else m.y=items.get(items.size()-1).y+items.get(items.size()-1).height+margin;
		m.fontSize=this.fontSize;
		items.add(m);
		return items.size()-1;
	}
	public int addMenuItem(MenuItem item)
	{
		if(items.size()==0) item.setActive(true);
		items.add(item);
		return items.size()-1;
	}
	public int addAndPlace(MenuItem item)
	{
		if(items.size()==0)
		{
			item.y=yoffset;
			item.setActive(true);
		}
		else item.y=items.get(items.size()-1).y+items.get(items.size()-1).height+margin;
		item.x=x;
		item.width=width;
		item.height=itemHeight;
		item.pos=items.size();
		item.fontSize=this.fontSize;
		
		items.add(item);
		return items.size()-1;
	}
	
	public void moveLastItemDown()
	{
		items.get(items.size()-1).y+=100;
	}
	
	public int moveDown()
	{
		if(items.size()==0) return -1;
		
		items.get(pos).setActive(false);
		do
		{
			++pos;
			if(pos>=items.size()) pos=0;
		}
		while(!items.get(pos).isEnabled());
		
		items.get(pos).setActive(true);
		return pos;
	}
	public int moveUp()
	{
		if(items.size()==0) return -1;
		
		items.get(pos).setActive(false);
		do
		{
			--pos;
			if(pos<0) pos=items.size()-1;
		}
		while(!items.get(pos).isEnabled());
		
		items.get(pos).setActive(true);
		return pos;
	}
	public void setActive(int pos)
	{
		if(items.size()==0) return;
		
		if(pos>=0 && pos<items.size())
		{
			items.get(this.pos).setActive(false);
			this.pos=pos;
			items.get(this.pos).setActive(true);
		}
	}
	public int getItemPosAt(int x, int y)
	{
		for(int i=0; i<items.size(); i++)
		{
			if(items.get(i).contains(x, y)) return i;
		}
		return -1;
	}
	
	public void setFontSize(float size)
	{
		for(int i=0; i<items.size(); i++)
		{
			items.get(i).fontSize=size;
		}
		fontSize=size;
	}
	
	public void activateItem()
	{
		if(active) onItemActivated(pos);
	}
	
	protected void onItemActivated(int pos)
	{
		//... to be overrided
	}
	
	public void paint(Graphics g)
	{
		if(!visible) return;
		paintFone(g);
		for(int i=0; i<items.size(); i++)
		{
			items.get(i).paint(g);
		}
	}
	protected void paintFone(Graphics g) {}
	
	public void update()
	{
		for(int i=0; i<items.size(); i++)
		{
			items.get(i).update();
		}
	}
	@Override
	public boolean onEvent(String eventId, Object params)
	{
		if(eventId=="keyTyped" && isActive())
		{
			int code=(int)params;
			if(code==Input.KEY_UP.getCode())
			{
				this.moveUp();
				return true;
			}
			if(code==Input.KEY_DOWN.getCode())
			{
				this.moveDown();
				return true;
			}
			if(code==Input.KEY_ENTER.getCode())
			{
				this.activateItem();
				return true;
			}
			return false;
		}
		return false;
	}
	
	public void navigateBack(boolean disposing)
	{
		if(hasParent() && hasOwner())
		{
			owner.setMenu(parent);
		}
		if(disposing)
		{
			dispose();
		}
	}
	
	public void dispose()
	{
		EventBroker.unsubscribe(this);
		for(int i=0; i<items.size(); i++)
		{
			items.get(i).finalize();
		}
		items.clear();
	}
}
